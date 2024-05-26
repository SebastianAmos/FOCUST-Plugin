package clcm.focust.parameters;
import clcm.focust.filter.FilterType;
import clcm.focust.filter.Vector3D;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class FilterParameters {
    FilterType filterType;
    Vector3D sigma1;
    Vector3D sigma2;
}
