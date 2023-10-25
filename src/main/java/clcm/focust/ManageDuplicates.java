package clcm.focust;

import java.util.HashMap;
import java.util.Map;
import ij.IJ;
import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.plugin.ImageCalculator;
import ij.process.ImageProcessor;
import inra.ijpb.measure.IntensityMeasures;


/**
 * This class contains methods for dealing cases of duplicate smaller objects within larger objects. i.e. multiple nuclei labels within a single cell label
 */
public class ManageDuplicates {

	
	public ImagePlus run(ImagePlus labels, ImagePlus toRelabel) {
		
		// impose labels of secondary object onto primary object (or tertiary) 
		ImagePlus matched = imposeLabels(labels, toRelabel);
		
		// matched = primary or tertiary objects relabelled with secondary object values.

		// for each original primary object
		Map<Double, Double> map = matchLabels(toRelabel, matched);
		
		// for each repeated primary label, append an index.
		Map<Double, Double> indexed = mapDuplicates(map);
		
		// CHANGE OUTPUT TYPE TO OBJECT THAT HOLDS A MAP AND THE RELABELLED IMAGE WITH INDICIES!!!
		
		return null;
		
	}
	
	
	/**
	 * converts original label value to 1 then imposes the values from the "labels"
	 * ImagePlus by multiplication.
	 * 
	 * @param labels   Imp with labels to impose on the orignal image. i.e.
	 *                 secondary objects.
	 * @param toRelabel Imp with labels that need to adopt the labelling from the
	 *                 "labels" imp. i.e. primary objects.
	 * @return
	 */
	public ImagePlus imposeLabels(ImagePlus labels, ImagePlus toRelabel) {

		ImagePlus binary = LabelEditor.makeBinary(toRelabel);
		IJ.run(binary, "Divide...", "value=" + binary.getDisplayRangeMax() + " stack");
		ImagePlus matched = ImageCalculator.run(labels, binary, "Multiply create stack");

		return matched;
	}
	
	
	/**
	 * Compare the labels of two images and store in a map. keys = original labels
	 * and values = relabelled labels (potentially duplicates) 
	 * 
	 * @param original 
	 * @param relabelled 
	 * @return a map with original labels 
	 */
	public Map<Double, Double> matchLabels(ImagePlus original, ImagePlus relabelled) {

		final IntensityMeasures im = new IntensityMeasures(original, relabelled);

		Map<Double, Double> labelComparisons = new HashMap<>();

		ResultsTable labels = im.getMax();

		for (int i = 0; i < labels.size(); i++) {

			labelComparisons.put(labels.getValue("Label", i), labels.getValue("Max", i));
			
		}
		
		return labelComparisons;
		
	}
	
	
	/**
	 * for each repeated value encountered, append 0.1.
	 * 
	 * @param map
	 */
	public Map<Double, Double> mapDuplicates(Map<Double, Double> map) {

		Map<Double, Integer> labelTracker = new HashMap<>();
		Map<Double, Double> results = new HashMap<>();

		for (Double key : map.keySet()) {

			Double val = map.get(key);
			int counter = labelTracker.getOrDefault(val, 0);
			labelTracker.put(val, counter + 1);

			if (counter > 0) {
				val += counter * 0.1;
			}

			results.put(key, val);

		}
		
		System.out.println("Counter Map: " + labelTracker);

		return results;

	}
	
	
	/**
	 * Assign labels from a map where a key is discovered.
	 * 
	 * @param imp a uniquely labelled image
	 * @param map A map of old to new labels
	 * @return
	 */
	public ImagePlus relabelFromMap(ImagePlus imp, Map<Double, Double> map) {
		IJ.run(imp, "32-bit", "");
		for (int z = 1; z <= imp.getStackSize(); z++) {
			ImageProcessor ip = imp.getStack().getProcessor(z);
			for (int y = 0; y < ip.getHeight(); y++) {
				for (int x = 0; x < ip.getWidth(); x++) {
					Double lbl = Double.valueOf(ip.getPixelValue(x, y));
					Double val = map.get(lbl);
					ip.putPixelValue(x, y, val);

				}
			}
		}
		return imp;
	}
	
	
}
