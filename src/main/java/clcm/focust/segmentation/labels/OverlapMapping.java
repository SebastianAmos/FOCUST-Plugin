package clcm.focust.segmentation.labels;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import ij.ImagePlus;
import ij.measure.ResultsTable;
import inra.ijpb.measure.IntensityMeasures;
import inra.ijpb.measure.ResultsBuilder;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public class OverlapMapping {


	/**
	 * Process a list of ClearCLBuffer bands to count how many labels sit within each band.
	 * 
	 * @param bands
	 * @param label
	 * @param colHeader
	 * @return
	 */
	public ResultsTable perBand(List<ClearCLBuffer> bands, ImagePlus label, String bandType, String labType) {

		CLIJ2 clij2 = CLIJ2.getInstance();

		ClearCLBuffer lab = clij2.push(label);
		ClearCLBuffer centroids = clij2.create(lab);
		ClearCLBuffer overlapMap = clij2.create(lab);

		// init at 1 = core of banding
		int count = 1;

		ResultsTable results = new ResultsTable();

		// use centroids so each label is only represented in one band.
		clij2.reduceLabelsToCentroids(lab, centroids);

		// for each band in the list create a results table to hold data
		for (ClearCLBuffer band : bands) {

			ResultsTable rt = new ResultsTable();

			clij2.labelOverlapCountMap(band, centroids, overlapMap);
			clij2.statisticsOfLabelledPixels(band, overlapMap, rt);

			results.setColumn("Label", rt.getColumnAsVariables("IDENTIFIER"));
			results.setColumn("Number_Of_" + labType + "Objects_In_Band_" + bandType + count, rt.getColumnAsVariables("MAXIMUM_INTENSITY"));

			count++;
		}

		lab.close();
		centroids.close();
		overlapMap.close();


		return results;
	}


	/**
	 * Count how many labels reside within in the imp.  
	 * 
	 * @param imp
	 * @param label
	 * @param objectName A string to add to the column header
	 * @return
	 */
	public ResultsTable perLabel(ImagePlus imp, ImagePlus label, String objectName) {

		CLIJ2 clij2 = CLIJ2.getInstance();

		ClearCLBuffer lab = clij2.push(label);
		ClearCLBuffer image = clij2.push(imp);
		ClearCLBuffer centroids = clij2.create(lab);
		ClearCLBuffer overlapMap = clij2.create(lab);

		ResultsTable results = new ResultsTable();
		
		// use centroids so each label is only present in 1 x region
		clij2.reduceLabelsToCentroids(lab, centroids);

		ResultsTable rt = new ResultsTable();

		clij2.labelOverlapCountMap(image, centroids, overlapMap);
		clij2.statisticsOfLabelledPixels(image, overlapMap, rt);

		results.setColumn("Label", rt.getColumnAsVariables("Label"));
		results.setColumn("Number_Of_" + objectName + "_Objects", rt.getColumnAsVariables("Max"));

		lab.close();
		centroids.close();
		overlapMap.close();
		
		return results;
	}



	
	/**
	 * Calculates the band that each label resides within.
	 * @param bands
	 * @param labels
	 * @return
	 */
	public ResultsTable parentBand(List<ClearCLBuffer> bands, ImagePlus labels, String bandType) {

		ResultsTable results = new ResultsTable();
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		ClearCLBuffer relabelledBands = clij2.create(bands.get(0));
		ClearCLBuffer temp = clij2.create(relabelledBands);
		
		AtomicInteger counter = new AtomicInteger(1);
		
		// set all pixels for each band type = counter value.
		// and combine all of the band types together into a single buffer once re-labeled. 
		bands.forEach(e -> {
			int count = counter.getAndIncrement();
			clij2.set(e, (double) count);
			clij2.addImages(e, relabelledBands, temp);
			clij2.copy(temp, relabelledBands);
		});
		
		
		final IntensityMeasures im = new IntensityMeasures(clij2.pull(relabelledBands), labels);
		
		results.setColumn("Label", im.getMax().getColumnAsVariables("Label"));
		results.setColumn("Parent_Object_ID_" + bandType,im.getMax().getColumnAsVariables("Max"));
		
		return results;
	}

	

	
	/**
	 * Calculates the imp2 object that imp1 resides within.
	 * 
	 * @param imp1
	 * @param imp2
	 * @return
	 */
	public ResultsTable parentLabel(ImagePlus imp1, ImagePlus imp2) {
		
		ResultsBuilder rb = new ResultsBuilder();
		final IntensityMeasures im = new IntensityMeasures(imp2, imp1);
		rb.addResult(im.getMax());
	
		return rb.getResultsTable();
	}

}
