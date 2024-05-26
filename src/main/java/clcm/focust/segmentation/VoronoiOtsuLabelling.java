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

public class VoronoiOtsuLabelling implements Method {

	@Override
	public ImagePlus apply(CLIJ2 clij2, ImagePlus imp, BackgroundType background, FilterType filter, ThresholdType threshold,
			ObjectParameters parameters, ParameterCollection parameterCollection) {


		ClearCLBuffer input = clij2.push(imp);
		ClearCLBuffer segmented = clij2.create(input);

		ClearCLBuffer bg = background.getFilter().apply(clij2, input, parameters.getBackgroundParameters().getSigma1(),
				parameters.getBackgroundParameters().getSigma2());
		ClearCLBuffer filtered = filter.getFilter().apply(clij2, bg, parameters.getFilterParameters().getSigma1(),
				parameters.getFilterParameters().getSigma2());

		// Sigma X = spot sigma: how close objects can be .
		// Sigma Y = outline sigma: how precisely segmented objects are outlined.
		clij2.voronoiOtsuLabeling(filtered, segmented, parameters.getMethodParameters().getSigma().getX(),
				parameters.getMethodParameters().getSigma().getY());

		// pull image
		ImagePlus output = Segmentation.pullAndSetDisplay(clij2, segmented, imp.getCalibration(), parameterCollection);

		input.close();
		bg.close();
		filtered.close();
		segmented.close();
		
		return output;
	}

}
