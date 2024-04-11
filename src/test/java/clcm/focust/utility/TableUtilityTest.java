package clcm.focust.utility;

import java.io.File;

import org.junit.jupiter.api.Test;

import clcm.focust.parameters.ParameterCollection;
import ij.measure.ResultsTable;

class TableUtilityTest {

	@Test
	void testJoinTablesByLabel() {
		
		TableUtility tu = new TableUtility();
		ResultsTableUtility rtu = new ResultsTableUtility();
	
		File dir = new File("C:\\Users\\simpl\\OneDrive\\Desktop\\FOCUST");
		
		ParameterCollection params = ParameterCollection.builder().
				outputDir(dir.toString()).
				build();
		
	
		ResultsTable rt = new ResultsTable();
		
		ResultsTable rt1 = testResultsTable1();
		ResultsTable rt2 = testResultsTable2();
		
		tu.joinTablesByLabel(rt1, rt2, rt, "Label");
		
		rtu.saveAndStackResults(rt1, "rt1_data", params);
		rtu.saveAndStackResults(rt2, "rt2_data", params);
		
		rtu.saveAndStackResults(rt, "combined_data", params);
		
		System.out.println(rt);
		rt.show("Combined Results");
	}

	
	
	

	/**
	 * Generate an example ResultsTable for testing
	 * @return
	 */
	private ResultsTable testResultsTable1() {
		ResultsTable rt = new ResultsTable();
		rt.addRow();
		rt.addValue("ImageID", "Test-Image-1");
		rt.addValue("Label", 1);
		rt.addValue("Primary-Value", 123.987);
		rt.addRow();
		rt.addValue("ImageID", "Test-Image-1");
		rt.addValue("Label", 3);
		rt.addValue("Primary-Value", 987.123);
		return rt;
	}
	

	/**
	 * Generate an example ResultsTable for testing
	 * @return
	 */
	private ResultsTable testResultsTable2() {
		ResultsTable rt = new ResultsTable();
		rt.addRow();
		rt.addValue("ImageID", "Test-Image-1");
		rt.addValue("Label", 1);
		rt.addValue("Secondary-Value", 86.77);
		rt.addRow();
		rt.addValue("ImageID", "Test-Image-1");
		rt.addValue("Label", 3);
		rt.addValue("Secondary-Value", 642.987);
		return rt;
	}
	
	
}
