package clcm.focust.utility;

import clcm.focust.segmentation.labels.LabelEditor;
import ij.IJ;
import ij.ImagePlus;
import inra.ijpb.plugins.AnalyzeRegions3D;
import process3d.Erode_;
import ij.measure.ResultsTable;


public class BinarySearch {
	

	public static AnalyzeRegions3D analyze3D = new AnalyzeRegions3D(); // from MorphoLibJ
	public static Erode_ erode3D = new Erode_(); // from VIB_ FIJI library
	
	
	
	/**
	 * Generates an object that is approximately 50 % of the input objects volume based on the closest number of erosion iterations.
	 *  
	 * @param imp 
	 * 			An ImagePlus to be eroded.
	 * @param volumePercentageRemaining
	 * 			The target percentage of volume reduction as a fraction of 1. i.e 0.5 for 50 %. 
	 * 
	 * @return The eroded image.
	 */
	
	public static ImagePlus createSpheroidCore(ImagePlus imp, double volumePercentageRemaining) {
		ImagePlus img = LabelEditor.makeBinary(imp);
		
		// compute max number of iterations possible on current image.
		int maxIterations = maxIterations(img.getWidth(), img.getHeight());
		System.out.println("Maximum number of iterations is: " + maxIterations); // testing!
		
		// original volume of current image.
		int originalVoxNum = calculateVoxNum(img.duplicate());
		
		System.out.println("Original Voxel Count is: " + originalVoxNum); // testing!
		int targetVoxNum = (int) (volumePercentageRemaining * originalVoxNum);
		System.out.println("Target Voxel Count is: " + targetVoxNum); // testing!
		
		// compute optimal number of erosion iterations.
		int optimalIterationNum = binarySearchIterationNumber(img.duplicate(), maxIterations, originalVoxNum, volumePercentageRemaining);
		
		System.out.println("Optimal number of iterations is: " + optimalIterationNum);
		
		ImagePlus erodedStack = erodeStack(img.duplicate(), optimalIterationNum);
		
		return erodedStack;
	}


	
	/**
	 * Compute the max number of iterations possible given the dimensions of the image.
	 * This sets the upper limit for a binary search method.
	 * 
	 * @param w
	 * 			The width of an image.
	 * @param h
	 * 			The height of an image.
	 * 
	 * @return The max possible iteration number.
	 */
	
	private static int maxIterations(int w, int h) {
		int smallerDimension = Math.min(w, h);
		return smallerDimension / 2;
	}
	
	
	
	/**
	 * Computes the total number of voxels in an image.
	 * If voxel count is 0, return. 
	 * 
	 * @param imp
	 * 			An ImagePlus to be measured.
	 * @return The total number of voxels within ip.
	 */
	
	private static int calculateVoxNum(ImagePlus imp) {
		ResultsTable rt = analyze3D.process(imp);
		int voxNum = 0;
		try {
			voxNum = (int) rt.getValue("VoxelCount", 0);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		return voxNum;
	}
	
	
	
	/**
	 * Binary search for the number of erosion iterations that result in a mask that is approximately 50 % of the original volume.
	 * 
	 * @param imp
	 * 			An ImagePlus input to be eroded.
	 * @param maxIterations
	 * 			The maximum possible number of iterations.
	 * @param originalVoxNum
	 * 			The original volume of imp. 
	 * 			
	 * @return The number of iterations that erode the ip to approximately 50 % of it's original volume. 
	 */	
	private static int binarySearchIterationNumber(ImagePlus imp, int maxIterations, int originalVoxNum, double reductionPercentage) {
		
		int lower = 0;
		int higher = maxIterations;
		int targetIterationNumber = -1;
	
		
		int targetVoxNum = (int) (reductionPercentage * originalVoxNum);
		IJ.log("Total volume = " + originalVoxNum);
		IJ.log("Target volume = " + targetVoxNum);
		
		while (lower <= higher) {
			int middle = (lower + higher) / 2;
			ImagePlus erodedImg = erodeStack(imp.duplicate(), middle);
			int voxNum = calculateVoxNum(erodedImg);
			if (voxNum <= 0.5 * originalVoxNum) {
				targetIterationNumber = middle;
				higher = middle - 1;
				IJ.log("Current iteration count: " + targetIterationNumber);
				IJ.log("Vox count too low ( "+voxNum+" ), setting iteration count lower: " + lower +" - " + higher);
				
			} else {
				lower = middle + 1;
				IJ.log("Vox count too high ( "+voxNum+" ), setting iteration count higher: " + lower +" - " + higher);
			}
		}
		System.out.println("Final iteration number: " + targetIterationNumber);
		return targetIterationNumber;
	}
	
	
	
	/**
	 * Erodes a 3D binary stack i number of times.
	 * 
	 * @param imp 
	 * 			Must be a binary ImagePlus object.
	 * @param iterations
	 * 			The number of times the erosion (kernel size of 3 px).
	 * 
	 * @return The eroded 3D stack.
	 */
	
	private static ImagePlus erodeStack(ImagePlus imp, int iterations) {
		for (int i = 0; i < iterations; i++) {
			erode3D.erode(imp, 255, false);
		}
		return imp;
	}
}
