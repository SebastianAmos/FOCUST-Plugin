package clcm.focust.segmentation;

import clcm.focust.filter.BackgroundType;
import clcm.focust.filter.FilterType;
import clcm.focust.parameters.ObjectParameters;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.threshold.ThresholdType;
import ij.ImagePlus;

public class MembraneScalar implements Method{

	@Override
	public ImagePlus apply(ImagePlus imp, BackgroundType background, FilterType filter, ThresholdType threshold,
			ObjectParameters parameters, ParameterCollection parameterCollection) {
		
		// subtract img from scalar 
		// top hat
		// threshold DoG
		// parametric watershed
		// conn comp
		// extend by voronoi
		// mask by original threshold of secondary object
		
		
		
		
		
		return null;
	}
	
	
}
