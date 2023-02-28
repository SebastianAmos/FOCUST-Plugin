package clcm.focust;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import java.io.File;


@Plugin(type = Command.class, label = "Single Cell Analysis Lite", menuPath = "Plugins>FOCUST>Lite>Single Cell Analysis")
public class SingleCellLite implements Command {


    @Parameter(label = "Browse", style = "directory")
    private File intputPath;


    @Override
    public void run() {
        // TODO Auto-generated method stub

    }


}
