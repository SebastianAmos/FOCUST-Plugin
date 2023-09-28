package clcm.focust;


import java.util.HashMap;
import java.util.Map;
import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.measure.ResultsTable;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import inra.ijpb.plugins.AnalyzeRegions3D;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;


/**
 * A class that contains helper methods for processing labels and stacks.
 * @author SebastianAmos
 *
 */

public class LabelEditor {

	public static ImagePlus renamedImage;
	//private static AnalyzeRegions3D analyze3D = new AnalyzeRegions3D();
	

	
	/**
	 * Adds an empty black slice as the first and last slice of an image stack.
	 * 
	 * @param ImagePlus input
	 */
	public static void padTopAndBottom(ImagePlus input) {
		
		ImageProcessor emptySlice = input.getProcessor().createProcessor(input.getWidth(), input.getHeight());
		
		emptySlice.setColor(0);
		emptySlice.fill();
		
		// set to first slice, add empty, then repeat on last slice.
		input.setSlice(0);
		input.getStack().addSlice(emptySlice);
		input.setSlice(input.getNSlices()+1);
		input.getStack().addSlice(emptySlice);
		
	}
	
	
	
	

	/**
	 * Count how many labels from labelToCount overlap with mask. 
	 * 
	 * @param mask
	 * @param labelToCount
	 * @return ResultsTable
	 */
	public static ResultsTable countOverlappingLabels(ImagePlus mask, ImagePlus labelToCount) {
		CLIJ2 clij2 = CLIJ2.getInstance();
		clij2.clear();
		ClearCLBuffer larger = clij2.push(mask);
		ClearCLBuffer smaller = clij2.push(labelToCount);
		ClearCLBuffer countMap = clij2.create(larger);
		clij2.labelOverlapCountMap(larger, smaller, countMap);
		ImagePlus impCountMap = clij2.pull(countMap);
		clij2.clear();
		ResultsTable labelCounts = TableUtility.processIntensity(impCountMap, mask);
		return labelCounts;
	}
	
	
	/**
	 * This method assesses labels matched with a secondary image for duplicates. 
	 * Where identified, duplicate label values are indexed to preserve identity.
	 * 
	 * @param matched An image that has been label-matched to overlying labels from original image.
	 * @param original An image containing the original label values.
	 * 
	 * @return an adjusted image with duplicate label value corrections.
	 *
	 * @TODO
	 * - Test label duplicates compared to the same image > could just pass one image to de-duplicate? 
	 * 
	 */
	
	public static ImagePlus findAndRename(ImagePlus matched, ImagePlus original) {
		
		Map<Integer, Integer> labelMaps = new HashMap<>();
		
		// iterate over slices of matched image to map the labels
		// for every z slice, at every x and y pixel position, compare the label values.
		for (int z = 1; z <= matched.getStackSize(); z++) {
			ImageProcessor ipMatched = matched.getStack().getProcessor(z);
			ImageProcessor ipOriginal = original.getStack().getProcessor(z);
			
			for (int y = 0; y < ipMatched.getHeight(); y++) {
				for (int x = 0; x < ipMatched.getWidth(); x++) {
					int lblMatched = (int) ipMatched.getPixelValue(x, y);
					int lblOriginal = (int) ipOriginal.getPixelValue(x, y);
					
					// where a pixel label exists, and the map contains that value, reassign the value.
					if (lblMatched > 0 ) {
						if (labelMaps.containsKey(lblMatched)) {
							
							// shared value found, append an index to individualize the label 
							int newIndex = labelMaps.get(lblMatched) + 1;
							labelMaps.put(lblMatched, newIndex);
							ipOriginal.putPixelValue(x, y, Double.parseDouble( lblOriginal + "0" + newIndex));
							
						} else {
							// new label found, add to the map
							labelMaps.put(lblMatched, 0);
							ipOriginal.putPixelValue(x, y, lblOriginal);
						}
					}
				}
			}
		}
		ImagePlus output = original;
		return output;
	} 
	
	
	
