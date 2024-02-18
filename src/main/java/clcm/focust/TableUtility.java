package clcm.focust;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clcm.focust.parameters.ParameterCollection;
import ij.IJ;
import ij.ImagePlus;
import ij.macro.Variable;
import ij.measure.ResultsTable;
import inra.ijpb.measure.IntensityMeasures;
import inra.ijpb.measure.ResultsBuilder;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij2.CLIJ2;
import util.FindConnectedRegions.Results;

/**
 * This helper class contains helper methods for calculating intensities and saving results tables. 
 */

public class TableUtility {

	
	public static ResultsTable intDen(ResultsTable volume, ResultsTable mean) {

		double[] vol = volume.getColumnAsDoubles(0);
		double[] intMean = mean.getColumnAsDoubles(0);

		Variable[] intDen = new Variable[vol.length];

		for (int i = 0; i < vol.length; i++) {
			intDen[i] = new Variable(vol[i] * intMean[i]);
		}

		mean.setColumn("IntDen", intDen);

		return mean;
	}



	/**
	 * This method calculates the intensity of the input image for each object in the label image.
	 * IntensityMeasures and ResultsBuilder instanced from MorphoLibJ.	
	 * 
	 * @param input 
	 * 			A raw image containing intensity data. Can be passed from the current image's channels[]. 
	 * @param label 
	 * 			A labelled (segmented) image.
	 * 
	 * @return A results table containing label, mean_intensity, volume, max and IntDen columns.
	 */
	public static ResultsTable processIntensity(ImagePlus input, ImagePlus label) {

		// constructs default table and appends any columns in supplied tables that don't yet exist.
		ResultsBuilder rb = new ResultsBuilder();
		final IntensityMeasures im = new IntensityMeasures(input, label);

		// get vol and mean separately so intden can be calculated without needing to compute twice.
		ResultsTable rtVol = im.getVolume();
		ResultsTable rtMean = im.getMean();
		rb.addResult(rtVol);
		rb.addResult(rtMean);
		rb.addResult(intDen(rtVol, rtMean));
		rb.addResult(im.getSumOfVoxels());
		rb.addResult(im.getMax());
		rb.addResult(im.getMin());
		rb.addResult(im.getMedian());
		rb.addResult(im.getMode());
		rb.addResult(im.getSkewness());
		rb.addResult(im.getKurtosis());
		rb.addResult(im.getStdDev());
		ResultsTable table = rb.getResultsTable();
		return table; 
	}

	
	
	
	public static void removeColumns(ResultsTable rt, String[] colsToRemove) {

		for (String col : colsToRemove) {
			rt.deleteColumn(col);
		}

	}

	
	
	public static void saveTable(ResultsTable table, String dir, String name) {
		try { 
			table.saveAs(dir + name);
		} catch (IOException e) {
			e.printStackTrace();
			IJ.log("Cannot save results table: " + dir + name);
		}
	}

	/*
	 * combine the label-matched results tables together. label/id col must match where it needs to. 
	 */
	public ResultsTable compileSingleCellTables(ResultsTable primary, ResultsTable secondary, ResultsTable tertiary) {
		
		ResultsBuilder rb = new ResultsBuilder();
		
		rb.addResult(primary);
		rb.addResult(secondary);
		rb.addResult(tertiary);
		
		ResultsTable rt = rb.getResultsTable();
		
		return rt;
	}
/**
	 * Collects the columns from RT and puts them into a Map of columns. 
	 * @param rt
	 * @param columnDataMap
	 */
	public static void collectColumns(ResultsTable rt, Map<String, List<Variable>> columnDataMap) {		

		String[] columnNames = rt.getHeadings();
		
		for (String name : columnNames) {
			
			// returns the list for that column header, or makes a new one if it doesn't exist yet.
			List<Variable> columnData = columnDataMap.getOrDefault(name, new ArrayList<>());
			
			// grab the entire column
			Variable[] columnVariables = rt.getColumnAsVariables(name);
			
			// add the array to the list
			columnData.addAll(Arrays.asList(columnVariables));
			
			// update the map
			columnDataMap.put(name, columnData);
		}
	}
	
	
	/**
	 * Calculate the intensity of every channel in the input image (channels) within
	 * each label of every object type (segmentedObjects).
	 * 
	 * @param segmentedObjects
	 * @param channels
	 * @return
	 */
	public static Map<ImagePlus, ResultsTable> compileIntensityResults(ArrayList<ImagePlus> segmentedObjects, ImagePlus[] channels, ParameterCollection parameters){

		// A map for intensity cals
		Map<ImagePlus, ResultsTable> intensityTables = new HashMap<>();
			
		/*
		 * calculate the intensity of every channel in the input image (channels) 
		 */
		for (int j = 0; j < segmentedObjects.size(); j++) {
				
			ResultsTable rt = new ResultsTable();
				
			for (int k = 0; k < channels.length; k++) {
				
				ResultsTable temp = TableUtility.processIntensity(channels[k], segmentedObjects.get(j));
				String[] headers = temp.getHeadings();
				
				// if channel 1
				if ((k + 1) == 1) {
					String c1Name = null;
					if (!parameters.getNameChannel1().isEmpty()) {
						c1Name = parameters.getNameChannel1();
					} else {
						c1Name = ("C" + (k + 1)).toString();
					}
					for (String head : headers) {
						rt.setColumn(c1Name, temp.getColumnAsVariables(head));
					}
				}
				
				// if channel 2
				if ((k + 1) == 2) {
					String c2Name = null;
					if (!parameters.getNameChannel2().isEmpty()) {
						c2Name = parameters.getNameChannel2();
					} else {
						c2Name = ("C" + (k + 1)).toString();
					}
					for (String head : headers) {
						rt.setColumn(c2Name, temp.getColumnAsVariables(head));
					}
				}
				
				// if channel 3
				if ((k + 1) == 3) {
					String c3Name = null;
					if (!parameters.getNameChannel3().isEmpty()) {
						c3Name = parameters.getNameChannel3();
					} else {
						c3Name = ("C" + (k + 1)).toString();
					}
					for (String head : headers) {
						rt.setColumn(c3Name, temp.getColumnAsVariables(head));
					}
				}
				
				// if channel 4
				if ((k + 1) == 4) {
					String c4Name = null;
					if (!parameters.getNameChannel4().isEmpty()) {
						c4Name = parameters.getNameChannel4();
					} else {
						c4Name = ("C" + (k + 1)).toString();
					}
					for (String head : headers) {
						rt.setColumn(c4Name, temp.getColumnAsVariables(head));
					}
				}
				
			}
			
			intensityTables.put(segmentedObjects.get(j), rt);
			
		}
		
		return intensityTables;
		
	}
	
	
	public static ResultsTable compileBandIntensities(List<ClearCLBuffer> bands, ImagePlus[] channels) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		List<ResultsTable> rtList = new ArrayList<>();
	
