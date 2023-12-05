package clcm.focust.mode;

import java.io.File;

import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.parameters.ParameterCollection;
import ij.IJ;
import ij.ImagePlus;

public class ModeProcess{

	/**
	 * This method is the kick off for image processing. 
	 * Opens images from the directory, passes them to the segment class, then the user-defined mode.
	 * 
	 * @param parameters
	 * @return
	 */
	public void run(ParameterCollection parameters) {
	
		File f = new File(parameters.getInputDir());
		String[] list = f.list();

		for (int i = 0; i < list.length; i++) {

			String path = parameters.getInputDir() + list[i];

			// open image.
			ImagePlus imp = IJ.openImage(path);
			String imgName = imp.getTitle();
			
			// Run the user-specific segmentation for appropriate channels.
			ModeSegment segment = new ModeSegment();
			SegmentedChannels objects = segment.run(parameters, imp);
			
			// Run the selected mode 
			parameters.getMode().getMode().run(parameters);
			
			
			// Results handling? 
		
		
		}
		
		
	}
}
