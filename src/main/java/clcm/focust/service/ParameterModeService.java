package clcm.focust.service;

import clcm.focust.FOCUSTService;
import clcm.focust.data.DataConstants;
import clcm.focust.data.DataConstants.Datum;
import clcm.focust.data.DataListener;
import clcm.focust.data.DatumSubscriptionService;
import clcm.focust.data.DatumUpdateService;
import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.mode.ModeAnalyse;
import clcm.focust.mode.ModeProcess;
import clcm.focust.parameters.ParameterCollection;

public class ParameterModeService implements FOCUSTService, DataListener<DataConstants.Datum, ParameterCollection> {

	
	private DatumUpdateService<SegmentedChannels> segmentedUpdateService;
	
	private DatumSubscriptionService<ParameterCollection> parameterSubService;
	
	public ParameterModeService(DatumSubscriptionService<ParameterCollection> parameterSubService, DatumUpdateService<SegmentedChannels> segmentedChannelsService) {
		super();
		this.parameterSubService = parameterSubService;
		this.segmentedUpdateService = segmentedChannelsService;
	}

	@Override
	public void init() {
		parameterSubService.registerListener(this); 
	}

	@Override
	public void shutdown() {
		parameterSubService.deregisterListener(DataConstants.Datum.DATUM, this);
	}
	
	

	@Override
	public void dataUpdated(Datum key, ParameterCollection newData) {
		//TODO was this temporary @seb?
		ModeProcess process = new ModeProcess();
		process.run(newData);
		//segmentedUpdateService.notifyUpdated(newData.getMode().getMode().run(newData));
		
	}

	@Override
	public void dataDeleted(Datum key) {
		// Do nothing
	}

}
