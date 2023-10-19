package clcm.focust.segmentation.skeleton;

import ij.measure.ResultsTable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkeletonResultsHolder {
	
	private ResultsTable standard;
	private ResultsTable extra;

}
