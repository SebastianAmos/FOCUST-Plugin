package clcm.focust;

import ij.ImagePlus;
import inra.ijpb.plugins.AnalyzeRegions3D;
import ij.measure.ResultsTable;
import ij.plugin.filter.Binary;
import ij.process.BinaryProcessor;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class BinarySearch {

	public static AnalyzeRegions3D analyze3D = new AnalyzeRegions3D();
	
	
	public ImagePlus createSpheroidCore(ImagePlus input) {
		ImagePlus img = LabelEditor.makeBinary(input);
		
		
		
		ImagePlus output = img;
		return output;
	}

	// calculate the max number of iterations possible given the dimensions of the image
	private int maxIterations(int w, int h) {
		
		int smallerDimension = Math.min(w, h);
		return smallerDimension /2;
		
	}
	
	// calculate the number of voxels in the object
	private int calculateVoxNum(ImagePlus input) {
		
		ResultsTable rt = analyze3D.process(input);
		int voxNum = (int) rt.getValue("VoxelCount", 0);
		return voxNum;
		
	}
	
	
	private int binarySearchIterationNumber(ByteProcessor bp, int maxIterations) {
		
		int low = 0;
		int high = maxIterations;
		int targetIterationNumber = -1;
		
		while (low <= high) {
			
			int middle = (low + high) / 2;
			
			
			BinaryProcessor binaryP = new BinaryProcessor(bp);
			for (int i = 0; i < middle; i++) {
				binaryP.erode();
			}
			
		}
		
		
		
		
		return targetIterationNumber;
		
	}
	
	
}
