package clcm.focust.segmentation;

import clcm.focust.filter.BackgroundType;
import clcm.focust.filter.FilterType;
import clcm.focust.parameters.ObjectParameters;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.threshold.ThresholdType;
import ij.ImagePlus;
import ij.ImageStack;
import inra.ijpb.binary.BinaryImages;
import inra.ijpb.morphology.MinimaAndMaxima3D;
import inra.ijpb.morphology.Morphology;
import inra.ijpb.morphology.Strel3D;
import inra.ijpb.watershed.Watershed;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

public class MorphologicalWatershedObject implements Method {

    @Override
    public ImagePlus apply(CLIJ2 clij2, ImagePlus imp, BackgroundType background, FilterType filter, ThresholdType threshold, ObjectParameters parameters, ParameterCollection parameterCollection) {

        ClearCLBuffer input = clij2.push(imp);

        ClearCLBuffer bg = background.getFilter().apply(clij2, input, parameters.getBackgroundParameters().getSigma1(),
                parameters.getBackgroundParameters().getSigma2());
        ClearCLBuffer filtered = filter.getFilter().apply(clij2, bg, parameters.getFilterParameters().getSigma1(),
                parameters.getFilterParameters().getSigma2());

        ImagePlus img = clij2.pull(filtered);

        boolean dams = false;

        // Tolerance = Method Parameters Sigma X
        double tolerance = parameters.getMethodParameters().getSigma().getX(); // Use around 2000-3000 for 16-bit images.

        // Connectivity = Method Parameters Sigma Y
        int connectivity = (int) Math.round(parameters.getMethodParameters().getSigma().getY()); // Can be either 6 or 26.

        // Gradient radius = size of morphological gradient to be applied before morpho operations.
        int gradient_radius = (int) Math.round(parameters.getMethodParameters().getSigma().getZ()); // Can be either 0 or 1.


        Strel3D strel = Strel3D.Shape.CUBE.fromRadius(gradient_radius);

        // apply to input image
        ImageStack image = Morphology.gradient(img.getStack(), strel);


        ImageStack min = MinimaAndMaxima3D.extendedMinima(image, tolerance, connectivity);
        ImageStack imposeMin = MinimaAndMaxima3D.imposeMinima(image, min, connectivity);
        ImageStack labelledMin = BinaryImages.componentsLabeling(min, connectivity, 32);
        ImageStack result = Watershed.computeWatershed(imposeMin, labelledMin, connectivity, dams);
        ClearCLBuffer segmented = clij2.push(new ImagePlus("", result));
        ImagePlus output = Segmentation.pullAndSetDisplay(clij2, segmented, imp.getCalibration(), parameterCollection);

        input.close();
        bg.close();
        filtered.close();


        return output;
    }

}