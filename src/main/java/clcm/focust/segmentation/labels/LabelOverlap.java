package clcm.focust.segmentation.labels;

import ij.ImagePlus;
import ij.measure.ResultsTable;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import java.util.ArrayList;
import java.util.List;

import static clcm.focust.utility.SwingIJLoggerUtils.ijLog;

public class LabelOverlap {

    /**
     * Compute jaccard and sorensen overlap indices between secondary and tertiary objects for each primary object.
     * @param clij2
     * @param primary
     * @param secondary
     * @param tertiary
     * @return ResultsTable with overlap indices and primaryID.
     */
    public ResultsTable run(CLIJ2 clij2, ImagePlus primary, ImagePlus secondary, ImagePlus tertiary){

        ResultsTable results = new ResultsTable();

        ClearCLBuffer primaryBuffer = clij2.push(primary);
        ClearCLBuffer secondaryBuffer = clij2.push(secondary);
        ClearCLBuffer tertiaryBuffer = clij2.push(tertiary);

        ResultsTable stats = new ResultsTable();
        clij2.statisticsOfLabelledPixels(primaryBuffer, primaryBuffer, stats);

        for (int i = 0; i < stats.size(); i++) {

            int label = Integer.parseInt(stats.getStringValue("IDENTIFIER", i));
            ijLog("Processing primary object: " + label + " of " + stats.size());

            ClearCLBuffer primaryMask = clij2.create(primaryBuffer);

            // isolate the active primary object.
            clij2.labelToMask(primaryBuffer, primaryMask, label);

            // calculate overlap indices.
            List<Double> overlap = maskObjects(clij2, primaryMask, secondaryBuffer, tertiaryBuffer);

            primaryMask.close();

            results.addRow();
            results.addValue("Label", label);
            results.addValue("OverlapJaccard", overlap.get(0));
            results.addValue("OverlapSorensen", overlap.get(1));

        }

        primaryBuffer.close();
        secondaryBuffer.close();
        tertiaryBuffer.close();


        return results;
    }

    /**
     * Isolate the secondary and tertiary objects by the active primary object and run overlap calculations.
     * @param clij2
     * @param primary
     * @param secondary
     * @param tertiary
     * @return List of overlap indices.
     */
    public List<Double> maskObjects(CLIJ2 clij2, ClearCLBuffer primary, ClearCLBuffer secondary, ClearCLBuffer tertiary){

         ClearCLBuffer secondaryMask = clij2.create(secondary);
         ClearCLBuffer tertiaryMask = clij2.create(tertiary);

         clij2.mask(primary, secondary, secondaryMask);
         clij2.mask(primary, tertiary, tertiaryMask);

         List<Double> overlap = calculateOverlap(clij2, secondaryMask, tertiaryMask);

         secondaryMask.close();
         tertiaryMask.close();

         return overlap;
    }

    /**
     * Runs the jaccard and sorensen dice overlap calculations.
     * @return List with JI and SD indices.
     */
    public List<Double> calculateOverlap(CLIJ2 clij2, ClearCLBuffer sec, ClearCLBuffer ter){

        List<Double> results = new ArrayList<>();
        results.add(clij2.getJaccardIndex(sec, ter));
        results.add(clij2.getSorensenDiceCoefficient(sec, ter));

        return results;
    }
}