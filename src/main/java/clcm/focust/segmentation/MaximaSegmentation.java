package clcm.focust.segmentation;

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
		
		
		ClearCLBuffer bg = background.getFilter().apply(input, Vector3D.builder().x(3).y(3).z(3).build(), Vector3D.builder().x(0).y(0).z(0).build());
		ClearCLBuffer filtered = filter.getFilter().apply(bg, Vector3D.builder().x(0).y(0).z(0).build(), Vector3D.builder().x(0).y(0).z(0).build());
		ClearCLBuffer thresholdImg = threshold.getThreshold().apply(filtered, 0);
		
		ClearCLBuffer inverted = clij2.create(input);
		ClearCLBuffer maxima = clij2.create(input);
		ClearCLBuffer labelled = clij2.create(input);
		ClearCLBuffer segmented = clij2.create(input);
		
		clij2.invert(filtered, inverted);
		clij2.detectMaxima3DBox(filtered, maxima, 30, 30, 30); 
		
		clij2.labelSpots(maxima, labelled);
		MorphoLibJMarkerControlledWatershed.morphoLibJMarkerControlledWatershed(clij2, inverted, labelled, thresholdImg, segmented);
		ImagePlus output = clij2.pull(segmented);
		
		clij2.clear();
		
		return output;
	}


}
