package clcm.focust.segmentation.skeleton;

import ij.ImagePlus;
import ij.measure.ResultsTable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkeletonResultsHolder {
	
	private ResultsTable standard;
	private ResultsTable extra;
	private ResultsTable labelMatched;
	private ImagePlus labelledSkeletons;

}
