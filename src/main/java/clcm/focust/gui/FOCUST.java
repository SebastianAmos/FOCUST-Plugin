
package clcm.focust.gui;

import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.scijava.Context;
import org.scijava.command.Command;
import org.scijava.command.ContextCommand;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.SciJavaPlugin;
import org.scijava.ui.UIService;

import ij.plugin.PlugIn;

@Plugin(type = Command.class, menuPath = "Plugins>FOCUST")
public class FOCUST implements Command {
	/*
	 * @Parameter private static MainScreen MainGui;
	 */
	@Parameter
	private UIService uiService;

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
