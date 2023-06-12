package clcm.focust;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FilterSpecTest {

    String getTestFileName(String filename){
        File file = new File(filename);
        return file.getAbsolutePath();
    }

    @org.junit.jupiter.api.Test
    void saveFilterSpecs() {
        FilterSpec[] specs = new FilterSpec[1];
        specs[0] = new FilterSpec(1, 2, 3, 4, 5, 6, 7);
        FilterSpec.saveFilterSpecs(specs, getTestFileName("src/test/resources/filterSpecWriteTest.json"));
    }

    @org.junit.jupiter.api.Test
    void readFilterSpecJSON() {
        String path = getTestFileName("src/test/resources/filterSpecReadTest.json");
        FilterSpec[] specs = FilterSpec.readFilterSpecJSON(path);
        assert specs != null;
        assertEquals(specs[0].sigma_x, 1);
        assertEquals(specs[0].sigma_y, 2);
        assertEquals(specs[0].sigma_z, 3);
        assertEquals(specs[0].radius_x, 4);
        assertEquals(specs[0].radius_y, 5);
        assertEquals(specs[0].radius_z, 6);
        assertEquals(specs[0].greaterConstant, 7);
    }
}