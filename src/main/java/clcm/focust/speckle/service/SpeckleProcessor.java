package clcm.focust.speckle.service;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import clcm.focust.FOCUSTService;
import clcm.focust.Segment;
import clcm.focust.data.DataConstants;
import clcm.focust.data.DataConstants.Datum;
import clcm.focust.speckle.SpeckleType;
import clcm.focust.speckle.Speckles;
import clcm.focust.speckle.SpecklesConfiguration;
import clcm.focust.speckle.view.SpeckleView;
import ij.IJ;
import ij.ImagePlus;
import ij.macro.Variable;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import ij.plugin.ChannelSplitter;
import clcm.focust.data.DataListener;
import clcm.focust.data.DataMapUpdateService;
import clcm.focust.data.DatumSubscriptionService;
import lombok.RequiredArgsConstructor;
import net.haesleinhuepf.clij2.CLIJ2;

/**
 * This class listens for Updated speckle configuration. When it recieves new
 * configuration values, it immediately begins processing.
 */
@RequiredArgsConstructor
public class SpeckleProcessor implements DataListener<DataConstants.Datum, SpecklesConfiguration>, FOCUSTService {

	/** To Listen on. */
	private final DatumSubscriptionService<SpecklesConfiguration> configurationSubService;

	/** To update to. */
	private final DataMapUpdateService<String, Speckles> specklesUpdateService;

	/** Runs speckle processing on this service. */
	private final ExecutorService speckleExeService = Executors.newSingleThreadExecutor();

	@Override
	public void init() {
		configurationSubService.registerListener(this);
	}

	@Override
	public void shutdown() {
		configurationSubService.deregisterListener(DataConstants.Datum.DATUM, this);
	}

	@Override
	public void dataUpdated(Datum key, SpecklesConfiguration newData) {
		speckleExeService.submit(() -> processSpeckles(newData.isAnalysisMode(), newData));
	}

	@Override
	public void dataDeleted(Datum key) {
		// Nothing to do.
	}

	/*
	 * TODO OVERALL: - implement a file extension filter - could be a good way to
	 * then distinguish between original vs pre-segmented images later on too... -
	 * make erosion of secondary object relative to it's original size, instead of
	 * an arbitrary number of iterations. - implement intensity measurements in core
	 * vs periphery - make intensity analysis dependent on then number of channels
	 * in current image array - not fixed to 4. - Make it batch process friendly!
	 * End of single image loops marked at the end of the "Process.." methods
	 *
	 *
	 * Implementing a file extension filter may be useful.
	 */

