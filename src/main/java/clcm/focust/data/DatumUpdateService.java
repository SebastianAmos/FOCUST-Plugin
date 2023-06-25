package clcm.focust.data;

public interface DatumUpdateService<T extends DataObject> extends DataMapUpdateService<DataConstants.Datum, T> {
	default void notifyUpdated(T newData) {
		this.notifyUpdated(DataConstants.Datum.DATUM, newData);
	}
	default void notifyDeleted() {
		this.notifyDeleted(DataConstants.Datum.DATUM);
	}
}
