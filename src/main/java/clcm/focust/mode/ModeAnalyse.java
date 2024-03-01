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

public class ModeAnalyse {
	
	private AnalyzeRegions3D analyze3D = new AnalyzeRegions3D();
	ArrayList<ImagePlus> segmentedObjects = new ArrayList<>();
	ArrayList<ResultsTable> measureResults = null;

	List<ResultsTable> primaryResults = new ArrayList<>();
	List<ResultsTable> secondaryResults = new ArrayList<>();
	List<ResultsTable> tertiaryResults = new ArrayList<>();
	
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
		primaryResults.add(analyze3D.process(imgData.images.getPrimary()));
		secondaryResults.add(analyze3D.process(imgData.images.getSecondary()));
		imgData.images.getTertiary().ifPresent(t -> tertiaryResults.add(analyze3D.process(t)));
		
		
		// Add segmented images to lists for intensity analysis
		segmentedObjects.add(imgData.images.getPrimary());
		segmentedObjects.add(imgData.images.getSecondary());
		imgData.images.getTertiary().ifPresent(t -> {segmentedObjects.add(t);});
		
		
		// Map intensity data to each object type
		ijLog("Measuring channel intensities...");
		Map<ImagePlus, ResultsTable> intensityTables = TableUtility.compileIntensityResults(segmentedObjects, 
				imgData.images.getChannels().toArray(new ImagePlus[imgData.images.getChannels().size()]), 
				parameters);
		
		
		// Extract the data we want to see first in the results table and add to the final results lists in the first position.
		primaryResults.add(0, TableUtility.extractGroupAndTitle(primaryResults.get(0), parameters, imgName));
		secondaryResults.add(0, TableUtility.extractGroupAndTitle(secondaryResults.get(0), parameters, imgName));
		imgData.images.getTertiary().ifPresent(t -> {
			tertiaryResults.add(0, TableUtility.extractGroupAndTitle(tertiaryResults.get(0), parameters, imgName));
		});
		
		
		// Then append the intensity data to the end.
		primaryResults.add(intensityTables.get(segmentedObjects.get(0)));
		secondaryResults.add(intensityTables.get(segmentedObjects.get(1)));
		imgData.images.getTertiary().ifPresent(t -> {
			tertiaryResults.add(intensityTables.get(segmentedObjects.get(2)));
		});
		
	
		
		
		// Append standard skeleton results
		if(parameters.getSkeletonParameters().getPrimary()) {
			
			ResultsTable primarySkeletons = imgData.getSkeletons().get("Primary").getStandard();
			
			primaryResults.add(primarySkeletons);
			
			primarySkeletons.show("primarySkeletons");
		}
		
		
		/*
		 * TESTING!!! 
		 */
		
		
		if(parameters.getSkeletonParameters().getSecondary()) {
			
			
			ResultsTable secondarySkeletons = imgData.getSkeletons().get("Secondary").getStandard();
			
			//secondaryResults.add(secondarySkeletons);
			ResultsTable secTest = TableUtility.test(TableUtility.compileAllResults(secondaryResults), imgData.getSkeletons().get("Secondary"), "Secondary");
			
			secTest.show("secTest");
			
			secondarySkeletons.show("secondarySkeletons");
			
			imgData.getSkeletons().get("Secondary").getLabelMatched().show("Secondary Skeleton Matched Labels");
		
			
			
		}
		
		
		
		
		
		if(parameters.getSkeletonParameters().getTertairy()) {
			imgData.getImages().getTertiary().ifPresent(t -> {
				tertiaryResults.add(imgData.getSkeletons().get("Tertiary").getStandard());
			});
		}
		
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
		
	
		
		System.out.println("Secondary Table List Length: " + secondaryResults.size() + " tables");
		
		/*
		 * Build the final results table for each object type and update the imgData object before hand off
		 */
		
		imgData.setPrimary(TableUtility.compileTables(primaryResults));
		imgData.setSecondary(TableUtility.compileTables(secondaryResults));
		imgData.images.getTertiary().ifPresent(t -> {
			imgData.setTertiary(TableUtility.compileTables(tertiaryResults));
		});
		
		
		
		/*
		imgData.setPrimary(TableUtility.compileAllResults(primaryResults));
		imgData.setSecondary(TableUtility.compileAllResults(secondaryResults));
		imgData.images.getTertiary().ifPresent(t -> {
			imgData.setTertiary(TableUtility.compileAllResults(tertiaryResults));
		});
		
		*/
		
		
		
		
		// Hand off to selected mode
		parameters.getMode().getMode().run(parameters, imgData, imgName);
		
	}
}
