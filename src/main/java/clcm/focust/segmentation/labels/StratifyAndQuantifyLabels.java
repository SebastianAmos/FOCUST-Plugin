package clcm.focust.segmentation.labels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.segmentation.Segmentation;
import clcm.focust.utility.TableUtility;
import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import ij.process.ImageConverter;
import inra.ijpb.binary.ChamferWeights3D;
import inra.ijpb.binary.distmap.DistanceTransform3DFloat;
import inra.ijpb.label.distmap.ChamferDistanceTransform3DFloat;
import inra.ijpb.algo.DefaultAlgoListener;
import inra.ijpb.binary.distmap.ChamferDistanceTransform3DShort;
import inra.ijpb.binary.distmap.ChamferMask3D;
import inra.ijpb.binary.distmap.ChamferMasks3D;
import inra.ijpb.data.image.Images3D;
import inra.ijpb.label.distmap.DistanceTransform3D;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clijx.morpholibj.MorphoLibJChamferDistanceMap;

/**
 * @author SebastianAmos
 */


public class StratifyAndQuantifyLabels {



	private final static String weightLabel = ChamferWeights3D.WEIGHTS_3_4_5_7.toString();
	private final static boolean normalize = true;

	/**
	 * 
	 * @param imgData
	 * @param imp
	 * @param bandPercent
	 * @param objectType
	 * @param params
	 * @param imgName
	 * @return StratifiedResultsHolder:
	 */
	public StratifiedResultsHolder process(SegmentedChannels imgData, ImagePlus imp, Double bandPercent, String objectType, ParameterCollection params, String imgName) {
		
		// Lists to store each band type
		List<ClearCLBuffer> b1 = new ArrayList<>(); // inner 25 %
		List<ClearCLBuffer> b2 = new ArrayList<>(); // inner-middle 25 %
		List<ClearCLBuffer> b3 = new ArrayList<>(); // outer-middle 25 %
		List<ClearCLBuffer> b4 = new ArrayList<>(); // outer 25 %
		
		Calibration cal = imp.getCalibration();
		
		CLIJ2 clij2 = CLIJ2.getInstance();

		ClearCLBuffer labs = clij2.push(imp.duplicate());
	
		// Generate the bands based on the specification
		Integer iterations = (int) (1 / bandPercent);
		
		Map<Integer, List<ClearCLBuffer>> bands = generateStratifiedBands(labs, imp.duplicate(), bandPercent, iterations, clij2, cal);
		
		// Group the band types together
		for (Map.Entry<Integer, List<ClearCLBuffer>> entry : bands.entrySet()) {
			
			List<ClearCLBuffer> stratifiedBands = entry.getValue();
			
			// Collect the bands into lists so they can be joined back into one image per band type for intensity analysis.
			b1.add(stratifiedBands.get(0));
			b2.add(stratifiedBands.get(1));
			if (bandPercent == 0.25) {
				b3.add(stratifiedBands.get(2));
				b4.add(stratifiedBands.get(3));
			}
		}
		
		// Hold the 4 final band type images.
		List<ClearCLBuffer> bandTypes = new ArrayList<>();
		
		// Add all labels of each band type into the same buffer for intensity analysis
		bandTypes.add(combineAndRelabelBuffers(b1, labs, clij2, 1));
		bandTypes.add(combineAndRelabelBuffers(b2, labs, clij2, 2));
		if (bandPercent == 0.25) {
			bandTypes.add(combineAndRelabelBuffers(b3, labs, clij2, 3));
			bandTypes.add(combineAndRelabelBuffers(b4, labs, clij2, 4));
		}

		
		saveBands(bandTypes, objectType, params, imgName, cal);
		
		/** Generate Results */
		ImagePlus[] channels = new ImagePlus[imgData.getChannels().size()];
		List<ResultsTable> rtList = TableUtility.compileBandIntensities(bandTypes, imgData.getChannels().toArray(channels), cal, params);


        return StratifiedResultsHolder.builder()
				.bands(bandTypes)
				.tableList(rtList)
				.build();
	}
	
	
	/**
	 * Saves all of the band types, appending the current object type and band number to the name.
	 * Objects are either of the type "Q" = quarter, or "H" = half.
	 * Q1 = outer most band: Q4 = innermost band. H1 = outer half: H2 = inner half.
	 * 
	 * @param bandTypes
	 * @param objectType
	 */
	private void saveBands(List<ClearCLBuffer> bandTypes, String objectType, ParameterCollection params, String imgName, Calibration cal) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		int counter = 1;
		
