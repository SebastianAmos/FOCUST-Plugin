package clcm.focust.mode;

import static clcm.focust.SwingIJLoggerUtils.ijLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import clcm.focust.TableUtility;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.segmentation.labels.StratifiedResultsHolder;
import ij.ImagePlus;
import ij.measure.ResultsTable;
import inra.ijpb.plugins.AnalyzeRegions3D;

public class ModeAnalyse implements Mode {
	
	private AnalyzeRegions3D analyze3D = new AnalyzeRegions3D();
	private ArrayList<ImagePlus> segmentedObjects = new ArrayList<>();
	private ArrayList<ResultsTable> measureResults = null;

	private List<ResultsTable> primaryResults = new ArrayList<>();
	private List<ResultsTable> secondaryResults = new ArrayList<>();
	private List<ResultsTable> tertiaryResults = new ArrayList<>();
	
	/**
	 * Method to conduct common analyses shared between modes.
	 * 
	 * @param parameters
	 * @param imgData
	 * @param imgName
	 */
	public void run(ParameterCollection parameters, CompiledImageData imgData, String imgName) {

		// Quantify primary, secondary, and optional tertiary.
		ijLog("Analysing objects...");
		primaryResults.add(analyze3D.process(imgData.getImages().getPrimary()));
		secondaryResults.add(analyze3D.process(imgData.getImages().getSecondary()));
		imgData.getImages().getTertiary().ifPresent(t -> tertiaryResults.add(analyze3D.process(t)));

		
		// Add segmented images to lists for intensity analysis
		segmentedObjects.add(imgData.getImages().getPrimary());
		segmentedObjects.add(imgData.getImages().getSecondary());
		imgData.getImages().getTertiary().ifPresent(t -> {segmentedObjects.add(t);});

		
		// Map intensity data to each object type
		ijLog("Measuring channel intensities...");
		Map<ImagePlus, ResultsTable> intensityTables = TableUtility.compileIntensityResults(segmentedObjects, 
				imgData.getImages().getChannels().toArray(new ImagePlus[imgData.getImages().getChannels().size()]), 
				parameters);
		
		
		// Extract the data we want to see first in the results table and add to the final results lists in the first position.
		primaryResults.add(0, TableUtility.extractGroupAndTitle(primaryResults.get(0), parameters, imgName));
		secondaryResults.add(0, TableUtility.extractGroupAndTitle(secondaryResults.get(0), parameters, imgName));
		imgData.getImages().getTertiary().ifPresent(t -> {
			tertiaryResults.add(0, TableUtility.extractGroupAndTitle(tertiaryResults.get(0), parameters, imgName));
		});
		
		ijLog("Results 1");
		
		// Then append the intensity data to the end.
		primaryResults.add(intensityTables.get(segmentedObjects.get(0)));
		secondaryResults.add(intensityTables.get(segmentedObjects.get(1)));
		imgData.getImages().getTertiary().ifPresent(t -> {
			tertiaryResults.add(intensityTables.get(segmentedObjects.get(2)));
		});
		ijLog("Results 2");
		
		// Append standard skeleton results
		if(parameters.getSkeletonParamters().getPrimary()) {
			primaryResults.add(imgData.getSkeletons().get("Primary").getStandard());
		}
		
		if(parameters.getSkeletonParamters().getSecondary()) {
			primaryResults.add(imgData.getSkeletons().get("Secondary").getStandard());
		}
		
		if(parameters.getSkeletonParamters().getTertairy()) {
			imgData.getImages().getTertiary().ifPresent(t -> {
				primaryResults.add(imgData.getSkeletons().get("Tertiary").getStandard());
			});
		}
		
		ijLog("Results 3");
		
		/* Append the stratification results tables if they were generated */
		for (Entry<String, StratifiedResultsHolder> band : imgData.getStratifyResults().entrySet()) {
			
			String type = band.getKey();
			ResultsTable rt = band.getValue().getTable();
			
			switch (type) {
			case "pri25":
				primaryResults.add(rt);
			case "pri50":
				primaryResults.add(rt);
			case "sec25":
				secondaryResults.add(rt);
			case "sec50":
				secondaryResults.add(rt);
			case "ter25":
				tertiaryResults.add(rt);
			case "ter50":
				tertiaryResults.add(rt);
			default:
				break;
			}
		}
		
		ijLog("Results 4");
		
		/*
		 * Build the final results table for each object type and update the imgData object before hand off
		 */
		TableUtility tu = new TableUtility();
		
		imgData.setPrimary(tu.compileAllResults(primaryResults));
		imgData.setSecondary(tu.compileAllResults(secondaryResults));
		imgData.getImages().getTertiary().ifPresent(t -> {
			imgData.setTertiary(tu.compileAllResults(tertiaryResults));
		});
		
		ijLog("Handing off.");
		
		// Hand off to selected mode
		parameters.getMode().getMode().run(parameters, imgData, imgName);
		ijLog("Results 5");
	}
}
