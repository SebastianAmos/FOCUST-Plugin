package clcm.focust.parameters;
import clcm.focust.KillBorderTypes;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ParameterCollection {
    String inputDir;
    // Primary Object
    ObjectParameters primaryObject;
    // Secondary Object
    ObjectParameters secondaryObject;
    // Tertiary Object
    ObjectParameters tertiaryObject;

    // Common Paramters
    KillBorderTypes killBorderType;
    // Analysis Parameters
    String groupingInfo;
    String nameChannel1;
    String nameChannel2;
    String nameChannel3;
    String nameChannel4;
    Boolean tertiaryIsDifference;
    Boolean coreVPeriphery;
    double coreVolume;
    // Optimize Parameters
    boolean primaryDisplayOriginal;
    boolean primaryOverlay;
    boolean secondaryDisplayOriginal;
    boolean secondaryOverlay;
    boolean tertiaryDisplayOriginal;
    boolean tertiaryOverlay;

}
