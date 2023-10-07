package clcm.focust;


import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;

public interface Filter {

	ClearCLBuffer apply(ClearCLBuffer input, Vector3D v1, Vector3D v2);
	
}
