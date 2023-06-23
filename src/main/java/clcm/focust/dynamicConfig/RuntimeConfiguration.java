package clcm.focust.dynamicConfig;

import java.nio.file.Path;

import clcm.focust.data.DataObject;

/**
 * A Data object which holds the runtime configuration items for speckle
 * analysis. This is static configuration data that is not related to the image
 * analysis domain. Filenames, extenstions etc.
 */
public class RuntimeConfiguration implements DataObject {
	
	public RuntimeConfiguration(Path inputDirectory) {
		super();
		this.inputDirectory = inputDirectory;
	}

	private final Path inputDirectory;

	public Path getInputDirectory() {
		return inputDirectory;
	}

}
