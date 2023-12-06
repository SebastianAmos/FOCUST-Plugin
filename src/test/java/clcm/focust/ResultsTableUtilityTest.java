package clcm.focust;

import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;
import clcm.focust.parameters.ParameterCollection;
import ij.measure.ResultsTable;

class ResultsTableUtilityTest {

	@Test
	void stackAndSaveResultsTest() throws IOException {

		// Temp dir
		File tempDir = new File(System.getProperty("java.io.tempdir"), "test-csv");
		tempDir.mkdir();
		
		// Generate test data and run method
		try {
			ResultsTableUtility rtUtil = new ResultsTableUtility();
			
			ResultsTable rtTest = testResultsTable();
			
			ParameterCollection params = ParameterCollection.builder().
					outputDir(tempDir.getAbsolutePath()).
					build();
			
			rtUtil.saveAndStackResults(rtTest, "test_results", params, "Test Results");
			
			// Was csv created?
			File csv = new File(tempDir, "test_results.csv");
			assertTrue(csv.exists());
			
			// Are the contents expected?
			List<List<String>> expected = createExpectedData();
			List<List<String>> actual = rtUtil.readCSV(csv);
			
			assertEquals(expected, actual);
			
		} finally {
			deleteTemps(tempDir);
		}
	}

	// Clean up temp dir and contents
	private void deleteTemps(File tempDir) {
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
	 * testResultsTable as nested lists.
	 * @return
	 */
	private List<List<String>> createExpectedData() {
		
		return Arrays.asList(
				Arrays.asList("image", "ID", "Value"),
				Arrays.asList("Test-Image-1", "56", "123.987"),
				Arrays.asList("Test-Image-2", "123", "987.123"));
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
