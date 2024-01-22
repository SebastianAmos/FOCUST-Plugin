package clcm.focust.segmentation.skeleton;

import java.util.HashMap;
import java.util.Map;

import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.parameters.ParameterCollection;

public class SkeletonProcess {

	/**
	 * Skeletonises the user-selected object types and stores the labelled skeletons and results tables mapped to a string of object type.
	 * @param params
	 * @param segmentedChannels
	 * @return
	 */
	public Map<String, SkeletonResultsHolder> run(ParameterCollection params, SegmentedChannels segmentedChannels) {
		
		Map<String, SkeletonResultsHolder> results = new HashMap<>();
		
		Skeleton skeleton = new Skeleton();
		
		if(params.getSkeletonParamters().getPrimary()) {
			results.put("Primary", skeleton.analyzeSkeletons(skeleton.createSkeletons(segmentedChannels.getPrimary()), segmentedChannels.getPrimary(), "IMGNAME"));
		}
		
		if(params.getSkeletonParamters().getSecondary()) {
			results.put("Secondary", skeleton.analyzeSkeletons(skeleton.createSkeletons(segmentedChannels.getSecondary()), segmentedChannels.getSecondary(), "IMGNAME"));
		}
		
		if(params.getSkeletonParamters().getTertairy()) {
			segmentedChannels.getTertiary().ifPresent(t -> {
				results.put("Teritary", skeleton.analyzeSkeletons(skeleton.createSkeletons(t), t, "IMGNAME"));
			});
		}
		
		return results;
		
	}
	

	
	
}