	/**
	 * Main processing loop for speckle analysis of multiple images.
	 * 
	 * @param analysisOnly
	 * @param speckleConf
	 */
	public void processSpeckles(boolean analysisOnly, final SpecklesConfiguration speckleConf) {
		ImagePlus[] channelsSpeckle;
		// grab the file names and start a timer
		long startTime = System.currentTimeMillis();

		File f = speckleConf.getInputDirectory().toFile();
		String[] filesList = f.list();
		int count = 0;

		/*
		 * // set up the arraylists to hold results for each image for each object type
		 * (keys). String[] keys = {"primary", "secondary", "tertiary"}; Map<String,
		 * ArrayList<Variable>> resultsMap = new HashMap<>();
		 * 
		 * for (String key : keys) { ArrayList<Variable> label = new ArrayList<>();
		 * ArrayList<Variable> imageID = new ArrayList<>(); ArrayList<Variable> volume =
		 * new ArrayList<>(); ArrayList<Variable> voxelCount = new ArrayList<>();
		 * ArrayList<Variable> sphericity = new ArrayList<>(); ArrayList<Variable>
		 * elongation = new ArrayList<>(); ArrayList<Variable> breadth = new
		 * ArrayList<>(); ArrayList<Variable> surfaceArea = new ArrayList<>();
		 * ArrayList<Variable> centroidX = new ArrayList<>(); ArrayList<Variable>
		 * centroidY = new ArrayList<>(); ArrayList<Variable> centroidZ = new
		 * ArrayList<>(); ArrayList<Variable> c1Mean = new ArrayList<>();
		 * ArrayList<Variable> c1IntDen = new ArrayList<>(); ArrayList<Variable> c2Mean
		 * = new ArrayList<>(); ArrayList<Variable> c2IntDen = new ArrayList<>();
		 * ArrayList<Variable> c3Mean = new ArrayList<>(); ArrayList<Variable> c3IntDen
		 * = new ArrayList<>(); ArrayList<Variable> parent = new ArrayList<>();
		 * ArrayList<Variable> secondaryCount = new ArrayList<>(); ArrayList<Variable>
		 * tertiaryCount = new ArrayList<>(); }
		 * 
		 */
		ImagePlus primaryObjectsSpeckles;
		ImagePlus secondaryObjectsSpeckles;
		ImagePlus tertiaryObjectsSpeckles;

		// If analysis-only-mode, create a new list[] containing image names that DO NOT
		// match the prefix expectations i.e. are the original images, not the segmented
		// outputs.
		if (analysisOnly) {
			ArrayList<String> tempList = new ArrayList<>();
			for (String imgName : filesList) {
				if (!imgName.startsWith(Segment.PRIMARYPREFIX) && !imgName.startsWith(Segment.SECONDARYPREFIX)
						&& !imgName.startsWith(Segment.COREPREFIX) && !imgName.startsWith(Segment.OUTERPREFIX)
						&& !imgName.startsWith(Segment.TERTIARYPREFIX)) {
					tempList.add(imgName);
				}
			}
			filesList = new String[tempList.size()];
			filesList = tempList.toArray(filesList);
		}

		ResultsTable primaryFinalResults = null;
		ResultsTable secondaryFinalResults = null;
		ResultsTable tertiaryFinalResults = null;

		// make this conditional: link to the total number of objects set to create
		Map<String, List<Variable>> primary = new LinkedHashMap<>();
		Map<String, List<Variable>> secondary = new LinkedHashMap<>();
		Map<String, List<Variable>> tertiary = new LinkedHashMap<>();

		IJ.log("-------------------------------------------------------");
		IJ.log("		FOCUST: Speckle Protocol		");

		// iterate through each image in the list
		for (String file : filesList) {
			count++;

			// reset the temp arrays
			Variable[] labels = null;
			Variable[] imageID = null;
			Variable[] vol = null;

			Path path = f.toPath().resolve(file);

			IJ.log("Processing image " + count + " of " + filesList.length);
			IJ.log("Current image name: " + file);
			IJ.log("-------------------------------------------------------");
			ImagePlus imp = IJ.openImage(path.toString());
			int numberOfChannels = imp.getNChannels();

			// TEST WITHOUT CONVERTING TO 8-BIT!
			IJ.run(imp, "8-bit", "");

			Calibration cal = imp.getCalibration();

			channelsSpeckle = ChannelSplitter.split(imp);
			int primaryChannelChoice = SpeckleView.primaryChannelChoice;
			int secondaryChannelChoice = SpeckleView.secondaryChannelChoice;
			int tertiaryChannelChoice = SpeckleView.tertiaryChannelChoice;

			String imgName = imp.getTitle();

			// If analysisMode is T, find the correct primary object file for the current
			// image

			// MAKE THIS WORK BY DETECTING THE IMAGE EXTENSION!!!
			// NOT ALL DATA WILL BE .nd2 or .dv
			// TODO lachlan extract analysis
			if (analysisOnly) {
				IJ.log("Analysis Only Mode Active: Finding Images...");
				IJ.log("-------------------------------------------------------");
				String fileName = file.replace(".dv", ".tif");
				IJ.log("Opening:[" + speckleConf.getInputDirectory() + Segment.PRIMARYPREFIX + fileName + "]");
				primaryObjectsSpeckles = IJ
						.openImage(speckleConf.getInputDirectory() + Segment.PRIMARYPREFIX + fileName);
				secondaryObjectsSpeckles = IJ
						.openImage(speckleConf.getInputDirectory() + Segment.SECONDARYPREFIX + fileName);
				tertiaryObjectsSpeckles = IJ
						.openImage(speckleConf.getInputDirectory() + Segment.TERTIARYPREFIX + fileName);
				IJ.log("Analysis Only Mode Active: Found Images...");
				IJ.log("-------------------------------------------------------");
			} else {
				// if analysis mode is F, segment objects based on channel preferences
				IJ.log("Analysis Only Mode Not Active: Running Segmentation...");
				IJ.log("-------------------------------------------------------");
				IJ.log("CLIJ2 CLINFO: [" + CLIJ2.clinfo() + "]");
				IJ.log("CLIJ2 GPU: [" + CLIJ2.getInstance().getGPUName() + "] OCL:["
						+ CLIJ2.getInstance().getOpenCLVersion() + "]");
				primaryObjectsSpeckles = Segment.gpuSegmentOtsu(channelsSpeckle[primaryChannelChoice],
						SpeckleView.sigma_x, SpeckleView.sigma_y, SpeckleView.sigma_z, SpeckleView.radius_x,
						SpeckleView.radius_y, SpeckleView.radius_z);
				secondaryObjectsSpeckles = Segment.gpuSegmentGreaterConstant(channelsSpeckle[secondaryChannelChoice],
						SpeckleView.sigma_x2, SpeckleView.sigma_y2, SpeckleView.sigma_z2,
						SpeckleView.greaterConstantSecondary, SpeckleView.radius_x2, SpeckleView.radius_y2,
						SpeckleView.radius_z2);
				// make tertiary processing conditional
				tertiaryObjectsSpeckles = Segment.gpuSegmentGreaterConstant(channelsSpeckle[tertiaryChannelChoice],
						SpeckleView.sigma_x3, SpeckleView.sigma_y3, SpeckleView.sigma_z3,
						SpeckleView.greaterConstantTertiary, SpeckleView.radius_x3, SpeckleView.radius_y3,
						SpeckleView.radius_z3);
				IJ.log("Analysis Only Mode Not Active: Finished Segmentation...");
				IJ.log("-------------------------------------------------------");
			}

			Map<SpeckleType, ImagePlus> speckles = new EnumMap<>(SpeckleType.class);

			IJ.resetMinAndMax(primaryObjectsSpeckles);
			primaryObjectsSpeckles.setCalibration(cal);

			IJ.resetMinAndMax(secondaryObjectsSpeckles);
			secondaryObjectsSpeckles.setCalibration(cal);

			IJ.resetMinAndMax(tertiaryObjectsSpeckles);
			tertiaryObjectsSpeckles.setCalibration(cal);

			speckles.put(SpeckleType.PRIMARY, primaryObjectsSpeckles);
			speckles.put(SpeckleType.SECONDARY, primaryObjectsSpeckles);
			speckles.put(SpeckleType.TERTIARY, primaryObjectsSpeckles);

			/** Provide a new speckle for further analysis: */
			specklesUpdateService.notifyUpdated(file, new Speckles(speckles, Arrays.asList(channelsSpeckle)));
		}
		/* Await All of the speckles to be done. TODO Process results complete */
	}
}
