package clcm.focust;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

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
			// csv exists - stack current data
			List<String> existingHeaders = readCSVHeaders(csv);
			List<List<String>> existingData = readCSV(csv);
			
			if(headersCheck(rt, existingHeaders)) {
				existingData.add(existingHeaders)
			}
			
			
			
			
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
	
	
	// get records
	private List<List<String>> readCSV(File file){
		
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

	private void writeCSV(File file, ResultsTable rt) {
		
	}
	
	
	private boolean headersCheck(ResultsTable rt, List<String> existingHeaders) {
		List<String> rtHeaders = getRTHeaders(rt); 
		return rtHeaders.equals(existingHeaders);
	}
	
	private List<String> getRTHeaders(ResultsTable rt){
		return Arrays.asList(rt.getHeadings());
	}
	
}
