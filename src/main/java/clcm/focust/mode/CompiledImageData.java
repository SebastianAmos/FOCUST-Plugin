package clcm.focust.mode;

import java.util.Map;
import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.segmentation.labels.StratifiedResultsHolder;
import clcm.focust.segmentation.skeleton.SkeletonResultsHolder;
import ij.measure.ResultsTable;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CompiledImageData {

	private SegmentedChannels images;
	private Map<String, SkeletonResultsHolder> skeletons;
	private Map<String, StratifiedResultsHolder> stratifyResults;
	
	private ResultsTable primary;
	private ResultsTable secondary;
	private ResultsTable tertiary;
}
