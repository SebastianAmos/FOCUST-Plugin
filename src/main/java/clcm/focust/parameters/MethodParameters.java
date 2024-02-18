package clcm.focust.parameters;
import clcm.focust.filter.Vector3D;
import clcm.focust.segmentation.MethodTypes;
import clcm.focust.threshold.ThresholdType;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class MethodParameters {
	private MethodTypes methodType;
	private ThresholdType thresholdType; 
	private Vector3D sigma;
	private String classifierFilename;
	private double thresholdSize;
}
