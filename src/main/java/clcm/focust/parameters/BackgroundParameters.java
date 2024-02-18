package clcm.focust.parameters;
import clcm.focust.filter.BackgroundType;
import clcm.focust.filter.Vector3D;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class BackgroundParameters {
    private BackgroundType backgroundType;
    private Vector3D sigma1;
    private Vector3D sigma2;
}
