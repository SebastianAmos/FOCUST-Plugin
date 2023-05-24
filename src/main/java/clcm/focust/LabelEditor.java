package clcm.focust;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import ij.ImagePlus;
import ij.ImageStack;
import ij.measure.ResultsTable;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import inra.ijpb.plugins.AnalyzeRegions3D;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clijx.CLIJx;

public class LabelEditor {

	public static ImagePlus renamedImage;
	private static AnalyzeRegions3D analyze3D = new AnalyzeRegions3D();
	
	
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
							int newIndex = labelMaps.get(lblMatched) + 1 ;
							labelMaps.put(lblMatched, newIndex);
							ipOriginal.putPixelValue(x, y, Double.parseDouble( lblOriginal + "00" + newIndex));
							
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
	 * @return binaryImage Binary equivalent of the input.
	 *  
	 */
	public static ImagePlus makeBinary(ImagePlus input) {
	    ImageStack stk = input.getStack();
		ImageStack bstk = new ImageStack(stk.getWidth(), stk.getHeight(), stk.getSize());
		for (int z = 1; z <= stk.getSize(); z++) {
			ImageProcessor ip = stk.getProcessor(z);
			ByteProcessor bp = ip.convertToByteProcessor();
			bp.threshold(0);
			bstk.setProcessor(bp, z);
		}
		ImagePlus output = new ImagePlus("",bstk);
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
	
	
	
	
	
	
	
	// clij2 approach to find labels in matched that have the same 
	public static ImagePlus gpuRenameLabels(ImagePlus matched, ImagePlus original) {
		ResultsTable rtMatched = analyze3D.process(matched);
		ResultsTable rtOriginal = analyze3D.process(original);
		
		double[] lblsMatched = rtMatched.getColumnAsDoubles(0);
		double[] lblsOriginal = rtOriginal.getColumnAsDoubles(0);
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		clij2.clear();
		CLIJx clijx = CLIJx.getInstance();
		clijx.clear();
		
		
		// Covert double arrays to float buffers
		FloatBuffer fb1 = FloatBuffer.allocate(lblsMatched.length);
		for (double label : lblsMatched) {
			fb1.put((float)label);
		}
		fb1.flip();
		
		
		FloatBuffer fb2 = FloatBuffer.allocate(lblsOriginal.length);
		for (double label : lblsOriginal) {
			fb2.put((float) label);
		}
		fb2.flip();
		
		
		// read CCLbufers from float buffers
		ClearCLBuffer bufMatched = clij2.create(lblsMatched.length, 1);
		bufMatched.readFrom(fb1, true);
		
		ClearCLBuffer bufOriginal = clij2.create(lblsOriginal.length, 1);
		bufOriginal.readFrom(fb2, true);
		
		clij2.push(bufMatched);
		clij2.push(bufOriginal);
		
		double maxLabelMatched = clij2.maximumOfAllPixels(bufMatched);
		
		ClearCLBuffer uniqueLabels = clij2.create(bufMatched);
		clij2.set(uniqueLabels, 0);
		
		
		// unique labels to objects
		for(int i = 0; i < lblsMatched.length; i++) {
			double label = lblsMatched[i];
			double uniqueLabel = label + maxLabelMatched *(i+1);
			clij2.addImageAndScalar(uniqueLabels, uniqueLabels, uniqueLabel);
			clij2.greaterConstant(uniqueLabels, uniqueLabels, label);
		}
		
		double maxLabelOriginal = clij2.maximumOfAllPixels(bufOriginal);
		
		ClearCLBuffer updateLabels = clij2.create(bufOriginal);
		clij2.set(updateLabels, 0);
		
		clij2.addImageAndScalar(bufOriginal, updateLabels, maxLabelMatched + 1);
		clij2.greaterConstant(updateLabels, updateLabels, maxLabelMatched + 1);
		clij2.mask(updateLabels, updateLabels, uniqueLabels);
		clij2.addImageAndScalar(updateLabels, updateLabels, maxLabelOriginal);
		
		
		renamedImage = clij2.pull(updateLabels);
		
		
		return renamedImage;
	}
	
	
	
	
}// end of LabelEditor class
