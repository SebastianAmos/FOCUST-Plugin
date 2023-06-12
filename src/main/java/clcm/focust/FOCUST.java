
package clcm.focust;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.scijava.command.Command;
import org.scijava.plugin.Plugin;

/**
 * TODO 
 * Class javadoc
 *
 */
@Plugin(type = Command.class, label = "FOCUST", menuPath = "Plugins>FOCUST")
public class FOCUST implements Command {

/** TODO:  public fields. Should be private. */
public static FutureTask<JFileChooser> futureFileChooser = new FutureTask<>(JFileChooser::new);	
public static File[] imageFiles;
public static String inputDir = "";
public static String outputDir = "";
public static JFileChooser fileChooser = null;
public static Path inputPath;


	/**
	 * Launch the main gui for FOCUST.
	 * @param args the command line arguments	
	 */
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
	

	public static void fileFinder() {
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
			String imagePathString = imageFiles[0].getParent();
			inputPath = Paths.get(imagePathString);
			
		} else {
			return;
		}	
	}
}