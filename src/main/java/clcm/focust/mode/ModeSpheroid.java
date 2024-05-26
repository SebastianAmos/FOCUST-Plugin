package clcm.focust.mode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import clcm.focust.parameters.ParameterCollection;
import clcm.focust.segmentation.labels.OverlapMapping;
import clcm.focust.segmentation.labels.StratifiedResultsHolder;
import clcm.focust.segmentation.labels.StratifyAndQuantifyLabels;
import clcm.focust.utility.ResultsTableUtility;
import clcm.focust.utility.TableUtility;
import clcm.focust.utility.Timer;
import ij.IJ;
import ij.measure.ResultsTable;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;

import static clcm.focust.utility.SwingIJLoggerUtils.ijLog;


public class ModeSpheroid implements Mode {

	

    private final OverlapMapping countLabels = new OverlapMapping();
	

	/** 
	 * - Primary objects (nuclei) are referred to as children
	 * - Secondary objects (spheroids) are referred to as parents
	 */
	
	@Override
	public void run(ParameterCollection parameters, CompiledImageData imgData, String imgName) {

		List<ResultsTable> tertiary = new ArrayList<>();
        List<ResultsTable> secondary = new ArrayList<>();
        List<ResultsTable> primary = new ArrayList<>();

		CLIJ2 clij2 = CLIJ2.getInstance();

		// init with current collated table for each type.
		imgData.images.getTertiary().ifPresent(t -> {
			tertiary.add(imgData.getTertiary());
		});

		
		// Count the number of primary/child objects within each secondary/parent object
		// centroids are created here and accessed by countLabels.centroids
		ResultsTable sec = imgData.getSecondary();
		TableUtility.appendResultsByLabel(sec, countLabels.countChildren(imgData.getImages().getSecondary(), imgData.getImages().getPrimary(), "Nuclei"), "");
		secondary.add(sec);


		ResultsTable pri = imgData.getPrimary();
		TableUtility.appendResultsByLabel(pri, countLabels.parentLabelOfCentroids(imgData.getImages().getSecondary(), countLabels.centroids), "");
		primary.add(pri);

		// Calculate the secondary/parent band (if available) for each primary/child object and the number of primary/children per secondary/parent.
		for (Entry<String, StratifiedResultsHolder> band : imgData.getStratifyResults().entrySet()) {

			String type = band.getKey();
			List<ClearCLBuffer> bands = band.getValue().getBands();
			
			switch (type) {

			case "sec25":

				primary.addAll(countLabels.parentBand(bands, countLabels.centroids, "Q")); // Q = quarter
				secondary.addAll(countLabels.perBand(bands, countLabels.centroids, "Q", "Nuclei"));
				break;

			case "sec50":

				primary.addAll(countLabels.parentBand(bands, countLabels.centroids, "H")); // H = half
				secondary.addAll(countLabels.perBand(bands, countLabels.centroids, "H", "Nuclei"));
				break;

			default:
				break;
			}
			
		}

		ResultsTableUtility rtSave = new ResultsTableUtility();

		rtSave.saveAndStackResults(TableUtility.appendAllResultsByLabel(primary), "primary_objects", parameters);
		
		
		/* If the tertiary objects were created by subtraction and do not represent induvidual cytoplasms, add them to the secondary table.
		 * otherwise, tertiary data needs to be saved independently and the same nested relationships between primary and secondaries as above need to be established
		 */
		
		if (imgData.images.getTertiary().isPresent()) {
	
			if (parameters.getTertiaryIsDifference()) { // combine with secondary -> labels should match
				
				// TODO: ********change names in tertiary table to (+ cyto) so that they can be merged safely without overwriting where col names match: 

				// Add "Tertiary" to all headers in tert tables before appending.
				ResultsTable ter = TableUtility.appendAllResultsByLabel(tertiary);
				TableUtility.appendToHeader("Tertiary.", ter);

                secondary.add(ter);
				rtSave.saveAndStackResults(TableUtility.appendAllResultsByLabel(secondary), "secondary_objects", parameters);
				
			} else {
				
				// compile and save secondary and tertiary independently
				rtSave.saveAndStackResults(TableUtility.appendAllResultsByLabel(secondary), "secondary_objects", parameters);
				rtSave.saveAndStackResults(TableUtility.appendAllResultsByLabel(tertiary), "tertiary_objects", parameters);
			}
			
		} else {
			rtSave.saveAndStackResults(TableUtility.appendAllResultsByLabel(secondary), "secondary_objects", parameters);
		}

		// clean up
		clij2.clear();

		Timer.stop(parameters);
	}

}
