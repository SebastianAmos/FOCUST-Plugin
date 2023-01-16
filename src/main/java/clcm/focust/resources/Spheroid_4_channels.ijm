// Sebastian Amos 2022
// This script will output spheroid and nuclei results into seperate spreadsheets. 
// Data outputs from this pipeline maintain given grouping and imageID values.
// Outputs from this script are well optimised for cleaning and analysis in R or python. 

/*======================================
// Reminder --> Set scale to global!!!!
========================================*/

//Ask the user for an output folder - results and tifs will be saved here
#@ File (label = "Input directory", style = "directory") inputDir
#@ File (label = "Output directory", style = "directory") output
#@ String (label = "Channel 2 Stain", value = "") channel2
#@ String (label = "Channel 3 Stain", value = "") channel3
#@ String (label = "Channel 4 Stain", value = "") channel4
#@ String (label = "Grouping?", value = "") groupvalue

/* ==============================
 * START THE TIMER AND BATCH LOOP
 ===============================*/

//Timestamp
verystart = getTime();
Sheet = 0;

// Make some arrays to store data for concat. later
originalimagename = newArray();
datagrouping = newArray();
Groupingnuc = newArray();
NucImageID = newArray();
NucLabel = newArray();
NucVox = newArray();
NucVol = newArray();
NucSpher = newArray();
NucElong = newArray();
WholeSpheroidVox = newArray();
WholeSpheroidVol = newArray();
WholeSpheriodSpher = newArray();
WholeSpheroidElong = newArray();
C2WholeMean = newArray();
C2WholeVox = newArray();
C2WholeIntDen = newArray();
C3WholeMean = newArray();
C3WholeVox = newArray();
C3WholeIntDen = newArray();
C4WholeMean = newArray(); 
C4WholeIntDen = newArray();
InnerSpheroidVox = newArray();
OuterSpheroidVox = newArray(); 
C2InnerSpheroidMean = newArray();
C2InnerSpheroidIntDen = newArray(); 
C3InnerSpheroidMean = newArray(); 
C3InnerSpheroidIntDen = newArray(); 
C4InnerSpheroidMean = newArray(); 
C4InnerSpheroidIntDen = newArray();
C2OuterSpheroidMean = newArray(); 
C2OuterSpheroidIntDen = newArray(); 
C3OuterSpheroidMean = newArray(); 
C3OuterSpheroidIntDen = newArray();
C4OuterSpheroidMean = newArray();
C4OuterSpheroidIntDen = newArray();
C2NucMean = newArray(); 
C2NucIntDen = newArray(); 
C3NucMean = newArray();
C3NucIntDen = newArray();
C4NucMean = newArray();
C4NucIntDen = newArray();
CytoVox = newArray();
CytoVol = newArray(); 
C2CytoMean = newArray(); 
C2CytoIntDen = newArray(); 
C3CytoMean = newArray(); 
C3CytoIntDen = newArray(); 
C4CytoMean = newArray();
C4CytoIntDen = newArray();


/*===================================
 *Single Image Loop Starts Here
 ====================================*/

setBatchMode(true);

