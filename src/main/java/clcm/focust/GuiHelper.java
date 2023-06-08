package clcm.focust;


import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

public class GuiHelper {

	/**
	 * This method iterates through a button collection and returns the text of the selected radiobutton.
	 * @param btnGroup
	 * @return String of the selected radiobutton.
	 */
	public static String getSelectedButton(ButtonGroup btnGroup) {
		for (Enumeration<AbstractButton> btns = btnGroup.getElements(); btns.hasMoreElements();) {
			AbstractButton btn = btns.nextElement();
			if (btn.isSelected()) {
				return btn.getText();
			}
			
		}
		return null;
	}

	
	
}
