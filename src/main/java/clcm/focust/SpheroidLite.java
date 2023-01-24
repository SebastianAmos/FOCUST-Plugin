package clcm.focust;

import java.io.File;
import org.scijava.ItemVisibility;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import ij.IJ;


@Plugin(type = Command.class, label = "FOCUST: Spheroid Analysis Lite", menuPath = "Plugins>FOCUST>Lite>Spheroid Analysis")

public class SpheroidLite implements Command {
	
	@Parameter(label = "Select Input Directory",style="directory")
	private File[] inputFiles;
	
	@Parameter(label = "Select Output Directory", style = "directory", required = false)
	private File outputSelect;
	
	@Parameter(label = "Total Number of Channels/Image?", choices = {"1", "2", "3", "4"})
	private String totalChannelNum;
	
	@Parameter(label = "Select Primary Object Channel:", min = "1", max = "4")
	private Integer primaryChannelChoice = 1;
	
	@Parameter(label = "Select Secondary Object Channel:", min = "1", max = "4")
	private Integer secondaryChannelChoice = 4;
	
	@Parameter(label = "Name Channel 2:", style = "text field", required = false)
	private String channel2Name;
	
	@Parameter(label = "Name Channel 3:", style = "text field", required = false)
	private String channel3Name;
	
	@Parameter(label = "Name Channel 4:", style = "text field", required = false)
	private String channel4Name;
	
	@Parameter(label = "Grouping Info?", style = "text field", required = false)
	private String groupName;
	
	@Parameter(label = "Select Segmentation Parameter File", style = "directory", required = false)
	private File segParameterFile;
	
	// channel fields here
	//@Parameter(label = "Background Subtraction Radius:", min = "0f", max = "1000f", required = false)
	//private Float primaryBGSubtraction = 1.0f;
	
	//@Parameter(label = "3D Gaussian Blur:", min = "0")
	//private Double gBlurx;
	
	
	@Parameter(visibility = ItemVisibility.MESSAGE, required = false)
	private final String runMessage = "Click 'OK' when you are ready to run analysis!";
	
	
	// executes when "ok" is pressed on the dialog box.
	
	// where channel names and grouping info are null, set variable contents to "C2, C3, C4 and "" - respectively.
	
	@Override
	public void run() {
		IJ.log("running");
		
		// run loaded parameter file interpreter here to extract variable options
		// run segmentation function here.
		
	}

}
