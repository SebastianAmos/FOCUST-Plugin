package clcm.focust;

import org.scijava.command.Command;
import org.scijava.plugin.Plugin;

@Plugin(type = Command.class, menuPath = "Plugins>FOCUST>FOCUST")
public class FOCUSTCommand implements Command {
	
	@Override
	public final void run() {
		FOCUST.instance().run();
	} 

}
