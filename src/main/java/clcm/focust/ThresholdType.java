package clcm.focust;

public enum ThresholdType {

	OTSU( , "Otsu"),
	GC(, "GC"),
	YEN(, "Yen"),
	HUANG(, "Huang");
	

	
	ThresholdType(Threshold threshold, String displayName){
		this.threshold = threshold;
		this.displayName = displayName;
	}
	
	public Threshold getThreshold() {
		return threshold;
	}
	
	private Threshold threshold;
	private String displayName;
	
	@Override
	public String toString() {
		return displayName;
	}
	
}
