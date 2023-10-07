package clcm.focust;


import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;

public interface PreProcess {

	ClearCLBuffer background(ClearCLBuffer input, BackgroundType backgroundType);
	ClearCLBuffer filter(ClearCLBuffer input, FilterType filterType);
	ClearCLBuffer threshold(ClearCLBuffer input, ThresholdType thresholdType);

	//void method(ImagePlus imp, MethodType methodType);
	
	
}

//void filter(ImagePlus imp, BackgroundType backgroundType, FilterType filterType);

//void segment(ImagePlus imp, BackgroundType backgroundType, FilterType filterType, MethodType methodType);