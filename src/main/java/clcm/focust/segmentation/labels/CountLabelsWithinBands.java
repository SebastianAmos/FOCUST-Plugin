package clcm.focust.segmentation.labels;

import java.util.List;

import ij.ImagePlus;
import ij.measure.ResultsTable;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public class CountLabelsWithinBands {

	
	public ResultsTable runBuffers(List<ClearCLBuffer> bands, ImagePlus label, String colHeader) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		ClearCLBuffer lab = clij2.push(label);
		ClearCLBuffer centroids = clij2.create(lab);
		ClearCLBuffer overlapMap = clij2.create(lab);
		
		// init at 1 = core of banding
		int count = 1;
	
		ResultsTable results = new ResultsTable();
		
		clij2.reduceLabelsToCentroids(lab, centroids);
		
		// for each band in the list create a results table to hold data
		for (ClearCLBuffer band : bands) {

			ResultsTable rt = new ResultsTable();
			
			clij2.labelOverlapCountMap(band, centroids, overlapMap);
			clij2.statisticsOfLabelledPixels(band, overlapMap, rt);
			
			results.setColumn("Label", rt.getColumnAsVariables("Label"));
			results.setColumn(colHeader + "_band_" + count, rt.getColumnAsVariables("Max"));
			
			count++;
		}
		
		lab.close();
		centroids.close();
		overlapMap.close();
		
		
		return results;
	}
	
public ResultsTable runImagePlus(ImagePlus imp, ImagePlus label, String colHeader) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		ClearCLBuffer lab = clij2.push(label);
		ClearCLBuffer image = clij2.push(imp);
		ClearCLBuffer centroids = clij2.create(lab);
		ClearCLBuffer overlapMap = clij2.create(lab);

		ResultsTable results = new ResultsTable();

		clij2.reduceLabelsToCentroids(lab, centroids);

		// for each band in the list create a results table to hold data


		ResultsTable rt = new ResultsTable();

		clij2.labelOverlapCountMap(image, centroids, overlapMap);
		clij2.statisticsOfLabelledPixels(image, overlapMap, rt);

		results.setColumn("Label", rt.getColumnAsVariables("Label"));
		results.setColumn(colHeader, rt.getColumnAsVariables("Max"));




		lab.close();
		centroids.close();
		overlapMap.close();


		return results;
}




}
