package clcm.focust;

import clcm.focust.filter.Vector3D;

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
        specs[0] = new FilterSpec(Vector3D.builder().x(1).y(2).z(3).build(), Vector3D.builder().x(4).y(5).z(6).build(), 7.0);
        FilterSpec.saveFilterSpecs(specs, getTestFileName("src/test/resources/filterSpecWriteTest.json"));
    }

    @org.junit.jupiter.api.Test
    void readFilterSpecJSON() {
        String path = getTestFileName("src/test/resources/filterSpecReadTest.json");
        FilterSpec[] specs = FilterSpec.readFilterSpecJSON(path);
        assert specs != null;
        assertEquals(specs[0].sigma.getX(), 1);
        assertEquals(specs[0].sigma.getY(), 2);
        assertEquals(specs[0].sigma.getZ(), 3);
        assertEquals(specs[0].radii.getX(), 4);
        assertEquals(specs[0].radii.getY(), 5);
        assertEquals(specs[0].radii.getZ(), 6);
        assertEquals(specs[0].greaterConstant, 7.0);
    }
}