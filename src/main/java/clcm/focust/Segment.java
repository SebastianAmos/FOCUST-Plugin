package clcm.focust;

import ij.IJ;
import ij.ImagePlus;
import ij.macro.Variable;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import ij.plugin.ChannelSplitter;
import ij.plugin.ImageCalculator;
import inra.ijpb.plugins.AnalyzeRegions3D;
import inra.ijpb.plugins.IntensityMeasures3D;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clijx.CLIJx;
import net.haesleinhuepf.clijx.morpholibj.MorphoLibJMarkerControlledWatershed;
import net.haesleinhuepf.clijx.morpholibj.AbstractMorphoLibJAnalyzeRegions3D;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
	private static IntensityMeasures3D intensity3D = new IntensityMeasures3D();
	//private static IntensityMeasures primaryC2Intensity = new IntensityMeasures(primaryObjectSpheroid, channelsSpheroid[2]);
	

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

	/*
	 * TODO List Create a file loop to grab the list[i] of files in the inputDir
	 * folder. assign the first index of the list to the object img. Establish the
	 * appropriate variables (channel names, dir names). run a background subtract
	 * on the img. Split the channels --> push channel 1 (imgC1) to CLIJ2_ run
	 * segmentation commands with user defined variable inputs. save the outputs.
	 * run analysis with morpholibJ commands. hold results table objects open. run
	 * macro code to construct the results table in the required format. push back
	 * as outputTable object. save as .csv
	 */

	

	
	/* ------------------------------------------------------------------------------------
	 * This method segments primary and secondary objects based on user-defined parameters.
	 * Objects are then used for region-restricted intensity analysis and 3D measurements. 
	 * -----------------------------------------------------------------------------------*/
	public static void ProcessSpheroid() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {

				// Grab the file names.
				File f = new File(SpheroidView.inputDir);
				String[] list = f.list();
				String dir = SpheroidView.inputDir;
				int count = 0; 
				// Iterate through each image in the directory and segment the selected primary and secondary channels.
				for (int i = 0; i < list.length; i++) {
					count++;
					String path = SpheroidView.inputDir + list[i];
					IJ.log("Processing image " + count + " of " + list.length);
					IJ.log("Current image name: " + list[i]);
					ImagePlus imp = IJ.openImage(path);
					Calibration cal = imp.getCalibration();
					
					

					channelsSpheroid = ChannelSplitter.split(imp);
					int primaryChannelChoice = SpheroidView.primaryChannelChoice;
					int secondaryChannelChoice = SpheroidView.secondaryChannelChoice;
					String imgName = imp.getTitle();
					
					/*
					 * create and measure primary objects
					 */
					GPUSpheroidPrimaryObject(primaryChannelChoice);
					primaryObjectSpheroid.setCalibration(cal);
					IJ.saveAs(primaryObjectSpheroid, "TIF", dir + "primaryObjects_" + imgName);
					ResultsTable primaryResults = analyze3D.process(primaryObjectSpheroid);
					String pRName = dir + "Primary_Results.csv";
					try {
						primaryResults.saveAs(pRName);
					} catch (IOException e) {
						e.printStackTrace();
						IJ.log("Unable to save primary results table.");
					}
					
					
					
					/*
					 * create and measure secondary object
					 */
					
					/* TODO
					 *  > Take the whole secondary object and transform into core vs periphery regions for intensity analysis.
					 *  > This was done with image calculation and a fixed number of erosion iterations in the IJ scripts. 
					 *  --> consider developing a more programmatic approach i.e. shrinking the object by 50 % to its x, y, z centroid?   
					 */
					
					
					IJ.log("Commencing Secondary Object...");
					GPUSpheroidSecondaryObject(secondaryChannelChoice);
					secondaryObjectSpheroid.setCalibration(cal);
					IJ.resetMinAndMax(secondaryObjectSpheroid);
					IJ.saveAs(secondaryObjectSpheroid, "TIF", dir + "SecondaryObjects_" + imgName);
					ResultsTable secondaryResults = analyze3D.process(secondaryObjectSpheroid);
					String sRName = dir + "Secondary_Results.csv";
					
					try {
						secondaryResults.saveAs(sRName);
					} catch (IOException e) {
						e.printStackTrace();
						IJ.log("Unable to save secondary results table.");
					}
					
					
					// Create inner ROI
					// --> Duplicate whole spheroid, erode by fixed iteration 
					ImagePlus innerROI = secondaryObjectSpheroid.duplicate();
					IJ.run(innerROI, "Make Binary", "method=Default background=Dark black");
					IJ.run(innerROI, "Options...", "iterations=70 count=1 black do=Erode stack");
					IJ.saveAs(innerROI, "TIF", dir + "Inner_Secondary_" + imgName);
					
					// Create outer ROI
					// --> duplicate whole spheroid, run 8-bit, 
					ImagePlus outerROI = ImageCalculator.run(secondaryObjectSpheroid, innerROI, "Subtract create stack");
					IJ.saveAs(outerROI, "TIF", dir + "Outer_Secondary_" + imgName);
					
					
					
					
					
					
					
					/* 
					 * Intensity measurements and building results tables 
					 */
					
					/*
					 * TODO
					 * > Write grouping variable into each row as its own column before save. 
					 * > Write channel names into header for column respectively  
					 */
					
					
					// Primary Objects
					ResultsTable primaryC1Intensity = IntensityMeasurements.Process(channelsSpheroid[0], primaryObjectSpheroid);
					ResultsTable primaryC2Intensity = IntensityMeasurements.Process(channelsSpheroid[1], primaryObjectSpheroid);
					ResultsTable primaryC3Intensity = IntensityMeasurements.Process(channelsSpheroid[2], primaryObjectSpheroid);
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

					String secondaryFinalName = dir + "Final_Secondary_Object_Results.csv";
					try {
						secondaryFinalTable.saveAs(secondaryFinalName);
					} catch (IOException e) {
						e.printStackTrace();
						IJ.log("Unable to save final primary results table. Check that the objects were created.");
					}
					
					
					
					IJ.log(list.length + " Images Processed");
					IJ.log("Processing Finished!");
					
					
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
		 * // _Borrowed_ from AbstractMorphoLibJAnalyzeRegions3D xxx ImagePlus
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
