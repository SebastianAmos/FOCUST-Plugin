# FOCUST-Plugin
**F**luorescent **O**bject and **C**ell gp**U**-accelerated **S**egmentation **T**oolbox for ImageJ/Fiji. 
This plugin allows the user to analyse spheroid, single cell or speckle datasets in ways we hope are useful. 
FOCUST also includes an optimization mode, to help users new to ImageJ/FIJI or image anaylsis more broadly, configure the parameters for segmenting 3D fluorescent datasets. 

Under the hood, FOCUST is dependent on the outstanding [CLIJ2](https://github.com/clij/clij2) and [MorpholibJ](https://github.com/ijpb/MorphoLibJ) projects. If you use this plugin, please be sure to cite their work. 

The ouputs from FOCUST are all in a single .csv file (excel sheet) in tidy format. This means that data can be qickly and easily graphed or imported directly into data workflows in software such as R-Studio.

## Spheroid Analysis
FOCUST includes a spheroid mode to analyse densely-packed nuclei within a spheroid or organoid. For this mode, datasets must contain a stain for the nucleus and a stain that represents the spheroid/organoid as a whole structure (such as a memebrane or cytoskeletal stain).
<ins> The spheroid analysis mode will: </ins>
- Segment the nuclei and the spheroid as a whole.
- Quantify the morphology of nuclear and spheroidal objects (including volume, voxel count, elongation etc.). 
- Generate "core" and "periphery" regions of interest.
- Quantify the intensity of fluorescent stains in channel 2, 3 and 4 (if enabled) in the nuclei, whole spheroid, core and periphery.

## Single Cell Analysis

For this mode, datasets must contain a stain that represents the nucleus and a stain that represents each cell more broadly (such as a membrane or cytoskeletal dye). 

<ins> The single cell analysis mode will: </ins>
- Segment the nuclei and cell body
- 


## Speckle Analysis

For this mode, datasets must contain a stain that represents the nucleus and a stain that represents up to 2 (two) sub-nuclear condensates, or speckles. 

<ins> The speckle analysis mode will: </ins>
- Segment the nuclei and speckles as defined by the user.
- Count the number of speckles per nucleus 
- Quantify the 






