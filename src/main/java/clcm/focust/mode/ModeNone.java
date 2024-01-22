package clcm.focust.mode;


import clcm.focust.parameters.ParameterCollection;

public class ModeNone implements Mode {
	
	
	
	/**
	 * Segmentation and save only. No analysis conducted.
	 */
	@Override
	public void run(ParameterCollection parameters, CompiledImageData imgData ,String imgName) {
		
		/* Segmented images have already been saved.
		 * No additional processing to do.
		 * This mode is a placeholder for segmentation only.
		 */
		
	}
}