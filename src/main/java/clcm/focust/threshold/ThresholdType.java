package clcm.focust.threshold;

public enum ThresholdType {

	OTSU(new OtsuThreshold() , "Otsu"),
	YEN(new YenThreshold(), "Yen"),
	HUANG(new HuangThreshold(), "Huang"),
	ISODATA(new IsoDataThreshold(),"Iso Data"),
	INTERMODES(new IntermodesThreshold(), "Intermodes"),
	LI(new LiThreshold(), "Li"),
	TRIANGLE(new TriangleThreshold(), "Triangle"),
	SHANBHAG(new ShanbhagThreshold(), "Shanbhag"),
	MINERROR(new MinErrorThreshold(), "Min Error"),
	MIN(new MinimumThreshold(), "Minimum"),
	MEAN(new MeanThreshold(), "Mean"),
	MAXENTROPY(new MaxEntropyThreshold(), "Max Ent."),
	MOMENTS(new MomentsThreshold(), "Moments"),
	PERCENTILE(new PercentileThreshold(), "Percentile"),
	RENYIENTROPY(new RenyiEntropyThreshold(), "Renyi Ent."),
	GREATERCONSTANT(new GreaterConstantThreshold(), "G. Const."),
	SMALLERCONSTANT(new SmallerConstantThreshold(), "S. Const.");
	

	
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
