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

public class ModeBasic implements Mode {

	private AnalyzeRegions3D analyze3D = new AnalyzeRegions3D();
	ArrayList<ImagePlus> segmentedObjects = null;
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
		primaryResults.add(analyze3D.process(imgData.images.getPrimary()));
		secondaryResults.add(analyze3D.process(imgData.images.getSecondary()));
		imgData.images.getTertiary().ifPresent(t -> tertiaryResults.add(analyze3D.process(t)));

		
		// Add segmented images to lists for intensity analysis
		segmentedObjects.add(imgData.images.getPrimary());
		segmentedObjects.add(imgData.images.getSecondary());
		imgData.images.getTertiary().ifPresent(t -> {segmentedObjects.add(t);});


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


		/*
		 * Build and save the final results tables
		 */

		ResultsTableUtility rtSave = new ResultsTableUtility();
		TableUtility tu = new TableUtility();

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



