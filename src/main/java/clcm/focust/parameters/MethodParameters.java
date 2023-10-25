package clcm.focust.parameters;
import clcm.focust.filter.Vector3D;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class MethodParameters {
    Vector3D sigma;
    String classifierFilename;
    float threshold;
}
