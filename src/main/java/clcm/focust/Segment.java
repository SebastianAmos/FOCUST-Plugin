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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

/**
 * @author SebastianAmos
 *
 */

public class Segment {

	public static ImagePlus[] channelsSpheroid;
	public static ImagePlus[] channelsSingleCell;
	public static ImagePlus primaryObjectSpheroid;
	public static ImagePlus secondaryObjectSpheroid;
	private static ImagePlus cellObjects;
	private static ImagePlus primaryOriginalObjectsCells;
	private static ImagePlus secondaryObjectsCells;
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
	 * - Make it batch process friendly! End of single image loops marked at the end of the "Process.." methods
	 */


// This method segments primary and secondary objects from single cell datasets based on user-defined parameters
	
	
	/*
	 * TODO:
	 * - implement kill borders function for secondary and primary objects (mask primary by secondary for instances where secondary objects are removed, so that child objects are as well).
	 * - find matching labels for primary, secondary and tertiary objects (label matching) then build a results table based on this. 
	 * - 
	 */
	
	
	public static void processSingleCells(boolean analysisOnly) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {

				// grab the file names
				File f = new File(SingleCellView.inputDir);
				String[] list = f.list();
				String dir = SingleCellView.inputDir;
				int count = 0;
					

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
						IJ.log("Processing image " + count + " of " + list.length);
						IJ.log("Current image name: " + list[i]);
						IJ.log("---------------------------------------------");
						ImagePlus imp = IJ.openImage(path);
						int numberOfChannels = imp.getNChannels();
						IJ.run(imp, "8-bit", "");
						Calibration cal = imp.getCalibration();
						channelsSingleCell = ChannelSplitter.split(imp);
						int primaryChannelChoice = SingleCellView.primaryChannelChoice;
						int secondaryChannelChoice = SingleCellView.secondaryChannelChoice;
						String imgName = imp.getTitle();
					
						// if analysisMode is T, find the correct primary object file for the current image
						if(analysisOnly) {
							String fileName = list[i].replace(".nd2", ".tif");
							primaryOriginalObjectsCells = IJ.openImage(SingleCellView.inputDir + primaryPrefix + fileName);
						} else {
							// if analysis mode is F, segment primary channel from user inputs
							IJ.log("Primary object segmention:");
							primaryOriginalObjectsCells = gpuSingleCell(primaryChannelChoice, SingleCellView.sigma_x, SingleCellView.sigma_y, SingleCellView.sigma_z, SingleCellView.greaterConstantPrimary, SingleCellView.radius_x, SingleCellView.radius_y, SingleCellView.radius_z);
						}
						
						IJ.log("Processing Primary Object...");
						IJ.resetMinAndMax(primaryOriginalObjectsCells);
						primaryOriginalObjectsCells.setCalibration(cal);
						
						
						// TESTING!!
						IJ.saveAs(primaryOriginalObjectsCells, "TIF", dir + "Primary_Original_Objects_" + imgName);
						
						////
						
						
						// create and measure secondary object
						IJ.log("Processing Secondary Object...");
						
						// If analysis-only-mode, find the right secondary object file for the current image.   
						if(analysisOnly) {
							String fileName = list[i].replace(".nd2", ".tif");
							secondaryObjectsCells = IJ.openImage(SingleCellView.inputDir + secondaryPrefix + fileName);
						} else {
							secondaryObjectsCells = gpuSingleCell(secondaryChannelChoice, SingleCellView.sigma_x2, SingleCellView.sigma_y2, SingleCellView.sigma_z2, SingleCellView.greaterConstantSecondary, SingleCellView.radius_x2, SingleCellView.radius_y2, SingleCellView.radius_z2);
						}
						
						IJ.resetMinAndMax(secondaryObjectsCells);
						secondaryObjectsCells.setCalibration(cal);
						
						
						
						// Give primary objects the same label ID as the secondary objects 
						// Convert to binary, get max range value (maxLUT), divide label IDs by maxLUT so ID = 1. 
						ImagePlus primaryLabelMatch = primaryOriginalObjectsCells.duplicate();
						IJ.run(primaryLabelMatch, "Make Binary", "method=Huang background=Dark black");
						IJ.run(primaryLabelMatch, "Divide...", "value=255 stack"); // if binary, max should be 255. could use getDisplayRangeMax()? *****
						
						// Assign secondary labels to primary objects 
						ImagePlus primaryObjectsCells = ImageCalculator.run(secondaryObjectsCells, primaryLabelMatch, "Multiply create stack");
						
						// Generate a tertiary (cytoplasmic) ROI (subtract primary from secondary objects)
						IJ.log("Processing Tertiary Object...");
						ImagePlus tertiaryObjectsCells = ImageCalculator.run(secondaryObjectsCells, primaryObjectsCells, "Subtract create stack");
						
						// set calibration and save the ROI if the - assuming analysis only mode provide primary and secondary objects only!
						tertiaryObjectsCells.setCalibration(cal);
						IJ.resetMinAndMax(tertiaryObjectsCells);
						
						
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
					 * Find cases where the primary, secondary and tertiary label IDs match - tracking repeated label IDs.
					 * Where labels match, write data to a combined results table. Calculate some ratios and add to new columns.
					 * In the combined results table each row = all related primary, secondary and tertiary object data.
					 * 
					 * In context, multi-nucleated cells are not uncommon so supporting multiple primary objects per secondary object is important functionality.
					 */
							
					// TODO: add user-input channel names to the combined results table if the user input text, otherwise leave as "Cx".
						
						// Create the combined table
						ResultsTable combinedResults = new ResultsTable();
						
						// A map to store counts of label instances in the primary results table. This will support multiple primary objects per secondary object. 
						Map<Integer, Integer> labelCounts = new HashMap<>();
						
						
						// for each primary label, check if exists in the secondary and tertiary tables 
						for (int j = 0; j < primaryFinalTable.size() ; j++) {
							
							int label = (int) primaryFinalTable.getValue("Label", j);
							
							// Lists to track row indices that contain matching labels
							List<Integer> secondaryRowIndices = new ArrayList<>();
							List<Integer> tertiaryRowIndices = new ArrayList<>();
							
							// Check the secondary objects table
							for (int k = 0; k < secondaryFinalTable.size() ; k++) { 
								if((int) secondaryFinalTable.getValue("Label", k) == label) {
									secondaryRowIndices.add(k);
								}
							}
							
							// Check the tertiary objects table
							for (int l = 0; l < tertiaryFinalTable.size() ; l++) {
								if((int) tertiaryFinalTable.getValue("Label", l) == label) {
									tertiaryRowIndices.add(l);
								}
							}
							
							// Check if matching labels were found in secondary and tertiary tables
							if (!secondaryRowIndices.isEmpty() && !tertiaryRowIndices.isEmpty()) {
								
								// Get the count for the current label to handle any cases of multiple primary objects
								int duplicateCount = labelCounts.getOrDefault(label, 0) + 1 ;
								labelCounts.put(label, duplicateCount);
								
								// Iterate over the rows where the labels match and add the appropriate columns to the combined results table
								
								for (int secondaryRowIndex : secondaryRowIndices) {
									for (int tertiaryRowIndex : tertiaryRowIndices) {
										
										// Add a new row and populate with columns
										// Index value for primary = j, index values from sec and tert are +RowIndex respectively
										combinedResults.addRow();
										// duplicate label counts
										//combinedResults.addValue("Label", label + "_" + duplicateCount);
										combinedResults.addValue("Label", label);
										combinedResults.addValue("ImageID", primaryFinalTable.getValue("ImageID", j));
										if (!SingleCellView.groupingInfo.isEmpty()) {
											combinedResults.addValue("Group", primaryFinalTable.getValue("Group", j));
										}
										combinedResults.addValue("Primary_Volume", primaryFinalTable.getValue("Volume", j));
										combinedResults.addValue("Primary_Voxel_Count", primaryFinalTable.getValue("Voxel_Count", j));
										combinedResults.addValue("Primary_Sphericity", primaryFinalTable.getValue("Sphericity", j));
										combinedResults.addValue("Primary_Elongation", primaryFinalTable.getValue("Elongation", j));
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
										
										// create a volume ratio between the primary and tertiary objects
										combinedResults.addValue("Primary/Tertiary_Volume_Ratio", primaryFinalTable.getValue("Volume", j)/tertiaryFinalTable.getValue("Volume", tertiaryRowIndex));

										
										
										
									}
								}
							}
						}
						
						IJ.log("trying to display combined table");
						
						combinedResults.show("Combined Results");
						
						IJ.log("combined table displayed");
						
						// Save the combined results table 
						String combinedResultsName = dir + "Combined_Results.csv";
						try {
							combinedResults.saveAs(combinedResultsName);
						} catch (IOException e) {
							e.printStackTrace();
							IJ.log("Cannot save combined results table. Check that the objects were created.");
						}
						
						IJ.log("Finished processing");
						
						
 							
						
					} // end of single image loop!!
					
