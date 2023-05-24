package clcm.focust;

import ij.ImagePlus;
import ij.macro.Variable;
import ij.measure.ResultsTable;
import inra.ijpb.measure.IntensityMeasures;

/**
 * This helper class contains a method (process) for calculating intensity values.
 */
public class IntensityMeasurements {

/**
 * This method calculates the intensity of the input image for each object in the label image.
 * IntensityMeasures instanced from MorphoLibJ.	
 * 
 * @param input A raw image containing intensity data. Can be passed from the current image's channels[]. 
 * @param label A labelled (segmented) image.
 * 
 * @return A results table containing label, mean_intensity, volume and IntDen columns.
 */
	
	public static ResultsTable process(ImagePlus input, ImagePlus label) {
		
		final IntensityMeasures im = new IntensityMeasures(input, label);
		ResultsTable table = new ResultsTable();
		ResultsTable rtVolume = new ResultsTable();
		ResultsTable rtMean = new ResultsTable();
		
		rtVolume = im.getVolume();
		rtMean = im.getMean();

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
	
		table.setColumn("Label", labArray);
		table.setColumn("Mean_Intensity", meanIntensityArray);
		table.setColumn("Volume", volumeArray);
		table.setColumn("IntDen", intDen);
		return table;
	}
}