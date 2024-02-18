package clcm.focust.segmentation.labels;

import java.util.List;
import ij.measure.ResultsTable;
import lombok.Builder;
import lombok.Data;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;

@Data
@Builder
public class StratifiedResultsHolder {
	
	private ResultsTable table;
	private List<ClearCLBuffer> bands;

	private List<ResultsTable> objectCounts; // record how many objects sit within the label
	private List<ResultsTable> parentObjects; // record which secondary objects a primary object sits within
	
}
