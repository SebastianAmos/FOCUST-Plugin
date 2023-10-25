package clcm.focust.method;


import ij.IJ;
import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.process.ImageProcessor;
import sc.fiji.skeletonize3D.Skeletonize3D_;
import sc.fiji.analyzeSkeleton.AnalyzeSkeleton_;


public class Skeleton {
	
	/**
	 * generate skeletons for distance and length calculations.
	 * needs to be 8bit?
	 * @param imp
	 * @return 
	 */
	public ImagePlus createSkeletons(ImagePlus imp) {
		Skeletonize3D_ sk = new Skeletonize3D_();
		IJ.run(imp, "8-bit", "");
		sk.setup("", imp);
		ImageProcessor ip = imp.getProcessor();
		sk.run(ip);
		ImagePlus img = new ImagePlus("skeleton", ip.duplicate());

		return img;
		
	}
	

	/**
	 * analyse the skeletons and match with original labels.
	 * @param skeletons
	 * @param labels
	 * @return
	 */
	public void analyzeSkeletons(ImagePlus skeletons, ImagePlus labels) {

		AnalyzeSkeleton_ ak = new AnalyzeSkeleton_();
		
		ak.setup("", skeletons);
		ImageProcessor ip = skeletons.getProcessor();
		ak.run(ip);
		
		
	
		
	}
	
	
	
	
}
