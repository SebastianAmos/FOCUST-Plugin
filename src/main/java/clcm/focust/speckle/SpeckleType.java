package clcm.focust.speckle;
/** Defines the various types of Speckle and associated parameters. */
public enum SpeckleType {
	PRIMARY("Primary_Objects_"), SECONDARY("Secondary_Objects_"), TERTIARY("Tertiary_Objects_");

	/** The prefix for file naming. */
	private final String prefix;

	/**
	 * Constructor.
	 * 
	 * @param aPrefix to set
	 */
	private SpeckleType(final String aPrefix) {
		prefix = aPrefix;
	}
}
