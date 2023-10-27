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

	FilterType filterType;
	Vector3D filterS1;
	Vector3D filterS2;
	
	BackgroundType backgroundType;
	Vector3D backgroundS1;
	Vector3D backgroundS2;
	
	MethodTypes methodType;
	ThresholdType thresholdType;
	Vector3D sigmaMethod;
	double thresholdSize;
	
	
	
	
}
