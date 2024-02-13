package clcm.focust.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import clcm.focust.ResultsTableUtility;
import clcm.focust.TableUtility;
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
	

	/** 
	 * - Primary objects (nuclei) are referred to as children
	 * - Secondary objects (spheroids) are referred to as parents
	 */
	
	@Override
	public void run(ParameterCollection parameters, CompiledImageData imgData, String imgName) {
		
		// init with current collated table for each type.
		primary.add(imgData.getPrimary());
		secondary.add(imgData.getSecondary());
		imgData.images.getTertiary().ifPresent(t -> {
			tertiary.add(imgData.getTertiary());
		});
	
		
		// Count the number of primary/child objects within each secondary/parent object
		primary.add(countLabels.perLabel(imgData.getImages().getSecondary(), imgData.getImages().getPrimary(), "Primary"));
		
		
		// Calculate the ID of the secondary/parent object each primary/child object resides within.
		secondary.add(countLabels.parentLabel(imgData.getImages().getPrimary(), imgData.getImages().getSecondary()));
		
		
		// Calculate the secondary/parent band (if available) for each primary/child object and the number of primary/children per secondary/parent.
		for (Entry<String, StratifiedResultsHolder> band : imgData.getStratifyResults().entrySet()) {

			String type = band.getKey();
			List<ClearCLBuffer> bands = band.getValue().getBands();
			
			switch (type) {
			case "sec25":

				primary.add(countLabels.parentBand(bands, imgData.getImages().getPrimary(), "Q")); // Q = quarter
				secondary.add(countLabels.perBand(bands, imgData.getImages().getPrimary(), "Q", "Primary")); 
				
			case "sec50":
				
				primary.add(countLabels.parentBand(bands, imgData.getImages().getPrimary(), "H")); // H = half
				secondary.add(countLabels.perBand(bands, imgData.getImages().getPrimary(), "H", "Primary"));
				
			default:
				break;
			}
			
		}

		
		
		TableUtility tu = new TableUtility();
		ResultsTableUtility rtSave = new ResultsTableUtility();
		
		imgData.setPrimary(tu.compileAllResults(primary));
		rtSave.saveAndStackResults(imgData.getPrimary(), "primary_objects", parameters);
		
		
		/* If the tertiary objects were created by subtraction and do not represent induvidual cytoplasms, add them to the secondary table.
		 * otherwise, tertiary data needs to be saved independently and the same nested relationships between primary and secondaries as above need to be established
		 */
		
		if (imgData.images.getTertiary().isPresent()) {
	
			if (parameters.getTertiaryIsDifference()) { // combine with secondary -> labels should match
				
				// TODO: ********change names in tertiary table to (+ cyto) so that they can be merged safely without overwriting where col names match: 
				
				tertiary.stream().forEach(t -> secondary.add(t));
				imgData.setSecondary(tu.compileAllResults(secondary));
				rtSave.saveAndStackResults(imgData.getSecondary(), "secondary_objects", parameters);
				
			} else {
				
				// compile secondary and tertiary independently
				imgData.setSecondary(tu.compileAllResults(secondary));
				imgData.setTertiary(tu.compileAllResults(tertiary));
			}
			
		} else {
			imgData.setSecondary(tu.compileAllResults(secondary));
		}
			
		rtSave.saveAndStackResults(imgData.getSecondary(), "secondary_objects", parameters);
		rtSave.saveAndStackResults(imgData.getTertiary(), "tertiary_objects", parameters);
		
	}

}
