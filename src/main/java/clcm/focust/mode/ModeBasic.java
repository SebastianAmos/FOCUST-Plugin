package clcm.focust.mode;

import clcm.focust.ResultsTableUtility;
import clcm.focust.parameters.ParameterCollection;
import static clcm.focust.SwingIJLoggerUtils.ijLog;

/**
 *
 * @author SebastianAmos
 * 
 */

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
			imgData.images.getTertiary().ifPresent(t ->{
				rtSave.saveAndStackResults(imgData.getTertiary(), "tertiary_objects", parameters);
			});
			
	}
} 