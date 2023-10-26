package clcm.focust;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import ij.IJ;
import ij.ImagePlus;
import ij.macro.Variable;
import ij.measure.ResultsTable;
import inra.ijpb.measure.IntensityMeasures;
import inra.ijpb.measure.ResultsBuilder;

/**
 * This helper class contains helper methods for calculating intensities and saving results tables. 
 */

public class TableUtility {

	

	public static void collectColumns(ResultsTable rt, Map<String, List<Variable>> columnDataMap) {

		String[] columnNames = rt.getHeadings();

		for (String name : columnNames) {

			// returns the list for that column header, or makes a new one if it doesn't exist yet.
			List<Variable> columnData = columnDataMap.getOrDefault(name, new ArrayList<>());

			// grab the entire column
			Variable[] columnVariables = rt.getColumnAsVariables(name);

			// add the array to the list	
			columnData.addAll(Arrays.asList(columnVariables));

			// update the map
			columnDataMap.put(name, columnData);

		}

	}



	public static ResultsTable intDen(ResultsTable volume, ResultsTable mean) {

		double[] vol = volume.getColumnAsDoubles(0);
		double[] intMean = mean.getColumnAsDoubles(0);

		Variable[] intDen = new Variable[vol.length];

		for (int i = 0; i < vol.length; i++) {
			intDen[i] = new Variable(vol[i] * intMean[i]);
		}

		mean.setColumn("IntDen", intDen);

		return mean;
	}



	/**
	 * This method calculates the intensity of the input image for each object in the label image.
	 * IntensityMeasures and ResultsBuilder instanced from MorphoLibJ.	
	 * 
	 * @param input 
	 * 			A raw image containing intensity data. Can be passed from the current image's channels[]. 
	 * @param label 
	 * 			A labelled (segmented) image.
	 * 
	 * @return A results table containing label, mean_intensity, volume, max and IntDen columns.
	 */
	public static ResultsTable processIntensity(ImagePlus input, ImagePlus label) {

		// constructs default table and appends any columns in supplied tables that don't yet exist.
		ResultsBuilder rb = new ResultsBuilder();
		final IntensityMeasures im = new IntensityMeasures(input, label);

		// get vol and mean separately so intden can be calculated without needing to compute twice.
		ResultsTable rtVol = im.getVolume();
		ResultsTable rtMean = im.getMean();
		rb.addResult(rtVol);
		rb.addResult(rtMean);
		rb.addResult(intDen(rtVol, rtMean));
		rb.addResult(im.getMax());
		rb.addResult(im.getMin());
		rb.addResult(im.getMedian());
		rb.addResult(im.getMode());
		rb.addResult(im.getSumOfVoxels());
		rb.addResult(im.getKurtosis());
		rb.addResult(im.getStdDev());
		ResultsTable table = rb.getResultsTable();
		return table; 
	}

	
	
	
	public static void removeColumns(ResultsTable rt, String[] colsToRemove) {

		for (String col : colsToRemove) {
			rt.deleteColumn(col);
		}

	}

	
	
	public static void saveTable(ResultsTable table, String dir, String name) {
		try { 
			table.saveAs(dir + name);
		} catch (IOException e) {
			e.printStackTrace();
			IJ.log("Cannot save results table: " + dir + name);
		}
	}

	
	//=================================================================================================
	// for single cell duplicate management
	//=================================================================================================
	
	
	
	/*
	 * combine the label-matched results tables together. label/id col must match where it needs to. 
	 */
	public ResultsTable compileSingleCellTables(ResultsTable primary, ResultsTable secondary, ResultsTable tertiary) {
		
		ResultsBuilder rb = new ResultsBuilder();
		
		rb.addResult(primary);
		rb.addResult(secondary);
		rb.addResult(tertiary);
		
		ResultsTable rt = rb.getResultsTable();
		
		return rt;
		
	}




}