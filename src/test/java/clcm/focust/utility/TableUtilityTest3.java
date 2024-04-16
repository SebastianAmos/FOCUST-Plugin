package clcm.focust.utility;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TableUtilityTest3 {

	@Test
	void testGetBaseLabel() {


		TableUtility tu = new TableUtility();
		
		double[] doubleArray = { 1.999, 3.14, 2.718, 1.414, 1.732, 5.678, -2.5, 10.0, 7.77, -0.99, 100.5 };
		
		for (double d : doubleArray) {
			System.out.println(d + " = " + tu.getBaseLabel(d));
		}
		
	}

}
