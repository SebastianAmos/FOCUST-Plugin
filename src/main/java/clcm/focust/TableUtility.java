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

/**
 * This helper class contains helper methods for calculating intensities and saving results tables. 
 */

public class TableUtility {

/**
 * This method calculates the intensity of the input image for each object in the label image.
 * IntensityMeasures instanced from MorphoLibJ.	
 * 
 * @param input 
 * 			A raw image containing intensity data. Can be passed from the current image's channels[]. 
 * @param label 
 * 			A labelled (segmented) image.
 * 
 * @return A results table containing label, mean_intensity, volume, max and IntDen columns.
 */
	
	public static ResultsTable processIntensity(ImagePlus input, ImagePlus label) {
		
		final IntensityMeasures im = new IntensityMeasures(input, label);
		ResultsTable table = new ResultsTable();
		ResultsTable rtVolume = new ResultsTable();
		ResultsTable rtMean = new ResultsTable();
		ResultsTable rtMax = new ResultsTable();
		
		rtVolume = im.getVolume();
		rtMean = im.getMean();
		rtMax = im.getMax();

		// calculate Integrated Density
		double[] vol = rtVolume.getColumnAsDoubles(0);
		double[] intMean = rtMean.getColumnAsDoubles(0);
		Variable[] intDen = new Variable[vol.length];
		
		for (int i = 0; i < vol.length; i++) {
			intDen[i] = new Variable(vol[i] * intMean[i]);
		}
		
		Variable[] labArray = rtMean.getColumnAsVariables("Label");
		Variable[] volumeArray = rtVolume.getColumnAsVariables("Volume");
		Variable[] meanIntensityArray = rtMean.getColumnAsVariables("Mean");
		Variable[] maxArray = rtMax.getColumnAsVariables("Max");
	
		table.setColumn("Label", labArray);
		table.setColumn("Mean_Intensity", meanIntensityArray);
		table.setColumn("Volume", volumeArray);
		table.setColumn("Max", maxArray);
		table.setColumn("IntDen", intDen);
		return table;
	}
	
	public static void saveTable(ResultsTable table, String dir, String name) {
		try { 
			table.saveAs(dir + name);
		} catch (IOException e) {
			e.printStackTrace();
			IJ.log("Cannot save results table: " + dir + name);
		}
	}
	
	
	/**
	 * Takes columns from RT and puts them into map - Column heading to List of variable. 
	 * @param rt
	 * @param columnDataMap
	 */
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
	
	public static void removeColumns(ResultsTable rt, String[] colsToRemove) {
		
		for (String col : colsToRemove) {
			rt.deleteColumn(col);
		}
		
	}
	
	
}