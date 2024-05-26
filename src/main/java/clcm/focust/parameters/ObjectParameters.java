package clcm.focust.parameters;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ObjectParameters {
    private int channel;
    private BackgroundParameters backgroundParameters;
    private FilterParameters filterParameters;
    private MethodParameters methodParameters;

}
