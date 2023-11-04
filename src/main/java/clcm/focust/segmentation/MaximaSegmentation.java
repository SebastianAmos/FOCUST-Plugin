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
import net.haesleinhuepf.clijx.morpholibj.MorphoLibJMarkerControlledWatershed;

public class MaximaSegmentation implements Method{

	public ImagePlus apply(ImagePlus imp, BackgroundType background, FilterType filter, ThresholdType threshold, ObjectParameters parameters, ParameterCollection parameterCollection) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer input = clij2.push(imp);
		
		ClearCLBuffer bg = background.getFilter().apply(input, parameters.getBackgroundParameters().getSigma1(), parameters.getBackgroundParameters().getSigma2());
		ClearCLBuffer filtered = filter.getFilter().apply(bg, parameters.getFilterParameters().getSigma1(), parameters.getFilterParameters().getSigma2());
		ClearCLBuffer thresholdImg = threshold.getThreshold().apply(filtered, parameters.getMethodParameters().getThresholdSize());
		
		ClearCLBuffer inverted = clij2.create(input);
		ClearCLBuffer maxima = clij2.create(input);
		ClearCLBuffer labelled = clij2.create(input);
		ClearCLBuffer segmented = clij2.create(input);
		ClearCLBuffer killBorders = clij2.create(input);
		
		clij2.invert(filtered, inverted);
		clij2.detectMaxima3DBox(filtered, maxima, 
				parameters.getMethodParameters().getSigma().getX(), 
				parameters.getMethodParameters().getSigma().getY(), 
				parameters.getMethodParameters().getSigma().getZ());
		
		clij2.labelSpots(maxima, labelled);
		MorphoLibJMarkerControlledWatershed.morphoLibJMarkerControlledWatershed(clij2, inverted, labelled, thresholdImg, segmented);
		
		// Kill borders
		killBorders = parameterCollection.getKillBorderType().getKillBorders().apply(segmented);
		
		ImagePlus output = clij2.pull(killBorders);
		IJ.run(output, "glasbey inverted", "");
		
		// clean up GPU without using clij2.clear() - as this will interrupt optimisation workflow and prevent multiple instances.
		input.close();
		bg.close();
		filtered.close();
		thresholdImg.close();
		inverted.close();
		maxima.close();
		labelled.close();
		segmented.close();
		killBorders.close();
		
		return output;
	}


}
