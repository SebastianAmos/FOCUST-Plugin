package clcm.focust.segmentation;

import clcm.focust.filter.BackgroundType;
import clcm.focust.filter.FilterType;
import clcm.focust.parameters.ObjectParameters;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.threshold.ThresholdType;
import ij.ImagePlus;

public class TrainedClassifier implements Method {

	@Override
	public ImagePlus apply(ImagePlus input, BackgroundType background, FilterType filter, ThresholdType threshold, ObjectParameters parameters, ParameterCollection parameterCollection) {
		
		ImagePlus output = new ImagePlus();
	
		
		return output;
	}

	
	
	// Add support for running weka classifer files, 
	// deepimageJ classifers and 
	// stardist models? 
	
	
	
	
	
}
