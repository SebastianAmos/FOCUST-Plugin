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
import org.apache.commons.io.FilenameUtils;
import static clcm.focust.SwingIJLoggerUtils.ijLog;

public class ModeSegment{
	private static String primaryPrefix = "Primary_Objects_";
	private static String secondaryPrefix = "Secondary_Objects_";
	private static String tertiaryPrefix = "Tertiary_Objects_";

	/**
	 * This method runs the user-defined segmentation on the appropriate channels.
	 * If analysis only = T, segmentation is not run, images are opened from input. 
	 * Returns an object that contains calibrated segmented images.
	 * 
	 * @param parameters
	 * @param imp
	 * @return
	 */
	public SegmentedChannels run(ParameterCollection parameters, ImagePlus imp, String fileName) {
		
		// Get image info
		String imgName = imp.getTitle();
		Calibration cal = imp.getCalibration();
		// Split channels
		ImagePlus[] channels = ChannelSplitter.split(imp);
		
		ImagePlus primary = null;
		ImagePlus secondary = null;
		Optional<ImagePlus> tertiary = Optional.empty();	
		
		// open images if analysis-only = true
		if(parameters.getAnalysisOnly()) {
			
			// prep file extension
			String rmExtName = FilenameUtils.removeExtension(fileName);
			
			IJ.log("Opening: " + parameters.getInputDir() + primaryPrefix + rmExtName + ".tif");
			
			primary = IJ.openImage(parameters.getInputDir() + primaryPrefix + rmExtName + ".tif");
			
			IJ.log("Opening: " + parameters.getInputDir() + secondaryPrefix + rmExtName + ".tif");
			secondary = IJ.openImage(parameters.getInputDir() + secondaryPrefix + rmExtName + ".tif");
			
			if (parameters.getProcessTertiary()) {
				tertiary = Optional.ofNullable(IJ.openImage(parameters.getInputDir() + tertiaryPrefix + rmExtName + ".tif"));
			} else if (parameters.getTertiaryIsDifference()) {
				tertiary = Optional.ofNullable(ImageCalculator.run(secondary, primary, "Subtract create stack"));
			} 
			
		} else {
			
			// analysis-only = false, run the user-defined segmentation.
			
			ijLog("Number of channels: " + channels.length);
			
			// Run user-defined segmentation on the correct channel
			primary = Segmentation.run(channels[parameters.getPrimaryObject().getChannel()],
					parameters.getPrimaryObject(),
					parameters);
			
			secondary = Segmentation.run(channels[parameters.getSecondaryObject().getChannel()],
					parameters.getSecondaryObject(),
					parameters);

			// if tertiary should be processed, run segmentation, otherwise generate by subtraction if selected.
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
				IJ.saveAs(primary.duplicate(), "TIF", parameters.getOutputDir() + "Primary_Objects_" + imgName);
				IJ.saveAs(secondary.duplicate(), "TIF", parameters.getOutputDir() + "Secondary_Objects_" + imgName);
				tertiary.ifPresent(t -> {
					IJ.saveAs(t.duplicate(), "TIF", parameters.getOutputDir() + "Tertiary_Objects_" + imgName);
				});

			} else {
				IJ.saveAs(primary.duplicate(), "TIF", parameters.getInputDir() + "Primary_Objects_" + imgName);
				IJ.saveAs(secondary.duplicate(), "TIF", parameters.getInputDir() + "Secondary_Objects_" + imgName);
				tertiary.ifPresent(t -> {
					IJ.saveAs(t.duplicate(), "TIF", parameters.getInputDir() + "Tertiary_Objects_" + imgName);
				});
			}

		}

		System.out.println("IN MODE SEGMENT: ");
		if (primary == null) {
			System.out.println("Primary is NULL");
		} else { 
			System.out.println("Primary is NOT NULL");
		}
		
		if (secondary == null) {
			System.out.println("Secondary is NULL");
		} else { 
			System.out.println("Secondary is NOT NULL");
		}

		
		
		
		ImagePlus pTest = primary.duplicate();
		pTest.setTitle("segmentedPrimary");
		ImagePlus sTest = secondary.duplicate();
		sTest.setTitle("segmentedSecondary");
		
		
		
		System.out.println("Primary Image Title: " + (pTest != null ? pTest.getTitle() : "null"));
		System.out.println("Secondary Image Title: " + (sTest != null ? sTest.getTitle() : "null"));

		
		// Build return data object
		SegmentedChannels segmentedChannels = SegmentedChannels.builder().
				primary(primary).
				secondary(secondary).
				tertiary(tertiary).
				channels(Arrays.asList(channels)).build();

		ijLog("Segmentation Complete.");

		return segmentedChannels;

	}

}
