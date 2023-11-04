package clcm.focust.parameters;
import clcm.focust.KillBorderTypes;
import clcm.focust.data.DataObject;
import clcm.focust.mode.ModeType;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ParameterCollection implements DataObject{
    final String inputDir;
    final String outputDir;
    final ModeType mode;
    // Primary Object
    final ObjectParameters primaryObject;
    // Secondary Object
    final ObjectParameters secondaryObject;
    // Tertiary Object
    final ObjectParameters tertiaryObject;

    // Common Paramters
    final KillBorderTypes killBorderType;
    // Analysis Parameters
    final String groupingInfo;
    final String nameChannel1;
    final String nameChannel2;
    final String nameChannel3;
    final String nameChannel4;
    final Boolean tertiaryIsDifference;
    final Boolean coreVPeriphery;
    final Boolean processTertiary;
    final double coreVolume;
    final Boolean skeletonize;

    // Optimize Parameters
    final boolean primaryDisplayOriginal;
    final boolean primaryOverlay;
    final boolean secondaryDisplayOriginal;
    final boolean secondaryOverlay;
    final boolean tertiaryDisplayOriginal;
    final boolean tertiaryOverlay;

}
