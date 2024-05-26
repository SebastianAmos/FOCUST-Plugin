package clcm.focust.filter;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public class DifferenceOfGaussian implements Filter {

	
	@Override
	public ClearCLBuffer apply(CLIJ2 clij2, ClearCLBuffer input, Vector3D v1, Vector3D v2) {
		ClearCLBuffer filtered = clij2.create(input);
		clij2.differenceOfGaussian3D(input, filtered, v1.getX(), v1.getY(), v1.getZ(), v2.getX(), v2.getY(), v2.getZ());
		return filtered;
	
	}
}
