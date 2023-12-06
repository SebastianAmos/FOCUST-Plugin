package clcm.focust;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import clcm.focust.parameters.ParameterCollection;
import ij.measure.ResultsTable;

class ResultsTableUtilityTest {

	@Test
	void stackAndSaveResultsTest(){

		// Set directory below. 
		File dir = createDir();
		
		// Generate results, append to existing csv or write a new one if it doesn't exist.
		
			try {
				ResultsTableUtility rtUtil = new ResultsTableUtility();
				
				ResultsTable rtTest = testResultsTable();
				System.out.println(rtTest.toString());
				
				ParameterCollection params = ParameterCollection.builder().
						outputDir(dir.toString()).
						build();
				
				rtUtil.saveAndStackResults(rtTest, "test_results", params);
				
				// Check csv is exists
				File csvFile = new File(dir + "/" + "test_results.csv");
				assertTrue(csvFile.exists());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	
	private File createDir() {
		File dir = new File("C:/Users/21716603/Desktop/rtTesting");
		dir.mkdir();
		return dir;
	}


	void deleteTemps(File tempDir) {
		File[] contents = tempDir.listFiles();
		if (contents != null) {
			for (File file : contents) {
				if (file.isFile()) {
					file.delete();
				}
			}
		}
		tempDir.delete();
	}

	
	/**
	 * Generate an example ResultsTable for testing
	 * @return
	 */
	private ResultsTable testResultsTable() {
		ResultsTable rt = new ResultsTable();
		rt.addRow();
		rt.addValue("image", "Test-Image-1");
		rt.addValue("ID", 56);
		rt.addValue("Value", 123.987);
		rt.addRow();
		rt.addValue("image", "Test-Image-2");
		rt.addValue("ID", 123);
		rt.addValue("Value", 987.123);
		return rt;
	}

}
