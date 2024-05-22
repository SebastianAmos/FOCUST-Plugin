package clcm.focust.utility;

import ij.IJ;
import inra.ijpb.plugins.AnalyzeRegions3D;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clijx.morpholibj.MorphoLibJMarkerControlledWatershed;

public class CheckPlugins {


	public static boolean areAvailable() {

		if (isCLIJ2Available() && isIJPBAvailable() && isCLIJxAvailable() && isCLIJAvailable()) {
			return true;
		} else {
			return false;
		}

	}

	
	private static boolean isCLIJ2Available() {
		try {
			CLIJ2.class.getName();
		} catch (final NoClassDefFoundError err) {
			IJ.showMessage("Please activate all CLIJ and CLIJ2 update sites.");
			return false;
		}
		return true;
	}
	
	
	private static boolean isIJPBAvailable() {
		try {
			AnalyzeRegions3D.class.getName();
		} catch (final NoClassDefFoundError err) {
			IJ.showMessage("Please activate the IJPB-plugins update site.");
			return false;
		}
		return true;
	}
	
	
	private static boolean isCLIJxAvailable() {
		try {
			MorphoLibJMarkerControlledWatershed.class.getName();
		} catch (final NoClassDefFoundError err) {
			IJ.showMessage("Please activate all CLIJ and CLIJ2 update sites.");
			return false;
		}
		return true;
	}
	
	
	private static boolean isCLIJAvailable() {
		try {
			ClearCLBuffer.class.getName();
		} catch (final NoClassDefFoundError err) {
			IJ.showMessage("Please activate all CLIJ and CLIJ2 update sites.");
			return false;
		}
		return true;
	}
	
	
}
