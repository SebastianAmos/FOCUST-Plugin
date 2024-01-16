package clcm.focust.segmentation.labels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clcm.focust.TableUtility;
import clcm.focust.mode.CompiledImageData;
import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.measure.ResultsTable;
import ij.process.ImageProcessor;
import inra.ijpb.label.distmap.ChamferDistanceTransform3DFloat;
import inra.ijpb.algo.DefaultAlgoListener;
import inra.ijpb.binary.distmap.ChamferDistanceTransform3DShort;
import inra.ijpb.binary.distmap.ChamferMask3D;
import inra.ijpb.binary.distmap.ChamferMasks3D;
import inra.ijpb.data.image.Images3D;
import inra.ijpb.label.distmap.DistanceTransform3D;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;


import static clcm.focust.SwingIJLoggerUtils.ijLog;

public class StratifyAndQuantifyLabels {
	
	// TODO: For each label within an image 
	// > Isolate/mask 
	// > Chamfer distance 3D
	// > Threshold in 25% increments to create stratified labels
	// > Impose original label ID onto new labels
	// > Measure channel intensities within those stratified labels
	// > Compile results
	// >> Run parallel? 
	
	/**
	 * @param imgData Labelled image must be re-indexed to ensure no gaps.
	 * @return
	 */
	public ResultsTable process(CompiledImageData imgData) {
		
		// Lists to store each band type
		List<ClearCLBuffer> b1 = new ArrayList<>(); // outer 25%
		List<ClearCLBuffer> b2 = new ArrayList<>(); // outer-middle 25%
		List<ClearCLBuffer> b3 = new ArrayList<>(); // inner-middle 25%
		List<ClearCLBuffer> b4 = new ArrayList<>(); // inner 25%
		
		
		imgData.getImages().getPrimary().show();
		// Test with primary

		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer labs = clij2.push(imgData.getImages().getPrimary());
		
		// Generate the bands 
		Map<Integer, List<ClearCLBuffer>> bands = generateStratifiedBands(labs);
		
		// At this stage all bands have a label of 1. 
		// Recombine them into a single image for each band type, then multiply each band subtype by the the original image. 1 * x = x
		
		// Combine all band types together
		for (Map.Entry<Integer, List<ClearCLBuffer>> entry : bands.entrySet()) {
			
			List<ClearCLBuffer> stratifiedBands = entry.getValue();
			
			// Collect the bands into lists so they can be joined back into one image per band time for intensity analysis.
			b1.add(stratifiedBands.get(0));
			b2.add(stratifiedBands.get(1));
			b3.add(stratifiedBands.get(2));
			b4.add(stratifiedBands.get(3));
		}
		
		// Create final and temp buffers to add all labels into the same image.
		ClearCLBuffer b1Type = clij2.create(labs);
		ClearCLBuffer b2Type = clij2.create(labs);
		ClearCLBuffer b3Type = clij2.create(labs);
		ClearCLBuffer b4Type = clij2.create(labs);
		
		ClearCLBuffer b1Temp = clij2.create(labs);
		ClearCLBuffer b2Temp = clij2.create(labs);
		ClearCLBuffer b3Temp = clij2.create(labs);
		ClearCLBuffer b4Temp = clij2.create(labs);

		
		// Hold the 4 final band images.
		List<ClearCLBuffer> bandTypes = new ArrayList<>();
		
		// add all labels of each band type into the same buffer for intensity analysis
		// TODO: write a method for this!!!!!
		b1.forEach(e -> {
			clij2.addImages(e, b1Type, b1Temp);
			clij2.copy(b1Temp, b1Type);
		});
		
		b2.forEach(e ->{
			clij2.addImages(e, b2Type, b2Temp);
			clij2.copy(b2Temp, b2Type);
		});
		
		b3.forEach(e ->{
			clij2.addImages(e, b3Type, b3Temp);
			clij2.copy(b3Temp, b3Type);
		});
		
		b4.forEach(e ->{
			clij2.addImages(e, b4Type, b4Temp);
			clij2.copy(b4Temp, b4Type);
		});
		
		
		
		// multiply each band image by the original label image to transfer the label IDs
		ClearCLBuffer band1 = clij2.create(b1Type);
		ClearCLBuffer band2 = clij2.create(b2Type);
		ClearCLBuffer band3 = clij2.create(b3Type);
		ClearCLBuffer band4 = clij2.create(b4Type);
		
		
		clij2.multiplyImages(b1Type, labs, band1);
		clij2.multiplyImages(b2Type, labs, band2);
		clij2.multiplyImages(b3Type, labs, band3);
		clij2.multiplyImages(b4Type, labs, band4);
		
		bandTypes.add(band1);
		bandTypes.add(band2);
		bandTypes.add(band3);
		bandTypes.add(band4);
		
		// Do the intensity analysis for each band type, within each channel 
		// ----> Using a combined chamfer distance map for testing!!
		ClearCLBuffer testDist = computeChamferDistanceMap(labs, 1);
		ClearCLBuffer testDist2 = clij2.create(testDist); 
		clij2.copy(testDist, testDist2);
		ClearCLBuffer[] TestDists = {testDist, testDist2};
		
		
		ResultsTable rt = TableUtility.compileBandIntensities(bandTypes, TestDists);
	
		rt.show("BAND RESULTS");
		
		// clij2 statistics methods are done on cpu - so using morpholibj methods
		//ResultsTable b1Results = TableUtility.processIntensity(clij2.pull(testDist), clij2.pull(band1));
		//ResultsTable b2Results = TableUtility.processIntensity(clij2.pull(testDist), clij2.pull(band2));
		//ResultsTable b3Results = TableUtility.processIntensity(clij2.pull(testDist), clij2.pull(band3));
		//ResultsTable b4Results = TableUtility.processIntensity(clij2.pull(testDist), clij2.pull(band4));
		
		//b1Results.show("b1");
		//b2Results.show("b2");
		//b3Results.show("b3");
		//b4Results.show("b4");
		
		ImagePlus b1TypeImg = clij2.pull(band1);
		ImagePlus b2TypeImg = clij2.pull(band2);
		b1TypeImg.setTitle("b1TypeIMAGE");
		b2TypeImg.setTitle("b2TypeIMAGE");
		
		b1TypeImg.show();
		b2TypeImg.show();
		
		ImagePlus b2Lab1 = clij2.pull(b2.get(0));
		ImagePlus b2Lab2 = clij2.pull(b2.get(1));
		
		b2Lab1.setTitle("b2Lab1");
		b2Lab2.setTitle("b2Lab2");
		b2Lab1.show();
		b2Lab2.show();
		
		ClearCLBuffer b1Buffer = clij2.create(labs);
		ClearCLBuffer b2Buffer = clij2.create(labs);
		ClearCLBuffer b3Buffer = clij2.create(labs);
		ClearCLBuffer b4Buffer = clij2.create(labs);
		
		b1Buffer = combineBuffers(b1, b1Buffer);
		
		ImagePlus b1Imp = clij2.pull(b1Buffer);
		ImagePlus b1Lab = clij2.pull(b1.get(0));
		
		b1Imp.setTitle("combinedBuffer");
		b1Lab.setTitle("Label");
		b1Lab.show();
		b1Imp.show();
		
		// gather/write the data in a meaningful way
		// [c1_band1, c1_band2, c1_band3, c1_band4]
		
		// -> write to resultsBuilder
		
		return null;
	}
	
	
	
