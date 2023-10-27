package clcm.focust.segmentation;

import clcm.focust.ParamTest;
import clcm.focust.filter.BackgroundType;
import clcm.focust.filter.FilterType;
import clcm.focust.threshold.ThresholdType;
import ij.ImagePlus;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clijx.morpholibj.MorphoLibJMarkerControlledWatershed;

public class MaximaSegmentation implements Method{

	public ImagePlus apply(ImagePlus imp, BackgroundType background, FilterType filter, ThresholdType threshold, ParamTest params) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer input = clij2.push(imp);
		
		ClearCLBuffer bg = background.getFilter().apply(input, params.getBackgroundS1(), params.getBackgroundS2());
		ClearCLBuffer filtered = filter.getFilter().apply(bg, params.getFilterS1(), params.getFilterS2());
		ClearCLBuffer thresholdImg = threshold.getThreshold().apply(filtered, params.getThresholdSize());
		
		ClearCLBuffer inverted = clij2.create(input);
		ClearCLBuffer maxima = clij2.create(input);
		ClearCLBuffer labelled = clij2.create(input);
		ClearCLBuffer segmented = clij2.create(input);
		
		clij2.invert(filtered, inverted);
		clij2.detectMaxima3DBox(filtered, maxima, params.getSigmaMethod().getX(), params.getSigmaMethod().getY(), params.getSigmaMethod().getZ()); 
		
		clij2.labelSpots(maxima, labelled);
		MorphoLibJMarkerControlledWatershed.morphoLibJMarkerControlledWatershed(clij2, inverted, labelled, thresholdImg, segmented);
		ImagePlus output = clij2.pull(segmented);
		
		// clean up GPU without using clij2.clear() - as this will interrupt optimisation workflow and prevent multiple instances.
		bg.close();
		filtered.close();
		thresholdImg.close();
		inverted.close();
		maxima.close();
		labelled.close();
		segmented.close();
		
		return output;
	}


}
