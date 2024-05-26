package clcm.focust.mode;

import static clcm.focust.utility.SwingIJLoggerUtils.ijLog;

import clcm.focust.parameters.ParameterCollection;
import clcm.focust.utility.ResultsTableUtility;
import clcm.focust.utility.Timer;
import net.haesleinhuepf.clij2.CLIJ2;



public class ModeBasic implements Mode {

	/**
	 * Each object type is treated independently and one results table per object type is generated.
	 */
	
	@Override
	public void run(ParameterCollection parameters, CompiledImageData imgData, String imgName) {
			
			
			ResultsTableUtility rtSave = new ResultsTableUtility();
			
			ijLog("Saving results tables...");
			
			// Primary results
			rtSave.saveAndStackResults(imgData.getPrimary(), "primary_objects", parameters);

			// Secondary results
			rtSave.saveAndStackResults(imgData.getSecondary(), "secondary_objects", parameters);

			// Tertiary results (optional)
			imgData.getImages().getTertiary().ifPresent(t -> rtSave.saveAndStackResults(imgData.getTertiary(), "tertiary_objects", parameters));

			
			// clean up
			CLIJ2 clij2 = CLIJ2.getInstance();
			clij2.clear();

			Timer.stop(parameters);
	}
} 