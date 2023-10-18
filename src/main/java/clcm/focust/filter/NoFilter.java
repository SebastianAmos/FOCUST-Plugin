package clcm.focust.filter;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;

public class NoFilter implements Filter {

	@Override
	public ClearCLBuffer apply(ClearCLBuffer input, Vector3D v1, Vector3D v2) {
		
		return input;
	}

}
