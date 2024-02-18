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

public class ConnectedComponents implements Method {

	@Override
	public ImagePlus apply(ImagePlus imp, BackgroundType background, FilterType filter, ThresholdType threshold,
			ObjectParameters parameters, ParameterCollection parameterCollection) {

		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer input = clij2.push(imp);
		ClearCLBuffer bg = background.getFilter().apply(input, parameters.getBackgroundParameters().getSigma1(), parameters.getBackgroundParameters().getSigma2());
		ClearCLBuffer filtered = filter.getFilter().apply(bg, parameters.getFilterParameters().getSigma1(), parameters.getFilterParameters().getSigma2());
		ClearCLBuffer thresholdImg = threshold.getThreshold().apply(filtered, parameters.getMethodParameters().getThresholdSize());
		
		ClearCLBuffer labelled = clij2.create(input);
		ClearCLBuffer killBorders = clij2.create(input);
		ClearCLBuffer ordered = clij2.create(input);
		
		clij2.connectedComponentsLabelingBox(thresholdImg, labelled);
		
		// Kill borders
		killBorders = parameterCollection.getKillBorderType().getKillBorders().apply(labelled);
		
		// close label gaps
		clij2.closeIndexGapsInLabelMap(killBorders, ordered);
				
		ImagePlus output = clij2.pull(ordered);
		IJ.run(output, "glasbey inverted", "");
		
		input.close();
		bg.close();
		filtered.close();
		thresholdImg.close();
		labelled.close();
		killBorders.close();
		ordered.close();
		
		return output;
	}

}
