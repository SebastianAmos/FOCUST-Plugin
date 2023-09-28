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
	 * Merge the provided images together into a multichannel composite.
	 * 
	 * @param channels An array of images to combine in order.
	 * @return Composite image of all images provided.
	 */
	
	public ImagePlus createComposite(ImagePlus[] channels) {

		
		ImagePlus img;

		// display the image if array only contains a single element
		if(channels.length == 1) {
			img = channels[0];
		} else {

			// images must be open for merge to work.
			for (ImagePlus imp : channels) {
				imp.show();
			}

			StringBuilder channelNames = new StringBuilder();

			for (int i = 0; i < channels.length; i++) {
				if(i > 0 ) {
					channelNames.append(",");
				}
				channelNames.append("c").append(i + 1).append("=[").append(channels[i].getTitle()).append("]");
			}

			img = new ImagePlus();

			// "create" generates a composite, rather than a merge. "keep" duplicates the images to merge them nondestructively.
			IJ.run(img,"Merge Channels...", channelNames.toString() + " create keep");

			// close the open images.
			for (ImagePlus imp : channels) {
				imp.close();
			}
		}


		return img;

	}

}
