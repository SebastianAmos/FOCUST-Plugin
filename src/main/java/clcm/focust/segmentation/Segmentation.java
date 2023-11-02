package clcm.focust.segmentation;

import clcm.focust.parameters.BackgroundParameters;
import clcm.focust.parameters.FilterParameters;
import clcm.focust.parameters.MethodParameters;
import clcm.focust.parameters.ObjectParameters;
import clcm.focust.parameters.ParameterCollection;
import ij.ImagePlus;

public class Segmentation {
	
	public static ImagePlus run(ImagePlus input, ObjectParameters parameters, ParameterCollection parameterCollection) {
	
		// collect parameter data
		BackgroundParameters background = parameters.getBackgroundParameters();
		FilterParameters filter = parameters.getFilterParameters();
		MethodParameters method = parameters.getMethodParameters();
		
		
		// run selected method with respective parameters
		ImagePlus output = method.getMethodType().getMethod().apply(input, 
				background.getBackgroundType(), 
				filter.getFilterType(), 
				method.getThresholdType(), 
				parameters,
				parameterCollection);
		
		return output;
		
	}
	
	
}
