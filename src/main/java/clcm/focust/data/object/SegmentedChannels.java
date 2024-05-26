package clcm.focust.data.object;

import java.util.List;
import java.util.Optional;

import clcm.focust.data.DataObject;
import clcm.focust.parameters.ParameterCollection;
import ij.ImagePlus;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class SegmentedChannels implements DataObject {
	
	private final ImagePlus primary;
	private final ImagePlus secondary;
	private final Optional<ImagePlus> tertiary;
	private final List<ImagePlus> channels;
	
}
