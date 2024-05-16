package clcm.focust.mode;

import clcm.focust.parameters.ParameterCollection;
import clcm.focust.segmentation.labels.StratifyAndQuantifyLabels;
import clcm.focust.utility.*;
import ij.IJ;
import ij.measure.ResultsTable;
import net.haesleinhuepf.clij2.CLIJ2;

import static clcm.focust.utility.SwingIJLoggerUtils.ijLog;

public class ModeSingleCell implements Mode {
	
	private String primaryObjectName = "Primary.";
	private String secondaryObjectName = "Secondary.";
	private String tertiaryObjectName = "Tertiary.";
	

	@Override
	public void run(ParameterCollection parameters, CompiledImageData imgData, String imgName) {

		// Run mode basic to save spreadsheets individually for reference - TESTING
		//ModeBasic mb = new ModeBasic();
		//mb.run(parameters, imgData, imgName);

		/*
		 * MAP DUPLICATE PRIMARY OBJECTS
		 */
		ManageDuplicates md = new ManageDuplicates();
		RelabelledObjects primaryRelabelled = md.run(imgData.images.getSecondary(), imgData.images.getPrimary(), imgData.getPrimary());
		IJ.saveAs(primaryRelabelled.getRelabelled(), "TIF", parameters.getInputDir() + "Primary_Objects_Relabelled_" + imgName);

		/*
		 * MERGE THE DATA BY LABEL 
		 */
		TableUtility tu = new TableUtility();
		ResultsTableUtility rtSave = new ResultsTableUtility();
		
		// join secondary to primary - writing to a new table to avoid duplicate labels and the need to remove non-matches.
		ResultsTable rt = tu.joinTablesByLabel(primaryRelabelled.getResults(), primaryObjectName, imgData.getSecondary(), secondaryObjectName, "Label");

		// TODO - NOT TESTED FOR SEGMENTING INDUVIDUAL CYTOS, ONLY FROM TERTIARY AS DIFFERENCE
		// join tertiary into combined if tertiary data is available
		if (parameters.getProcessTertiary() || parameters.getTertiaryIsDifference()) {
			rt = tu.joinTablesByLabel(rt, "", imgData.getTertiary(), tertiaryObjectName, "Label");
		}

		rtSave.saveAndStackResults(rt, "single_cells", parameters);

		// clean up
		CLIJ2 clij2 = CLIJ2.getInstance();
		clij2.clear();

		Timer.stop(parameters);

	}
}