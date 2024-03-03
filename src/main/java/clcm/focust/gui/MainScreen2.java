package clcm.focust.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import clcm.focust.data.DataConstants;
import clcm.focust.data.DataMapManager;
import clcm.focust.data.DatumManager;
import clcm.focust.data.DatumUpdateService;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.speckle.SpecklesConfiguration;
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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;



public class MainScreen2 extends JFrame{
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
	public MainScreen2(DatumUpdateService<ParameterCollection> paramManager ) {
		setTitle("FOCUST");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainScreen2.class.getResource("/clcm/focust/resources/icon.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 557, 339);
		mainPane = new JPanel();

		setContentPane(mainPane);
		GridBagLayout gbl_mainPane = new GridBagLayout();
		gbl_mainPane.columnWidths = new int[]{272, 88, 83, 67, 0};
		gbl_mainPane.rowHeights = new int[]{135, 82, 0, 1, 0};
		gbl_mainPane.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_mainPane.rowWeights = new double[]{1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		mainPane.setLayout(gbl_mainPane);
		
		JPanel pnlHeader = new JPanel();
		GridBagConstraints gbc_pnlHeader = new GridBagConstraints();
		gbc_pnlHeader.anchor = GridBagConstraints.NORTH;
		gbc_pnlHeader.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlHeader.insets = new Insets(0, 0, 5, 0);
		gbc_pnlHeader.gridwidth = 4;
		gbc_pnlHeader.gridx = 0;
		gbc_pnlHeader.gridy = 0;
		mainPane.add(pnlHeader, gbc_pnlHeader);
		pnlHeader.setLayout(new MigLayout("", "[580px][100px][100][150,right]", "[114px]"));
		
				JLabel lblMasterIcon = new JLabel("");
				lblMasterIcon.setHorizontalAlignment(SwingConstants.LEFT);
				lblMasterIcon.setIcon(new ImageIcon(MainScreen2.class.getResource("/clcm/focust/resources/fullLogo.png")));
				pnlHeader.add(lblMasterIcon, "cell 0 0,alignx left,growy");
				
						JButton btnHelp = new JButton("Help");
						pnlHeader.add(btnHelp, "cell 3 0,growx,aligny top");
						
								btnHelp.setFont(new Font("Gadugi", Font.BOLD, 13));
								
								btnHelp.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										SwingUtilities.invokeLater(() -> {
											
											
										});
									}
								});
		
		btnOptimize = new JButton("Optimize");
		GridBagConstraints gbc_btnOptimize = new GridBagConstraints();
		gbc_btnOptimize.fill = GridBagConstraints.BOTH;
		gbc_btnOptimize.insets = new Insets(0, 0, 5, 5);
		gbc_btnOptimize.gridx = 0;
		gbc_btnOptimize.gridy = 1;
		mainPane.add(btnOptimize, gbc_btnOptimize);
		btnOptimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(() -> {
					OptimizeGUI optimizeGui = new OptimizeGUI(paramManager);
					optimizeGui.setLocationRelativeTo(null);
					optimizeGui.setVisible(true);
					Window win = SwingUtilities.getWindowAncestor(btnOptimize);
					win.dispose();
				});
			}
		});
		btnOptimize.setFont(new Font("Gadugi", Font.BOLD, 16));
		
		JButton btnAnalyze = new JButton("Analyze");
		GridBagConstraints gbc_btnAnalyze = new GridBagConstraints();
		gbc_btnAnalyze.gridwidth = 3;
		gbc_btnAnalyze.fill = GridBagConstraints.BOTH;
		gbc_btnAnalyze.insets = new Insets(0, 0, 5, 0);
		gbc_btnAnalyze.gridx = 1;
		gbc_btnAnalyze.gridy = 1;
		mainPane.add(btnAnalyze, gbc_btnAnalyze);
		btnAnalyze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					
					// launch anaylsis with the speckle analysis mode selected
					AnalysisGUI gui = new AnalysisGUI(paramManager);
					gui.setMode(0);
					gui.setLocationRelativeTo(null);
					gui.setVisible(true);
					
					Window win = SwingUtilities.getWindowAncestor(btnAnalyze);
					win.dispose();
				});
			}
		});
		btnAnalyze.setFont(new Font("Gadugi", Font.BOLD, 16));
		
		JLabel lblQMarkIcon = new JLabel("");
		GridBagConstraints gbc_lblQMarkIcon = new GridBagConstraints();
		gbc_lblQMarkIcon.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblQMarkIcon.insets = new Insets(0, 0, 5, 5);
		gbc_lblQMarkIcon.gridx = 0;
		gbc_lblQMarkIcon.gridy = 2;
		mainPane.add(lblQMarkIcon, gbc_lblQMarkIcon);
		lblQMarkIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblQMarkIcon.setIcon(new ImageIcon(MainScreen2.class.getResource("/clcm/focust/resources/iconquestionmark.png")));
		
		JLabel lblSpheroidIcon = new JLabel("");
		GridBagConstraints gbc_lblSpheroidIcon = new GridBagConstraints();
		gbc_lblSpheroidIcon.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpheroidIcon.gridx = 1;
		gbc_lblSpheroidIcon.gridy = 2;
		mainPane.add(lblSpheroidIcon, gbc_lblSpheroidIcon);
		lblSpheroidIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblSpheroidIcon.setIcon(new ImageIcon(MainScreen2.class.getResource("/clcm/focust/resources/spheroidIcon0.2.png")));
		
		JLabel lblSingleCellIcon = new JLabel("");
		GridBagConstraints gbc_lblSingleCellIcon = new GridBagConstraints();
		gbc_lblSingleCellIcon.insets = new Insets(0, 0, 5, 5);
		gbc_lblSingleCellIcon.gridx = 2;
		gbc_lblSingleCellIcon.gridy = 2;
		mainPane.add(lblSingleCellIcon, gbc_lblSingleCellIcon);
		lblSingleCellIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblSingleCellIcon.setIcon(new ImageIcon(MainScreen2.class.getResource("/clcm/focust/resources/singlecellicon.png")));
		
		JLabel lblSpeckleIcon = new JLabel("");
		GridBagConstraints gbc_lblSpeckleIcon = new GridBagConstraints();
		gbc_lblSpeckleIcon.insets = new Insets(0, 0, 5, 0);
		gbc_lblSpeckleIcon.gridx = 3;
		gbc_lblSpeckleIcon.gridy = 2;
		mainPane.add(lblSpeckleIcon, gbc_lblSpeckleIcon);
		lblSpeckleIcon.setHorizontalAlignment(SwingConstants.CENTER);
		lblSpeckleIcon.setIcon(new ImageIcon(MainScreen2.class.getResource("/clcm/focust/resources/speckleIcon.png")));
		
		JPanel pnlFooter = new JPanel(); 
		GridBagConstraints gbc_pnlFooter = new GridBagConstraints();
		gbc_pnlFooter.anchor = GridBagConstraints.NORTH;
		gbc_pnlFooter.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlFooter.gridwidth = 4;
		gbc_pnlFooter.gridx = 0;
		gbc_pnlFooter.gridy = 3;
		mainPane.add(pnlFooter, gbc_pnlFooter);
		pnlFooter.setLayout(new BoxLayout(pnlFooter, BoxLayout.X_AXIS));
		
	}


	void OptimizeGuiDisplay() {
		GenericDialog gd = new GenericDialog("Optimization Selector");
		String[] choices = {"Spheroid Analysis", "Single Cell Analysis", "Speckle Analysis"};
		gd.addRadioButtonGroup("Select a Mode to Optimize:", choices, 1, 3, null);
		gd.showDialog();
		
	}
}