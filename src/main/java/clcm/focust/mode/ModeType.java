package clcm.focust.mode;

public enum ModeType {

	NONE(new ModeNone(), "None"),
	BASIC(new ModeBasic(), "Basic"),
	SPHEROID(new ModeSpheroid(), "Spheroid"),
	SINGLECELL(new ModeSingleCell(), "Single Cell"),
	SPECKLE(new ModeSpeckle(), "Speckle");
	
	private Mode mode;
	private String displayName;
	
	ModeType(Mode mode, String displayName) {
		this.mode = mode;
		this.displayName = displayName;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public String toString() {
		return displayName;
	}
	
}

