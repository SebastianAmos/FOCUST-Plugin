package clcm.focust.method;

public enum MethodTypes {
	
	MAXIMA(new MaximaSegmentation(), "Maxima"),
	MINIMA(new MaximaSegmentation(), "Minima"),
	CLASSIFER(new TrainedClassifier(), "Trained Classifer"),
	DEEPIMAGEJ(new MaximaSegmentation(), "Deep IJ");
	
	private Method method;
	private String displayName;
	
	MethodTypes(Method method, String displayName) {
		this.method = method;
		this.displayName = displayName;
	}
	
	public Method getMethod() {
		return method;
	}
	
	public String toString() {
		return displayName;
	}
	
	
}
