package clcm.focust.mode;

import java.util.ArrayList;

import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.parameters.ParameterCollection;
import ij.ImagePlus;

public interface Mode {
	
	SegmentedChannels run(ParameterCollection parameters);
	

}
