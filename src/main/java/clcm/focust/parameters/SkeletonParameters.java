package clcm.focust.parameters;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkeletonParameters {
	Boolean primary;
	Boolean secondary;
	Boolean tertairy;
}
