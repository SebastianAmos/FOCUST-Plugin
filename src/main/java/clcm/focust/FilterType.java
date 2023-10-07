package clcm.focust;

public enum FilterType {
	
	
	GAUSSIANBLUR(new GaussianBlur()),
	DOG(new DifferenceOfGaussian());
	
	
	
	FilterType(Filter filter){
		this.filter = filter;
	}
	
	public Filter getFilter() {
		return filter;
	}
	
	private Filter filter;

	
}
