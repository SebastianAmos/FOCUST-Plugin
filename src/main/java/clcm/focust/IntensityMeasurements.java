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
	
	
	// Parameter for "input" can be passed in using the public "channel" array that contain the original split channels of the active image . 
	// Parameter for "label" can be passed in using the ImagePlus object that corresponds to the segmented output. i.e. primaryObjectSpheroid. 
	public static ResultsTable Process(ImagePlus input, ImagePlus label) {
		
		// Instance the IntensityMeasures class from MorpholibJ
		final IntensityMeasures im = new IntensityMeasures(input, label);
		
		
		// Currently doesn't work!!!!!! 
		ResultsTable rtVolume = im.getVolume();
		ResultsTable rtMean = im.getMean();
		rtMean.show("MeanTable");
		Variable[] labArray = rtMean.getColumnAsVariables("Label");
		Variable[] volumeArray = rtVolume.getColumnAsVariables("Volume");
		Variable[] meanIntensityArray = rtMean.getColumnAsVariables("Mean");
		//IJ.log(Integer.toString(meanIntensityArray.length));
		table.setColumn("Label", labArray);
		table.setColumn("IntensityMean", meanIntensityArray);
		table.setColumn("Volume", volumeArray);
		
				
		
		/* >> Alternative method is to iterate through each row and build the table up manually. 
		 * for (int i = 0; i < im.getMean().getCounter(); i++) { table.addRow();
		 * table.setValue("Mean", i, mean[i]); }
		 * 
		 * table.addValue("Mean_Intensity", im.getMean());
		 */
	
		return table;
	}
	

}
