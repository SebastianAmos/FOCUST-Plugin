package clcm.focust.speckle.service;

import static clcm.focust.SwingIJLoggerUtils.ijLog;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import clcm.focust.FOCUSTService;
import clcm.focust.TableUtility;
import clcm.focust.data.DataConstants;
import clcm.focust.data.DataConstants.Datum;
import clcm.focust.speckle.ExpectedSpeckleResults;
import clcm.focust.speckle.SpeckleResult;
import clcm.focust.speckle.SpeckleType;
import clcm.focust.speckle.SpecklesConfiguration;
import clcm.focust.data.DataListener;
import clcm.focust.data.DataMapSubscriptionService;
import clcm.focust.data.DatumService;
import clcm.focust.data.DatumSubscriptionService;
import ij.IJ;
import ij.measure.ResultsTable;
import lombok.RequiredArgsConstructor;

/**
 * Class that handles the results arriving from processed speckles.
 */
@RequiredArgsConstructor
public class SpeckleResultsHandlerService implements FOCUSTService {

	/** Where to register for updates on speckle Results. */
	private final DataMapSubscriptionService<String, SpeckleResult> speckleResultSubscriptionService;

	/** Where to register for updates on the expected speckle results. */
	private final DatumSubscriptionService<ExpectedSpeckleResults> expectedResultSubscriptionService;

	/** Where to get the configuration. */
	private final DatumService<SpecklesConfiguration> configSvc;

	/** Keys to wait for presence before publishing compiled results. */
	private Set<String> expectedKeys = new HashSet<>();

	/** Listens to updated speckle results. */
	private final DataListener<String, SpeckleResult> resultsListener = new SpeckleResultListener();

	/** Listens to updated expected results sets. */
	private final DataListener<DataConstants.Datum, ExpectedSpeckleResults> expectedListener = new ExpectedResultsListener();

	/**
	 * Cache the collected data so far. We're waiting for all the expectedKeys to be
	 * present.
	 */
	private final Map<String, SpeckleResult> cachedValues = new HashMap<>();

	/** Listener for speckle results. */
	private final class SpeckleResultListener implements DataListener<String, SpeckleResult> {
		/*
		 * If all results are present in the cache, this kicks off the compilation of
		 * results for all speckles.
		 */
		@Override
		public void dataUpdated(String key, SpeckleResult newData) {
			if (!expectedKeys.contains(key)) {
				ijLog("Not expecting this! [" + key);
				/* We're not expecting this value. Ignore. */
				return;
			}
			cachedValues.put(key, newData);
			if (checkComplete()) {
				processCompiledResults();
			} else {
				int missing = expectedKeys.size() - cachedValues.size();
				ijLog(("Incomplete: missing " + missing + " Result(s)"));
			}
		}

		@Override
		public void dataDeleted(String key) {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * Listens for changes to the expected results.
	 */
	private final class ExpectedResultsListener implements DataListener<DataConstants.Datum, ExpectedSpeckleResults> {

		@Override
		public void dataUpdated(Datum key, ExpectedSpeckleResults newData) {
			ijLog("New Expected Results! :" + newData.getResults().toString());
			expectedKeys = newData.getResults();
		}

		@Override
		public void dataDeleted(Datum key) {
			// TODO Auto-generated method stub
		}

	}

	private boolean checkComplete() {
		return cachedValues.keySet().containsAll(expectedKeys);
	}

	/**
	 * To be called when all results are available.
	 * 
	 * @return
	 */
	private void processCompiledResults() {
		SpecklesConfiguration rtConf = configSvc.get().orElseThrow(IllegalStateException::new);

		ResultsTable primaryTable = new ResultsTable();
		ResultsTable secondaryTable = new ResultsTable();
		ResultsTable tertiaryTable = new ResultsTable();

		/** WHAT R THOOOOOOSE */
		/* To sus out... */
		/*
		 * primary.forEach((k, v) -> primaryTable.setColumn(k, v.toArray(new
		 * Variable[v.size()]))); secondary.forEach((k, v) ->
		 * secondaryTable.setColumn(k, v.toArray(new Variable[v.size()])));
		 * tertiary.forEach((k, v) -> tertiaryTable.setColumn(k, v.toArray(new
		 * Variable[v.size()])));
		 */
		ijLog("Saving Results Tables...");
		ijLog("-------------------------------------------------------");

		/* Doing this for each image until seb says otherwise. */
		for (Map.Entry<String, SpeckleResult> entry : cachedValues.entrySet()) {
			Map<SpeckleType, ResultsTable> results = entry.getValue().getResults();
			TableUtility.saveTable(results.get(SpeckleType.PRIMARY), rtConf.getInputDirectory().toString(),
					"Primary_Results" + entry.getKey() + ".csv");
			TableUtility.saveTable(results.get(SpeckleType.SECONDARY), rtConf.getInputDirectory().toString(),
					"Secondary_Results" + entry.getKey() + ".csv");
			TableUtility.saveTable(results.get(SpeckleType.TERTIARY), rtConf.getInputDirectory().toString(),
					"Tertiary_Results" + entry.getKey() + ".csv");
		}

		TableUtility.saveTable(primaryTable, rtConf.getInputDirectory().toString(), "PrimaryTable.csv");
		TableUtility.saveTable(secondaryTable, rtConf.getInputDirectory().toString(), "SecondaryTable.csv");
		TableUtility.saveTable(tertiaryTable, rtConf.getInputDirectory().toString(), "TertiaryTable.csv");

		// TableUtility.saveTable(primary, dir, "ArrayListResults.csv");
		// TableUtility.saveTable(rt, dir, "RT.csv");
		// TableUtility.saveTable(primaryFinal, dir, "primaryFINAL.csv");

		// long endTime = System.currentTimeMillis();
		// double timeSec = (endTime - startTime) / 1000;
		ijLog("It took " + /* timeSec */ "NaN" + " seconds to process " + "Some " + " images.");
		ijLog("Speckle Batch Protocol Complete!");
		IJ.saveString(IJ.getLog(), rtConf.getInputDirectory().toString() + "Log.txt");
	}

	@Override
	public void init() {
		speckleResultSubscriptionService.registerAllKeysListener(resultsListener);
		expectedResultSubscriptionService.registerListener(expectedListener);
	}

	@Override
	public void shutdown() {
		speckleResultSubscriptionService.deregisterAllKeysListener(resultsListener);
		// TODO fix?
		expectedResultSubscriptionService.deregisterListener(DataConstants.Datum.DATUM, expectedListener);
	}

}
