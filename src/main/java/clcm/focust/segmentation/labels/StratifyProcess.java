package clcm.focust.segmentation.labels;

import java.util.HashMap;
import java.util.Map;
import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.parameters.ParameterCollection;
import ij.IJ;

public class StratifyProcess {
	
	// switch to determine which label maps to stratify
	// each method generates a single results table
	public Map<String, StratifiedResultsHolder> process(ParameterCollection params, SegmentedChannels segmentedChannels, String imgName) {
		
		Map<String, StratifiedResultsHolder> tables = new HashMap<>();
		
		double quarter = 0.25;
		double half = 0.5;
		
		StratifyAndQuantifyLabels stratify = new StratifyAndQuantifyLabels();
		
		if(params.getStratifyParameters().getPrimary25()) {
			IJ.log("Generating 25 % bands for primary object.");
			
			tables.put("pri25", stratify.process(segmentedChannels, segmentedChannels.getPrimary(), quarter, "Primary_Q_", params, imgName));
		}
		
		if(params.getStratifyParameters().getPrimary50()) {
			IJ.log("Generating 50 % bands for primary object.");
			tables.put("pri50", stratify.process(segmentedChannels, segmentedChannels.getPrimary(), half, "Primary_H_", params, imgName));
		}
		
		
		if(params.getStratifyParameters().getSecondary25()) {
			IJ.log("Generating 25 % bands for secondary object.");
			tables.put("sec25", stratify.process(segmentedChannels, segmentedChannels.getSecondary(), quarter, "Secondary_Q_", params, imgName));
		}
		
		
		if(params.getStratifyParameters().getSecondary50()) {
			IJ.log("Generating 50 % bands for secondary object.");
			tables.put("sec50", stratify.process(segmentedChannels, segmentedChannels.getSecondary(), half, "Secondary_H_", params, imgName));
		}
		
		
		// manage with optional tertiary object
		if(params.getStratifyParameters().getTertiary25()) {
			segmentedChannels.getTertiary().ifPresent(t -> {
				IJ.log("Generating 25 % bands for tertiary object.");
				tables.put("ter25", stratify.process(segmentedChannels, t, quarter, "Tertiary_Q_", params, imgName));
			});
		}
		
		
		// manage with optional tertiary object
		if(params.getStratifyParameters().getTertiary50()) {
			segmentedChannels.getTertiary().ifPresent(t -> {
				IJ.log("Generating 50 % bands for tertiary object.");
				tables.put("ter50", stratify.process(segmentedChannels, t, half, "Tertiary_H_", params, imgName));
			});
		}
		
		return tables;
		
	}
}
