package clcm.focust;

import java.util.Map;

import ij.ImagePlus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelabelledObjects {

	
	private Map<Double, Double> map;
	private ImagePlus relabelled;
	
}
