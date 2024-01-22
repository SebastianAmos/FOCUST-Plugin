package clcm.focust.mode;

import java.io.File;
import java.util.Map;
import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.segmentation.SaveSkeletons;
import clcm.focust.segmentation.labels.StratifyProcess;
import clcm.focust.segmentation.skeleton.SkeletonProcess;
import clcm.focust.segmentation.skeleton.SkeletonResultsHolder;
import ij.IJ;
import ij.ImagePlus;
import ij.measure.ResultsTable;

import static clcm.focust.SwingIJLoggerUtils.ijLog;

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
		ijLog("Number of images to process: " + list.length);

		for (int i = 0; i < list.length; i++) {
			
			String path = parameters.getInputDir() + list[i];

			// Open image.
			ijLog("Opening image path: " + path);
			ImagePlus imp = IJ.openImage(path);
			String imgName = imp.getTitle();
			
			// Run the user-specified segmentation for appropriate channels.
			ModeSegment segment = new ModeSegment();
			SegmentedChannels objects = segment.run(parameters, imp);
		
			
			// Generate skeletons based on user inputs and save
			SkeletonProcess skeletonize = new SkeletonProcess();
			Map<String, SkeletonResultsHolder> skeletonResults = skeletonize.run(parameters, objects);
			SaveSkeletons saveSkeletons = new SaveSkeletons();
			saveSkeletons.saveSkeletons(skeletonResults, parameters, imgName);
			
			
			// Generate stratified bands - bands are saved within
			StratifyProcess stratify = new StratifyProcess();
			Map<String, ResultsTable> stratifyResults = stratify.process(parameters, objects, imgName);
			
			
			// Build compiledImageData object.
			CompiledImageData imgData = CompiledImageData.builder().
					images(objects).
					skeletons(skeletonResults).
					stratifyResults(stratifyResults).
					build();
			
			
			// Run the selected mode
			// Each analysis method called within a mode should check for empty results tables and mark with na to improve data awareness.
			parameters.getMode().getMode().run(parameters, imgData, imgName);
			
			
			
		
		}
	}
}
