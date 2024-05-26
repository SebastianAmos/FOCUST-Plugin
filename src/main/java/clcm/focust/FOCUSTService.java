package clcm.focust;

/**
 * A service required for focust to run. Started at focust initialistion. Stopped on shutdown.
 */
public interface FOCUSTService {
    /** Called at FOCUST start. */
	void init();
	/** Called at FOCUST shutdown. */
    void shutdown();
}