		if(params.getOutputDir().isEmpty()) {
			for (ClearCLBuffer band : bandTypes) {
				ClearCLBuffer copy = clij2.create(band);
				clij2.copy(band, copy);
				ImagePlus imp = clij2.pull(copy);
				imp.setCalibration(cal);
				Segmentation.adjustLutRange(imp);
				IJ.saveAs(imp, "TIF", params.getInputDir() + objectType + counter + "_" + imgName);
				counter++;
				copy.close();
				imp.close();
			} 
		} else {
			for (ClearCLBuffer band : bandTypes) {
				ClearCLBuffer copy = clij2.create(band);
				clij2.copy(band, copy);
				ImagePlus imp = clij2.pull(copy);
				imp.setCalibration(cal);
				Segmentation.adjustLutRange(imp);
				IJ.saveAs(imp, "TIF", params.getOutputDir() + objectType + counter + "_"+ imgName);
				counter++;
				copy.close();
				imp.close();
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
			e.close();
		});
		
		clij2.multiplyImages(type, labels, result);

		type.close();
		intermediate.close();

		return result;
	}
	
	
	/**
//	 * Extracts each label from an image and stratifies it into x % distance bands.
	 * 
	 * @param labs Labelled image to process.
	 * @param bandPercent Percentage of the distance map histogram to segment for each label.
	 * @param bandIterations Number of bands to create. Should be related to the percentage. i.e 25% = 4 iterations.
	 * @return
	 */
	private Map<Integer, List<ClearCLBuffer>> generateStratifiedBands(ClearCLBuffer labs, ImagePlus labels, Double bandPercent, Integer bandIterations, CLIJ2 clij2, Calibration cal){
		
		Map<Integer, List<ClearCLBuffer>> stratifiedLabels = new HashMap<>();

		labels.setCalibration(cal);

		// generate distance map on the whole label image before masking out each label
		ClearCLBuffer dMap = computeChamferDistanceMap(labels ,clij2, cal);
		//ClearCLBuffer dMap = xComputeDistanceMap(labels ,clij2, cal);

		ResultsTable stats = new ResultsTable();

		// use pixel stats w/o background --> LABELS MUST BE INDEXED WITHOUT SPACES
		clij2.statisticsOfLabelledPixels(labs, labs, stats);

		// TODO -> Consider using im.max or something here again - otherwise should use the LABEL (i think its called IDENTIFIER in clij2 tables) in the below loop instead of i.

		
		// for each label value, generate a mask, compute distance map, stratify based on histogram, add bands into a list mapped to the original label --> OR index int?
		// add the whole label and the stratified bands into the map.
		// label IDs will ascend without gaps - so using i as label ID is fine - just start from 1 to avoid processing the background(0).
		
		for (int i = 1; i <= stats.size(); i++) {
			
			ClearCLBuffer mask = clij2.create(labs);
			ClearCLBuffer distanceMask = clij2.create(labs);
	
			// extract a single label
			clij2.labelToMask(labs, mask, i);
			
			// mask the distance map by the label to only process that region of the distance map
			clij2.mask(dMap, mask, distanceMask);

			List<ClearCLBuffer> bands = gpuGenerateDistanceMapBands(distanceMask, clij2, bandPercent, bandIterations);
			
			// add the ordered bands for this label to the map, paired to the index of the label they were generated from.
			stratifiedLabels.put(i, bands);
			
			mask.close();
			distanceMask.close();

		}
		
		return stratifiedLabels;
	}
	
	

	/**
	 * Computes a distance map from an input buffer.
	 * @param labelledImg
	 * @param clij2
	 * @param cal
	 * @return
	 */
	private ClearCLBuffer computeChamferDistanceMap(ImagePlus labelledImg, CLIJ2 clij2, Calibration cal) {

		if (labelledImg.getBitDepth() != 8) {
			System.out.println("Stratification Note: Image not 8-bit, converting for distance map algo.");
			ImageConverter converter = new ImageConverter(labelledImg);
			converter.convertToGray8();
		}

		// Create 3D chamfer map of label - set to default Svensson
		ChamferMasks3D weightsOption = ChamferMasks3D.SVENSSON_3_4_5_7;
		
		ChamferMask3D weights = weightsOption.getMask();
		
		DistanceTransform3D algo = new ChamferDistanceTransform3DFloat(weights, true);
		
		ChamferDistanceTransform3DShort cdist = new ChamferDistanceTransform3DShort(weights);
		
		DefaultAlgoListener.monitor(algo);
		
		ImageStack result = cdist.distanceMap(labelledImg.getStack());
		
		ImagePlus resultPlus = new ImagePlus("img", result);
		
		double[] distExtent = Images3D.findMinAndMax(resultPlus);
		resultPlus.setDisplayRange(0, distExtent[1]);
		resultPlus.setCalibration(cal);

		ClearCLBuffer output = clij2.push(resultPlus);

		return output;
	}


	// TODO testing clijx version - uses deprecated method in ChamferWeights3D
	@Deprecated
	private ClearCLBuffer xComputeDistanceMap(ImagePlus imp, CLIJ2 clij2, Calibration cal){

		if (imp.getBitDepth() != 8) {
			System.out.println("Stratification Note: Image not 8-bit, converting for distance map algo.");
			ImageConverter converter = new ImageConverter(imp);
			converter.convertToGray8();
		}

		ChamferWeights3D chamferWeights = ChamferWeights3D.fromLabel(weightLabel);
		float[] weights = chamferWeights.getFloatWeights();
		inra.ijpb.binary.distmap.DistanceTransform3D algo = new DistanceTransform3DFloat(weights, normalize);

		ImageStack output_stack = algo.distanceMap(imp.getStack());

        return clij2.push(new ImagePlus("title", output_stack));
	}
	
	
	/**
	 * Stratifies a distance map into 4 bands by 25 % or 50 % histogram bin increments.
	 *
	 * @param dMap
	 * @param percent
	 * @param iterations
	 * @return an ordered list of bands from inner to outer. [inner 25%, inner-middle 25%, outer-middle 25%, outer 25%] OR [inner 50%, outer 50%]
	 */
	private List<ClearCLBuffer> gpuGenerateDistanceMapBands(ClearCLBuffer dMap, CLIJ2 clij2, Double percent, Integer iterations){

		// get the min and max pixel values from the distance map
		double max = clij2.getMaximumOfAllPixels(dMap);
		double min = clij2.getMinimumOfAllPixels(dMap);

		List<ClearCLBuffer> bands = new ArrayList<>();
		
		// set thresholds and mask, incrementing the histogram bin by x % for each iteration.
		for (int j = 0; j < iterations; j++) {
			ClearCLBuffer result = clij2.create(dMap);
			float thresholdMin = (float) (min + j * percent * (max-min));
			float thresholdMax = (float) (min + (j + 1) * percent * (max-min)) + 0.01f; // had an issue with the max value not being included - add a float to include.
			net.haesleinhuepf.clij2.plugins.WithinIntensityRange.withinIntensityRange(clij2, dMap, result, thresholdMin, thresholdMax);
			bands.add(result);
		}
		
		// flip the list so 1 = core.
		Collections.reverse(bands);
		
		return bands;
	}


}
