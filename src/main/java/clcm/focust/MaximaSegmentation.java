package clcm.focust;

import ij.ImagePlus;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clijx.morpholibj.MorphoLibJMarkerControlledWatershed;




public class MaximaSegmentation{

	CLIJ2 clij2 = CLIJ2.getInstance();
	

	public ImagePlus run(ImagePlus imp, BackgroundType background, FilterType filter, ThresholdType threshold) {
		
		ClearCLBuffer input = clij2.push(imp);
		
		ClearCLBuffer bg = background.getFilter().apply(input, new Vector3D(0,0,0), new Vector3D(0,0,0));
		ClearCLBuffer filtered = filter.getFilter().apply(bg, new Vector3D(0,0,0), new Vector3D(0,0,0));
		
	
		ClearCLBuffer inverted = clij2.create(input);
		ClearCLBuffer maxima = clij2.create(input);
		ClearCLBuffer labelled = clij2.create(input);
		ClearCLBuffer segmented = clij2.create(input);
	
		
		clij2.invert(filtered, inverted);		
		clij2.detectMaxima3DBox(filtered, maxima, 0, 0, 0); // *** How to pass the gui inputs?
		clij2.labelSpots(maxima, labelled);
		MorphoLibJMarkerControlledWatershed.morphoLibJMarkerControlledWatershed(clij2, inverted, labelled, thresholdOutput, segmented);
		ImagePlus output = clij2.pull(segmented);
		
		clij2.clear();
		
		return output;
	}

}
