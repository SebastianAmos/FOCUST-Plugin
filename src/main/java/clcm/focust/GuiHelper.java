package clcm.focust;


import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Arrays;
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




	/**
	 * Collects components from a GUI container so they can edited together. 
	 * Intended to allow enabling or disabling of a container's components.
	 * 
	 * @param container Probably a JPanel
	 * @return 
	 */
	public Component[] getComponents(Component container) {
		ArrayList<Component> list = null;

		try {
			list = new ArrayList<Component>(Arrays.asList(
					((Container) container).getComponents()));
			for (int index = 0; index < list.size(); index++) {
				for (Component currentComponent : getComponents(list.get(index))) {
					list.add(currentComponent);
				}
			}
		} catch (ClassCastException e) {
			list = new ArrayList<Component>();
		}

		return list.toArray(new Component[list.size()]);
	}
    
	
	
}
