package clcm.focust.filter;

public enum BackgroundType {

	NONE(new NoFilter(), "None"),
	DOG(new DifferenceOfGaussian(), "3D DoG"),
	GAUSSIAN(new GaussianBlur(), "3D Gaussian"),
	TOPHAT(new TopHat(), "3D Top Hat");
	

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
