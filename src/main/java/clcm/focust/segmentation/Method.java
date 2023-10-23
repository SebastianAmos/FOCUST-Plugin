package clcm.focust.segmentation;

import clcm.focust.filter.BackgroundType;
import clcm.focust.filter.FilterType;
import clcm.focust.threshold.ThresholdType;
import ij.ImagePlus;

public interface Method {

	ImagePlus apply(ImagePlus imp, BackgroundType background, FilterType filter, ThresholdType threshold);
	
}
