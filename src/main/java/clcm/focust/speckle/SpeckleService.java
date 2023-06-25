package clcm.focust.speckle;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import clcm.focust.FOCUST;
import clcm.focust.FOCUSTService;
import clcm.focust.LabelEditor;
import clcm.focust.SpeckleView;
import clcm.focust.TableUtility;
import clcm.focust.data.DataConstants.Datum;
import clcm.focust.config.RuntimeConfiguration;
import clcm.focust.data.DataConstants;
import clcm.focust.data.DataListener;
import clcm.focust.data.DataMapService;
import ij.IJ;
import ij.ImagePlus;
import ij.macro.Variable;
import ij.measure.ResultsTable;
import ij.plugin.ImageCalculator;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

/**
 * TODO Document.
 * 
 * @author lachie
 *
 */
public class SpeckleService implements FOCUSTService, DataListener<Datum, Speckles> {

	private static final DataMapService<DataConstants.Datum, clcm.focust.config.RuntimeConfiguration> configMgr = FOCUST
			.instance().rtConfManager();

	@Override
	public void init() {
		FOCUST.instance().specklesManager().registerListener(Datum.DATUM, this);
	}

	@Override
	public void shutdown() {
		FOCUST.instance().specklesManager().deregisterListener(Datum.DATUM, this);
	}

