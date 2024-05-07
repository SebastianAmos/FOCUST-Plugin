package clcm.focust.segmentation;

import ij.ImagePlus;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clijx.morpholibj.MorphoLibJMarkerControlledWatershed;

public class MaximaTest {
	
	public static void apply(ImagePlus imp) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		ClearCLBuffer input = clij2.push(imp);
		ClearCLBuffer filtered = clij2.create(input);
		ClearCLBuffer inverted = clij2.create(input);
		ClearCLBuffer maxima = clij2.create(input);
		ClearCLBuffer labels = clij2.create(input);
		ClearCLBuffer threshold = clij2.create(input);
		ClearCLBuffer output = clij2.create(input);
		
		clij2.gaussianBlur3D(input, filtered, 2, 2, 2);
		clij2.invert(filtered, inverted);
		clij2.detectMaxima3DBox(filtered, maxima, 10, 10, 10);
		clij2.labelSpots(maxima, labels);
		clij2.thresholdOtsu(filtered, threshold);
		MorphoLibJMarkerControlledWatershed.morphoLibJMarkerControlledWatershed(clij2, inverted, labels, threshold, output);


		ImagePlus img = clij2.pull(output);
		img.show();

	}

}
