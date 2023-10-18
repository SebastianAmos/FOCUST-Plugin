package clcm.focust.filter;

public enum BackgroundType {

	NONE(new NoFilter(), "None"),
	DOG(new DifferenceOfGaussian(), "3D DoG"),
	GAUSSIAN(new GaussianBlur(), "3D Gaussian"),
	TOPHAT(new TopHat(), "3D Top Hat"),
	BOTTOMHAT(new BottomHat(), "3D Bottom Hat"),
	MINIMUM(new Minimum(), "3D Minimum"),
	INVERTEDMINIMUM(new InvertedMinimum(), "3D Inverted Min."),
	INVERTEDTUBENESS(new InvertedTubeness(), "3D Inverted Tube.");
	

	private String displayName;
	private Filter filter;
	
	BackgroundType(Filter filter, String displayName){
		this.filter = filter;
		this.displayName = displayName;
	}
	
	public Filter getFilter() {
		return filter;
	}

	public String toString() {
		return displayName;
	}
	
}
