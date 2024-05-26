package clcm.focust.threshold;


import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public interface Threshold {

	ClearCLBuffer apply(CLIJ2 clij2, ClearCLBuffer input, double radius);

}
