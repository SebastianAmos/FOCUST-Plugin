package clcm.focust.mode;

import clcm.focust.parameters.ParameterCollection;
import ij.measure.ResultsTable;
import net.haesleinhuepf.clij2.CLIJ2;
import clcm.focust.utility.ManageDuplicates;
import clcm.focust.utility.RelabelledObjects;
import clcm.focust.utility.ResultsTableUtility;
import clcm.focust.utility.TableUtility;

public class ModeSingleCell implements Mode {


	@Override
	public void run(ParameterCollection parameters, CompiledImageData imgData, String imgName) {

		/* TODO:
		 * Manage duplicate labels
		 * 
		 * New table, add row data for nuc, cell and cyto where labelIDs match.
		 * 
		 */

		/*
		 * MAP DUPLICATE PRIMRAY OBJECTS
		 */

		//ManageDuplicates md = new ManageDuplicates();

		//RelabelledObjects primaryRelabelled = md.run(imgData.images.getSecondary(), imgData.images.getPrimary());


		/*
		 * MERGE THE DATA BY LABEL 
		 */
		TableUtility tu = new TableUtility();
		ResultsTableUtility rtSave = new ResultsTableUtility();
		
		ResultsTable pri = imgData.getPrimary();
		ResultsTable sec = imgData.getSecondary();
		
		pri.show("Primary");
		sec.show("Secondary");
		
		// join secondary into primary
		ResultsTable rt = tu.joinTablesByLabel(imgData.getPrimary(), "Primary.", imgData.getSecondary(), "Secondary.", "Label");

		// join tertiary into combined if tertiary data is available
		if (parameters.getProcessTertiary() || parameters.getTertiaryIsDifference()) {
			rt = tu.joinTablesByLabel(rt, "", imgData.getTertiary(), "Tertiary.", "Label");
		}

		rtSave.saveAndStackResults(rt, "single_cells", parameters);

		
		// clean up
		CLIJ2 clij2 = CLIJ2.getInstance();
		clij2.clear();
		
	}



}