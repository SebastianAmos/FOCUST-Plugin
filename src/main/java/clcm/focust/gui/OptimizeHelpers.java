package clcm.focust.gui;

import java.util.ArrayList;

import clcm.focust.segmentation.labels.LabelEditor;
import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.ImageStack;
import ij.plugin.ChannelSplitter;
import ij.process.ImageProcessor;

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
			
			gui.ImageName.setText(gui.list[gui.currentIndex]);
			
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
	
	
	
	/**
	 * creates a composite image with or without an edges overlay of segmented objects.
	 * @param original
	 * @param segmented
	 * @param withOverlay
	 * @return
	 */
	public ImagePlus processDisplay(ImagePlus original, ImagePlus segmented, Boolean withOverlay) {

		if (original.getBitDepth() != segmented.getBitDepth()) {
			ImageStack originalStack = segmented.getStack();
			ImageStack newStack = new ImageStack(segmented.getWidth(), segmented.getHeight());

			for (int i = 1; i <= originalStack.getSize(); i++) {
				ImageProcessor ip = originalStack.getProcessor(i);
				ImageProcessor convertedIP = convertBitDepth(ip, original.getBitDepth());
				newStack.addSlice(originalStack.getSliceLabel(i), convertedIP);
			}

			segmented.setStack(newStack);
		}

		ArrayList<ImagePlus> images = new ArrayList<>();
		images.add(original);
		
		if (withOverlay) {
			ImagePlus edges = LabelEditor.detectEdgesLabelled(segmented);
			images.add(edges);
			
		}
		
		ImagePlus[] imagesArray = new ImagePlus[images.size()];
		imagesArray = images.toArray(imagesArray);

        return createComposite(imagesArray);
	}


	private ImageProcessor convertBitDepth(ImageProcessor ip, int targetBitDepth) {
		if (ip.getBitDepth() == targetBitDepth) {
			return ip;
		}
		switch (targetBitDepth) {
			case 8:
				return ip.convertToByte(false);
			case 16:
				return ip.convertToShort(false);
			case 32:
				return ip.convertToFloat();
			default:
				throw new IllegalArgumentException("Unsupported bit depth: " + targetBitDepth);
		}
	}


	
	public ImagePlus setSliceMiddle(ImagePlus imp) {
		int nChannels = imp.getNChannels();
		int nSlices = imp.getStackSize();
		int middle = (nSlices + 1) / 2;
		
		for (int i = 1; i <= nChannels; i++) {
			imp.setPosition(i, 1, 1);
			imp.setSlice(middle);
			//IJ.resetMinAndMax(imp);
		}
		
		return imp;
	}

	/**
	 * Filter the open images for those that contain "Object" in the title.
	 * @return an array containing images with "Object" in the title.
	 */
	public ImagePlus[] getOpenImages(){

		int[] windowList = WindowManager.getIDList();

		ArrayList<ImagePlus> filteredImages = new ArrayList<>();

		for (int imp : windowList) {
			ImagePlus img = WindowManager.getImage(imp);
			if (img != null && img.getTitle().contains("Object")) {
				filteredImages.add(img);
			}
		}

		if(filteredImages.size() == 0) {
			IJ.showMessage("No segmented objects found. Ensure you process segmentation first.");
		}

		return filteredImages.toArray(new ImagePlus[0]);
	}

}
