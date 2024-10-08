package clcm.focust.mode;

import static clcm.focust.utility.SwingIJLoggerUtils.ijLog;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.segmentation.Segmentation;
import ij.IJ;
import ij.ImagePlus;
import ij.measure.Calibration;
import ij.plugin.ChannelSplitter;
import org.apache.commons.io.FilenameUtils;

public class ModeSegment{

	/**
	 * This method runs the user-defined segmentation on the appropriate channels.
	 * If analysis only = T, segmentation is not run, images are opened from input. 
	 * Returns an object that contains calibrated segmented images.
	 * 
	 * @param parameters ParameterCollection
	 * @param imp an ImagePlus object
	 * @param fileName The name of the file.
	 * @return SegmentedChannels object that contains calibrated segmented images.
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

			ijLog("Analysis only mode: Opening images from input directory.");

			// prep file extension
			String rmExtName = FilenameUtils.removeExtension(fileName);

			ijLog("Opening: " + parameters.getInputDir() + ModeConstants.PRIMARY_PREFIX + rmExtName + ".tif");

			primary = IJ.openImage(parameters.getInputDir() + ModeConstants.PRIMARY_PREFIX + rmExtName + ".tif");

			ijLog("Opening: " + parameters.getInputDir() + ModeConstants.SECONDARY_PREFIX + rmExtName + ".tif");
			secondary = IJ.openImage(parameters.getInputDir() + ModeConstants.SECONDARY_PREFIX + rmExtName + ".tif");



			if (parameters.getTertiaryIsDifference()) {
				//tertiary = Optional.ofNullable(ImageCalculator.run(secondary, primary, "Subtract create stack"));
				//tertiary = Optional.ofNullable(LabelEditor.subtractOneFromTwo(primary, secondary));

				ijLog("Generating teritary object by subtraction...");

				tertiary = Optional.of(Segmentation.generateBySubtractionAndRelabel(secondary, primary));
				
				if (!parameters.getOutputDir().isEmpty()) {
					IJ.saveAs(tertiary.get().duplicate(), "TIF", parameters.getOutputDir() + ModeConstants.TERTIARY_PREFIX + imgName);
				} else {
					IJ.saveAs(tertiary.get().duplicate(), "TIF", parameters.getInputDir() + ModeConstants.TERTIARY_PREFIX + imgName);
				}

			}

			if (parameters.getProcessTertiary()) {

				ijLog("Opening: " + parameters.getInputDir() + ModeConstants.TERTIARY_PREFIX + rmExtName + ".tif");
				tertiary = Optional.ofNullable(IJ.openImage(parameters.getInputDir() + ModeConstants.TERTIARY_PREFIX + rmExtName + ".tif"));

			}


		} else {
			
			// analysis-only = false, run the user-defined segmentation.
			
			ijLog("Number of channels: " + channels.length);
			ijLog("Segmenting objects...");
			long startTime = System.currentTimeMillis();



			// Attempt at multithreading segmentation
			ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

			Future<ImagePlus> primaryFuture = executor.submit(() -> Segmentation.run(channels[parameters.getPrimaryObject().getChannel()].duplicate(),
					parameters.getPrimaryObject(),
					parameters));

			Future<ImagePlus> secondaryFuture = executor.submit(() -> Segmentation.run(channels[parameters.getSecondaryObject().getChannel()].duplicate(),
					parameters.getSecondaryObject(),
					parameters));

			try {
				primary = primaryFuture.get();
				secondary = secondaryFuture.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}

			if (parameters.getProcessTertiary()) {
				Future<ImagePlus> tertiaryFuture = executor.submit(() -> Segmentation.run(channels[parameters.getTertiaryObject().getChannel()].duplicate(),
						parameters.getTertiaryObject(),
						parameters));
				try {
					tertiary = Optional.ofNullable(tertiaryFuture.get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}

			} else if (parameters.getTertiaryIsDifference()) {
				tertiary = Optional.of(Segmentation.generateBySubtraction(secondary, primary, parameters));
			}

			executor.shutdown();

			long endTime = System.currentTimeMillis();
			ijLog("Segmented completed in: " + (endTime - startTime)/1000 + " seconds.");




			// Run user-defined segmentation on the correct channel
			/*
			primary = Segmentation.run(channels[parameters.getPrimaryObject().getChannel()].duplicate(),
					parameters.getPrimaryObject(),
					parameters);
			
			secondary = Segmentation.run(channels[parameters.getSecondaryObject().getChannel()].duplicate(),
					parameters.getSecondaryObject(),
					parameters);

			// if tertiary should be processed, run segmentation, otherwise generate by subtraction if selected.
			if (parameters.getProcessTertiary()) {
				tertiary = Optional.ofNullable(Segmentation.run(channels[parameters.getTertiaryObject().getChannel()].duplicate(),
						parameters.getTertiaryObject(),
						parameters));
			} else if (parameters.getTertiaryIsDifference()) {
				tertiary = Optional.ofNullable(Segmentation.generateBySubtraction(secondary, primary, parameters));
			}

			long endTime = System.currentTimeMillis();
			ijLog("Time to run segmentation on a single thread: " + (endTime - startTime)/1000 + "seconds.");

			 */

			// Set calibrations
            assert primary != null;
            primary.setCalibration(cal);
            assert secondary != null;
            secondary.setCalibration(cal);
			tertiary.ifPresent(t -> t.setCalibration(cal));
			
			
			// Save the segmented images
			if (!parameters.getOutputDir().isEmpty()) {
				IJ.saveAs(primary.duplicate(), "TIF", parameters.getOutputDir() + ModeConstants.PRIMARY_PREFIX + imgName);
				IJ.saveAs(secondary.duplicate(), "TIF", parameters.getOutputDir() + ModeConstants.SECONDARY_PREFIX + imgName);
				tertiary.ifPresent(t -> {
					IJ.saveAs(t.duplicate(), "TIF", parameters.getOutputDir() + ModeConstants.TERTIARY_PREFIX + imgName);
				});

			} else {
				IJ.saveAs(primary.duplicate(), "TIF", parameters.getInputDir() + ModeConstants.PRIMARY_PREFIX + imgName);
				IJ.saveAs(secondary.duplicate(), "TIF", parameters.getInputDir() + ModeConstants.SECONDARY_PREFIX + imgName);
				tertiary.ifPresent(t -> {
					IJ.saveAs(t.duplicate(), "TIF", parameters.getInputDir() + ModeConstants.TERTIARY_PREFIX + imgName);
				});
			}

		}

		// Build return data object
		SegmentedChannels segmentedChannels = SegmentedChannels.builder().
				primary(primary).
				secondary(secondary).
				tertiary(tertiary).
				channels(Arrays.asList(channels)).build();


		return segmentedChannels;

	}

}
