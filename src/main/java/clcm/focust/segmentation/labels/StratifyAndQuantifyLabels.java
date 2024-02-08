package clcm.focust.segmentation.labels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import clcm.focust.TableUtility;
import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.mode.CompiledImageData;
import clcm.focust.parameters.ParameterCollection;
import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.measure.ResultsTable;
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
	
	
	/**
	 * CHANGE TO FIRST INPUT = LABEL TYPE TO STRATIFY
	 * 
	 * @param imgData 
	 * @param bandPercent
	 * @return
	 */
	public ResultsTable process(SegmentedChannels imgData, ImagePlus imp, Double bandPercent, String objectType, ParameterCollection params, String imgName) {
		
		// Lists to store each band type
		List<ClearCLBuffer> b1 = new ArrayList<>(); // inner 25%
		List<ClearCLBuffer> b2 = new ArrayList<>(); // inner-middle 25%
		List<ClearCLBuffer> b3 = new ArrayList<>(); // outer-middle 25%
		List<ClearCLBuffer> b4 = new ArrayList<>(); // outer 25%
		
		
		// show lab img for testing
		imp.setTitle("LAB IMG_" + objectType);
		imp.show();
		
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		ClearCLBuffer labs = clij2.push(imp);
		
		// Generate the bands based on the specification
		Integer iterations = (int) (1 / bandPercent);
		
		Map<Integer, List<ClearCLBuffer>> bands = generateStratifiedBands(labs, bandPercent, iterations);
		
		// Group the band types together
		for (Map.Entry<Integer, List<ClearCLBuffer>> entry : bands.entrySet()) {
			
			List<ClearCLBuffer> stratifiedBands = entry.getValue();
			
			// Collect the bands into lists so they can be joined back into one image per band type for intensity analysis.
			b1.add(stratifiedBands.get(0));
			b2.add(stratifiedBands.get(1));
			b3.add(stratifiedBands.get(2));
			b4.add(stratifiedBands.get(3));
		}
		
		// Hold the 4 final band type images.
		List<ClearCLBuffer> bandTypes = new ArrayList<>();
		
		// Add all labels of each band type into the same buffer for intensity analysis
		bandTypes.add(combineAndRelabelBuffers(b1, labs, clij2, 1));
		bandTypes.add(combineAndRelabelBuffers(b2, labs, clij2, 2));
		bandTypes.add(combineAndRelabelBuffers(b3, labs, clij2, 3));
		bandTypes.add(combineAndRelabelBuffers(b4, labs, clij2, 4));
		
		// Do the intensity analysis for each band type, within each channel
		// ----> Using a combined chamfer distance map for testing!!
		//ClearCLBuffer testDist = computeChamferDistanceMap(labs);
		//ClearCLBuffer testDist2 = clij2.create(testDist); 
		//clij2.copy(testDist, testDist2);
		//ClearCLBuffer[] TestDists = {testDist, testDist2};
		
		
		
		saveBands(bandTypes, objectType, params, imgName);
		
		/** Generate Results */
		ImagePlus[] channels = new ImagePlus[imgData.getChannels().size()];
		ResultsTable rt = TableUtility.compileBandIntensities(bandTypes, imgData.getChannels().toArray(channels));
		
		
		
		
		return rt;
	}
	
	
	/**
	 * Saves all of the band types, appending the current object type and band number to the name.
	 * Objects are either of the type "Q" = quarter, or "H" = half.
	 * Q1 = outer most band: Q4 = innermost band. H1 = outer half: H2 = inner half.
	 * 
	 * @param bandTypes
	 * @param objectType
	 */
	private void saveBands(List<ClearCLBuffer> bandTypes, String objectType, ParameterCollection params, String imgName) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		int counter = 1;
		
		if(params.getOutputDir().isEmpty()) {
			for (ClearCLBuffer band : bandTypes) {
				IJ.saveAs(clij2.pull(band), "TIF", params.getInputDir() + objectType + counter + "_" + imgName);
				System.out.println("Saving band: " + counter);
				counter++;
				band.close();
			} 
		} else {
			for (ClearCLBuffer band : bandTypes) {
				IJ.saveAs(clij2.pull(band), "TIF", params.getOutputDir() + objectType + counter + "_"+ imgName);
				System.out.println("Saving band: " + counter);
				counter++;
				band.close();
			}
		}
		
	}


	/**
	 * Combine all buffers in a list into a single buffer, then adopt labeling from the original buffer.
	 * 
	 * @param buffers List to combine into a single buffer
	 * @param labels  Original labels to adopt
	 * @param clij2 instance.
	 * @return
	 */
	private ClearCLBuffer combineAndRelabelBuffers(List<ClearCLBuffer> buffers, ClearCLBuffer labels, CLIJ2 clij2, int bandType) {
		
		ClearCLBuffer intermediate = clij2.create(buffers.get(0));
		ClearCLBuffer type = clij2.create(intermediate);
		ClearCLBuffer result = clij2.create(intermediate);
		
		buffers.forEach(e -> {
			clij2.addImages(e, type, intermediate);
			clij2.copy(intermediate, type);
		});
		
		clij2.multiplyImages(type, labels, result);
		
		
		ClearCLBuffer imgCopy = clij2.create(result);
		clij2.copy(result, imgCopy);
		ImagePlus img = clij2.pull(imgCopy);
		img.setTitle("band" + bandType);
		imgCopy.close();
		
		return result;
	}
	
	
	/**
	 * Extracts each label from an image and stratifies it into x % distance bands.
	 * 
	 * 
	 * @param labs Labelled image to process.
	 * @param bandPercent Percentage of the distance map histogram to segment for each label.
	 * @param bandIterations Number of bands to create. Should be related to the percentage. i.e 25% = 4 iterations.
	 * @return
	 */
	private Map<Integer, List<ClearCLBuffer>> generateStratifiedBands(ClearCLBuffer labs, Double bandPercent, Integer bandIterations){
		
		Map<Integer, List<ClearCLBuffer>> stratifiedLabels = new HashMap<>();
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		// generate distance map on the whole label image before masking out each label
		ClearCLBuffer dMap2 = computeChamferDistanceMap(labs);
		ClearCLBuffer copy = clij2.create(dMap2);
		
		
		clij2.copy(dMap2, copy);
		ImagePlus dmapImg = clij2.pull(copy);
		dmapImg.setTitle("DistMap");
		dmapImg.show();
		
		
		ResultsTable stats = new ResultsTable();
		
		// use pixel stats w/o background --> LABELS MUST BE INDEXED WITHOUT SPACES
		clij2.statisticsOfLabelledPixels(labs, labs, stats);
		
		
		// for each label value, generate a mask, compute distance map, stratify based on histogram, add bands into a list mapped to the original label --> OR index int?
		// add the whole label and the stratified bands into the map.
		// label IDs will ascend without gaps - so using i as label ID is fine - just start from 1 to avoid processing the background(0).
		for (int i = 1; i <= stats.size(); i++) {
			ClearCLBuffer mask = clij2.create(labs);
			ClearCLBuffer distanceMask = clij2.create(labs);
			
			// extract a single label
			clij2.labelToMask(labs, mask, i);
			
			// mask the distance map by the label to only process that region of the distance map
			clij2.mask(dMap2, mask, distanceMask);
			
			// generate the distance map
			//ClearCLBuffer dMap = computeChamferDistanceMap(mask); // single mask chamfer distance map
			
			// generate the stratified bands from the distance map
			// take one mask distance map and create bands. 
			//List<ClearCLBuffer> bands = gpuGenerateDistanceMapBands(dMap, bandPercent, bandIterations);
			List<ClearCLBuffer> bands = gpuGenerateDistanceMapBands(distanceMask, bandPercent, bandIterations);
			
			
			// add the ordered bands for this label to the map, paired to the index of the label they were generated from.
			stratifiedLabels.put(i, bands);
			
			// debugging - delete when functional
			ImagePlus buffMask = clij2.pull(mask);
			ImagePlus distMap = clij2.pull(distanceMask);
			distMap.setTitle("DistanceMap" + i);
			distMap.show();
			buffMask.setTitle("Mask" + i);
			buffMask.show();
		}
		
		
		
		return stratifiedLabels;
	}
	
	
	
	/**
	 * @param label
	 * @return A list of band segmentations at 25 % volume increments from a label.
	 * 
	 * List should contain 4 images in order [outer 25%, outer-middle 25%, inner-middle 25%, inner 25%].
	 * 
	 * Chamfer weight set to MorpholibJ default: Svensson
	 */
	private ClearCLBuffer computeChamferDistanceMap(ClearCLBuffer input) {
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		ImagePlus label = clij2.pull(input);
		
		label.setTitle("DistMapINPUTLAB");
		label.show();
		
		// Create 3D chamfer map of label - set to default Svensson
		ChamferMasks3D weightsOption = ChamferMasks3D.SVENSSON_3_4_5_7;
		
		ChamferMask3D weights = weightsOption.getMask();
		
		DistanceTransform3D algo = new ChamferDistanceTransform3DFloat(weights, true);
		
		ChamferDistanceTransform3DShort cdist = new ChamferDistanceTransform3DShort(weights);
		
		DefaultAlgoListener.monitor(algo);
		
		IJ.run(label, "8-bit", "");
		
		ImageStack img = label.getStack();
		
		
		ImageStack result = cdist.distanceMap(img);
		
		ImagePlus resultPlus = new ImagePlus("img", result);
		resultPlus.setTitle("DistanceMapPROCESSED");
		resultPlus.show();
		double[] distExtent = Images3D.findMinAndMax(resultPlus);
		resultPlus.setDisplayRange(0, distExtent[1]);
		
		resultPlus.copyScale(label);
		
		ImagePlus test = resultPlus;
		test.setTitle("PostScale");
		test.show();
		
		ClearCLBuffer output = clij2.push(resultPlus);
		
		return output;
	}
	
	
	/**
	 * Stratifies a distance map into 4 bands by 25% or 50% histogram bin increments.
	 *
	 * @param dMap
	 * @return an ordered list of bands from inner to outer. [inner 25%, inner-middle 25%, outer-middle 25%, outer 25%] OR [inner 50%, outer 50%]
	 */
	private List<ClearCLBuffer> gpuGenerateDistanceMapBands(ClearCLBuffer dMap, Double percent, Integer iterations){
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		// get the min and max pixel values for the the distance map
		double max = clij2.getMaximumOfAllPixels(dMap);
		double min = clij2.getMinimumOfAllPixels(dMap);
		
		List<ClearCLBuffer> bands = new ArrayList<>();
		
		// set thresholds and mask, incrementing the histogram bin by 25% for each iteration. 
		for (int j = 0; j < iterations; j++) {
			ClearCLBuffer result = clij2.create(dMap);
			float thresholdMin = (float) (min + j * percent * (max-min));
			float thresholdMax = (float) (min + (j + 1) * percent * (max-min));
			net.haesleinhuepf.clij2.plugins.WithinIntensityRange.withinIntensityRange(clij2, dMap, result, thresholdMin, thresholdMax);
			bands.add(result);
		}
		
		// flip the list so 1 = core.
		Collections.reverse(bands);
		
		return bands;
	}
	
	
}