	/**
	 * Combine all labels bands of the same type back into a single buffer object ready for intensity analysis.
	 * 
	 * @param buffers
	 * @param result
	 * @return
	 */
	private ClearCLBuffer combineBuffers(List<ClearCLBuffer> buffers, ClearCLBuffer result) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		buffers.stream().forEach(e -> {
			clij2.copy(e, result);
		});
		
		return result;
	}
	
	
	/**
	 * Add all bands back into a single buffer object.
	 * @param buffers
	 * @param result
	 * @return
	 */
	private ClearCLBuffer addBuffers(List<ClearCLBuffer> buffers, ClearCLBuffer result) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		buffers.stream().forEach(e -> {
			clij2.addImages(e, result, result);
		});
		
		return result;
	}
	
	
	/**
	 * Extracts each label from an image and stratifies it into 25% distance bands.
	 * @param labels
	 * @return a map of label IDs to stored bands
	 */
	private Map<Integer, List<ClearCLBuffer>> generateStratifiedBands(ClearCLBuffer labs){
		
		Map<Integer, List<ClearCLBuffer>> stratifiedLabels = new HashMap<>();
		
		CLIJ2 clij2 = CLIJ2.getInstance();

		
		ResultsTable stats = new ResultsTable();
		
		// use pixel stats w/o background --> LABELS MUST BE INDEXED WITHOUT SPACES
		clij2.statisticsOfLabelledPixels(labs, labs, stats);
		stats.show("Image Stats");
		
		// for each label value, generate a mask, compute distance map, stratify based on histogram, add bands into a list mapped to the original label --> OR index int?
		// add the whole label and the stratified bands into the map.
		// label IDs will ascend without gaps - so using i as label ID is fine - just start from 1 to avoid processing the background(0).
		IJ.log("number of objects = " + stats.size());
		
		for (int i = 1; i <= stats.size(); i++) {
			ClearCLBuffer mask = clij2.create(labs);
			//ClearCLBuffer dMap = clij2.create(labs);
			ClearCLBuffer binary = clij2.create(labs);
			
			// extract a single label
			clij2.labelToMask(labs, mask, i);
			IJ.log("Getting mask: " + i);
			
			
			// generate the distance map
			//MorphoLibJChamferDistanceMap.morphoLibJChamferDistanceMap(clij2, mask, dMap); // pulls to cpu anyway
			ClearCLBuffer dMap = computeChamferDistanceMap(mask, i); // single mask chamfer distance map
			
			
			// generate the stratified bands from the distance map
			// take one mask distance map and create bands. 
			List<ClearCLBuffer> bands = gpuGenerateDistanceMapBands(dMap);
			
			// add the ordered bands for this label to the map, paired to the index of the label they were generated from.
			stratifiedLabels.put(i, bands);
			
			
			// debugging - delete when functional
			ImagePlus buffMask = clij2.pull(mask);
			ImagePlus distMap = clij2.pull(dMap);
			distMap.setTitle("DistanceMap" + i);
			distMap.show();
			buffMask.setTitle("Mask" + i);
			buffMask.show();
		}
		
		
		
		return stratifiedLabels;
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
	 * @param label
	 * @return A list of band segmentations at 25 % volume increments from a label.
	 * 
	 * List should contain 4 images in order [outer 25%, outer-middle 25%, inner-middle 25%, inner 25%].
	 * 
	 * Chamfer weight set to MorpholibJ default: Svensson
	 */
	private ClearCLBuffer computeChamferDistanceMap(ClearCLBuffer input, int index) {
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		ImagePlus label = clij2.pull(input);
		
		// Create 3D chamfer map of label - set to default Svensson
		ChamferMasks3D weightsOption = ChamferMasks3D.SVENSSON_3_4_5_7;
		
		ChamferMask3D weights = weightsOption.getMask();
		
		DistanceTransform3D algo = new ChamferDistanceTransform3DFloat(weights, true);
		
		ChamferDistanceTransform3DShort cdist = new ChamferDistanceTransform3DShort(weights);
		
		DefaultAlgoListener.monitor(algo);
		
		IJ.run(label, "8-bit", "");
		
		ImageStack img = label.getStack();
		
		
		ImageStack result = cdist.distanceMap(img);
		
		if (result == null){
			ijLog("Unable to generate chamfer distace map 3D for label: " + index);
		}
		
		ImagePlus resultPlus = new ImagePlus("img", result);
		double[] distExtent = Images3D.findMinAndMax(resultPlus);
		resultPlus.setDisplayRange(0, distExtent[1]);
		
		resultPlus.copyScale(label);
		ClearCLBuffer output = clij2.push(resultPlus);
		
		return output;
	}
	
	
	
	/**
	 * TESTING
	 * Generate a combined distance map for testing purposes!
	 * @param input
	 * @return
	 */
	private ClearCLBuffer testDistanceMap(ClearCLBuffer input) {
		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer output = clij2.create(input);
		return output; 
	}
	
	/**
	 * Stratifies a distance map into 4 bands by 25% histogram bin increments.
	 *
	 * @param dMap
	 * @return an orderd list of bands from outer to inner. [outer 25%, outer-middle 25%, inner-middle 25%, inner 25%]
	 */
	private List<ClearCLBuffer> gpuGenerateDistanceMapBands(ClearCLBuffer dMap){
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		// get the min and max pixel values for the the distance map
		double max = clij2.getMaximumOfAllPixels(dMap);
		double min = clij2.getMinimumOfAllPixels(dMap);
		
		List<ClearCLBuffer> bands = new ArrayList<>();
		
		// set thresholds and mask, incrementing the histogram bin by 25% for each iteration. 
		for (int j = 0; j < 4; j++) {
			ClearCLBuffer result = clij2.create(dMap);
			float thresholdMin = (float) (min + j * 0.25 * (max-min));
			float thresholdMax = (float) (min + (j + 1) * 0.25 * (max-min));
			net.haesleinhuepf.clij2.plugins.WithinIntensityRange.withinIntensityRange(clij2, dMap, result, thresholdMin, thresholdMax);
			bands.add(result);
		}
		return bands;
	}
	
	
	/**
	 * Segment 25% label distance bands from a distance map.
	 * @param distances A 3D chamfer distance map.
	 * @return A list of all bands generated from the distance map.
	 */
	private ArrayList<ImagePlus> generateDistanceMapBands(ImagePlus distances){
		
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
