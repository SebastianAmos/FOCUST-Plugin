package clcm.focust.gui;

import java.io.File;
import org.scijava.ItemVisibility;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import ij.IJ;


@Plugin(type = Command.class, label = "FOCUST: Analysis Lite", menuPath = "Plugins>FOCUST>Lite>Analysis")

public class AnalysisLite implements Command {
	
	@Parameter(label = "Input Directory",style="directory")
	private File inputFiles;
	
	@Parameter(label = "Output Directory", style = "directory", required = false)
	private File outputSelect;
	
	@Parameter(label = "Name Channel 1 :", style = "text field", required = false)
	private String channel1Name;
	
	@Parameter(label = "Name Channel 2 :", style = "text field", required = false)
	private String channel2Name;
	
	@Parameter(label = "Name Channel 3 :", style = "text field", required = false)
	private String channel3Name;
	
	@Parameter(label = "Name Channel 4:", style = "text field", required = false)
	private String channel4Name;
	
	@Parameter(label = "Grouping Info?", style = "text field", required = false)
	private String groupName;
	
	@Parameter(label = "Select Parameter File", style = "file", required = true)
	private File segParameterFile;
	
	@Parameter(visibility = ItemVisibility.MESSAGE, required = false)
	private final String runMessage = "Click 'OK' when you are ready to run analysis!";
	
	
	@Override
	public void run() {
		IJ.log("running");
		
		// open and parse .json to ParameterCollection object.
		
		// if inputs are in the GD gui, use them over the paramcollection object. 
		
		
		
	}

}
