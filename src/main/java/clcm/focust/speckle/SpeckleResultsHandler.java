package clcm.focust.speckle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import clcm.focust.FOCUST;
import clcm.focust.FOCUSTService;
import clcm.focust.TableUtility;
import clcm.focust.config.SpecklesConfiguration;
import clcm.focust.data.DataListener;
import clcm.focust.data.DataMapSubscriptionService;
import clcm.focust.data.DatumUpdateService;
import clcm.focust.data.DatumService;
import ij.IJ;
import ij.macro.Variable;
import ij.measure.ResultsTable;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class SpeckleResultsHandler implements FOCUSTService, DataListener<String,SpeckleResult>{
	
	/** Where to register for updates on speckle Results. */
	private final DataMapSubscriptionService<String, SpeckleResult> speckleResultSubscriptionService; 
	
	/** Where to put the final result. */
	private final DatumUpdateService<CompiledSpeckleResults> compiledResultUpdateService;
	
	private final DatumService<SpecklesConfiguration> configSvc = FOCUST.instance().rtConfManager();
	
	/** Keys to wait for presence before publishing compiled results. */
	private final Set<String> expectedKeys;
	
	/** Cache the collected data so far. We're waiting for all the expectedKeys to be present. */ 
	private final Map<String, SpeckleResult> cachedValues = new HashMap<>();
	
	
	/**
	 * If all results are present in the cache, this kicks off the compilation of results for all speckles.
	 */
	@Override
	public void dataUpdated(String key, SpeckleResult newData) {
		if (!expectedKeys.contains(key)) {
			/* We're not expecting this value. Ignore. */
			return;
		}
		cachedValues.put(key, newData);
		if (checkComplete()) {
			processCompiledResults();
		}	
	}

	@Override
	public void dataDeleted(String key) {
		// TODO Auto-generated method stub
		
	}
	
	/** TODO. Method checks if all keys of expectedKeys are done. */
	private boolean checkComplete() {
		return false;
	}
	
	private boolean processCompiledResults() {
		SpecklesConfiguration rtConf = configSvc.get().orElseThrow(IllegalStateException::new);

		ResultsTable primaryTable = new ResultsTable();
		ResultsTable secondaryTable = new ResultsTable();
		ResultsTable tertiaryTable = new ResultsTable();

		/* To sus out... */
		primary.forEach((k, v) -> primaryTable.setColumn(k, v.toArray(new Variable[v.size()])));
		secondary.forEach((k, v) -> secondaryTable.setColumn(k, v.toArray(new Variable[v.size()])));
		tertiary.forEach((k, v) -> tertiaryTable.setColumn(k, v.toArray(new Variable[v.size()])));

		IJ.log("Saving Results Tables...");
		IJ.log("-------------------------------------------------------");

		/* Hang on. We're only doing the last image here? */
		TableUtility.saveTable(primaryFinalResults, rtConf.getInputDirectory().toString() , "Primary_Results.csv");
		TableUtility.saveTable(secondaryFinalResults, rtConf.getInputDirectory().toString(), "Secondary_Results.csv");
		TableUtility.saveTable(tertiaryFinalResults, rtConf.getInputDirectory().toString(), "Tertiary_Results.csv");

		TableUtility.saveTable(primaryTable, rtConf.getInputDirectory().toString(), "PrimaryTable.csv");
		TableUtility.saveTable(secondaryTable, rtConf.getInputDirectory().toString(), "SecondaryTable.csv");
		TableUtility.saveTable(tertiaryTable, rtConf.getInputDirectory().toString(), "TertiaryTable.csv");

		// TableUtility.saveTable(primary, dir, "ArrayListResults.csv");
		// TableUtility.saveTable(rt, dir, "RT.csv");
		// TableUtility.saveTable(primaryFinal, dir, "primaryFINAL.csv");

		//long endTime = System.currentTimeMillis();
		//double timeSec = (endTime - startTime) / 1000;
		IJ.log("It took " + /* timeSec */ "NaN" + " seconds to process " + "Some "+ " images.");
		IJ.log("Speckle Batch Protocol Complete!");
		IJ.getLog();
		IJ.saveString(IJ.getLog(), rtConf.getInputDirectory().toString() + "Log.txt");
	}

	@Override
	public void init() {
		speckleResultSubscriptionService.registerAllKeysListener(this);
	}

	@Override
	public void shutdown() {
		speckleResultSubscriptionService.deregisterAllKeysListener(this);
	}

}
