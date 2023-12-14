package clcm.focust.segmentation.labels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import clcm.focust.mode.CompiledImageData;
import ij.ImagePlus;
import ij.ImageStack;
import ij.measure.ResultsTable;
import ij.process.ImageProcessor;
import inra.ijpb.label.distmap.ChamferDistanceTransform3DFloat;
import inra.ijpb.algo.DefaultAlgoListener;
import inra.ijpb.binary.distmap.ChamferMask3D;
import inra.ijpb.binary.distmap.ChamferMasks3D;
import inra.ijpb.data.image.Images3D;
import inra.ijpb.label.distmap.DistanceTransform3D;
import inra.ijpb.plugins.AnalyzeRegions3D;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import static clcm.focust.SwingIJLoggerUtils.ijLog;

public class StratifyAndQuantifyLabels {

	private AnalyzeRegions3D analyze3D = new AnalyzeRegions3D();
	
	// TODO: For each label within an image 
	// > Isolate/mask 
	// > Chamfer distance 3D
	// > Threshold in 25% increments to create stratified labels
	// > Impose original label ID onto new labels
	// > Measure channel intensities within those stratified labels
	// > Compile results
	// >> Run parallel? 
	public ResultsTable process(CompiledImageData imgData) {
		
		
		
		ResultsTable length = analyze3D.process(imgData.getImages().getPrimary());
		
		ResultsTable rt = new ResultsTable();
		
		
		
		// Mask each label and add it to a list.
		ArrayList<ImagePlus> masks = new ArrayList<>();;
		
		
		// for each label and then each channel
		// generate the label masks
		for (int i = 0; i < length.size(); i++) {
			for (int j = 0; j < imgData.getImages().getChannels().size(); j++) {
				masks.add(maskLabel(imgData.getImages().getPrimary(), imgData.getImages().getChannels().get(j), i));
			}
		}
		
		// new list to collect stream outputs then do for each
		
		ArrayList<ImagePlus> distanceMaps = new ArrayList<>();
		
		masks.stream().forEach(e -> {
			distanceMaps.add(computeChamferDistanceMap(e, masks.indexOf(e)));
		});
		
		
		// Generate distance bands for each label via it's distance map. 
		List<List<ImagePlus>> labelBands = new ArrayList<>();
		
		distanceMaps.stream().forEach(e -> {
			labelBands.add(generateDistanceBands(e));
		});
		
		
		// mask each channel by each original label.
		
		
		
		// compute intensities
		
		
		// -> write to resultsBuilder
		
		return rt;
	}
	
	
	/**
	 * Returns an individual label for processing.
	 * @param label A in image containing labels.
	 * @param index The label ID of the label to mask.
	 * @return
	 */
	private List<Map<ImagePlus, List<ImagePlus>>> maskLabel(ImagePlus label, ArrayList<ImagePlus> channels, int index) {
		
		// maybe use a hashmap to relate 
		
		// for each channel mask, store in a map to a list of label bands.
		List<Map<ImagePlus, List<ImagePlus>>> maskCollection = new ArrayList<>();
		
		// hold the channel masks for each image channel IN A LOOP
		List<ImagePlus> channelMask = new ArrayList<>();
		
		
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer input = clij2.push(label);
		ClearCLBuffer masked = clij2.create(input);
		
		// new method to mask original img by the selected label
		for (ImagePlus img : channels) {
			ClearCLBuffer imgBuffer = clij2.push(img);
			ImagePlus maskedOriginal = maskOriginal(input, imgBuffer, index);
			
		}
		
		
		
		clij2.labelToMask(input, masked, index);
		ImagePlus output = clij2.pull(masked);
		input.close();
		masked.close();
		
		
		return maskCollection;
	}
	
	
	/**
	 * Masks the original image by the selected label.
	 * 
	 * @param label
	 * @param img
	 * @param index
	 * @return An image that has been masked by the current label.
	 */
	private ImagePlus maskOriginal(ClearCLBuffer label, ClearCLBuffer img, int index) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		ClearCLBuffer maskedImg = clij2.create(img);
		
		clij2.maskLabel(img, label, maskedImg, index);
		
		ImagePlus output = clij2.pull(maskedImg);
		
		return output;
	}
	
	
	/**
	 * 
	 * @param label
	 * @return A list of band segmentations at 25 % volume increments from a label.
	 * 
	 * List should contain 4 images in order [outer 25%, outer-middle 25%, inner-middle 25%, inner 25%].
	 * 
	 * Chamfer weight set to MorpholibJ default: Svensson
	 */
	private ImagePlus computeChamferDistanceMap(ImagePlus label, int index) {
		
		// Create 3D chamfer map of label - set to default Svensson
		ChamferMasks3D weightsOption = ChamferMasks3D.SVENSSON_3_4_5_7;
		
		ChamferMask3D weights = weightsOption.getMask();
		
		DistanceTransform3D algo = new ChamferDistanceTransform3DFloat(weights, true);
		
		DefaultAlgoListener.monitor(algo);
		
		ImageStack img = label.getStack();
		
		ImageStack result = algo.distanceMap(img);
		
		if (result == null){
			ijLog("Unable to generate chamfer distace map 3D for label: " + index);
		}
		
		ImagePlus resultPlus = new ImagePlus("img", result);
		double[] distExtent = Images3D.findMinAndMax(resultPlus);
		resultPlus.setDisplayRange(0, distExtent[1]);
		
		resultPlus.copyScale(label);
		
		return resultPlus;
	}
	
	
	
	/**
	 * Segment 25% label distance bands from a distance map.
	 * @param distances A 3D chamfer distance map.
	 * @return A list of all generate bands from the disance map.
	 */
	private ArrayList<ImagePlus> generateDistanceBands(ImagePlus distances){
		
		ArrayList<ImagePlus> bands = new ArrayList<>();
		
		// get histogram extremes 
		double min = distances.getDisplayRangeMin();
		double max = distances.getDisplayRangeMax();
		
		// set thresholds and mask, incrementing the histogram bin by 25% each time.
		for (int i = 0; i < 4; i++) {
			
			ImagePlus temp = distances.duplicate();
			
			double thresholdMin = min + i * 0.25 * (max-min);
			double thresholdMax = min + (i + 1) * 0.25 * (max-min);
			
			ImageProcessor ip = temp.getProcessor();
			ip.setThreshold(thresholdMin, thresholdMax, ImageProcessor.NO_LUT_UPDATE);
			bands.add(new ImagePlus("", ip.createMask()));
			
			temp.close();
		}

		return bands;
	}
	
	
	
	
	
}
