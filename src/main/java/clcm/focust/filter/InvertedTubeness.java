package clcm.focust.filter;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public class InvertedTubeness implements Filter{

	@Override
	public ClearCLBuffer apply(CLIJ2 clij2, ClearCLBuffer input, Vector3D v1, Vector3D v2) {
		
		Tubeness tubeness = new Tubeness();
		
		ClearCLBuffer invert = clij2.create(input);
		ClearCLBuffer tube = clij2.create(input);
		ClearCLBuffer temp = clij2.create(input);
		ClearCLBuffer filtered = clij2.create(input);
		
		clij2.invert(input, invert);
		tube = tubeness.apply(clij2, invert, v1, v2);
		
		// Bump intensity
		clij2.multiplyImageAndScalar(tube, temp, 2);
		clij2.subtractImages(input, temp, filtered);
		
		return filtered;
	}

}