// Get a list of items in the input directory and iterate through them
FileList = getFileList(inputDir);
for (f = 0; f < lengthOf(FileList); f++) {
	ActiveImage = inputDir + File.separator + FileList[f];
	if (!File.isDirectory(ActiveImage));
	open(ActiveImage);
	Sheet++;
	imagestart = getTime();

// Initiate the GPU to improve compute time
run("CLIJ2 Macro Extensions", "cl_device=");
Ext.CLIJ2_clear();

// Grab orig file name, split channels, close channel 3 and select channel 1
ImageName = getTitle();
originalimagename = Array.concat(originalimagename, ImageName);
group = groupvalue;
datagrouping = Array.concat(datagrouping, group);
selectWindow(ImageName);
run("Split Channels");
selectWindow("C1-" +ImageName);
image1 = getTitle();
run("Subtract Background...", "rolling=50 stack");

/*=============================
 * SEGMENTING THE NUCLEI 
==============================*/

// Load the DAPI image into the GPU and keep it there until segmentation complete.
Ext.CLIJ2_push(image1);

// Copy
Ext.CLIJ2_copy(image1, image2);

// Gaussian Blur3D
sigma_x = 2;
sigma_y = 2;
sigma_z = 2;
Ext.CLIJ2_gaussianBlur3D(image2, image3, sigma_x, sigma_y, sigma_z);

// Invert
Ext.CLIJ2_invert(image3, image4);

// Threshold Otsu
Ext.CLIJ2_thresholdOtsu(image3, image5);

// Detect Maxima3D Box
radiusX = 10;
radiusY = 9;
radiusZ = 7;
Ext.CLIJ2_detectMaxima3DBox(image3, image7, radiusX, radiusY, radiusZ);

// Label Spots - gives each segmentation an object ID
Ext.CLIJ2_labelSpots(image7, image8);
run("glasbey_on_dark");

// Marker Controlled Watershed
Ext.CLIJx_morphoLibJMarkerControlledWatershed(image4, image8, image5, segmented);
Ext.CLIJ2_pull(segmented);
run("glasbey_on_dark");
Ext.CLIJ2_release(segmented);
saveAs("tif", output + File.separator + ImageName + "_Segmented_Nuclei" + ".TIF");

// Cleanup the GPU
Ext.CLIJ2_clear();

/*==========================
 * CREATE WHOLE SPHEROID ROI
===========================*/
selectWindow("C4-"+ ImageName);
run("Duplicate...", "duplicate");
image_10 = getTitle();
Ext.CLIJ2_push(image_10);

// Copy
Ext.CLIJ2_copy(image_10, image_12);

// Gaussian Blur3D
sigma_x = 3;
sigma_y = 3;
sigma_z = 3;
Ext.CLIJ2_gaussianBlur3D(image_12, image_13, sigma_x, sigma_y, sigma_z);

// Greater Constant
constant = 60;
Ext.CLIJ2_greaterConstant(image_13, image_14, constant);

// Fill holes inside spheroid
Ext.CLIJ2_binaryFillHoles(image_14, image_15);

Ext.CLIJ2_pull(image_15);

saveAs("tif", output + File.separator + ImageName + "_Whole_Spheroid" + ".TIF");

// Cleanup the GPU
Ext.CLIJ2_clear();

/*========================
 * 3D NUCLEI MEASUREMENTS
 =========================*/

//Perform 3D volumetric analysis on the nuclei and add store in arrays
selectWindow(ImageName+ "_Segmented_Nuclei.tif");
run("Analyze Regions 3D", "voxel_count volume surface_area mean_breadth sphericity euler_number bounding_box centroid equivalent_ellipsoid ellipsoid_elongations surface_area_method=[Crofton (13 dirs.)] euler_connectivity=26");
Table.rename(ImageName + "_Segmented_Nuclei-morpho", "NucMeasurements");
nRNM = Table.size;
nucimageidvalue = ImageName;

// Create a loop that iterates through each nuc and adds the image name as an ID to relate it to the parent spheroid
for (i = 0; i < nRNM; i++) {
Table.set("ImageID", i, nucimageidvalue );
Table.set("Group", i, groupvalue );
}

// Extract data from the nuc results table and append it to the appropriate arrays created at the start of the script for storage
nucimageidtable = Table.getColumn("ImageID");
NucImageID = Array.concat(NucImageID,nucimageidtable);
Nucdatagrouping = Table.getColumn("Group");
Groupingnuc = Array.concat(Groupingnuc, Nucdatagrouping);
nuclabtable = Table.getColumn("Label");
NucLabel = Array.concat(NucLabel, nuclabtable);
nucvoxtable = Table.getColumn("VoxelCount");
NucVox = Array.concat(NucVox,nucvoxtable);
nucvoltable = Table.getColumn("Volume");
NucVol = Array.concat(NucVol, nucvoltable);
nucsphertable = Table.getColumn("Sphericity");
NucSpher = Array.concat(NucSpher,nucsphertable);
nucelongtable = Table.getColumn("Elli.R1/R2");
NucElong = Array.concat(NucElong,nucelongtable);


/*============================
 * WHOLE SPHEROID MEASUREMENTS
 =============================*/
selectWindow(ImageName + "_Whole_Spheroid.tif");
WholeSpheroid = getTitle();
run("Analyze Regions 3D", "voxel_count volume surface_area mean_breadth sphericity euler_number bounding_box centroid equivalent_ellipsoid ellipsoid_elongations surface_area_method=[Crofton (13 dirs.)] euler_connectivity=26");
Table.rename(ImageName + "_Whole_Spheroid-morpho", "SpheroidMeasurements");

// Extract data from the spheroid results table and append it to the appropriate arrays created at the start of the script for storage
spheroidvoxtable = Table.getColumn("VoxelCount");
WholeSpheroidVox = Array.concat(WholeSpheroidVox,spheroidvoxtable);
spheroidvoltable = Table.getColumn("Volume");
WholeSpheroidVol = Array.concat(WholeSpheroidVol,spheroidvoltable);
spheriodSphertable = Table.getColumn("Sphericity");
WholeSpheriodSpher = Array.concat(WholeSpheriodSpher,spheriodSphertable);
spheroidelongtable = Table.getColumn("Elli.R1/R2");
WholeSpheroidElong = Array.concat(WholeSpheroidElong,spheroidelongtable);


/*======================================
 * CREATE INNER AND OUTER SPHEROID ROIS
 ======================================*/
 // Inner ROI - Not percentage based thus, poor scalability: be aware!
selectWindow(ImageName + "_Whole_Spheroid.tif");
run("Duplicate...", "title=[InnerSpheroid] duplicate");
run("Make Binary", "method=Default background=Dark black");
// change iteration number here to adjust size of the inner ROI. Keep it consistent within a dataset. 
run("Options...", "iterations=70 count=1 black do=Erode stack");
saveAs("tif", output + File.separator + ImageName + "_Inner_Spheroid" + ".TIF");

// Outer ROI - dependent on inner ROI
selectWindow(ImageName + "_Whole_Spheroid.tif");
run("Duplicate...", "title=[OuterSpheroid] duplicate");
selectWindow(ImageName + "_Inner_Spheroid.tif");
run("8-bit");
setAutoThreshold("MinError dark");
setOption("BlackBackground", true);
run("Convert to Mask", "method=MinError background=Dark black");
rename("InnerSpheroid");
imageCalculator("Subtract create stack", "OuterSpheroid", "InnerSpheroid");
selectWindow("Result of OuterSpheroid");
saveAs("tif", output + File.separator + ImageName + "_Outer_Spheroid" + ".TIF");
rename("OuterROI");
selectWindow("InnerSpheroid");
rename("InnerROI");


/*=====================================
 * CREATE AND MEASURE A CYTOPLASMIC ROI
 ======================================*/
 // Make nuclei binary
selectWindow(ImageName+ "_Segmented_Nuclei.tif");
run("Duplicate...", "title=[nuclei] duplicate");
run("8-bit");
setAutoThreshold("MinError dark");
setOption("BlackBackground", true);
run("Convert to Mask", "method=MinError background=Dark black");
rename("nuclei");

// Subtract nuclei from whole spheroid to generate cytoplsamic ROI
selectWindow(ImageName + "_Whole_Spheroid.tif");
run("Duplicate...", "title=[WholeSpheroidROICyto] duplicate");
imageCalculator("Subtract create stack", "WholeSpheroidROICyto", "nuclei");
selectWindow("Result of WholeSpheroidROICyto");

//Save output
saveAs("tif", output + File.separator + ImageName + "_Spheroid_Cytoplasm" + ".TIF");

// Rename ROIs
rename("CytoROI");
selectWindow(ImageName+ "_Segmented_Nuclei.tif");
rename("NucROI");

// Measure cytoplasm
selectWindow("CytoROI");
run("Analyze Regions 3D", "voxel_count volume surface_area mean_breadth sphericity euler_number bounding_box centroid equivalent_ellipsoid ellipsoid_elongations surface_area_method=[Crofton (13 dirs.)] euler_connectivity=26");
Table.rename("CytoROI-morpho", "CytoplasmicMeasurements");

// add to the arrays 
CytoVolvar = Table.getColumn("Volume");
CytoVol = Array.concat(CytoVol, CytoVolvar);
CytoVoxvar = Table.getColumn("VoxelCount");
CytoVox = Array.concat(CytoVox,CytoVoxvar);


/*========================================
 * 3D INTENSITY QUANTIFICATION - CHANNEL 2
 =========================================*/
// Spheroid
//Use the whole spheroid masks (ROIs) to quantify the fluorescent intensity of channel 2 whole ROI
selectWindow("C2-" + ImageName);
rename("C2Image");
selectWindow(WholeSpheroid);
rename("WholeSpheroidROI");
run("Intensity Measurements 2D/3D", "input=C2Image labels=WholeSpheroidROI mean numberofvoxels");
Table.rename("C2Image-intensity-measurements", "C2WholeResults");
nRC2 = Table.size;

// Calculate the intden C2 whole ROI
for (w = 0; w < nRC2; w++) {
	whole2_mean = Table.get("Mean", w);
	whole2_vox = Table.get("NumberOfVoxels", w); 
	whole2_intden = (whole2_mean) * whole2_vox ;
	Table.set("Integrated_Density", w, whole2_intden ); 
	}
Table.update;

// Extract data from the spheroid intensity C2 results table and append it to the appropriate arrays created at the start of the script for storage
c2mean = Table.getColumn("Mean");
C2WholeMean = Array.concat(C2WholeMean,c2mean);
c2vox = Table.getColumn("NumberOfVoxels");
C2WholeVox = Array.concat(C2WholeVox,c2vox);
c2intden = Table.getColumn("Integrated_Density");
C2WholeIntDen = Array.concat(C2WholeIntDen,c2intden);
//------------------

//------------------
//Inner Spheroid ROI
run("Intensity Measurements 2D/3D", "input=C2Image labels=InnerROI mean numberofvoxels");
Table.rename("C2Image-intensity-measurements", "C2InnerResults");
nISR = Table.size;
for (i = 0; i < nISR; i++) {
	InnerC2Mean = Table.get("Mean", i);
	InnerC2Vox = Table.get("NumberOfVoxels", i);
	InnerC2IntDen = (InnerC2Mean) * InnerC2Vox ; 
	Table.set("Integrated_Density", i, InnerC2IntDen);
}
Table.update;

// Grab values and add to arrays
InnerSpheroidVoxvar = Table.getColumn("NumberOfVoxels");
C2InnerSpheroidMeanvar = Table.getColumn("Mean");
C2InnerSpheroidIntDenvar = Table.getColumn("Integrated_Density");
C2InnerSpheroidMean = Array.concat(C2InnerSpheroidMean,C2InnerSpheroidMeanvar);
C2InnerSpheroidIntDen = Array.concat(C2InnerSpheroidIntDen,C2InnerSpheroidIntDenvar);
InnerSpheroidVox = Array.concat(InnerSpheroidVox,InnerSpheroidVoxvar);
//------------------


//------------------
//Outer Spheroid ROI
run("Intensity Measurements 2D/3D", "input=C2Image labels=OuterROI mean numberofvoxels");
Table.rename("C2Image-intensity-measurements", "C2OuterResults");
nOSR = Table.size;
for (i = 0; i < nOSR; i++) {
	OuterC2Mean = Table.get("Mean", i);
	OuterC2Vox = Table.get("NumberOfVoxels", i);
	OuterC2IntDen = (OuterC2Mean) * OuterC2Vox ; 
	Table.set("Integrated_Density", i, OuterC2IntDen);
}
Table.update;

// Grab values
OuterSpheroidVoxvar = Table.getColumn("NumberOfVoxels");
C2OuterSpheroidMeanvar = Table.getColumn("Mean");
C2OuterSpheroidIntDenvar = Table.getColumn("Integrated_Density");

// add to arrays
C2OuterSpheroidIntDen = Array.concat(C2OuterSpheroidIntDen,C2OuterSpheroidIntDenvar);
C2OuterSpheroidMean = Array.concat(C2OuterSpheroidMean, C2OuterSpheroidMeanvar);
OuterSpheroidVox = Array.concat(OuterSpheroidVox,OuterSpheroidVoxvar);
//------------------


//------------------
// Cytoplasmic ROI
run("Intensity Measurements 2D/3D", "input=C2Image labels=CytoROI mean numberofvoxels");
Table.rename("C2Image-intensity-measurements", "C2CytoResults");
nCR = Table.size;
for (i = 0; i < nCR; i++) {
	CytoC2Mean = Table.get("Mean", i);
	CytoC2Vox = Table.get("NumberOfVoxels", i);
	CytoC2IntDen = (CytoC2Mean) * CytoC2Vox ;
	Table.set("Integrated_Density", i, CytoC2IntDen);
}
Table.update;

// Grab values
C2CytoMeanvar = Table.getColumn("Mean");
C2CytoIntDenvar = Table.getColumn("Integrated_Density");

// Add to arrays 
C2CytoMean = Array.concat(C2CytoMean,C2CytoMeanvar);
C2CytoIntDen = Array.concat(C2CytoIntDen,C2CytoIntDenvar);
//------------------


//------------------
// Nuclei ROI
run("Intensity Measurements 2D/3D", "input=C2Image labels=NucROI mean numberofvoxels");
Table.rename("C2Image-intensity-measurements", "C2NucResults");
nNR = Table.size;
for (i = 0; i < nNR; i++) {
	NucC2Mean = Table.get("Mean", i);
	NucC2Vox = Table.get("NumberOfVoxels", i);
	NucC2IntDen = (NucC2Mean) * NucC2Mean ;
	Table.set("Integrated_Density", i, NucC2IntDen);
}
Table.update;

// Grab values 
C2NucMeanvar = Table.getColumn("Mean");
C2NucIntDenvar = Table.getColumn("Integrated_Density");

// Add to arrays
C2NucMean = Array.concat(C2NucMean,C2NucMeanvar);
C2NucIntDen = Array.concat(C2NucIntDen,C2NucIntDenvar);
//--------------------

/*========================================
 * 3D INTENSITY QUANTIFICATION - CHANNEL 3
 =========================================*/
selectWindow("C3-" + ImageName);
rename("C3Image");

// Whole Spheroid
run("Intensity Measurements 2D/3D", "input=C3Image labels=WholeSpheroidROI mean numberofvoxels");
Table.rename("C3Image-intensity-measurements", "C3WholeResults");
nRC3 = Table.size;

// Calculate intden C3 whole ROI 
for (q = 0; q < nRC3; q++) {
	whole3_mean = Table.get("Mean", q);
	whole3_vox = Table.get("NumberOfVoxels", q);
	whole3_intden = (whole3_mean) * whole3_vox ;
	Table.set("Integrated_Density", q, whole3_intden );
	}
Table.update;

// Extract data from the spheroid intensity C3 results table and append it to the appropriate arrays created at the start of the script for storage
c3mean = Table.getColumn("Mean");
C3WholeMean = Array.concat(C3WholeMean,c3mean);
c3vox = Table.getColumn("NumberOfVoxels");
C3WholeVox = Array.concat(C3WholeVox,c3vox);
c3intden = Table.getColumn("Integrated_Density");
C3WholeIntDen = Array.concat(C3WholeIntDen,c3intden);
//----------------------------

//----------------------------
// Inner Spheroid
run("Intensity Measurements 2D/3D", "input=C3Image labels=InnerROI mean numberofvoxels");
Table.rename("C3Image-intensity-measurements", "C3InnerResults");
nISR2 = Table.size;
for (i = 0; i < nISR2; i++) {
	InnerC3Mean = Table.get("Mean", i);
	InnerC3Vox = Table.get("NumberOfVoxels", i);
	InnerC3IntDen = (InnerC3Mean) * InnerC3Vox ; 
	Table.set("Integrated_Density", i, InnerC3IntDen);
}
Table.update;

// Grab values 
C3InnerSpheroidMeanvar = Table.getColumn("Mean");
C3InnerSpheroidIntDenvar = Table.getColumn("Integrated_Density");

// Add to arrays
C3InnerSpheroidMean = Array.concat(C3InnerSpheroidMean,C3InnerSpheroidMeanvar);
C3InnerSpheroidIntDen = Array.concat(C3InnerSpheroidIntDen,C3InnerSpheroidIntDenvar);
//---------------------------


//---------------------------
// Outer Spheroid
run("Intensity Measurements 2D/3D", "input=C3Image labels=OuterROI mean numberofvoxels");
Table.rename("C3Image-intensity-measurements", "C3OuterResults");
nOSR2 = Table.size;
for (i = 0; i < nOSR2; i++) {
	OuterC3Mean = Table.get("Mean", i);
	OuterC3Vox = Table.get("NumberOfVoxels", i);
	OuterC3IntDen = (OuterC3Mean) * OuterC3Vox ; 
	Table.set("Integrated_Density", i, OuterC3IntDen);
}
Table.update;

// Grab values
C3OuterSpheroidMeanvar = Table.getColumn("Mean");
C3OuterSpheroidIntDenvar = Table.getColumn("Integrated_Density");

// add to arrays
C3OuterSpheroidIntDen = Array.concat(C3OuterSpheroidIntDen,C3OuterSpheroidIntDenvar);
C3OuterSpheroidMean = Array.concat(C3OuterSpheroidMean, C3OuterSpheroidMeanvar);
//----------------------

//---------------------
// Cytoplasm ROI
run("Intensity Measurements 2D/3D", "input=C3Image labels=CytoROI mean numberofvoxels");
Table.rename("C3Image-intensity-measurements", "C3CytoResults");
nCR3 = Table.size;
for (i = 0; i < nCR3; i++) {
	CytoC3Mean = Table.get("Mean", i);
	CytoC3Vox = Table.get("NumberOfVoxels", i);
	CytoC3IntDen = (CytoC3Mean) * CytoC3Vox ;
	Table.set("Integrated_Density", i, CytoC3IntDen);
}
Table.update;

// Grab values
C3CytoMeanvar = Table.getColumn("Mean");
C3CytoIntDenvar = Table.getColumn("Integrated_Density");

// Add to arrays 
C3CytoMean = Array.concat(C3CytoMean,C3CytoMeanvar);
C3CytoIntDen = Array.concat(C3CytoIntDen,C3CytoIntDenvar);

//---------------------

//-------------------------
// Nuclei ROI
run("Intensity Measurements 2D/3D", "input=C3Image labels=NucROI mean numberofvoxels");
Table.rename("C3Image-intensity-measurements", "C3NucResults");
nNR3 = Table.size;
for (i = 0; i < nNR3; i++) {
	NucC3Mean = Table.get("Mean", i);
	NucC3Vox = Table.get("NumberOfVoxels", i);
	NucC3IntDen = (NucC3Mean) * NucC3Mean ;
	Table.set("Integrated_Density", i, NucC3IntDen);
}
Table.update;

// Grab values 
C3NucMeanvar = Table.getColumn("Mean");
C3NucIntDenvar = Table.getColumn("Integrated_Density");

// Add to arrays
C3NucMean = Array.concat(C3NucMean,C3NucMeanvar);
C3NucIntDen = Array.concat(C3NucIntDen,C3NucIntDenvar);
//-------------------------


/*========================================
 * 3D INTENSITY QUANTIFICATION - CHANNEL 4
 =========================================*/
// Whole Spheorid
selectWindow("C4-" + ImageName);
rename("C4Image");

// Whole Spheroid
run("Intensity Measurements 2D/3D", "input=C4Image labels=WholeSpheroidROI mean numberofvoxels");
Table.rename("C4Image-intensity-measurements", "C4WholeResults");
nRC4 = Table.size;

// Calculate intden C4 whole ROI 
for (q = 0; q < nRC4; q++) {
	whole4_mean = Table.get("Mean", q);
	whole4_vox = Table.get("NumberOfVoxels", q);
	whole4_intden = (whole4_mean) * whole4_vox ;
	Table.set("Integrated_Density", q, whole4_intden );
	}
Table.update;

// Extract data from the spheroid intensity C4 results table and append it to the appropriate arrays created at the start of the script for storage
c4mean = Table.getColumn("Mean");
C4WholeMean = Array.concat(C4WholeMean,c4mean);
c4vox = Table.getColumn("NumberOfVoxels");
C4WholeVox = Array.concat(C4WholeVox,c4vox);
c4intden = Table.getColumn("Integrated_Density");
C4WholeIntDen = Array.concat(C4WholeIntDen,c4intden);
//----------------------------

//---------------------------
// Inner Spheroid
run("Intensity Measurements 2D/3D", "input=C4Image labels=InnerROI mean numberofvoxels");
Table.rename("C4Image-intensity-measurements", "C4InnerResults");
nISR4 = Table.size;
for (i = 0; i < nISR4; i++) {
	InnerC4Mean = Table.get("Mean", i);
	InnerC4Vox = Table.get("NumberOfVoxels", i);
	InnerC4IntDen = (InnerC4Mean) * InnerC4Vox ; 
	Table.set("Integrated_Density", i, InnerC4IntDen);
}
Table.update;

// Grab values 
C4InnerSpheroidMeanvar = Table.getColumn("Mean");
C4InnerSpheroidIntDenvar = Table.getColumn("Integrated_Density");

// Add to arrays
C4InnerSpheroidMean = Array.concat(C4InnerSpheroidMean,C4InnerSpheroidMeanvar);
C4InnerSpheroidIntDen = Array.concat(C4InnerSpheroidIntDen,C4InnerSpheroidIntDenvar);
//-----------------------

//-----------------------
// Outer Spheroid
run("Intensity Measurements 2D/3D", "input=C4Image labels=OuterROI mean numberofvoxels");
Table.rename("C4Image-intensity-measurements", "C4OuterResults");
nOSR4 = Table.size;
for (i = 0; i < nOSR4; i++) {
	OuterC4Mean = Table.get("Mean", i);
	OuterC4Vox = Table.get("NumberOfVoxels", i);
	OuterC4IntDen = (OuterC4Mean) * OuterC4Vox ; 
	Table.set("Integrated_Density", i, OuterC4IntDen);
}
Table.update;

// Grab values
C4OuterSpheroidMeanvar = Table.getColumn("Mean");
C4OuterSpheroidIntDenvar = Table.getColumn("Integrated_Density");

// add to arrays
C4OuterSpheroidIntDen = Array.concat(C4OuterSpheroidIntDen,C4OuterSpheroidIntDenvar);
C4OuterSpheroidMean = Array.concat(C4OuterSpheroidMean, C4OuterSpheroidMeanvar);
//--------------------------------

//--------------------------------
// Cytoplasm
run("Intensity Measurements 2D/3D", "input=C4Image labels=CytoROI mean numberofvoxels");
Table.rename("C4Image-intensity-measurements", "C4CytoResults");
nCR4 = Table.size;
for (i = 0; i < nCR4; i++) {
	CytoC4Mean = Table.get("Mean", i);
	CytoC4Vox = Table.get("NumberOfVoxels", i);
	CytoC4IntDen = (CytoC4Mean) * CytoC4Vox ;
	Table.set("Integrated_Density", i, CytoC4IntDen);
}
Table.update;

// Grab values
C4CytoMeanvar = Table.getColumn("Mean");
C4CytoIntDenvar = Table.getColumn("Integrated_Density");

// Add to arrays 
C4CytoMean = Array.concat(C4CytoMean,C4CytoMeanvar);
C4CytoIntDen = Array.concat(C4CytoIntDen,C4CytoIntDenvar);
//------------------------------

//------------------------------
// Nuclei
run("Intensity Measurements 2D/3D", "input=C4Image labels=NucROI mean numberofvoxels");
Table.rename("C4Image-intensity-measurements", "C4NucResults");
nNR4 = Table.size;
for (i = 0; i < nNR4; i++) {
	NucC4Mean = Table.get("Mean", i);
	NucC4Vox = Table.get("NumberOfVoxels", i);
	NucC4IntDen = (NucC4Mean) * NucC4Mean ;
	Table.set("Integrated_Density", i, NucC4IntDen);
}
Table.update;

// Grab values 
C4NucMeanvar = Table.getColumn("Mean");
C4NucIntDenvar = Table.getColumn("Integrated_Density");

// Add to arrays
C4NucMean = Array.concat(C4NucMean,C4NucMeanvar);
C4NucIntDen = Array.concat(C4NucIntDen,C4NucIntDenvar);
//--------------------------------------


/*========================================
 * SOME CLEANING AND THEN FINISH UP
 =========================================*/
// Clear the GPU 
Ext.CLIJ2_clear();

//Finish, close all windows and report compute time
print((getTime() - imagestart)/1000);
print("Image "+ Sheet + " Complete!");
run("Close All");
selectWindow("C2WholeResults");
run("Close");
selectWindow("C2InnerResults");
run("Close");
selectWindow("C2OuterResults");
run("Close");
selectWindow("C2CytoResults");
run("Close");
selectWindow("C2NucResults");
run("Close");
selectWindow("C3WholeResults");
run("Close");
selectWindow("C3InnerResults");
run("Close");
selectWindow("C3OuterResults");
run("Close");
selectWindow("C3CytoResults");
run("Close");
selectWindow("C3NucResults");
run("Close");
selectWindow("C4WholeResults");
run("Close");
selectWindow("C4InnerResults");
run("Close");
selectWindow("C4OuterResults");
run("Close");
selectWindow("C4CytoResults");
run("Close");
selectWindow("C4NucResults");
run("Close");
selectWindow("NucMeasurements");
run("Close");
selectWindow("SpheroidMeasurements");
run("Close"); 
selectWindow("CytoplasmicMeasurements");
run("Close"); 
}
// Single Image Loop Ends Here

