package clcm.focust.segmentation;

import clcm.focust.filter.BackgroundType;
import clcm.focust.filter.FilterType;
import clcm.focust.parameters.ObjectParameters;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.threshold.ThresholdType;
import ij.ImagePlus;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public class ExtendByVoronoi implements Method {

	@Override
	public ImagePlus apply(CLIJ2 clij2, ImagePlus imp, BackgroundType background, FilterType filter, ThresholdType threshold, ObjectParameters parameters, ParameterCollection parameterCollections) {
	
		
		ClearCLBuffer input = clij2.push(imp);
		ClearCLBuffer thresholded = clij2.create(input);
		ClearCLBuffer voronoi = clij2.create(input);
		ClearCLBuffer filtered = clij2.create(input);
		ClearCLBuffer temp = clij2.create(input);
		
		// Extend nuclei by voronoi  
		
		
		ImagePlus output = clij2.pull(temp);
		
		return output;
	}

}
