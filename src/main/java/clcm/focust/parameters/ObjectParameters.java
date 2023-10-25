package clcm.focust.parameters;
import clcm.focust.filter.BackgroundType;
import clcm.focust.filter.FilterType;

import clcm.focust.segmentation.MethodTypes;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ObjectParameters {
    int channel;
    BackgroundType backgroundType;
    FilterParameters backgroundParameters;
    FilterType filterType;
    FilterParameters filterParameters;
    MethodTypes methodType;
    MethodParameters methodParameters;

}
