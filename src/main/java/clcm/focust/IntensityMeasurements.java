package clcm.focust;

import ij.IJ;
import ij.ImagePlus;
import ij.macro.Variable;
import ij.measure.ResultsTable;
import inra.ijpb.measure.IntensityMeasures;


public class IntensityMeasurements {
	
	// Parameter for "input" can be passed in using the public "channel" array that contains the original split channels of the active image . 
	// Parameter for "label" can be passed in using the ImagePlus object that corresponds to the segmented output. i.e. secondaryObjectSpheroid. 
	
	public static ResultsTable Process(ImagePlus input, ImagePlus label) {
		
		// Instance the IntensityMeasures class from MorpholibJ
		final IntensityMeasures im = new IntensityMeasures(input, label);
		ResultsTable table = new ResultsTable();  // move this into Process function otherwise it might not reset between images in batch.
		ResultsTable rtVolume = new ResultsTable();
		ResultsTable rtMean = new ResultsTable();
		IJ.log("input image name is: " + input.getTitle());
		IJ.log("label image name is: " + label.getTitle());
		
		rtVolume = im.getVolume();
		rtMean = im.getMean();

		
		// calculate Integrated Density
		double[] vol = rtVolume.getColumnAsDoubles(0);
		//IJ.log("vol array is: " + vol);
		double[] intMean = rtMean.getColumnAsDoubles(0);
		//IJ.log("mean array is: " + intMean);
		Variable[] intDen = new Variable[vol.length];
		
		
		// test to see if writing to table vs array fixes overwrite problem
		for (int i = 0; i < vol.length; i++) {
			intDen[i] = new Variable(vol[i] * intMean[i]);
			//double volVar = vol[i];
			//double meanVar = intMean[i];
			//double intDenVar = volVar * meanVar; 
			//rtVolume.addValue("IntDen", intDenVar);
		}
		
		Variable[] labArray = rtMean.getColumnAsVariables("Label");
		Variable[] volumeArray = rtVolume.getColumnAsVariables("Volume");
		Variable[] meanIntensityArray = rtMean.getColumnAsVariables("Mean");
		//Variable[] IntDenArray = rtVolume.getColumnAsVariables("IntDen");
		
		
		table.setColumn("Label", labArray);
		table.setColumn("Mean_Intensity", meanIntensityArray);
		table.setColumn("Volume", volumeArray);
		table.setColumn("IntDen", intDen);
		//table.setColumn("IntDen", IntDenArray);
		return table;
	}
}
