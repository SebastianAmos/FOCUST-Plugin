package clcm.focust.mode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import clcm.focust.Segmentation;
import clcm.focust.TableUtility;
import clcm.focust.parameters.ParameterCollection;
import ij.IJ;
import ij.ImagePlus;
import ij.macro.Variable;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import ij.plugin.ChannelSplitter;
import ij.plugin.ImageCalculator;
import inra.ijpb.measure.ResultsBuilder;
import inra.ijpb.plugins.AnalyzeRegions3D;

public class ModeBasic implements Mode {

	private AnalyzeRegions3D analyze3D = new AnalyzeRegions3D();
	ArrayList<ImagePlus> segmentedObjects = null;
	ArrayList<ResultsTable> measureResults = null;
	private Map<String, List<Variable>> primaryMap = new LinkedHashMap<>();
	private Map<String, List<Variable>> secondaryMap = new LinkedHashMap<>();
	private Map<String, List<Variable>> tertiaryMap = new LinkedHashMap<>();

	/**
	 * Segmentation is followed by 3D measurements and intensity quantification. 
	 * Each object is treated independently and one results table per object type is generated. 
	 */
	@Override
	public void run(ParameterCollection parameters) {

		File f = new File(parameters.getInputDir());
		String[] list = f.list();

		for (int i = 0; i < list.length; i++) {

			String path = parameters.getInputDir() + list[i];

			// open image. 
			// TODO add a listener for new images and log the image name? 
			ImagePlus imp = IJ.openImage(path);
			String imgName = imp.getTitle();
			Calibration cal = imp.getCalibration();

			int numOfChannels = imp.getNChannels();

			// split channels
			ImagePlus[] channels = ChannelSplitter.split(imp);

			// run user-defined segmentation on the correct channel
			ImagePlus primary = Segmentation.run(channels[parameters.getPrimaryObject().getChannel()],
					parameters.getPrimaryObject());
			ImagePlus secondary = Segmentation.run(channels[parameters.getSecondaryObject().getChannel()],
					parameters.getSecondaryObject());
			ImagePlus tertiary = null;

			// if tertiary should be processed, run segmentation, otherwise generate by
			// subtraction if selected.
			if (parameters.getProcessTertiary()) {
				tertiary = Segmentation.run(channels[parameters.getTertiaryObject().getChannel()],
						parameters.getTertiaryObject());
			} else if (parameters.getTertiaryIsDifference()) {
				tertiary = ImageCalculator.run(secondary, primary, "Subtract create stack");
			}

			// set cals
			primary.setCalibration(cal);
			secondary.setCalibration(cal);
			
			// save images to output directory if available, if not, save to input directory
			// TODO add a listener for image saving a log saved names.
			if (!parameters.getOutputDir().isEmpty()) {
				IJ.saveAs(primary, "TIF", parameters.getOutputDir() + "Primary_Objects" + imgName);
				IJ.saveAs(secondary, "TIF", parameters.getOutputDir() + "Secondary_Objects" + imgName);

				if (tertiary != null) {
					tertiary.setCalibration(cal);
					IJ.saveAs(tertiary, "TIF", parameters.getOutputDir() + "Tertiary_Objects" + imgName);
				}
			} else {
				IJ.saveAs(primary, "TIF", parameters.getInputDir() + "Primary_Objects" + imgName);
				IJ.saveAs(secondary, "TIF", parameters.getInputDir() + "Secondary_Objects" + imgName);

				if (tertiary != null) {
					tertiary.setCalibration(cal);
					IJ.saveAs(tertiary, "TIF", parameters.getInputDir() + "Tertiary_Objects" + imgName);
				}
			}
			
			// add segmeneted objects to array
		
			segmentedObjects.add(primary);
			segmentedObjects.add(secondary);
			if(tertiary != null) {
				segmentedObjects.add(tertiary);
			}
			
			
			
			
			/*
			 * Measure objects and quantify label-restricted intensities of all channels and object types
			 */
			
			// could use results buidlers to acumulate results for each type... but a results manager is probably better. 
			//ResultsBuilder rb1 = new ResultsBuilder();
			
			
			ResultsTable rt1 = null; // primary
			ResultsTable rt2 = null; // secondary
			ResultsTable rt3 = null; // tertiary
			
			// grabs the columns as a variable array
			TableUtility.collectColumns(analyze3D.process(primary), primaryMap);
			TableUtility.collectColumns(analyze3D.process(secondary), secondaryMap);
			if (tertiary != null) {
				TableUtility.collectColumns(analyze3D.process(tertiary), tertiaryMap);
			}
			
			// just results tables if thats better for a results manager
			rt1 = analyze3D.process(primary);
			rt2 = analyze3D.process(secondary);
			if(tertiary != null) {
				rt3 = analyze3D.process(tertiary);
			}
			
			measureResults.add(rt1);
			measureResults.add(rt2);
			if(tertiary != null) {
				measureResults.add(rt3);
			}
			
			
			
			// Map intensity calcs to each object type
			Map<ImagePlus, ResultsTable> intensityTables = TableUtility.compileIntensityResults(segmentedObjects, channels, parameters);
			
			
			// Create tables that hold grouping and image title info for each object type
			ResultsTable rt1Info = TableUtility.extractGroupAndTitle(rt1, parameters, imgName);
			ResultsTable rt2Info = TableUtility.extractGroupAndTitle(rt2, parameters, imgName);
			ResultsTable rt3Info = null;
			if (tertiary != null) {
				rt3Info = TableUtility.extractGroupAndTitle(rt3, parameters, imgName);
			}
			
			/*
			 * for (ResultsTable rt : measureResults) {
			 * if(parameters.getGroupingInfo().isEmpty()) { for (int j = 0; j < rt.size();
			 * j++) { rt.setValue("ImageID", j, imgName); } } else { for (int j = 0; j <
			 * rt.size(); j++) { rt.setValue("ImageID", j, imgName); rt.setValue("Group", j,
			 * parameters.getGroupingInfo()); } } }
			 */			
			
			// build final results tables
			ResultsBuilder rb1 = new ResultsBuilder();
			ResultsBuilder rb2 = new ResultsBuilder();
			ResultsBuilder rb3 = new ResultsBuilder();
			
			
			/*
			 * Build the final results tables
			 */
			
			// Final results tables
			ResultsTable f1 = null;
			ResultsTable f2 = null;
			ResultsTable f3 = null;
			
			// Primary 
			rb1.addResult(rt1Info);
			rb1.addResult(rt1);
			rb1.addResult(intensityTables.get(primary));
			f1 = rb1.getResultsTable();
			
			// Secondary
			rb2.addResult(rt2Info);
			rb2.addResult(rt2);
			rb2.addResult(intensityTables.get(secondary));
			f2 = rb2.getResultsTable();
			
			// Tertiary
			if(tertiary != null) {
				rb3.addResult(rt3Info);
				rb3.addResult(rt3);
				rb3.addResult(intensityTables.get(tertiary));
				f3 = rb3.getResultsTable();
			}
			
			
			// TODO -> Save results tables to disk - stacking results by column header if they already exist
			// this will allow batch saving between images processed.
			
			}// end of single image loop
		} 
	}


