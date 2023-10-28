package clcm.focust;

import clcm.focust.parameters.BackgroundParameters;
import clcm.focust.parameters.FilterParameters;
import clcm.focust.parameters.MethodParameters;
import clcm.focust.parameters.ObjectParameters;
import ij.ImagePlus;

public class Segmentation {
	
	public ImagePlus run(ImagePlus input, ObjectParameters parameters) {
	
		// collect parameter data
		BackgroundParameters background = parameters.getBackgroundParameters();
		FilterParameters filter = parameters.getFilterParameters();
		MethodParameters method = parameters.getMethodParameters();
		
		
		// run selected method with respective parameters
		ImagePlus output = method.getMethodType().getMethod().apply(input, 
				background.getBackgroundType(), 
				filter.getFilterType(), 
				method.getThresholdType(), 
				parameters);
		
		
		return null;
		
	}
	
	
}
