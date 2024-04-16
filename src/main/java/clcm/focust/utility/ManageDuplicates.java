package clcm.focust.utility;

import java.util.HashMap;
import java.util.Map;

import clcm.focust.segmentation.labels.LabelEditor;
import ij.IJ;
import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.plugin.ImageCalculator;
import ij.process.ImageProcessor;
import inra.ijpb.measure.IntensityMeasures;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;


/**
 * This class contains methods for dealing cases of duplicate smaller objects within larger objects. i.e. multiple nuclei labels within a single cell label
 */
public class ManageDuplicates {

	
	/**
	 * The run method for the class. 
	 *
	 * @param labels an image with the labels you want to impose (i.e. a secondary object)
	 * @param toRelabel an image with labels you want to replace with the "labels" input (i.e. a primary or tertiary object) 
	 * @return an object that contains a map of old to new labels, and a relabelled image.
	 */
	public RelabelledObjects run(ImagePlus labels, ImagePlus toRelabel, ResultsTable rt) {
		
		// impose labels of secondary object onto primary object (or tertiary) 
		ImagePlus matched = imposeLabels(labels, toRelabel); // matched = primary or tertiary objects relabelled with secondary object values.
		
		// for each original primary object
		Map<Double, Double> map = matchLabels(toRelabel, matched);
		
		// for each repeated primary label, append an index.
		Map<Double, Double> indexed = mapDuplicates(map);
		
		ImagePlus relabelled = relabelFromMap(toRelabel, indexed);
		
		modifyTableLabels(rt, indexed);
		
		RelabelledObjects relab = RelabelledObjects.builder().
				map(indexed).
				relabelled(relabelled).
				results(rt).build();
		
		return relab;
		
	}
	
	
	// Extract label from rt, compare to the keys in the map, if a match is found
	// replace the label with the map value - append an index if the value has been seen before. 
	
	//TODO: MUST WRITE MATCHES TO NEW RESULTS TABLE AS THOSE WITHOUT A MATCH REMAIN. 
	// -> would be fine if the labels didn't have to be matched. Easier so build a new one. 
	
	private void modifyTableLabels(ResultsTable rt, Map<Double, Double> indexed) {
		
		ResultsTable output = new ResultsTable();
		
		// for each row in the table
		for (int i = 0; i < rt.size(); i++) {
			
			// get the label 
			double lbl = Double.parseDouble(rt.getStringValue("Label", i));
			
			// compare to each label in the map.
			for (Double key : indexed.keySet()) {
				
				if (lbl == key) {

					// NEW RESULTS TABLE TO AVOID DUPLICATES!!!
					int row = output.getCounter();
					output.incrementCounter();
					
					for (int j = 0; j < rt.getLastColumn(); j++) {
						String head = rt.getColumnHeading(j);
						
						// use the the string method for known strings
						if (head == "ImageID" || head == "Group" || head == "Label") {
							output.setValue(head, row, rt.getStringValue(head, i));
						} else {
							output.setValue(head, row, rt.getValue(head, i));
						}
					}
					
					// Rewrite the Label col with the new value from the map of logged duplicates
					Double val = indexed.get(key);
					
					output.setValue("Label", row, val);
					
				}
			}
		}
		
		rt = output;

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
		
		ImagePlus matchedCopy = matched.duplicate();
		matchedCopy.setTitle("Binary Primary");
		matchedCopy.show();
		
		
		return matched;
	}
	
	public ImagePlus imposeLabelling(ImagePlus labels, ImagePlus toRelabel) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer labs = clij2.push(labels);
		ClearCLBuffer toLab = clij2.push(toRelabel);
		ClearCLBuffer relabelled = clij2.create(toLab);
		
		
		
		
		ImagePlus output = null;
		
		return output;
	}
	
	
	
	/**
	 * Compare the labels of two images and store in a map. keys = original labels
	 * and values = relabelled labels (potentially duplicates).
	 * 
	 * @param original 
	 * @param relabelled 
	 * @return a map with original labels 
	 */
	public Map<Double, Double> matchLabels(ImagePlus original, ImagePlus relabelled) {
		
		IntensityMeasures im = new IntensityMeasures(relabelled, original);
		
		Map<Double, Double> labelComparisons = new HashMap<>();
		
		ResultsTable labels = im.getMax();
		
		ResultsTable labelsCopy = (ResultsTable) labels.clone();
		labelsCopy.show("LabelsCopy");
		

		int maxColIndex = labels.getColumnIndex("Max");
		
		for (int i = 0; i < labels.size(); i++) {
	
			labelComparisons.put(Double.parseDouble(labels.getLabel(i)), labels.getValueAsDouble(maxColIndex, i));
			
		}
		
		System.out.println("Label Comparisons Map : /n" + labelComparisons);
		
		return labelComparisons;
		
	}
	
	
	/**
	 * for each repeated value encountered, append 0.1.
	 * Ignore if val == 0. 
	 * 
	 * @param map
	 */
	public Map<Double, Double> mapDuplicates(Map<Double, Double> map) {

		Map<Double, Integer> labelTracker = new HashMap<>();
		Map<Double, Double> results = new HashMap<>();

		for (Double key : map.keySet()) {

			Double val = map.get(key);

			if (val != 0) {
					
				int counter = labelTracker.getOrDefault(val, 0);
				labelTracker.put(val, counter + 1);

				if (counter > 0) {
					val += counter * 0.1;
				}

				results.put(key, val);
				
			}
			
		}

		System.out.println("input MAP: " + map);
		System.out.println("output MAP (indexed): " + results);
		
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
					if (map.containsKey(lbl)) {
						Double val = map.get(lbl);
						ip.putPixelValue(x, y, val);
					}

				}
			}
		}
		return imp;
	}
	
	
}