// Once all images in the batch have been processed - we now want to create a result table for the nuc and spheroid data and populate it with the data storage in the arrays
// Create a spheroid results table and write arrays as columns
Table.create("Results");
Table.setColumn("Group", datagrouping);
Table.setColumn("ImageID_Spheroid", originalimagename); 
Table.setColumn("Spheroid_Vox_Count",WholeSpheroidVox);
Table.setColumn("Speroid_Volume", WholeSpheroidVol);
Table.setColumn("Spheroid_Sphericity", WholeSpheriodSpher);
Table.setColumn("Spheroid_Elongation", WholeSpheroidElong);
Table.setColumn(channel2 + "_Spheroid_Mean", C2WholeMean);
Table.setColumn(channel2 + "_Spheroid_Vox_Count", C2WholeVox);
Table.setColumn(channel2 + "_Spheroid_IntDen", C2WholeIntDen);
Table.setColumn(channel3 + "_Spheroid_Mean", C3WholeMean);
Table.setColumn(channel3 + "_Spheroid_Vox_Count", C3WholeVox);
Table.setColumn(channel3 + "_Spheroid_IntDen", C3WholeIntDen);
Table.setColumn(channel4 + "_Spheroid_Mean", C4WholeMean);
Table.setColumn(channel4 + "_Spheroid_IntDen", C4WholeIntDen);
Table.setColumn("Cytoplasm_Vox_Count", CytoVox);
Table.setColumn("Cytoplasm_Volume", CytoVol);
Table.setColumn(channel2 + "_Cytoplasm_Mean", C2CytoMean);
Table.setColumn(channel2 + "_Cytoplasm_IntDen", C2CytoIntDen);
Table.setColumn(channel3 + "_Cytplasm_Mean", C3CytoMean);
Table.setColumn(channel3 + "_Cytoplasm_IntDen", C3CytoIntDen);
Table.setColumn(channel4 + "_Cytoplasm_Mean", C4CytoMean);
Table.setColumn(channel4 + "_Cytoplasm_IntDen", C4CytoIntDen);
Table.setColumn("Inner_Spheroid_Vox_Count", InnerSpheroidVox);
Table.setColumn("Outer_Spheroid_Vox_Count", OuterSpheroidVox);
Table.setColumn(channel2 +"_Inner_Spheroid_Mean", C2InnerSpheroidMean);
Table.setColumn(channel2 + "_Inner_Spheroid_IntDen", C2InnerSpheroidIntDen);
Table.setColumn(channel3 + "_Inner_Spheroid_Mean", C3InnerSpheroidMean);
Table.setColumn(channel3 + "_Inner_Spheroid_IntDen", C3InnerSpheroidIntDen);
Table.setColumn(channel4 + "_Inner_Spheroid_Mean", C4InnerSpheroidMean);
Table.setColumn(channel4 + "_Inner_Spheroid_IntDen", C4InnerSpheroidIntDen);
Table.setColumn(channel2 + "_Outer_Spheroid_IntDen", C2OuterSpheroidIntDen);
Table.setColumn(channel2 + "_Outer_Spheroid_Mean", C2OuterSpheroidMean);
Table.setColumn(channel3 + "_Outer_Spheroid_Mean", C3OuterSpheroidMean);
Table.setColumn(channel4 + "_Outer_Spheroid_Mean", C4OuterSpheroidMean);
Table.setColumn(channel4 + "_Outer_Spheroid_IntDen", C4OuterSpheroidIntDen);
Table.update;

