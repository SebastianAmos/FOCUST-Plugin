package clcm.focust.gui;

import java.io.File;
import java.io.IOException;
import clcm.focust.mode.ModeProcess;
import clcm.focust.parameters.ObjectParameters;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.parameters.SkeletonParameters;
import clcm.focust.parameters.StratifyParameters;
import ij.IJ;
import org.scijava.ItemVisibility;
import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;


@Plugin(type = Command.class, menuPath = "Plugins>FOCUST>Analysis Lite")

public class AnalysisLite implements Command {
	
	@Parameter(label = "Input Directory:", style="directory")
	private File inputdir;
	
	@Parameter(label = "Output Directory:", style = "directory", required = false)
	private File outputdir;
	
	@Parameter(label = "Name Channel 1:", style = "text field", required = false)
	private String c1name;
	
	@Parameter(label = "Name Channel 2:", style = "text field", required = false)
	private String c2name;
	
	@Parameter(label = "Name Channel 3:", style = "text field", required = false)
	private String c3name;
	
	@Parameter(label = "Name Channel 4:", style = "text field", required = false)
	private String c4name;
	
	@Parameter(label = "Grouping Info?", style = "text field", required = false)
	private String group;
	
	@Parameter(label = "Select Parameter File", style = "file")
	private File parameterfile;
	
	@Parameter(visibility = ItemVisibility.MESSAGE, required = false)
	private final String runmsg = "Click 'OK' when you are ready to run analysis!";
	
	
	@Override
	public void run() {

        IJ.log("Running Analysis Lite...");

		IJ.log("Loading parameter file...");

		try {
			ParameterCollection param = ParameterCollection.loadParameterCollection(parameterfile.getAbsolutePath());
			IJ.log("Parameter file loaded.");

			launchFOCUST(param);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private void launchFOCUST(ParameterCollection param) {

			ObjectParameters primaryObject = param.getPrimaryObject();
			ObjectParameters secondaryObject = param.getSecondaryObject();
			ObjectParameters tertiaryObject = param.getTertiaryObject();
			SkeletonParameters skeletonParams = param.getSkeletonParameters();
			StratifyParameters stratifyParams = param.getStratifyParameters();

			if (outputdir != null){
				outputdir = inputdir;
			}

			ParameterCollection parameterCollection = ParameterCollection.builder().
					inputDir(inputdir.getAbsolutePath() + "\\").
					outputDir(outputdir.getAbsolutePath() + "\\").
					mode(param.getMode()).
					analysisOnly(param.getAnalysisOnly()).
					primaryObject(primaryObject).
					secondaryObject(secondaryObject).
					tertiaryObject(tertiaryObject).
					killBorderType(param.getKillBorderType()).
					groupingInfo(group).
					nameChannel1(c1name).
					nameChannel2(c2name).
					nameChannel3(c3name).
					nameChannel4(c4name).
					tertiaryIsDifference(param.getTertiaryIsDifference()).
					processTertiary(param.getProcessTertiary()).
					skeletonParameters(skeletonParams).
					stratifyParameters(stratifyParams).
					build();

			ModeProcess mp = new ModeProcess();
			mp.run(parameterCollection);

	}

}