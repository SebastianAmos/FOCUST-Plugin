package clcm.focust.utility;

import clcm.focust.mode.ModeConstants;
import ij.ImagePlus;
import ij.measure.ResultsTable;
import inra.ijpb.measure.region2d.MorphometricFeatures2D;
import inra.ijpb.measure.region3d.MorphometricFeatures3D;

import static clcm.focust.utility.SwingIJLoggerUtils.ijLog;

// runs MorphometricFeatures3D from IJPB
public class MeasureRegions {

    public ResultsTable run(ImagePlus img){

        // Adapt for 2D or 3D images.
        if (img.getNSlices() < 2) {

            ijLog("Running 2D morphometric features...");

            return new MorphometricFeatures2D()
                .add(MorphometricFeatures2D.Feature.PIXEL_COUNT)
                .add(MorphometricFeatures2D.Feature.AREA)
                .add(MorphometricFeatures2D.Feature.PERIMETER)
                .add(MorphometricFeatures2D.Feature.EULER_NUMBER)
                .add(MorphometricFeatures2D.Feature.CIRCULARITY)
                .add(MorphometricFeatures2D.Feature.BOUNDING_BOX)
                .add(MorphometricFeatures2D.Feature.CENTROID)
                .add(MorphometricFeatures2D.Feature.EQUIVALENT_ELLIPSE)
                .add(MorphometricFeatures2D.Feature.ELLIPSE_ELONGATION)
                .add(MorphometricFeatures2D.Feature.CONVEXITY)
                .add(MorphometricFeatures2D.Feature.MAX_FERET_DIAMETER)
                .add(MorphometricFeatures2D.Feature.ORIENTED_BOX)
                .add(MorphometricFeatures2D.Feature.ORIENTED_BOX_ELONGATION)
                .add(MorphometricFeatures2D.Feature.GEODESIC_DIAMETER)
                .add(MorphometricFeatures2D.Feature.TORTUOSITY)
                .add(MorphometricFeatures2D.Feature.MAX_INSCRIBED_DISK)
                .add(MorphometricFeatures2D.Feature.AVERAGE_THICKNESS)
                .add(MorphometricFeatures2D.Feature.GEODESIC_ELONGATION)
                .computeTable(img);

        } else{

            ijLog("Running 3D morphometric features...");
        return new MorphometricFeatures3D()
                .add(MorphometricFeatures3D.Feature.VOXEL_COUNT)
                .add(MorphometricFeatures3D.Feature.VOLUME)
                .add(MorphometricFeatures3D.Feature.SURFACE_AREA)
                .add(MorphometricFeatures3D.Feature.MEAN_BREADTH)
                .add(MorphometricFeatures3D.Feature.EULER_NUMBER)
                .add(MorphometricFeatures3D.Feature.SPHERICITY)
                .add(MorphometricFeatures3D.Feature.BOUNDING_BOX)
                .add(MorphometricFeatures3D.Feature.CENTROID)
                .add(MorphometricFeatures3D.Feature.EQUIVALENT_ELLIPSOID)
                .add(MorphometricFeatures3D.Feature.ELLIPSOID_ELONGATIONS)
                .add(MorphometricFeatures3D.Feature.MAX_INSCRIBED_BALL)
                .computeTable(img);
        }
    }

}
