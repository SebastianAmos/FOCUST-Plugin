package clcm.focust.filter;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public class GaussianBlur implements Filter {

	@Override
	public ClearCLBuffer apply(ClearCLBuffer input, Vector3D v1, Vector3D v2) {
		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer filtered = clij2.create(input);
		clij2.gaussianBlur3D(input, filtered, v1.getX(), v1.getY(), v1.getZ());
		return filtered;
	}
	
	
}
