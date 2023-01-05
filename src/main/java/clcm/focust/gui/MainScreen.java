package clcm.focust.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.scijava.plugin.Plugin;
import org.scijava.command.Command;
import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.WindowManager;
import ij.gui.ImageCanvas;
import ij.gui.ImageRoi;
import ij.gui.ImageWindow;
import ij.gui.Overlay;
import ij.gui.Roi;
import ij.gui.StackWindow;
import ij.gui.Toolbar;
import ij.plugin.PlugIn;


import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.JButton;


public class MainScreen extends JFrame{

	/**
	 * The serialVersionUID. 
	 */
	private static final long serialVersionUID = 2501487734017653908L;
	private JPanel contentPane;
	
	/** Button new Button. Optimise? . */
	private JButton btnOptimize;
	


	
/*	
	public static void main(String[] args) {
		
		
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				MainGui = new MainScreen();
				MainGui.setVisible(true);
			}
		});
	}
	
*/
	/*
	    public void run(final String arg) {
	    	java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					MainGui = new MainScreen();
					MainGui.setVisible(true);
				}
			});
	    }
*/

	/**
	 * Create the frame.
	 */
	public MainScreen() {
		setTitle("FOCUST");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainScreen.class.getResource("/clcm/focust/resources/icon2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 806, 476);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbMode = new JLabel("Select a mode to begin:");
		lbMode.setFont(new Font("Gadugi", Font.BOLD, 16));
		lbMode.setVerticalAlignment(SwingConstants.TOP);
		lbMode.setBounds(20, 134, 192, 35);
		contentPane.add(lbMode);
		
		btnOptimize = new JButton("Optimize");
		btnOptimize.setFont(new Font("Gadugi", Font.BOLD, 13));
		btnOptimize.setBounds(87, 169, 154, 51);
		contentPane.add(btnOptimize);
		
		JButton btnSpheroid = new JButton("Spheroid Analysis");
		btnSpheroid.setFont(new Font("Gadugi", Font.BOLD, 13));
		btnSpheroid.setBounds(87, 247, 154, 51);
		contentPane.add(btnSpheroid);
		
		JLabel lblSpheroidIcon = new JLabel("");
		lblSpheroidIcon.setIcon(new ImageIcon(MainScreen.class.getResource("/clcm/focust/resources/spheroidIcon0.2.png")));
		lblSpheroidIcon.setBounds(10, 241, 77, 67);
		contentPane.add(lblSpheroidIcon);
		
		JLabel lblSingleCellIcon = new JLabel("");
		lblSingleCellIcon.setIcon(new ImageIcon(MainScreen.class.getResource("/clcm/focust/resources/singlecellicon.png")));
		lblSingleCellIcon.setBounds(20, 302, 77, 67);
		contentPane.add(lblSingleCellIcon);
		
		JButton btnSingleCell = new JButton("Single Cell Analysis");
		btnSingleCell.setFont(new Font("Gadugi", Font.BOLD, 13));
		btnSingleCell.setBounds(87, 309, 154, 51);
		contentPane.add(btnSingleCell);
		
		JLabel lblSpeckleIcon = new JLabel("");
		lblSpeckleIcon.setIcon(new ImageIcon(MainScreen.class.getResource("/clcm/focust/resources/speckleIcon.png")));
		lblSpeckleIcon.setBounds(20, 359, 77, 67);
		contentPane.add(lblSpeckleIcon);
		
		JButton btnSpeckle = new JButton("Speckle Analysis");
		btnSpeckle.setFont(new Font("Gadugi", Font.BOLD, 13));
		btnSpeckle.setBounds(87, 371, 154, 51);
		contentPane.add(btnSpeckle);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setFont(new Font("Gadugi", Font.BOLD, 13));
		btnHelp.setBounds(690, 11, 90, 35);
		contentPane.add(btnHelp);
		
		JLabel lblDetermineTheParameters = new JLabel("Determine the parameters for segmenting a new dataset.");
		lblDetermineTheParameters.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblDetermineTheParameters.setBounds(251, 176, 360, 35);
		contentPane.add(lblDetermineTheParameters);
		
		JLabel lblBatchProcessSpheroid = new JLabel("Batch process a dataset containing spheroids or organoids.");
		lblBatchProcessSpheroid.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBatchProcessSpheroid.setBounds(251, 254, 388, 35);
		contentPane.add(lblBatchProcessSpheroid);
		
		JLabel lblBatchProcessSingleCell = new JLabel("Batch process a dataset that contains primary objects and secondary objects.");
		lblBatchProcessSingleCell.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBatchProcessSingleCell.setBounds(251, 316, 489, 35);
		contentPane.add(lblBatchProcessSingleCell);
		
		JLabel lblBatchProcessSpeckle = new JLabel("Batch process a dataset that contains multiple secondary objects per primary object. ");
		lblBatchProcessSpeckle.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBatchProcessSpeckle.setBounds(251, 378, 522, 35);
		contentPane.add(lblBatchProcessSpeckle);
		
		JLabel lblMasterIcon = new JLabel("");
		lblMasterIcon.setIcon(new ImageIcon(MainScreen.class.getResource("/clcm/focust/resources/FullLogo3.png")));
		lblMasterIcon.setBounds(10, 0, 445, 114);
		contentPane.add(lblMasterIcon);
		
		JLabel lblQMarkIcon = new JLabel("");
		lblQMarkIcon.setIcon(new ImageIcon(MainScreen.class.getResource("/clcm/focust/resources/iconquestionmark.png")));
		lblQMarkIcon.setBounds(30, 161, 43, 67);
		contentPane.add(lblQMarkIcon);
		
		
		
		
		/*
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			
			}
		}); */
	}
	

}
