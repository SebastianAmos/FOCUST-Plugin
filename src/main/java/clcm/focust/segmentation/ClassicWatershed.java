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
	public ImagePlus apply(ImagePlus imp, BackgroundType background, FilterType filter, ThresholdType threshold,
			ObjectParameters parameters, ParameterCollection parameterCollection) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer input = clij2.push(imp);
		
		ClearCLBuffer bg = background.getFilter().apply(input, parameters.getBackgroundParameters().getSigma1(), parameters.getBackgroundParameters().getSigma2());
		ClearCLBuffer filtered = filter.getFilter().apply(bg, parameters.getFilterParameters().getSigma1(), parameters.getFilterParameters().getSigma2());
		ClearCLBuffer thresholdImg = threshold.getThreshold().apply(filtered, parameters.getMethodParameters().getThresholdSize());
		
		ClearCLBuffer segmented = clij2.create(input);
		ClearCLBuffer killBorders = clij2.create(input);
		
		// method - casting doubles to float.
		/*
		 * sigma x = h-min
		 * sigma y = h-max
		 * sigma z = NOT USED
		 */
		
		float hMin = (float) parameters.getMethodParameters().getSigma().getX();
		float hMax = (float) parameters.getMethodParameters().getSigma().getY();
		
		MorphoLibJClassicWatershed.morphoLibJClassicWatershed(clij2, filtered, thresholdImg, segmented, hMin, hMax);
		
		// kill borders
		killBorders = parameterCollection.getKillBorderType().getKillBorders().apply(segmented);
	
		// pull output
		ImagePlus output = clij2.pull(killBorders);
		IJ.run(output, "glasey inverted", "");
		
	
		// clean up
		input.close();
		bg.close();
		filtered.close();
		thresholdImg.close();
		segmented.close();
		killBorders.close();
		
	

		
		return output;
	}

}
