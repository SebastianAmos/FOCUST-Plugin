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

    public OptimizeExecutor() {
        this.imageExecutor = new ImageExecutor();
    }

    /**
     * Execute segmentation with ImageExecutor for multi-threading of optimization processing.
     * @param img
     * @param objectParameters
     * @param parameters
     * @param name
     * @param displayOriginal
     * @param withOverlay
     */
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
                    ImagePlus objectDisplayOverlay = optimizeHelpers.processDisplay(duplicate, output.duplicate(), withOverlay);
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
    public void shutdown() {
        imageExecutor.shutdown();
    }

}
