package clcm.focust.segmentation.labels;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import ij.ImagePlus;
import ij.measure.ResultsTable;
import inra.ijpb.measure.IntensityMeasures;
import inra.ijpb.measure.ResultsBuilder;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public class OverlapMapping {

	public ClearCLBuffer centroids;


	/**
	 * Process a list of ClearCLBuffer bands to count how many labels sit within each band.
	 * @param bands
	 * @param bandType
	 * @return
	 */
	public List<ResultsTable> perBand(List<ClearCLBuffer> bands, ClearCLBuffer centroids, String bandType) {

		CLIJ2 clij2 = CLIJ2.getInstance();

		// init at 1 = core of banding
		int count = 1;

		List<ResultsTable> results = new ArrayList<>();

		// for each band in the list create a results table to hold data
		for (ClearCLBuffer band : bands) {

			ClearCLBuffer overlapMap = clij2.create(band);
			ClearCLBuffer copy = clij2.create(band);

			clij2.copy(band, copy);

			clij2.labelOverlapCountMap(band, centroids, overlapMap);

			IntensityMeasures im = new IntensityMeasures(clij2.pull(overlapMap), clij2.pull(copy));

			ResultsTable rt = im.getMax();

			rt.renameColumn("Max", bandType + count + ".NumberOfNuclei");

			overlapMap.close();
			copy.close();
			count++;

			results.add(rt);

		}

		return results;
	}


	/**
	 * Count how many labels reside within in the imp
	 * @param imp
	 * @param label
	 * @return
	 */
	public ResultsTable countChildren(ImagePlus imp, ImagePlus label) {

		CLIJ2 clij2 = CLIJ2.getInstance();

		ClearCLBuffer lab = clij2.push(label);
		ClearCLBuffer image = clij2.push(imp);
		centroids = clij2.create(lab);
		ClearCLBuffer overlapMap = clij2.create(lab);

		// use centroids so each primary label is only represented in 1 region
		clij2.reduceLabelsToCentroids(lab, centroids);
		clij2.labelOverlapCountMap(image, centroids, overlapMap);
		ImagePlus overlap = clij2.pull(overlapMap);

		IntensityMeasures im = new IntensityMeasures(overlap, imp);
		ResultsTable rt = im.getMax();

		rt.renameColumn("Max", "NumberOfNuclei");

		overlap.close();
		lab.close();
		overlapMap.close();
		image.close();
		
		return rt;
	}



	/**
	 * Calculate the band that each label centroid resides within.
	 * @param bands The list of ClearCLBuffers.
	 * @param centroids The ClearCLBuffer points.
	 * @param bandType Name to append to the "Max" header
	 * @return List of ResultsTables with the band ID of each centroid.
	 */
	public List<ResultsTable> parentBand(List<ClearCLBuffer> bands, ClearCLBuffer centroids, String bandType) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		int count = 1;
		List<ResultsTable> results = new ArrayList<>();

		ClearCLBuffer centroidCopy = clij2.create(centroids);
		clij2.copy(centroids, centroidCopy);
		ImagePlus centroidsImp = clij2.pull(centroidCopy);
		centroidCopy.close();

		for (ClearCLBuffer band : bands) {

			ClearCLBuffer copy = clij2.create(band);
			clij2.copy(band, copy);
			ImagePlus bandImp = clij2.pull(copy);

			IntensityMeasures im = new IntensityMeasures(bandImp, centroidsImp);
			ResultsTable rt = im.getMax();
			rt.renameColumn("Max", bandType + count + ".ParentBandID");

			bandImp.close();
			copy.close();

			results.add(rt);
			count++;
		}

		centroidsImp.close();

		return results;
	}




	/**
	 * Calculate the imp object that each centroid resides within.
	 * @param imp A labelled image that are matched to centroids of other labels
	 * @param centroids The centroids to match to the imp
	 * @return ResultsTable with the parent object ID for each centroid.
	 */
	public ResultsTable parentLabelOfCentroids(ImagePlus imp, ClearCLBuffer centroids) {
		
		ResultsTable rt = new ResultsTable();

		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer copy = clij2.create(centroids);
		clij2.copy(centroids, copy);

		ImagePlus cent = clij2.pull(copy);

		final IntensityMeasures im = new IntensityMeasures(imp, cent);
		rt = im.getMax();
		rt.renameColumn("Max", "ParentID");
		cent.close();
		return rt;
	}

}
