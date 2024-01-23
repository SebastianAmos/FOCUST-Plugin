package clcm.focust.parameters;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ParameterCollectionTest {


    @Test
    void saveParameterCollection() {
        ParameterCollection params = ParameterCollection.builder().
                outputDir("src/test/resources/").build();
        try {
            ParameterCollection.saveParameterCollection(params, "parameterCollectionWriteTest.json");
        }
        catch (IOException e){
            fail("IO Exception encountered");
        }
    }

    @Test
    void loadParameterCollection() {
        String filename = "parameterCollectionReadTest.json";
        try{
            ParameterCollection params = ParameterCollection.loadParameterCollection("src/test/resources/parameterCollectionWriteTest.json");
            assertEquals("src/test/resources/", params.outputDir);
        } catch (IOException e){
            fail("IO Exception encountered");
        }
    }
}