
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

@Plugin(type = Command.class, label = "FOCUST", menuPath = "Plugins>FOCUST")
public final class FOCUST implements Command {

	public static FutureTask<JFileChooser> futureFileChooser = new FutureTask<>(JFileChooser::new);
	public static File[] imageFiles;
	public static String inputDir = "";
	public static String outputDir = "";
	public static JFileChooser fileChooser = null;
	public static Path inputPath;
	
	/**
	 * Constructor. Use  {@link #instance()}
	 */
	private FOCUST() {}	


	/**
	 * Thread-safe Singleton.
	 *
	 */
	private static class InstanceHolder {
		/** The singleton instance. */ 
		private static FOCUST INSTANCE = new FOCUST();;

		/** Constructor. */
		private InstanceHolder() {
			/* Empty. */ 
		}
	}

	/**
	 * Launch the main gui for FOCUST.
	 */
	public static void main(String[] args) {
		instance().run();
	}

	@Override
	public final void run() {
		SwingUtilities.invokeLater(() -> {
			MainScreen mainGui = new MainScreen();
			mainGui.setVisible(true);
			mainGui.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		});
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(futureFileChooser);
	}
	
	/**
	 * Thread-safe instance accessor for singleton
	 * @return the singleton
	 */
	public static FOCUST instance() {
		return InstanceHolder.INSTANCE;
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
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			imageFiles = fileChooser.getSelectedFiles();
			String imagePathString = imageFiles[0].getParent();
			inputPath = Paths.get(imagePathString);

		} else {
			return;
		}
	}
}