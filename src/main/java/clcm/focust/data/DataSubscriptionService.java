package clcm.focust.data;

/**
 * Use this interface to subscribe to updates of a type of data.
 *
 * @param <T> the data type
 */
public interface DataSubscriptionService<K,T> {
	
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

}
