package clcm.focust.threshold;


import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;

public interface Threshold {

	ClearCLBuffer apply(ClearCLBuffer input, double radius);

}
