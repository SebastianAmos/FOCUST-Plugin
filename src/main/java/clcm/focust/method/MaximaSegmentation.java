package clcm.focust.method;

import clcm.focust.filter.BackgroundType;
import clcm.focust.filter.FilterType;
import clcm.focust.filter.Vector3D;
import clcm.focust.threshold.ThresholdType;
import ij.ImagePlus;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clijx.morpholibj.MorphoLibJMarkerControlledWatershed;

public class MaximaSegmentation implements Method{

	public ImagePlus apply(ImagePlus imp, BackgroundType background, FilterType filter, ThresholdType threshold) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer input = clij2.push(imp);
		
		ClearCLBuffer bg = background.getFilter().apply(input, new Vector3D(2,2,2), new Vector3D(0,0,0));
		ClearCLBuffer filtered = filter.getFilter().apply(bg, new Vector3D(0,0,0), new Vector3D(0,0,0));
		ClearCLBuffer thresholdImg = threshold.getThreshold().apply(filtered, 0);
		
		ClearCLBuffer inverted = clij2.create(input);
		ClearCLBuffer maxima = clij2.create(input);
		ClearCLBuffer labelled = clij2.create(input);
		ClearCLBuffer segmented = clij2.create(input);
		
		clij2.invert(filtered, inverted);
		clij2.detectMaxima3DBox(filtered, maxima, 10, 10, 10); 
		
		clij2.labelSpots(maxima, labelled);
		MorphoLibJMarkerControlledWatershed.morphoLibJMarkerControlledWatershed(clij2, inverted, labelled, thresholdImg, segmented);
		ImagePlus output = clij2.pull(segmented);
		
		clij2.clear();
		
		return output;
	}


}
