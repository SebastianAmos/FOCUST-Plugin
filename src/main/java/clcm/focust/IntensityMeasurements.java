package clcm.focust;

import ij.IJ;
import ij.ImagePlus;
import ij.macro.Variable;
import ij.measure.ResultsTable;
import inra.ijpb.measure.IntensityMeasures;


public class IntensityMeasurements {

	static ImagePlus input; 
	static ImagePlus label; 
	static ResultsTable table = new ResultsTable(); 
	
	
	// Parameter for "input" can be passed in using the public "channel" array that contains the original split channels of the active image . 
	// Parameter for "label" can be passed in using the ImagePlus object that corresponds to the segmented output. i.e. primaryObjectSpheroid. 
	
	public static ResultsTable Process(ImagePlus input, ImagePlus label) {
		
		// Instance the IntensityMeasures class from MorpholibJ
		final IntensityMeasures im = new IntensityMeasures(input, label);
	 
		ResultsTable rtVolume = im.getVolume();
		ResultsTable rtMean = im.getMean();

		//IJ.log("index for label is: " + rtVolume.getColumnIndex("Label"));
		//IJ.log("index for volume is: " + rtVolume.getColumnIndex("Volume"));
		//IJ.log("Index for mean is: " + rtMean.getColumnIndex("Mean"));
		
		// calculate Integrated Density
		double[] vol = rtVolume.getColumnAsDoubles(0);
		//IJ.log("vol array is: " + vol);
		double[] intMean = rtMean.getColumnAsDoubles(0);
		//IJ.log("mean array is: " + intMean);
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
