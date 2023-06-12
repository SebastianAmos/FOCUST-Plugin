package clcm.focust.data;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Publish-subscribe model for data in a map format of a given type.
 *
 * @param <K> the key type
 * @param <T> the object type
 */
public final class DataMapManager<K extends Enum<K>, T>
		implements DataSubscriptionService<K, T>, DataService<K, T>, DataUpdateService<K, T> {

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

	/**
	 * Constructor.
	 * 
	 * @param the class of the key.
	 */
	public DataMapManager(Class<K> keyClazz) {
		listeners = new EnumMap<>(keyClazz);
		Arrays.stream(keyClazz.getEnumConstants()).forEach(k -> listeners.put(k, new HashSet<>()));
	}

	@Override
	public final void notifyUpdated(K key, T newData) {
		/* Receives new piece of data: 1. Store it. 2. Update subscribed classes. */
		LOGGER.fine(() -> String.format("Data received for key [%s] , Datum [%s]", key, newData));
		objects.put(key, newData);
		listeners.get(key).forEach(listener -> listener.dataUpdated(key, newData));
		LOGGER.finer(() -> String.format("Listeners notified for key [%s], Datum [%s]", key, newData));
	}

	@Override
	public final void notifyDeleted(K key, Class<?> clazz) {
		LOGGER.fine(() -> String.format("Data deleted for key [%s] ", key));
		objects.remove(key);
		listeners.get(key).forEach(listener -> listener.dataDeleted(key));
		LOGGER.finer(() -> String.format("Data delete notification sent for key [%s] by %s", key));
	}

	@Override
	public final Optional<T> get(K key) {
		return Optional.ofNullable(objects.get(key));
	}

	@Override
	public final  void registerListener(K key, DataListener<K, T> listener) {
		LOGGER.fine(() -> String.format("Listener %s subscribing for [%s]",listener, key));
		listeners.get(key).add(listener);
	}

	@Override
	public final void deregisterListener(K key, DataListener<K, T> listener) {
		LOGGER.fine(() -> String.format("Listener %s unsubscribing for [%s]",listener, key));
		listeners.get(key).remove(listener);
	}

}
