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
	public ImagePlus apply(ImagePlus imp, BackgroundType background, FilterType filter, ThresholdType threshold,
			ObjectParameters parameters, ParameterCollection parameterCollection) {

		CLIJ2 clij2 = CLIJ2.getInstance();

		ClearCLBuffer input = clij2.push(imp);
		ClearCLBuffer bg = clij2.create(input);
		ClearCLBuffer filtered = clij2.create(input);
		ClearCLBuffer segmented = clij2.create(input);
		ClearCLBuffer killBorders = clij2.create(input);

		bg = background.getFilter().apply(input, parameters.getBackgroundParameters().getSigma1(),
				parameters.getBackgroundParameters().getSigma2());
		filtered = filter.getFilter().apply(bg, parameters.getFilterParameters().getSigma1(),
				parameters.getFilterParameters().getSigma2());

		// Sigma X = spot sigma: how close objects can be .
		// Sigma Y = outline sigma: how precisely segmented objects are outlined.
		clij2.voronoiOtsuLabeling(filtered, segmented, parameters.getMethodParameters().getSigma().getX(),
				parameters.getMethodParameters().getSigma().getY());

		// kill borders
		killBorders = parameterCollection.getKillBorderType().getKillBorders().apply(segmented);

		ImagePlus output = clij2.pull(segmented);
		IJ.run(output, "glasbey inverted", "");

		input.close();
		bg.close();
		filtered.close();
		segmented.close();
		killBorders.close();
		
		return output;
	}

}
