package clcm.focust.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataManager<T> implements DataSubscriptionService<T>,DataService<T>,DataUpdateService<T> {

	/**
	 * The object.
	 */
	private T object = null;
	
	/** The registered Data listeners. */
	private List<DataListener<T>> listeners;
	
	/**
	 * Constructor.
	 */
    public DataManager() {
    	listeners = new ArrayList<>();
    }
	
	@Override
	public void notifyUpdated(T newData) {
		object = newData;
		listeners.forEach(l -> l.dataUpdated(newData));		
	}

	@Override
	public void notifyDeleted() {
		object=null;
		listeners.forEach(DataListener::dataDeleted);		
	}

	@Override
	public Optional<T> get() {
		return Optional.ofNullable(object);
	}

	@Override
	public void registerListener(DataListener<T> listener) {
		listeners.add(listener);
	}

	@Override
	public void deregisterListener(DataListener<T> listener) {
		listeners.remove(listener);		
	}

}
