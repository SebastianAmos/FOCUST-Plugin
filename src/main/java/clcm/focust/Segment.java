package clcm.focust;

import ij.IJ;
import ij.ImagePlus;
import ij.measure.ResultsTable;
import ij.plugin.ChannelSplitter;
import inra.ijpb.measure.IntensityMeasures;
import inra.ijpb.plugins.AnalyzeRegions3D;
import inra.ijpb.plugins.IntensityMeasures3D;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clijx.CLIJx;
import net.haesleinhuepf.clijx.morpholibj.MorphoLibJMarkerControlledWatershed;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.SwingUtilities;

public class Segment {

	public static ImagePlus[] channelsSpheroid;
	public static ImagePlus primaryObjectSpheroid;
	public static ImagePlus secondaryObjectSpheroid;
	private static AnalyzeRegions3D analyze3D = new AnalyzeRegions3D();
	private static IntensityMeasures3D intensity3D = new IntensityMeasures3D();
	//private static IntensityMeasures primaryC2Intensity = new IntensityMeasures(primaryObjectSpheroid, channelsSpheroid[2]);
	

	public static void threadLog(final String log) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				IJ.log(log);
			}
		});
	}

	 
	/*
	 * Implementing a file extension filter may be useful.  
	 */
	// (list[i].endsWith(".tif")||list[i].endsWith(".nd2")||list[i].endsWith("_D3D"));
	// IJ.showProgress(i+1, list.length);

	/*
	 * TODO List Create a file loop to grab the list[i] of files in the inputDir
	 * folder. assign the first index of the list to the object img. Establish the
	 * appropriate variables (channel names, dir names). run a background subtract
	 * on the img. Split the channels --> push channel 1 (imgC1) to CLIJ2_ run
	 * segmentation commands with user defined variable inputs. save the outputs.
	 * run analysis with morpholibJ commands. hold results table objects open. run
	 * macro code to construct the results table in the required format. push back
	 * as outputTable object. save as .csv
	 */

	public static void ProcessSpheroid() {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {

				// Grab the file names.
				File f = new File(SpheroidView.inputDir);
				String[] list = f.list();
				String dir = SpheroidView.inputDir;

				// Iterate through each image in the directory and segment the selected primary and secondary channels.
				for (int i = 0; i < list.length; i++) {
					String path = SpheroidView.inputDir + list[i];
					IJ.log("Processing Image: " + list[i]);
					ImagePlus imp = IJ.openImage(path);
					channelsSpheroid = ChannelSplitter.split(imp);
					int primaryChannelChoice = SpheroidView.primaryChannelChoice;
					int secondaryChannelChoice = SpheroidView.secondaryChannelChoice;
					String imgName = imp.getTitle();
					
					/*
					 * Primary object: create and measure
					 */
					GPUSpheroidPrimaryObject(primaryChannelChoice);
					IJ.saveAs(primaryObjectSpheroid, "TIF", dir + "primaryObjects_" + imgName);
					ResultsTable primaryResults = analyze3D.process(primaryObjectSpheroid);
					String pRName = dir + "Primary_Results.csv";
					try {
						primaryResults.saveAs(pRName);
					} catch (IOException e) {
						e.printStackTrace();
						IJ.log("Unable to save primary results table.");
					}
					IJ.log("Primary Ojbects Completed for: " + imgName);
					
					
					
					// Testing intensity measurements!!!!
					IntensityMeasurements.Process(channelsSpheroid[2], primaryObjectSpheroid);
					IntensityMeasurements.table.show("IntensityData");
					
					
					/*
					 * Secondary object: create and measure
					 */
					IJ.log("Commencing Secondary Object...");
					GPUSpheroidSecondaryObject(secondaryChannelChoice);
					IJ.saveAs(secondaryObjectSpheroid, "TIF", dir + "SecondaryObjects_" + imgName);
					ResultsTable secondaryResults = analyze3D.process(secondaryObjectSpheroid);
					String sRName = dir + "Secondary_Results.csv";
					try {
						secondaryResults.saveAs(sRName);
					} catch (IOException e) {
						e.printStackTrace();
						IJ.log("Unable to save secondary results table.");
					}
					
					
					/* 
					 * Intensity measurements
					 */
					//ResultsTable primaryIntensity = intensity3D.run(sRName);
					
					ResultsTable primaryC2Intensity = new ResultsTable();
					
					
					
					
					
					IJ.log(list.length + " Images Processed");
					IJ.log("Processing Finished!");
					// Empty the channel array between images
					Arrays.fill(channelsSpheroid, null);

				}
			}
		});
		t1.start();
	}

	/*
	 * Test if --> all images for processing are opened, split channels, select the
	 * primary channel choice for each image, and store in
	 * primaryChannelChoiceProcessing[]. Push entire array for ImagePlus objects
	 * into the GPU to minimise back and forth and use cache. Process all primary
	 * objects at once.
	 */

	public static ImagePlus GPUSpheroidPrimaryObject(int primaryChannelChoice) {

		// ready clij2
		CLIJ2 clij2 = CLIJ2.getInstance();
		clij2.clear();
		CLIJx clijx = CLIJx.getInstance();
		clijx.clear();

		// CLIJ argument structure = (input image, ClearCLBuffer output image).
		// Push image
		ClearCLBuffer input = clij2.push(channelsSpheroid[primaryChannelChoice]);
		ClearCLBuffer blurred = clij2.create(input);
		ClearCLBuffer inverted = clij2.create(input);
		ClearCLBuffer threshold = clij2.create(input);
		ClearCLBuffer detectedMax = clij2.create(input);
		ClearCLBuffer labelledSpots = clij2.create(input);
		ClearCLBuffer segmented = clij2.create(input);

		/*
		 * Could implement a clij2.filter(GB) with user input values for background
		 * subtract radius. Then feed to clij2.subtractImages(input, background,
		 * backgroundSubtracted). Instead of running the background subtract outside of
		 * the GPU.
		 */

		// 3D blur
		clij2.gaussianBlur3D(input, blurred, SpheroidView.sigma_x, SpheroidView.sigma_y, SpheroidView.sigma_z);

		// invert
		clij2.invert(blurred, inverted);

		// threshold
		clij2.thresholdOtsu(blurred, threshold);

		// detect maxima
		clij2.detectMaxima3DBox(blurred, detectedMax, SpheroidView.radius_x, SpheroidView.radius_y,
				SpheroidView.radius_z);

		// label spots
		clij2.labelSpots(detectedMax, labelledSpots);

		// marker controlled watershed
		MorphoLibJMarkerControlledWatershed.morphoLibJMarkerControlledWatershed(clij2, inverted, labelledSpots,
				threshold, segmented);

		primaryObjectSpheroid = clij2.pull(segmented);

		// primaryObjects.show();
		input.close();
		blurred.close();
		inverted.close();
		threshold.close();
		detectedMax.close();
		labelledSpots.close();
		segmented.close();

		return primaryObjectSpheroid;

	}

	public static ImagePlus GPUSpheroidSecondaryObject(int secondaryChannelChoice) {

		// ready clij2
		CLIJ2 clij2 = CLIJ2.getInstance();
		clij2.clear();
		CLIJx clijx = CLIJx.getInstance();
		clijx.clear();

		// create images
		ClearCLBuffer input = clij2.push(channelsSpheroid[secondaryChannelChoice]);
		ClearCLBuffer blurred = clij2.create(input);
		ClearCLBuffer threshold = clij2.create(input);
		ClearCLBuffer fillHoles = clij2.create(input);

		// blur
		clij2.gaussianBlur3D(input, blurred, SpheroidView.sigma_x2, SpheroidView.sigma_x2, SpheroidView.sigma_z2);

		// greater constant
		clij2.greaterConstant(blurred, threshold, SpheroidView.greaterConstant);

		// fill holes
		clij2.binaryFillHoles(threshold, fillHoles);

		// pull
		secondaryObjectSpheroid = clij2.pull(fillHoles);

		input.close();
		blurred.close();
		threshold.close();
		fillHoles.close();

		return secondaryObjectSpheroid;
	}
	
	
	

	
	

}
