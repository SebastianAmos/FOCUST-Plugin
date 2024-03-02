package clcm.focust.utility;

public enum KillBorderTypes {
	
	NO(new NoKillBorders(), "No"),
	XY(new XYKillBorders(), "X+Y"),
	XYZ(new XYZKillBorders(), "X+Y+Z");
	
	private String displayName;
	private KillBorders killborders;
	
	KillBorderTypes(KillBorders killborders, String displayName){
		this.killborders = killborders;
		this.displayName = displayName;
	}
	
	
	public KillBorders getKillBorders() {
		return killborders;
	}

	@Override
	public String toString() {
		return displayName;
	}
	
	
}
