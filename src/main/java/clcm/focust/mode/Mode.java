package clcm.focust.mode;

import java.util.ArrayList;

import clcm.focust.ParamTest;
import ij.ImagePlus;

public interface Mode {
	
	void run(ParamTest params, ArrayList<ImagePlus> channels);
	

}