	@Override
	public void dataUpdated(Datum key, Speckles newData) {
		CLIJ2 clij2 = CLIJ2.getInstance();
		ImagePlus primaryObjectsSpeckles = newData.getSpeckle(SpeckleType.PRIMARY)
				.orElseThrow(IllegalStateException::new);
		ImagePlus secondaryObjectsSpeckles = newData.getSpeckle(SpeckleType.SECONDARY)
				.orElseThrow(IllegalStateException::new);
		ImagePlus tertiaryObjectsSpeckles = newData.getSpeckle(SpeckleType.TERTIARY)
				.orElseThrow(IllegalStateException::new);

		// If kill borders is selected, then apply the appropriate method
		// TODO killBordersText cannot be accessed like this!.
		switch (SpeckleView.killBordersText) {
		case "No"  :
			break;

		case "X + Y":
			String imgName = "temp"; // TODO obtain this bad boi from data.
			// add padding to Z for primary objects
			LabelEditor.padTopAndBottom(primaryObjectsSpeckles);
			final Path saveAsPath = configMgr.get(DataConstants.Datum.DATUM)
					.map(RuntimeConfiguration::getInputDirectory)
					.map(p -> p.resolve(SpeckleType.PRIMARY.getPaddedPrefix() + imgName))
					.orElseThrow(() -> new IllegalArgumentException(""));

			IJ.saveAs(primaryObjectsSpeckles, "TIF", saveAsPath.toString());

			ClearCLBuffer primaryPadded = clij2.push(primaryObjectsSpeckles);
			ClearCLBuffer removedBorders = clij2.create(primaryPadded);
			ClearCLBuffer resetZ = null;

			// kill borders for primary object
			clij2.excludeLabelsOnEdges(primaryPadded, removedBorders);

			// remove the z padding
			clij2.subStack(removedBorders, resetZ, 1, (int) removedBorders.getDepth() - 1);

			primaryObjectsSpeckles = clij2.pull(resetZ);
			clij2.clear();

			// mask secondary and tertiary objects by remaining primary objects.
			// THIS CAN BE SENT TO GPU INSTEAD
			secondaryObjectsSpeckles = ImageCalculator.run(primaryObjectsSpeckles, secondaryObjectsSpeckles,
					"Multiply create stack");
			tertiaryObjectsSpeckles = ImageCalculator.run(primaryObjectsSpeckles, tertiaryObjectsSpeckles,
					"Multiply create stack");

			break;

		case "X + Y + Z":
			// No padding required in Z
			// Run kill borders on primary objects then mask other object channels
			ClearCLBuffer primaryLabel = clij2.push(primaryObjectsSpeckles);
			ClearCLBuffer removedBorder = clij2.create(primaryLabel);

			clij2.excludeLabelsOnEdges(primaryLabel, removedBorder);

			primaryObjectsSpeckles = clij2.pull(removedBorder);
			clij2.clear();

			secondaryObjectsSpeckles = ImageCalculator.run(primaryObjectsSpeckles, secondaryObjectsSpeckles,
					"Multiply create stack");
			tertiaryObjectsSpeckles = ImageCalculator.run(primaryObjectsSpeckles, tertiaryObjectsSpeckles,
					"Multiply create stack");

			break;
		}

		if (!analysisOnly || SpeckleView.killBordersText == "X + Y" || SpeckleView.killBordersText == "X + Y + Z") {
			IJ.saveAs(primaryObjectsSpeckles, "TIF", dir + "Primary_Objects_" + imgName);
			IJ.saveAs(secondaryObjectsSpeckles, "TIF", dir + "Secondary_Objects_" + imgName);
			IJ.saveAs(tertiaryObjectsSpeckles, "TIF", dir + "Tertiary_Objects_" + imgName);
		}

		IJ.log("Running Volumetric Analysis...");
		IJ.log("-------------------------------------------------------");

		// Make an array of all segmented objects --> MAKE CONDITIONAL ON HOW MANY
		// EXIST! maybe an arraylist?
		ImagePlus[] objectImages = { primaryObjectsSpeckles, secondaryObjectsSpeckles, tertiaryObjectsSpeckles };

		// make tertiary conditional as not all images will require 3 channel processing
		ResultsTable primaryResults = analyze3D.process(primaryObjectsSpeckles);
		ResultsTable secondaryResults = analyze3D.process(secondaryObjectsSpeckles);
		ResultsTable tertiaryResults = analyze3D.process(tertiaryObjectsSpeckles);

		// Add to the maps
		TableUtility.collectColumns(primaryResults, primary);
		TableUtility.collectColumns(secondaryResults, secondary);
		TableUtility.collectColumns(tertiaryResults, tertiary);

		IJ.log("Running Intensity Analysis...");
		IJ.log("-------------------------------------------------------");

		// A map to store the intensity results for each segmented image.
		Map<ImagePlus, ResultsTable> intensityTables = new HashMap<>();

		// Measure the intensity of each channel, within each segmented object.
		for (int j = 0; j < objectImages.length; j++) {

			ResultsTable result = new ResultsTable();

			for (int k = 0; k < channelsSpeckle.length; k++) {

				ResultsTable temp = TableUtility.processIntensity(channelsSpeckle[k], objectImages[j]);
				// result.setColumn("Label", temp.getColumnAsVariables("Label"));
				result.setColumn(("C" + (k + 1) + "_Mean_Intensity").toString(),
						temp.getColumnAsVariables("Mean_Intensity"));
				result.setColumn(("C" + (k + 1) + "_IntDen").toString(), temp.getColumnAsVariables("IntDen"));

			}

			intensityTables.put(objectImages[j], result);

		}

		/*
		 * Count overlapping labels
		 * 
		 * - Count the number of secondary and tertiary objects for each primary object
		 * - Calculate parent for each secondary and tertiary object.
		 * 
		 * 
		 * - make conditional on whether tertiary objects exist!!
		 */

		IJ.log("Generating Results Tables...");
		IJ.log("-------------------------------------------------------");

		// Count secondary and tertiary objects per primary object.
		ResultsTable c2Count = LabelEditor.countOverlappingLabels(primaryObjectsSpeckles, secondaryObjectsSpeckles);
		ResultsTable c3Count = LabelEditor.countOverlappingLabels(primaryObjectsSpeckles, tertiaryObjectsSpeckles);

		c2Count.show("c2count");
		c3Count.show("c2count");

		// Calculate parent (primary) for each secondary and tertiary object.
		ResultsTable c2Parent = TableUtility.processIntensity(primaryObjectsSpeckles, secondaryObjectsSpeckles);
		ResultsTable c3Parent = TableUtility.processIntensity(primaryObjectsSpeckles, tertiaryObjectsSpeckles);
		c2Parent.show("c2parent");

		// Leave the table with just max value (which represents the count per primary
		// object)
		ResultsTable c2CountEdit = c2Count;
		ResultsTable c3CountEdit = c3Count;
		ResultsTable c2ParentEdit = c2Parent;
		ResultsTable c3ParentEdit = c3Parent;

		c2CountEdit.show("c2countedit");
		c2ParentEdit.show("c2parentshow");

		// remove all columns by max
		String[] colsToRemove = { "Label", "Mean_Intensity", "Volume", "IntDen" };

		// remove cols not working
		TableUtility.removeColumns(c2CountEdit, colsToRemove);
		TableUtility.removeColumns(c3CountEdit, colsToRemove);
		TableUtility.removeColumns(c2ParentEdit, colsToRemove);
		TableUtility.removeColumns(c3ParentEdit, colsToRemove);

		c2CountEdit.renameColumn("Max", "C2_Object_Count");
		c3CountEdit.renameColumn("Max", "C3_Object_Count");

		c3CountEdit.show("thisbitch!");
		// add the C2 and C3 counts to the primary map
		TableUtility.collectColumns(c2CountEdit, primary);
		TableUtility.collectColumns(c3CountEdit, primary);

		c2ParentEdit.renameColumn("Max", "Parent_Label");
		c3ParentEdit.renameColumn("Max", "Parent_Label");

		// add the parent label to the secondary and tertiary tables
		TableUtility.collectColumns(c2ParentEdit, secondary);
		TableUtility.collectColumns(c3ParentEdit, tertiary);

		// Add imageID and grouping info to intensity tables.
		String group = SpeckleView.groupingInfo;

		for (ResultsTable rt : intensityTables.values()) {
			int length = rt.size();
			if (SpeckleView.groupingInfo.isEmpty()) {
				for (int j = 0; j < length; j++) {
					rt.setValue("ImageID", j, imgName);
				}
			} else {
				for (int j = 0; j < length; j++) {
					rt.setValue("ImageID", j, imgName);
					rt.setValue("Group", j, group);
				}
			}
		}

		/*
		 * Build the final tables - first extract the intensity tables from the map
		 */

		ResultsTable primaryIntensity = null;
		ResultsTable secondaryIntensity = null;
		ResultsTable tertiaryIntensity = null;

		for (Map.Entry<ImagePlus, ResultsTable> entry : intensityTables.entrySet()) {

			ImagePlus key = entry.getKey();
			ResultsTable val = entry.getValue();

			if (key.equals(primaryObjectsSpeckles)) {
				primaryIntensity = val;
			}

			if (key.equals(secondaryObjectsSpeckles)) {
				secondaryIntensity = val;
			}

			if (key.equals(tertiaryObjectsSpeckles)) {
				tertiaryIntensity = val;
			}
		}

		TableUtility.collectColumns(primaryIntensity, primary);
		TableUtility.collectColumns(secondaryIntensity, secondary);
		TableUtility.collectColumns(tertiaryIntensity, tertiary);

		// priLabel.add(primaryIntensity.getColumnAsVariables("Label"));
		// priVolume.add(primaryResults.getColumnAsVariables("Volume"));
		// priImgID.add(primaryIntensity.getColumnAsVariables("ImageID"));
		/*
		 * labels = primaryIntensity.getColumnAsVariables("Label"); imageID =
		 * primaryIntensity.getColumnAsVariables("ImageID"); vol =
		 * primaryResults.getColumnAsVariables("Volume");
		 * 
		 * for (Variable variable : labels) { priLabel.add(variable); } for (Variable
		 * variable : imageID) { priImgID.add(variable); } for (Variable variable : vol)
		 * { priVolume.add(variable); }
		 * 
		 */

		// Primary table
		primaryFinalResults = new ResultsTable();
		primaryFinalResults.setColumn("Label", primaryResults.getColumnAsVariables("Label"));
		primaryFinalResults.setColumn("ImageID", primaryIntensity.getColumnAsVariables("ImageID"));
		if (!SpeckleView.groupingInfo.isEmpty()) {
			primaryFinalResults.setColumn("Group", primaryIntensity.getColumnAsVariables("Group"));
		}
		primaryFinalResults.setColumn("C2_Object_Count", c2Count.getColumnAsVariables("C2_Object_Count"));
		primaryFinalResults.setColumn("C3_Object_Count", c3Count.getColumnAsVariables("C3_Object_Count"));
		primaryFinalResults.setColumn("Volume", primaryResults.getColumnAsVariables("Volume"));
		primaryFinalResults.setColumn("Voxel_Count", primaryResults.getColumnAsVariables("VoxelCount"));
		primaryFinalResults.setColumn("Sphericity", primaryResults.getColumnAsVariables("Sphericity"));
		primaryFinalResults.setColumn("Elongation", primaryResults.getColumnAsVariables("Elli.R1/R3"));
		primaryFinalResults.setColumn("Mean_Breadth", primaryResults.getColumnAsVariables("MeanBreadth"));
		primaryFinalResults.setColumn("Surface_Area", primaryResults.getColumnAsVariables("SurfaceArea"));
		primaryFinalResults.setColumn("Centroid_X", primaryResults.getColumnAsVariables("Centroid.X"));
		primaryFinalResults.setColumn("Centroid_Y", primaryResults.getColumnAsVariables("Centroid.Y"));
		primaryFinalResults.setColumn("Centroid_Z", primaryResults.getColumnAsVariables("Centroid.Z"));

		// This needs to be conditional on channel number as Cx may not exist for every
		// dataset.
		primaryFinalResults.setColumn("C1_Mean_Intensity", primaryIntensity.getColumnAsVariables("C1_Mean_Intensity"));
		primaryFinalResults.setColumn("C1_IntDen", primaryIntensity.getColumnAsVariables("C1_IntDen"));
		primaryFinalResults.setColumn("C2_Mean_Intensity", primaryIntensity.getColumnAsVariables("C2_Mean_Intensity"));
		primaryFinalResults.setColumn("C2_IntDen", primaryIntensity.getColumnAsVariables("C2_IntDen"));
		primaryFinalResults.setColumn("C3_Mean_Intensity", primaryIntensity.getColumnAsVariables("C3_Mean_Intensity"));
		primaryFinalResults.setColumn("C3_IntDen", primaryIntensity.getColumnAsVariables("C3_IntDen"));

		// Secondary table
		secondaryFinalResults = new ResultsTable();

		secondaryFinalResults.setColumn("Label", secondaryResults.getColumnAsVariables("Label"));
		secondaryFinalResults.setColumn("ImageID", secondaryIntensity.getColumnAsVariables("ImageID"));
		if (!SpeckleView.groupingInfo.isEmpty()) {
			secondaryFinalResults.setColumn("Group", secondaryIntensity.getColumnAsVariables("Group"));
		}
		secondaryFinalResults.setColumn("Parent_Label", c2Parent.getColumnAsVariables("Parent_Label"));
		secondaryFinalResults.setColumn("Volume", secondaryResults.getColumnAsVariables("Volume"));
		secondaryFinalResults.setColumn("Voxel_Count", secondaryResults.getColumnAsVariables("VoxelCount"));
		secondaryFinalResults.setColumn("Sphericity", secondaryResults.getColumnAsVariables("Sphericity"));
		secondaryFinalResults.setColumn("Elongation", secondaryResults.getColumnAsVariables("Elli.R1/R3"));
		secondaryFinalResults.setColumn("Mean_Breadth", secondaryResults.getColumnAsVariables("MeanBreadth"));
		secondaryFinalResults.setColumn("Surface_Area", secondaryResults.getColumnAsVariables("SurfaceArea"));
		secondaryFinalResults.setColumn("Centroid_X", secondaryResults.getColumnAsVariables("Centroid.X"));
		secondaryFinalResults.setColumn("Centroid_Y", secondaryResults.getColumnAsVariables("Centroid.Y"));
		secondaryFinalResults.setColumn("Centroid_Z", secondaryResults.getColumnAsVariables("Centroid.Z"));

		// This needs to be conditional on channel number as Cx may not exist for every
		// dataset.
		secondaryFinalResults.setColumn("C1_Mean_Intensity",
				secondaryIntensity.getColumnAsVariables("C1_Mean_Intensity"));
		secondaryFinalResults.setColumn("C1_IntDen", secondaryIntensity.getColumnAsVariables("C1_IntDen"));
		secondaryFinalResults.setColumn("C2_Mean_Intensity",
				secondaryIntensity.getColumnAsVariables("C2_Mean_Intensity"));
		secondaryFinalResults.setColumn("C2_IntDen", secondaryIntensity.getColumnAsVariables("C2_IntDen"));
		secondaryFinalResults.setColumn("C3_Mean_Intensity",
				secondaryIntensity.getColumnAsVariables("C3_Mean_Intensity"));
		secondaryFinalResults.setColumn("C3_IntDen", secondaryIntensity.getColumnAsVariables("C3_IntDen"));

		// Tertiary table
		tertiaryFinalResults = new ResultsTable();

		tertiaryFinalResults.setColumn("Label", tertiaryResults.getColumnAsVariables("Label"));
		tertiaryFinalResults.setColumn("ImageID", tertiaryIntensity.getColumnAsVariables("ImageID"));
		if (!SpeckleView.groupingInfo.isEmpty()) {
			tertiaryFinalResults.setColumn("Group", tertiaryIntensity.getColumnAsVariables("Group"));
		}
		tertiaryFinalResults.setColumn("Parent_Label", c3Parent.getColumnAsVariables("Parent_Label"));
		tertiaryFinalResults.setColumn("Volume", tertiaryResults.getColumnAsVariables("Volume"));
		tertiaryFinalResults.setColumn("Voxel_Count", tertiaryResults.getColumnAsVariables("VoxelCount"));
		tertiaryFinalResults.setColumn("Sphericity", tertiaryResults.getColumnAsVariables("Sphericity"));
		tertiaryFinalResults.setColumn("Elongation", tertiaryResults.getColumnAsVariables("Elli.R1/R3"));
		tertiaryFinalResults.setColumn("Mean_Breadth", tertiaryResults.getColumnAsVariables("MeanBreadth"));
		tertiaryFinalResults.setColumn("Surface_Area", tertiaryResults.getColumnAsVariables("SurfaceArea"));
		tertiaryFinalResults.setColumn("Centroid_X", tertiaryResults.getColumnAsVariables("Centroid.X"));
		tertiaryFinalResults.setColumn("Centroid_Y", tertiaryResults.getColumnAsVariables("Centroid.Y"));
		tertiaryFinalResults.setColumn("Centroid_Z", tertiaryResults.getColumnAsVariables("Centroid.Z"));

		// This needs to be conditional on channel number as Cx may not exist for every
		// dataset.
		tertiaryFinalResults.setColumn("C1_Mean_Intensity",
				tertiaryIntensity.getColumnAsVariables("C1_Mean_Intensity"));
		tertiaryFinalResults.setColumn("C1_IntDen", tertiaryIntensity.getColumnAsVariables("C1_IntDen"));
		tertiaryFinalResults.setColumn("C2_Mean_Intensity",
				tertiaryIntensity.getColumnAsVariables("C2_Mean_Intensity"));
		tertiaryFinalResults.setColumn("C2_IntDen", tertiaryIntensity.getColumnAsVariables("C2_IntDen"));
		tertiaryFinalResults.setColumn("C3_Mean_Intensity",
				tertiaryIntensity.getColumnAsVariables("C3_Mean_Intensity"));
		tertiaryFinalResults.setColumn("C3_IntDen", tertiaryIntensity.getColumnAsVariables("C3_IntDen"));

		// end of single image loop!!

		/*
		 * ResultsTable primary = new ResultsTable(); Variable[] primaryLabels =
		 * priLabel.toArray(new Variable[priLabel.size()]); Variable[] primaryImgID =
		 * priImgID.toArray(new Variable[priImgID.size()]); Variable[] primaryVolume =
		 * priVolume.toArray(new Variable[priVolume.size()]);
		 * 
		 * primary.setColumn("Label", primaryLabels); primary.setColumn("ImageID",
		 * primaryImgID); primary.setColumn("Volume", primaryVolume);
		 */

		/*
		 * ResultsTable primary = new ResultsTable(); int c = priLabel.size(); for (int
		 * i = 0; i < c; i++) { // if it's the first element to be written to the table
		 * if (primary.size() == 0 ) { primary.setColumn("Label", priLabel.get(i));
		 * primary.setColumn("ImageID", priImgID.get(i)); primary.setColumn("Volume",
		 * priVolume.get(i)); } else {
		 * 
		 * Variable[] lab = priLabel.get(i); Variable[] imgID = priImgID.get(i);
		 * Variable[] vol = priVolume.get(i);
		 * 
		 * 
		 * 
		 * 
		 * for (int j = 0; j < lab.length; j++) { primary.addValue("Label",
		 * lab[j].getString()); } } }
		 * 
		 * ResultsTable primaryFinal = new ResultsTable(); ResultsTable primary1 = new
		 * ResultsTable(); ResultsTable primary2 = new ResultsTable(); for (Variable[]
		 * array : priLabel) { for (Variable element : array) { primaryFinal.addRow();
		 * primaryFinal.addValue("Label", element.getString()); } } for (Variable[]
		 * array : priImgID) { for (Variable element : array) { for (int i = 0; i <
		 * primary.size(); i++) { primaryFinal.setValue("ImageID", i,
		 * element.getString()); } primary1.addRow(); primary1.addValue("ImageID",
		 * element.getString()); } } for (Variable[] array : priVolume) { for (Variable
		 * element : array) { for (int i = 0; i < primary.size(); i++) {
		 * primaryFinal.setValue("Volume", i, Double.parseDouble(element.getString()));
		 * } primary2.addRow(); primary2.addValue("Volume", element.getString()); } }
		 * 
		 * ResultsTable rt = new ResultsTable(); rt.setColumn("Label",
		 * primary1.getColumnAsVariables("Label")); rt.setColumn("ImageID",
		 * primary1.getColumnAsVariables("ImageID")); //rt.setColumn("Volume",
		 * primary2.getColumnAsVariables("Volume"));
		 */

		ResultsTable primaryTable = new ResultsTable();
		ResultsTable secondaryTable = new ResultsTable();
		ResultsTable tertiaryTable = new ResultsTable();

		primary.forEach((k, v) -> primaryTable.setColumn(k, v.toArray(new Variable[v.size()])));
		secondary.forEach((k, v) -> secondaryTable.setColumn(k, v.toArray(new Variable[v.size()])));
		tertiary.forEach((k, v) -> tertiaryTable.setColumn(k, v.toArray(new Variable[v.size()])));

		IJ.log("Saving Results Tables...");
		IJ.log("-------------------------------------------------------");

		TableUtility.saveTable(primaryFinalResults, dir, "Primary_Results.csv");
		TableUtility.saveTable(secondaryFinalResults, dir, "Secondary_Results.csv");
		TableUtility.saveTable(tertiaryFinalResults, dir, "Tertiary_Results.csv");

		TableUtility.saveTable(primaryTable, dir, "PrimaryTable.csv");
		TableUtility.saveTable(secondaryTable, dir, "SecondaryTable.csv");
		TableUtility.saveTable(tertiaryTable, dir, "TertiaryTable.csv");

		// TableUtility.saveTable(primary, dir, "ArrayListResults.csv");
		// TableUtility.saveTable(rt, dir, "RT.csv");
		// TableUtility.saveTable(primaryFinal, dir, "primaryFINAL.csv");

		long endTime = System.currentTimeMillis();
		double timeSec = (endTime - startTime) / 1000;
		IJ.log("It took " + timeSec + " seconds to process " + list.length + " images.");
		IJ.log("Speckle Batch Protocol Complete!");
		IJ.getLog();
		IJ.saveString(IJ.getLog(), dir + "Log.txt");
	}
	// TODO Auto-generated method stub

	@Override
	public void dataDeleted(Datum key) {
		// TODO Auto-generated method stub

	}

}