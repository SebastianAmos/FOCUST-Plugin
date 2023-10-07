package clcm.focust;


import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;

public interface Threshold {

	ClearCLBuffer threshold(ClearCLBuffer input, double radius);

}
