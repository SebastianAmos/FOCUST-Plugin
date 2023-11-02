package clcm.focust.mode;

import java.io.File;
import clcm.focust.Segmentation;
import clcm.focust.parameters.ParameterCollection;
import ij.IJ;
import ij.ImagePlus;
import ij.measure.Calibration;
import ij.plugin.ChannelSplitter;
import ij.plugin.ImageCalculator;

public class ModeNone implements Mode {
	
	
	
	/**
	 * Segmentation only. No analysis conducted.
	 */
	@Override
	public void run(ParameterCollection parameters) {

		File f = new File(parameters.getInputDir());
		String[] list = f.list();

		for (int i = 0; i < list.length; i++) {

			String path = parameters.getInputDir() + list[i];

			// open image. 
			// TODO add a listener for new images and log the image name? 
			ImagePlus imp = IJ.openImage(path);
			String imgName = imp.getTitle();
			Calibration cal = imp.getCalibration();

			int numOfChannels = imp.getNChannels();

			// split channels
			ImagePlus[] channels = ChannelSplitter.split(imp);

			// run user-defined segmentation on the correct channel
			ImagePlus primary = Segmentation.run(channels[parameters.getPrimaryObject().getChannel()],
					parameters.getPrimaryObject(),
					parameters);
			ImagePlus secondary = Segmentation.run(channels[parameters.getSecondaryObject().getChannel()],
					parameters.getSecondaryObject(),
					parameters);
			ImagePlus tertiary = null;

			// if tertiary should be processed, run segmentation, otherwise generate by
			// subtraction if selected.
			if (parameters.getProcessTertiary()) {
				tertiary = Segmentation.run(channels[parameters.getTertiaryObject().getChannel()],
						parameters.getTertiaryObject(),
						parameters);
			} else if (parameters.getTertiaryIsDifference()) {
				tertiary = ImageCalculator.run(secondary, primary, "Subtract create stack");
			}
			
			// set cals
			primary.setCalibration(cal);
			secondary.setCalibration(cal);
			
			if(tertiary != null) {
				tertiary.setCalibration(cal);
			}
			
			

			// save images to output directory if available, if not, save to input directory
			// TODO add a listener for image saving a log saved names.
			if (!parameters.getOutputDir().isEmpty()) {
				IJ.saveAs(primary, "TIF", parameters.getOutputDir() + "Primary_Objects" + imgName);
				IJ.saveAs(secondary, "TIF", parameters.getOutputDir() + "Secondary_Objects" + imgName);

				if (tertiary != null) {
					IJ.saveAs(tertiary, "TIF", parameters.getOutputDir() + "Tertiary_Objects" + imgName);
				}
			} else {
				IJ.saveAs(primary, "TIF", parameters.getInputDir() + "Primary_Objects" + imgName);
				IJ.saveAs(secondary, "TIF", parameters.getInputDir() + "Secondary_Objects" + imgName);

				if (tertiary != null) {
					IJ.saveAs(tertiary, "TIF", parameters.getInputDir() + "Tertiary_Objects" + imgName);
				}
			}
		}
	}
}