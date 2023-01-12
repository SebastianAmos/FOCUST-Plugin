
package clcm.focust.gui;


import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.scijava.command.Command;
import org.scijava.menu.MenuConstants;
import org.scijava.plugin.Menu;
import org.scijava.plugin.Plugin;


@Plugin(type = Command.class, menuPath = "Plugins>FOCUST>Run")

public class FOCUST implements Command {


/**
 * Launch the main gui for FOCUST.	
 */
 
// main for testing in IDE.
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			MainScreen MainGui = new MainScreen();
			MainGui.setVisible(true);
			MainGui.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		});
	} 

	@Override
	public void run() {
		SwingUtilities.invokeLater(() -> {
			MainScreen MainGui = new MainScreen();
			MainGui.setVisible(true);
			MainGui.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		});
	}	
}