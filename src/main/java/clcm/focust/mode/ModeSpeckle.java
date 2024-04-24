package clcm.focust.mode;

import clcm.focust.parameters.ParameterCollection;
import net.haesleinhuepf.clij2.CLIJ2;

public class ModeSpeckle implements Mode {


	@Override
	public void run(ParameterCollection parameters, CompiledImageData imgData, String imgName) {
		/*
		 * TODO:
		 * - Count speckles per nucleus
		 * - Assign a parent to each speckle
		 *
		 * if stratify:
		 * - Count speckles within each band of the parent
		 * - Assign a band of the parent to each speckle
		 */


		// clean up
		CLIJ2 clij2 = CLIJ2.getInstance();
		clij2.clear();
		
	}

}
