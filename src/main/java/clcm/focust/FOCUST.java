
package clcm.focust;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.scijava.command.Command;
import org.scijava.plugin.Plugin;

import clcm.focust.data.DataConstants;
import clcm.focust.data.DataMapManager;
import clcm.focust.data.DataMapUpdateService;
import clcm.focust.data.DatumManager;
import clcm.focust.speckle.ExpectedSpeckleResults;
import clcm.focust.speckle.SpeckleResult;
import clcm.focust.speckle.Speckles;
import clcm.focust.speckle.SpecklesConfiguration;
import clcm.focust.speckle.service.SpeckleProcessor;
import clcm.focust.speckle.service.SpeckleResultsHandlerService;
import clcm.focust.speckle.service.SpeckleService;

public final class FOCUST {

	public static FutureTask<JFileChooser> futureFileChooser = new FutureTask<>(JFileChooser::new);
	public static File[] imageFiles;
	public static String inputDir = "";
	public static String outputDir = "";
	public static JFileChooser fileChooser = null;
	public static Path inputPath;

	private List<FOCUSTService> services;

	/** Data manager for speckles. */
	private DataMapManager<String, Speckles> specklesManager;

	private DataMapManager<String, SpeckleResult> speckleResultsManager;

	private final DatumManager<ExpectedSpeckleResults> expectedSpeckleResultsManager;

	/** Data manager for runtime configuration. */
	private DatumManager<SpecklesConfiguration> configurationManager;

	/**
	 * Constructor. Use {@link #instance()}
	 */
	private FOCUST() {
		services = new ArrayList<>();
		ExecutorService dataExecService = Executors.newSingleThreadExecutor();

		/* Data managers. */
		specklesManager = new DataMapManager<>(dataExecService);
		speckleResultsManager = new DataMapManager<>(dataExecService);
		expectedSpeckleResultsManager = new DatumManager<>(dataExecService);
		configurationManager = new DatumManager<>(dataExecService);

		/* Services - Speckle. */
		services.add(new SpeckleProcessor(configurationManager, specklesManager, expectedSpeckleResultsManager));
		services.add(new SpeckleService(configurationManager, specklesManager, speckleResultsManager));
		services.add(new SpeckleResultsHandlerService(speckleResultsManager, expectedSpeckleResultsManager,
				configurationManager));
	}

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

	/**
	 * Do the tings.
	 */
	final void run() {
		services.forEach(FOCUSTService::init);
		/* Clean up our mess, to be good citizens. */
		Runtime.getRuntime().addShutdownHook(new Thread(() -> services.forEach(FOCUSTService::shutdown)));
		CheckPlugins.areAvailable();
		SwingUtilities.invokeLater(() -> {
			MainScreen MainGui = new MainScreen();
			MainGui.setLocationRelativeTo(null);
			MainGui.setVisible(true);
			MainGui.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		});
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(futureFileChooser);
	}

	/**
	 * Thread-safe instance accessor for singleton
	 * 
	 * @return the singleton
	 */
	public static FOCUST instance() {
		return InstanceHolder.INSTANCE;
	}


	public final DatumManager<SpecklesConfiguration> specklesConfigurationManager() {
		return configurationManager;
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