// Save the spheroid results table to the output directory
run("Read and Write Excel", "file=[" + output + "/" + "Master_Results_Spheroids.xlsx] sheet=["+groupvalue+"]");

// Could close it here, but renamed for debugging purposes
Table.rename("Results", "Sphr_Results");

// Create a nuclei results table and write arrays as columns
Table.create("Results");
Table.setColumn("ImageID_Nuclei", NucImageID);
Table.setColumn("Group", Groupingnuc);
Table.setColumn("Nuclear_Label", NucLabel);
Table.setColumn("Nuclear_Vox_Count", NucVox);
Table.setColumn("Nuclear_Volume", NucVol);
Table.setColumn("Nuclear_Sphericity", NucSpher);
Table.setColumn("Nuclear_Elongation", NucElong);
Table.setColumn(channel2 + "_Nuclear_Mean", C2NucMean);
Table.setColumn(channel2 + "_Nuclear_IntDen", C2NucIntDen);
Table.setColumn(channel3 + "_Nuclear_Mean", C3NucMean);
Table.setColumn(channel3 + "_Nuclear_IntDen", C3NucIntDen);
Table.setColumn(channel4 + "_Nuclear_Mean", C4NucMean);
Table.setColumn(channel4 + "_Nuclear_IntDen", C4NucIntDen);
Table.update;

// Save the nuclei results table to the output directory
run("Read and Write Excel", "file=[" + output + "/" + "Master_Results_Nuclei.xlsx] sheet=["+groupvalue+"]");

// Finish up
selectWindow("Results");
run("Close");
selectWindow("Sphr_Results");
run("Close"); 
print((getTime() - verystart)/1000); 
print("Batch Processing Complete!");
print(lengthOf(FileList) + " Images Processed! :D");