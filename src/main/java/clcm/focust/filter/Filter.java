package clcm.focust.filter;


import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public interface Filter {

	ClearCLBuffer apply(CLIJ2 clij2, ClearCLBuffer input, Vector3D v1, Vector3D v2);
	
}
