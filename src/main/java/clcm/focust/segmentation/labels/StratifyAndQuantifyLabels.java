package clcm.focust.segmentation.labels;

import java.util.*;

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
import inra.ijpb.label.distmap.ChamferDistanceTransform3DFloat;
import inra.ijpb.algo.DefaultAlgoListener;
import inra.ijpb.binary.distmap.ChamferDistanceTransform3DShort;
import inra.ijpb.binary.distmap.ChamferMask3D;
import inra.ijpb.binary.distmap.ChamferMasks3D;
import inra.ijpb.data.image.Images3D;
import inra.ijpb.label.distmap.DistanceTransform3D;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

import static clcm.focust.utility.SwingIJLoggerUtils.ijLog;

/**
 * @author SebastianAmos
 */


public class StratifyAndQuantifyLabels {

	private ClearCLBuffer band1; // inner 25 % or 50 %
	private ClearCLBuffer band2; // inner-middle 25 % or outer 50 %
	private ClearCLBuffer band3; // outer-middle 25 %
	private ClearCLBuffer band4; // outer 25 %


	// TODO testing memory reports
	public static void reportMemory(String method, CLIJ2 clij2){
		System.out.println("GPU memory in method: " + method + " is " + clij2.reportMemory());
	}

	/**
	 * @param imgData
	 * @param imp
	 * @param bandPercent
	 * @param objectType
	 * @param params
	 * @param imgName
	 * @return StratifiedResultsHolder:
	 */
	public StratifiedResultsHolder process(SegmentedChannels imgData, ImagePlus imp, Double bandPercent, String objectType, ParameterCollection params, String imgName) {

		ijLog("Stratification commencing...");
		long startTime = System.currentTimeMillis();

		// Lists to store each band type
		List<ClearCLBuffer> b1 = new ArrayList<>(); // inner 25 %
		List<ClearCLBuffer> b2 = new ArrayList<>(); // inner-middle 25 %
		List<ClearCLBuffer> b3 = new ArrayList<>(); // outer-middle 25 %
		List<ClearCLBuffer> b4 = new ArrayList<>(); // outer 25 %
		
		Calibration cal = imp.getCalibration();
		CLIJ2 clij2 = CLIJ2.getInstance();

		reportMemory("StratifyAndQuantifyLabels", CLIJ2.getInstance());

		ClearCLBuffer labs = clij2.push(imp.duplicate());

		// Generate the bands based on the specification
		Integer iterations = (int) (1 / bandPercent);

		// initialise the band buffers.
		band1 = clij2.create(labs);
		band2 = clij2.create(labs);
		if (bandPercent == 0.25) {
			band3 = clij2.create(labs);
			band4 = clij2.create(labs);
		}
		
		List<ClearCLBuffer> bands = generateStratifiedBands(labs, imp.duplicate(), bandPercent, iterations, clij2, cal);

		// relabel the bands
		relabelBuffers(bands, labs, clij2);
		labs.close();

		//saveBands(bandTypes, objectType, params, imgName, cal);
		saveBands(bands, objectType, params, imgName, cal);
		
		/* Generate Results */
		ImagePlus[] channels = new ImagePlus[imgData.getChannels().size()];

		//List<ResultsTable> rtList = TableUtility.compileBandIntensities(bandTypes, imgData.getChannels().toArray(channels), cal, params); // this is slow.
		List<ResultsTable> rtList = TableUtility.compileBandIntensitiesMultithreaded(bands, imgData.getChannels().toArray(channels), cal, params);

		long endTime = System.currentTimeMillis();
		ijLog("Time to stratify and quantify: " + (endTime - startTime)/1000 + "seconds.");

        return StratifiedResultsHolder.builder()
				.bands(bands)
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
	private ClearCLBuffer combineAndRelabelBuffers(List<ClearCLBuffer> buffers, ClearCLBuffer labels, CLIJ2 clij2) {

		reportMemory("STARTcombineAndRelabelBuffers", clij2);

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

		reportMemory("ENDcombineAndRelabelBuffers", clij2);

		return result;
	}


	/**
	 * Combine buffer bands into the final band type buffer.
	 * @param buffer
	 * @param clij2
	 * @return
	 */
	private void combineBuffers(ClearCLBuffer buffer, ClearCLBuffer band, CLIJ2 clij2) {
		ClearCLBuffer copy = clij2.create(band);
		clij2.copy(band, copy); // wasn't copying original band - testing whether that was why only one label existed.
		clij2.addImages(buffer, copy, band);
		copy.close();
		buffer.close();
	}


	/**
	 * Relabel a band with the label_ID from the original labelled image.
	 * @param buffers
	 * @param labels
	 * @param clij2
	 */
	private void relabelBuffers(List<ClearCLBuffer> buffers, ClearCLBuffer labels, CLIJ2 clij2) {

		for (ClearCLBuffer b : buffers) {
			ClearCLBuffer copy = clij2.create(b);
			clij2.copy(b, copy); // to avoid in place
			clij2.multiplyImages(copy, labels, b);
			copy.close();
		}

	}


	/**
	 * Extracts each label from an image and stratifies it into x % distance bands.
	 * 
	 * @param labs Labelled image to process.
	 * @param bandPercent Percentage of the distance map histogram to segment for each label.
	 * @param bandIterations Number of bands to create. Should be related to the percentage. i.e 25% = 4 iterations.
	 * @return
	 */
	private List<ClearCLBuffer> generateStratifiedBands(ClearCLBuffer labs, ImagePlus labels, Double bandPercent, Integer bandIterations, CLIJ2 clij2, Calibration cal){

		labels.setCalibration(cal);

		// generate distance map on the whole label image before masking out each label
		ClearCLBuffer dMap = computeChamferDistanceMap(labels ,clij2, cal);

		reportMemory("generateStratifiedBands", clij2);

		ResultsTable stats = new ResultsTable();

		// use pixel stats w/o background --> LABELS MUST BE INDEXED WITHOUT SPACES
		clij2.statisticsOfLabelledPixels(labs, labs, stats);

		// TODO -> Consider using im.max here - otherwise should use the LABEL (I think its called IDENTIFIER in clij2 tables) in the below loop instead of i.

		// for each label value, generate a mask, compute distance map, stratify based on histogram, add bands into a list mapped to the original label --> OR index int?
		// label IDs will ascend without gaps - so using i as label ID is fine - just start from 1 to avoid processing the background(0).
		
		for (int i = 1; i <= stats.size(); i++) {
			
			ClearCLBuffer mask = clij2.create(labs);
			ClearCLBuffer distanceMask = clij2.create(labs);

			// extract a single label
			clij2.labelToMask(labs, mask, i);
			
			// mask the distance map by the label to only process that region of the distance map
			clij2.mask(dMap, mask, distanceMask);

			List<ClearCLBuffer> bands = gpuGenerateDistanceMapBands(distanceMask, clij2, bandPercent, bandIterations);



			// TODO sort the bands into types here
			// Combine the new bands with the correct global band type buffer.
			combineBuffers(bands.get(0), band1, clij2);
			combineBuffers(bands.get(1), band2, clij2);
			if (bandPercent == 0.25) {
				combineBuffers(bands.get(2), band3, clij2);
				combineBuffers(bands.get(3), band4, clij2);
			}

			reportMemory("generateStratifiedBands AFTER distance map bands", clij2);
			// add the ordered bands for this label to the map, paired to the index of the label they were generated from.

			//stratifiedLabels.put(i, bands);
			
			mask.close();
			distanceMask.close();

		}

		// initialise an arraylist with the 4 band types.
		List<ClearCLBuffer> bandTypes = new ArrayList<>(Arrays.asList(band1, band2));
		if (bandPercent == 0.25) {
			bandTypes.addAll(Arrays.asList(band3, band4));
		}


		
		return bandTypes;
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
	
	
	/**
	 * Stratifies a distance map into 4 bands by 25 % or 50 % histogram bin increments.
	 *
	 * @param dMap
	 * @param percent
	 * @param iterations
	 * @return an ordered list of bands from inner to outer. [inner 25%, inner-middle 25%, outer-middle 25%, outer 25%] OR [inner 50%, outer 50%]
	 */
	private List<ClearCLBuffer> gpuGenerateDistanceMapBands(ClearCLBuffer dMap, CLIJ2 clij2, Double percent, Integer iterations){

		reportMemory("Within GenerateDistanceMapBands", clij2);

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
