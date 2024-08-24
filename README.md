# FOCUST
The **F**luorescent **O**bject and **C**ell gp**U**-accelerated **S**egmentation **T**oolbox for FIJI/ImageJ. 

This plugin allows the user to segment and analyze 3D fluorescent image data in ways we hope are useful.
FOCUST also includes an optimization mode, to help users new to ImageJ/FIJI or image anaylsis more broadly, configure the parameters for segmenting 3D fluorescent datasets. 

## Installation
1. Download and install [FIJI](https://imagej.net/software/fiji/downloads)
2. In the Toolbar: Help > Update
3. Click manage update sites
4. Tick the following options:
   - FOCUST
   - CLIJ
   - CLIJ2
   - CLIJx-Assistant
   - IJPB-Plugins

For documentation please [click here to visit the website!](https://sebastianamos.github.io/FOCUST-Plugin-Site/)

The goal: To provide a user-friendly solution for segmenting and relating 3D datasets acquired by fluorescence microscopy in ways that reflect the biological sample. FOCUST provides an interactive optimization mode to configure segmentation parameters and numerous analysis modes to relate and manage data in a biologically meaningful way.

## Analysis
FOCUST centers around object types (primary, secondary and teritary) representing different components of a biological sample. 

Currently available analysis modes are:
- None (Execute segmentation only)
- Basic (Analyze segmented objects - no object relationships)
- Spheroid (Nuclei inside a whole spheroid)
- Single Cell (Nuclei inside a single cell)
- Speckle (Speckles (a.k.a. subnuclear condensates) inside a nucleus)

## Help
For assistance with using FOCUST, please see the website - YouTube demo coming soon!
To lodge a query or ask a question, please use the [forum](https://forum.image.sc/) and tag me @SebastianAmos. 
Be sure to search for relevant topics before creating a new one.
You can also create a GitHub issue above. 

I am always open to implementing new features and functionality when I can find the time - so if you have an interesting use-case or a cool idea, let me know on the forum or of Github. 

## Dependencies
FOCUST depends on the following projects:

- [CLIJ2](https://clij.github.io/)
- [MorpholibJ](https://ijpb.github.io/MorphoLibJ/)
- [Skeletonize3D](https://imagej.net/plugins/skeletonize3d)
- [AnalyzeSkeleton3D](https://imagej.net/plugins/analyze-skeleton/)
- [Google GSON](https://github.com/google/gson)

We thank the original authors for their contributions.
