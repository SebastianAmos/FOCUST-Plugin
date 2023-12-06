package clcm.focust.mode;

import java.io.File;

import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.segmentation.skeleton.Skeleton;
import clcm.focust.segmentation.skeleton.SkeletonResultsHolder;
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
			
			// Run the user-specified segmentation for appropriate channels.
			ModeSegment segment = new ModeSegment();
			SegmentedChannels objects = segment.run(parameters, imp);
			
			// run skeletons for secondary and tertiary objects
			// TODO: add gui options to pick sec or ter or both - maybe even primary???
			
			SkeletonResultsHolder tertiarySkeletonResults = null;
			
			if(parameters.getSkeletonize()) {
				Skeleton skeleton = new Skeleton();
				
				// secondary
				ImagePlus secondarySkeleton = skeleton.createSkeletons(objects.getSecondary());
				SkeletonResultsHolder secondarySkeletonResults = skeleton.analyzeSkeletons(secondarySkeleton, objects.getSecondary(), imgName);
				
				// tertiary
				objects.getTertiary().ifPresent(t -> {
					ImagePlus tertiarySkeleton = skeleton.createSkeletons(t);
					tertiarySkeletonResults = skeleton.analyzeSkeletons(tertiarySkeleton, t, imgName);
				});
				
			}
			
			
			// Build compiledImageData object.
			CompiledImageData imgData = CompiledImageData.builder()
					.images(objects)
					.secondarySkeletons(null)
					.tertiarySkeletons(tertiarySkeletonResults)
					.build();
			
			
			// Run the selected mode
			// Each analysis method called within a mode should check for empty results tables and mark with na to improve data awareness. 
			parameters.getMode().getMode().run(parameters, imgData, imgName);
			
			
			// Use ResultsTableUtility.saveAndStackResults(); to save results tables. 
	
			
			
		
		}
	}
}
