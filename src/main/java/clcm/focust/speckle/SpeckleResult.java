package clcm.focust.speckle;

import java.util.Map;

import clcm.focust.data.DataObject;
import lombok.Builder;
import lombok.Value;
import ij.measure.ResultsTable;

/**
 * Speckle Results. Prefer compositon over inheritance. 
 *
 */
@Builder
@Value
public class SpeckleResult implements DataObject{
	private final Map<SpeckleType,ResultsTable> results;
}
