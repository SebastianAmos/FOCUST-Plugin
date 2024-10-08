package clcm.focust.data;

import java.util.Optional;

/**
 * A way to access data in a central repository.
 */
public interface DataMapService<K, T> {
	
	/**
	 * Get the piece of data associated with this object.
	 * @param K the key
	 * @return the data
	 */
	Optional<T> get(K key);

}