					IJ.log("Finished processing");
			}
		});
	t1.start();
	}
	
	
	
	
	
	
	
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
						IJ.log("Primary object segmention:");
						gpuSpheroidPrimaryObject(primaryChannelChoice);
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
						gpuSpheroidSecondaryObject(secondaryChannelChoice);
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
					* - binary search for number of iterations to erode to approx 50 %. the results table secondaryResults contains the total volume of the whole spheroid. 
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
					
					primaryC1Intensity = IntensityMeasurements.process(channelsSpheroid[0], primaryObjectSpheroid);
					ResultsTable primaryC2Intensity = IntensityMeasurements.process(channelsSpheroid[1], primaryObjectSpheroid);
					primaryC3Intensity = IntensityMeasurements.process(channelsSpheroid[2], primaryObjectSpheroid);
					ResultsTable primaryC4Intensity = IntensityMeasurements.process(channelsSpheroid[3], primaryObjectSpheroid);
					
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
					ResultsTable secondaryC2Intensity = IntensityMeasurements.process(channelsSpheroid[1], secondaryObjectSpheroid);
					ResultsTable secondaryC3Intensity = IntensityMeasurements.process(channelsSpheroid[2], secondaryObjectSpheroid);
					ResultsTable secondaryC4Intensity = IntensityMeasurements.process(channelsSpheroid[3], secondaryObjectSpheroid);
					
					
					
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

	/*
	 * Take the selected channel and process as the primary object
	 */
	
	public static ImagePlus gpuSpheroidPrimaryObject(int primaryChannelChoice) {

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
	
	public static ImagePlus gpuSpheroidSecondaryObject(int secondaryChannelChoice) {

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
	
	
	/*
	 * Take the selected channel and process for single cells
	 */

	public static ImagePlus gpuSingleCell(int channelChoice, double sigma_x, double sigma_y, double sigma_z, double constant, double detect_x, double detect_y, double detect_z) {
		// ready clij2
		CLIJ2 clij2 = CLIJ2.getInstance();
		clij2.clear();
		CLIJx clijx = CLIJx.getInstance();
		clijx.clear();
		
		ClearCLBuffer input = clij2.push(channelsSingleCell[channelChoice]);
		ClearCLBuffer blurred = clij2.create(input);
		ClearCLBuffer inverted = clij2.create(input);
		ClearCLBuffer threshold = clij2.create(input);
		ClearCLBuffer detectedMax = clij2.create(input);
		ClearCLBuffer labelledSpots = clij2.create(input);
		ClearCLBuffer segmented = clij2.create(input);
		
		// 3D blur
		clij2.gaussianBlur3D(input, blurred, sigma_x, sigma_y, sigma_z);
		
		// invert
		clij2.invert(blurred, inverted);
		
		// threshold - greater constant
		clij2.greaterConstant(blurred, threshold, constant);
		
		// detect maxima
		clij2.detectMaxima3DBox(blurred, detectedMax, detect_x, detect_y, detect_z);
		
		// label spots
		clij2.labelSpots(detectedMax, labelledSpots);
		
		// marker controlled watershed
		MorphoLibJMarkerControlledWatershed.morphoLibJMarkerControlledWatershed(clij2, inverted, labelledSpots, threshold, segmented);
		
		cellObjects = clij2.pull(segmented);
		
		return cellObjects;
	}

	
}


