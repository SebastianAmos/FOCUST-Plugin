package clcm.focust.parameters;
import clcm.focust.filter.Vector3D;
import clcm.focust.segmentation.MethodTypes;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class MethodParameters {
    MethodTypes methodType;
    Vector3D sigma;
    String classifierFilename;
    double threshold;
}
