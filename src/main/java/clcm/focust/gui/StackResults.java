package clcm.focust.gui;

import ij.IJ;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import java.io.File;

@Plugin(type = Command.class, label = "FOCUST: Rename Files", menuPath = "Plugins > FOCUST > Stack Results")

/*
 * This class allows users concatenate the csv outputs from multiple rounds of anaylsis.
 */
public class StackResults implements Command {

    @Parameter(label = "Input Directory",style="directory")
    private File inputFiles;

    @Parameter(label = "Append:", style = "listBox", required = true, choices = {"Primary", "Secondary", "Tertiary"})
    private String prefix;

    // write a method to append the prefix "Primary" to each file name in the input directory using buffered reader and writer.

    public void stackResults(File inputFiles, String prefix) {
        File[] files = inputFiles.listFiles();
        if(files == null) {
            IJ.showMessage("No files found in the directory. Ensure your input directory contain images to rename.");
        } else {
            for (File file : files) {
                String fileName = file.getName();
                String newFileName = prefix + "_Objects_" + fileName;
                File newFile = new File(inputFiles, newFileName);
                file.renameTo(newFile);
            }
        }
        IJ.log("Files renamed successfully.");
    }


    @Override
    public void run() {
    stackResults(inputFiles, prefix);
    }
}
