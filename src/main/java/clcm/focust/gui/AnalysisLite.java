package clcm.focust.gui;

import java.io.File;
import java.io.IOException;

import clcm.focust.mode.ModeProcess;
import clcm.focust.parameters.ObjectParameters;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.parameters.SkeletonParameters;
import clcm.focust.parameters.StratifyParameters;
import mpicbg.imagefeatures.FloatArray2DMOPS;
import org.scijava.ItemVisibility;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import ij.IJ;

import static clcm.focust.utility.SwingIJLoggerUtils.ijLog;


@Plugin(type = Command.class, menuPath = "Plugins>FOCUST>Analysis Lite")

public class AnalysisLite implements Command {
	
	@Parameter(label = "Input Directory",style="directory")
	private File inputFiles;
	
	@Parameter(label = "Output Directory", style = "directory", required = false)
	private File outputSelect;
	
	@Parameter(label = "Name Channel 1 :", style = "text field", required = false)
	private String channel1Name;
	
	@Parameter(label = "Name Channel 2 :", style = "text field", required = false)
	private String channel2Name;
	
	@Parameter(label = "Name Channel 3 :", style = "text field", required = false)
	private String channel3Name;
	
	@Parameter(label = "Name Channel 4:", style = "text field", required = false)
	private String channel4Name;
	
	@Parameter(label = "Grouping Info?", style = "text field", required = false)
	private String groupName;
	
	@Parameter(label = "Select Parameter File", style = "file", required = true)
	private File segParameterFile;
	
	@Parameter(visibility = ItemVisibility.MESSAGE, required = false)
	private final String runMessage = "Click 'OK' when you are ready to run analysis!";
	
	
	@Override
	public void run() {

		ijLog("Loading parameter file...");

		ParameterCollection param;

        try {
            param = ParameterCollection.loadParameterCollection(segParameterFile.getAbsolutePath());
			ijLog("Parameter file loaded.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

		ObjectParameters primaryObject = param.getPrimaryObject();
		ObjectParameters secondaryObject = param.getSecondaryObject();
		ObjectParameters tertiaryObject = param.getTertiaryObject();
		SkeletonParameters skeletonParams = param.getSkeletonParameters();
		StratifyParameters stratifyParams = param.getStratifyParameters();

		ParameterCollection parameterCollection = ParameterCollection.builder().
				inputDir(inputFiles.getAbsolutePath() + "\\").
				outputDir(outputSelect.getAbsolutePath() + "\\").
				mode(param.getMode()).
				analysisOnly(param.getAnalysisOnly()).
				primaryObject(primaryObject).
				secondaryObject(secondaryObject).
				tertiaryObject(tertiaryObject).
				killBorderType(param.getKillBorderType()).
				groupingInfo(groupName).
				nameChannel1(channel1Name).
				nameChannel2(channel2Name).
				nameChannel3(channel3Name).
				nameChannel4(channel4Name).
				tertiaryIsDifference(param.getTertiaryIsDifference()).
				processTertiary(param.getProcessTertiary()).
				skeletonParameters(skeletonParams).
				stratifyParameters(stratifyParams).
				build();

		ModeProcess mp = new ModeProcess();
		mp.run(parameterCollection);


		// if progress bar -> implement an analysis only flag to prevent attempts at GUI updates if they don't exist?

	}

}
