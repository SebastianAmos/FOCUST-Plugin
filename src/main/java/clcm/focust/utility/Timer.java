package clcm.focust.utility;

import clcm.focust.parameters.ParameterCollection;
import ij.IJ;
import lombok.Getter;
import lombok.Setter;

import static clcm.focust.utility.SwingIJLoggerUtils.ijLog;

public class Timer {

    private static long startTime;
    @Getter
    @Setter
    private static long totalTime;
    @Getter
    private static int nImgs;
    @Getter
    private static int currentImgIndex;

    public static void start() {
        startTime = System.currentTimeMillis();
    }

    public static void stop(ParameterCollection parameters){
        long stopTime = System.currentTimeMillis();
        long diff = (stopTime - startTime)/1000;
        ijLog("Image number "  + currentImgIndex + " processed in " + diff + " seconds.");
        Timer.total(diff, parameters);
    }

    // total time to process in msec
    public static void total(long imgTime, ParameterCollection parameters){
        totalTime = totalTime + imgTime;

        if (currentImgIndex == nImgs){
            ijLog("FOCUST Batch Completed.");
            ijLog("Total time to process " + nImgs + " images: " + totalTime + " seconds.");


            String dir;

            if(!parameters.getOutputDir().isEmpty()){
                dir = parameters.getOutputDir();
            } else {
                dir = parameters.getInputDir();
            }

            // Log window outputs were incomplete, delay save.
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            String logContents = IJ.getLog();
            String path = dir + "/FOCUST-Log.txt";
            IJ.saveString(logContents, path);

        }

    }

    public static void numberOfImages(int n){
        nImgs = n;
    }

    public static void currentImgIndex(int i){
        currentImgIndex = i;
    }


}
