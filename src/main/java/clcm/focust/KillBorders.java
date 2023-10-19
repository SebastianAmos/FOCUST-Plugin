package clcm.focust;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;

public interface KillBorders {

	ClearCLBuffer apply(ClearCLBuffer input);
	
}
