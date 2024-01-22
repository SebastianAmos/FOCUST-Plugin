package clcm.focust.segmentation.labels;

import java.util.HashMap;
import java.util.Map;

import clcm.focust.mode.CompiledImageData;
import clcm.focust.parameters.ParameterCollection;
import ij.measure.ResultsTable;

public class StratifyProcess {
	
	// switch to determine which label maps to stratify
	// each method generates a single results table
	public Map<String, ResultsTable> run(ParameterCollection params, CompiledImageData imgData) {
		
		Map<String, ResultsTable> tables = new HashMap<>();
		
		Double quarter = 0.25;
		Double half = 0.5;
		
		StratifyAndQuantifyLabels stratify = new StratifyAndQuantifyLabels();
		
		if(params.getStratifyParameters().getPrimary25()) {
			tables.put("pri25", stratify.process(imgData, imgData.getImages().getPrimary(), quarter));
			
		}
		
		if(params.getStratifyParameters().getPrimary50()) {
			tables.put("pri50", stratify.process(imgData, imgData.getImages().getPrimary(), half));
		}
		
		
		if(params.getStratifyParameters().getSecondary25()) {
			tables.put("sec25", stratify.process(imgData, imgData.getImages().getSecondary(), quarter));
		}
		
		
		if(params.getStratifyParameters().getSecondary50()) {
			tables.put("sec50", stratify.process(imgData, imgData.getImages().getSecondary(), half));
		}
		
		
		// manage with optional tertiary object
		if(params.getStratifyParameters().getTertiary25()) {
			imgData.getImages().getTertiary().ifPresent(t -> {
				tables.put("ter25", stratify.process(imgData, t, quarter));
			});
			
		}
		
		
		// manage with optional tertiary object
		if(params.getStratifyParameters().getTertiary50()) {
			imgData.getImages().getTertiary().ifPresent(t -> {
				tables.put("ter50", stratify.process(imgData, t, half));
			});
			
		}
		
		return tables;
		
	}
	
}
