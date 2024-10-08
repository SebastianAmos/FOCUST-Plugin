package clcm.focust.mode;

import clcm.focust.parameters.ParameterCollection;
import clcm.focust.segmentation.labels.LabelOverlap;
import clcm.focust.segmentation.labels.OverlapMapping;
import clcm.focust.segmentation.labels.StratifiedResultsHolder;
import clcm.focust.utility.ResultsTableUtility;
import clcm.focust.utility.TableUtility;
import clcm.focust.utility.Timer;
import ij.measure.ResultsTable;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import static clcm.focust.utility.SwingIJLoggerUtils.ijLog;


public class ModeSpeckle implements Mode {

	private final OverlapMapping countLabels = new OverlapMapping();

	@Override
	public void run(ParameterCollection parameters, CompiledImageData imgData, String imgName) {

		List<ResultsTable> tertiary = new ArrayList<>();
		List<ResultsTable> secondary = new ArrayList<>();
		List<ResultsTable> primary = new ArrayList<>();

		CLIJ2 clij2 = CLIJ2.getInstance();

		// Count number of secondary objects within primary objects
		ResultsTable pri = imgData.getPrimary();
		TableUtility.appendResultsByLabel(pri, countLabels.countChildren(imgData.getImages().getPrimary(), imgData.getImages().getSecondary(), "SecondarySpeckles"), "");
		primary.add(pri);

		// Assign a parent to secondary speckle objects
		// centroids = secondary
		ResultsTable sec = imgData.getSecondary();
		TableUtility.appendResultsByLabel(sec, countLabels.parentLabelOfCentroids(imgData.getImages().getPrimary(), countLabels.centroids), "");
		secondary.add(sec);

		ClearCLBuffer secondaryCentroids = clij2.create(countLabels.centroids);
		clij2.copy(countLabels.centroids, secondaryCentroids);

		// Count number of tertiary objects within primary 	objects
		// centroids = tertiary
		imgData.getImages().getTertiary().ifPresent(t -> {
			// count speckles per nucleus
			ResultsTable ter = imgData.getTertiary();
			TableUtility.appendResultsByLabel(pri, countLabels.countChildren(imgData.getImages().getPrimary(), t, "TertiarySpeckles"), ""); // countLabels.centroids is reset here

			// assign parent
			TableUtility.appendResultsByLabel(ter, countLabels.parentLabelOfCentroids(imgData.getImages().getPrimary(), countLabels.centroids), "");
			tertiary.add(ter);
		});

		 //Calculate the overlap between secondary and tertiary speckles for each primary object.
		imgData.getImages().getTertiary().ifPresent(t -> {
			try {
				ijLog("Running overlap analysis...");
				LabelOverlap labelOverlap = new LabelOverlap();
				ResultsTable test = labelOverlap.run(clij2, imgData.getImages().getPrimary().duplicate(), imgData.getImages().getSecondary().duplicate(), t.duplicate());
				//test.show("test");
				primary.add(test);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		ijLog("Speckle overlap analysis complete");

		// if banding is available, count speckles within each band of the parent
		for (Entry<String, StratifiedResultsHolder> band : imgData.getStratifyResults().entrySet()) {

			String type = band.getKey();
			List<ClearCLBuffer> bands = band.getValue().getBands();

			switch (type) {

				case "pri25":

					secondary.addAll(countLabels.parentBand(bands, secondaryCentroids, "Q"));
					primary.addAll(countLabels.perBand(bands, secondaryCentroids, "Q", "SecondarySpeckles"));

					imgData.getImages().getTertiary().ifPresent(t -> {
						tertiary.addAll(countLabels.parentBand(bands, countLabels.centroids, "Q"));
						primary.addAll(countLabels.perBand(bands, countLabels.centroids, "Q", "TertiarySpeckles"));
					});

					break;

				case "pri50":

					secondary.addAll(countLabels.parentBand(bands, secondaryCentroids, "H"));
					primary.addAll(countLabels.perBand(bands, secondaryCentroids, "H", "SecondarySpeckles"));

					imgData.getImages().getTertiary().ifPresent(t -> {
						tertiary.addAll(countLabels.parentBand(bands, countLabels.centroids, "H"));
						primary.addAll(countLabels.perBand(bands, countLabels.centroids, "H", "TertiarySpeckles"));
					});
					break;

				default:
					break;

			}

		}



		// save the results
		ResultsTableUtility rtSave = new ResultsTableUtility();
		rtSave.saveAndStackResults(TableUtility.appendAllResultsByLabel(primary), "primary_objects", parameters);
		rtSave.saveAndStackResults(TableUtility.appendAllResultsByLabel(secondary), "secondary_objects", parameters);
		imgData.getImages().getTertiary().ifPresent(t -> rtSave.saveAndStackResults(TableUtility.appendAllResultsByLabel(tertiary), "tertiary_objects", parameters));

		ijLog("Results Saved.");

		// clean up
		clij2.clear();

		Timer.stop(parameters);
		
	}

}