		for (int i = 0; i < bands.size(); i++) {
			
			//TODO:
			System.out.println("band: " + i + " of " + bands.size());
			
			ResultsTable rt = new ResultsTable();
				
			for (int j = 0; j < channels.length; j++) {
				
				//TODO: 
				System.out.println("Channel: " + j + "of " + channels.length);
				
				
				
				ResultsTable temp = TableUtility.processIntensity(channels[j], clij2.pull(bands.get(i)));
				
				// get col headers without Label
				List<String> headers = new ArrayList<>(Arrays.asList(temp.getHeadings()).subList(1, temp.getHeadings().length));
				
				
				// get the labelID values + remove them from the current rt
				ResultsTable rtLab = new ResultsTable();
				rtLab.setColumn("Label", temp.getColumnAsVariables("Label"));
				rtList.add(rtLab);
				
				
				temp.show("Band " + (i+1) + " Results" );
				
				// channel 1
				if ((j + 1) == 1) {
					String c1Name = ("band" + (i + 1) + "_c1").toString();
					
					for (String head : headers) {
						String headerName = (c1Name + "_" + head).toString();
						rt.setColumn(headerName, temp.getColumnAsVariables(head));
					}
				}
				
				
				// channel 2
				if ((j + 1) == 2) {
					String c2Name = ("band" + (i + 1) + "_c2").toString();
					
					for (String head : headers) {
						String headerName = (c2Name + "_" + head).toString();
						rt.setColumn(headerName, temp.getColumnAsVariables(head));
					}
				}
				
				// channel 3
				if ((j + 1) == 3) {
					String c3Name = ("band" + (i + 1) + "_c3").toString();
			
					for (String head : headers) {
						String headerName = (c3Name + "_" + head).toString();
						rt.setColumn(headerName, temp.getColumnAsVariables(head));
					}
				}
				
				// channel 4
				if ((j + 1) == 4) {
					String c4Name = ("band" + (i + 1) + "_c4").toString();
					
					for (String head : headers) {
						String headerName = (c4Name + "_" + head).toString();
						rt.setColumn(headerName, temp.getColumnAsVariables(head));
					}
				}
				
			}
			
			rt.show("FINAL BAND " + (i+1) + " TABLE");
			
			rtList.add(rt);
			
		}
		
		TableUtility tu = new TableUtility();
		ResultsTable output = tu.compileAllResults(rtList);
		
		return output;
	}
	
	
	
	
	public static ResultsTable extractGroupAndTitle(ResultsTable rt, ParameterCollection parameters, String imgName) {
		
		ResultsTable result = new ResultsTable();
		
		//rt.show("Results");
		String headers = rt.getColumnHeadings();
		System.out.println("Headers = " + headers);
		
		//System.out.println(rt);
		
		if(parameters.getGroupingInfo().isEmpty()) {
			for (int i = 0; i < rt.size(); i++) {
				//result.setValue("Label", i, rt.getValue("Label", i));
				result.setValue("ImageID", i, imgName);
			}
		} else {
			for (int i = 0; i < rt.size(); i++) {
				//result.setValue("Label", i, rt.getValue("Label", i)); 
				result.setValue("ImageID", i, imgName);
				result.setValue("Group", i, parameters.getGroupingInfo());
			}
		}
		
		result.setColumn("Label", rt.getColumnAsVariables("Label"));
		
		result.show("Final Table");
		return result;
	}
	
	
	
	
	
	
	
	
	// TODO: Method for running analysis on all objects if present and storing results tables
	public ResultsTable compileAllResults(List<ResultsTable> list){
		
		ResultsBuilder rb = new ResultsBuilder();
		
		for (ResultsTable rt : list) {
			rb.addResult(rt);
		}
	
	
		return rb.getResultsTable();
	}
	
	
	
}