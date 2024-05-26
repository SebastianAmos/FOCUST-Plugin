package clcm.focust.parameters;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ObjectParameters {
    int channel;
    BackgroundParameters backgroundParameters;
    FilterParameters filterParameters;
    MethodParameters methodParameters;

}
