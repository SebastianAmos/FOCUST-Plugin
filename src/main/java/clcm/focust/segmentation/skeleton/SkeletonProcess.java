package clcm.focust.segmentation.skeleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.parameters.ParameterCollection;
import ij.measure.ResultsTable;

public class SkeletonProcess {

	/**
	 * Skeletonises the user-selected object types and stores the labelled skeletons and results tables mapped to a string of object type.
	 * @param params
	 * @param segmentedChannels
	 * @return
	 */
	public Map<String, SkeletonResultsHolder> run(ParameterCollection params, SegmentedChannels segmentedChannels, String imgName) {
		
		Map<String, SkeletonResultsHolder> results = new HashMap<>();
		
		Skeleton skeleton = new Skeleton();
		
		if(params.getSkeletonParameters().getPrimary()) {
			
			results.put("Primary", skeleton.analyzeSkeletons(skeleton.createSkeletons(segmentedChannels.getPrimary().duplicate(), imgName, "Primary"), segmentedChannels.getPrimary(), imgName));
			
		}
		
		if(params.getSkeletonParameters().getSecondary()) {

			results.put("Secondary", skeleton.analyzeSkeletons(skeleton.createSkeletons(segmentedChannels.getSecondary().duplicate(), imgName, "Secondary"), segmentedChannels.getSecondary(), imgName));
			
		}
		
		if(params.getSkeletonParameters().getTertairy()) {
			segmentedChannels.getTertiary().ifPresent(t -> {
				results.put("Tertiary", skeleton.analyzeSkeletons(skeleton.createSkeletons(t.duplicate(),imgName, "Tertiary"), t, imgName));
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
		
		
		
		for (Map.Entry<String, SkeletonResultsHolder> result : results.entrySet()) {
			
			ResultsTable newRt =  new ResultsTable();
			
			String objectName = result.getKey();
			
			SkeletonResultsHolder holder = result.getValue();
			
			ResultsTable rt = holder.getStandard();
			
			List<String> headers = new ArrayList<>(Arrays.asList(rt.getHeadings()));
			
			for (String head : headers) {
				
				String newHead = objectName + "." + "Skeleton." + head;
				newRt.setColumn(newHead, rt.getColumnAsVariables(head));
				
			}
			
			holder.setStandard(newRt);
			
			
		}
		
		return results;
	}
	
	
	
}



