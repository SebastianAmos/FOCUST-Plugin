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
    final String inputDir;
    final String outputDir;
    final ModeType mode;
    final Boolean analysisOnly;
    
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
    final Boolean processTertiary;
    final SkeletonParameters skeletonParamters;
    final StratifyParameters stratifyParameters;

    // Optimize Parameters
    final boolean primaryDisplayOriginal;
    final boolean primaryOverlay;
    final boolean secondaryDisplayOriginal;
    final boolean secondaryOverlay;
    final boolean tertiaryDisplayOriginal;
    final boolean tertiaryOverlay;
    
    
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
