package clcm.focust.filter;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public class MeanFilter implements Filter{

	@Override
	public ClearCLBuffer apply(ClearCLBuffer input, Vector3D v1, Vector3D v2) {
		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer threshold = clij2.create(input);
		clij2.mean3DBox(input, threshold, v1.getX(), v1.getY(), v1.getZ());
		return threshold;
	}

}
