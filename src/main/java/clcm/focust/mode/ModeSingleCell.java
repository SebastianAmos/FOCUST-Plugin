package clcm.focust.mode;

import clcm.focust.parameters.ParameterCollection;
import ij.measure.ResultsTable;

public class ModeSingleCell implements Mode {


	@Override
	public void run(ParameterCollection parameters, CompiledImageData imgData, String imgName) {

		/* TODO:
		 * Manage duplicate labels
		 * 
		 * New table, add row data for nuc, cell and cyto where labelIDs match.
		 * 
		 */
		
		ResultsTable rt = new ResultsTable();
		
		
	}

}
