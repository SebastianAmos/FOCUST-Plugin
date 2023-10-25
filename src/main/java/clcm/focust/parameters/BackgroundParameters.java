package clcm.focust.parameters;
import clcm.focust.filter.BackgroundType;
import clcm.focust.filter.Vector3D;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class BackgroundParameters {
    BackgroundType backgroundType;
    Vector3D sigma1;
    Vector3D sigma2;
}
