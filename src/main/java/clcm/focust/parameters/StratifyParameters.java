package clcm.focust.parameters;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StratifyParameters {
	private Boolean primary25;
	private Boolean primary50;
	private Boolean secondary25;
	private Boolean secondary50;
	private Boolean tertiary25;
	private Boolean tertiary50;
}
