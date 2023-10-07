package clcm.focust.filter;

public enum FilterType {
	
	NONE(new NoFilter(), "None"),
	DOG(new DifferenceOfGaussian(), "3D DoG"),
	GAUSSIAN(new GaussianBlur(), "3D Gaussian"),
	MEDIAN(new MedianFilter(), "3D Median"),
	MEAN(new MeanFilter(), "3D Mean");
	
	
	private String displayName;
	private Filter filter;
	
	FilterType(Filter filter, String displayName){
		this.filter = filter;
		this.displayName = displayName;
	}
	
	public Filter getFilter() {
		return filter;
	}

	@Override
	public String toString() {
		return displayName;
	}
}
