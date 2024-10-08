package clcm.focust.utility;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public class XYKillBorders implements KillBorders {

	@Override
	public ClearCLBuffer apply(ClearCLBuffer input) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		long width = input.getWidth();
		long height = input.getHeight();
		long depth = input.getDepth();


		ClearCLBuffer padded = clij2.create(width, height, depth + 2);
		ClearCLBuffer output = clij2.create(input);
		ClearCLBuffer empty = clij2.create(width, height);
		clij2.set(empty, 0.0f);
		
		// pad the top and bottom of the stack before killing borders to preserve objects touching Z borders.
		clij2.paste3D(empty, padded, 0, 0, 0);
		clij2.paste3D(empty, padded, 0, 0, depth + 1);
		
		// paste the input between the padded top and bottom.
		clij2.paste3D(input, padded, 0, 0, 1);
		
		ClearCLBuffer temp = clij2.create(padded);
		
		// kill borders
		clij2.excludeLabelsOnEdges(padded, temp);
		
		// remove the added padding
		clij2.subStack(temp, output, 1, (int) padded.getDepth()-1);
		
		padded.close();
		empty.close();
		
		return output;
	}

}
