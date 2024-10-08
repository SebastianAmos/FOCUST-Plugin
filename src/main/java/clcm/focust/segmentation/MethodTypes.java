package clcm.focust.segmentation;

public enum MethodTypes {
	
	MAXIMA(new MaximaSegmentation(), "Maxima"),
	CLASSICWATERSHED(new ClassicWatershed(), "Classic Watershed"),
	VORONOIOTSU(new VoronoiOtsuLabelling(), "Voronoi Otsu"),
	CONNECTEDCOMP(new ConnectedComponents(), "Conn. Comp."),
	MORPHOLOGICALWATERSHEDBORDER(new MorphologicalWatershedBorder(), "Morpho. Border"),
	MORPHOLOGICALWATERSHEDOBJECT(new MorphologicalWatershedObject(), "Morpho. Object");
	
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
