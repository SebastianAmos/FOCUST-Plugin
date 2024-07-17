
package clcm.focust;


import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import clcm.focust.data.DatumManager;
import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.gui.MainScreen2;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.service.ParameterModeService;
import clcm.focust.utility.CheckPlugins;

public final class FOCUST {

	private List<FOCUSTService> services;

	/** Data manager for runtime. */
	private final DatumManager<ParameterCollection> paramManager; 
	private final DatumManager<SegmentedChannels> segmentedChannelsManager;


	/**
	 * Constructor. Use {@link #instance()}
	 */
	private FOCUST() {
		services = new ArrayList<>();
		ExecutorService dataExecService = new FOCUSTExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

		/* Data managers. */
		paramManager = new DatumManager<>(dataExecService);
		segmentedChannelsManager = new DatumManager<>(dataExecService);
		

		/* Services - Speckle. */
		services.add(new ParameterModeService(paramManager, segmentedChannelsManager));

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
			MainScreen2 MainGui = new MainScreen2(paramManager);
			MainGui.setLocationRelativeTo(null);
			MainGui.setVisible(true);
			MainGui.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		});

		ExecutorService executor =  new FOCUSTExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	}

	/**
	 * Thread-safe instance accessor for singleton
	 * 
	 * @return the singleton
	 */
	public static FOCUST instance() {
		return InstanceHolder.INSTANCE;
	}


	public DatumManager<SegmentedChannels> getSegmentedChannelsManager() {
		return segmentedChannelsManager;
	}

}