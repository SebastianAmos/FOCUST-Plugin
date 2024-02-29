package clcm.focust.segmentation;

import clcm.focust.filter.BackgroundType;
import clcm.focust.filter.FilterType;
import clcm.focust.parameters.ObjectParameters;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.threshold.ThresholdType;
import ij.IJ;
import ij.ImagePlus;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clijx.morpholibj.MorphoLibJClassicWatershed;

public class ClassicWatershed implements Method {

	@Override
	public ImagePlus apply(CLIJ2 clij2, ImagePlus imp, BackgroundType background, FilterType filter, ThresholdType threshold,
			ObjectParameters parameters, ParameterCollection parameterCollection) {
		
		ClearCLBuffer input = clij2.push(imp);
		
		ClearCLBuffer bg = background.getFilter().apply(clij2, input, parameters.getBackgroundParameters().getSigma1(), parameters.getBackgroundParameters().getSigma2());
		ClearCLBuffer filtered = filter.getFilter().apply(clij2, bg, parameters.getFilterParameters().getSigma1(), parameters.getFilterParameters().getSigma2());
		ClearCLBuffer thresholdImg = threshold.getThreshold().apply(clij2, filtered, parameters.getMethodParameters().getThresholdSize());
		
		ClearCLBuffer segmented = clij2.create(input);

		
		// method - casting doubles to float.
		/*
		 * sigma x = h-min
		 * sigma y = h-max
		 * sigma z = NOT USED
		 */
		
		float hMin = (float) parameters.getMethodParameters().getSigma().getX();
		float hMax = (float) parameters.getMethodParameters().getSigma().getY();
		
		MorphoLibJClassicWatershed.morphoLibJClassicWatershed(clij2, filtered, thresholdImg, segmented, hMin, hMax);
		
		
		// pull image
		ImagePlus output = Segmentation.pullAndSetDisplay(clij2, segmented, imp.getCalibration(), parameterCollection);
		
		// clean up
		input.close();
		bg.close();
		filtered.close();
		thresholdImg.close();
		segmented.close();

		return output;
	}

}
