package clcm.focust.mode;

import static clcm.focust.utility.SwingIJLoggerUtils.ijLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import clcm.focust.parameters.ParameterCollection;
import clcm.focust.segmentation.labels.StratifiedResultsHolder;
import clcm.focust.utility.TableUtility;
import com.itextpdf.text.log.SysoCounter;
import ij.ImagePlus;
import ij.measure.ResultsTable;
import inra.ijpb.plugins.AnalyzeRegions3D;

public class ModeAnalyse {
	
	private final AnalyzeRegions3D analyze3D = new AnalyzeRegions3D();
	ArrayList<ImagePlus> segmentedObjects = new ArrayList<>();
	List<ResultsTable> primaryResults = new ArrayList<>();
	List<ResultsTable> secondaryResults = new ArrayList<>();
	List<ResultsTable> tertiaryResults = new ArrayList<>();
	
	/**
	 * Method to conduct common analyses shared between modes.
	 * 
	 * @param parameters Param collection
	 * @param imgData Compiled image data
	 * @param imgName Name of the current image
	 */
	public void run(ParameterCollection parameters, CompiledImageData imgData, String imgName) {
		
		// Quantify primary, secondary, and optional tertiary.
		ijLog("Analysing objects...");
		primaryResults.add(analyze3D.process(imgData.images.getPrimary()));
		secondaryResults.add(analyze3D.process(imgData.images.getSecondary()));
		imgData.images.getTertiary().ifPresent(t -> tertiaryResults.add(analyze3D.process(t)));
		
		
		// Add segmented images to list for intensity analysis
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
		
		
		// Append the intensity data to the end.
		primaryResults.add(intensityTables.get(segmentedObjects.get(0)));
		secondaryResults.add(intensityTables.get(segmentedObjects.get(1)));
		imgData.images.getTertiary().ifPresent(t -> {
			tertiaryResults.add(intensityTables.get(segmentedObjects.get(2)));
		});


		/*
		 * Append standard skeleton results.
		 * Easier to compile current results first then match to skeletons.
		 */
		if(parameters.getSkeletonParameters().getPrimary()) {
			ResultsTable rt = TableUtility.matchAndAppendSkeletons(TableUtility.compileAllResults(primaryResults), imgData.getSkeletons().get("Primary"));
			primaryResults.clear();
			primaryResults.add(rt);
		}


		if(parameters.getSkeletonParameters().getSecondary()) {
			ResultsTable rt = TableUtility.matchAndAppendSkeletons(TableUtility.compileAllResults(secondaryResults), imgData.getSkeletons().get("Secondary"));
			secondaryResults.clear();
			secondaryResults.add(rt);
		}
		
		
		if(parameters.getSkeletonParameters().getTertairy()) {
			imgData.getImages().getTertiary().ifPresent(t -> {
				ResultsTable rt = TableUtility.matchAndAppendSkeletons(TableUtility.compileAllResults(tertiaryResults), imgData.getSkeletons().get("Tertiary"));
				tertiaryResults.clear();
				tertiaryResults.add(rt);
			});
		}




		// Append the stratification results tables if they were generated
		for (Entry<String, StratifiedResultsHolder> band : imgData.getStratifyResults().entrySet()) {
			
			String type = band.getKey();

			System.out.println("Type: " + type);

			List<ResultsTable> rtList = band.getValue().getTableList();

			//TODO - a method that appends all stratification results to the final results tables
			// compile results first, then append the stratified results

			switch (type) {
			case "pri25":
				System.out.println("pri25 Triggered");
				//primaryResults.add(rtList);
			case "pri50":
				//primaryResults.add(rtList);
			case "sec25":
				//secondaryResults.add(rt);
				System.out.println("Number of items BEFORE: " + secondaryResults.size());
				ResultsTable sec25 = TableUtility.compileAllResults(secondaryResults);
				TableUtility.addBandResults(sec25, rtList, "Q");
				secondaryResults.clear();
				secondaryResults.add(sec25);

				//ResultsTable test = (ResultsTable) secondaryResults.get(0).clone();
				//test.show("Secondary_Objects");

				System.out.println("Number of items AFTER: " + secondaryResults.size());

			case "sec50":
				System.out.println("sec50 Triggered");

				ResultsTable sec50 = TableUtility.compileAllResults(secondaryResults);
				TableUtility.addBandResults(sec50, rtList, "H");
				secondaryResults.clear();
				secondaryResults.add(sec50);

			case "ter25":
				System.out.println("ter25 Triggered");
				//tertiaryResults.add(rtList);
			case "ter50":
				//tertiaryResults.add(rtList);
			default:
				break;
			}
		}

		/*
		 * Build the final results table for each object type and update the imgData object before hand off
		 */
		imgData.setPrimary(TableUtility.compileTables(primaryResults));

		System.out.println("Number of items in secondaryResults: " + secondaryResults.size());

		imgData.setSecondary(TableUtility.compileTables(secondaryResults));

		imgData.images.getTertiary().ifPresent(t -> {
			imgData.setTertiary(TableUtility.compileTables(tertiaryResults));
		});
		

		// Hand off to selected mode
		parameters.getMode().getMode().run(parameters, imgData, imgName);
		
	}

}
