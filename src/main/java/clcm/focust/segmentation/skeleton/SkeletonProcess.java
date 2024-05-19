package clcm.focust.segmentation.skeleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.parameters.ParameterCollection;
import ij.IJ;
import ij.measure.ResultsTable;

public class SkeletonProcess {

	/**
	 * Skeletonises the user-selected object types and stores the labelled skeletons and results tables mapped to a string of object type.
	 * @param params Parameter collection.
	 * @param segmentedChannels Segmented channels.
	 * @return Map of object type to skeleton results holder.
	 */
	public Map<String, SkeletonResultsHolder> run(ParameterCollection params, SegmentedChannels segmentedChannels, String imgName) {
		
		Map<String, SkeletonResultsHolder> results = new HashMap<>();
		
		Skeleton skeleton = new Skeleton();
		
		if(params.getSkeletonParameters().getPrimary()) {
			IJ.log("Skeletonizing primary objects...");
			long startTime = System.currentTimeMillis();
			results.put("Primary", skeleton.analyzeSkeletons(skeleton.createSkeletons(segmentedChannels.getPrimary().duplicate(), imgName, "Primary"), segmentedChannels.getPrimary(), imgName, segmentedChannels.getPrimary().getCalibration()));
			long endTime = System.currentTimeMillis();
			IJ.log("Completed in:  " + (endTime - startTime)/1000 + " seconds.");
		}
		
		if(params.getSkeletonParameters().getSecondary()) {
			IJ.log("Skeletonizing secondary objects...");
			long startTime = System.currentTimeMillis();
			results.put("Secondary", skeleton.analyzeSkeletons(skeleton.createSkeletons(segmentedChannels.getSecondary().duplicate(), imgName, "Secondary"), segmentedChannels.getSecondary(), imgName, segmentedChannels.getSecondary().getCalibration()));
			long endTime = System.currentTimeMillis();
			IJ.log("Completed in:  " + (endTime - startTime)/1000 + " seconds.");
		}
		
		if(params.getSkeletonParameters().getTertairy()) {
			segmentedChannels.getTertiary().ifPresent(t -> {
				IJ.log("Skeletonizing tertiary objects...");
				long startTime = System.currentTimeMillis();
				results.put("Tertiary", skeleton.analyzeSkeletons(skeleton.createSkeletons(t.duplicate(),imgName, "Tertiary"), t, imgName, t.getCalibration()));
				long endTime = System.currentTimeMillis();
				IJ.log("Completed in:  " + (endTime - startTime)/1000 + " seconds.");
			});
		}


        return renameHeaders(results);
	}
	
	
	/**
	 * Append the object type to the headers of the standard table so it can be appended to the final rt. 
	 * @param results
	 * @return
	 */
	public Map<String, SkeletonResultsHolder> renameHeaders(Map<String, SkeletonResultsHolder> results){
		
		
		
		for (Map.Entry<String, SkeletonResultsHolder> result : results.entrySet()) {
			
			ResultsTable newRt =  new ResultsTable();
			
			SkeletonResultsHolder holder = result.getValue();
			
			ResultsTable rt = holder.getStandard();
			
			List<String> headers = new ArrayList<>(Arrays.asList(rt.getHeadings()));
			
			for (String head : headers) {
				
				String newHead = "Skeleton." + head;
				newRt.setColumn(newHead, rt.getColumnAsVariables(head));
				
			}
			
			holder.setStandard(newRt);
			
			
		}
		
		return results;
	}
	
	
	
}



