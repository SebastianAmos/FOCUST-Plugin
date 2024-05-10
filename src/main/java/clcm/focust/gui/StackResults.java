package clcm.focust.gui;

import clcm.focust.parameters.ParameterCollection;
import ij.IJ;
import org.scijava.ItemVisibility;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import clcm.focust.utility.ResultsTableUtility;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

@Plugin(type = Command.class, menuPath = "Plugins>FOCUST>Stack Results")

/*
 * This plugin allows users to stack the csv outputs from multiple rounds of anaylsis.
 * Searches through all sub-directories in the input dir and creates appended files for each encountered file type in the input dir.
 */
public class StackResults implements Command {

    @Parameter(visibility = ItemVisibility.MESSAGE, required = false)
    private final String runMessage = "<html>This plugin will stack (append) any .csv files that were generated <br/>by FOCUST that are found in the input directory and any sub-directories.</html>";

    @Parameter(label = "Input Directory",style="directory", required = true)
    private File inputFiles;

    ArrayList<String> fileList = new ArrayList<String>(Arrays.asList("primary_objects.csv", "secondary_objects.csv", "tertiary_objects.csv", "single_cells.csv"));
    ResultsTableUtility rtu = new ResultsTableUtility();

    public void stackResults(File inputDir) {
        ParameterCollection parameters = ParameterCollection.builder().inputDir(inputDir.getAbsolutePath()).build();

        Queue<File> directories = new LinkedList<>(); // use queue to store new subdirs.
        directories.add(inputDir);

        while (!directories.isEmpty()) {

            File currentDir = directories.poll();
            File[] files = currentDir.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        directories.add(file);
                    } else {
                        String fileName = file.getName();
                        if (fileList.contains(fileName)) {
                            rtu.stackCSVUtility(file, parameters.getInputDir(), fileName);
                        }
                    }
                }
            }
        }
        IJ.log("Results successfully stacked.");
    }

    @Override
    public void run() {
        stackResults(inputFiles);
    }

}
