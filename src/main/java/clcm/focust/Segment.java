package clcm.focust;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

/**
 * A class for segmenting fluorescent datasets based on user-defined parameters. 
 * Three segmentation workflows currently available (processSpheroids, processSingleCells and processSpeckles.
 * @author SebastianAmos
 *
 */

public class Segment {

	public static ImagePlus[] channelsSpheroid;
	public static ImagePlus[] channelsSingleCell;
	public static ImagePlus[] channelsSpeckle;
	public static ImagePlus primaryObjectSpheroid;
	public static ImagePlus secondaryObjectSpheroid;
	private static ImagePlus primaryOriginalObjectsCells;
	private static ImagePlus secondaryObjectsCells;
	private static ImagePlus primaryObjectsSpeckles;
	private static ImagePlus secondaryObjectsSpeckles;
	private static ImagePlus tertiaryObjectsSpeckles;
	private static AnalyzeRegions3D analyze3D = new AnalyzeRegions3D();
	
	
	// Could make these user-input strings to provide greater flexibility 
	private static String primaryPrefix = "Primary_Objects_";
	private static String secondaryPrefix = "Secondary_Objects_";
	private static String tertiaryPrefix = "Tertiary_Objects";
	private static String corePrefix = "Inner_Secondary_";
	private static String outerPrefix = "Outer_Secondary_";
	

