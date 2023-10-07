package clcm.focust;

import ij.ImagePlus;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clijx.morpholibj.MorphoLibJMarkerControlledWatershed;




public class MaximaSegmentation implements PreProcess{

	CLIJ2 clij2 = CLIJ2.getInstance();
	private ClearCLBuffer filteredOutput;
	private ClearCLBuffer backgroundOutput;
	private ClearCLBuffer thresholdOutput;
	
	
	@Override
	public ClearCLBuffer background(ClearCLBuffer input, BackgroundType backgroundType) {
		switch(backgroundType) {
		case None:
			backgroundOutput = input;
			break;
		case Default:
			break;
		case DoG:
			backgroundOutput = DifferenceOfGaussian.run(input, 0, 0, 0, 0, 0, 0);
			break;
		case TopHat:
			backgroundOutput = TopHat.run(input, 0, 0, 0);
		}
		return backgroundOutput;
	}

	@Override
	public ClearCLBuffer filter(ClearCLBuffer input, FilterType filterType) {
		switch (filterType) {
		case None:
			filteredOutput = input;
			break;
		case GaussianBlur:
			filteredOutput = GaussianBlur.run(input, 0, 0, 0);
			break;
		case DoG:
			filteredOutput = DifferenceOfGaussian.run(input, 0, 0, 0, 0, 0, 0);
			break;
		case Median:
			filteredOutput = input; //*** method not implemented yet.
			break;
		case Mean:
			filteredOutput = input; //*** method not implemented yet.
			break;
		}
		return filteredOutput;
	}
	

	@Override
	public ClearCLBuffer threshold(ClearCLBuffer input, ThresholdType thresholdType) {
		switch (thresholdType) {
		case Otsu:
			thresholdOutput = filteredOutput;
			break;
		case GC:
			
			break;
		case Yen:
			
			break;
		case Huang:
			
			break;

		}
		return thresholdOutput;
	}
	

	public ImagePlus run(ImagePlus imp) {
		
		ClearCLBuffer input = clij2.push(imp);
		ClearCLBuffer bg = clij2.create(input);
		ClearCLBuffer filtered = clij2.create(input);
		ClearCLBuffer inverted = clij2.create(input);
		ClearCLBuffer maxima = clij2.create(input);
		ClearCLBuffer labelled = clij2.create(input);
		ClearCLBuffer segmented = clij2.create(input);
		
		bg = backgroundOutput;
		filtered = filteredOutput;
		
		clij2.invert(filtered, inverted);		
		clij2.detectMaxima3DBox(filtered, maxima, 0, 0, 0); // *** How to pass the gui inputs?
		clij2.labelSpots(maxima, labelled);
		MorphoLibJMarkerControlledWatershed.morphoLibJMarkerControlledWatershed(clij2, inverted, labelled, thresholdOutput, segmented);
		ImagePlus output = clij2.pull(segmented);
		
		clij2.clear();
		
		return output;
	}
	
}
