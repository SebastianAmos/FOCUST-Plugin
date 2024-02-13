package clcm.focust.segmentation.labels;

import java.util.List;
import ij.measure.ResultsTable;
import lombok.Builder;
import lombok.Data;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;

@Data
@Builder
public class StratifiedResultsHolder {
	
	ResultsTable table;
	List<ClearCLBuffer> bands;

	
}
