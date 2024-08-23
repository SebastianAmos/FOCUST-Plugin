package clcm.focust.mode;

import ij.ImagePlus;
import ij.measure.ResultsTable;
import inra.ijpb.measure.region3d.MorphometricFeatures3D;

// runs MorphometricFeatures3D from IJPB
public class Measure3D {

    public ResultsTable run(ImagePlus img){

        return new MorphometricFeatures3D().add(MorphometricFeatures3D.Feature.VOXEL_COUNT)
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
