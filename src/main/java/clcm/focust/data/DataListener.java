package clcm.focust.data;

/**
 * Use this inteface to subscribe to a {@link DataSubscriptionService}
 *
 * @param <T> the data to listen to.
 */
public interface DataListener<T> {

	/**
	 * Inform the subscribed class that the piece of data has been changed.
	 * @param newData the new data
	 */
	void dataUpdated(T newData);
	
	/**
	 * Inform the subscribed class that the piece of data has been changed.
	 */
	void dataDeleted();
}
