package clcm.focust.mode;

import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.parameters.ParameterCollection;


public interface Mode {
	
	SegmentedChannels run(ParameterCollection parameters);
	

}
