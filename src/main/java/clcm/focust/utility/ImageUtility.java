package clcm.focust.utility;

import ij.IJ;
import ij.ImagePlus;
import loci.formats.FormatException;
import loci.plugins.BF;
import loci.plugins.in.ImagePlusReader;
import loci.plugins.in.ImporterOptions;

import java.io.IOException;

import static clcm.focust.utility.SwingIJLoggerUtils.ijLog;

public class ImageUtility {

    /**
     * Bio-Formats reader to open the first image in a series of compatible formats.
     * Implementing this way to avoid the importer window disrupting processing.
     *
     * @param path Path to the image file.
     * @return First ImagePlus object from the series if multi-series.
     */
    public static ImagePlus openImage(String path) {

        try {
            ImporterOptions options = new ImporterOptions();
            options.setId(path);
            options.setWindowless(true);
            options.setQuiet(true);
            ImagePlus[] imps = BF.openImagePlus(options);
            return imps[0];
        } catch (FormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}