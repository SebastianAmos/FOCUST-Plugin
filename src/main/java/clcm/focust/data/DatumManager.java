package clcm.focust.data;

import java.util.concurrent.ExecutorService;

/**
 * TODO: Don't extend DataMapManager; lazy workaround. any class that is not
 * abstract should be final.
 */
public class DatumManager<T extends DataObject> extends DataMapManager<DataConstants.Datum, T>
		implements DatumService<T>, DatumSubscriptionService<T>, DatumUpdateService<T> {

	/** DataMapManager, except, one Datum. */
	public DatumManager(ExecutorService anExecutorService) {
		super(anExecutorService);
	}
}
