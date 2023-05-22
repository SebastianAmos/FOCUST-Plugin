package clcm.focust;

import ij.IJ;
import ij.ImagePlus;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import ij.plugin.ChannelSplitter;
import ij.plugin.ImageCalculator;
import inra.ijpb.plugins.AnalyzeRegions3D;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clijx.CLIJx;
import net.haesleinhuepf.clijx.morpholibj.MorphoLibJMarkerControlledWatershed;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import javax.swing.SwingUtilities;

/**
 * @author SebastianAmos
 *
 */

public class Segment {

	public static ImagePlus[] channelsSpheroid;
	public static ImagePlus primaryObjectSpheroid;
	public static ImagePlus secondaryObjectSpheroid;
	private static AnalyzeRegions3D analyze3D = new AnalyzeRegions3D();
	static ResultsTable primaryC1Intensity = new ResultsTable();
	static ResultsTable primaryC3Intensity = new ResultsTable();
	
	// Could make these user-input strings to provide greater flexibility 
	private static String primaryPrefix = new String("Primary_Objects_");
	private static String secondaryPrefix = new String("Secondary_Objects_");
	private static String corePrefix = new String("Inner_Secondary_");
	private static String outerPrefix = new String("Outer_Secondary_");
	

	public static void threadLog(final String log) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				IJ.log(log);
			}
		});
	}

	 
	/*
	 * Implementing a file extension filter may be useful.  
	 */
	// (list[i].endsWith(".tif")||list[i].endsWith(".nd2")||list[i].endsWith("_D3D"));
	// IJ.showProgress(i+1, list.length);

	
	/*TODO: 
	 * - implement a file extension filter - could be a good way to then distinguish between original vs pre-segmented images later on too...
	 * - make erosion of secondary object relative to it's original size, instead of an abitrary number of iterations.
	 * - implement intensity measurements in core vs periphery
	 * - make intensity analysis dependent on then number of channels in current image array - not fixed to 4. 
	 * - add core vs periphery intensity measurements to the secondary results table.
	 */



	/* ------------------------------------------------------------------------------------
	 * This method segments primary and secondary objects based on user-defined parameters.
	 * Objects are then used for region-restricted intensity analysis and 3D measurements. 
	 * -----------------------------------------------------------------------------------*/
	public static void ProcessSpheroid(boolean analysisOnly) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {

				// grab the file names
				File f = new File(SpheroidView.inputDir);
				String[] list = f.list();
				String dir = SpheroidView.inputDir;
				int count = 0;
					
				
				
				// If analysis-only-mode, create a new list[] containing image names that DO NOT match the prefix expectations i.e. are the original images, not the objects.
					if(analysisOnly) {
						ArrayList<String> tempList = new ArrayList<>();
						for (String imgName : list) {
							if (!imgName.startsWith(primaryPrefix) && !imgName.startsWith(secondaryPrefix) && !imgName.startsWith(corePrefix) && !imgName.startsWith(outerPrefix)) {
								tempList.add(imgName);
							}
						}
							list = new String[tempList.size()];
							list = tempList.toArray(list);
					}
					
					
					
				// Iterate through each image in the directory and segment the selected primary and secondary channels.
				for (int i = 0; i < list.length; i++) {
					count++;
					String path = SpheroidView.inputDir + list[i];
					IJ.log("---------------------------------------------");
					IJ.log("Processing image " + count + " of " + list.length);
					IJ.log("Current image name: " + list[i]);
					IJ.log("---------------------------------------------");
					ImagePlus imp = IJ.openImage(path);
					Calibration cal = imp.getCalibration();
					channelsSpheroid = ChannelSplitter.split(imp);
					int primaryChannelChoice = SpheroidView.primaryChannelChoice;
					int secondaryChannelChoice = SpheroidView.secondaryChannelChoice;
					String imgName = imp.getTitle();
					
					/*
					 * create and measure primary objects
					 */
					
					
					// If analysis-only-mode, find the right primary object file for the current image.  
					if(analysisOnly) {
						String fileName = list[i].replace(".nd2", ".tif");
						primaryObjectSpheroid = IJ.openImage(SpheroidView.inputDir + primaryPrefix + fileName);
					} else {
						GPUSpheroidPrimaryObject(primaryChannelChoice);
					}
					
					IJ.log("Processing Primary Object...");
					IJ.resetMinAndMax(primaryObjectSpheroid);
					primaryObjectSpheroid.setCalibration(cal);
					if(!analysisOnly) {
						IJ.saveAs(primaryObjectSpheroid, "TIF", dir + "Primary_Objects_" + imgName);
					}
					ResultsTable primaryResults = analyze3D.process(primaryObjectSpheroid);
					
					/*
					// saving this just for testing (checking for row mis-match in the final table)
					String pRName = dir + "Primary_Results.csv";
					// saving this just for testing (checking for row mis-match in the final table)
					try {
						primaryResults.saveAs(pRName);
					} catch (IOException e) {
						e.printStackTrace();
						IJ.log("Unable to save primary results table.");
					}
					*/
					
					
					
					/*
					 * create and measure secondary object
					 */
				
					IJ.log("Processing Secondary Object...");
					
					
					// If analysis-only-mode, find the right secondary object file for the current image.   
					if(analysisOnly) {
						String fileName = list[i].replace(".nd2", ".tif");
						secondaryObjectSpheroid = IJ.openImage(SpheroidView.inputDir + secondaryPrefix + fileName);
					} else {
						GPUSpheroidSecondaryObject(secondaryChannelChoice);
					}
					
					
					secondaryObjectSpheroid.setCalibration(cal);
					IJ.resetMinAndMax(secondaryObjectSpheroid);
					if(!analysisOnly) {
						IJ.saveAs(secondaryObjectSpheroid, "TIF", dir + "Secondary_Objects_" + imgName);
					}
					
					ResultsTable secondaryResults = analyze3D.process(secondaryObjectSpheroid);
					
					
					/*
					// saving this just for testing (checking for row mis-match in the final table)
					String sRName = dir + "Secondary_Results.csv";
					try {
						secondaryResults.saveAs(sRName);
					} catch (IOException e) {
						e.printStackTrace();
						IJ.log("Unable to save secondary results table.");
					} */
					
					
					/* Create inner ROI
					* --> Duplicate whole spheroid, erode by fixed iteration count
					* TODO: Implement a more programmatic approach for erosion. i.e. to 50 % of original secondary object volume, rather than a fixed number of iterations.
					*/
					
					ImagePlus innerROI = secondaryObjectSpheroid.duplicate();
					IJ.run(innerROI, "Make Binary", "method=Default background=Dark black");
					IJ.run(innerROI, "Options...", "iterations=70 count=1 black do=Erode stack");
					IJ.saveAs(innerROI, "TIF", dir + "Inner_Secondary_" + imgName);
					
					// Create outer ROI
					ImagePlus outerROI = ImageCalculator.run(secondaryObjectSpheroid, innerROI, "Subtract create stack");
					IJ.saveAs(outerROI, "TIF", dir + "Outer_Secondary_" + imgName);
					
					
					
					
					/* 
					 * Intensity measurements and building results tables 
					 */
					// Primary Objects

					// TODO: change c1 and c3 back from static. Unless best practice? That was just part of testing.
					
					primaryC1Intensity = IntensityMeasurements.Process(channelsSpheroid[0], primaryObjectSpheroid);
					ResultsTable primaryC2Intensity = IntensityMeasurements.Process(channelsSpheroid[1], primaryObjectSpheroid);
					primaryC3Intensity = IntensityMeasurements.Process(channelsSpheroid[2], primaryObjectSpheroid);
					ResultsTable primaryC4Intensity = IntensityMeasurements.Process(channelsSpheroid[3], primaryObjectSpheroid);
					
					/* 
					 * Write image name and grouping (where entered) data to a results table.
					 * This allows whole columns to be extracted as Variable[] to construct the final table.
					 */

					// get counter
					int primaryTableLength = primaryC1Intensity.getColumnAsVariables("Label").length;
					
					// new results table type
					ResultsTable primaryImageData = new ResultsTable();
					
					// check to see if grouping info has been entered, if yes, then populate the imageData table, if not, ignore. 
					if (SpheroidView.groupingInfo.isEmpty()) {
						
							for (int o = 0; o < primaryTableLength ; o++) {
								primaryImageData.addRow();
								primaryImageData.addValue("ImageID", imgName);
							}
							
					} else {
						
						String group = SpheroidView.groupingInfo;
						
						for (int o = 0; o < primaryTableLength ; o++) {
							primaryImageData.addRow();
							primaryImageData.addValue("ImageID", imgName);
							primaryImageData.addValue("Group", group);
						}
					}
					
					
					
					
					// Build the  final primary results table
					ResultsTable primaryFinalTable = new ResultsTable();
					primaryFinalTable.setColumn("Label", primaryC1Intensity.getColumnAsVariables("Label"));
					primaryFinalTable.setColumn("ImageID", primaryImageData.getColumnAsVariables("ImageID"));
					if (!SpheroidView.groupingInfo.isEmpty()) {
						primaryFinalTable.setColumn("Group", primaryImageData.getColumnAsVariables("Group"));
					}
					primaryFinalTable.setColumn("Volume", primaryResults.getColumnAsVariables("Volume"));
					primaryFinalTable.setColumn("Voxel_Count", primaryResults.getColumnAsVariables("VoxelCount"));
					primaryFinalTable.setColumn("Sphericity", primaryResults.getColumnAsVariables("Sphericity"));
					primaryFinalTable.setColumn("Elongation", primaryResults.getColumnAsVariables("Elli.R1/R3"));
					primaryFinalTable.setColumn("C1_Mean_Intensity", primaryC1Intensity.getColumnAsVariables("Mean_Intensity"));
					primaryFinalTable.setColumn("C1_IntDen", primaryC1Intensity.getColumnAsVariables("IntDen"));
					primaryFinalTable.setColumn("C2_Mean_Intensity", primaryC2Intensity.getColumnAsVariables("Mean_Intensity"));
					primaryFinalTable.setColumn("C2_IntDen", primaryC2Intensity.getColumnAsVariables("IntDen"));
					primaryFinalTable.setColumn("C3_Mean_Intensity", primaryC3Intensity.getColumnAsVariables("Mean_Intensity"));
					primaryFinalTable.setColumn("C3_IntDen", primaryC3Intensity.getColumnAsVariables("IntDen"));
					primaryFinalTable.setColumn("C4_Mean_Intensity", primaryC4Intensity.getColumnAsVariables("Mean_Intensity"));
					primaryFinalTable.setColumn("C4_IntDen", primaryC4Intensity.getColumnAsVariables("IntDen"));
					
					
					
					//ArrayList<Variable[]> greg = new ArrayList<Variable[]>();
					//greg.add(primaryResults.getColumnAsVariables("Volume")); // Image 0
					//greg.add(primaryResults.getColumnAsVariables("Voxel_Count")); // Image 1
					
					
					
					String primaryFinalName = dir + "Final_Primary_Object_Results.csv";
					try {
						primaryFinalTable.saveAs(primaryFinalName);
					} catch (IOException e) {
						e.printStackTrace();
						IJ.log("Unable to save final primary results table. Check that the objects were created.");
					}
					
					
					
					// Secondary Objects
					
					/* TODO
					 * > Make this conditional: Not all images will contain 4 channels to analyse the intensities of - grab channel array and just run for each element creating a new resultstable each time? 
					 */
					ResultsTable secondaryC1Intensity = IntensityMeasurements.Process(channelsSpheroid[0], secondaryObjectSpheroid);
					ResultsTable secondaryC2Intensity = IntensityMeasurements.Process(channelsSpheroid[1], secondaryObjectSpheroid);
					ResultsTable secondaryC3Intensity = IntensityMeasurements.Process(channelsSpheroid[2], secondaryObjectSpheroid);
					ResultsTable secondaryC4Intensity = IntensityMeasurements.Process(channelsSpheroid[3], secondaryObjectSpheroid);
					
					
					
					// get counter
					int secondaryTableLength = secondaryC1Intensity.getColumnAsVariables("Label").length;
					ResultsTable secondaryImageData = new ResultsTable();
			
					// check to see if grouping info has been entered, if yes, then populate the imageData table, if not, ignore. 
					if (SpheroidView.groupingInfo.isEmpty()) {
							for (int o = 0; o < secondaryTableLength ; o++) {
								secondaryImageData.addRow();
								secondaryImageData.addValue("ImageID", imgName);
							}
					} else {
						String group = SpheroidView.groupingInfo;
						for (int o = 0; o < secondaryTableLength ; o++) {
							secondaryImageData.addRow();
							secondaryImageData.addValue("ImageID", imgName);
							secondaryImageData.addValue("Group", group);
						}
					}
					
					
					// intensity measurements for core and periphery 
					ResultsTable coreC1Intensity = IntensityMeasurements.Process(channelsSpheroid[0], innerROI);
					ResultsTable coreC2Intensity = IntensityMeasurements.Process(channelsSpheroid[1], innerROI);
					ResultsTable coreC3Intensity = IntensityMeasurements.Process(channelsSpheroid[2], innerROI);
					ResultsTable coreC4Intensity = IntensityMeasurements.Process(channelsSpheroid[3], innerROI);
					
					ResultsTable peripheryC1Intensity = IntensityMeasurements.Process(channelsSpheroid[0], outerROI);
					ResultsTable peripheryC2Intensity = IntensityMeasurements.Process(channelsSpheroid[1], outerROI);
					ResultsTable peripheryC3Intensity = IntensityMeasurements.Process(channelsSpheroid[2], outerROI);
					ResultsTable peripheryC4Intensity = IntensityMeasurements.Process(channelsSpheroid[3], outerROI);
					
					
					
					
					
					// Build the final secondary results table
					ResultsTable secondaryFinalTable = new ResultsTable();
					secondaryFinalTable.setColumn("Label", secondaryC1Intensity.getColumnAsVariables("Label"));
					secondaryFinalTable.setColumn("ImageID", secondaryImageData.getColumnAsVariables("ImageID"));
					if (!SpheroidView.groupingInfo.isEmpty()) {
						secondaryFinalTable.setColumn("Group", secondaryImageData.getColumnAsVariables("Group"));
					}
					secondaryFinalTable.setColumn("Volume", secondaryResults.getColumnAsVariables("Volume"));
					secondaryFinalTable.setColumn("Voxel_Count", secondaryResults.getColumnAsVariables("VoxelCount"));
					secondaryFinalTable.setColumn("Sphericity", secondaryResults.getColumnAsVariables("Sphericity"));
					secondaryFinalTable.setColumn("Elongation", secondaryResults.getColumnAsVariables("Elli.R1/R3"));
					secondaryFinalTable.setColumn("Whole_C1_Mean_Intensity", secondaryC1Intensity.getColumnAsVariables("Mean_Intensity"));
					secondaryFinalTable.setColumn("Whole_C1_IntDen", secondaryC1Intensity.getColumnAsVariables("IntDen"));
					secondaryFinalTable.setColumn("Whole_C2_Mean_Intensity", secondaryC2Intensity.getColumnAsVariables("Mean_Intensity"));
					secondaryFinalTable.setColumn("Whole_C2_IntDen", secondaryC2Intensity.getColumnAsVariables("IntDen"));
					secondaryFinalTable.setColumn("Whole_C3_Mean_Intensity", secondaryC3Intensity.getColumnAsVariables("Mean_Intensity"));
					secondaryFinalTable.setColumn("Whole_C3_IntDen", secondaryC3Intensity.getColumnAsVariables("IntDen"));
					secondaryFinalTable.setColumn("Whole_C4_Mean_Intensity", secondaryC4Intensity.getColumnAsVariables("Mean_Intensity"));
					secondaryFinalTable.setColumn("Whole_C4_IntDen", secondaryC4Intensity.getColumnAsVariables("IntDen"));
					
					secondaryFinalTable.setColumn("Core_C1_Mean_Intensity", coreC1Intensity.getColumnAsVariables("Mean_Intensity"));
					secondaryFinalTable.setColumn("Core_C1_IntDen", coreC1Intensity.getColumnAsVariables("IntDen"));
					secondaryFinalTable.setColumn("Periphery_C1_Mean_Intensity", peripheryC1Intensity.getColumnAsVariables("Mean_Intensity"));
					secondaryFinalTable.setColumn("Periphery_C1_IntDen", peripheryC1Intensity.getColumnAsVariables("IntDen"));
					
					secondaryFinalTable.setColumn("Core_C2_Mean_Intensity", coreC2Intensity.getColumnAsVariables("Mean_Intensity"));
					secondaryFinalTable.setColumn("Core_C2_IntDen", coreC2Intensity.getColumnAsVariables("IntDen"));
					secondaryFinalTable.setColumn("Periphery_C2_Mean_Intensity", peripheryC2Intensity.getColumnAsVariables("Mean_Intensity"));
					secondaryFinalTable.setColumn("Periphery_C2_IntDen", peripheryC2Intensity.getColumnAsVariables("IntDen"));
					
					secondaryFinalTable.setColumn("Core_C3_Mean_Intensity", coreC3Intensity.getColumnAsVariables("Mean_Intensity"));
					secondaryFinalTable.setColumn("Core_C3_IntDen", coreC3Intensity.getColumnAsVariables("IntDen"));
					secondaryFinalTable.setColumn("Periphery_C3_Mean_Intensity", peripheryC3Intensity.getColumnAsVariables("Mean_Intensity"));
					secondaryFinalTable.setColumn("Periphery_C3_IntDen", peripheryC3Intensity.getColumnAsVariables("IntDen"));
				
					secondaryFinalTable.setColumn("Core_C4_Mean_Intensity", coreC4Intensity.getColumnAsVariables("Mean_Intensity"));
					secondaryFinalTable.setColumn("Core_C4_IntDen", coreC4Intensity.getColumnAsVariables("IntDen"));
					secondaryFinalTable.setColumn("Periphery_C4_Mean_Intensity", peripheryC4Intensity.getColumnAsVariables("Mean_Intensity"));
					secondaryFinalTable.setColumn("Periphery_C4_IntDen", peripheryC4Intensity.getColumnAsVariables("IntDen"));
					
					

					String secondaryFinalName = dir + "Final_Secondary_Object_Results.csv";
					try {
						secondaryFinalTable.saveAs(secondaryFinalName);
					} catch (IOException e) {
						e.printStackTrace();
						IJ.log("Unable to save final primary results table. Check that the objects were created.");
					}
					
					
					IJ.log("---------------------------------------------");
					IJ.log(list.length + " Images Processed");
					IJ.log("Processing Finished!");
					IJ.log("---------------------------------------------");
					
				}
			}
		});
		t1.start();
	}

	/*
	 * Take the selected channel and process as the primary object
	 */
	
	public static ImagePlus GPUSpheroidPrimaryObject(int primaryChannelChoice) {

		// ready clij2
		CLIJ2 clij2 = CLIJ2.getInstance();
		clij2.clear();
		CLIJx clijx = CLIJx.getInstance();
		clijx.clear();

		// CLIJ argument structure = (input image, ClearCLBuffer output image).
		// Push image
		ClearCLBuffer input = clij2.push(channelsSpheroid[primaryChannelChoice]);
		ClearCLBuffer blurred = clij2.create(input);
		ClearCLBuffer inverted = clij2.create(input);
		ClearCLBuffer threshold = clij2.create(input);
		ClearCLBuffer detectedMax = clij2.create(input);
		ClearCLBuffer labelledSpots = clij2.create(input);
		ClearCLBuffer segmented = clij2.create(input);

		/*
		 * Could implement a clij2.filter(GB) with user input values for background
		 * subtract radius. Then feed to clij2.subtractImages(input, background,
		 * backgroundSubtracted). Instead of running the background subtract outside of
		 * the GPU.
		 */

		// 3D blur
		clij2.gaussianBlur3D(input, blurred, SpheroidView.sigma_x, SpheroidView.sigma_y, SpheroidView.sigma_z);

		// invert
		clij2.invert(blurred, inverted);

		// threshold
		clij2.thresholdOtsu(blurred, threshold);

		// detect maxima
		clij2.detectMaxima3DBox(blurred, detectedMax, SpheroidView.radius_x, SpheroidView.radius_y,
				SpheroidView.radius_z);

		// label spots
		clij2.labelSpots(detectedMax, labelledSpots);

		
		 // marker controlled watershed
		 MorphoLibJMarkerControlledWatershed.morphoLibJMarkerControlledWatershed(
		 clij2, inverted, labelledSpots, threshold, segmented);
		 
		  
		  /* 
		 * // from AbstractMorphoLibJAnalyzeRegions3D xxx ImagePlus
		 * labels_imp = clij2.pull(labels);
		 * 
		 * ResultsTable table = new AnalyzeRegions3D().process(labels_imp);
		 * 
		 * ClearCLBuffer vector = clij2.create(table.getCounter(), 1, 1);
		 * 
		 * clij2.pushResultsTableColumn(vector, table, property);
		 * 
		 * ClearCLBuffer vector_with_background = clij2.create(table.getCounter() + 1,
		 * 1, 1); clij2.setColumn(vector_with_background, 0, 0); clij2.paste(vector,
		 * vector_with_background, 1, 0, 0);
		 * 
		 * clij2.replaceIntensities(labels, vector_with_background, parametric_map);
		 * 
		 * // clean up vector.close(); vector_with_background.close();
		 */ 
		 primaryObjectSpheroid = clij2.pull(segmented);
		 
		// primaryObjects.show();
		input.close();
		blurred.close();
		inverted.close();
		threshold.close();
		detectedMax.close();
		labelledSpots.close();
		segmented.close();
		
		return primaryObjectSpheroid;

	}

	/*
	 * Take the selected channel and process as the secondary object
	 */
	
	public static ImagePlus GPUSpheroidSecondaryObject(int secondaryChannelChoice) {

		// ready clij2
		CLIJ2 clij2 = CLIJ2.getInstance();
		clij2.clear();
		CLIJx clijx = CLIJx.getInstance();
		clijx.clear();

		// create images
		ClearCLBuffer input = clij2.push(channelsSpheroid[secondaryChannelChoice]);
		ClearCLBuffer blurred = clij2.create(input);
		ClearCLBuffer threshold = clij2.create(input);
		ClearCLBuffer fillHoles = clij2.create(input);

		// blur
		clij2.gaussianBlur3D(input, blurred, SpheroidView.sigma_x2, SpheroidView.sigma_x2, SpheroidView.sigma_z2);

		// greater constant
		clij2.greaterConstant(blurred, threshold, SpheroidView.greaterConstant);

		// fill holes
		clij2.binaryFillHoles(threshold, fillHoles);

		// pull
		secondaryObjectSpheroid = clij2.pull(fillHoles);

		input.close();
		blurred.close();
		threshold.close();
		fillHoles.close();

		return secondaryObjectSpheroid;
	}
}
