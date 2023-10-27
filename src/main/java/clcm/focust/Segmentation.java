package clcm.focust;

import ij.ImagePlus;

public class Segmentation {
	
	public ImagePlus run(ImagePlus input, ParamTest parameters) {
		
		ImagePlus output = parameters.methodType.getMethod().apply(input, parameters.backgroundType, parameters.filterType, parameters.thresholdType, parameters);
		
		return output;
		
	}
	
}
