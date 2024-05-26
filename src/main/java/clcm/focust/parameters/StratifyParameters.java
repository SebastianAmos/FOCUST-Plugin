package clcm.focust.parameters;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StratifyParameters {
		Boolean primary25;
		Boolean primary50;
		Boolean secondary25;
		Boolean secondary50;
		Boolean tertiary25;
		Boolean tertiary50;
}
