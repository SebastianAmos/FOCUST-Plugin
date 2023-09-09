package clcm.focust.speckle;

import java.nio.file.Path;

import clcm.focust.data.DataObject;
import lombok.Builder;
import lombok.Value;

/**
 * A Data object which holds the runtime configuration items for speckle
 * analysis. This is static configuration data that is not related to the image
 * analysis domain. Filenames, extenstions etc.
 */
@Builder
@Value
public class SpecklesConfiguration implements DataObject {
	private final Path inputDirectory;
	private final String killBordersText;
	private final boolean analysisMode;
}
