package clcm.focust.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import clcm.focust.parameters.ParameterCollection;
import ij.measure.ResultsTable;

public class ResultsTableUtility {
	
	
	/**
	 * 
	 * @param rt The date that needs to be saved.
	 * @param rtName The name of the csv file. Ensure it is the same if the csv already exists and you want to append data.
	 * @param parameters Used to determine the path. 
	 */
	public void saveAndStackResults(ResultsTable rt, String rtName, ParameterCollection parameters) {
		
		String dir = null;
		File csv = null;
		
		// set directory
		if(!parameters.getOutputDir().isEmpty()) {
			dir = parameters.getOutputDir();
		} else {
			dir = parameters.getInputDir();
		}
		
		File directory = new File(dir);
		String path = dir + "/" + rtName + ".csv";
		File[] dirContents = directory.listFiles();
 		String csvName = rtName + ".csv";
 		
		
 		// Check if the csv already exists.
		if(containsFile(dirContents, csvName)) { 
			
			// .csv exists
			csv = new File(directory + "/" + csvName);
			List<String> existingHeaders = readCSVHeaders(csv);
			
			// Check ResultsTable and .csv headers are the same before appending.
			if(headersCheck(rt, existingHeaders)) {
				appendCSV(csv, convertResultsTableData(rt));
			} else {
				System.out.println("Headers don't match - Cannot append new data to existing .csv file.");
			}
			
		} else {
			// .csv doesn't exist: Write a new one, populating it with data.
			csv = new File(path);
			writeCSV(csv, convertResultsTableData(rt), getResultsTableHeaders(rt));
		}
		
	}
	
	
	/**
	 * Create a new csv and populate with data. The headers to be written must be supplied.
	 * 
	 * @param csv The new file (path) to be written.
	 * @param data The data to write. 
	 * @param headers The headers from the ResultsTable.
	 */
	private void writeCSV(File csv, List<List<String>> data, List<String> headers) {

		try(BufferedWriter bw = new BufferedWriter(new FileWriter(csv,false))) {
			// Write the header row
			bw.append(headers.stream().collect(Collectors.joining(",")));
			bw.append(System.lineSeparator());

			// Add the data
			data.forEach(line -> {
				try {
					bw.append(line.stream().collect(Collectors.joining(","))+System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Failed to write new csv records.");
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to write new csv records.");
		}

	}
	
	
	/**
	 * If a csv by the same name exists, append new data by stacking columns.
	 * 
	 * @param csv The existing csv file that data should be appended to.
	 * @param data The data to be appended.
	 */
	private void appendCSV(File csv, List<List<String>> data) {
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(csv,true))) {

			// 
			data.forEach(line -> {
				try {
					bw.append(line.stream().collect(Collectors.joining(","))+System.lineSeparator());
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Failed to write new csv records.");
				}
			});
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to append csv reocrds.");
		}
		
	}

	
	// Get csv headers for cross-check.
		public List<String> readCSVHeaders(File file){
			try(BufferedReader br = new BufferedReader(new FileReader(file))){
				// Header may only be the first line of the CSV. Otherwise it's bound to break.
				return Arrays.stream(br.readLine().split(",")).map(String::trim).collect(Collectors.toList());
			}catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	
		
	
	// Get .csv as nested list
		public List<List<String>> readCSV(File file){
			
			try(BufferedReader br = new BufferedReader(new FileReader(file))){
				
				/* Takes each line, separates them by comma, trims whitespace, puts it in a list, puts all of the lists from all lines into a list.*/
			    return br.lines().skip(1).map(line -> Arrays.stream(line.split(",")).map(String::trim).collect(Collectors.toList()))
			    		.collect(Collectors.toList());
			}catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}		
		}

	
	private boolean headersCheck(ResultsTable rt, List<String> existingHeaders) {
		List<String> rtHeaders = getResultsTableHeaders(rt); 
		return rtHeaders.equals(existingHeaders);
	}
	
	
	private List<String> getResultsTableHeaders(ResultsTable rt){
		return Arrays.asList(rt.getHeadings());
	}
	
	
	/**
	 * For each row and col of a ResultsTable, add to a list.
	 * Could use row to string, but would need to pull values out again anyway.
	 * @param rt
	 * @return
	 */
	public List<List<String>> convertResultsTableData(ResultsTable rt){
		List<List<String>> results = new ArrayList<>();
		for (int i = 0; i < rt.size(); i++) {
			List<String> rowDat = new ArrayList<>();
			for (int j = 0; j < rt.getLastColumn()+1; j++) {
				rowDat.add(rt.getStringValue(j, i));
			}
			results.add(rowDat);
		}
		return results;
	}
	
	
	/**
	 * Check if a directory contains a given file.
	 * 
	 * @param contents The contents of a directory.
	 * @param csv The file name to look for.
	 * @return
	 */
	public boolean containsFile(File[] contents, String csv) {
		for (File file : contents) {
			if(file.isFile() && file.getName().equals(csv)) {
				return true;
			}
		}
		return false;
	}
	
	
}
