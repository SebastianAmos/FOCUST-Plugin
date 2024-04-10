package clcm.focust.mode;

import java.util.Optional;

import clcm.focust.parameters.ParameterCollection;
import ij.ImagePlus;
import ij.measure.ResultsTable;
import clcm.focust.utility.ManageDuplicates;
import clcm.focust.utility.RelabelledObjects;
import clcm.focust.utility.TableUtility;

public class ModeSingleCell implements Mode {


	@Override
	public void run(ParameterCollection parameters, CompiledImageData imgData, String imgName) {

		/* TODO:
		 * Manage duplicate labels
		 * 
		 * New table, add row data for nuc, cell and cyto where labelIDs match.
		 * 
		 */

		/*
		 * MAP DUPLICATE PRIMRAY OBJECTS
		 */
		
		ManageDuplicates md = new ManageDuplicates();

		RelabelledObjects primaryRelabelled = md.run(imgData.images.getSecondary(), imgData.images.getPrimary());
		 
		/***** For relabelling tertiary, not required.
		 * imgData.images.getTertiary().ifPresent(t -> { RelabelledObjects
		 * tertiaryRelabelled = md.run(imgData.images.getSecondary(), t); });
		 */
		 
		// TODO: TESTING
		
		
		
		/*
		 * MERGE THE DATA BY LABEL 
		 */
		
		ResultsTable rt = TableUtility.matchLabelledResults(parameters, imgData);
		
		
				
				
				
				
				
			}
			
			
			
		}
		

