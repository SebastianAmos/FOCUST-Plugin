package clcm.focust.mode;

import java.util.ArrayList;
import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.parameters.ParameterCollection;
import ij.ImagePlus;

public class ModeSpheroid implements Mode {


	@Override
	public void run(ParameterCollection parameters, CompiledImageData imgData, String imgName) {
		
		// Spheroid method will do the following: 
		
				// Count how many nuclei per spheroid
				
				// combine secondary and tertiary results tables (for whole spheroid and cytoplasmic --> ONLY IF NOT SEGMENTING TERTIARY
				
				// Count how many primary objects per band if available 
				// -> reduce nuclei to centroids first then count to limit crossover
				
				// 
		
	}

}
