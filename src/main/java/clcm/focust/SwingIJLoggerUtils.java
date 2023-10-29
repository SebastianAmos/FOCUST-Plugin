package clcm.focust;

import javax.swing.SwingUtilities;

import ij.IJ;

/**
 * Utility method/s to log via ImageJ on the EDT.
 */
public final class SwingIJLoggerUtils {
	/** IJ.Log(toLog) except on EDT (invokeLater) */
	public static final void ijLog(String toLog) {
		SwingUtilities.invokeLater(() -> IJ.log(toLog));
	}
}
