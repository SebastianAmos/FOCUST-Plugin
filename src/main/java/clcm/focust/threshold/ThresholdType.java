package clcm.focust.threshold;

public enum ThresholdType {

	OTSU(new OtsuThreshold() , "Otsu"),
	YEN(new YenThreshold(), "Yen"),
	HUANG(new HuangThreshold(), "Huang"),
	ISODATA(new IsoDataThreshold(),"Iso Data"),
	INTERMODES(new IntermodesThreshold(), "Intermodes"),
	GC(new GreaterConstantThreshold(), "G.Const.");
	

	
	private String displayName;
	private Threshold threshold;
	
	
	ThresholdType(Threshold threshold, String displayName){
		this.threshold = threshold;
		this.displayName = displayName;
	}
	
	public Threshold getThreshold() {
		return threshold;
	}
	
	@Override
	public String toString() {
		return displayName;
	}
	
}
