package clcm.focust.segmentation.labels;



import org.junit.jupiter.api.Test;

class StratifyAndQuantifyLabelsTest {

	@Test
	void test() {
		// get histogram max
		double min = 0;
		double max = 76;
		
		// 25 % of max to increment histogram bin by
		double binSize = max * 0.25;
		
		for (int i = 0; i < 4; i++) {
			double thresholdMin = min + i * 0.25 * (max-min);
			double thresholdMax = min + (i + 1) * 0.25 * (max-min);
			System.out.println("Iteration: " + i + " Min: " + thresholdMin + " Max: " + thresholdMax);
		}
	}

}
