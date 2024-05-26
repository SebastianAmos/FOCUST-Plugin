package clcm.focust.speckle;

import clcm.focust.data.DataObject;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ij.ImagePlus;

/** TODO document. */
public final class Speckles implements DataObject {

	private final Map<SpeckleType, ImagePlus> speckles;

	private final List<ImagePlus> channelsSpeckles;

	/**
	 * TODO Document
	 * 
	 * @param speckles
	 * @param channelsSpeckles
	 */
	public Speckles(Map<SpeckleType, ImagePlus> speckles, final List<ImagePlus> channelsSpeckles) {
		this.speckles = speckles;
		this.channelsSpeckles = channelsSpeckles;
	}

	/**
	 * Getter for speckle. Returns a mutable copy.
	 * 
	 * @param key to get
	 */
	public final Optional<ImagePlus> getSpeckle(SpeckleType key) {
		/* TODO: This is a shallow clone. That's not really good enough. */
		return Optional.ofNullable(speckles.get(key)).map(it -> (ImagePlus) it.clone());
	}

	/**
	 * Getter for channels Speckles.
	 * @return
	 */
	public final Optional<List<ImagePlus>> getChannelsSpeckles() {
		return Optional.ofNullable(channelsSpeckles).map(it -> Collections.unmodifiableList(it));
	}
}
