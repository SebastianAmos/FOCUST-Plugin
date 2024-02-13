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

	SegmentedChannels images;
	Map<String, SkeletonResultsHolder> skeletons;
	Map<String, StratifiedResultsHolder> stratifyResults;
	
	ResultsTable primary;
	ResultsTable secondary;
	ResultsTable tertiary;
}
