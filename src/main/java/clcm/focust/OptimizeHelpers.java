package clcm.focust;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.ChannelSplitter;

public class OptimizeHelpers {
	
	OptimizeGUI gui;
	
	public void setOptimizeGUI(OptimizeGUI gui) {
		this.gui = gui;
	}
	
	/**
	 * A helper method for loading images for optimization.
	 * Any images open from the previous image are closed.
	 * 
	 */
	
	public void initialiseImage() {
		if(gui.inputDir != null && gui.list != null && gui.currentIndex >= 0 && gui.currentIndex <= gui.list.length) {
			String imgPath = gui.inputDir + gui.list[gui.currentIndex];
			gui.currentImage = IJ.openImage(imgPath);
			gui.channelArray = ChannelSplitter.split(gui.currentImage);
			
			if(gui.currentImage != null) {
				IJ.run("Close All");
			}
		} else {
			IJ.showMessage("ERROR: Ensure you have selected a directory that contains images.");
		}
		
	}
	
	
	/**
	 * Set the image index to the next image in the list then initialise that image.
	 */
	public void loadNext() {
		if(gui.currentIndex < gui.list.length - 1) {
			gui.currentIndex = ++gui.currentIndex;
			initialiseImage();
		}
	}
	
	
	/**
	 * Set the image index to the previous image in the list then initialise that image.
	 */
	public void loadPrevious() {
		if(gui.currentIndex > 0 ) {
			gui.currentIndex = --gui.currentIndex;
			initialiseImage();
		}
	}

	
	/**
	 * Combine the images provided together.
	 * 
	 * @param impArray An array of images to merge in order.
	 * @return a composite image that contains multiple channels.
	 */
	
	public ImagePlus mergeAndDisplay(ImagePlus[] impArray) {
		
		
		
		return null;
	}
	
	
}
