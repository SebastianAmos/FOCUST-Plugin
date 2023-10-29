package clcm.focust.speckle;

/**
 * Defines the various types of Speckle and associated offline configuration
 * parameters.
 */
public enum SpeckleType {
	
	//@formatter:off
	/** Primary type. */
	PRIMARY("Primary_Objects_", "PADDED_PRIMARY_"),
	/** Secondary type. */
	SECONDARY("Secondary_Objects_", "PADDED_SECONDARY_"),
    /** Tertiary type. */
	TERTIARY("Tertiary_Objects_", "PADDED_TERTIARY_");
	//@formatter:on

	/** The prefix for file naming. */
	private final String prefix;

	/** A more different prefix for file naming. */
	private final String paddedPrefix;

	/**
	 * Constructor.
	 * 
	 * @param aPrefix to set
	 */
	private SpeckleType(final String aPrefix, final String aPaddedPrefix) {
		prefix = aPrefix;
		paddedPrefix = aPaddedPrefix;
	}

	/** Getter for enum const prefix. */
	public String getPrefix() {
		return prefix;
	}

	/** Getter for enum const padding prefix. */
	public String getPaddedPrefix() {
		return paddedPrefix;
	}

}
