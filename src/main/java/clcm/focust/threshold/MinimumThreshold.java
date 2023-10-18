package clcm.focust.threshold;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public class MinimumThreshold implements Threshold {

	@Override
	public ClearCLBuffer apply(ClearCLBuffer input, double radius) {
		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer threshold = clij2.create(input);
		clij2.thresholdMinimum(input, threshold);
		return threshold;
	}

}
