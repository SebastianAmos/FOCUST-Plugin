package clcm.focust.utility;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.widget.Button;


// Use callback parameter to link fucntions to button change states
@Plugin(type = Command.class, label = "Speckle Analysis Lite", menuPath = "Plugins>FOCUST>Lite>Speckle Analysis")
public class SpeckleLite implements Command {
	
	@Parameter(label = "Select Input Directory: ", style ="directory" )
	private Button inputSelect;

	@Parameter(label = "Select Output Directory: ", style = "directory")
	private Button outputSelect;
	
	
	
	
	
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
