package clcm.focust.segmentation;

import clcm.focust.parameters.BackgroundParameters;
import clcm.focust.parameters.FilterParameters;
import clcm.focust.parameters.MethodParameters;
import clcm.focust.parameters.ObjectParameters;
import clcm.focust.parameters.ParameterCollection;
import ij.IJ;
import ij.ImagePlus;
import ij.measure.Calibration;
import ij.plugin.LutLoader;
import ij.process.LUT;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public class Segmentation {
	
	public static ImagePlus run(ImagePlus input, ObjectParameters parameters, ParameterCollection parameterCollection) {
	
		// Extract parameter data
		BackgroundParameters background = parameters.getBackgroundParameters();
		FilterParameters filter = parameters.getFilterParameters();
		MethodParameters method = parameters.getMethodParameters();
		
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		// Run selected method with respective parameters
		ImagePlus output = method.getMethodType().getMethod().apply(clij2, 
				input, 
				background.getBackgroundType(), 
				filter.getFilterType(), 
				method.getThresholdType(), 
				parameters,
				parameterCollection);
		
		return output;
		
	}
	
	
	/**
	 * Runs the killBorders method, closes labelling gaps, pulls from GPU then sets the display range, calibration and LUT.
	 * 
	 * @param clij2
	 * @param buffer
	 * @param cal
	 * @return
	 */
	public static ImagePlus pullAndSetDisplay(CLIJ2 clij2, ClearCLBuffer buffer, Calibration cal, ParameterCollection parameters) {
		
		// Kill borders
		ClearCLBuffer killBorders = clij2.create(buffer);
		killBorders  = parameters.getKillBorderType().getKillBorders().apply(buffer);
		
		// Close label gaps
		ClearCLBuffer ordered = clij2.create(buffer);
		clij2.closeIndexGapsInLabelMap(killBorders, ordered);
		
		// set display max to max label value 
		double maxVal = clij2.maximumOfAllPixels(ordered);
		ImagePlus output = clij2.pull(ordered);
		
		// get LUT
		LUT lut = LutLoader.openLut(IJ.getDirectory("luts") + "glasbey_on_dark.lut");
		
		output.setLut(lut);
		output.setDisplayRange(0.0, maxVal);
		output.setCalibration(cal);
		
		killBorders.close();
		ordered.close();
		
		return output;
	}
	
	
	
	public static ImagePlus generateBySubtraction(ImagePlus larger, ImagePlus smaller, ParameterCollection params) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer largerBuffer = clij2.push(larger);
		ClearCLBuffer smallerBuffer = clij2.push(smaller);
		ClearCLBuffer largeMinusSmall = clij2.create(largerBuffer);
		clij2.binarySubtract(largerBuffer, smallerBuffer, largeMinusSmall);
		ImagePlus output = pullAndSetDisplay(clij2, largeMinusSmall, larger.getCalibration(), params);
		largerBuffer.close();
		smallerBuffer.close();
		largeMinusSmall.close();
		
		return output;
	}
	
	
}
