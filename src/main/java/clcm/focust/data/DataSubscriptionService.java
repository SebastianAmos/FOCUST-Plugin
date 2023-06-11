package clcm.focust.data;

/**
 * Use this interface to subscribe to updates of a type of data.
 *
 * @param <T> the data type
 */
public interface DataSubscriptionService<T> {
	
	/**
	 * Subscribe this listener to updates.
	 * @param listener to subscribe
	 */
	void registerListener(DataListener<T> listener);
	
	/**
	 * Unsubscribe from updates.
	 * @param listener to unsub
	 */
	void deregisterListener(DataListener<T> listener);

}
