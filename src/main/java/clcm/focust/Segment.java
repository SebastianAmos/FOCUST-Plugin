package clcm.focust;

import ij.IJ;
import ij.ImagePlus;
import ij.io.Opener;
import ij.plugin.ChannelSplitter;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clijx.CLIJx;
import net.haesleinhuepf.clijx.morpholibj.*;

import java.io.File;

public class Segment {
	
	//private CLIJ2 clij2;
	//private ImagePlus img; 
	//private ClearCLBuffer 
	private static ImagePlus[] channels;
	
	
	
	
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
		// Grab the file location
		File f = new File(SpheroidView.inputDir);
		String[] list = f.list();
		
		// Iterate through each image and apply segmentation. 
		for (int i=0; i<list.length; i++) {
			if (list[i].endsWith(".tif")||list[i].endsWith(".nd2"));
			String path = SpheroidView.inputDir+list[i];
			IJ.showProgress(i+1, list.length);
			ImagePlus img = Opener.openUsingBioFormats(path);
			channels = ChannelSplitter.split(img);
			
			
			
			
			
		}
		}		
			
	public ImagePlus GPUSpheroidPrimaryObject() {		
		
			// ready clij2
			CLIJ2 clij2 = CLIJ2.getInstance();
			clij2.clear();
			CLIJx clijx = CLIJx.getInstance();
			clijx.clear();
			
			
			
			// CLIJ parameter structure = (input image, ClearCLBuffer output image). 
			
			// Push image
			ClearCLBuffer input = clij2.push(channels[0]);
			ClearCLBuffer blurred = null;
			ClearCLBuffer inverted = null;
			ClearCLBuffer threshold = null;
			ClearCLBuffer detectedMax = null;
			ClearCLBuffer labelledSpots = null;
			ClearCLBuffer segmentated = null;
			
			/*
			 * Could implement a clij2.filter(GB) with user input values for background subtract radius
			 * Then feed to clij2.subtractImages(input, background, backgroundSubtracted). 
			 * Instead of running the background subtract outside of the GPU
			 */
			
			//clij2.gaussianBlur3D
			clij2.gaussianBlur3D(input, blurred, SpheroidView.sigma_x, SpheroidView.sigma_y, SpheroidView.sigma_z);
		
			// clij2.invert blurred image
			clij2.invert(blurred, inverted);
			
			//clij2.thresholdOtsu(
			clij2.thresholdOtsu(blurred, threshold);
			
			// maxima 
			clij2.detectMaxima3DBox(blurred, detectedMax, SpheroidView.radius_x, SpheroidView.radius_y, SpheroidView.radius_z);
			
			// label
			clij2.labelSpots(detectedMax, labelledSpots);
			
			// marker controlled watershed
		
			
			
			
			
			}
			
			
			
			
			
			
		
		
		
		
		
		
		//String[] list = (new File(FOCUST.inputDir)).list();
		
		//list = getFileList();
				
		
		//File macro = new File("C:/Users/21716603/Desktop/Spheroid 4 channels.ijm");
		//Path inputPath = FOCUST.inputDir;
		
		
		//String iDir = FOCUST.inputPath.toString().replace("\\", "/");
		//System.out.println(iDir);

	
		
		
		
	
}
