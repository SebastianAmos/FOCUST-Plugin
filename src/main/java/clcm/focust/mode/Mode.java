package clcm.focust.mode;


import clcm.focust.parameters.ParameterCollection;


public interface Mode {
	
	 void run(ParameterCollection parameters, CompiledImageData imgData, String imgName);
	
}
