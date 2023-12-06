package clcm.focust.mode;


import clcm.focust.parameters.ParameterCollection;

public class ModeNone implements Mode {
	
	
	
	/**
	 * Segmentation and save only. No analysis conducted.
	 */
	@Override
	public CompiledImageData run(ParameterCollection parameters, String imgName) {
		
		
		return null;
	}
}