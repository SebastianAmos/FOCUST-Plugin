package clcm.focust.speckle;

import java.util.Set;

import clcm.focust.data.DataObject;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ExpectedSpeckleResults implements DataObject {
	private final Set<String> results;
}
