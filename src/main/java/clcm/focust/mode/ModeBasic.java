package clcm.focust.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import clcm.focust.ResultsTableUtility;
import clcm.focust.TableUtility;
import clcm.focust.parameters.ParameterCollection;
import ij.ImagePlus;
import ij.measure.ResultsTable;
import inra.ijpb.plugins.AnalyzeRegions3D;
import static clcm.focust.SwingIJLoggerUtils.ijLog;

public class ModeBasic implements Mode {

	private AnalyzeRegions3D analyze3D = new AnalyzeRegions3D();
	ArrayList<ImagePlus> segmentedObjects = new ArrayList<>();
	ArrayList<ResultsTable> measureResults = null;

	List<ResultsTable> primaryResults = new ArrayList<>();
	List<ResultsTable> secondaryResults = new ArrayList<>();
	List<ResultsTable> tertiaryResults = new ArrayList<>();

	
	/**
	 * Segmentation is followed by 3D measurements and intensity quantification.
	 * Each object type is treated independently and one results table per object type is generated.
	 */
	
	@Override
	public void run(ParameterCollection parameters, CompiledImageData imgData, String imgName) {

		ResultsTable rt1 = null; // primary
		ResultsTable rt2 = null; // secondary
		ResultsTable rt3 = null; // tertiary
		

		// Quantify primary and secondary, then optional tertiary.
		ijLog("Analysing objects...");
		primaryResults.add(analyze3D.process(imgData.images.getPrimary()));
		secondaryResults.add(analyze3D.process(imgData.images.getSecondary()));
		imgData.images.getTertiary().ifPresent(t -> tertiaryResults.add(analyze3D.process(t)));

		
		// Add segmented images to lists for intensity analysis
		segmentedObjects.add(imgData.images.getPrimary());
		segmentedObjects.add(imgData.images.getSecondary());
		imgData.images.getTertiary().ifPresent(t -> {segmentedObjects.add(t);});

		
		ijLog("Measuring channel intensities...");
		// Map intensity calcs to each object type
		Map<ImagePlus, ResultsTable> intensityTables = TableUtility.compileIntensityResults(segmentedObjects, 
				imgData.images.getChannels().toArray(new ImagePlus[imgData.images.getChannels().size()]), 
				parameters);


		// Extract the data we want to see first in the results table and add to the final results lists in the first position.
		primaryResults.add(0, TableUtility.extractGroupAndTitle(rt1, parameters, imgName));
		secondaryResults.add(0, TableUtility.extractGroupAndTitle(rt2, parameters, imgName));
		imgData.images.getTertiary().ifPresent(t -> {
			tertiaryResults.add(0, TableUtility.extractGroupAndTitle(rt3, parameters, imgName));
		});


		// Then append the intensity data to the end.
		primaryResults.add(intensityTables.get(segmentedObjects.get(0)));
		secondaryResults.add(intensityTables.get(segmentedObjects.get(1)));
		imgData.images.getTertiary().ifPresent(t -> {
			tertiaryResults.add(intensityTables.get(segmentedObjects.get(2)));
		});


		/* Append standard skeleton results */
		
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
		
		/* Append the stratification results tables if they were generated */
		for (Map.Entry<String, ResultsTable> band : imgData.getStratifyResults().entrySet()) {
			
			String type = band.getKey();
			ResultsTable rt = band.getValue();
			
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
		
		
		
		/*
		 * Build and save the final results tables
		 */
		
		ResultsTableUtility rtSave = new ResultsTableUtility();
		TableUtility tu = new TableUtility();
		
		ijLog("Saving results tables...");
		
		// Primary results
		rtSave.saveAndStackResults(tu.compileAllResults(primaryResults), "primary_objects", parameters);

		// Secondary results
		rtSave.saveAndStackResults(tu.compileAllResults(secondaryResults), "secondary_objects", parameters);

		// Tertiary results (optional)
		imgData.images.getTertiary().ifPresent(t ->{
			rtSave.saveAndStackResults(tu.compileAllResults(tertiaryResults), "tertiary_objects", parameters);
		});
	}
} 



