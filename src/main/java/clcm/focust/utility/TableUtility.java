package clcm.focust.utility;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

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

import static clcm.focust.utility.SwingIJLoggerUtils.ijLog;

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
	 * @param input A raw image containing intensity data. Can be passed from the current image's channels[].
	 * @param label A labelled (segmented) image.
	 * @return A results table containing intensity results.
	 */
	public static ResultsTable processIntensity(ImagePlus input, ImagePlus label) {

		// constructs default table and appends any columns in supplied tables that don't yet exist.
		ResultsBuilder rb = new ResultsBuilder();
		final IntensityMeasures im = new IntensityMeasures(input, label);

		// get vol and mean separately so intden can be calculated without needing to compute twice.
		ResultsTable rtVol = im.getVolume();
		ResultsTable rtMean = im.getMean();
		rb.addResult(rtVol);
		rb.addResult(im.getNumberOfVoxels());
		rb.addResult(rtMean);
		//rb.addResult(intDen(rtVol, rtMean));
		rb.addResult(im.getSumOfVoxels());
		rb.addResult(im.getMax());
		rb.addResult(im.getMin());
		rb.addResult(im.getMedian());
		rb.addResult(im.getMode());
		rb.addResult(im.getSkewness());
		rb.addResult(im.getKurtosis());
		rb.addResult(im.getStdDev());
        return rb.getResultsTable();
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

        return rb.getResultsTable();
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
	 * @param segmentedObjects List of objects within which intensity is to be calculated.
	 * @param channels Array of channels to be measured.
	 * @return A map of segmented objects to their respective results tables.
	 */
	public static Map<ImagePlus, ResultsTable> compileIntensityResults(ArrayList<ImagePlus> segmentedObjects, ImagePlus[] channels, ParameterCollection parameters){

		long start = System.currentTimeMillis();

		// A map for intensity calcs
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
						c1Name = ("C" + (k + 1));
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
						c2Name = ("C" + (k + 1));
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
						c3Name = ("C" + (k + 1));
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
						c4Name = ("C" + (k + 1));
					}
					for (String head : headers) {
						String newC4Head = c4Name + "." + head;
						rt.setColumn(newC4Head, temp.getColumnAsVariables(head));
					}
				}
				
			}
			
			intensityTables.put(segmentedObjects.get(j), rt);
			
		}

		long end = System.currentTimeMillis();
		ijLog("Single threaded intensity calculations completed in " + (end - start)/1000 + " seconds.");

		return intensityTables;
		
	}

	public static Map<ImagePlus, ResultsTable> compileIntensityResultsMultithread(ArrayList<ImagePlus> segmetentedObjects, ImagePlus[] channels, ParameterCollection parameters) {

		long start = System.currentTimeMillis();

		Map<ImagePlus, ResultsTable> intensityResults = new HashMap<>();

		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		for (ImagePlus segmentedObject : segmetentedObjects) {

			List<Future<ResultsTable>> futures = new ArrayList<>();

			for (int k = 0; k < channels.length; k++) {

				ImagePlus channel = channels[k];

				String channelName = getChannelName(k, parameters);

				Future<ResultsTable> future = executor.submit(() -> {
					ResultsTable temp = TableUtility.processIntensity(channel, segmentedObject);
					List<String> headers = new ArrayList<>(Arrays.asList(temp.getHeadings()));
					headers.remove(0);

					for (String head : headers) {
						String newHead = channelName + "." + head;
						temp.renameColumn(head, newHead);
					}

					return temp;

				});

				futures.add(future);

			}

			ResultsTable rt = new ResultsTable();

			for (Future<ResultsTable> future : futures) {
				try {
					ResultsTable temp = future.get();
					List<String> headers = new ArrayList<>(Arrays.asList(temp.getHeadings()));
					for (String head : headers) {
						rt.setColumn(head, temp.getColumnAsVariables(head));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			intensityResults.put(segmentedObject, rt);

		}

		executor.shutdown();

		long end = System.currentTimeMillis();
		ijLog("Intensity calculations completed in " + (end - start)/1000 + " seconds.");

		return intensityResults;
	}


	/** TODO While this implementation is slightly faster to compute, the channel data can be in any order, so stacks results would need more work that probably isn't worth a second or two per image...
	 *
	 * @param segmetentedObjects
	 * @param channels
	 * @param parameters
	 * @return
	 */
	public static Map<ImagePlus, ResultsTable> compileIntensityResultsMultithreadConcurrentMap(ArrayList<ImagePlus> segmetentedObjects, ImagePlus[] channels, ParameterCollection parameters) {

		long start = System.currentTimeMillis();

		Map<ImagePlus, ResultsTable> intensityResults = new ConcurrentHashMap<>();

		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		ijLog("Number of threads: " + Runtime.getRuntime().availableProcessors() + " cores.");

		/* Multithreaded */
		for (ImagePlus segmentedObject : segmetentedObjects){
			for (int i = 0; i < channels.length; i++) {
				ImagePlus channel = channels[i];
				String channelName = getChannelName(i, parameters);

				executor.submit(() -> {
					ResultsTable temp = TableUtility.processIntensity(channel, segmentedObject);
					List<String> headers = new ArrayList<>(Arrays.asList(temp.getHeadings()));
					headers.remove(0);

					for (String head : headers){
						String newHead = channelName + "." + head;
						temp.renameColumn(head, newHead);
					}

					// sync to concurrent map
					synchronized (intensityResults){
						if(intensityResults.containsKey(segmentedObject)) {
							ResultsTable existingTable = intensityResults.get(segmentedObject);
							List<String> renamedHeaders = new ArrayList<>(Arrays.asList(temp.getHeadings()));
							for (String head : renamedHeaders){
								existingTable.setColumn(head, temp.getColumnAsVariables(head));
							}
						} else {
							intensityResults.put(segmentedObject, temp);
						}
					}
					return null;
				});
			}
		}
		executor.shutdown();
		/* End Multithreaded */

		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();
		ijLog("Intensity calculations completed in " + (end - start)/1000 + " seconds.");
		return intensityResults;
	}




	private static String getChannelName(int k, ParameterCollection parameters) {
		String channelName = "C";

		if (k == 0 ) {
			if(!parameters.getNameChannel1().isEmpty()){
				channelName = parameters.getNameChannel1();
			} else {
				channelName = "C" + (k + 1);
			}
		}

		if (k == 1) {
			if(!parameters.getNameChannel2().isEmpty()){
				channelName = parameters.getNameChannel2();
			} else {
				channelName = "C" + (k + 1);
			}
		}

		if (k == 2) {
			if(!parameters.getNameChannel3().isEmpty()){
				channelName = parameters.getNameChannel3();
			} else {
				channelName = "C" + (k + 1);
			}
		}

		if (k == 3) {
			if(!parameters.getNameChannel4().isEmpty()){
				channelName = parameters.getNameChannel4();
			} else {
				channelName = "C" + (k + 1);
			}
		}

		return channelName;
	}



	
	public static List<ResultsTable> compileBandIntensities(List<ClearCLBuffer> bands, ImagePlus[] channels, Calibration cal, ParameterCollection parameters) {

		ijLog("Calculating band-restricted intensities...");
		long start = System.currentTimeMillis();

		CLIJ2 clij2 = CLIJ2.getInstance();
		List<ResultsTable> rtList = new ArrayList<>();
		int count = 1;
		
		for (ClearCLBuffer band : bands) {


            ResultsTable rt = new ResultsTable();

            Variable[] labelID = null;

            for (int j = 0; j < channels.length; j++) {

                ClearCLBuffer copy = clij2.create(band);
               //clij2.copy(band, copy);
                ImagePlus imp = clij2.pull(copy);
                imp.setCalibration(cal);
                copy.close();

                ResultsTable temp = TableUtility.processIntensity(channels[j], imp);

                // get col headers without Label
                List<String> headers = new ArrayList<>(Arrays.asList(temp.getHeadings()).subList(1, temp.getHeadings().length));

                labelID = temp.getColumnAsVariables("Label");
                //temp.show("Band " + count + " Results");

                // channel 1
                if ((j + 1) == 1) {
                    String c1Name = null;

					if (!parameters.getNameChannel1().isEmpty()) {
						c1Name = count + "." + parameters.getNameChannel1();
					} else {
						c1Name = (count + ".c1");
					}

                    for (String head : headers) {
                        String headerName = (c1Name + "." + head);
                        rt.setColumn(headerName, temp.getColumnAsVariables(head));
                    }
                }


                // channel 2
                if ((j + 1) == 2) {
                    String c2Name = null;

					if (!parameters.getNameChannel2().isEmpty()) {
						c2Name = count + "." + parameters.getNameChannel2();
					} else {
						c2Name = (count + ".c2");
					}

                    for (String head : headers) {
                        String headerName = (c2Name + "." + head);
                        rt.setColumn(headerName, temp.getColumnAsVariables(head));
                    }
                }

                // channel 3
                if ((j + 1) == 3) {
                    String c3Name = null;

					if (!parameters.getNameChannel3().isEmpty()) {
						c3Name = count + "." + parameters.getNameChannel3();
					} else {
						c3Name = (count + ".c3");
					}

                    for (String head : headers) {
                        String headerName = (c3Name + "." + head);
                        rt.setColumn(headerName, temp.getColumnAsVariables(head));
                    }
                }

                // channel 4
                if ((j + 1) == 4) {
                    String c4Name = null;

					if (!parameters.getNameChannel4().isEmpty()) {
						c4Name = count + "." + parameters.getNameChannel4();
					} else {
						c4Name = (count + ".c4");
					}


                    for (String head : headers) {
                        String headerName = (c4Name + "." + head);
                        rt.setColumn(headerName, temp.getColumnAsVariables(head));
                    }
                }

            }

            rt.setColumn("Label", labelID);
            //rt.show("FINAL BAND " + count + " TABLE");

            rtList.add(rt);

            count++;
        }

		long end = System.currentTimeMillis();
		ijLog("Band intensity results compiled in " + (end - start)/1000 + " seconds.");

		return rtList;
	}



	public static List<ResultsTable> compileBandIntensitiesMultithreaded(List<ClearCLBuffer> bands, ImagePlus[] channels, Calibration cal, ParameterCollection parameters) {

		ijLog("Measuring band-restricted intensities...");
		long start = System.currentTimeMillis();

		CLIJ2 clij2 = CLIJ2.getInstance();

		List<Future<ResultsTable>> futureList = new ArrayList<>();
		ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		int count = 0;
		for (ClearCLBuffer band: bands){

			count++;

			for(int j = 0; j < channels.length; j++) {

				ImagePlus channel = channels[j];
				int finalJ = j;
				int finalCount = count;

				Future<ResultsTable> future = executor.submit(() ->{
					ClearCLBuffer copy = clij2.create(band);
					clij2.copy(band, copy);
					ImagePlus imp = clij2.pull(copy);
					imp.setCalibration(cal);
					copy.close();

					ResultsTable temp = TableUtility.processIntensity(channel, imp);
					imp.close();
					List<String> headers = new ArrayList<>(Arrays.asList(temp.getHeadings()));
					headers.remove(0);

					String channelName = getChannelName(finalJ, parameters);
					for (String head : headers) {
						String newHead = finalCount + "." + channelName + "." + head;
						temp.renameColumn(head, newHead);
					}
					return temp;
				});
				futureList.add(future);
			}
		}

		List<ResultsTable> rtList = new ArrayList<>();
		for (Future<ResultsTable> future : futureList) {
			try {
				rtList.add(future.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		executor.shutdown();

		long end = System.currentTimeMillis();
		ijLog("Completed in " + (end - start)/1000 + " seconds.");

		return rtList;
	}
	
	
	public static ResultsTable extractGroupAndTitle(ResultsTable rt, ParameterCollection parameters, String imgName) {
		
		ResultsTable result = new ResultsTable();
		String headers = rt.getColumnHeadings();
		if(parameters.getGroupingInfo().isEmpty()) {
			for (int i = 0; i < rt.size(); i++) {
				result.setValue("ImageID", i, imgName);
			}
		} else {
			for (int i = 0; i < rt.size(); i++) {
				result.setValue("ImageID", i, imgName);
				result.setValue("Group", i, parameters.getGroupingInfo());
			}
		}
		result.setColumn("Label", rt.getColumnAsVariables("Label"));

		return result;
	}
	
	
	/**
	 * Compile results with the ResultsBuilder class. 
	 * 
	 * @param list
	 * @return
	 */
	public static ResultsTable compileAllResults(List<ResultsTable> list){

		ResultsTable output;

		System.out.println("List size: " + list.size());

		if (list.size() > 1) {
			System.out.println("Compiling tables...");
			ResultsBuilder rb = new ResultsBuilder();
			for (ResultsTable rt : list) {
				rb.addResult(rt);
			}
			output = rb.getResultsTable();
		} else {
			System.out.println("Only one table exists - returning.");
			output = list.get(0);
		}

		return output;
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

	
	
	
	/**
	 * Match skeletons to labels objects as they typically don't adopt the same labelling and some shapes may skeletonize incompletely.
	 * This way users have visibility to shared skeletons and data may still be analysed in linked and meaningful ways.
	 * 
	 * @param rt The compiled table to match and add skeletons to.
	 * @param skel The skeleton results holder object that labelled skeletons can be pulled from.
	 * @return The compiled table with matched skeletons appended.
	 */
	public static ResultsTable matchAndAppendSkeletons(ResultsTable rt, SkeletonResultsHolder skel) {

		ResultsTable standard = skel.getStandard();
		ResultsTable matched = skel.getLabelMatched();

		for (int i = 0; i < rt.size(); i++) {

			double lbl = Double.parseDouble(rt.getStringValue("Label", i));

			for (int j = 0; j < matched.size(); j++) {

				double sklbl = Double.parseDouble(matched.getStringValue("Label", j));

				if (lbl == sklbl) {

					double skID = matched.getValue("Max", j);

					// find the matching label in standard

					for (int k = 0; k < standard.size(); k++) {

						double standardID = standard.getValue("Skeleton.# Skeleton", k);

						if (skID == standardID) {

							for (int l = 0; l < standard.getLastColumn()+1; l++) {

								String head = standard.getColumnHeading(l);

								rt.setValue(head, i, standard.getValue(head, k));
							}
						}
					}
				}
			}
		}
		
		return rt;
	}


	// Iterate through the list of tables and append the results to the main table.
	public static void addBandResults(ResultsTable rt , List<ResultsTable> rtList, String bandName) {
		int count = 1;
		for (ResultsTable table : rtList) {
			appendResultsByLabel(rt, table, bandName);
			count++;
		}


	}


	public static void appendResultsByLabel(ResultsTable rt, ResultsTable stratified, String bandName) {

		try {
			for (int i = 0; i < rt.size(); i++) {

				double lbl = Double.parseDouble(rt.getStringValue("Label", i));

				for (int j = 0; j < stratified.size(); j++) {

					double lbl1 = Double.parseDouble(stratified.getStringValue("Label", j));

					if (lbl == lbl1) {

						for (int k = 0; k < stratified.getLastColumn()+1; k++) {

							String head = stratified.getColumnHeading(k);

							if(!head.contains("Label")) {

								rt.setValue(bandName + head, i, stratified.getValue(head, j));

							}
						}
					}
				}
			}
		} catch (NumberFormatException e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * Combine all results held in a list by label.
	 *
	 * @param list The list of ResultsTables to combine
	 * @return a single ResultsTable with all results appended.
	 */
	public static ResultsTable appendAllResultsByLabel(List<ResultsTable> list){

		ResultsTable rt = list.get(0);

		for (int i = 1; i < list.size(); i++) {
			appendResultsByLabel(rt, list.get(i), "");
		}

		return rt;
	}


	/**
	 * Combine two ResultsTables where col matches.
	 * Could change col to a list for multiple matches, but only operating on single image data at this stage.
	 * 
	 * @param rt1 The first table to check
	 * @param rt1Object The name that should be appended to each column header from rt1
	 * @param rt2 The second table to check
	 * @param rt2Object The name that should be appended to each column header from rt2 
	 * @param col The column header to match rows by
	 */
	public ResultsTable joinTablesByLabel(ResultsTable rt1, String rt1Object, ResultsTable rt2, String rt2Object, String col) {

		ResultsTable rt = new ResultsTable();

		for (int i = 0; i < rt1.size(); i++) {
			
			double lbl1 = Double.parseDouble(rt1.getStringValue(col, i));
			
			for (int j = 0; j < rt2.size(); j++) {

				double lbl2 = Double.parseDouble(rt2.getStringValue(col, j));

				if (getBaseLabel(lbl1) == getBaseLabel(lbl2)) {

					int row = rt.getCounter();
					rt.incrementCounter();

					// write common columns
					rt.setValue("ImageID", row, rt1.getStringValue("ImageID", i));
					if (rt1.columnExists("Group")) {
						rt.setValue("Group", row, rt1.getStringValue("Group", i));
					}
					rt.setValue("Label", row, lbl1);
					
					// add all values in a each matched row for i (index rt1) and j (index rt2)
					for (int k = 0; k < rt1.getLastColumn()+1; k++) {
						String head1 = rt1.getColumnHeading(k);
						if (!head1.equals("ImageID") && !head1.equals("Group") && !head1.equals("Label") && !head1.equals("-")) {
							rt.setValue(rt1Object + head1, row, rt1.getValue(head1, i));
						}
					}

					for (int l = 0; l < rt2.getLastColumn()+1; l++) {
						String head2 = rt2.getColumnHeading(l);
						if (!head2.equals("ImageID") && !head2.equals("Group") && !head2.equals("Label") && !head2.equals("-")) {
							rt.setValue(rt2Object + head2, row, rt2.getValue(head2, j));
						}
					}
				}
			}
		}
		return rt;
	}
	
	
	/**
	 * Perform an inner join on two results tables.
	 * 
	 * @param rt Existing table
	 * @param rt1 Table to join (inner) with existing table
	 * @param rt1Object Name of object type for rt1 i.e. "tertiary" 
	 * @param col Column header to join by
	 */
	public void innerJoin(ResultsTable rt, ResultsTable rt1, String rt1Object, String col) {
		
		for (int i = 0; i < rt.size(); i++) {
			
			double lbl = rt.getValue(col, i);
			
			for (int j = 0; j < rt1.size(); j++) {
				
				double lbl1 = rt1.getValue(col, j);
				
				if (lbl == lbl1) {
					
					for (int k = 0; k < rt1.getLastColumn()+1 ; k++) {		
						String head1 = rt1.getColumnHeading(k);
						if (!head1.equals("ImageID") && !head1.equals("Group") && !head1.equals("Label")) {
							rt.setValue(rt1Object + "." + head1, i, rt1.getValue(head1, j));
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * Return the integer component from a double. 
	 * @param lab The label value
	 * @return The integer component of the label
	 */
	public int getBaseLabel(double lab) {
		//Math.floor(lab);
		return (int) lab;
	}


	/**
	 * Append a string to the beginning of each column header in a ResultsTable.
	 * Exclude the Label, ImageID, and Group column.
	 * @param txt To append before each header.
	 * @param rt The ResultsTable to modify.
	 */
	static public void appendToHeader(String txt, ResultsTable rt) {
		String[] headers = rt.getHeadings();
		for (String header : headers) {
			if (!header.equals("Label") && !header.equals("ImageID") && !header.equals("Group")){
				rt.renameColumn(header, txt + header);
			}

		}
	}
}