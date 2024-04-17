package clcm.focust.segmentation;

import java.util.Map;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.segmentation.skeleton.SkeletonResultsHolder;
import ij.IJ;

public class SaveSkeletons {

	/**
	 * Save the labelled skeletons for each skeleton image that was created.
	 * @param skeletons A map of object type (string) to skeletonResults objects
	 * @param parameters Parameter collection
	 * @param imgName Current image name
	 */
	public void saveSkeletons(Map<String, SkeletonResultsHolder> skeletons, ParameterCollection parameters, String imgName) {
		
		if(!skeletons.isEmpty()) {
			for (Map.Entry<String, SkeletonResultsHolder> skeleton : skeletons.entrySet()) {
				String object = skeleton.getKey();
				SkeletonResultsHolder results = skeleton.getValue();
			
				// Save the labelled skeletons
				if (!parameters.getOutputDir().isEmpty()) {
					
					IJ.saveAs(results.getLabelledSkeletons(), "TIF", parameters.getOutputDir() + object + "_Skeletons_" + imgName);
					
				} else {
					
					IJ.saveAs(results.getLabelledSkeletons(), "TIF", parameters.getInputDir() + object + "_Skeletons_" + imgName);
					
				}
			}
		}
	}
}
