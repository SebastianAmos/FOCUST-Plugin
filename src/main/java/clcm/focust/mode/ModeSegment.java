package clcm.focust.mode;

import java.util.Arrays;
import java.util.Optional;

import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.segmentation.Segmentation;
import ij.IJ;
import ij.ImagePlus;
import ij.measure.Calibration;
import ij.plugin.ChannelSplitter;
import ij.plugin.ImageCalculator;

public class ModeSegment{

	
	/**
	 * This method runs the user-defined segmentation on the appropriate channels.
	 * Returns an object that contains calibrated segmented images.
	 * 
	 * @param parameters
	 * @param imp
	 * @return
	 */
	public SegmentedChannels run(ParameterCollection parameters, ImagePlus imp) {

			// Get image info
			String imgName = imp.getTitle();
			Calibration cal = imp.getCalibration();


			// Split channels
			ImagePlus[] channels = ChannelSplitter.split(imp);

			// Run user-defined segmentation on the correct channel
			ImagePlus primary = Segmentation.run(channels[parameters.getPrimaryObject().getChannel()],
					parameters.getPrimaryObject(),
					parameters);
			ImagePlus secondary = Segmentation.run(channels[parameters.getSecondaryObject().getChannel()],
					parameters.getSecondaryObject(),
					parameters);
			Optional<ImagePlus> tertiary = Optional.empty();

			
			// if tertiary should be processed, run segmentation, otherwise generate by
			// subtraction if selected.
			if (parameters.getProcessTertiary()) {
				tertiary = Optional.ofNullable(Segmentation.run(channels[parameters.getTertiaryObject().getChannel()],
						parameters.getTertiaryObject(),
						parameters));
			} else if (parameters.getTertiaryIsDifference()) {
				tertiary = Optional.ofNullable(ImageCalculator.run(secondary, primary, "Subtract create stack"));
			}
			
			
			// Set calibrations
			primary.setCalibration(cal);
			secondary.setCalibration(cal);
			tertiary.ifPresent(t -> t.setCalibration(cal));
			
			
			// Save the segmented images
			if (!parameters.getOutputDir().isEmpty()) {
				IJ.saveAs(primary, "TIF", parameters.getOutputDir() + "Primary_Objects" + imgName);
				IJ.saveAs(secondary, "TIF", parameters.getOutputDir() + "Secondary_Objects" + imgName);
				tertiary.ifPresent(t -> {
					IJ.saveAs(t, "TIF", parameters.getOutputDir() + "Tertiary_Objects" + imgName);
				});
				
			} else {
				IJ.saveAs(primary, "TIF", parameters.getInputDir() + "Primary_Objects" + imgName);
				IJ.saveAs(secondary, "TIF", parameters.getInputDir() + "Secondary_Objects" + imgName);
				tertiary.ifPresent(t -> {
					IJ.saveAs(t, "TIF", parameters.getInputDir() + "Tertiary_Objects" + imgName);
				});
			}
			
			
			// Build return data object
			SegmentedChannels segChannels = SegmentedChannels.builder().
					parameterCollection(parameters).
					primary(primary).
					secondary(secondary).
					tertiary(tertiary).
					channels(Arrays.asList(channels)).build();
			
			return segChannels;

	}
	
}
