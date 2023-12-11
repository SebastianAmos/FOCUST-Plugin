package clcm.focust.mode;

import java.io.File;
import java.util.Optional;

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
		IJ.log("Number of images to process: " + list.length);

		for (int i = 0; i < list.length; i++) {
			
			String path = parameters.getInputDir() + list[i];

			// open image.
			IJ.log("Opening image path: " + path);
			ImagePlus imp = IJ.openImage(path);
			String imgName = imp.getTitle();
			
			// Run the user-specified segmentation for appropriate channels.
			ModeSegment segment = new ModeSegment();
			SegmentedChannels objects = segment.run(parameters, imp);
			
			// run skeletons for secondary and tertiary objects
			// TODO: add gui options to pick sec or ter or both - maybe even primary???
			
			// Init both optionals.
			Optional<SkeletonResultsHolder> secondarySkeletonResults = Optional.empty();
			Optional<SkeletonResultsHolder> tertiarySkeletonResults = Optional.empty();
			
			
			if(parameters.getSkeletonize()) {
				Skeleton skeleton = new Skeleton();
				
				// Secondary
				ImagePlus secondarySkeleton = skeleton.createSkeletons(objects.getSecondary());
				secondarySkeletonResults = Optional.ofNullable(skeleton.analyzeSkeletons(secondarySkeleton, objects.getSecondary(), imgName));
				
				// Tertiary - optional
				ImagePlus tertiarySkeleton = objects.getTertiary().map(t -> skeleton.createSkeletons(t)).orElse(null);
				tertiarySkeletonResults = objects.getTertiary().map(t -> skeleton.analyzeSkeletons(tertiarySkeleton, t, imgName));
				
			}
			
			
			// Build compiledImageData object.
			CompiledImageData imgData = CompiledImageData.builder()
					.images(objects)
					.secondarySkeletons(secondarySkeletonResults)
					.tertiarySkeletons(tertiarySkeletonResults)
					.build();
			
			
			// Run the selected mode
			// Each analysis method called within a mode should check for empty results tables and mark with na to improve data awareness.
			parameters.getMode().getMode().run(parameters, imgData, imgName);
			
			

	
			
			
		
		}
	}
}
