package clcm.focust.segmentation.labels;

import java.util.HashMap;
import java.util.Map;
import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.parameters.ParameterCollection;
import ij.measure.ResultsTable;

public class StratifyProcess {
	
	// switch to determine which label maps to stratify
	// each method generates a single results table
	public Map<String, ResultsTable> process(ParameterCollection params, SegmentedChannels segmentedChannels, String imgName) {
		
		Map<String, ResultsTable> tables = new HashMap<>();
		
		Double quarter = 0.25;
		Double half = 0.5;
		
		StratifyAndQuantifyLabels stratify = new StratifyAndQuantifyLabels();
		
		if(params.getStratifyParameters().getPrimary25()) {
			tables.put("pri25", stratify.process(segmentedChannels, segmentedChannels.getPrimary(), quarter, "Primary_Q", params, imgName));
		}
		
		
		if(params.getStratifyParameters().getPrimary50()) {
			tables.put("pri50", stratify.process(segmentedChannels, segmentedChannels.getPrimary(), half, "Primary_H", params, imgName));
		}
		
		
		if(params.getStratifyParameters().getSecondary25()) {
			tables.put("sec25", stratify.process(segmentedChannels, segmentedChannels.getSecondary(), quarter, "Secondary_Q", params, imgName));
		}
		
		
		if(params.getStratifyParameters().getSecondary50()) {
			tables.put("sec50", stratify.process(segmentedChannels, segmentedChannels.getSecondary(), half, "Secondary_H", params, imgName));
		}
		
		
		// manage with optional tertiary object
		if(params.getStratifyParameters().getTertiary25()) {
			segmentedChannels.getTertiary().ifPresent(t -> {
				tables.put("ter25", stratify.process(segmentedChannels, t, quarter, "Tertiary_Q", params, imgName));
			});
		}
		
		
		// manage with optional tertiary object
		if(params.getStratifyParameters().getTertiary50()) {
			segmentedChannels.getTertiary().ifPresent(t -> {
				tables.put("ter50", stratify.process(segmentedChannels, t, half, "Tertiary_H", params, imgName));
			});
		}
		
		return tables;
		
	}
}
