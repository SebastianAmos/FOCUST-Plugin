package clcm.focust.data;

import java.util.Optional;
/**
 * DataMapService for an individual object (hence Datum. )
 *
 * @param <T> the type of the object
 */
public interface DatumService<T extends DataObject> extends DataMapService<DataConstants.Datum, T> {
	default Optional<T> get() {
		return this.get(DataConstants.Datum.DATUM);
	}
}
