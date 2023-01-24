package clcm.focust;

import java.io.File;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;


@Plugin(type = Command.class, label = "Single Cell Analysis", menuPath = "Plugins>FOCUST>Lite>Single Cell Analysis")
public class SingleCellLite implements Command {

	
	
	@Parameter(label = "Browse", style = "directory")
	private File intputPath;
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	
	
}
