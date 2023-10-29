package clcm.focust.service;

import clcm.focust.FOCUSTService;
import clcm.focust.data.DataConstants;
import clcm.focust.data.DataConstants.Datum;
import clcm.focust.data.DataListener;
import clcm.focust.data.DatumSubscriptionService;
import clcm.focust.parameters.ParameterCollection;

public class ParameterModeService implements FOCUSTService, DataListener<DataConstants.Datum, ParameterCollection> {

	private DatumSubscriptionService<ParameterCollection> parameterSubService;
	
	public ParameterModeService(DatumSubscriptionService<ParameterCollection> parameterSubService) {
		super();
		this.parameterSubService = parameterSubService;
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
		// TODO Auto-generated method stub
		newData.getMode().getMode().run(newData);
	}

	@Override
	public void dataDeleted(Datum key) {
		// Do nothing
	}

}
