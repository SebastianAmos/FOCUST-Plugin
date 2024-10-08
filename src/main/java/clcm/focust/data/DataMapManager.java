package clcm.focust.data;

import static clcm.focust.utility.SwingIJLoggerUtils.ijLog;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;


/**
 * Publish-subscribe model for data in a map format of a given type.
 * 
 * TODO: Consider a separate datamanager for individual keys instead of using
 * the Datum constant which is a bit unweildy.
 *
 * @param <K> the key type
 * @param <T> the object type
 */
public class DataMapManager<K, T extends DataObject>
		implements DataMapSubscriptionService<K, T>, DataMapService<K, T>, DataMapUpdateService<K, T> {

	/**
	 * The class logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(DataMapManager.class.getName());

	/**
	 * The object.
	 */
	private Map<K, T> objects = new HashMap<>();

	/** The registered Data listeners. */
	private Map<K, Set<DataListener<K, T>>> listeners;

	/** The registered data listeners for all keys. */
	private Set<DataListener<K, T>> allKeyListeners;

	private final ExecutorService updateExecutorService;

	/**
	 * Constructor.
	 * 
	 * @param anExecutorService to execute updates upon.
	 */
	public DataMapManager(final ExecutorService anExecutorService) {
		listeners = new HashMap<>();
		allKeyListeners = new HashSet<>();
		updateExecutorService = anExecutorService;
	}

	@Override
	public final void notifyUpdated(K key, T newData) {
		/* Receives new piece of data: 1. Store it. 2. Update subscribed classes. */
		LOGGER.fine(() -> String.format("Data received for key [%s] , Datum [%s]", key, newData));
		ijLog(String.format("Data received for key [%s] , Datum [%s]", key, newData));
		objects.put(key, newData);
		/* TODO Determine and handle overlap. */
		listeners.getOrDefault(key, new HashSet<>())
				.forEach(listener -> updateExecutorService.submit(() -> listener.dataUpdated(key, newData)));
		ijLog(String.format("Data received for key [%s] , Datum [%s]", key, newData));
		allKeyListeners.forEach(listener -> {
			updateExecutorService.submit(() -> {
				ijLog(String.format("Notifying [%s] , Datum [%s]", listener, newData));
				listener.dataUpdated(key, newData);
			});
		});

		// LOGGER.finer(() -> String.format("Listeners notified for key [%s], Datum
		// [%s]", key, newData));
	}

	@Override
	public final void notifyDeleted(K key) {
		LOGGER.fine(() -> String.format("Data deleted for key [%s] ", key));
		ijLog(String.format("Data deleted for key [%s]", key));
		objects.remove(key);

		listeners.getOrDefault(key, new HashSet<>())
				.forEach(listener -> updateExecutorService.submit(() -> listener.dataDeleted(key)));
		/* TODO Determine and handle overlap. */
		allKeyListeners.forEach(listener -> updateExecutorService.submit(() -> listener.dataDeleted(key)));

		LOGGER.finer(() -> String.format("Data delete notification sent for key [%s] by %s", key));
	}

	@Override
	public final Optional<T> get(K key) {
		return Optional.ofNullable(objects.get(key));
	}

	@Override
	public final void registerListener(K key, DataListener<K, T> listener) {
		ijLog(String.format("Listener %s registered for key [%s]", listener.getClass().getName(), key));
		LOGGER.fine(() -> String.format("Listener %s subscribing for [%s]", listener, key));
		listeners.computeIfAbsent(key, k -> new HashSet<>());
		listeners.get(key).add(listener);
	}

	@Override
	public final void registerAllKeysListener(DataListener<K, T> listener) {
		ijLog(String.format("Listener %s registered for all keys", listener.getClass().getName()));
		LOGGER.fine(() -> String.format("Listener %s subscribing for [All Keys]", listener.getClass().getName()));
		allKeyListeners.add(listener);
	}

	@Override
	public final void deregisterListener(K key, DataListener<K, T> listener) {
		ijLog(String.format("Listener %s deregistered for key [%s]", listener.getClass().getName(), key));
		LOGGER.fine(() -> String.format("Listener %s unsubscribing for [%s]", listener, key));
		Optional.ofNullable(listeners.get(key)).ifPresent(set -> set.remove(listener));
	}

	@Override
	public void deregisterAllKeysListener(DataListener<K, T> listener) {
		ijLog(String.format("Listener %s deregistered for all keys", listener.getClass().getName()));
		allKeyListeners.remove(listener);
	}

}
