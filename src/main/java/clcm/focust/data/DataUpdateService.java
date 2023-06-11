package clcm.focust.data;
/**
 * 
 * Implemented by a class which can store and update stored data.
 *
 * @param <T> the data type
 */
public interface DataUpdateService<T> {
	
	/**
	 * Update the DataUpdateService with the new (replacement) data.
	 * @param newData to update with
	 */
	void notifyUpdated(T newData);
	
	/**
	 * Update the DataUpdateService with the deleted data.
	 */
	void notifyDeleted();
}
