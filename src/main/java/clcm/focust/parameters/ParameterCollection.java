package clcm.focust.parameters;

import clcm.focust.KillBorderTypes;
import clcm.focust.data.DataObject;
import clcm.focust.mode.ModeType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.Data;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

@Data
@Builder
public class ParameterCollection implements DataObject {
	private final String inputDir;
	private final String outputDir;
	private final ModeType mode;
	private final Boolean analysisOnly;
    
    // Primary Object
	private final ObjectParameters primaryObject;
    // Secondary Object
	private final ObjectParameters secondaryObject;
    // Tertiary Object
	private final ObjectParameters tertiaryObject;

    // Common Paramters
	private final KillBorderTypes killBorderType;
    // Analysis Parameters
	private final String groupingInfo;
	private final String nameChannel1;
	private final String nameChannel2;
	private final String nameChannel3;
	private final String nameChannel4;
	private final Boolean tertiaryIsDifference;
	private final Boolean processTertiary;
	private final SkeletonParameters skeletonParamters;
	private final StratifyParameters stratifyParameters;

    // Optimize Parameters
    private final boolean primaryDisplayOriginal;
    private final boolean primaryOverlay;
    private final boolean secondaryDisplayOriginal;
    private final boolean secondaryOverlay;
    private final boolean tertiaryDisplayOriginal;
    private final boolean tertiaryOverlay;
    
    
    public static void saveParameterCollection(ParameterCollection parameterCollection, String filename) throws IOException {
    	
    	String dir = null;
    	
    	if (parameterCollection.getOutputDir().isEmpty()) {
			dir = parameterCollection.getInputDir();
		} else {
			dir = parameterCollection.getOutputDir();
		}
    	
        try (Writer writer = new FileWriter(dir + filename)) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(parameterCollection, writer);
        writer.flush();
        writer.close();
        }
    }
    
    
    public static ParameterCollection loadParameterCollection(String file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(new FileReader(file), ParameterCollection.class);
    }
}
