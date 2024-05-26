package clcm.focust.mode;

import static clcm.focust.mode.ModeConstants.*;
import static clcm.focust.utility.SwingIJLoggerUtils.ijLog;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.segmentation.SaveSkeletons;
import clcm.focust.segmentation.labels.StratifiedResultsHolder;
import clcm.focust.segmentation.labels.StratifyProcess;
import clcm.focust.segmentation.skeleton.SkeletonProcess;
import clcm.focust.segmentation.skeleton.SkeletonResultsHolder;
import clcm.focust.utility.Timer;
import ij.IJ;
import ij.ImagePlus;
import net.haesleinhuepf.clij2.CLIJ2;


public class ModeProcess{

	/**
	 * This method is the kick off for image processing.
	 * Opens images from the directory, passes them to the segment class, then the user-defined mode.
	 * 
	 * @param parameters Parameter collection
	 */
	
	public void run(ParameterCollection parameters) {

		ijLog("FOCUST: Running in " + parameters.getMode().toString() + " mode.");
		Timer.setTotalTime(0);

		File f = new File(parameters.getInputDir());
		String[] list = f.list();
        assert list != null;

		// Save parameter file.
		try {
			ParameterCollection.saveParameterCollection(parameters, "/FOCUST-Parameter-File.json");
			ijLog("FOCUST parameter file saved.");
		} catch (IOException e1) {
			System.out.println("Unable to save FOCUST parameter file.");
			e1.printStackTrace();
		}

		for (int i = 0; i < list.length; i++) {

			Timer.start(); // start timer for each image. Total time is accumulated.

			CLIJ2 clij2 = CLIJ2.getInstance();
			clij2.clear();

			// if analysis-only mode: filter the list to remove objects and only list original images.
			if (parameters.getAnalysisOnly()) {
				ArrayList<String> tempList = new ArrayList<>();
				for (String imgName : list) {
					if (!imgName.startsWith(PRIMARY_PREFIX) && !imgName.startsWith(SECONDARY_PREFIX) && !imgName.startsWith(TERTIARY_PREFIX)) {
						tempList.add(imgName);
					}
				}
					list = new String[tempList.size()];
					list = tempList.toArray(list);
			}

			Timer.numberOfImages(list.length);
			Timer.currentImgIndex(i + 1);

			ijLog("Processing image " + Timer.getCurrentImgIndex() + " of " + Timer.getNImgs());

			String path = parameters.getInputDir() + list[i];

			// Open image.
			ijLog("Opening image path: " + path);
			ImagePlus imp = IJ.openImage(path);
			String imgName = imp.getTitle();

			ModeSegment segment = new ModeSegment();
			SegmentedChannels segmentedChannels = segment.run(parameters, imp, list[i]);

			// Generate skeletons based on user inputs and save
			SkeletonProcess skeletonize = new SkeletonProcess();
			Map<String, SkeletonResultsHolder> skeletonResults = skeletonize.run(parameters, segmentedChannels, imgName);
			SaveSkeletons saveSkeletons = new SaveSkeletons();
			saveSkeletons.saveSkeletons(skeletonResults, parameters, imgName);

			// Generate stratified bands - bands are saved within
			StratifyProcess stratify = new StratifyProcess();

			Map<String, StratifiedResultsHolder> stratifyResults = stratify.process(parameters, segmentedChannels, imgName);

			// Build compiledImageData object.
			CompiledImageData imgData = CompiledImageData.builder().
					images(segmentedChannels).
					skeletons(skeletonResults).
					stratifyResults(stratifyResults).
					build();
			
			// Hand off to mode analyse for common anaylses
			ModeAnalyse analyse = new ModeAnalyse();

			if(!parameters.getMode().equals(ModeType.NONE)) {
				analyse.run(parameters, imgData, imgName);
			} else { 
				Timer.stop(parameters);
			}
		}
	}
}
