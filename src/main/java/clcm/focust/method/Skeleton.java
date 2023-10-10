package clcm.focust.method;

import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.process.ImageProcessor;
import sc.fiji.skeletonize3D.Skeletonize3D_;
import sc.fiji.analyzeSkeleton.AnalyzeSkeleton_;


public class Skeleton {

	
	/**
	 * generate skeletons for distance and length calculations.
	 * @param imp
	 * @return
	 */
	public ImagePlus skeletonize(ImagePlus imp) {
		
		Skeletonize3D_ sk = new Skeletonize3D_();
	
		sk.setup(null, imp);
		
		ImageProcessor ip = imp.getProcessor();
	
		sk.run(ip);
		
		
	
		
		return null;
		
	}
	
	
	/**
	 * analyse the skeletons and match with original labels.
	 * @param skeletons
	 * @param labels
	 * @return
	 */
	public ResultsTable analyze(ImagePlus skeletons, ImagePlus labels) {
		
		AnalyzeSkeleton_ ak = new AnalyzeSkeleton_();
		
		ak.run();
		return null;
		
	}
	
}