	public static ImagePlus reLabel(ImagePlus img) {
		IJ.run(img, "16-bit", "");
		for (int z = 1; z <= img.getStackSize(); z++) {
			ImageProcessor ipImg = img.getStack().getProcessor(z);
			for (int y = 0; y < ipImg.getHeight(); y++) {
				for (int x = 0; x < ipImg.getWidth(); x++) {
					int lbl = (int) ipImg.getPixelValue(x, y);
					
					if (lbl > 0) {
						double newLbl = Double.parseDouble(lbl + "0" + 1);
						ipImg.putPixelValue(x, y, newLbl);
					}
				}
			}
			
		}
		ImagePlus output = img;
		return output;
	}
	
	
	
	/**
	 * A modified implementation of findAndRename(); 
	 * 
	 * @param matched
	 * @param original
	 * @return output The relabelled image where duplicate labels have been indexed. 
	 */
	
	public static ImagePlus manageDuplicates(ImagePlus matched, ImagePlus original) {
		
		// Create a map to store label values from the original image. If a new value is encountered, initialise from 0 so the first repeat can index from 1. 
		Map<Integer, Integer> labelMaps = new HashMap<>();
		
		// Iterate over the original image stack to map the labels, reassigning values in the matched image where required.
		// For each z slice, at every x, y pixel position compare label values
		for (int z = 1; z <= original.getStackSize(); z++) {
			
			ImageProcessor ipMatched = matched.getStack().getProcessor(z);
			ImageProcessor ipOriginal = original.getStack().getProcessor(z);
			
			for (int y = 0; y < ipOriginal.getHeight(); y++) {
				for (int x = 0; x < ipOriginal.getWidth(); x++) {
					
					int lblMatched = (int) ipMatched.getPixelValue(x, y);
					int lblOriginal = (int) ipOriginal.getPixelValue(x, y);
					
					// If there is a label (i.e. not background which = 0), and it exists in the map, reassign the pixel value with the same label + index.
					if (lblOriginal > 0) {
						if (labelMaps.containsKey(lblOriginal)) {
							
							// Shared label exists, append an index to induvidualise the instance
							int newIndex = labelMaps.get(lblOriginal) + 1;
						
							labelMaps.put(lblOriginal, newIndex);
							ipMatched.putPixelValue(x, y, Double.parseDouble(lblMatched + "00" + newIndex));
						
						} else {
							// Initialise new label keys with an index value of 0, so if found again, indexing for repeats can start at 1. 
							labelMaps.put(lblOriginal, 0);
							ipMatched.putPixelValue(x, y, lblMatched);
						}
					}
				}
			}
		}
		ImagePlus output = matched;
		return output;
	}
	
	
	
	
	
	
	/**
	 * Converts all labels within an image to binary equivalents.
	 * 
	 * @param input A labelled image.
	 * 
	 * @return output Binary equivalent of the input.
	 *  
	 */
	public static ImagePlus makeBinary(ImagePlus input) {
	    ImageStack stack = input.getStack();
		ImageStack binaryStack = new ImageStack(stack.getWidth(), stack.getHeight(), stack.getSize());
		for (int z = 1; z <= stack.getSize(); z++) {
			ImageProcessor ip = stack.getProcessor(z);
			ByteProcessor bp = ip.convertToByteProcessor();
			bp.threshold(0);
			binaryStack.setProcessor(bp, z);
		}
		ImagePlus output = new ImagePlus("",binaryStack);
		return output;
	}
	
	
	
	/**
	 * A method to re-index label values where they are duplicated between spatially indenpdent objects.
	 * 
	 * @param input The labelled image to be re-indexed where labels are duplicated between objects.
	 * @return output A re-labelled image where repeated values are indexed per instance. 
	 */
	
	public static ImagePlus labelIndexAppender(ImagePlus input) {
		IJ.run(input, "16-bit", "");
		Map<Integer, Integer> labelCountMap = new HashMap<>();
		
		for (int z = 1; z <= input.getStackSize(); z++) {
			ImageProcessor ip = input.getStack().getProcessor(z);
			for (int y = 0; y < input.getHeight(); y++) {
				for (int x = 0; x < input.getWidth(); x++) {
					int pixVal = ip.getPixel(x, y);
					if (pixVal > 0 ) {
						int count = labelCountMap.getOrDefault(pixVal, 0) + 1;
						labelCountMap.put(pixVal, count);
						String newLab = pixVal + "0" + count;
						ip.putPixel(x, y, Integer.parseInt(newLab));
					}
							
				}
			}
		}
		ImagePlus output = input;
		return output;
	}
	
	
	
