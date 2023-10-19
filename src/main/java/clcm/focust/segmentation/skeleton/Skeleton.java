package clcm.focust.segmentation.skeleton;


import ij.IJ;
import ij.ImagePlus;
import ij.measure.ResultsTable;
import lombok.Builder;
import lombok.Data;
import sc.fiji.skeletonize3D.Skeletonize3D_;
import sc.fiji.analyzeSkeleton.AnalyzeSkeleton_;
import sc.fiji.analyzeSkeleton.Graph;
import sc.fiji.analyzeSkeleton.SkeletonResult;


/**
 * Wrapper to implement functionality from Skeletonize3D and AnalyzeSkeleton3D and return results in IJ ResultTable objects.
 *
 */

@Data
@Builder
public class Skeleton {
	
	
	
	/**
	 * Skeletonize the input image.
	 * 
	 * @param imp
	 * @return 
	 */
	public ImagePlus createSkeletons(ImagePlus imp) {
		ImagePlus skeletons = imp.duplicate();
		Skeletonize3D_ skeletonise = new Skeletonize3D_();
		IJ.run(imp, "8-bit", "");
		skeletonise.setup("", skeletons);
		//ImageProcessor ip = imp.getProcessor();
		skeletonise.run(null);
		//ImagePlus img = new ImagePlus("skeleton", ip.duplicate());

		return skeletons;
		
	}
	

	/**
	 * Analyse the skeletons and match with original labels.
	 * 
	 * @param skeletons
	 * @param labels
	 * @return
	 */
	public void analyzeSkeletons(ImagePlus imp, ImagePlus labels) {

		final AnalyzeSkeleton_ analyseSkeletons = new AnalyzeSkeleton_();
		final ImagePlus skeletons = imp.duplicate();

		analyseSkeletons.setup("", skeletons);
		IJ.log("Running skeleton analysis...");
		final SkeletonResult skeletonResults = analyseSkeletons.run(AnalyzeSkeleton_.NONE, false, false, null, true, true);
		IJ.log("complete.");
		// pull data from SkeletonResults object
		SkeletonResultsHolder results = buildResults(skeletonResults);
		
		ResultsTable rt = results.getStandard();
		ResultsTable xt = results.getExtra();
		
		
	}
	
	
	/**
	 * Create a map of skeleton to original labels.
	 * This can be used to match the skeletons with existing results. 
	 * 
	 * @param skeletons
	 * @param originaLabels
	 */
	public void matchLabels(ImagePlus skeletons, ImagePlus originaLabels) {
		
		// intensity match skeletons within label masks
		
		
		
	}
	
	
	/**
	 * Extracts the 
	 * 
	 * @param skel
	 * @return
	 */
	private SkeletonResultsHolder buildResults(SkeletonResult skel) {

		// init return object
		SkeletonResultsHolder results = new SkeletonResultsHolder(null, null);

		ResultsTable standard = new ResultsTable();
		ResultsTable extra = new ResultsTable();

		// collect the standard results
		final String[] headers = { "# Skeleton", 
				"# Branches", 
				"# Junctions", 
				"# End-point voxels", 
				"# Junction voxels",
				"# Slab voxels", 
				"Average Branch Length", 
				"# Triple points", 
				"# Quadruple points",
				"Maximum Branch Length" };

		for (int i = 0; i < skel.getNumOfTrees(); i++) {
			standard.addValue(headers[0], i + 1);
			standard.addValue(headers[1], skel.getBranches()[i]);
			standard.addValue(headers[2], skel.getJunctions()[i]);
			standard.addValue(headers[3], skel.getEndPoints()[i]);
			standard.addValue(headers[4], skel.getJunctionVoxels()[i]);
			standard.addValue(headers[5], skel.getSlabs()[i]);
			standard.addValue(headers[6], skel.getAverageBranchLength()[i]);
			standard.addValue(headers[7], skel.getTriples()[i]);
			standard.addValue(headers[8], skel.getQuadruples()[i]);
			standard.addValue(headers[9], skel.getMaximumBranchLength()[i]);
		}
		
		
		// collect the extra results
		final String[] xtHeaders = {"# Skeleton", 
				"# Branch", 
				"Branch Length", 
				"V1x",
				"V1y",
				"V1z",
				"V2x",
				"V2y",
				"V2z",
				"Euclidean Distance"};
		
		
		final Graph[] graphs = skel.getGraph();
		
		// set the rts
		results.setStandard(standard);
		results.setExtra(extra);
		
		return results;
		
	}
	
	
	
}
