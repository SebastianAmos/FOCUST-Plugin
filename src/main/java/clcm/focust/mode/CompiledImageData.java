package clcm.focust.mode;

import java.util.Optional;

import clcm.focust.data.object.SegmentedChannels;
import clcm.focust.segmentation.skeleton.SkeletonResultsHolder;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CompiledImageData {

	SegmentedChannels images;
	Optional<SkeletonResultsHolder> secondarySkeletons;
	Optional<SkeletonResultsHolder> tertiarySkeletons;
	
}