	public static void threadLog(final String log) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				IJ.log(log);
			}
		});
	}

	 
	
	/*TODO OVERALL: 
	 * - implement a file extension filter - could be a good way to then distinguish between original vs pre-segmented images later on too...
	 * - make erosion of secondary object relative to it's original size, instead of an arbitrary number of iterations.
	 * - implement intensity measurements in core vs periphery
	 * - make intensity analysis dependent on then number of channels in current image array - not fixed to 4. 
	 * - Make it batch process friendly! End of single image loops marked at the end of the "Process.." methods
	 *
	 *
	 * Implementing a file extension filter may be useful.  
	 */
	// (list[i].endsWith(".tif")||list[i].endsWith(".nd2")||list[i].endsWith("_D3D"));
	// IJ.showProgress(i+1, list.length);

	
	
	
	
	
	public void processSpeckles(boolean analysisOnly) {
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {

				// grab the file names and start a timer
				long startTime = System.currentTimeMillis();
				File f = new File(SpeckleView.inputDir);
				String[] list = f.list();
				String dir = SpeckleView.inputDir;
				int count = 0;
				

				// If analysis-only-mode, create a new list[] containing image names that DO NOT match the prefix expectations i.e. are the original images, not the segmented outputs.
					if(analysisOnly) {
						ArrayList<String> tempList = new ArrayList<>();
						for (String imgName : list) {
							if (!imgName.startsWith(primaryPrefix) && !imgName.startsWith(secondaryPrefix) && !imgName.startsWith(corePrefix) && !imgName.startsWith(outerPrefix) && !imgName.startsWith(tertiaryPrefix)) {
								tempList.add(imgName);
							}
						}
							list = new String[tempList.size()];
							list = tempList.toArray(list);
					}
					
					// iterate through each image in the list
					for (int i = 0; i < list.length; i++) {
						count++;
						String path = SpeckleView.inputDir + list[i];
						IJ.log("-------------------------------------------------");
						IJ.log("----------FOCUST: Speckle Protocol-----------");
						IJ.log("Processing image " + count + " of " + list.length);
						IJ.log("Current image name: " + list[i]);
						IJ.log("-------------------------------------------------");
						ImagePlus imp = IJ.openImage(path);
						int numberOfChannels = imp.getNChannels();
						
						// TEST WITHOUT CONVERTING TO 8-BIT!
						IJ.run(imp, "8-bit", "");
						
						Calibration cal = imp.getCalibration();
						channelsSpeckle = ChannelSplitter.split(imp);
						int primaryChannelChoice = SpeckleView.primaryChannelChoice;
						int secondaryChannelChoice = SpeckleView.secondaryChannelChoice;
						int tertiaryChannelChoice = SpeckleView.tertiaryChannelChoice;
						
						String imgName = imp.getTitle();
						
						
						// if analysisMode is T, find the correct primary object file for the current image
						// MAKE THIS WORK BY DETECTING THE IMAGE EXTENSION!!!! 
						// NOT ALL DATA WILL BE .nd2 or .dv
						if(analysisOnly) {
							IJ.log("Analysis Only Mode Active: Finding Images...");
							String fileName = list[i].replace(".dv", ".tif");
							primaryObjectsSpeckles = IJ.openImage(SpeckleView.inputDir + primaryPrefix + fileName);
							secondaryObjectsSpeckles = IJ.openImage(SpeckleView.inputDir + secondaryPrefix + fileName);
							tertiaryObjectsSpeckles = IJ.openImage(SpeckleView.inputDir + tertiaryPrefix + fileName);
							
						} else {
							// if analysis mode is F, segment objects based on channel preferences
							IJ.log("Analysis Only Mode Not Active: Running Segmentation");
							primaryObjectsSpeckles = gpuSegmentOtsu(channelsSpeckle[primaryChannelChoice], SpeckleView.sigma_x, SpeckleView.sigma_y, SpeckleView.sigma_z, SpeckleView.radius_x, SpeckleView.radius_y, SpeckleView.radius_z);
							secondaryObjectsSpeckles = gpuSegmentGreaterConstant(channelsSpeckle[secondaryChannelChoice] , SpeckleView.sigma_x2, SpeckleView.sigma_y2, SpeckleView.sigma_z2, SpeckleView.greaterConstantSecondary, SpeckleView.radius_x2, SpeckleView.radius_y2, SpeckleView.radius_z2);
							// make tertiary processing conditional
							tertiaryObjectsSpeckles = gpuSegmentGreaterConstant(channelsSpeckle[tertiaryChannelChoice], SpeckleView.sigma_x3, SpeckleView.sigma_y3, SpeckleView.sigma_z3, SpeckleView.greaterConstantTertiary, SpeckleView.radius_x3, SpeckleView.radius_y3, SpeckleView.radius_z3);
						}
						
						// Calibrate segmented outputs
						IJ.resetMinAndMax(primaryObjectsSpeckles);
						primaryObjectsSpeckles.setCalibration(cal);
						
						IJ.resetMinAndMax(secondaryObjectsSpeckles);
						secondaryObjectsSpeckles.setCalibration(cal);
						
						IJ.resetMinAndMax(tertiaryObjectsSpeckles);
						tertiaryObjectsSpeckles.setCalibration(cal);
					
					
						if (!analysisOnly) {
							IJ.saveAs(primaryObjectsSpeckles, "TIF", dir + "Primary_Objects_" + imgName);
							IJ.saveAs(secondaryObjectsSpeckles, "TIF", dir + "Secondary_Objects_" + imgName);
							IJ.saveAs(tertiaryObjectsSpeckles, "TIF", dir + "Tertiary_Objects_" + imgName);
						}
						
						IJ.log("Running Volumetric Analysis...");
						
						// make tertiary conditional as not all images will require 3 channel processing
						ResultsTable primaryResults = analyze3D.process(primaryObjectsSpeckles);
						ResultsTable secondaryResults = analyze3D.process(secondaryObjectsSpeckles);
						ResultsTable tertiaryResults = analyze3D.process(tertiaryObjectsSpeckles);
						
						IJ.log("Running Intensity Analysis...");
						
						
						// Make an array of all segmented objects --> MAKE CONDITIONAL ON HOW MANY EXIST! maybe an arraylist?
						ImagePlus[] objectImages = {primaryObjectsSpeckles, secondaryObjectsSpeckles, tertiaryObjectsSpeckles}; 
						
						
						// a map to store the intensity results for each segmented image
						Map<ImagePlus, ResultsTable> tables = new HashMap<>();
						
						// Measure the intensity of each channel, within each segmented object.
						for (int j = 0; j < objectImages.length; j++) {
							
							ResultsTable result = new ResultsTable();
							
							for (int k = 0; k < channelsSpeckle.length; k++) {
								
								ResultsTable temp = IntensityMeasurements.process(channelsSpeckle[k], objectImages[j]);
								result.setColumn("Label", temp.getColumnAsVariables("Label"));
								result.setColumn(("C" + (k + 1) + "_Mean_Intensity").toString(), temp.getColumnAsVariables("Mean_Intensity"));
								result.setColumn(("C" + (k + 1) + "_IntDen").toString(), temp.getColumnAsVariables("IntDen"));
								
							}
							
							tables.put(objectImages[j], result);
							
						}
						
						// append imageID and grouping info
						String group = SpeckleView.groupingInfo;
						
						for (ResultsTable rt : tables.values()) {
							
							if (SpeckleView.groupingInfo.isEmpty()) {
								
								for (int j = 0; j < rt.size(); j++) {
									rt.addRow();
									rt.addValue("ImageID", imgName);
								}
								
							} else {
								
								for (int j = 0; j < rt.size(); j++) {
									rt.addRow();
									rt.addValue("ImageID", imgName);
									rt.addValue("Group", group);
								}
								
							}
						}
						
						
						for (Map.Entry<ImagePlus, ResultsTable> entry : tables.entrySet()) {
							
							ImagePlus key = entry.getKey();
							ResultsTable val = entry.getValue();
							
							if (key.equals(primaryObjectsSpeckles)) {
								IntensityMeasurements.saveTable(val, dir, "Primary_Objects_Ints.csv");
							} 
							
							if (key.equals(secondaryObjectsSpeckles)) {
								IntensityMeasurements.saveTable(val, dir, "Secondary_Objects_Ints.csv");
							}
							
							if (key.equals(tertiaryObjectsSpeckles)) {
								IntensityMeasurements.saveTable(val, dir, "Tertiary_Objects_Ints.csv");
							}
						}
						
						
						
						
					} // end of single image loop!!
					
					
					
					long endTime = System.currentTimeMillis();
					double timeSec = (endTime - startTime) / 1000;
					IJ.log("It took " + timeSec + " seconds to process " + list.length + " images.");
					IJ.log("Speckle Batch Protocol Complete!");
					IJ.getLog();
					IJ.saveString(IJ.getLog(), dir + "Log.txt");
				
			}
		});
	t1.start();
	}
	
	
	
	
	
	
	
	
	/**
	 * TODO:
	 * - implement kill borders function for secondary and primary objects (mask primary by secondary for instances where secondary objects are removed, so that child objects are as well).
	 * - Multiple primary object support per secondary object.
	 * - 
	 */
	
	
	/**
	 * This method segments primary and secondary objects from "single cell" datasets based on user-defined parameters.
	 * Primary (nuclei) and secondary (cells) objects are used to create tertiary (cytoplasmic) labels. 
	 * Primary, secondary and tertiary metrics are then related so each row in the final results table all pertain to the same biological entity. 
	 * Support for multiple primary objects per secondary object coming...
	 * 
	 * @param analysisOnly Boolean from gui checkbox to determine if segmentation has already been done. 
	 */
	
	public void processSingleCells(boolean analysisOnly) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {

				// grab the file names and start a timer
				long startTime = System.currentTimeMillis();
				File f = new File(SingleCellView.inputDir);
				String[] list = f.list();
				String dir = SingleCellView.inputDir;
				int count = 0;
				
				// Create the combined table outside of the image loop
				ResultsTable combinedResults = new ResultsTable();
					

				// If analysis-only-mode, create a new list[] containing image names that DO NOT match the prefix expectations i.e. are the original images, not the segmented outputs.
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
					
					// iterate through each image in the list
					for (int i = 0; i < list.length; i++) {
						count++;
						String path = SingleCellView.inputDir + list[i];
						IJ.log("---------------------------------------------");
						IJ.log("--------FOCUST: Single Cell Protocol---------");
						IJ.log("Processing image " + count + " of " + list.length);
						IJ.log("Current image name: " + list[i]);
						IJ.log("---------------------------------------------");
						ImagePlus imp = IJ.openImage(path);
						int numberOfChannels = imp.getNChannels();
						
						// TEST WITHOUT CONVERTING TO 8-BIT!!
						IJ.run(imp, "8-bit", "");
						
						Calibration cal = imp.getCalibration();
						channelsSingleCell = ChannelSplitter.split(imp);
						int primaryChannelChoice = SingleCellView.primaryChannelChoice;
						int secondaryChannelChoice = SingleCellView.secondaryChannelChoice;
						String imgName = imp.getTitle();
					
						IJ.log("Processing Primary Object...");
						// if analysisMode is T, find the correct primary object file for the current image
						if(analysisOnly) {
							String fileName = list[i].replace(".nd2", ".tif");
							primaryOriginalObjectsCells = IJ.openImage(SingleCellView.inputDir + primaryPrefix + fileName);
							/*if (primaryOriginalObjectsCells.getStackSize() != imp.getStackSize()) {
								IJ.error("Error", "The raw image and primary object stack sizes do not match.");
							}*/
						} else {
							// if analysis mode is F, segment primary channel based on user inputs
							primaryOriginalObjectsCells = gpuSegmentGreaterConstant(channelsSingleCell[primaryChannelChoice], SingleCellView.sigma_x, SingleCellView.sigma_y, SingleCellView.sigma_z, SingleCellView.greaterConstantPrimary, SingleCellView.radius_x, SingleCellView.radius_y, SingleCellView.radius_z);
						}
						
						IJ.resetMinAndMax(primaryOriginalObjectsCells);
						primaryOriginalObjectsCells.setCalibration(cal);
						
						
						// TESTING!!
						//IJ.saveAs(primaryOriginalObjectsCells, "TIF", dir + "Primary_Original_Objects_" + imgName);
						// for some reason saving as a binary output, not a connected component label map.
						////
						
						
						// create and measure secondary object
						IJ.log("Processing Secondary Object...");
						
						// If analysis-only-mode, find the right secondary object file for the current image.
						if(analysisOnly) {
							String fileName = list[i].replace(".nd2", ".tif");
							secondaryObjectsCells = IJ.openImage(SingleCellView.inputDir + secondaryPrefix + fileName);
							/*if (secondaryObjectsCells.getStackSize() != imp.getStackSize()) {
								IJ.error("Error", "The raw image and secondary object stack sizes do not match.");
							}*/
						} else {
							secondaryObjectsCells = gpuSegmentGreaterConstant(channelsSingleCell[secondaryChannelChoice], SingleCellView.sigma_x2, SingleCellView.sigma_y2, SingleCellView.sigma_z2, SingleCellView.greaterConstantSecondary, SingleCellView.radius_x2, SingleCellView.radius_y2, SingleCellView.radius_z2);
						}
						
						IJ.resetMinAndMax(secondaryObjectsCells);
						secondaryObjectsCells.setCalibration(cal);
						
						
						
						// TESTING!!
						//IJ.saveAs(secondaryObjectsCells, "TIF", dir + "Secondary_Original_Objects_" + imgName);
						
						
						
						// Give primary objects the same label ID as the secondary objects 
						// Convert to binary, get max range value (maxLUT), divide label IDs by maxLUT so ID = 1. 
						
						//ImagePlus primaryLabelMatch = primaryOriginalObjectsCells.duplicate();
						//IJ.run(primaryLabelMatch, "Make Binary", "method=Huang background=Dark black");
						
						IJ.log("Converting primary labels to binary");
						ImagePlus primaryLabelMatch = LabelEditor.makeBinary(primaryOriginalObjectsCells);
						IJ.log("the display max is: " + primaryLabelMatch.getDisplayRangeMax());
						IJ.run(primaryLabelMatch, "Divide...", "value=" + primaryLabelMatch.getDisplayRangeMax() + " stack");
						//IJ.saveAs(primaryLabelMatch, "TIF", dir + "objectvaluesshouldbe1_" + imgName);
						
						
						// Assign secondary labels to primary objects
						ImagePlus primaryObjectsCells = ImageCalculator.run(secondaryObjectsCells, primaryLabelMatch, "Multiply create stack");
						//IJ.saveAs(primaryObjectsCells, "TIF", dir + "objectvaluesshouldbesecondary!_" + imgName);
						
						
						// Generate a tertiary (cytoplasmic) ROI (subtract primary from secondary objects)
						IJ.log("Processing Tertiary Object...");
						ImagePlus tertiaryObjectsCells = ImageCalculator.run(secondaryObjectsCells, primaryObjectsCells, "Subtract create stack");
						
						// set calibration and save the ROI if the - assuming analysis only mode provide primary and secondary objects only!
						tertiaryObjectsCells.setCalibration(cal);
						IJ.resetMinAndMax(tertiaryObjectsCells);
						
						
						// TESTING
						// ***********************************************************************************************
						
						/*
						 * REMAP THE LABELS for the primary objects!!!!!!
						 * - Where two primary objects share a label acquired from a secondary object, but were previously uniquely identified, append an index to the shared label values to induvidualise them again.
						 * - Make this an option (maybe another check box?) 
						 */
						
						/* - findAndRename returns a rewritten original relative to matched**
						*  - manageDuplicates returns a rewritten matched relative to original **
						*
						*  - primaryObjectsCell contains the labelling from the secondary objects. Some primary objects may share the same label.
						*  - primaryOriginalObjectsCells contains the original labels that uniquely identify each primary object.
						
						*/
						
						IJ.log("TESTING LABEL EDITOR FUNCTIONS");
						// pixel scanning approach - matched vs matched (relative to matched)
						// THE OUTPUT IS ALL BINARY (ALL LABELS = 255).
						ImagePlus matchedLabs = primaryObjectsCells.duplicate();
						ImagePlus originalLabs = primaryOriginalObjectsCells.duplicate();
						ImagePlus matched = primaryObjectsCells.duplicate();
						ImagePlus findAndRenameOutput = LabelEditor.findAndRename(matched, originalLabs);
						ImagePlus relabelled = LabelEditor.reLabel(matchedLabs);
						IJ.saveAs(relabelled, "TIF", dir + "Relablled_" + imgName);
						IJ.saveAs(findAndRenameOutput, "TIF", dir + "findAndRename_" + imgName);
						ImagePlus labs = primaryObjectsCells.duplicate();
						ImagePlus newLabs = LabelEditor.labelIndexAppender(labs);
						IJ.saveAs(newLabs, "TIF", dir + "appended_" + imgName);
						
						/*
						// THE OUTPUT IS MOSTLY BINARY BUT ALL HIGH-Z-VALUE SMALL SPECKS ARE MULTI-LABELLED!
						ImagePlus originalLabs = primaryOriginalObjectsCells.duplicate();
						ImagePlus matchedLabs1 = primaryObjectsCells.duplicate();
						ImagePlus relabelled1 = LabelEditor.manageDuplicates(matchedLabs1, originalLabs);
						IJ.saveAs(relabelled1, "TIF", dir + "MD_" + imgName);
						*/
						
					/*
					 * IJ.log("First test passed"); // matched vs matched ImagePlus relablledsame =
					 * LabelEditor.findAndRename(primaryObjectsCells, primaryObjectsCells);
					 * IJ.saveAs(relablledsame, "TIF", dir + "Relabelled_ matched_matched" +
					 * imgName);
					 * 
					 * IJ.log("Second test passed"); // matched vs original with assessment order
					 * swapped (relative to original) ImagePlus duplicates =
					 * LabelEditor.manageDuplicates(primaryObjectsCells,
					 * primaryOriginalObjectsCells); IJ.saveAs(duplicates, "TIF", dir +
					 * "Duplicates_matched_original" + imgName); IJ.log("Third test passed");
					 * 
					 * // matched vs matched with assessment order swapped (relative to original)
					 * ImagePlus duplicatessame = LabelEditor.manageDuplicates(primaryObjectsCells,
					 * primaryObjectsCells); IJ.saveAs(duplicatessame, "TIF", dir +
					 * "Duplicates_matched_mathed" + imgName); IJ.log("Forth test passed");
					 *
					 *	
					 *	IJ.log("-----------------------------------");
					 *	IJ.log("All label editor tests executed.");
					 *	IJ.log("-----------------------------------");
					 *
					 */
						
						
						
						
						// If analysis mode is false, save the segmented outputs for the primary and secondary object channels, then the tertiary object output.
						if(!analysisOnly) {
							IJ.saveAs(primaryObjectsCells, "TIF", dir + "Primary_Objects_" + imgName);
							IJ.saveAs(secondaryObjectsCells, "TIF", dir + "Secondary_Objects_" + imgName);
						}
						IJ.saveAs(tertiaryObjectsCells, "TIF", dir + "Tertiary_Objects_" + imgName);
						
						
					// Measure all of the segmented outputs and save results to separate tables. 
						ResultsTable primaryResults = analyze3D.process(primaryObjectsCells);
						ResultsTable secondaryResults = analyze3D.process(secondaryObjectsCells);
						ResultsTable tertiaryResults = analyze3D.process(tertiaryObjectsCells);
						
						
					/*
					 * Build and save the final table for primary objects. 
					*/
						
						// Measure the channel intensities. SCC1 = single cell channel 1.
						// Intensity analysis is dependent on the total number of channels in the image.
						ResultsTable primarySCC1Intensity = IntensityMeasurements.process(channelsSingleCell[0], primaryObjectsCells);
						ResultsTable primarySCC2Intensity = null;
						ResultsTable primarySCC3Intensity = null;
						ResultsTable primarySCC4Intensity = null;
						
						if (numberOfChannels >=2) {
						primarySCC2Intensity = IntensityMeasurements.process(channelsSingleCell[1], primaryObjectsCells);
						}
						if (numberOfChannels >= 3) {
						primarySCC3Intensity = IntensityMeasurements.process(channelsSingleCell[2], primaryObjectsCells);
						}
						if (numberOfChannels >= 4) {
						primarySCC4Intensity = IntensityMeasurements.process(channelsSingleCell[3], primaryObjectsCells);
						}
						
						
						// new results table - SC = single cell
						ResultsTable primaryImageDataSC = new ResultsTable();
						
						// check to see if grouping info has been entered, if yes, then populate the imageData table, if not, ignore. 
						if (SingleCellView.groupingInfo.isEmpty()) {
								for (int o = 0; o < primarySCC1Intensity.size() ; o++) {
									primaryImageDataSC.addRow();
									primaryImageDataSC.addValue("ImageID", imgName);
								}
						} else {
							String group = SingleCellView.groupingInfo;
							for (int o = 0; o < primarySCC1Intensity.size() ; o++) {
								primaryImageDataSC.addRow();
								primaryImageDataSC.addValue("ImageID", imgName);
								primaryImageDataSC.addValue("Group", group);
							}
						}
						
						
						ResultsTable primaryFinalTable = new ResultsTable();
						primaryFinalTable.setColumn("Label", primarySCC1Intensity.getColumnAsVariables("Label"));
						primaryFinalTable.setColumn("ImageID", primaryImageDataSC.getColumnAsVariables("ImageID"));
						if (!SingleCellView.groupingInfo.isEmpty()) {
							primaryFinalTable.setColumn("Group", primaryImageDataSC.getColumnAsVariables("Group"));
						}
						primaryFinalTable.setColumn("Volume", primaryResults.getColumnAsVariables("Volume"));
						primaryFinalTable.setColumn("Voxel_Count", primaryResults.getColumnAsVariables("VoxelCount"));
						primaryFinalTable.setColumn("Sphericity", primaryResults.getColumnAsVariables("Sphericity"));
						primaryFinalTable.setColumn("Elongation", primaryResults.getColumnAsVariables("Elli.R1/R3"));
						primaryFinalTable.setColumn("Mean_Breadth", primaryResults.getColumnAsVariables("MeanBreadth"));
						primaryFinalTable.setColumn("Centroid_X", primaryResults.getColumnAsVariables("Centroid.X"));
						primaryFinalTable.setColumn("Centroid_Y", primaryResults.getColumnAsVariables("Centroid.Y"));
						primaryFinalTable.setColumn("Centroid_Z", primaryResults.getColumnAsVariables("Centroid.Z"));
						primaryFinalTable.setColumn("C1_Mean_Intensity", primarySCC1Intensity.getColumnAsVariables("Mean_Intensity"));
						primaryFinalTable.setColumn("C1_IntDen", primarySCC1Intensity.getColumnAsVariables("IntDen"));
						if (numberOfChannels >=2) {
							primaryFinalTable.setColumn("C2_Mean_Intensity", primarySCC2Intensity.getColumnAsVariables("Mean_Intensity"));
							primaryFinalTable.setColumn("C2_IntDen", primarySCC2Intensity.getColumnAsVariables("IntDen"));
						}
						if (numberOfChannels >=3) {
							primaryFinalTable.setColumn("C3_Mean_Intensity", primarySCC3Intensity.getColumnAsVariables("Mean_Intensity"));
							primaryFinalTable.setColumn("C3_IntDen", primarySCC3Intensity.getColumnAsVariables("IntDen"));
						}
						if (numberOfChannels >=4) {
							primaryFinalTable.setColumn("C4_Mean_Intensity", primarySCC4Intensity.getColumnAsVariables("Mean_Intensity"));
							primaryFinalTable.setColumn("C4_IntDen", primarySCC4Intensity.getColumnAsVariables("IntDen"));
						}
						
					
						
						// Save the primary results table 
						String primaryResultsName = dir + "Primary_Object_Results.csv";
						try {
							primaryFinalTable.saveAs(primaryResultsName);
						} catch (IOException e) {
							e.printStackTrace();
							IJ.log("Cannot save primary results table. Check that the objects were created.");
						}
						
						
					/*
					 * Build and save the final table for secondary objects.
					 */
						
						ResultsTable secondarySCC1Intensity = IntensityMeasurements.process(channelsSingleCell[0], secondaryObjectsCells);
						ResultsTable secondarySCC2Intensity = null;
						ResultsTable secondarySCC3Intensity = null;
						ResultsTable secondarySCC4Intensity = null;
						
						if (numberOfChannels >=2) {
						secondarySCC2Intensity = IntensityMeasurements.process(channelsSingleCell[1], secondaryObjectsCells);
						}
						if (numberOfChannels >= 3) {
						secondarySCC3Intensity = IntensityMeasurements.process(channelsSingleCell[2], secondaryObjectsCells);
						}
						if (numberOfChannels >= 4) {
						secondarySCC4Intensity = IntensityMeasurements.process(channelsSingleCell[3], secondaryObjectsCells);
						}
						

						
						// new results table - SC = single cell
						ResultsTable secondaryImageDataSC = new ResultsTable();
						
						// check to see if grouping info has been entered, if yes, then populate the imageData table, if not, ignore. 
						if (SingleCellView.groupingInfo.isEmpty()) {
							
								for (int o = 0; o < secondarySCC1Intensity.size() ; o++) {
									secondaryImageDataSC.addRow();
									secondaryImageDataSC.addValue("ImageID", imgName);
								}
								
						} else {
							
							String group = SingleCellView.groupingInfo;
							
							for (int o = 0; o < secondarySCC1Intensity.size() ; o++) {
								secondaryImageDataSC.addRow();
								secondaryImageDataSC.addValue("ImageID", imgName);
								secondaryImageDataSC.addValue("Group", group);
							}
						}
						
						
						// build the final secondary table 
						ResultsTable secondaryFinalTable = new ResultsTable(); 
						
						secondaryFinalTable.setColumn("Label", secondarySCC1Intensity.getColumnAsVariables("Label"));
						secondaryFinalTable.setColumn("ImageID", secondaryImageDataSC.getColumnAsVariables("ImageID"));
						if (!SingleCellView.groupingInfo.isEmpty()) {
							secondaryFinalTable.setColumn("Group", secondaryImageDataSC.getColumnAsVariables("Group"));
						}
						secondaryFinalTable.setColumn("Volume", secondaryResults.getColumnAsVariables("Volume"));
						secondaryFinalTable.setColumn("Voxel_Count", secondaryResults.getColumnAsVariables("VoxelCount"));
						secondaryFinalTable.setColumn("Sphericity", secondaryResults.getColumnAsVariables("Sphericity"));
						secondaryFinalTable.setColumn("Elongation", secondaryResults.getColumnAsVariables("Elli.R1/R3"));
						secondaryFinalTable.setColumn("Mean_Breadth", secondaryResults.getColumnAsVariables("MeanBreadth"));
						secondaryFinalTable.setColumn("Centroid_X", secondaryResults.getColumnAsVariables("Centroid.X"));
						secondaryFinalTable.setColumn("Centroid_Y", secondaryResults.getColumnAsVariables("Centroid.Y"));
						secondaryFinalTable.setColumn("Centroid_Z", secondaryResults.getColumnAsVariables("Centroid.Z"));
						secondaryFinalTable.setColumn("C1_Mean_Intensity", secondarySCC1Intensity.getColumnAsVariables("Mean_Intensity"));
						secondaryFinalTable.setColumn("C1_IntDen", secondarySCC1Intensity.getColumnAsVariables("IntDen"));
						
						if (numberOfChannels >=2) {
							secondaryFinalTable.setColumn("C2_Mean_Intensity", secondarySCC2Intensity.getColumnAsVariables("Mean_Intensity"));
							secondaryFinalTable.setColumn("C2_IntDen", secondarySCC2Intensity.getColumnAsVariables("IntDen"));
						}
						
						if (numberOfChannels >=3) {
							secondaryFinalTable.setColumn("C3_Mean_Intensity", secondarySCC3Intensity.getColumnAsVariables("Mean_Intensity"));
							secondaryFinalTable.setColumn("C3_IntDen", secondarySCC3Intensity.getColumnAsVariables("IntDen"));
						}
						
						if (numberOfChannels >=4) {
							secondaryFinalTable.setColumn("C4_Mean_Intensity", secondarySCC4Intensity.getColumnAsVariables("Mean_Intensity"));
							secondaryFinalTable.setColumn("C4_IntDen", secondarySCC4Intensity.getColumnAsVariables("IntDen"));
						}
						
						
						// Save the secondary results table 
						String secondaryResultsName = dir + "Secondary_Object_Results.csv";
						try {
							secondaryFinalTable.saveAs(secondaryResultsName);
						} catch (IOException e) {
							e.printStackTrace();
							IJ.log("Cannot save secondary results table. Check that the objects were created.");
						}
						
						
						
						/*
						 * Build and save the final table for tertiary objects.
						 * 
						 */
							
							ResultsTable tertiarySCC1Intensity = IntensityMeasurements.process(channelsSingleCell[0], tertiaryObjectsCells);
							ResultsTable tertiarySCC2Intensity = null;
							ResultsTable tertiarySCC3Intensity = null;
							ResultsTable tertiarySCC4Intensity = null;
							
							if (numberOfChannels >=2) {
								tertiarySCC2Intensity = IntensityMeasurements.process(channelsSingleCell[1], tertiaryObjectsCells);
							}
							if (numberOfChannels >= 3) {
								tertiarySCC3Intensity = IntensityMeasurements.process(channelsSingleCell[2], tertiaryObjectsCells);
							}
							if (numberOfChannels >= 4) {
								tertiarySCC4Intensity = IntensityMeasurements.process(channelsSingleCell[3], tertiaryObjectsCells);
							}
							
							
							// new results table - SC = single cell
							ResultsTable tertiaryImageDataSC = new ResultsTable();
							
							// check to see if grouping info has been entered, if yes, then populate the imageData table, if not, ignore. 
							if (SingleCellView.groupingInfo.isEmpty()) {
								
									for (int o = 0; o < tertiarySCC1Intensity.size() ; o++) {
										tertiaryImageDataSC.addRow();
										tertiaryImageDataSC.addValue("ImageID", imgName);
									}
									
							} else {
								
								String group = SingleCellView.groupingInfo;
								
								for (int o = 0; o < tertiarySCC1Intensity.size() ; o++) {
									tertiaryImageDataSC.addRow();
									tertiaryImageDataSC.addValue("ImageID", imgName);
									tertiaryImageDataSC.addValue("Group", group);
								}
							}
							
							
							// build the final tertiary table 
							ResultsTable tertiaryFinalTable = new ResultsTable(); 
							
							tertiaryFinalTable.setColumn("Label", tertiarySCC1Intensity.getColumnAsVariables("Label"));
							tertiaryFinalTable.setColumn("ImageID", tertiaryImageDataSC.getColumnAsVariables("ImageID"));
							if (!SingleCellView.groupingInfo.isEmpty()) {
								tertiaryFinalTable.setColumn("Group", tertiaryImageDataSC.getColumnAsVariables("Group"));
							}
							tertiaryFinalTable.setColumn("Volume", tertiaryResults.getColumnAsVariables("Volume"));
							tertiaryFinalTable.setColumn("Voxel_Count", tertiaryResults.getColumnAsVariables("VoxelCount"));
							tertiaryFinalTable.setColumn("Sphericity", tertiaryResults.getColumnAsVariables("Sphericity"));
							tertiaryFinalTable.setColumn("Elongation", tertiaryResults.getColumnAsVariables("Elli.R1/R3"));
							tertiaryFinalTable.setColumn("Mean_Breadth", tertiaryResults.getColumnAsVariables("MeanBreadth"));
							tertiaryFinalTable.setColumn("Centroid_X", tertiaryResults.getColumnAsVariables("Centroid.X"));
							tertiaryFinalTable.setColumn("Centroid_Y", tertiaryResults.getColumnAsVariables("Centroid.Y"));
							tertiaryFinalTable.setColumn("Centroid_Z", tertiaryResults.getColumnAsVariables("Centroid.Z"));
							tertiaryFinalTable.setColumn("C1_Mean_Intensity", tertiarySCC1Intensity.getColumnAsVariables("Mean_Intensity"));
							tertiaryFinalTable.setColumn("C1_IntDen", tertiarySCC1Intensity.getColumnAsVariables("IntDen"));
							
							if (numberOfChannels >=2) {
								tertiaryFinalTable.setColumn("C2_Mean_Intensity", tertiarySCC2Intensity.getColumnAsVariables("Mean_Intensity"));
								tertiaryFinalTable.setColumn("C2_IntDen", tertiarySCC2Intensity.getColumnAsVariables("IntDen"));
							}
							
							if (numberOfChannels >=3) {
								tertiaryFinalTable.setColumn("C3_Mean_Intensity", tertiarySCC3Intensity.getColumnAsVariables("Mean_Intensity"));
								tertiaryFinalTable.setColumn("C3_IntDen", tertiarySCC3Intensity.getColumnAsVariables("IntDen"));
							}
							
							if (numberOfChannels >=4) {
								tertiaryFinalTable.setColumn("C4_Mean_Intensity", tertiarySCC4Intensity.getColumnAsVariables("Mean_Intensity"));
								tertiaryFinalTable.setColumn("C4_IntDen", tertiarySCC4Intensity.getColumnAsVariables("IntDen"));
							}
							
							
							// Save the tertiary results table 
							String tertiaryResultsName = dir + "Tertiary_Object_Results.csv";
							try {
								tertiaryFinalTable.saveAs(tertiaryResultsName);
							} catch (IOException e) {
								e.printStackTrace();
								IJ.log("Cannot save tertiary results table. Check that the objects were created.");
							}
							
						
					
					/*
					 * Build the final results table. 
					 * Match labels between primary, secondary and tertiary results tables so that row label[x] = all data on x 		
					 */
							
					// TODO: add user-input channel names to the combined results table if the user input text, otherwise leave as "Cx".
							
							for (int j = 0; j < primaryFinalTable.size() ; j++) {
								
								int primaryLabel = Integer.parseInt(primaryFinalTable.getStringValue("Label", j));
								
								// find matching rows
								int secondaryRowIndex = findMatchingRow(secondaryFinalTable, "Label", primaryLabel);
								int tertiaryRowIndex = findMatchingRow(tertiaryFinalTable, "Label", primaryLabel);
								
								// where row labels match between all three tables, add a row to the combined results table
								if (secondaryRowIndex >=0 && tertiaryRowIndex >= 0) {
									combinedResults.addRow();
									combinedResults.addValue("Label", primaryLabel);
									combinedResults.addValue("ImageID", primaryFinalTable.getStringValue("ImageID", j));
									if(!SingleCellView.groupingInfo.isEmpty()) {
										combinedResults.addValue("Group", primaryFinalTable.getStringValue("Group", j));
									}
									combinedResults.addValue("Primary_Volume", primaryFinalTable.getValue("Volume", j));
									combinedResults.addValue("Primary_Voxel_Count", primaryFinalTable.getValue("Voxel_Count", j));
									combinedResults.addValue("Primary_Sphericity", primaryFinalTable.getValue("Sphericity", j));
									combinedResults.addValue("Primary_Elongation", primaryFinalTable.getValue("Elongation", j));
									combinedResults.addValue("Primary_Mean_Breadth", primaryFinalTable.getValue("Mean_Breadth", j));
									combinedResults.addValue("Primary_Centroid_X", primaryFinalTable.getValue("Centroid_X", j));
									combinedResults.addValue("Primary_Centroid_Y", primaryFinalTable.getValue("Centroid_Y", j));
									combinedResults.addValue("Primary_Centroid_Z", primaryFinalTable.getValue("Centroid_Z", j));
									combinedResults.addValue("Primary_C1_Mean_Intensity", primaryFinalTable.getValue("C1_Mean_Intensity", j));
									combinedResults.addValue("Primary_C1_IntDen", primaryFinalTable.getValue("C1_IntDen", j));
									if (numberOfChannels >=2) {
										combinedResults.addValue("Primary_C2_Mean_Intensity", primaryFinalTable.getValue("C2_Mean_Intensity", j));
										combinedResults.addValue("Primary_C2_IntDen", primaryFinalTable.getValue("C2_IntDen", j));
										}
									if (numberOfChannels >=3) {
										combinedResults.addValue("Primary_C3_Mean_Intensity", primaryFinalTable.getValue("C3_Mean_Intensity", j));
										combinedResults.addValue("Primary_C3_IntDen", primaryFinalTable.getValue("C3_IntDen", j));
										}
									if (numberOfChannels >=4) {
										combinedResults.addValue("Primary_C4_Mean_Intensity", primaryFinalTable.getValue("C4_Mean_Intensity", j));
										combinedResults.addValue("Primary_C4_IntDen", primaryFinalTable.getValue("C4_IntDen", j));
										}
									
									combinedResults.addValue("Secondary_Volume", secondaryFinalTable.getValue("Volume", secondaryRowIndex));
									combinedResults.addValue("Secondary_Voxel_Count", secondaryFinalTable.getValue("Voxel_Count", secondaryRowIndex));
									combinedResults.addValue("Secondary_Sphericity", secondaryFinalTable.getValue("Sphericity", secondaryRowIndex));
									combinedResults.addValue("Secondary_Elongation", secondaryFinalTable.getValue("Elongation", secondaryRowIndex));
									combinedResults.addValue("Secondary_Mean_Breadth", secondaryFinalTable.getValue("Mean_Breadth", secondaryRowIndex));
									combinedResults.addValue("Secondary_Centroid_X", secondaryFinalTable.getValue("Centroid_X", secondaryRowIndex));
									combinedResults.addValue("Secondary_Centroid_Y", secondaryFinalTable.getValue("Centroid_Y", secondaryRowIndex));
									combinedResults.addValue("Secondary_Centroid_Z", secondaryFinalTable.getValue("Centroid_Z", secondaryRowIndex));
									combinedResults.addValue("Secondary_C1_Mean_Intensity", secondaryFinalTable.getValue("C1_Mean_Intensity", secondaryRowIndex));
									combinedResults.addValue("Secondary_C1_IntDen", secondaryFinalTable.getValue("C1_IntDen", secondaryRowIndex));
									if (numberOfChannels >=2) {
										combinedResults.addValue("Secondary_C2_Mean_Intensity", secondaryFinalTable.getValue("C2_Mean_Intensity", secondaryRowIndex));
										combinedResults.addValue("Secondary_C2_IntDen", secondaryFinalTable.getValue("C2_IntDen", secondaryRowIndex));
										}
									if (numberOfChannels >=3) {
										combinedResults.addValue("Secondary_C3_Mean_Intensity", secondaryFinalTable.getValue("C3_Mean_Intensity", secondaryRowIndex));
										combinedResults.addValue("Secondary_C3_IntDen", secondaryFinalTable.getValue("C3_IntDen", secondaryRowIndex));
										}
									if (numberOfChannels >=4) {
										combinedResults.addValue("Secondary_C4_Mean_Intensity", secondaryFinalTable.getValue("C4_Mean_Intensity", secondaryRowIndex));
										combinedResults.addValue("Secondary_C4_IntDen", secondaryFinalTable.getValue("C4_IntDen", secondaryRowIndex));
										}
									combinedResults.addValue("Tertiary_Volume", tertiaryFinalTable.getValue("Volume", tertiaryRowIndex));
									combinedResults.addValue("Tertiary_Voxel_Count", tertiaryFinalTable.getValue("Voxel_Count", tertiaryRowIndex));
									combinedResults.addValue("Tertiary_Sphericity", tertiaryFinalTable.getValue("Sphericity", tertiaryRowIndex));
									combinedResults.addValue("Tertiary_Elongation", tertiaryFinalTable.getValue("Elongation", tertiaryRowIndex));
									combinedResults.addValue("Tertiary_Mean_Breadth", tertiaryFinalTable.getValue("Mean_Breadth", tertiaryRowIndex));
									combinedResults.addValue("Tertiary_Centroid_X", tertiaryFinalTable.getValue("Centroid_X", tertiaryRowIndex));
									combinedResults.addValue("Tertiary_Centroid_Y", tertiaryFinalTable.getValue("Centroid_Y", tertiaryRowIndex));
									combinedResults.addValue("Tertiary_Centroid_Z", tertiaryFinalTable.getValue("Centroid_Z", tertiaryRowIndex));
									combinedResults.addValue("Tertiary_C1_Mean_Intensity", tertiaryFinalTable.getValue("C1_Mean_Intensity", tertiaryRowIndex));
									combinedResults.addValue("Tertiary_C1_IntDen", tertiaryFinalTable.getValue("C1_IntDen", tertiaryRowIndex));
									if (numberOfChannels >=2) {
										combinedResults.addValue("Tertiary_C2_Mean_Intensity", tertiaryFinalTable.getValue("C2_Mean_Intensity", tertiaryRowIndex));
										combinedResults.addValue("Tertiary_C2_IntDen", tertiaryFinalTable.getValue("C2_IntDen", tertiaryRowIndex));
										}
									if (numberOfChannels >=3) {
										combinedResults.addValue("Tertiary_C3_Mean_Intensity", tertiaryFinalTable.getValue("C3_Mean_Intensity", tertiaryRowIndex));
										combinedResults.addValue("Tertiary_C3_IntDen", tertiaryFinalTable.getValue("C3_IntDen", tertiaryRowIndex));
										}
									if (numberOfChannels >=4) {
										combinedResults.addValue("Tertiary_C4_Mean_Intensity", tertiaryFinalTable.getValue("C4_Mean_Intensity", tertiaryRowIndex));
										combinedResults.addValue("Tertiary_C4_IntDen", tertiaryFinalTable.getValue("C4_IntDen", tertiaryRowIndex));
										}
									
									// create some ratios -> MAKE THIS CONDITIONAL ON USER INPUT!!! Modular in some way?
									combinedResults.addValue("Primary/Tertiary_Volume_Ratio", primaryFinalTable.getValue("Volume", j) / tertiaryFinalTable.getValue("Volume", tertiaryRowIndex));
									if(numberOfChannels >=2) {
										combinedResults.addValue("Primary/Tertiary_C2_IntDen_Ratio", primaryFinalTable.getValue("C2_IntDen", j) / tertiaryFinalTable.getValue("C2_IntDen", tertiaryRowIndex));
									}
									if(numberOfChannels >=3) {
										combinedResults.addValue("Primary/Tertiary_C3_IntDen_Ratio", primaryFinalTable.getValue("C3_IntDen", j) / tertiaryFinalTable.getValue("C3_IntDen", tertiaryRowIndex));
									}
									if(numberOfChannels >=4) {
										combinedResults.addValue("Primary/Teritary_C4_IntDen_Ratio", primaryFinalTable.getValue("C4_IntDen", j) / tertiaryFinalTable.getValue("C4_IntDen", tertiaryRowIndex));
									}
									
									/* // loop through each 
									for (int x = 1; x <= numberOfChannels; x++) {
										String table = "Primary";
										String meanName = table + "_C" + Integer.toString(x) + "_Mean_Intensity";
										String meanTable = "C" + Integer.toString(x) + "_Mean_Intensity";
										String intdenName = table + "_C" + Integer.toString(x) + "_IntDen";
										String intdenTable = "C" + Integer.toString(x) + "_IntDen";
										IJ.log(meanName + " and " + meanTable + " and " + intdenName + " and " + intdenTable);
										
										combinedResults.addValue(meanName, primaryFinalTable.getValue(meanTable, j));
										combinedResults.addValue(intdenName, primaryFinalTable.getValue(intdenTable, j));
									}
									
									for (int y = 1; y <= numberOfChannels; y++) {
										String table = "Tertiary";
										String meanName = table + "_C" + Integer.toString(y) + "_Mean_Intensity";
										String meanTable = "C" + Integer.toString(y) + "_Mean_Intensity";
										String intdenName = table + "_C" + Integer.toString(y) + "_IntDen";
										String intdenTable = "C" + Integer.toString(y) + "_IntDen";
										
										combinedResults.addValue(meanName, tertiaryFinalTable.getValue(meanTable, tertiaryRowIndex));
										combinedResults.addValue(intdenName, tertiaryFinalTable.getValue(intdenTable, tertiaryRowIndex));
									}
									*/
									
									
									
								}
							}	
					} // end of single image loop!!
					
					combinedResults.show("Combined Results");
					
					// Save the combined results table 
					String combinedResultsName = dir + "Combined_Results.csv";
					try {
						combinedResults.saveAs(combinedResultsName);
					} catch (IOException e) {
						e.printStackTrace();
						IJ.log("Cannot save combined results table. Check that the objects were created.");
					}
					long endTime = System.currentTimeMillis();
					double timeSec = (endTime - startTime) / 1000;
					IJ.log("It took " + timeSec + " seconds to process " + list.length + " images.");
					IJ.log("Single Cell Batch Protocol Complete!");
			}
		});
	t1.start();
	}
	
	
	/**
	 * A helper method for matching labels between results tables in processSingleCells().
	 * 
	 * @param table 
	 * 			Results table to compare to primary table.
	 * @param columnName 
	 * 			The column header to search.
	 * @param value 
	 * 			The row index from the primary table.
	 * 
	 * @return The row in table that contains the same object label.
	 */

	private int findMatchingRow(ResultsTable table, String columnName, int value) {
		int index = -1;
		for (int i = 0; i < table.size(); i++) {
			int label = Integer.parseInt(table.getStringValue(columnName, i));
			if (label == value) {
				index = i; 
				break;
			}
		}
		return index;
	}
	
	
	
	// parameterise this!!! processSpheoroid(boolean analysisOnly, String inputDir, String outputDir, int primaryChannelChoie, int secondaryChannelChoice etc.)
	/* ------------------------------------------------------------------------------------
	 * This method segments primary and secondary objects based on user-defined parameters.
	 * Objects are then used for region-restricted intensity analysis and 3D measurements. 
	 * -----------------------------------------------------------------------------------*/
	public static void processSpheroid(boolean analysisOnly) {
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
					IJ.log("----------FOCUST: Spheroid Protocol----------");
					IJ.log("Processing image " + count + " of " + list.length);
					IJ.log("Current image name: " + list[i]);
					IJ.log("---------------------------------------------");
					ImagePlus imp = IJ.openImage(path);
					Calibration cal = imp.getCalibration();
					channelsSpheroid = ChannelSplitter.split(imp);
					int primaryChannelChoice = SpheroidView.primaryChannelChoice;
					int secondaryChannelChoice = SpheroidView.secondaryChannelChoice;
					String imgName = imp.getTitle();
					int numberOfChannels = imp.getNChannels();
					
					/*
					 * create and measure primary objects
					 */
					
					
					// If analysis-only-mode, find the right primary object file for the current image.  
					if(analysisOnly) {
						String fileName = list[i].replace(".nd2", ".tif");
						primaryObjectSpheroid = IJ.openImage(SpheroidView.inputDir + primaryPrefix + fileName);
					} else {
						IJ.log("Primary object segmention:");
						primaryObjectSpheroid = gpuSegmentOtsu(channelsSpheroid[primaryChannelChoice], SpheroidView.sigma_x, SpheroidView.sigma_y, SpheroidView.sigma_z, SpheroidView.radius_x, SpheroidView.radius_y, SpheroidView.radius_z);
					}
					
					IJ.log("Processing primary object...");
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
						secondaryObjectSpheroid = gpuSpheroidSecondaryObject(secondaryChannelChoice);
					}
					
					
					secondaryObjectSpheroid.setCalibration(cal);
					IJ.resetMinAndMax(secondaryObjectSpheroid);
					if(!analysisOnly) {
						IJ.saveAs(secondaryObjectSpheroid, "TIF", dir + "Secondary_Objects_" + imgName);
					}
					
					ResultsTable secondaryResults = analyze3D.process(secondaryObjectSpheroid);
					
					
					/* Create Spheroid ROIs
					* - Binary search for the optimal number of 3D erosions that yeild the closest final volume target for the core (innerNew)
					*/
					
					// fixed
					ImagePlus innerROI = secondaryObjectSpheroid.duplicate();
					IJ.run(innerROI, "Make Binary", "method=Default background=Dark black");
					IJ.run(innerROI, "Options...", "iterations=70 count=1 black do=Erode stack");
					IJ.saveAs(innerROI, "TIF", dir + "Inner_Secondary_" + imgName);
					
					// iterative
					IJ.log("Generating a Core...");
					ImagePlus innerNew = BinarySearch.createSpheroidCore(secondaryObjectSpheroid.duplicate(), 0.5);
					IJ.saveAs(innerNew, "TIF", dir + "NEW_CORE_" + imgName);
					
					// Create outer ROI
					ImagePlus outerROI = ImageCalculator.run(secondaryObjectSpheroid, innerROI, "Subtract create stack");
					IJ.saveAs(outerROI, "TIF", dir + "Outer_Secondary_" + imgName);
					
					
					
					
					/* 
					 * Intensity measurements and building results tables 
					 */
					// Primary Objects

					// TODO: change c1 and c3 back from static. Unless best practice? That was just part of testing.
					
					ResultsTable primaryC1Intensity = IntensityMeasurements.process(channelsSpheroid[0], primaryObjectSpheroid);
					ResultsTable primaryC2Intensity = null;
					ResultsTable primaryC3Intensity = null;
					ResultsTable primaryC4Intensity = null;
					
					
					if (numberOfChannels >=2) {
						primaryC2Intensity = IntensityMeasurements.process(channelsSpheroid[1], primaryObjectSpheroid);
					}
					if (numberOfChannels >=3) {
						primaryC3Intensity = IntensityMeasurements.process(channelsSpheroid[2], primaryObjectSpheroid);
					}
					if (numberOfChannels >=4) {
						primaryC4Intensity = IntensityMeasurements.process(channelsSpheroid[3], primaryObjectSpheroid);
					}
					
					/* 
					 * Write image name and grouping (where entered) data to a results table.
					 * This allows whole columns to be extracted as Variable[] to construct the final table.
					 */

					
					// new results table type
					ResultsTable primaryImageData = new ResultsTable();
					
					// check to see if grouping info has been entered, if yes, then populate the imageData table, if not, ignore. 
					if (SpheroidView.groupingInfo.isEmpty()) {
						
							for (int o = 0; o < primaryC1Intensity.size() ; o++) {
								primaryImageData.addRow();
								primaryImageData.addValue("ImageID", imgName);
							}
							
					} else {
						
						String group = SpheroidView.groupingInfo;
						
						for (int o = 0; o < primaryC1Intensity.size() ; o++) {
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
					primaryFinalTable.setColumn("Mean_Breadth", primaryResults.getColumnAsVariables("MeanBreadth"));
					primaryFinalTable.setColumn("Centroid_X", primaryResults.getColumnAsVariables("Centroid.X"));
					primaryFinalTable.setColumn("Centroid_Y", primaryResults.getColumnAsVariables("Centroid.Y"));
					primaryFinalTable.setColumn("Centroid_Z", primaryResults.getColumnAsVariables("Centroid.Z"));
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
					ResultsTable secondaryC1Intensity = IntensityMeasurements.process(channelsSpheroid[0], secondaryObjectSpheroid);
					ResultsTable secondaryC2Intensity = null;
					ResultsTable secondaryC3Intensity = null;
					ResultsTable secondaryC4Intensity = null;
					
					if (numberOfChannels >=2) {
						secondaryC2Intensity = IntensityMeasurements.process(channelsSpheroid[1], secondaryObjectSpheroid);
					}
					if (numberOfChannels >=3) {
						secondaryC3Intensity = IntensityMeasurements.process(channelsSpheroid[2], secondaryObjectSpheroid);
					}
					if (numberOfChannels >=4) {
						secondaryC4Intensity = IntensityMeasurements.process(channelsSpheroid[3], secondaryObjectSpheroid);
					}
					
					
					// new temp results table
					ResultsTable secondaryImageData = new ResultsTable();
			
					// check to see if grouping info has been entered, if yes, then populate the imageData table, if not, ignore. 
					if (SpheroidView.groupingInfo.isEmpty()) {
							for (int o = 0; o < secondaryC1Intensity.size() ; o++) {
								secondaryImageData.addRow();
								secondaryImageData.addValue("ImageID", imgName);
							}
					} else {
						String group = SpheroidView.groupingInfo;
						for (int o = 0; o < secondaryC1Intensity.size() ; o++) {
							secondaryImageData.addRow();
							secondaryImageData.addValue("ImageID", imgName);
							secondaryImageData.addValue("Group", group);
						}
					}
					
					
					// intensity measurements for core and periphery 
					ResultsTable coreC1Intensity = IntensityMeasurements.process(channelsSpheroid[0], innerROI);
					ResultsTable coreC2Intensity = IntensityMeasurements.process(channelsSpheroid[1], innerROI);
					ResultsTable coreC3Intensity = IntensityMeasurements.process(channelsSpheroid[2], innerROI);
					ResultsTable coreC4Intensity = IntensityMeasurements.process(channelsSpheroid[3], innerROI);
					
					ResultsTable peripheryC1Intensity = IntensityMeasurements.process(channelsSpheroid[0], outerROI);
					ResultsTable peripheryC2Intensity = IntensityMeasurements.process(channelsSpheroid[1], outerROI);
					ResultsTable peripheryC3Intensity = IntensityMeasurements.process(channelsSpheroid[2], outerROI);
					ResultsTable peripheryC4Intensity = IntensityMeasurements.process(channelsSpheroid[3], outerROI);
					
					
					
					
					
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
					secondaryFinalTable.setColumn("Mean_Breadth", secondaryResults.getColumnAsVariables("MeanBreadth"));
					secondaryFinalTable.setColumn("Centroid_X", secondaryResults.getColumnAsVariables("Centroid.X"));
					secondaryFinalTable.setColumn("Centroid_Y", secondaryResults.getColumnAsVariables("Centroid.Y"));
					secondaryFinalTable.setColumn("Centroid_Z", secondaryResults.getColumnAsVariables("Centroid.Z"));
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
					
				} // end of single image loop!! 
			}
		});
		t1.start();
	}

	
	
	
	/**
	 * Segment on the GPU via CLIJ2 with Otsu thresholding. 
	 * 
	 * @param channel
	 * 			ImagePlus of the channel to process
	 * @param sigma_x
	 * 			The x sigma for Gaussian blur 3D
	 * @param sigma_y
	 * 			The y sigma for Gaussian blur 3D
	 * @param sigma_z
	 * 			The z sigma for Gaussian blur 3D
	 * @param detect_x
	 * 			The x radius of spot detection
	 * @param detect_y
	 * 			The y radius of spot detection
	 * @param detect_z
	 * 			The z radius of spot detection
	 * 
	 * @return A segmented ImagePlus.
	 */
	public static ImagePlus gpuSegmentOtsu(ImagePlus channel, double sigma_x, double sigma_y, double sigma_z, double detect_x, double detect_y, double detect_z) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		clij2.clear();
		CLIJx clijx = CLIJx.getInstance();
		clijx.clear();

		ClearCLBuffer input = clij2.push(channel);
		ClearCLBuffer blurred = clij2.create(input);
		ClearCLBuffer inverted = clij2.create(input);
		ClearCLBuffer threshold = clij2.create(input);
		ClearCLBuffer detectedMax = clij2.create(input);
		ClearCLBuffer labelledSpots = clij2.create(input);
		ClearCLBuffer segmented = clij2.create(input);

		clij2.gaussianBlur3D(input, blurred, sigma_x, sigma_y, sigma_z);

		clij2.invert(blurred, inverted);

		clij2.thresholdOtsu(blurred, threshold);

		clij2.detectMaxima3DBox(blurred, detectedMax, detect_x, detect_y, detect_z);

		clij2.labelSpots(detectedMax, labelledSpots);

		MorphoLibJMarkerControlledWatershed.morphoLibJMarkerControlledWatershed(clij2, inverted, labelledSpots, threshold, segmented);
		 
		ImagePlus output = clij2.pull(segmented);
		
		input.close();
		blurred.close();
		inverted.close();
		threshold.close();
		detectedMax.close();
		labelledSpots.close();
		segmented.close();
		
		clij2.clear();
		clijx.clear();
		
		return output;

	}

	
	/**
	 * Spheroid secondary object (whole spheroid without segmentation) 
	 * One spheroid per image only. 
	 * 
	 * @param secondaryChannelChoice
	 * @return ImagePlus of the thresholded spheroid.
	 */
	public static ImagePlus gpuSpheroidSecondaryObject(int secondaryChannelChoice) {

		CLIJ2 clij2 = CLIJ2.getInstance();
		clij2.clear();
		CLIJx clijx = CLIJx.getInstance();
		clijx.clear();

		ClearCLBuffer input = clij2.push(channelsSpheroid[secondaryChannelChoice]);
		ClearCLBuffer blurred = clij2.create(input);
		ClearCLBuffer threshold = clij2.create(input);
		ClearCLBuffer fillHoles = clij2.create(input);

		clij2.gaussianBlur3D(input, blurred, SpheroidView.sigma_x2, SpheroidView.sigma_x2, SpheroidView.sigma_z2);

		clij2.greaterConstant(blurred, threshold, SpheroidView.greaterConstant);

		clij2.binaryFillHoles(threshold, fillHoles);

		secondaryObjectSpheroid = clij2.pull(fillHoles);

		input.close();
		blurred.close();
		threshold.close();
		fillHoles.close();

		return secondaryObjectSpheroid;
	}
	
	
	
	/**
	 * Segment on the GPU via CLIJ2 with greater constant (manual) thresholding. 
	 * 
	 * @param channel
	 * 			ImagePlus of the channel to process
	 * @param sigma_x
	 * 			The x sigma for Gaussian blur 3D
	 * @param sigma_y
	 * 			The y sigma for Gaussian blur 3D
	 * @param sigma_z
	 * 			The z sigma for Gaussian blur 3D
	 * @param constant
	 * 			The threshold constant value
	 * @param detect_x
	 * 			The x radius of spot detection
	 * @param detect_y
	 * 			The y radius of spot detection
	 * @param detect_z
	 * 			The z radius of spot detection
	 * 
	 * @return A segmented ImagePlus.
	 */
	public static ImagePlus gpuSegmentGreaterConstant(ImagePlus channel, double sigma_x, double sigma_y, double sigma_z, double constant, double detect_x, double detect_y, double detect_z) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		clij2.clear();
		CLIJx clijx = CLIJx.getInstance();
		clijx.clear();
		
		ClearCLBuffer input = clij2.push(channel);
		ClearCLBuffer blurred = clij2.create(input);
		ClearCLBuffer inverted = clij2.create(input);
		ClearCLBuffer threshold = clij2.create(input);
		ClearCLBuffer detectedMax = clij2.create(input);
		ClearCLBuffer labelledSpots = clij2.create(input);
		ClearCLBuffer segmented = clij2.create(input);
		ClearCLBuffer connected = clij2.create(input);
		
		clij2.gaussianBlur3D(input, blurred, sigma_x, sigma_y, sigma_z);
		
		clij2.invert(blurred, inverted);
	
		clij2.greaterConstant(blurred, threshold, constant);
		
		clij2.detectMaxima3DBox(blurred, detectedMax, detect_x, detect_y, detect_z);
		
		clij2.labelSpots(detectedMax, labelledSpots);

		MorphoLibJMarkerControlledWatershed.morphoLibJMarkerControlledWatershed(clij2, inverted, labelledSpots, threshold, segmented);
		
		clij2.connectedComponentsLabelingBox(segmented, connected);
		
		ImagePlus output = clij2.pull(connected);
		
		clij2.clear();
		clijx.clear();
		
		
		return output;
	}

	
}


