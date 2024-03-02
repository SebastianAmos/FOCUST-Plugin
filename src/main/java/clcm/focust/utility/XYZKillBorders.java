package clcm.focust.utility;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public class XYZKillBorders implements KillBorders {

	@Override
	public ClearCLBuffer apply(ClearCLBuffer input) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		ClearCLBuffer output = clij2.create(input);
				
		clij2.excludeLabelsOnEdges(input, output);
			
		return output;
	}

}
