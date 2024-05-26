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
	
		//File dir = new File("C:\\Users\\simpl\\OneDrive\\Desktop\\FOCUST");
		File dir = new File("C:\\Users\\21716603\\OneDrive - The University of Western Australia\\Desktop\\focust test");
		
		ParameterCollection params = ParameterCollection.builder().
				outputDir(dir.toString()).
				build();
		
		ResultsTable rt1 = testResultsTable1();
		ResultsTable rt2 = testResultsTable2();
		ResultsTable rt3 = testResultsTable3();
		
		ResultsTable rt = tu.joinTablesByLabel(rt1, "Primary.", rt2, "Secondary." , "Label");
		
		rt = tu.joinTablesByLabel(rt, "", rt3, "Tertiary.", "Label");
		
		
		rtu.saveAndStackResults(rt1, "rt1_data", params);
		rtu.saveAndStackResults(rt2, "rt2_data", params);
		rtu.saveAndStackResults(rt3, "rt3_data", params);
		
		
		rtu.saveAndStackResults(rt, "combined_data", params);
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
		rt.addValue("Value", 123.987);
		rt.addRow();
		rt.addValue("ImageID", "Test-Image-1");
		rt.addValue("Label", 3);
		rt.addValue("Value", 987.123);
		rt.addRow();
		rt.addValue("ImageID", "Test-Image-1");
		rt.addValue("Label", 7);
		rt.addValue("Value", 111.111);
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
		rt.addValue("Value", 86.77);
		rt.addRow();
		rt.addValue("ImageID", "Test-Image-1");
		rt.addValue("Label", 3);
		rt.addValue("Value", 642.987);
		rt.addRow();
		rt.addValue("ImageID", "Test-Image-1");
		rt.addValue("Label", 5);
		rt.addValue("Value", 123.123);
		return rt;
	}
	
	/**
	 * Generate an example ResultsTable for testing
	 * @return
	 */
	private ResultsTable testResultsTable3() {
		ResultsTable rt = new ResultsTable();
		rt.addRow();
		rt.addValue("ImageID", "Test-Image-1");
		rt.addValue("Label", 1);
		rt.addValue("Value", 10);
		rt.addRow();
		rt.addValue("ImageID", "Test-Image-1");
		rt.addValue("Label", 3);
		rt.addValue("Value", 11);
		rt.addRow();
		rt.addValue("ImageID", "Test-Image-1");
		rt.addValue("Label", 5);
		rt.addValue("Value", 12);
		return rt;
	}
	
}
