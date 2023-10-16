package clcm.focust.filter;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clijx.imagej2.ImageJ2Tubeness;

public class Tubeness implements Filter{

	@Override
	public ClearCLBuffer apply(ClearCLBuffer input, Vector3D v1, Vector3D v2) {
		CLIJ2 clij2 = CLIJ2.getInstance();
		ImageJ2Tubeness ij2Tubeness = new ImageJ2Tubeness();
		
		ClearCLBuffer filtered = clij2.create(input);
		
		// sigma 
		float xFloat = (float)v1.getX();
		
		ij2Tubeness.imageJ2Tubeness(clij2, input, filtered, xFloat, 0f, 0f, 0f);
		
		return filtered;
	}

}
