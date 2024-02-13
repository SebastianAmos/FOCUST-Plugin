package clcm.focust.mode;

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
import ij.IJ;
import ij.ImagePlus;
import ij.measure.ResultsTable;

import static clcm.focust.SwingIJLoggerUtils.ijLog;


public class ModeProcess{

	private static String primaryPrefix = "Primary_Objects_";
	private static String secondaryPrefix = "Secondary_Objects_";
	private static String tertiaryPrefix = "Tertiary_Objects_";

		
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
			
			
			
			// build in analysis only mode
			
			// if analysis-only mode: filter the list to remove objects and only list original images.
			if (parameters.getAnalysisOnly()) {
				ArrayList<String> tempList = new ArrayList<>();
				for (String imgName : list) {
					if (!imgName.startsWith(primaryPrefix) && !imgName.startsWith(secondaryPrefix) && !imgName.startsWith(tertiaryPrefix)) {
						tempList.add(imgName);
					}
				}
					list = new String[tempList.size()];
					list = tempList.toArray(list);
			}
			
			ijLog("new list length: " + list.length);
			
			String path = parameters.getInputDir() + list[i];
			
			
			// Open image.
			ijLog("Opening image path: " + path);
			ImagePlus imp = IJ.openImage(path);
			String imgName = imp.getTitle();
			
			// TODO: build in analysis only support 
			
			// testing save params
			try {
				ParameterCollection.saveParameterCollection(parameters, "/FOCUST-Parameter-File.json");
				System.out.println("Parameter file saved.");
			} catch (IOException e1) {
				System.out.println("Unable to save FOCUST parameter file.");
				e1.printStackTrace();
			}
			
			ModeSegment segment = new ModeSegment();
			SegmentedChannels objects = segment.run(parameters, imp, list[i]);
			
			
			// Generate skeletons based on user inputs and save
			SkeletonProcess skeletonize = new SkeletonProcess();
			Map<String, SkeletonResultsHolder> skeletonResults = skeletonize.run(parameters, objects);
			SaveSkeletons saveSkeletons = new SaveSkeletons();
			saveSkeletons.saveSkeletons(skeletonResults, parameters, imgName);
			
			
			// Generate stratified bands - bands are saved within
			StratifyProcess stratify = new StratifyProcess();
			Map<String, StratifiedResultsHolder> stratifyResults = stratify.process(parameters, objects, imgName);
			
			
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
