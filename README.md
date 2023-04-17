# FOCUST-Plugin
**F**luorescent **O**bject and **C**ell gp**U**-accelerated **S**egmentation **T**oolbox for ImageJ/Fiji. 
This plugin allows the user to analyse spheroid, single cell or speckle datasets in ways we hope are useful. 
FOCUST also includes an optimization mode, to help users new to ImageJ/FIJI or image anaylsis more broadly, configure the parameters for segmenting 3D fluorescent datasets. 

Under the hood, FOCUST depends on the outstanding [CLIJ2](https://github.com/clij/clij2) and [MorpholibJ](https://github.com/ijpb/MorphoLibJ) projects. If you use this plugin, please be sure to cite their work.

Data generated in the FOCUST plugin will output in a minimal number of .csv files (excel sheet) in tidy format. This means that data can be qickly and easily filtered, graphed or imported directly into data workflows in software such as R-Studio.

## Installation
To install and use FOCUST in FIJI, please use the update site: Help > Update > Manage Update Sites > __Tick the box next to__ FOCSUT > Close > Apply Changes. Restart ImageJ/FIJI and you will now find FOCUST in the Plugins sub-menu. 

## Spheroid Analysis
FOCUST includes a spheroid mode to analyse densely-packed nuclei within a spheroid or organoid. For this mode, datasets must contain a stain for the nucleus and a stain that represents the spheroid/organoid as a whole structure (such as a memebrane or cytoskeletal stain).
<ins> The spheroid analysis mode will: </ins>
- Segment the nuclei and the spheroid as a whole.
- Quantify the morphology of nuclear and spheroid objects (including volume, voxel count, elongation and sphericity). 
- Generate "core" and "periphery" regions of interest.
- Quantify the intensity of fluorescent stains in channel 2, 3 and 4 (if enabled) in the nuclei, whole spheroid, core and periphery.

## Single Cell Analysis

For this mode, datasets must contain a stain that represents the nucleus and a stain that represents each cell more broadly (such as a membrane or cytoskeletal dye). 

<ins> The single cell analysis mode will: </ins>
- Segment the nuclei and cell bodies and generate a cytoplasmic region of interest. 
- Relate each nucleus object to the cell body object it sits within.
- Generate morphological measurements on the nuclei, cell and cytoplasm objects.
- Quantify the intensity of stains in channels 2, 3 and 4 (if present) within the segmented objects.
- Generate intensity ratios of stains in channels 2, 3 and 4 (if present) between the nucleus and the cytoplasm.


## Speckle Analysis

For this mode, datasets must contain a stain that represents the nucleus and a stain that represents up to 2 (two) sub-nuclear condensates, or speckles.

<ins> The speckle analysis mode will: </ins>
- Segment the nuclei and speckles as defined by the user.
- Count the number of speckles per nucleus.
- Generate morphological measurements of the nuclei and speckle objects.
- Generate colocalisation data on two different types of speckles (if present).
- Relate each speckle to it's parent nucleus.

## Help
For assistance with using FOCUST, please see the manual or YouTube video for a demonstration.
To lodge a query or ask a question, please use the [forum](https://forum.image.sc/). Be sure to search for relevant topics before creating a new one.

I am alway open to implementing new features and functionality when I can find the time - so if you have an interesting use-case or a cool idea, let me know on the forum or of Github. 


