package clcm.focust;

import clcm.focust.filter.Vector3D;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Builder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Data
@Builder
public class FilterSpec {
    Vector3D sigma;

    Vector3D radii;

    public double greaterConstant;

    public FilterSpec(Vector3D sigma, Vector3D radii, double greaterConstant) {
        this.sigma = sigma;
        this.radii = radii;
        this.greaterConstant = greaterConstant;
    }

    public FilterSpec(){}  // Used for JSON deserialization

    public static void saveFilterSpecs(FilterSpec[] specs, String filename) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            File paramFile = new File(filename);
            mapper.writeValue(paramFile, specs);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static FilterSpec[] readFilterSpecJSON(String filename){
        try{
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(Paths.get(filename).toFile(), FilterSpec[].class);
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
