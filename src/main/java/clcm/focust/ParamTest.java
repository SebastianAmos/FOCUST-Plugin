package clcm.focust;

import clcm.focust.filter.BackgroundType;
import clcm.focust.filter.FilterType;
import clcm.focust.filter.Vector3D;
import clcm.focust.threshold.ThresholdType;
import clcm.focust.segmentation.MethodTypes;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParamTest {

	private FilterType filterType;
	private Vector3D filterS1;
	private Vector3D filterS2;
	
	private BackgroundType backgroundType;
	private Vector3D backgroundS1;
	private Vector3D backgroundS2;
	
	private MethodTypes methodType;
	private ThresholdType thresholdType;
	private Vector3D sigmaMethod;
	private double thresholdSize;
	
}
