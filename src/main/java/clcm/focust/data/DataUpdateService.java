package clcm.focust.data;
/**
 * 
 * Implemented by a class which can store and update stored data.
 *
 * @param <T> the data type
 */
public interface DataUpdateService<K,T> {
	
	/**
	 * Update the DataUpdateService with the new (replacement) data.
	 * @param newData to update with
	 */
	void notifyUpdated(K key, T newData);
	
	/**
	 * Update the DataUpdateService with the deleted data.
	 */
	void notifyDeleted(K key, Class<?> clazz);
}
