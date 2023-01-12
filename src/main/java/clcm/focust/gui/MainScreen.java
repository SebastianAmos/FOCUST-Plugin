package clcm.focust.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ij.gui.GenericDialog;

import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import java.awt.Font;
import javax.swing.JButton;


public class MainScreen extends JFrame{

	private JPanel mainPane;

	/** Button to open the optimization window. */
	private JButton btnOptimize;
	/** Button to open Spheroid window. */
	private JButton btnSpheroid;
	/** Button to open the single cell window. */
	private JButton btnSingleCell;
	/** Button to open the speckle window. */ 
	private JButton btnSpeckle;


	/**
	 * construct the main menu gui. 
	 */
	public MainScreen() {
		setTitle("FOCUST");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainScreen.class.getResource("/clcm/focust/resources/icon2.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 806, 476);
		mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mainPane);
		mainPane.setLayout(null);
		
		JLabel lbMode = new JLabel("Select a mode to begin:");
		lbMode.setFont(new Font("Gadugi", Font.BOLD, 16));
		lbMode.setVerticalAlignment(SwingConstants.TOP);
		lbMode.setBounds(20, 134, 192, 35);
		mainPane.add(lbMode);
		
		btnOptimize = new JButton("Optimize");
		btnOptimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				/*
				 * USE @PARAMETER functions such as #@ String (choices={"Option A", "Option B"}, style="radioButtonHorizontal") myChoiceABC
				 */
				
				
				
			/*	GenericDialog gd = new GenericDialog("Optimization Selector");
				String[] choices = {"Spheroid Analysis", "Single Cell Analysis", "Speckle Analysis"};
				gd.addRadioButtonGroup("Select a Mode to Optimize:", choices, 1, 3, null);
				gd.showDialog();
				String button = gd.getNextRadioButton(); 
			*/	
				
				
				
			/*	SwingUtilities.invokeLater(() -> {
					OptimizationSingleCellView OptimizeSingleCellGui = new OptimizationSingleCellView();
					OptimizeSingleCellGui.setVisible(true);
					Window win = SwingUtilities.getWindowAncestor(btnOptimize);
					win.dispose();
				}); */
			}
		});
		btnOptimize.setFont(new Font("Gadugi", Font.BOLD, 13));
		btnOptimize.setBounds(87, 169, 154, 51);
		mainPane.add(btnOptimize);
		
		JButton btnSpheroid = new JButton("Spheroid Analysis");
		btnSpheroid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					SpheroidView SpheroidGui = new SpheroidView();
					SpheroidGui.setVisible(true);
					Window win = SwingUtilities.getWindowAncestor(btnSpheroid);
					win.dispose();
				});
			}
		});
		btnSpheroid.setFont(new Font("Gadugi", Font.BOLD, 13));
		btnSpheroid.setBounds(87, 247, 154, 51);
		mainPane.add(btnSpheroid);
		
		JLabel lblSpheroidIcon = new JLabel("");
		lblSpheroidIcon.setIcon(new ImageIcon(MainScreen.class.getResource("/clcm/focust/resources/spheroidIcon0.2.png")));
		lblSpheroidIcon.setBounds(10, 241, 77, 67);
		mainPane.add(lblSpheroidIcon);
		
		JLabel lblSingleCellIcon = new JLabel("");
		lblSingleCellIcon.setIcon(new ImageIcon(MainScreen.class.getResource("/clcm/focust/resources/singlecellicon.png")));
		lblSingleCellIcon.setBounds(20, 302, 77, 67);
		mainPane.add(lblSingleCellIcon);
		
		JButton btnSingleCell = new JButton("Single Cell Analysis");
		btnSingleCell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					SingleCellView SingleCellGui = new SingleCellView();
					SingleCellGui.setVisible(true);
					Window win = SwingUtilities.getWindowAncestor(btnSingleCell);
					win.dispose();
					
				});
				
			}
		});
		btnSingleCell.setFont(new Font("Gadugi", Font.BOLD, 13));
		btnSingleCell.setBounds(87, 309, 154, 51);
		mainPane.add(btnSingleCell);
		
		JLabel lblSpeckleIcon = new JLabel("");
		lblSpeckleIcon.setIcon(new ImageIcon(MainScreen.class.getResource("/clcm/focust/resources/speckleIcon.png")));
		lblSpeckleIcon.setBounds(20, 359, 77, 67);
		mainPane.add(lblSpeckleIcon);
		
		JButton btnSpeckle = new JButton("Speckle Analysis");
		btnSpeckle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					SpeckleView SpeckleGui = new SpeckleView();
					SpeckleGui.setVisible(true);
					Window win = SwingUtilities.getWindowAncestor(btnSpeckle);
					win.dispose();
				});
			}
		});
		btnSpeckle.setFont(new Font("Gadugi", Font.BOLD, 13));
		btnSpeckle.setBounds(87, 371, 154, 51);
		mainPane.add(btnSpeckle);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setFont(new Font("Gadugi", Font.BOLD, 13));
		btnHelp.setBounds(690, 11, 90, 35);
		mainPane.add(btnHelp);
		
		JLabel lblDetermineTheParameters = new JLabel("Determine the parameters for segmenting a new dataset.");
		lblDetermineTheParameters.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblDetermineTheParameters.setBounds(251, 176, 360, 35);
		mainPane.add(lblDetermineTheParameters);
		
		JLabel lblBatchProcessSpheroid = new JLabel("Batch process a dataset containing spheroids or organoids.");
		lblBatchProcessSpheroid.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBatchProcessSpheroid.setBounds(251, 254, 388, 35);
		mainPane.add(lblBatchProcessSpheroid);
		
		JLabel lblBatchProcessSingleCell = new JLabel("Batch process a dataset that contains primary objects and secondary objects.");
		lblBatchProcessSingleCell.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBatchProcessSingleCell.setBounds(251, 316, 489, 35);
		mainPane.add(lblBatchProcessSingleCell);
		
		JLabel lblBatchProcessSpeckle = new JLabel("Batch process a dataset that contains multiple secondary objects per primary object. ");
		lblBatchProcessSpeckle.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBatchProcessSpeckle.setBounds(251, 378, 522, 35);
		mainPane.add(lblBatchProcessSpeckle);
		
		JLabel lblMasterIcon = new JLabel("");
		lblMasterIcon.setIcon(new ImageIcon(MainScreen.class.getResource("/clcm/focust/resources/FullLogo3.png")));
		lblMasterIcon.setBounds(10, 0, 445, 114);
		mainPane.add(lblMasterIcon);
		
		JLabel lblQMarkIcon = new JLabel("");
		lblQMarkIcon.setIcon(new ImageIcon(MainScreen.class.getResource("/clcm/focust/resources/iconquestionmark.png")));
		lblQMarkIcon.setBounds(30, 161, 43, 67);
		mainPane.add(lblQMarkIcon);

	}


	void OptimizeGuiDisplay() {
		GenericDialog gd = new GenericDialog("Optimization Selector");
		String[] choices = {"Spheroid Analysis", "Single Cell Analysis", "Speckle Analysis"};
		gd.addRadioButtonGroup("Select a Mode to Optimize:", choices, 1, 3, null);
		gd.showDialog();
		
	}
	
	
}