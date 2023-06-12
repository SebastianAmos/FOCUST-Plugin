package clcm.focust.data;

/**
 * Use this inteface to subscribe to a {@link DataSubscriptionService}
 *
 * @param <T> the data to listen to.
 */
public interface DataListener<K,T> {

	/**
	 * Inform the subscribed class that the piece of data has been changed.
	 * @param newData the new data
	 */
	void dataUpdated(K key, T newData);
	
	/**
	 * Inform the subscribed class that the piece of data has been changed.
	 */
	void dataDeleted(K key);
}
