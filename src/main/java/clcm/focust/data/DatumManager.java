package clcm.focust.data;

/**
 * TODO: Don't extend DataMapManager; lazy workaround. any class that is not
 * abstract should be final.
 */
public class DatumManager<T extends DataObject> extends DataMapManager<DataConstants.Datum, T>
		implements DatumService<T>, DatumSubscriptionService<T>, DatumUpdateService<T> {

	public DatumManager() {
		super();
	}
}
