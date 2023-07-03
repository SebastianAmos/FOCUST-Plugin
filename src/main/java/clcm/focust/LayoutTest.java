package clcm.focust;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

public class LayoutTest extends JFrame {

	JFrame frame = new JFrame("FOCUST: Spheroid Analysis");
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LayoutTest frame = new LayoutTest();
					frame.setVisible(true);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LayoutTest() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 834, 471);
		panel = new JPanel(new MigLayout());
		
		
		 JLabel lblHeader = new JLabel("Process a dataset:");
	     JLabel lblHeader2 = new JLabel("You must have optimised your segmentation parameters.");
	     JButton btnHelp = new JButton("Help");
	     JLabel lblInput = new JLabel("Select an Input Directory:");
	     JTextField txtInputDir = new JTextField(20);
	     JButton btnInput = new JButton("Browse");
	     JButton btnOutput = new JButton("Browse");
		
		panel.add(lblHeader);
		panel.add(lblHeader2);
		panel.add(txtInputDir, "wrap");
		
		
		
	}

}
