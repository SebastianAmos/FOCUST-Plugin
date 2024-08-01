package clcm.focust.utility;

import clcm.focust.gui.OptimizeHelpers;
import clcm.focust.parameters.ObjectParameters;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.segmentation.Segmentation;
import ij.IJ;
import ij.ImagePlus;

public class OptimizeExecutor {

    private final ImageExecutor imageExecutor;
    private final OptimizeHelpers optimizeHelpers = new OptimizeHelpers();

    public OptimizeExecutor(int threads) {
        this.imageExecutor = new ImageExecutor(threads);
    }

    public void processSegmentation(ImagePlus img, ObjectParameters objectParameters,
                                    ParameterCollection parameters, String name,
                                    Boolean displayOriginal, Boolean withOverlay) {

        try {
            imageExecutor.submit(() -> {
                ImagePlus output = Segmentation.run(img, objectParameters, parameters);
                IJ.resetMinAndMax(output);
                output.setTitle(name + " Objects");
                if (displayOriginal) {
                    ImagePlus duplicate = img.duplicate();
                    ImagePlus objectDisplayOverlay = optimizeHelpers.processDisplay(duplicate, output, withOverlay);
                    objectDisplayOverlay.setTitle(name + " Display");
                    objectDisplayOverlay.show();
                }
                output.show();
                IJ.run("Tile");
            });
        } catch (Exception e) {
            IJ.showMessage("Error. Check there are images to process with the correct number of channels");
            e.printStackTrace();
        }

    }


}
