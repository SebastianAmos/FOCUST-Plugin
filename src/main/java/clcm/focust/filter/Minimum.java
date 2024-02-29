package clcm.focust.filter;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public class Minimum implements Filter{

	@Override
	public ClearCLBuffer apply(CLIJ2 clij2, ClearCLBuffer input, Vector3D v1, Vector3D v2) {
		
		ClearCLBuffer temp = clij2.create(input);
		ClearCLBuffer filtered = clij2.create(input);
		
		clij2.minimum3DSphere(input, temp, v1.getX(), v1.getY(), v1.getZ());
		
		clij2.subtractImages(input, temp, filtered);
		
		return filtered;
	}

}
