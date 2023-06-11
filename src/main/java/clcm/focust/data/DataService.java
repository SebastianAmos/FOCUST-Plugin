package clcm.focust.data;

import java.util.Optional;

/**
 * A way to access data in a central repository.
 */
public interface DataService<T> {
	
	/**
	 * Get the piece of data associated with this object.
	 * @return the data
	 */
	Optional<T> get();

}
