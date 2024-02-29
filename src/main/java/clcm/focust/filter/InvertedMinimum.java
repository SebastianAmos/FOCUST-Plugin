package clcm.focust.filter;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public class InvertedMinimum implements Filter {

	@Override
	public ClearCLBuffer apply(CLIJ2 clij2, ClearCLBuffer input, Vector3D v1, Vector3D v2) {
		Minimum minimum = new Minimum();
		
		ClearCLBuffer invert = clij2.create(input);
		ClearCLBuffer min = clij2.create(input);
		ClearCLBuffer filtered = clij2.create(input);
		
		clij2.invert(input, invert);
		min = minimum.apply(clij2, invert, v1, v2);
		clij2.subtractImages(input, min, filtered);
		
		return filtered;
		
	}

}