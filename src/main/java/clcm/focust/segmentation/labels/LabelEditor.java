package clcm.focust.segmentation.labels;


import java.util.HashMap;
import java.util.Map;

import clcm.focust.utility.TableUtility;
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
	 * Generate a connected components image image, where all original labels are re-labelled.
	 * Map the new, unique labels for each object in an image, to the old, potenitally duplicate labels of the original.
	 * 
	 * @param original
	 * @return map 
	 */
	public Map<Double, Double> recordDuplicates(ImagePlus original) {
		
		Map<Double, Double> labels = new HashMap<>();
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		clij2.clear();
		ClearCLBuffer input = clij2.push(original);
		ClearCLBuffer cc = clij2.create(input);
		clij2.connectedComponentsLabelingBox(input, cc);
		ResultsTable table = new ResultsTable();
		clij2.statisticsOfLabelledPixels(input, cc, table);
		
		for (int i = 0; i < table.size(); i++) {
			labels.put(table.getValue("IDENTIFER", i), table.getValue("MAX", i));
		}
		
		clij2.clear();
		return labels;
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
	 * Converts all labels within an image to binary equivalents.
	 * 
	 * @param input A labelled image.
	 * 
	 * @return output Binary equivalent of the input.
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
	 * A method to re-index label values where they are duplicated between spatially independent objects.
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
	} 
	
	
	
	/**
	 * Short gpu method for returning binary object outlines.
	 * 
	 * @param imp A labelled image to detect the edges of.
	 * @return imp of binary label edges.
	 */
	public ImagePlus detectEdgesBinary(ImagePlus imp) {
		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer input = clij2.push(imp);
		ClearCLBuffer output = clij2.create(input);
		clij2.detectLabelEdges(input, output);
		ImagePlus binaryEdges = clij2.pull(output);
		return binaryEdges;
	}
	
	
	
	/**
	 * GPU method for returning labelled object outlines.
	 * 
	 * @param imp A labelled image to detect the edges of.
	 * @return imp of label edges.
	 */
	public static ImagePlus detectEdgesLabelled(ImagePlus imp) {
		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer input = clij2.push(imp);
		ClearCLBuffer output = clij2.create(input);
		clij2.reduceLabelsToLabelEdges(input, output);
		ImagePlus labelledEdges = clij2.pull(output);
		IJ.resetMinAndMax(labelledEdges);
		input.close();
		output.close();
		
		return labelledEdges;
	}
	
	
	
}
