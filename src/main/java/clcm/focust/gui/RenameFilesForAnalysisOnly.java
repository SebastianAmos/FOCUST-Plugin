package clcm.focust.gui;

import ij.IJ;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import java.io.File;

@Plugin(type = Command.class, menuPath = "Plugins>FOCUST>Rename Files")

/*
 * This class allows users to rename existing images so they can be recognised by FOCUST's Analysis Only mode.
 */
public class RenameFilesForAnalysisOnly implements Command {

    @Parameter(label = "Input Directory",style="directory")
    private File inputFiles;

    @Parameter(label = "Append:", style = "listBox", required = true, choices = {"Primary", "Secondary", "Tertiary"})
    private String prefix;

    // write a method to append the prefix "Primary" to each file name in the input directory using buffered reader and writer.

    public void renameFiles(File inputFiles, String prefix) {
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
    renameFiles(inputFiles, prefix);
    }
}