	/**
	 * This method will determine the number of iterations required to get to approximately 50% of the initial ROI volume and then runs the erosion.
	 * This generated a core ROI for spatial intensity analysis. 
	 * 
	 * @param input The whole spheroid.
	 * @return output An image containing a label that is 50 % of the volume of input (the core of the spheroid).
	 */
	public static ImagePlus createSpheroidCore(ImagePlus input) {
		ImagePlus img = LabelEditor.makeBinary(input);
		
		AnalyzeRegions3D analyze3D = new AnalyzeRegions3D();
		ResultsTable results = analyze3D.process(img);
		results.show("Whole Spheroid Results Table");
		int totalVoxNum = (int) results.getValue("VoxelCount", 0);
		int targetVoxNum = (int) (0.5 * totalVoxNum);
		int kernel = 3*3*3;
		int iterations = (int) Math.ceil(targetVoxNum/ kernel);
		System.out.println("Total voxels: " + totalVoxNum);
		System.out.println("Target voxels: " + targetVoxNum);
		System.out.println("Number of iterations: " + iterations);
		
		IJ.run(img, "Options...", "iterations=" + iterations + " count=1 black do=Erode stack");
		
		ResultsTable newResults = analyze3D.process(img);
		newResults.show("Eroded Spheroid Reuslts");
		int newVoxNum = (int) newResults.getValue("VoxelCount", 0);
		
		
		System.out.println("Final voxels: " + newVoxNum);
		
		ImagePlus output = img;
		return output;
	}
	
	
	
	

	public static ImagePlus relativeRename(ImagePlus matched, ImagePlus original) {
		Map<Integer, Integer> labelMapping = new HashMap<>();
		// iterate over pixels of matched image
		for (int z = 1; z <= matched.getStackSize(); z++) {
			ImageProcessor ip = matched.getStack().getProcessor(z);
			for (int y = 0; y < ip.getHeight(); y++) {
				for (int x = 0; x < ip.getWidth(); x++){
					int label = (int) ip.getPixelValue(x, y);
					if (label > 0 && !labelMapping.containsKey(label)) {
						labelMapping.put(label, 0);
					}
				}
			}	
		}
		int i = 0;
		for (int z = 1; z<= original.getStackSize(); z++) {
			ImageProcessor ip = original.getStack().getProcessor(z);
			for (int y = 0; y < ip.getHeight(); y++) {
				for (int x = 0; x < ip.getWidth(); x++) {
					int label = (int) ip.getPixelValue(x, y);
					if (labelMapping.containsKey(label)){
						int newLabel = labelMapping.get(label) + i + 1;
						labelMapping.put(label, newLabel);
						i++;
					}
				}
			}
		}
		renamedImage = original;
		return renamedImage;
	} // end of relativeRename method
	
	
	
	/**
	 * Short gpu method for returning binary object outlines.
	 * 
	 * @param imp A labelled image to detect the edges of.
	 * @return imp of binary label edges.
	 */
	
	public ImagePlus detectEdgesBinary(ImagePlus imp) {
		CLIJ2 clij2 = CLIJ2.getInstance();
		clij2.clear();
		ClearCLBuffer input = clij2.push(imp);
		ClearCLBuffer output = clij2.create(input);
		clij2.detectLabelEdges(input, output);
		ImagePlus binaryEdges = clij2.pull(output);
		clij2.clear();
		return binaryEdges;
	}
	
	
	
	/**
	 * GPU method for returning labelled object outlines.
	 * 
	 * @param imp A labelled image to detect the edges of.
	 * @return imp of label edges.
	 */
	public ImagePlus detectEdgesLabelled(ImagePlus imp) {
		CLIJ2 clij2 = CLIJ2.getInstance();
		clij2.clear();
		ClearCLBuffer input = clij2.push(imp);
		ClearCLBuffer output = clij2.create(input);
		clij2.reduceLabelsToLabelEdges(input, output);
		ImagePlus labelledEdges = clij2.pull(output);
		clij2.clear();
		return labelledEdges;
	}
	
	
	
}// end of LabelEditor class
