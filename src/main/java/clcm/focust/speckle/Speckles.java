package clcm.focust.speckle;

import clcm.focust.data.DataObject;
import java.util.Map;
import java.util.Optional;

import ij.ImagePlus;

/** TODO document. */
public final class Speckles implements DataObject {
	
	private final Map<SpeckleType, ImagePlus> speckles;

	public Speckles(Map<SpeckleType, ImagePlus> speckles) {
		this.speckles = speckles;
	}
	
	/**
	 * Getter for speckle. Returns a mutable copy. 
	 * @param key to get
	 */
	public final Optional<ImagePlus> getSpeckle(SpeckleType key) {
		/*TODO: This is a shallow clone. That's not really good enough.  */
		return Optional.ofNullable(speckles.get(key)).map( it -> (ImagePlus) it.clone());
	}
}
