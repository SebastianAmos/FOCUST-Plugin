package clcm.focust.segmentation.skeleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.parameters.ParameterCollection;
import ij.ImagePlus;
import ij.measure.ResultsTable;

public class SkeletonProcess {

	/**
	 * Skeletonises the user-selected object types and stores the labelled skeletons and results tables mapped to a string of object type.
	 * @param params
	 * @param segmentedChannels
	 * @return
	 */
	public Map<String, SkeletonResultsHolder> run(ParameterCollection params, SegmentedChannels segmentedChannels, String imgName) {
		
		System.out.println("Primary skeletons: " + params.getSkeletonParamters().getPrimary());
		System.out.println("Secondary skeletons: " + params.getSkeletonParamters().getSecondary());
		
		Map<String, SkeletonResultsHolder> results = new HashMap<>();
		
		Skeleton skeleton = new Skeleton();
		
		if(params.getSkeletonParamters().getPrimary()) {
			
			// Debugging - visualise that the create skeletons method receives
			ImagePlus imP = segmentedChannels.getPrimary().duplicate();
			imP.setTitle(imgName + "Primary_Before_Skeletonization");
			imP.show();
			
			results.put("Primary", skeleton.analyzeSkeletons(skeleton.createSkeletons(segmentedChannels.getPrimary().duplicate(), imgName, "Primary"), segmentedChannels.getPrimary(), imgName));
			
		}
		
		if(params.getSkeletonParamters().getSecondary()) {
			
			// Debugging - visualise that the create skeletons method receives
			ImagePlus imS = segmentedChannels.getSecondary().duplicate();
			imS.setTitle(imgName + "Secondary_Before_Skeletonization");
			imS.show();
			
			results.put("Secondary", skeleton.analyzeSkeletons(skeleton.createSkeletons(segmentedChannels.getSecondary().duplicate(), imgName, "Secondary"), segmentedChannels.getSecondary(), imgName));
			
		}
		
		if(params.getSkeletonParamters().getTertairy()) {
			segmentedChannels.getTertiary().ifPresent(t -> {
				results.put("Teritary", skeleton.analyzeSkeletons(skeleton.createSkeletons(t.duplicate(),imgName, "Tertiary"), t, imgName));
			});
		}
		
		Map<String, SkeletonResultsHolder> output = renameHeaders(results);
		
		
		return output;	
	}
	
	
	/**
	 * Append the object type to the headers of the standard table so it can be appended to the final rt. 
	 * @param results
	 * @return
	 */
	public Map<String, SkeletonResultsHolder> renameHeaders(Map<String, SkeletonResultsHolder> results){
		
		ResultsTable newRt =  new ResultsTable();
		
		for (Map.Entry<String, SkeletonResultsHolder> result : results.entrySet()) {
			
			String key = result.getKey();
			
			SkeletonResultsHolder val = result.getValue();
			
			ResultsTable rt = val.getStandard();
			
			List<String> headers = new ArrayList<>(Arrays.asList(rt.getHeadings()));
			
			for (String head : headers) {
				
				String newHead = key + "." + "Skeleton." + head;
				newRt.setColumn(newHead, rt.getColumnAsVariables(head));
				
			}
			
			val.setStandard(newRt);
			
			
		}
		
		
		
		return results;
	}
	
	
	
}



