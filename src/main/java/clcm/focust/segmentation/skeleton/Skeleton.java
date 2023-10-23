package clcm.focust.segmentation.skeleton;


import java.util.ArrayList;
import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import inra.ijpb.measure.IntensityMeasures;
import inra.ijpb.measure.ResultsBuilder;
import sc.fiji.skeletonize3D.Skeletonize3D_;
import sc.fiji.analyzeSkeleton.AnalyzeSkeleton_;
import sc.fiji.analyzeSkeleton.Edge;
import sc.fiji.analyzeSkeleton.Graph;
import sc.fiji.analyzeSkeleton.Point;
import sc.fiji.analyzeSkeleton.SkeletonResult;
import org.apache.commons.math3.util.MathArrays;

import clcm.focust.LabelEditor;


/**
 * Wrapper to implement functionality from Skeletonize3D and AnalyzeSkeleton3D and return results in IJ ResultTable objects.
 *
 */


public class Skeleton {
	
	private Calibration cal = null;
	
	/**
	 * Skeletonize the input image.
	 * 
	 * @param imp
	 * @return 
	 */
	public ImagePlus createSkeletons(ImagePlus imp) {
		
		ImagePlus img = imp.duplicate();
		Skeletonize3D_ skeletonise = new Skeletonize3D_();
		IJ.run(imp, "8-bit", "");
		ImagePlus skeletons = LabelEditor.makeBinary(img);
		skeletons.setTitle("Skeletons_of_" + img.getTitle());
		IJ.log("Computing skeletons...");
		skeletonise.setup("", skeletons);
		skeletonise.run(null);

		return skeletons;
		
	}
	
	
	/**
	 * Analyse the skeletons and produce a results holder containing two tables:
	 * 1 x standard and 1 x verbose results.
	 * 
	 * @param skeletons
	 * @param labels
	 * @return
	 */
	public SkeletonResultsHolder analyzeSkeletons(ImagePlus skeletonOriginals, ImagePlus labels) {

		final AnalyzeSkeleton_ analyseSkeletons = new AnalyzeSkeleton_();
		final ImagePlus skeletons = skeletonOriginals.duplicate();
		cal = skeletonOriginals.getCalibration();
		IJ.log("Analyzing Skeletons...");
		analyseSkeletons.setup("", skeletons);
		IJ.log("Running skeleton analysis...");
		//final SkeletonResult skeletonResults = analyseSkeletons.run(AnalyzeSkeleton_.NONE, false, false, null, true, true);
		final SkeletonResult skeletonResults = analyseSkeletons.run(AnalyzeSkeleton_.NONE, false, false, null, true, true, null);
		IJ.log("complete.");
		
		// get the tagged skeleton img
		final ImageStack lblSkeletonStack = analyseSkeletons.getLabeledSkeletons();
		ImagePlus labelledSkeletons = new ImagePlus("labelled_skeletons_" + skeletonOriginals.getTitle(), lblSkeletonStack);
		
		// pull data from SkeletonResults object
		SkeletonResultsHolder results = new SkeletonResultsHolder(null, null, null, null);
		results = buildResults(skeletonResults, results);
		results.setLabelledSkeletons(labelledSkeletons);
		results.setLabelMatched(matchSkeletonToLabels(labelledSkeletons, labels));
		
		
		return results;
	}
	
	
	/**
	 * Create a map of skeleton to original labels.
	 * This can be used to match the skeletons with existing results. 
	 * 
	 * @param skeletons
	 * @param originaLabels
	 */
	public ResultsTable matchSkeletonToLabels(ImagePlus skeletons, ImagePlus originalLabels) {
	
		ResultsBuilder rb = new ResultsBuilder();
		final IntensityMeasures im = new IntensityMeasures(skeletons, originalLabels);
		
		rb.addResult(im.getMax());
		
		ResultsTable rt = rb.getResultsTable();
		return rt;
	}
	
	
	/**
	 * Extracts the results from the standard and verbose results of a SkeletonResult object.
	 * Returns an object that holds both results tables.
	 * 
	 * @param skel
	 * @return
	 */
	private SkeletonResultsHolder buildResults(SkeletonResult skel, SkeletonResultsHolder results) {

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
			standard.addRow();
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
				"Euclidean Distance",
				"Running Average Length"};
		
		
		final Graph[] graphs = skel.getGraph();
		
		for (int j = 0; j < graphs.length; j++) {
			final ArrayList<Edge> edges = graphs[j].getEdges();
			edges.sort((a,b) -> -Double.compare(a.getLength(), b.getLength()));
			for (int k = 0; k < edges.size(); k++) {
				final Edge edge = edges.get(k);
				extra.addRow();
				extra.addValue(xtHeaders[0], j + 1); // skeleton
				extra.addValue(xtHeaders[1], k + 1); // branch 
				extra.addValue(xtHeaders[2], edge.getLength()); // length of branch (j)
				
				// first point
				final Point pt = edge.getV1().getPoints().get(0);
				extra.addValue(xtHeaders[3], (pt.x * cal.pixelWidth));
				extra.addValue(xtHeaders[4], (pt.y * cal.pixelHeight));
				extra.addValue(xtHeaders[5], (pt.z * cal.pixelDepth));
				
				// last point
				final Point pt2 = edge.getV2().getPoints().get(0);
				extra.addValue(xtHeaders[6], (pt2.x * cal.pixelWidth));
				extra.addValue(xtHeaders[7], (pt2.y * cal.pixelHeight));
				extra.addValue(xtHeaders[8], (pt2.z * cal.pixelDepth));
				
				// calculate euclidean dist
				final double dist = MathArrays.distance(
						new double[] {pt.x * cal.pixelWidth, 
								pt.y * cal.pixelHeight,
								pt.z * cal.pixelDepth},
						new double[] {pt2.x * cal.pixelWidth,
								pt2.y * cal.pixelHeight,
								pt2.z * cal.pixelDepth});
				extra.addValue(xtHeaders[9], dist);
				extra.addValue(xtHeaders[10], edge.getLength_ra());
			}
		}
		
		// set the tables
		results.setStandard(standard);
		results.setExtra(extra);
		
		return results;
		
	}
	
	
	
}
