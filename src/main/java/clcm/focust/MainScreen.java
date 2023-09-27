package clcm.focust;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import ij.gui.GenericDialog;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import net.miginfocom.swing.MigLayout;



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
		setBounds(100, 100, 899, 476);
		mainPane = new JPanel();
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mainPane);
		mainPane.setLayout(new BorderLayout(10, 10));
		
		JPanel pnlModeText = new JPanel();
		mainPane.add(pnlModeText, BorderLayout.EAST);
		pnlModeText.setLayout(new GridLayout(4, 0, 0, 0));
		
		JPanel pnlFooter = new JPanel(); 
		mainPane.add(pnlFooter, BorderLayout.SOUTH);
		
		JPanel pnlHeader = new JPanel();
		mainPane.add(pnlHeader, BorderLayout.NORTH);
		
		JLabel lblDetermineTheParameters = new JLabel("Determine the parameters for segmenting a new dataset.");
		lblDetermineTheParameters.setFont(new Font("Gadugi", Font.PLAIN, 14));
		pnlModeText.add(lblDetermineTheParameters);
		
		JLabel lblBatchProcessSpheroid = new JLabel("Batch process a dataset containing spheroids or organoids.");
		lblBatchProcessSpheroid.setFont(new Font("Gadugi", Font.PLAIN, 14));
		pnlModeText.add(lblBatchProcessSpheroid);
		
		JLabel lblBatchProcessSingleCell = new JLabel("Batch process a dataset that contains primary objects and secondary objects.");
		lblBatchProcessSingleCell.setFont(new Font("Gadugi", Font.PLAIN, 14));
		pnlModeText.add(lblBatchProcessSingleCell);
		
		JLabel lblBatchProcessSpeckle = new JLabel("Batch process a dataset that contains multiple secondary objects per primary object. ");
		lblBatchProcessSpeckle.setFont(new Font("Gadugi", Font.PLAIN, 14));
		pnlModeText.add(lblBatchProcessSpeckle, BorderLayout.EAST);
				pnlHeader.setLayout(new MigLayout("", "[580px][100px][100][150,right]", "[114px]"));
				
				JLabel lblMasterIcon = new JLabel("");
				lblMasterIcon.setHorizontalAlignment(SwingConstants.LEFT);
				lblMasterIcon.setIcon(new ImageIcon(MainScreen.class.getResource("/clcm/focust/resources/FullLogo3.png")));
				pnlHeader.add(lblMasterIcon, "cell 0 0,alignx left,growy");
				
				JLabel label = new JLabel("");
				pnlHeader.add(label, "cell 0 0,grow");
				
				JButton btnHelp = new JButton("Help");
				pnlHeader.add(btnHelp, "cell 3 0,growx,aligny top");
			
				btnHelp.setFont(new Font("Gadugi", Font.BOLD, 13));
		
		JPanel pnlModeSelect = new JPanel();
		mainPane.add(pnlModeSelect, BorderLayout.CENTER);
		pnlModeSelect.setLayout(new GridLayout(0, 1, 0, 10));
		
		btnOptimize = new JButton("Optimize");
		pnlModeSelect.add(btnOptimize);
		btnOptimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(() -> {
					OptimizeGUI optimizeGui = new OptimizeGUI();
					optimizeGui.setLocationRelativeTo(null);
					optimizeGui.setVisible(true);
					Window win = SwingUtilities.getWindowAncestor(pnlModeSelect);
					win.dispose();
				});
			}
		});
		btnOptimize.setFont(new Font("Gadugi", Font.BOLD, 13));
		
		JButton btnSpheroid_1 = new JButton("Spheroid Analysis");
		pnlModeSelect.add(btnSpheroid_1);
		btnSpheroid_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					
					// launch anaylsis with the spheroid analysis mode selected
					AnalysisGUI gui = new AnalysisGUI();
					gui.setMode(1);
					gui.setLocationRelativeTo(null);
					gui.setVisible(true);
					Window win = SwingUtilities.getWindowAncestor(pnlModeSelect);
					win.dispose();
				});
			}
		});
		btnSpheroid_1.setFont(new Font("Gadugi", Font.BOLD, 13));
		
		JButton btnSingleCell_1 = new JButton("Single Cell Analysis");
		pnlModeSelect.add(btnSingleCell_1);
		btnSingleCell_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					
					// launch anaylsis with the single cell analysis mode selected
					AnalysisGUI gui = new AnalysisGUI();
					gui.setMode(2);
					gui.setLocationRelativeTo(null);
					gui.setVisible(true);
					
					Window win = SwingUtilities.getWindowAncestor(pnlModeSelect);
					win.dispose();
					
				});
				
			}
		});
		btnSingleCell_1.setFont(new Font("Gadugi", Font.BOLD, 13));
		
		JButton btnSpeckle_1 = new JButton("Speckle Analysis");
		pnlModeSelect.add(btnSpeckle_1);
		btnSpeckle_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					
					// launch anaylsis with the speckle analysis mode selected
					AnalysisGUI gui = new AnalysisGUI();
					gui.setMode(3);
					gui.setLocationRelativeTo(null);
					gui.setVisible(true);
					
					Window win = SwingUtilities.getWindowAncestor(pnlModeSelect);
					win.dispose();
				});
			}
		});
		btnSpeckle_1.setFont(new Font("Gadugi", Font.BOLD, 13));
		
		JPanel pnlIconHolder = new JPanel();
		//mainPane.add(pnlIconHolder);
		mainPane.add(pnlIconHolder, BorderLayout.WEST);
		pnlIconHolder.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblQMarkIcon = new JLabel("");
		lblQMarkIcon.setHorizontalAlignment(SwingConstants.CENTER);
		pnlIconHolder.add(lblQMarkIcon);
		lblQMarkIcon.setIcon(new ImageIcon(MainScreen.class.getResource("/clcm/focust/resources/iconquestionmark.png")));
		
		JLabel lblSpheroidIcon = new JLabel("");
		lblSpheroidIcon.setHorizontalAlignment(SwingConstants.CENTER);
		pnlIconHolder.add(lblSpheroidIcon);
		lblSpheroidIcon.setIcon(new ImageIcon(MainScreen.class.getResource("/clcm/focust/resources/spheroidIcon0.2.png")));
		
		JLabel lblSingleCellIcon = new JLabel("");
		lblSingleCellIcon.setHorizontalAlignment(SwingConstants.CENTER);
		pnlIconHolder.add(lblSingleCellIcon);
		lblSingleCellIcon.setIcon(new ImageIcon(MainScreen.class.getResource("/clcm/focust/resources/singlecellicon.png")));
		
		JLabel lblSpeckleIcon = new JLabel("");
		lblSpeckleIcon.setHorizontalAlignment(SwingConstants.CENTER);
		pnlIconHolder.add(lblSpeckleIcon);
		lblSpeckleIcon.setIcon(new ImageIcon(MainScreen.class.getResource("/clcm/focust/resources/speckleIcon.png")));
		pnlFooter.setLayout(new BoxLayout(pnlFooter, BoxLayout.X_AXIS));
		
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					
					SpeckleView SpeckleGui = new SpeckleView();
					SpeckleGui.setVisible(true);
					SpeckleGUI speckleGUI = new SpeckleGUI();
					speckleGUI.setVisible(true);
					
					Window win = SwingUtilities.getWindowAncestor(pnlModeSelect);
					win.dispose();
				});
			}
		});
		
	}


	void OptimizeGuiDisplay() {
		GenericDialog gd = new GenericDialog("Optimization Selector");
		String[] choices = {"Spheroid Analysis", "Single Cell Analysis", "Speckle Analysis"};
		gd.addRadioButtonGroup("Select a Mode to Optimize:", choices, 1, 3, null);
		gd.showDialog();
		
	}
}