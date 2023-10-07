package clcm.focust;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public class TopHat {

	public static ClearCLBuffer run(ClearCLBuffer input, double x, double y, double z) {
		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer filtered = clij2.create(input);
		clij2.topHatBox(input, filtered, x, y, z);
		return filtered;
		
		}
	
}
