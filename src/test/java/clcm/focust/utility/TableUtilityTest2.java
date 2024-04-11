package clcm.focust.utility;

import java.io.File;
import org.junit.jupiter.api.Test;
import clcm.focust.parameters.ParameterCollection;
import ij.measure.ResultsTable;

class TableUtilityTest2 {

	@Test
	void testInnerJoin() {

		TableUtility tu = new TableUtility();
		ResultsTableUtility rtu = new ResultsTableUtility();
	
		File dir = new File("C:\\Users\\simpl\\OneDrive\\Desktop\\FOCUST");
		
		ParameterCollection params = ParameterCollection.builder().
				outputDir(dir.toString()).
				build();
		
		ResultsTable rt = testResultsTable1();
		ResultsTable rt1 = testResultsTable2();
		
		tu.innerJoin(rt, rt1, "secondary", "Label");
		
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
	
}
