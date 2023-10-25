package clcm.focust.method;

import clcm.focust.filter.BackgroundType;
import clcm.focust.filter.FilterType;
import clcm.focust.threshold.ThresholdType;
import ij.ImagePlus;

public interface Method {

	ImagePlus apply(ImagePlus input, BackgroundType background, FilterType filter, ThresholdType threshold);
	
}
