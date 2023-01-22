package clcm.focust;

import ij.IJ;
import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.ChannelSplitter;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clijx.CLIJx;
import net.haesleinhuepf.clijx.morpholibj.MorphoLibJMarkerControlledWatershed;


import java.io.File;

import javax.swing.SwingUtilities;

public class Segment{
	

	
	
	
	
	//private CLIJ2 clij2;
	//private ImagePlus img; 
	//private ClearCLBuffer 
	private static ImagePlus[] channels;
	private static ImagePlus primaryObjects;
	private static ImagePlus secondaryObjects;
	
	
	
	
	public static void threadLog(final String log) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				IJ.log(log);
			}
		});
	}
		
	
	
	
	
	
	
	
	
	//if (list[i].endsWith(".tif")||list[i].endsWith(".nd2")||list[i].endsWith("_D3D"));
	//IJ.showProgress(i+1, list.length);
	
	
	
	
	
	/*TODO List
	 * Create a file loop to grab the list[i] of files in the inputDir folder.
	 * assign the first index of the list to the object img.
	 * Establish the appropriate variables (channel names, dir names).
	 * run a background subtract on the img. 
	 * Split the channels --> push channel 1 (imgC1) to CLIJ2_
	 * run segmentation commands with user defined variable inputs. 
	 * save the outputs.
	 * run analysis with morpholibJ commands.
	 * hold results table objects open.
	 * run macro code to construct the results table in the required format. 
	 * push back as outputTable object. 
	 * save as .csv
	 */
	
	public static void ProcessSpheroid() {
			Thread t1 = new Thread(new Runnable() {
				@Override
				public void run() {
					
					// Grab the file names.
					File f = new File(SpheroidView.inputDir);
					String[] list = f.list();
					
					// Iterate through each image and segment the selected channel. 
					for (int i=0; i<list.length; i++) {
						String path = SpheroidView.inputDir+list[i];
						IJ.log("Processing Image: " + list[i]);
						ImagePlus imp = IJ.openImage(path);
						channels = ChannelSplitter.split(imp);
						int primaryChannelChoice = SpheroidView.primaryChannelChoice;
						int secondaryChannelChoice = SpheroidView.secondaryChannelChoice;
						GPUSpheroidPrimaryObject(primaryChannelChoice);
						IJ.log("Primary Ojbects Completed for:" + list[i]);
						IJ.log("Commencing Secondary Object...");
						GPUSpheroidSecondaryObject(secondaryChannelChoice);
						primaryObjects.setTitle("primaryObjects_");
						IJ.saveAs(primaryObjects, "TIF", path);
						
						
						// save primary and secondary objects?
						
						
						
					}
				}
			});
			t1.start();
		}		
			
	
	
	
	/*
	 * Test if --> all images for processing are opened, split channels, select the primary channel choice 
	 * for each image, and store in primaryChannelChoiceProcessing[]. 
	 * Push entire array for ImagePlus objects into the GPU to minimise back and forth and use cache. 
	 * Process all primary objects at once. 
	 */
	
	public static ImagePlus GPUSpheroidPrimaryObject(int primaryChannelChoice) {		
		
			// ready clij2
			CLIJ2 clij2 = CLIJ2.getInstance();
			clij2.clear();
			CLIJx clijx = CLIJx.getInstance();
			clijx.clear();
			
			// CLIJ argument structure = (input image, ClearCLBuffer output image). 
			// Push image
			ClearCLBuffer input = clij2.push(channels[primaryChannelChoice]);
			ClearCLBuffer blurred = clij2.create(input);
			ClearCLBuffer inverted = clij2.create(input);
			ClearCLBuffer threshold = clij2.create(input);
			ClearCLBuffer detectedMax = clij2.create(input);
			ClearCLBuffer labelledSpots = clij2.create(input);
			ClearCLBuffer segmented = clij2.create(input);
			
			/*
			 * Could implement a clij2.filter(GB) with user input values for background subtract radius.
			 * Then feed to clij2.subtractImages(input, background, backgroundSubtracted). 
			 * Instead of running the background subtract outside of the GPU.
			 */
			
			// 3D blur
			clij2.gaussianBlur3D(input,  blurred, SpheroidView.sigma_x, SpheroidView.sigma_y, SpheroidView.sigma_z);
		
			// invert
			clij2.invert(blurred, inverted);
			
			// threshold
			clij2.thresholdOtsu(blurred, threshold);
			
			// detect maxima 
			clij2.detectMaxima3DBox(blurred, detectedMax, SpheroidView.radius_x, SpheroidView.radius_y, SpheroidView.radius_z);
			
			// label spots
			clij2.labelSpots(detectedMax, labelledSpots);
			
			// marker controlled watershed
			MorphoLibJMarkerControlledWatershed.morphoLibJMarkerControlledWatershed(clij2, inverted, labelledSpots, threshold, segmented);
			
			primaryObjects = clij2.pull(segmented);
			
			//primaryObjects.show();
			input.close();
			blurred.close();
			inverted.close();
			threshold.close();
			detectedMax.close();
			labelledSpots.close();
			segmented.close();
			
			return primaryObjects;
		
			}
			
			/*
			 * Change so that --> same as for primary objects.
			 * Push all selected secondary objects as ImagePlus array into GPU for processing at once. 
			 */
			
	public static ImagePlus GPUSpheroidSecondaryObject(int secondaryChannelChoice) {
		
			// ready clij2
			CLIJ2 clij2 = CLIJ2.getInstance();
			clij2.clear();
			CLIJx clijx = CLIJx.getInstance();
			clijx.clear();
		
			// create images
			ClearCLBuffer input = clij2.push(channels[secondaryChannelChoice]);
			ClearCLBuffer blurred = clij2.create(input);
			ClearCLBuffer threshold = clij2.create(input);
			ClearCLBuffer fillHoles = clij2.create(input);
			
			// blur
			clij2.gaussianBlur3D(input, blurred, SpheroidView.sigma_x2, SpheroidView.sigma_x2, SpheroidView.sigma_z2);
			
			//greater constant
			clij2.greaterConstant(blurred, threshold, SpheroidView.greaterConstant);
		
			// fill holes
			clij2.binaryFillHoles(threshold, fillHoles);
			
			//pull
			ImagePlus secondaryObject = clij2.pull(fillHoles);
			
			secondaryObject.show();
			
			input.close();
			blurred.close();
			threshold.close();
			fillHoles.close();
			
			
			return secondaryObject;
	}
			
			
		
	
	
	

		
		
		
		
		//String[] list = (new File(FOCUST.inputDir)).list();
		
		//list = getFileList();
				
		
		//File macro = new File("C:/Users/21716603/Desktop/Spheroid 4 channels.ijm");
		//Path inputPath = FOCUST.inputDir;
		
		
		//String iDir = FOCUST.inputPath.toString().replace("\\", "/");
		//System.out.println(iDir);

	
		
		
		
	
}
