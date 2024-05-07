package clcm.focust.segmentation;

public enum MethodTypes {
	
	MAXIMA(new MaximaSegmentation(), "Maxima"),
	CLASSICWATERSHED(new ClassicWatershed(), "Classic Watershed"),
	VORONOIOTSU(new VoronoiOtsuLabelling(), "Voronoi Otsu"),
	EXTENDBYVORONOI(new ExtendByVoronoi(), "Extend by Voronoi"),
	CONNECTEDCOMP(new ConnectedComponents(), "Conn. Comp.");
	
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
