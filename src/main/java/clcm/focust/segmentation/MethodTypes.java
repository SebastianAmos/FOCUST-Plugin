package clcm.focust.segmentation;

public enum MethodTypes {
	
	MAXIMA(new MaximaSegmentation(), "Maxima"),
	MINIMA(new MaximaSegmentation(), "Minima"),
	CLASSICWATERSHED(new ClassicWatershed(), "Classic Watershed"),
	VORONOIOTSU(new VoronoiOtsuLabelling(), "Voronoi Otsu"),
	EXTENDBYVORONOI(new ExtendByVoronoi(), "Extend by Voronoi"),
	CONNECTEDCOMP(new ConnectedComponents(), "Conn. Comp."),
	MEMBRANESCALAR(new MembraneScalar(), "Membrane Scalar"),
	EDTMEMBRANES(new EDTMembranes(), "Membrane-Based");
	
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
