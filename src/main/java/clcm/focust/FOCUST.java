
package clcm.focust;


import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.scijava.command.Command;
import org.scijava.menu.MenuConstants;
import org.scijava.plugin.Menu;
import org.scijava.plugin.Plugin;


@Plugin(type = Command.class, menuPath = "Plugins>FOCUST")
public class FOCUST implements Command {


public static FutureTask<JFileChooser> futureFileChooser = new FutureTask<>(JFileChooser::new);	
public static File[] imageFiles;
public static String storeDir = "";

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
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(futureFileChooser);
	} 

	@Override
	public void run() {
		SwingUtilities.invokeLater(() -> {
			MainScreen MainGui = new MainScreen();
			MainGui.setVisible(true);
			MainGui.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		});
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(futureFileChooser);
	}	
	

	public static void FileFinder() {
		
		JFileChooser fileChooser = null;
		try {
			fileChooser = futureFileChooser.get();
		} catch (InterruptedException | ExecutionException e1) {
			e1.printStackTrace();
		}
		
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setDialogTitle("Select a Directory or File(s):");
		
		// abort if nothing selected or return the selected files 
		int returnValue = fileChooser.showOpenDialog(null);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			imageFiles = fileChooser.getSelectedFiles();
		} else {
			return;
		}	
	}
}