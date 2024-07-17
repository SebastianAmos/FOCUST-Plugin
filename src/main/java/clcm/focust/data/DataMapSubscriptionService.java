package clcm.focust.data;

import java.util.Optional;

/**
 * Use this interface to subscribe to updates of a type of data.
 *
 * @param <T> the data type
 */
public interface DataMapSubscriptionService<K,T> {
	
	/**
	 * Subscribe this listener to updates for a key.
	 * @param key of object
	 * @param listener to subscribe
	 */
	void registerListener(K key, DataListener<K,T> listener);
	
	/**
	 * Unsubscribe from updates for a key.
	 * @param key of object
	 * @param listener to unsub
	 */
	void deregisterListener(K key, DataListener<K,T> listener);

	/** Register a listener that listens to all keys. */
	void registerAllKeysListener(DataListener<K,T> speckleResultsHandler);
	
	/** Deregister a listener that listens to all keys. */
	void deregisterAllKeysListener(DataListener<K,T> speckleResultsHandler);

}
