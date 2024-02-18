package clcm.focust.mode;


import clcm.focust.parameters.ParameterCollection;

//TODO what is a mode?
public interface Mode {
	//TODO what does this do to the arguments?
	 void run(ParameterCollection parameters, CompiledImageData imgData, String imgName);
}
