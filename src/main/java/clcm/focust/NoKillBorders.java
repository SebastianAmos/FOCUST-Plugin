package clcm.focust;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;

public class NoKillBorders implements KillBorders {

	@Override
	public ClearCLBuffer apply(ClearCLBuffer input) {
		return input;
	}

}
