package clcm.focust.parameters;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ParameterCollectionTest {
	
	ParameterCollection params = ParameterCollection.builder().
            outputDir("src/test/resources/").build();
	
    @Test
    void saveParameterCollection() {
        try {
            ParameterCollection.saveParameterCollection(params, "parameterCollectionWriteTest.json");
        }
        catch (IOException e){
            fail("IO Exception encountered");
        }
    }
    
    
    @Test
    void loadParameterCollection() {
        try{
            ParameterCollection params = ParameterCollection.loadParameterCollection("src/test/resources/parameterCollectionWriteTest.json");
            assertEquals("src/test/resources/", params.getOutputDir());
        } catch (IOException e){
            fail("IO Exception encountered");
        }
    }
}
