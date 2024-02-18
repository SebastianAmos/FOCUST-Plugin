package clcm.focust.mode;

import java.util.Optional;

import ij.measure.ResultsTable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultsTables {

	private ResultsTable primary;
	private ResultsTable secondary;
	private Optional<ResultsTable> tertiary;

}
