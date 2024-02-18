package clcm.focust.parameters;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkeletonParameters {
	private Boolean primary;
	private Boolean secondary;
	private Boolean tertairy;
}
