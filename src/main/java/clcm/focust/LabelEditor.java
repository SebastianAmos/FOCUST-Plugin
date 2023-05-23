package clcm.focust;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.plugin.filter.Binary;
import ij.process.ImageProcessor;
import inra.ijpb.label.LabelImages;
import inra.ijpb.plugins.AnalyzeRegions3D;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clijx.CLIJx;

public class LabelEditor {

	public static ImagePlus renamedImage;
	private static AnalyzeRegions3D analyze3D = new AnalyzeRegions3D();
	
	/* This method takes a primary image that has been label-matched with a secondary image (matched) and the originally-labeled image.
	* This method detects duplicate labels in the matched image, where two objects were uniquely identified in the original image. 
	* This may occur in biological datasets where a cell has multiple nuclei, for example. 
	* the renamedImage is returned with any grouped labels appended with indices so they may be treated individually. 
	*/ 
	
	
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
		
		return renamedImage;
	}
	
	
	
	
	
	
	
	
	public static ImagePlus gpuRenameLabels(ImagePlus matched, ImagePlus original) {
		ResultsTable rtMatched = analyze3D.process(matched);
		ResultsTable rtOriginal = analyze3D.process(original);
		
		double[] lblsMatched = rtMatched.getColumnAsDoubles(-1);
		double[] lblsOriginal = rtOriginal.getColumnAsDoubles(-1);
		
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
			ClearCLBuffer label = bufMatched;
		}
		
		
		
		
		
		return renamedImage;
	}
	
	
	
	
	public static ImagePlus manageDuplicates(ImagePlus matched, ImagePlus original) {
		
		// generate results tables so labels can be accessed
		ResultsTable rtMatched = analyze3D.process(matched);
		ResultsTable rtOriginal = analyze3D.process(original);
		
		// grab label columns as double arrays
		double[] lblsMatched = rtMatched.getColumnAsDoubles(-1);
		double[] lblsOriginal = rtOriginal.getColumnAsDoubles(-1);
		
		//int[] labelMapping = createLabelMapping(lblsMatched, lblsOriginal);
		
		
		
		
		
		return renamedImage;
	}// end of manageDuplicates method
	
	
	
}// end of LabelEditor class
