package clcm.focust.utility;

import java.util.HashMap;
import java.util.Map;

public class MapTesting {

	public static void main(String[] args) {
		
		Map<Double, Double> map = new HashMap<>();
		Map<Double, Double> newMap = new HashMap<>();
		
		map.put(1.0, 1.0);
		map.put(2.0, 2.0);
		map.put(3.0, 1.0);
		map.put(4.0, 3.0);
		map.put(5.0, 4.0);
		map.put(6.0, 5.0);
		map.put(7.0, 5.0);
		map.put(8.0, 5.0);
		map.put(9.0, 5.0);
		map.put(10.0, 2.0);
		map.put(11.0, 2.0);
		
		
		System.out.println("Original Map: " + map);
		
		ManageDuplicates md = new ManageDuplicates();
		
		
		
		newMap = md.mapDuplicates(map);
		
		System.out.println("New Map: " + newMap);
		
	}

}
