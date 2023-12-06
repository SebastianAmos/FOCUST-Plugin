package clcm.focust;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;

import clcm.focust.parameters.ParameterCollection;
import ij.measure.ResultsTable;

public class ResultsTableUtility {
	
	
	// TODO a method to save results tables to csv -> If a results table of the same name exists, stack the results by column header
	// Use apache commons csv
	public void saveAndStackResults(ResultsTable rt, String rtName, ParameterCollection parameters, String tabelTitle) {
		
		String dir = null;
		
		if(!parameters.getOutputDir().isEmpty()) {
			dir = parameters.getOutputDir();
		} else {
			dir = parameters.getInputDir();
		}
		
		String path = dir + "/" + rtName + ".csv";
		
		File csv = new File(path);
		
		
		if(csv.exists()) { 
			// csv exists - exclude headers, stack current data and write.
			List<String> existingHeaders = readCSVHeaders(csv);
			List<List<String>> existingData = readCSV(csv);
			
			if(headersCheck(rt, existingHeaders)) {
				existingData.addAll(convertResultsTableData(rt));
				writeCSV(csv, existingData, existingHeaders);
			} else {
				System.out.println("Headers don't match when stacking results. Cannot append data.");
			}
			
		} else {
			// csv doesn't exist
			List<List<String>> rtData = convertResultsTableData(rt);
			writeCSV(csv, rtData, getResultsTableHeaders(rt));
		}
		
	}
	
	
	/**
	 * Write a table 
	 * @param csv
	 * @param data
	 */
	private void writeCSV(File csv, List<List<String>> data, List<String> headers) {
		
		try {
			
			Writer writer = new FileWriter(csv, true);
			CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.builder().setHeader(headers.toArray(new String[headers.size()])).build());
			
			for (List<String> record : data) {
				printer.printRecord(record);
			}
		
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Failed to print csv records.");
		}
			
		
	}

	// get csv headers
	private List<String> readCSVHeaders(File file){
			try {
				Reader in = new FileReader(file);
				CSVFormat format = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(false).build();
				CSVParser parser = CSVParser.parse(in, format);
				List<String> headers = parser.getHeaderNames();
				return headers;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
	}
	
	
	// get csv as nested list
	public List<List<String>> readCSV(File file){
		
		try {
			Reader in = new FileReader(file);
			CSVFormat format = CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).build();
			CSVParser parser = CSVParser.parse(in, format);
			
			return parser.getRecords().stream()
					.map(record -> {
						List<String> row = new ArrayList<>();
						record.forEach(row::add);
						return row;
					}).collect(Collectors.toList());
		} catch (FileNotFoundException e) {
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
	 * For each row and col, get the data.
	 * Could use row to string, but would need to pull values out again anyway.
	 * @param rt
	 * @return
	 */
	private List<List<String>> convertResultsTableData(ResultsTable rt){
		
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
	
	
	
}
