package clcm.focust.segmentation.skeleton;

import java.util.HashMap;
import java.util.Map;

import clcm.focust.mode.CompiledImageData;
import clcm.focust.parameters.ParameterCollection;

public class SkeletonProcess {

	public Map<String, SkeletonResultsHolder> run(ParameterCollection params, CompiledImageData imgData) {
		
		Map<String, SkeletonResultsHolder> results = new HashMap<>();
		
		Skeleton skeleton = new Skeleton();
		
		if(params.getSkeletonParamters().getPrimary()) {
			results.put("primary", skeleton.analyzeSkeletons(skeleton.createSkeletons(imgData.getImages().getPrimary()), imgData.getImages().getPrimary(), "IMGNAME"));
		}
		
		if(params.getSkeletonParamters().getSecondary()) {
			results.put("secondary", skeleton.analyzeSkeletons(skeleton.createSkeletons(imgData.getImages().getSecondary()), imgData.getImages().getSecondary(), "IMGNAME"));
		}
		
		if(params.getSkeletonParamters().getTertairy()) {
			imgData.getImages().getTertiary().ifPresent(t -> {
				results.put("teritary", skeleton.analyzeSkeletons(skeleton.createSkeletons(t), t, "IMGNAME"));
			});
		}
		
		
		return results;
	}
	
	
	
}
