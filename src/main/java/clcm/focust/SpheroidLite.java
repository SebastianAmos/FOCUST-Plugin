package clcm.focust;

import ij.IJ;
import org.scijava.ItemVisibility;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import java.io.File;


@Plugin(type = Command.class, label = "FOCUST: Spheroid Analysis Lite", menuPath = "Plugins>FOCUST>Lite>Spheroid Analysis")

public class SpheroidLite implements Command {

    @Parameter(visibility = ItemVisibility.MESSAGE, required = false)
    private final String runMessage = "Click 'OK' when you are ready to run analysis!";
    @Parameter(label = "Input Directory", style = "directory")
    private File inputFiles;
    @Parameter(label = "Output Directory", style = "directory", required = false)
    private File outputSelect;
    @Parameter(label = "How Many Channels?", choices = {"1", "2", "3", "4"})
    private String totalChannelNum;
    @Parameter(label = "Primary Object Channel:", choices = {"1", "2", "3", "4"})
    private String primaryChannelChoice;
    @Parameter(label = "Secondary Object Channel:", choices = {"1", "2", "3", "4"})
    private String secondaryChannelChoice;
    @Parameter(label = "Name Channel 2:", style = "text field", required = false)
    private String channel2Name;
    @Parameter(label = "Name Channel 3:", style = "text field", required = false)
    private String channel3Name;
    @Parameter(label = "Name Channel 4:", style = "text field", required = false)
    private String channel4Name;
    @Parameter(label = "Grouping Info?", style = "text field", required = false)
    private String groupName;

    // channel fields here
    //@Parameter(label = "Background Subtraction Radius:", min = "0f", max = "1000f", required = false)
    //private Float primaryBGSubtraction = 1.0f;

    //@Parameter(label = "3D Gaussian Blur:", min = "0")
    //private Double gBlurx;
    @Parameter(label = "Select Parameter File", style = "directory", required = false)
    private File segParameterFile;

    // where channel names and grouping info are null, set variable contents to "C2, C3, C4 and "" - respectively.
    // executes when "ok" is pressed on the dialog box.
    @Override
    public void run() {
        IJ.log("running");

        // run loaded parameter file interpreter here to extract variable options
        // run segmentation function here.

    }

}
