package clcm.focust.parameters;
import clcm.focust.filter.FilterType;
import clcm.focust.filter.Vector3D;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class FilterParameters {
	private FilterType filterType;
	private Vector3D sigma1;
	private Vector3D sigma2;
}
