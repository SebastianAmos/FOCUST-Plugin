package clcm.focust;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public class DifferenceOfGaussian {

	public static ClearCLBuffer run(ClearCLBuffer input, double x, double y, double z, double x2, double y2, double z2) {
		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer filtered = clij2.create(input);
		clij2.differenceOfGaussian3D(input, filtered, x, y, z, x2, y2, z2);
		return filtered;
		
		}
	
}
