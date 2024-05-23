package clcm.focust.segmentation;

import clcm.focust.filter.BackgroundType;
import clcm.focust.filter.FilterType;
import clcm.focust.parameters.ObjectParameters;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.threshold.ThresholdType;
import ij.ImagePlus;
import net.haesleinhuepf.clij2.CLIJ2;

public class DistanceTransform implements Method {
    @Override
    public ImagePlus apply(CLIJ2 clij2, ImagePlus imp, BackgroundType background, FilterType filter, ThresholdType threshold, ObjectParameters parameters, ParameterCollection parameterCollection) {
        return null;
    }
}
