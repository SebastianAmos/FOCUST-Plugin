package clcm.focust.data;

import clcm.focust.data.DataConstants.Datum;

public interface DatumSubscriptionService<T extends DataObject>
		extends DataMapSubscriptionService<DataConstants.Datum, T> {
	default void registerListener(DataListener<Datum, T> listener) {
		this.registerListener(DataConstants.Datum.DATUM, listener);
	}

	default void deregisterListener(Datum key, DataListener<Datum, T> listener) {
		this.deregisterListener(DataConstants.Datum.DATUM, listener);
	}
}
