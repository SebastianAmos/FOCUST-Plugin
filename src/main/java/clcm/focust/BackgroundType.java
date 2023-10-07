package clcm.focust;

public enum BackgroundType {

	GAUSSIANBLUR(new GaussianBlur()),
	DOG(new DifferenceOfGaussian()),
	TOPHAT(new TopHat());
	

	BackgroundType(Filter filter){
		this.filter = filter;
	}
	
	public Filter getFilter() {
		return filter;
	}
	
	private Filter filter;

	
	
}
