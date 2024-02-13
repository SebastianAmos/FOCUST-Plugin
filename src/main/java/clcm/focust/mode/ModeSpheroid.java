package clcm.focust.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import clcm.focust.parameters.ParameterCollection;
import clcm.focust.segmentation.labels.OverlapMapping;
import clcm.focust.segmentation.labels.StratifiedResultsHolder;
import ij.measure.ResultsTable;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;

public class ModeSpheroid implements Mode {

	
	List<ResultsTable> tertiary = new ArrayList<>();
	List<ResultsTable> secondary = new ArrayList<>();
	List<ResultsTable> primary = new ArrayList<>();
	OverlapMapping countLabels = new OverlapMapping();
	
	/* Spheroid method will do the following:
	 * Count how many nuclei per spheroid
	 * Combine secondary and tertiary results tables (for whole spheroid and cytoplasmic --> ONLY IF NOT SEGMENTING TERTIARY
	 * Count how many primary objects per band if available 
	 * -> reduce nuclei to centroids first then count to limit crossover
	 */
	

	/** 
	 * - Primary objects (nuclei) are referred to as children
	 * - Secondary objects (spheroids) are referred to as parents
	 */
	
	@Override
	public void run(ParameterCollection parameters, CompiledImageData imgData, String imgName) {
		
		// init with current collated table.
		primary.add(imgData.getPrimary());
		secondary.add(imgData.getSecondary());
		imgData.images.getTertiary().ifPresent(t -> {
			tertiary.add(imgData.getTertiary());
		});
	
		
		// Count primary objects within each secondary object
		primary.add(countLabels.perLabel(imgData.getImages().getSecondary(), imgData.getImages().getPrimary(), "Primary"));
		
		// Calculate the parent band (if available) for each primary object and the number of primary/children per secondary/parent.
		
		for (Entry<String, StratifiedResultsHolder> band : imgData.getStratifyResults().entrySet()) {

			String type = band.getKey();
			List<ClearCLBuffer> bands = band.getValue().getBands();
			
			switch (type) {
			case "sec25":

				primary.add(countLabels.parentBand(bands, imgData.getImages().getPrimary()), "Q");
				secondary.add(countLabels.perBand(bands, imgData.getImages().getPrimary(), "Q", "Primary")); // Q = quarter
				
			case "sec50":
				
				primary.add(countLabels.parentBand(bands, imgData.getImages().getPrimary()), "H");
				secondary.add(countLabels.perBand(bands, imgData.getImages().getPrimary(), "H", "Primary"));
				
				
			default:
				break;
			}
			
		}

		



		// Count primary objects per band type
		
		
		
		
		// Calculate the parent secondary object each primary object resides within
		secondary.add(countLabels.parentLabel(imgData.getImages().getPrimary(), imgData.getImages().getSecondary()));
		
		
		
		
		
		
		
		
		
		for (Entry<String, StratifiedResultsHolder> band : imgData.getStratifyResults().entrySet()) {

			String type = band.getKey();
			List<ClearCLBuffer> bands = band.getValue().getBands();

			
		}


		List<ResultsTable> overlaps;




		// Record the band a the primary object resides within
		List<ResultsTable> parents;
		
	}

}
