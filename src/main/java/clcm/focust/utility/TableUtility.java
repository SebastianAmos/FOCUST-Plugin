package clcm.focust.utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import clcm.focust.mode.CompiledImageData;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.segmentation.skeleton.SkeletonResultsHolder;
import ij.IJ;
import ij.ImagePlus;
import ij.macro.Variable;
import ij.measure.Calibration;
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
				List<String> headers = new ArrayList<>(Arrays.asList(temp.getHeadings()));
				headers.remove(0);
				
				// if channel 1
				if ((k + 1) == 1) {
					String c1Name = null;
					if (!parameters.getNameChannel1().isEmpty()) {
						c1Name = parameters.getNameChannel1();
					} else {
						c1Name = ("C" + (k + 1)).toString();
					}
					for (String head : headers) {
						String newC1Head = c1Name + "." + head;
						rt.setColumn(newC1Head, temp.getColumnAsVariables(head));
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
						String newC2Head = c2Name + "." + head;
						rt.setColumn(newC2Head, temp.getColumnAsVariables(head));
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
						String newC3Head = c3Name + "." + head;
						rt.setColumn(newC3Head, temp.getColumnAsVariables(head));
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
						String newC4Head = c4Name + "." + head;
						rt.setColumn(newC4Head, temp.getColumnAsVariables(head));
					}
				}
				
			}
			
			intensityTables.put(segmentedObjects.get(j), rt);
			
		}
		
		return intensityTables;
		
	}
	
	
	public static ResultsTable compileBandIntensities(List<ClearCLBuffer> bands, ImagePlus[] channels, Calibration cal) {
		
		CLIJ2 clij2 = CLIJ2.getInstance();
		
		List<ResultsTable> rtList = new ArrayList<>();
		
		//for (int i = 0; i < bands.size(); i++) {
		
		int count = 1;
		
		for (ClearCLBuffer band : bands) {
			
		
			//TODO:
			System.out.println("band: " + count + " of " + bands.size());
			
			ResultsTable rt = new ResultsTable();
				
			for (int j = 0; j < channels.length; j++) {
				
				//TODO: 
				System.out.println("Channel: " + j + " of " + channels.length);
				
				// pull buffer safely
				//ClearCLBuffer b = clij2.create(band);
				//ImagePlus bPull = clij2.pull(b);
				//bPull.duplicate().show(); 
				
				
				//TODO
				/*
				 * I think clij2 is returning null objects once 
				 */
				
				
				ClearCLBuffer copy = clij2.create(band);
				clij2.copy(band, copy);
				ImagePlus imp = clij2.pull(copy);
				imp.setCalibration(cal);
				copy.close();
				
				ResultsTable temp = TableUtility.processIntensity(channels[j], imp);
				
				// get col headers without Label
				List<String> headers = new ArrayList<>(Arrays.asList(temp.getHeadings()).subList(1, temp.getHeadings().length));
				
				
				// get the labelID values + remove them from the current rt
				ResultsTable rtLab = new ResultsTable();
				rtLab.setColumn("Label", temp.getColumnAsVariables("Label"));
				rtList.add(rtLab);
				
				
				temp.show("Band " + count + " Results" );
				
				// channel 1
				if ((j + 1) == 1) {
					String c1Name = ("band" + count + ".c1").toString();
					
					for (String head : headers) {
						String headerName = (c1Name + "." + head).toString();
						rt.setColumn(headerName, temp.getColumnAsVariables(head));
					}
				}
				
				
				// channel 2
				if ((j + 1) == 2) {
					String c2Name = ("band" + count + ".c2").toString();
					
					for (String head : headers) {
						String headerName = (c2Name + "." + head).toString();
						rt.setColumn(headerName, temp.getColumnAsVariables(head));
					}
				}
				
				// channel 3
				if ((j + 1) == 3) {
					String c3Name = ("band" + count + ".c3").toString();
			
					for (String head : headers) {
						String headerName = (c3Name + "." + head).toString();
						rt.setColumn(headerName, temp.getColumnAsVariables(head));
					}
				}
				
				// channel 4
				if ((j + 1) == 4) {
					String c4Name = ("band" + count + ".c4").toString();
					
					for (String head : headers) {
						String headerName = (c4Name + "." + head).toString();
						rt.setColumn(headerName, temp.getColumnAsVariables(head));
					}
				}
				
			}
			
			rt.show("FINAL BAND " + count + " TABLE");
			
			rtList.add(rt);
			
			count++;
		} // This one
		
		//TableUtility tu = new TableUtility();
		ResultsTable output = TableUtility.compileAllResults(rtList);
		
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

		return result;
	}
	
	
	
	public static ResultsTable compileAllResults(List<ResultsTable> list){
		ResultsBuilder rb = new ResultsBuilder();
		for (ResultsTable rt : list) {
			rb.addResult(rt);
		}
		return rb.getResultsTable();
	}
	
	
	
	public static ResultsTable compileTables(List<ResultsTable> list) {
		ResultsTable rt = new ResultsTable();
		for (ResultsTable table : list) {
			List<String> headers = new ArrayList<>(Arrays.asList(table.getHeadings()));
			for (String head : headers) {
				if (!rt.columnExists(head)) {
					rt.setColumn(head, table.getColumnAsVariables(head));
				}
			}
		}
		return rt;
	}
	
	
	
	// Add only matching skeletonIDs to a compiled table > write na if 0.
	
	public static ResultsTable matchAndAddSkeletons1(ResultsTable data, SkeletonResultsHolder skel) {
		
		ResultsTable output = new ResultsTable();
		
		ResultsTable standard = skel.getStandard();
		List<String> skeletonHeaders = new ArrayList<>(Arrays.asList(standard.getHeadings()));
		
		ResultsTable matched = skel.getLabelMatched();
		
		for (int i = 0; i < data.size(); i++) {
			
			double objectLabel = data.getValue("Label", i);
			
			for (int j = 0; j < matched.size(); j++) {
				
				if(objectLabel == matched.getValue("Label", j)) {
					
					for (String head : skeletonHeaders) {
						
						data.addValue(head, standard.getValue(head, i));
						
						
						
						
					}
					
				}
				
			}
		}
		
		
		return null;
	}
	
	
	
	/**
	 * Match skeletons to labels objects they typically don't adopt the same labelling and some shapes may skeletonize incompletely. 
	 * This way users have visibility to shared skeletons and data may still be analysed in linked and meaningful ways.
	 * 
	 * @param data
	 * @param skel
	 * @param objectHeaderName
	 * @return
	 */
	public static ResultsTable matchAndAddSkeletons(ResultsTable data, SkeletonResultsHolder skel, String objectHeaderName) { 
		
		ResultsTable standard = skel.getStandard();
		ResultsTable matched = skel.getLabelMatched();
		
		Variable[] matchedLabelsVar = matched.getColumnAsVariables("Label");
		
		// convert from variable to int - not required.
		int[] matchedLabels = new int[matchedLabelsVar.length];
		for (int i = 0; i < matchedLabelsVar.length; i++) {
			matchedLabels[i] = (int) matchedLabelsVar[i].getValue();
		}
		
		Variable[] dataLabelsVar = data.getColumnAsVariables("Label");
		
		int[] dataLabels = new int[dataLabelsVar.length];
		for (int i = 0; i < dataLabelsVar.length; i++) {
			dataLabels[i] = (int) dataLabelsVar[i].getValue();
		}
	
		for (int i = 0; i < data.size(); i++) {
			
			// find max for corresponding matched label
			int skelID = 0;
			
			for (int j = 0; j < matched.size(); j++) {
				if ((int) matchedLabels[j] == dataLabels[i]) {
					skelID = (int) matched.getValue("Max", j);
					break;
				}
			}
			
			int rowIndex = -1;
			for (int j = 0; j < standard.size(); j++) {
				if ((int) standard.getValue(objectHeaderName + ".Skeleton.# Skeleton", j) == skelID) {
					rowIndex = j;
					break;
				}
			}
			
			if (rowIndex != - 1) {
				for (int j = 0; j < standard.getHeadings().length; j++) {
					String header = standard.getColumnHeading(j);
					
					if (j != 0) {
						data.setValue(header, i, standard.getValueAsDouble(j, rowIndex));
					} 
				} 
				
			} else {
				
				for (int j = 0; j < standard.getHeadings().length; j++) {
					String header = standard.getColumnHeading(j);
					data.setValue(header, i, "NA");	
				}
			}
		}
		
		return data;
	}
	
	
	
	// TODO: Method to match rows between tables where Label is the same, and append all columns that aren't common.

	public static ResultsTable matchLabelledResults(ParameterCollection parameters, CompiledImageData imgData) {

		ResultsTable rt = new ResultsTable();

		for (int i = 0; i < imgData.getSecondary().size(); i++) {

			int priIndex = -1;
			final int currentIndex = i;
			final int currentLabel = (int) imgData.getSecondary().getValue("Label", i);

			priIndex = findMatchingRow(imgData.getPrimary(), "Label", currentLabel);

			imgData.getImages().getTertiary().ifPresent(t -> {
				int terIndex = findMatchingRow(imgData.getTertiary(), "Label", currentLabel);
			});

			// Match found, append all the data from available tables excluding Label, ImageID and Group.
			if (priIndex >= 0) {
				rt.addRow();
				rt.addValue("ImageID", imgData.getSecondary().getStringValue("ImageID", i));
				
				if (!parameters.getGroupingInfo().isEmpty()) {
					rt.addValue("Group", imgData.getSecondary().getStringValue("Group", i));	
				}
				
				rt.addValue("Label", currentLabel);
				
				for (int j = 0; j < imgData.getSecondary().getLastColumn(); j++) {
					String head = imgData.getSecondary().getColumnHeading(j);
					if (!head.equals("ImageID") && !head.equals("Group") && !head.equals("Label")) {
						double val = imgData.getPrimary().getValue(head, priIndex);
					}
				}
			}
	
		}
		
		
		return rt;
	}
	
	
	/**
	 * Combine two ResultsTables  where col matches.
	 * 
	 * Could change col to a list for multiple matches, but only operating on single image data.
	 * 
	 * @param rt1 The first table to check
	 * @param rt2 The second table to check
	 * @param rt The table to combine matches
	 * @param col The header to match by (Label)
	 */
	public void joinTablesByLabel(ResultsTable rt1, ResultsTable rt2, ResultsTable rt, String col) {

		for (int i = 0; i < rt1.size(); i++) {

			double lbl1 = rt1.getValue(col, i);

			for (int j = 0; j < rt2.size(); j++) {

				double lbl2 = rt2.getValue(col, j);

				if (lbl1 == lbl2) {

					rt.addRow();

					rt.addValue("ImageID", rt1.getStringValue("ImageID", i));

					
					
					// add all values in a each matched row for i (index rt1) and j (index rt2)
					// only write common columns once (ImageID, Group if present, Label) - check if column exists

					for (int k = 0; k < rt1.getLastColumn()+1; k++) {
						System.out.println("rt1 columns are: " + rt1.getColumnHeadings());
						String head1 = rt1.getColumnHeading(k);
						if (!rt.columnExists(head1)) {
							double val1 = rt1.getValue(head1, i);
							rt.addValue(head1, rt1.getValue(head1, i));
						}
					}

					for (int l = 0; l < rt2.getLastColumn()+1; l++) {
						System.out.println("rt2 columns are: " + rt2.getColumnHeadings());
						String head2 = rt2.getColumnHeading(l);
						if (!rt.columnExists(head2)) {
							double val2 = rt2.getValue(head2, j);
							rt.addValue(head2, rt2.getValue(head2, j));							
						}
					}
				}
			}
		}
	}
	
	
	
	
	void testJoinTablesByLabel() {

		TableUtility tu = new TableUtility();
		ResultsTableUtility rtu = new ResultsTableUtility();

		File dir = new File("C:\\Users\\simpl\\OneDrive\\Desktop\\FOCUST");

		ParameterCollection params = ParameterCollection.builder().
				outputDir(dir.toString()).
				build();


		ResultsTable rt = new ResultsTable();

		ResultsTable rt1 = testResultsTable1();
		ResultsTable rt2 = testResultsTable2();

		tu.joinTablesByLabel(rt1, rt2, rt, "Label");

		rtu.saveAndStackResults(rt1, "rt1_data", params);
		rtu.saveAndStackResults(rt2, "rt2_data", params);

		rtu.saveAndStackResults(rt, "combined_data", params);

		System.out.println(rt);
		rt.show("Combined Results");
	}



	

	/**
	 * Generate an example ResultsTable for testing
	 * @return
	 */
	private ResultsTable testResultsTable1() {
		ResultsTable rt = new ResultsTable();
		rt.addRow();
		rt.addValue("ImageID", "Test-Image-1");
		rt.addValue("Label", 1);
		rt.addValue("Primary-Value", 123.987);
		rt.addRow();
		rt.addValue("ImageID", "Test-Image-1");
		rt.addValue("Label", 3);
		rt.addValue("Primary-Value", 987.123);
		return rt;
	}
	

	/**
	 * Generate an example ResultsTable for testing
	 * @return
	 */
	private ResultsTable testResultsTable2() {
		ResultsTable rt = new ResultsTable();
		rt.addRow();
		rt.addValue("ImageID", "Test-Image-1");
		rt.addValue("Label", 1);
		rt.addValue("Secondary-Value", 86.77);
		rt.addRow();
		rt.addValue("ImageID", "Test-Image-1");
		rt.addValue("Label", 3);
		rt.addValue("Secondary-Value", 642.987);
		return rt;
	}
	
	
	
	
	/**
	 * Perform an inner join on the data by the columns specified in joinBy.
	 * Written for joining data by imageID and label for the single cell analysis mode. 
	 *
	 * @param rt1
	 * @param rt2
	 * @param joinBy
	 * @return
	 */
	public ResultsTable innerJoin(ResultsTable rt1, ResultsTable rt2, List<String> joinBy) {
		
		ResultsTable rt = new ResultsTable();
	
		
		for (int i = 0; i < rt1.size(); i++) {
			for (int j = 0; j < rt2.size(); j++) {
				
				for (String col : joinBy) {
					if (rt1.getValue(col, i) == rt2.getValue(col, j)) {
						
						addRow(rt, rt1, rt2, joinBy);
						
					} 
				}
				
			}
		}
		
		
		return rt;
	}
	
	
	/**
	 * Adds a row to the 
	 * 
	 * @param rt
	 * @param rt1
	 * @param row1
	 * @param rt2
	 * @param row2
	 * @param joinBy
	 */
	private void addRow(ResultsTable rt, ResultsTable rt1, int row1, ResultsTable rt2, int row2, List<String> joinBy) {
		
		for (int i = 0; i < rt1.getLastColumn(); i++) {
			String header = rt1.getColumnHeading(i);
			if (!joinBy.contains(header)) {
				rt.addValue(i, rt1.getValueAsDouble(i, row1));
			}
		}
		
		for (int i = 0; i < rt2.getLastColumn(); i++) {
			String header = rt2.getColumnHeading(i);
			if (!joinBy.contains(header)) {
				rt.addValue(i, rt2.getValueAsDouble(i, row2));
			}
		}
	}



	/**
	 * Locate matching rows between results tables for single cell processing.
	 * 
	 * @param table 
	 * @param colName the Label column
	 * @param val the label ID to find
	 * @return
	 */
	public static int findMatchingRow(ResultsTable table, String colName, int val) {
		int index = -1;
		for (int i = 0; i < table.size(); i++) {
			int label = Integer.parseInt(table.getStringValue(colName, i));
			if (label == val) {
				index = i;
				break;
			}
		}
		return index;
	}
	

}

