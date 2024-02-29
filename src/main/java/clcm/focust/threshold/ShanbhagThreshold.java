package clcm.focust.threshold;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public class ShanbhagThreshold implements Threshold{

	@Override
	public ClearCLBuffer apply(CLIJ2 clij2, ClearCLBuffer input, double radius) {
		ClearCLBuffer threshold = clij2.create(input);
		clij2.thresholdShanbhag(input, threshold);
		return threshold;
	}

}
