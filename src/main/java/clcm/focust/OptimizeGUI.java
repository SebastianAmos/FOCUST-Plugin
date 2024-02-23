package clcm.focust;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ItemEvent;
import javax.swing.border.MatteBorder;

import clcm.focust.data.DatumUpdateService;
import clcm.focust.filter.BackgroundType;
import clcm.focust.filter.FilterType;
import clcm.focust.filter.Vector3D;
import clcm.focust.parameters.BackgroundParameters;
import clcm.focust.parameters.FilterParameters;
import clcm.focust.parameters.MethodParameters;
import clcm.focust.parameters.ObjectParameters;
import clcm.focust.parameters.ParameterCollection;
import clcm.focust.parameters.SkeletonParameters;
import clcm.focust.parameters.StratifyParameters;
import clcm.focust.segmentation.MethodTypes;
import clcm.focust.segmentation.Segmentation;
import clcm.focust.threshold.ThresholdType;
import ij.IJ;
import ij.ImagePlus;
import ij.plugin.ChannelSplitter;
import java.awt.Toolkit;


/**
 * Built with WindowsBuilder Editor
 * @author SebastianAmos
 *
 */

public class OptimizeGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtInputDir;
	private final ButtonGroup rbtnOutputDir = new ButtonGroup();
	private JTextField txtPriFilterX;
	private JTextField txtPriFilterY;
	private JTextField txtPriFilterZ;
	private JTextField txtPriSpotX;
	private JTextField txtPriSpotY;
	private JTextField txtPriSpotZ;
	private JTextField txtSecFilterX;
	private JTextField txtSecFilterY;
	private JTextField txtSecFilterZ;
	private JTextField txtSecondaryMethodX;
	private JTextField txtSecondaryMethodY;
	private JTextField txtSecondaryMethodZ;
	private JTextField txtSecondaryMethodThreshold;
	private JTextField txtTertFilterX;
	private JTextField txtTertFilterY;
	private JTextField txtTertFilterZ;
	private JTextField txtTertiaryMethodX;
	private JTextField txtTertiaryMethodY;
	private JTextField txtTertiaryMethodZ;
	private final ButtonGroup killBordersChoice = new ButtonGroup();
	private JTextField txtTertiaryMethodThreshold;
	private JTextField txtPrimaryS1X;
	private JTextField txtPrimaryS1Y;
	private JTextField txtPrimaryS1Z;
	private JTextField txtPrimaryS2X;
	private JTextField txtPrimaryS2Y;
	private JTextField txtPrimaryS2Z;
	private JTextField txtPrimaryBGRadius;
	private JTextField txtSecondaryS1X;
	private JTextField txtSecondaryS1Y;
	private JTextField txtSecondaryS1Z;
	private JTextField txtSecondaryS2X;
	private JTextField txtSecondaryS2Y;
	private JTextField txtSecondaryS2Z;
	private JTextField txtSecondaryBGRadius;
	private JTextField txtTertiaryBGRadius;
	private JTextField txtTertiaryS2X;
	private JTextField txtTertiaryS2Y;
	private JTextField txtTertiaryS2Z;
	private JTextField txtTertiaryS1X;
	private JTextField txtTertiaryS1Y;
	private JTextField txtTertiaryS1Z;
	private JTextField txtPrimaryClassiferDirectory;
	private JTextField txtPrimaryMethodThreshold;
	private JTextField txtPriFilter2X;
	private JTextField txtPriFilter2Y;
	private JTextField txtPriFilter2Z;
	private JTextField txtSecFilter2X;
	private JTextField txtSecFilter2Y;
	private JTextField txtSecFilter2Z;
	private JTextField txtSecondaryClassiferDirectory;
	private JTextField txtTertiaryClassiferDirectory;
	private JButton btnBrowseTertiaryClassifer;
	private JTextField txtTertFilter2X;
	private JTextField txtTertFilter2Y;
	private JTextField txtTertFilter2Z;
	private KillBorderTypes selectedKillBorderOption;
	
	public String inputDir;
	public ImagePlus currentImage;
	public int currentIndex;
	public JLabel ImageName = new JLabel("");

	
	public String[] list;
	public ImagePlus[] channelArray;
	
	// original images
	private ImagePlus primaryImg = null;
	private ImagePlus secondaryImg = null;
	private ImagePlus tertiaryImg = null;
	
	// gpu outputs
	private ImagePlus primaryOutput = null;
	private ImagePlus secondaryOutput = null;
	private ImagePlus tertiaryOutput = null;
	

	OptimizeHelpers optimize;
	

	
	//OptimizeHelpers optimize = new OptimizeHelpers();
	
	/**
	 * Create the frame.
	 */
	public OptimizeGUI(DatumUpdateService<ParameterCollection> paramManager) {
		
		optimize = new OptimizeHelpers();
		optimize.setOptimizeGUI(this);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(OptimizeGUI.class.getResource("/clcm/focust/resources/icon.png")));
		setTitle("FOCUST: Optimization");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1159, 628);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		GuiHelper guiHelper = new GuiHelper();
		
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{801, 0};
		gbl_contentPane.rowHeights = new int[]{100, 218, 76, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel pnlHeader = new JPanel();
		GridBagConstraints gbc_pnlHeader = new GridBagConstraints();
		gbc_pnlHeader.fill = GridBagConstraints.BOTH;
		gbc_pnlHeader.insets = new Insets(0, 0, 5, 0);
		gbc_pnlHeader.gridx = 0;
		gbc_pnlHeader.gridy = 0;
		contentPane.add(pnlHeader, gbc_pnlHeader);
		GridBagLayout gbl_pnlHeader = new GridBagLayout();
		gbl_pnlHeader.columnWidths = new int[]{0, 179, 86, 0, 0, 0, 305, 0, 0, 0, 0, 0, 0};
		gbl_pnlHeader.rowHeights = new int[]{0, 0, 19, 0};
		gbl_pnlHeader.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlHeader.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlHeader.setLayout(gbl_pnlHeader);
		
		JLabel lblNewLabel = new JLabel("Optimize a Dataset");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		pnlHeader.add(lblNewLabel, gbc_lblNewLabel);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JButton btnBrowseInput = new JButton("Browse");
		btnBrowseInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Find and set the input directory.
				inputDir = IJ.getDir("Select an Input Directory");
				String inputDirSt = inputDir.toString();
				txtInputDir.setText(inputDirSt);
				
				// make the file list and load the first image
				File f = new File(inputDir);
				list = f.list();
				String path = inputDir + list[0];
				ImagePlus imp = IJ.openImage(path);
				channelArray = ChannelSplitter.split(imp);
				ImageName.setText(list[0]);
			}
		});
		
		JLabel lblNewLabel_2 = new JLabel("Select an input directory:");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 0;
		pnlHeader.add(lblNewLabel_2, gbc_lblNewLabel_2);
		btnBrowseInput.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnBrowseInput = new GridBagConstraints();
		gbc_btnBrowseInput.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBrowseInput.insets = new Insets(0, 0, 5, 5);
		gbc_btnBrowseInput.gridx = 2;
		gbc_btnBrowseInput.gridy = 0;
		pnlHeader.add(btnBrowseInput, gbc_btnBrowseInput);
		
		txtInputDir = new JTextField();
		txtInputDir.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_txtInputDir = new GridBagConstraints();
		gbc_txtInputDir.gridwidth = 5;
		gbc_txtInputDir.fill = GridBagConstraints.BOTH;
		gbc_txtInputDir.anchor = GridBagConstraints.WEST;
		gbc_txtInputDir.insets = new Insets(0, 0, 5, 5);
		gbc_txtInputDir.gridx = 3;
		gbc_txtInputDir.gridy = 0;
		pnlHeader.add(txtInputDir, gbc_txtInputDir);
		txtInputDir.setColumns(10);
		
		
		
		btnHelp.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnHelp = new GridBagConstraints();
		gbc_btnHelp.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHelp.gridwidth = 4;
		gbc_btnHelp.insets = new Insets(5, 0, 5, 0);
		gbc_btnHelp.gridx = 8;
		gbc_btnHelp.gridy = 0;
		pnlHeader.add(btnHelp, gbc_btnHelp);
		
		String[] analysisOptions = {"None", "Spheroid", "Single Cells", "Speckles"};
		DefaultComboBoxModel<String> analysisModel = new DefaultComboBoxModel<String>(analysisOptions);
		
		JButton btnAnalysis = new JButton("Analysis");
		btnAnalysis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
			}
		});
		
		JLabel lblNewLabel_2_1 = new JLabel("Current image:");
		lblNewLabel_2_1.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_2_1 = new GridBagConstraints();
		gbc_lblNewLabel_2_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2_1.gridx = 0;
		gbc_lblNewLabel_2_1.gridy = 1;
		pnlHeader.add(lblNewLabel_2_1, gbc_lblNewLabel_2_1);
		

		ImageName.setHorizontalAlignment(SwingConstants.LEFT);
		ImageName.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ImageName = new GridBagConstraints();
		gbc_ImageName.anchor = GridBagConstraints.WEST;
		gbc_ImageName.gridwidth = 6;
		gbc_ImageName.insets = new Insets(0, 0, 5, 5);
		gbc_ImageName.gridx = 1;
		gbc_ImageName.gridy = 1;
		pnlHeader.add(ImageName, gbc_ImageName);
		
		btnAnalysis.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnAnalysis = new GridBagConstraints();
		gbc_btnAnalysis.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAnalysis.gridwidth = 4;
		gbc_btnAnalysis.insets = new Insets(0, 0, 5, 0);
		gbc_btnAnalysis.gridx = 8;
		gbc_btnAnalysis.gridy = 1;
		pnlHeader.add(btnAnalysis, gbc_btnAnalysis);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setForeground(new Color(169, 169, 169));
		separator_1_1.setBackground(Color.WHITE);
		GridBagConstraints gbc_separator_1_1 = new GridBagConstraints();
		gbc_separator_1_1.gridwidth = 12;
		gbc_separator_1_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_1_1.gridx = 0;
		gbc_separator_1_1.gridy = 2;
		pnlHeader.add(separator_1_1, gbc_separator_1_1);
		
		JPanel pnlMain = new JPanel();
		GridBagConstraints gbc_pnlMain = new GridBagConstraints();
		gbc_pnlMain.insets = new Insets(0, 0, 5, 0);
		gbc_pnlMain.fill = GridBagConstraints.BOTH;
		gbc_pnlMain.gridx = 0;
		gbc_pnlMain.gridy = 1;
		contentPane.add(pnlMain, gbc_pnlMain);
		pnlMain.setLayout(new GridLayout(0, 4, 3, 0));
		
		JPanel pnlVariable = new JPanel();
		pnlMain.add(pnlVariable);
		GridBagLayout gbl_pnlVariable = new GridBagLayout();
		gbl_pnlVariable.columnWidths = new int[]{129, 0, 0};
		gbl_pnlVariable.rowHeights = new int[] {35, 35, 35, 35, 35, 35, 30, 30, 0, 35, 0, 0};
		gbl_pnlVariable.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_pnlVariable.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlVariable.setLayout(gbl_pnlVariable);
		
		JButton btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				optimize.loadPrevious();
				
				// reset image outputs
				primaryImg = null;
				secondaryImg = null;
				tertiaryImg = null;

				primaryOutput = null;
				secondaryOutput = null;
				tertiaryOutput = null;
			}
		});
		GridBagConstraints gbc_btnPrevious = new GridBagConstraints();
		gbc_btnPrevious.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPrevious.insets = new Insets(0, 5, 5, 5);
		gbc_btnPrevious.gridx = 0;
		gbc_btnPrevious.gridy = 0;
		pnlVariable.add(btnPrevious, gbc_btnPrevious);
		btnPrevious.setFont(new Font("Arial", Font.BOLD, 14));
		
		JButton btnNext = new JButton("      Next      ");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				optimize.loadNext();
				
				// reset image outputs
				primaryImg = null;
				secondaryImg = null;
				tertiaryImg = null;
				
				primaryOutput = null;
				secondaryOutput = null;
				tertiaryOutput = null;
				
			}
		});
		btnNext.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNext.insets = new Insets(0, 0, 5, 0);
		gbc_btnNext.gridx = 1;
		gbc_btnNext.gridy = 0;
		pnlVariable.add(btnNext, gbc_btnNext);
		
		JLabel lblPrimaryOverlay = new JLabel("Primary Objects:");
		lblPrimaryOverlay.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_lblPrimaryOverlay = new GridBagConstraints();
		gbc_lblPrimaryOverlay.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblPrimaryOverlay.insets = new Insets(0, 5, 5, 5);
		gbc_lblPrimaryOverlay.gridx = 0;
		gbc_lblPrimaryOverlay.gridy = 1;
		pnlVariable.add(lblPrimaryOverlay, gbc_lblPrimaryOverlay);
		
		JCheckBox ckbPrimaryDisplay = new JCheckBox("Display original?");
		ckbPrimaryDisplay.setSelected(true);
		ckbPrimaryDisplay.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ckbPrimaryDisplay = new GridBagConstraints();
		gbc_ckbPrimaryDisplay.fill = GridBagConstraints.HORIZONTAL;
		gbc_ckbPrimaryDisplay.insets = new Insets(0, 5, 5, 5);
		gbc_ckbPrimaryDisplay.gridx = 0;
		gbc_ckbPrimaryDisplay.gridy = 2;
		pnlVariable.add(ckbPrimaryDisplay, gbc_ckbPrimaryDisplay);
		
		JCheckBox ckbPrimaryDisplayOverlay = new JCheckBox("With overlay?");
		ckbPrimaryDisplayOverlay.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ckbPrimaryDisplayOverlay = new GridBagConstraints();
		gbc_ckbPrimaryDisplayOverlay.insets = new Insets(0, 0, 5, 0);
		gbc_ckbPrimaryDisplayOverlay.gridx = 1;
		gbc_ckbPrimaryDisplayOverlay.gridy = 2;
		pnlVariable.add(ckbPrimaryDisplayOverlay, gbc_ckbPrimaryDisplayOverlay);
		
		JSeparator horizontalSeparator_1 = new JSeparator();
		horizontalSeparator_1.setForeground(new Color(169, 169, 169));
		horizontalSeparator_1.setBackground(Color.WHITE);
		GridBagConstraints gbc_horizontalSeparator_1 = new GridBagConstraints();
		gbc_horizontalSeparator_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_horizontalSeparator_1.gridwidth = 2;
		gbc_horizontalSeparator_1.insets = new Insets(0, 5, 5, 5);
		gbc_horizontalSeparator_1.gridx = 0;
		gbc_horizontalSeparator_1.gridy = 3;
		pnlVariable.add(horizontalSeparator_1, gbc_horizontalSeparator_1);
		
		JLabel lblSecondaryOverlay = new JLabel("Secondary Objects:");
		lblSecondaryOverlay.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_lblSecondaryOverlay = new GridBagConstraints();
		gbc_lblSecondaryOverlay.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSecondaryOverlay.insets = new Insets(0, 5, 5, 5);
		gbc_lblSecondaryOverlay.gridx = 0;
		gbc_lblSecondaryOverlay.gridy = 4;
		pnlVariable.add(lblSecondaryOverlay, gbc_lblSecondaryOverlay);
		
		JCheckBox ckbSecondaryDisplay = new JCheckBox("Display original?");
		ckbSecondaryDisplay.setSelected(true);
		ckbSecondaryDisplay.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ckbSecondaryDisplay = new GridBagConstraints();
		gbc_ckbSecondaryDisplay.insets = new Insets(0, 0, 5, 5);
		gbc_ckbSecondaryDisplay.gridx = 0;
		gbc_ckbSecondaryDisplay.gridy = 5;
		pnlVariable.add(ckbSecondaryDisplay, gbc_ckbSecondaryDisplay);
		
		JCheckBox ckbSecondaryDisplayOverlay = new JCheckBox("With overlay?");
		ckbSecondaryDisplayOverlay.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ckbSecondaryDisplayOverlay = new GridBagConstraints();
		gbc_ckbSecondaryDisplayOverlay.insets = new Insets(0, 0, 5, 0);
		gbc_ckbSecondaryDisplayOverlay.gridx = 1;
		gbc_ckbSecondaryDisplayOverlay.gridy = 5;
		pnlVariable.add(ckbSecondaryDisplayOverlay, gbc_ckbSecondaryDisplayOverlay);
		
		JSeparator horizontalSeparator_1_1 = new JSeparator();
		horizontalSeparator_1_1.setForeground(new Color(169, 169, 169));
		horizontalSeparator_1_1.setBackground(Color.WHITE);
		GridBagConstraints gbc_horizontalSeparator_1_1 = new GridBagConstraints();
		gbc_horizontalSeparator_1_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_horizontalSeparator_1_1.gridwidth = 2;
		gbc_horizontalSeparator_1_1.insets = new Insets(0, 5, 5, 5);
		gbc_horizontalSeparator_1_1.gridx = 0;
		gbc_horizontalSeparator_1_1.gridy = 6;
		pnlVariable.add(horizontalSeparator_1_1, gbc_horizontalSeparator_1_1);
		
		JLabel lblTertiaryOverlay = new JLabel("Tertiary Objects:");
		lblTertiaryOverlay.setEnabled(false);
		lblTertiaryOverlay.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_lblTertiaryOverlay = new GridBagConstraints();
		gbc_lblTertiaryOverlay.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTertiaryOverlay.insets = new Insets(0, 5, 5, 5);
		gbc_lblTertiaryOverlay.gridx = 0;
		gbc_lblTertiaryOverlay.gridy = 7;
		pnlVariable.add(lblTertiaryOverlay, gbc_lblTertiaryOverlay);
		
		JCheckBox ckbTertiaryDisplay = new JCheckBox("Display original?");
		ckbTertiaryDisplay.setEnabled(false);
		ckbTertiaryDisplay.setSelected(true);
		ckbTertiaryDisplay.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ckbTertiaryDisplay = new GridBagConstraints();
		gbc_ckbTertiaryDisplay.insets = new Insets(0, 0, 5, 5);
		gbc_ckbTertiaryDisplay.gridx = 0;
		gbc_ckbTertiaryDisplay.gridy = 8;
		pnlVariable.add(ckbTertiaryDisplay, gbc_ckbTertiaryDisplay);
		
		JCheckBox ckbTertiaryDisplayOverlay = new JCheckBox("With overlay?");
		ckbTertiaryDisplayOverlay.setEnabled(false);
		ckbTertiaryDisplayOverlay.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ckbTertiaryDisplayOverlay = new GridBagConstraints();
		gbc_ckbTertiaryDisplayOverlay.insets = new Insets(0, 0, 5, 0);
		gbc_ckbTertiaryDisplayOverlay.gridx = 1;
		gbc_ckbTertiaryDisplayOverlay.gridy = 8;
		pnlVariable.add(ckbTertiaryDisplayOverlay, gbc_ckbTertiaryDisplayOverlay);
		
		JSeparator horizontalSeparator_1_1_1 = new JSeparator();
		horizontalSeparator_1_1_1.setForeground(new Color(169, 169, 169));
		horizontalSeparator_1_1_1.setBackground(Color.WHITE);
		GridBagConstraints gbc_horizontalSeparator_1_1_1 = new GridBagConstraints();
		gbc_horizontalSeparator_1_1_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_horizontalSeparator_1_1_1.gridwidth = 2;
		gbc_horizontalSeparator_1_1_1.insets = new Insets(0, 5, 5, 5);
		gbc_horizontalSeparator_1_1_1.gridx = 0;
		gbc_horizontalSeparator_1_1_1.gridy = 9;
		pnlVariable.add(horizontalSeparator_1_1_1, gbc_horizontalSeparator_1_1_1);
		
		JPanel pnlKillBorders = new JPanel();
		pnlKillBorders.setToolTipText("Do you want to remove objects touching the X + Y or X + Y+ Z borders?");
		GridBagConstraints gbc_pnlKillBorders = new GridBagConstraints();
		gbc_pnlKillBorders.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlKillBorders.anchor = GridBagConstraints.NORTH;
		gbc_pnlKillBorders.gridwidth = 2;
		gbc_pnlKillBorders.gridx = 0;
		gbc_pnlKillBorders.gridy = 10;
		pnlVariable.add(pnlKillBorders, gbc_pnlKillBorders);
		
		JLabel lblNewLabel_7 = new JLabel("Kill borders?");
		lblNewLabel_7.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlKillBorders.add(lblNewLabel_7);
		
		// init as no
		selectedKillBorderOption = KillBorderTypes.NO;
		
		for(KillBorderTypes type : KillBorderTypes.values()) {
			JRadioButton btn = new JRadioButton(type.toString());
			btn.setFont(new Font("Arial", Font.PLAIN, 13));
			btn.setActionCommand(type.name());
			btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedKillBorderOption = KillBorderTypes.valueOf(e.getActionCommand());
                    //System.out.println("Selected border mode: " + selectedKillBorderOption.toString());
                }
            });
			killBordersChoice.add(btn);
			if(type == KillBorderTypes.NO) {
				btn.setSelected(true);
			}
			pnlKillBorders.add(btn);
		}
		
		
		JPanel pnlPrimary = new JPanel();
		pnlPrimary.setBorder(new MatteBorder(0, 1, 0, 1, (Color) new Color(169, 169, 169)));
		pnlMain.add(pnlPrimary);
		GridBagLayout gbl_pnlPrimary = new GridBagLayout();
		gbl_pnlPrimary.columnWidths = new int[]{0, 0, 0};
		gbl_pnlPrimary.rowHeights = new int[] {30, 0, 0, 0, 0, 30, 0, 0, 35, 0, 30, 0, 0};
		gbl_pnlPrimary.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_pnlPrimary.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlPrimary.setLayout(gbl_pnlPrimary);
		
		JLabel lblNewLabel_4 = new JLabel("Primary Objects");
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.gridwidth = 2;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 0;
		pnlPrimary.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Channel:");
		lblNewLabel_5.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5.gridx = 0;
		gbc_lblNewLabel_5.gridy = 1;
		pnlPrimary.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		JComboBox cbPrimaryChannel = new JComboBox();
		cbPrimaryChannel.setFont(new Font("Arial", Font.PLAIN, 14));
		cbPrimaryChannel.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbPrimaryChannel.setSelectedIndex(0);
		GridBagConstraints gbc_cbPrimaryChannel = new GridBagConstraints();
		gbc_cbPrimaryChannel.insets = new Insets(0, 0, 5, 0);
		gbc_cbPrimaryChannel.anchor = GridBagConstraints.WEST;
		gbc_cbPrimaryChannel.gridx = 1;
		gbc_cbPrimaryChannel.gridy = 1;
		pnlPrimary.add(cbPrimaryChannel, gbc_cbPrimaryChannel);
		
		JLabel lblNewLabel_5_3_1 = new JLabel("Background:");
		lblNewLabel_5_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_5_3_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_3_1 = new GridBagConstraints();
		gbc_lblNewLabel_5_3_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_3_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_3_1.gridx = 0;
		gbc_lblNewLabel_5_3_1.gridy = 2;
		pnlPrimary.add(lblNewLabel_5_3_1, gbc_lblNewLabel_5_3_1);
		
		JComboBox<BackgroundType> cbPrimaryBackground = new JComboBox<>();
		cbPrimaryBackground.setModel(new DefaultComboBoxModel<>(BackgroundType.values()));
		cbPrimaryBackground.setSelectedIndex(0);
		cbPrimaryBackground.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbPrimaryBackground = new GridBagConstraints();
		gbc_cbPrimaryBackground.insets = new Insets(0, 0, 5, 5);
		gbc_cbPrimaryBackground.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbPrimaryBackground.gridx = 1;
		gbc_cbPrimaryBackground.gridy = 2;
		pnlPrimary.add(cbPrimaryBackground, gbc_cbPrimaryBackground);
		
		JPanel pnlPrimaryBGFirstBlur = new JPanel();
		GridBagConstraints gbc_pnlPrimaryBGFirstBlur = new GridBagConstraints();
		gbc_pnlPrimaryBGFirstBlur.anchor = GridBagConstraints.EAST;
		gbc_pnlPrimaryBGFirstBlur.gridwidth = 2;
		gbc_pnlPrimaryBGFirstBlur.insets = new Insets(0, 0, 5, 0);
		gbc_pnlPrimaryBGFirstBlur.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimaryBGFirstBlur.gridx = 0;
		gbc_pnlPrimaryBGFirstBlur.gridy = 3;
		pnlPrimary.add(pnlPrimaryBGFirstBlur, gbc_pnlPrimaryBGFirstBlur);
		pnlPrimaryBGFirstBlur.setVisible(false);
		
		JLabel lblNewLabel_7_1_1 = new JLabel("S1:");
		lblNewLabel_7_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGFirstBlur.add(lblNewLabel_7_1_1);
		
		JLabel lblNewLabel_6_6 = new JLabel("X");
		lblNewLabel_6_6.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_6.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_6.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGFirstBlur.add(lblNewLabel_6_6);
		
		txtPrimaryS1X = new JTextField();
		txtPrimaryS1X.setText("0");
		txtPrimaryS1X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryS1X.setColumns(4);
		pnlPrimaryBGFirstBlur.add(txtPrimaryS1X);
		
		JLabel lblNewLabel_6_1_4 = new JLabel("Y");
		lblNewLabel_6_1_4.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_4.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGFirstBlur.add(lblNewLabel_6_1_4);
		
		txtPrimaryS1Y = new JTextField();
		txtPrimaryS1Y.setText("0");
		txtPrimaryS1Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryS1Y.setColumns(4);
		pnlPrimaryBGFirstBlur.add(txtPrimaryS1Y);
		
		JLabel lblNewLabel_6_2_4 = new JLabel("Z");
		lblNewLabel_6_2_4.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_4.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGFirstBlur.add(lblNewLabel_6_2_4);
		
		txtPrimaryS1Z = new JTextField();
		txtPrimaryS1Z.setText("0");
		txtPrimaryS1Z.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryS1Z.setColumns(4);
		pnlPrimaryBGFirstBlur.add(txtPrimaryS1Z);
		
		JPanel pnlPrimaryBGSecondBlur = new JPanel();
		GridBagConstraints gbc_pnlPrimaryBGSecondBlur = new GridBagConstraints();
		gbc_pnlPrimaryBGSecondBlur.anchor = GridBagConstraints.EAST;
		gbc_pnlPrimaryBGSecondBlur.gridwidth = 2;
		gbc_pnlPrimaryBGSecondBlur.insets = new Insets(0, 0, 5, 0);
		gbc_pnlPrimaryBGSecondBlur.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimaryBGSecondBlur.gridx = 0;
		gbc_pnlPrimaryBGSecondBlur.gridy = 4;
		pnlPrimary.add(pnlPrimaryBGSecondBlur, gbc_pnlPrimaryBGSecondBlur);
		pnlPrimaryBGSecondBlur.setVisible(false);
		
		JLabel lblNewLabel_7_2_1 = new JLabel("S2:");
		lblNewLabel_7_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGSecondBlur.add(lblNewLabel_7_2_1);
		
		JLabel lblNewLabel_6_6_1 = new JLabel("X");
		lblNewLabel_6_6_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_6_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_6_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGSecondBlur.add(lblNewLabel_6_6_1);
		
		txtPrimaryS2X = new JTextField();
		txtPrimaryS2X.setText("0");
		txtPrimaryS2X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryS2X.setColumns(4);
		pnlPrimaryBGSecondBlur.add(txtPrimaryS2X);
		
		JLabel lblNewLabel_6_1_4_1 = new JLabel("Y");
		lblNewLabel_6_1_4_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_4_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_4_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGSecondBlur.add(lblNewLabel_6_1_4_1);
		
		txtPrimaryS2Y = new JTextField();
		txtPrimaryS2Y.setText("0");
		txtPrimaryS2Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryS2Y.setColumns(4);
		pnlPrimaryBGSecondBlur.add(txtPrimaryS2Y);
		
		JLabel lblNewLabel_6_2_4_1 = new JLabel("Z");
		lblNewLabel_6_2_4_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_4_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_4_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGSecondBlur.add(lblNewLabel_6_2_4_1);
		
		txtPrimaryS2Z = new JTextField();
		txtPrimaryS2Z.setText("0");
		txtPrimaryS2Z.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryS2Z.setColumns(4);
		pnlPrimaryBGSecondBlur.add(txtPrimaryS2Z);
		
		JLabel lblNewLabel_5_1_1 = new JLabel("Filter:");
		lblNewLabel_5_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_5_1_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_1_1.gridx = 0;
		gbc_lblNewLabel_5_1_1.gridy = 5;
		pnlPrimary.add(lblNewLabel_5_1_1, gbc_lblNewLabel_5_1_1);
		
		JComboBox<FilterType> cbPrimaryFilter = new JComboBox<>();
		cbPrimaryFilter.setModel(new DefaultComboBoxModel<>(FilterType.values()));
		cbPrimaryFilter.setSelectedIndex(0);
		cbPrimaryFilter.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbPrimaryFilter = new GridBagConstraints();
		gbc_cbPrimaryFilter.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbPrimaryFilter.insets = new Insets(0, 0, 5, 5);
		gbc_cbPrimaryFilter.gridx = 1;
		gbc_cbPrimaryFilter.gridy = 5;
		pnlPrimary.add(cbPrimaryFilter, gbc_cbPrimaryFilter);
		
		JPanel pnlPrimaryFirstBlur = new JPanel();
		GridBagConstraints gbc_pnlPrimaryFirstBlur = new GridBagConstraints();
		gbc_pnlPrimaryFirstBlur.anchor = GridBagConstraints.EAST;
		gbc_pnlPrimaryFirstBlur.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimaryFirstBlur.insets = new Insets(0, 0, 5, 0);
		gbc_pnlPrimaryFirstBlur.gridwidth = 2;
		gbc_pnlPrimaryFirstBlur.gridx = 0;
		gbc_pnlPrimaryFirstBlur.gridy = 6;
		pnlPrimary.add(pnlPrimaryFirstBlur, gbc_pnlPrimaryFirstBlur);
		pnlPrimaryFirstBlur.setVisible(false);
		
		JLabel lblNewLabel_7_1_1_1 = new JLabel("S1:");
		lblNewLabel_7_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryFirstBlur.add(lblNewLabel_7_1_1_1);
		
		JLabel lblNewLabel_6 = new JLabel("X");
		lblNewLabel_6.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6.setVerticalAlignment(SwingConstants.TOP);
		pnlPrimaryFirstBlur.add(lblNewLabel_6);
		
		txtPriFilterX = new JTextField();
		txtPriFilterX.setText("0");
		txtPriFilterX.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPriFilterX.setColumns(4);
		pnlPrimaryFirstBlur.add(txtPriFilterX);
		
		JLabel lblNewLabel_6_1 = new JLabel("Y");
		lblNewLabel_6_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_6_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1.setHorizontalAlignment(SwingConstants.LEFT);
		pnlPrimaryFirstBlur.add(lblNewLabel_6_1);
		
		txtPriFilterY = new JTextField();
		txtPriFilterY.setText("0");
		txtPriFilterY.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPriFilterY.setColumns(4);
		pnlPrimaryFirstBlur.add(txtPriFilterY);
		
		JLabel lblNewLabel_6_2 = new JLabel("Z");
		lblNewLabel_6_2.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_6_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2.setHorizontalAlignment(SwingConstants.LEFT);
		pnlPrimaryFirstBlur.add(lblNewLabel_6_2);
		
		txtPriFilterZ = new JTextField();
		txtPriFilterZ.setText("0");
		txtPriFilterZ.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPriFilterZ.setColumns(4);
		pnlPrimaryFirstBlur.add(txtPriFilterZ);
		
		JPanel pnlPrimarySecondBlur = new JPanel();
		GridBagConstraints gbc_pnlPrimarySecondBlur = new GridBagConstraints();
		gbc_pnlPrimarySecondBlur.anchor = GridBagConstraints.EAST;
		gbc_pnlPrimarySecondBlur.gridwidth = 2;
		gbc_pnlPrimarySecondBlur.insets = new Insets(0, 0, 5, 0);
		gbc_pnlPrimarySecondBlur.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimarySecondBlur.gridx = 0;
		gbc_pnlPrimarySecondBlur.gridy = 7;
		pnlPrimary.add(pnlPrimarySecondBlur, gbc_pnlPrimarySecondBlur);
		pnlPrimarySecondBlur.setVisible(false);
		
		JLabel lblNewLabel_7_1_1_2 = new JLabel("S2:");
		lblNewLabel_7_1_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimarySecondBlur.add(lblNewLabel_7_1_1_2);
		
		JLabel lblNewLabel_6_7 = new JLabel("X");
		lblNewLabel_6_7.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_7.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_7.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimarySecondBlur.add(lblNewLabel_6_7);
		
		txtPriFilter2X = new JTextField();
		txtPriFilter2X.setText("0");
		txtPriFilter2X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPriFilter2X.setColumns(4);
		pnlPrimarySecondBlur.add(txtPriFilter2X);
		
		JLabel lblNewLabel_6_1_5 = new JLabel("Y");
		lblNewLabel_6_1_5.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_5.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_5.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimarySecondBlur.add(lblNewLabel_6_1_5);
		
		txtPriFilter2Y = new JTextField();
		txtPriFilter2Y.setText("0");
		txtPriFilter2Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPriFilter2Y.setColumns(4);
		pnlPrimarySecondBlur.add(txtPriFilter2Y);
		
		JLabel lblNewLabel_6_2_5 = new JLabel("Z");
		lblNewLabel_6_2_5.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_5.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_5.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimarySecondBlur.add(lblNewLabel_6_2_5);
		
		txtPriFilter2Z = new JTextField();
		txtPriFilter2Z.setText("0");
		txtPriFilter2Z.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPriFilter2Z.setColumns(4);
		pnlPrimarySecondBlur.add(txtPriFilter2Z);
		
		JLabel lblNewLabel_5_1 = new JLabel("Method:");
		lblNewLabel_5_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_1 = new GridBagConstraints();
		gbc_lblNewLabel_5_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_1.gridx = 0;
		gbc_lblNewLabel_5_1.gridy = 8;
		pnlPrimary.add(lblNewLabel_5_1, gbc_lblNewLabel_5_1);
		
		JComboBox<MethodTypes> cbPrimaryMethod = new JComboBox<>();
		cbPrimaryMethod.setModel(new DefaultComboBoxModel<>(MethodTypes.values()));
		cbPrimaryMethod.setSelectedIndex(0);
		cbPrimaryMethod.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbPrimaryMethod = new GridBagConstraints();
		gbc_cbPrimaryMethod.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbPrimaryMethod.insets = new Insets(0, 0, 5, 5);
		gbc_cbPrimaryMethod.gridx = 1;
		gbc_cbPrimaryMethod.gridy = 8;
		pnlPrimary.add(cbPrimaryMethod, gbc_cbPrimaryMethod);
		
		JPanel pnlPrimarySpotSize = new JPanel();
		GridBagConstraints gbc_pnlPrimarySpotSize = new GridBagConstraints();
		gbc_pnlPrimarySpotSize.anchor = GridBagConstraints.EAST;
		gbc_pnlPrimarySpotSize.insets = new Insets(0, 0, 5, 0);
		gbc_pnlPrimarySpotSize.gridwidth = 2;
		gbc_pnlPrimarySpotSize.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimarySpotSize.gridx = 0;
		gbc_pnlPrimarySpotSize.gridy = 9;
		pnlPrimary.add(pnlPrimarySpotSize, gbc_pnlPrimarySpotSize);
		
		JLabel lblNewLabel_6_3 = new JLabel("X");
		lblNewLabel_6_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimarySpotSize.add(lblNewLabel_6_3);
		
		txtPriSpotX = new JTextField();
		txtPriSpotX.setText("0");
		txtPriSpotX.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPriSpotX.setColumns(4);
		pnlPrimarySpotSize.add(txtPriSpotX);
		
		JLabel lblNewLabel_6_1_1 = new JLabel("Y");
		lblNewLabel_6_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimarySpotSize.add(lblNewLabel_6_1_1);
		
		txtPriSpotY = new JTextField();
		txtPriSpotY.setText("0");
		txtPriSpotY.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPriSpotY.setColumns(4);
		pnlPrimarySpotSize.add(txtPriSpotY);
		
		JLabel lblNewLabel_6_2_1 = new JLabel("Z");
		lblNewLabel_6_2_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimarySpotSize.add(lblNewLabel_6_2_1);
		
		txtPriSpotZ = new JTextField();
		txtPriSpotZ.setText("0");
		txtPriSpotZ.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPriSpotZ.setColumns(4);
		pnlPrimarySpotSize.add(txtPriSpotZ);
		
		JPanel pnlPrimaryBGRadius = new JPanel();
		GridBagConstraints gbc_pnlPrimaryBGRadius = new GridBagConstraints();
		gbc_pnlPrimaryBGRadius.insets = new Insets(0, 0, 5, 0);
		gbc_pnlPrimaryBGRadius.anchor = GridBagConstraints.EAST;
		gbc_pnlPrimaryBGRadius.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimaryBGRadius.gridx = 1;
		gbc_pnlPrimaryBGRadius.gridy = 3;
		pnlPrimary.add(pnlPrimaryBGRadius, gbc_pnlPrimaryBGRadius);
		pnlPrimaryBGRadius.setVisible(false);
		
		JLabel lblNewLabel_6_3_1_1_2 = new JLabel("Radius:");
		lblNewLabel_6_3_1_1_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_3_1_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_3_1_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGRadius.add(lblNewLabel_6_3_1_1_2);
		
		txtPrimaryBGRadius = new JTextField();
		txtPrimaryBGRadius.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryBGRadius.setColumns(6);
		pnlPrimaryBGRadius.add(txtPrimaryBGRadius);
		
		JButton btnBrowsePrimaryClassifer = new JButton("Load");
		btnBrowsePrimaryClassifer.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnBrowsePrimaryClassifer = new GridBagConstraints();
		gbc_btnBrowsePrimaryClassifer.anchor = GridBagConstraints.EAST;
		gbc_btnBrowsePrimaryClassifer.insets = new Insets(0, 5, 5, 5);
		gbc_btnBrowsePrimaryClassifer.gridx = 0;
		gbc_btnBrowsePrimaryClassifer.gridy = 9;
		pnlPrimary.add(btnBrowsePrimaryClassifer, gbc_btnBrowsePrimaryClassifer);
		btnBrowsePrimaryClassifer.setVisible(false);
		
		txtPrimaryClassiferDirectory = new JTextField();
		txtPrimaryClassiferDirectory.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryClassiferDirectory.setColumns(10);
		GridBagConstraints gbc_txtPrimaryClassiferDirectory = new GridBagConstraints();
		gbc_txtPrimaryClassiferDirectory.insets = new Insets(0, 0, 5, 0);
		gbc_txtPrimaryClassiferDirectory.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrimaryClassiferDirectory.gridx = 1;
		gbc_txtPrimaryClassiferDirectory.gridy = 9;
		pnlPrimary.add(txtPrimaryClassiferDirectory, gbc_txtPrimaryClassiferDirectory);
		
		JComboBox<ThresholdType> cbPrimaryMethodThreshold = new JComboBox<>();
		cbPrimaryMethodThreshold.setModel(new DefaultComboBoxModel<>(ThresholdType.values()));
		cbPrimaryMethodThreshold.setSelectedIndex(0);
		cbPrimaryMethodThreshold.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbPrimaryMethodThreshold = new GridBagConstraints();
		gbc_cbPrimaryMethodThreshold.anchor = GridBagConstraints.WEST;
		gbc_cbPrimaryMethodThreshold.insets = new Insets(0, 5, 5, 5);
		gbc_cbPrimaryMethodThreshold.gridx = 0;
		gbc_cbPrimaryMethodThreshold.gridy = 10;
		pnlPrimary.add(cbPrimaryMethodThreshold, gbc_cbPrimaryMethodThreshold);
		
		JPanel pnlPrimaryMethodThreshold = new JPanel();
		GridBagConstraints gbc_pnlPrimaryMethodThreshold = new GridBagConstraints();
		gbc_pnlPrimaryMethodThreshold.insets = new Insets(0, 0, 5, 0);
		gbc_pnlPrimaryMethodThreshold.anchor = GridBagConstraints.EAST;
		gbc_pnlPrimaryMethodThreshold.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimaryMethodThreshold.gridx = 1;
		gbc_pnlPrimaryMethodThreshold.gridy = 10;
		pnlPrimary.add(pnlPrimaryMethodThreshold, gbc_pnlPrimaryMethodThreshold);
		pnlPrimaryMethodThreshold.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel_6_3_1_1_3 = new JLabel("Threshold:");
		pnlPrimaryMethodThreshold.add(lblNewLabel_6_3_1_1_3);
		lblNewLabel_6_3_1_1_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_3_1_1_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_3_1_1_3.setFont(new Font("Arial", Font.PLAIN, 14));
		
		txtPrimaryMethodThreshold = new JTextField();
		txtPrimaryMethodThreshold.setText("0");
		txtPrimaryMethodThreshold.setEnabled(false);
		pnlPrimaryMethodThreshold.add(txtPrimaryMethodThreshold);
		txtPrimaryMethodThreshold.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryMethodThreshold.setColumns(6);

		JButton btnProcessPrimary = new JButton("Process");
		
		OptimizeHelpers optimize = new OptimizeHelpers();
		
		btnProcessPrimary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Get display options
				Boolean displayOriginal = ckbPrimaryDisplay.isSelected();
				Boolean withOverlay = ckbPrimaryDisplayOverlay.isSelected();

				
				
				// Build the primary parameter object
				ObjectParameters primaryParams = ObjectParameters.builder().
						channel(cbPrimaryChannel.getSelectedIndex()).
						backgroundParameters(
								BackgroundParameters.builder().
										backgroundType((BackgroundType) cbPrimaryBackground.getSelectedItem()).
										sigma1(Vector3D.builder().x(Double.parseDouble(txtPrimaryS1X.getText())).y(Double.parseDouble(txtPrimaryS1Y.getText())).z(Double.parseDouble(txtPrimaryS1Z.getText())).build()).
										sigma2(Vector3D.builder().x(Double.parseDouble(txtPrimaryS2X.getText())).y(Double.parseDouble(txtPrimaryS2Y.getText())).z(Double.parseDouble(txtPrimaryS2Z.getText())).build()).
										build()
						).
						filterParameters(
								FilterParameters.builder().
										filterType((FilterType) cbPrimaryFilter.getSelectedItem()).
										sigma1(Vector3D.builder().x(Double.parseDouble(txtPriFilterX.getText())).y(Double.parseDouble(txtPriFilterY.getText())).z(Double.parseDouble(txtPriFilterZ.getText())).build()).
										sigma2(Vector3D.builder().x(Double.parseDouble(txtPriFilter2X.getText())).y(Double.parseDouble(txtPriFilter2Y.getText())).z(Double.parseDouble(txtPriFilter2Z.getText())).build()).
										build()
						).
						methodParameters(
								MethodParameters.builder().
										methodType((MethodTypes) cbPrimaryMethod.getSelectedItem()).
										sigma(Vector3D.builder().x(Double.parseDouble(txtPriSpotX.getText())).y(Double.parseDouble(txtPriSpotY.getText())).z(Double.parseDouble(txtPriSpotZ.getText())).build()).
										thresholdSize(Double.parseDouble(txtPrimaryMethodThreshold.getText())).
										classifierFilename(txtPrimaryClassiferDirectory.getText()).
										thresholdType((ThresholdType) cbPrimaryMethodThreshold.getSelectedItem()).
										build()
						).
						build();
				
				ParameterCollection parameterCollection = ParameterCollection.builder().
						killBorderType(selectedKillBorderOption).
						build();
				
				primaryImg = channelArray[primaryParams.getChannel()];	
				
				try {
					ImagePlus primaryDuplicate = primaryImg.duplicate();
					
					primaryOutput = Segmentation.run(primaryDuplicate, primaryParams, parameterCollection);
					IJ.resetMinAndMax(primaryOutput);
					primaryOutput.setTitle("Primary Objects");
					
					if (displayOriginal) {
						ImagePlus outputDuplicate = primaryOutput.duplicate();
						ImagePlus primaryDisplayOverlay = optimize.processDisplay(primaryDuplicate, outputDuplicate, withOverlay);
						primaryDisplayOverlay.setTitle("Primary Display");
						primaryDisplayOverlay.show();
					}
					
					primaryOutput.show();
					IJ.run("Tile", ""); // Arranges the windows so all visible images can be seen on the screen at once.

				} catch (Exception e1) {
					IJ.showMessage("No image to process. Select a directory and try again. Ensure your images have the required number of channels.");
					e1.printStackTrace();
				}

			}
		});
		
		btnProcessPrimary.setToolTipText("Run primary object segmentation");
		btnProcessPrimary.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnProcessPrimary = new GridBagConstraints();
		gbc_btnProcessPrimary.insets = new Insets(0, 0, 0, 5);
		gbc_btnProcessPrimary.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnProcessPrimary.gridx = 1;
		gbc_btnProcessPrimary.gridy = 11;
		pnlPrimary.add(btnProcessPrimary, gbc_btnProcessPrimary);
		pnlPrimaryMethodThreshold.setVisible(true);
		txtPrimaryClassiferDirectory.setVisible(false);
		
		JPanel pnlSecondary = new JPanel();
		pnlMain.add(pnlSecondary);
		GridBagLayout gbl_pnlSecondary = new GridBagLayout();
		gbl_pnlSecondary.columnWidths = new int[]{0, 0};
		gbl_pnlSecondary.rowHeights = new int[] {30, 0, 0, 0, 0, 30, 0, 0, 37, 0, 0, 0, 0};
		gbl_pnlSecondary.columnWeights = new double[]{1.0, 1.0};
		gbl_pnlSecondary.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlSecondary.setLayout(gbl_pnlSecondary); 
		
		JLabel lblNewLabel_4_1 = new JLabel("Secondary Objects");
		lblNewLabel_4_1.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_4_1 = new GridBagConstraints();
		gbc_lblNewLabel_4_1.gridwidth = 2;
		gbc_lblNewLabel_4_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_4_1.gridx = 0;
		gbc_lblNewLabel_4_1.gridy = 0;
		pnlSecondary.add(lblNewLabel_4_1, gbc_lblNewLabel_4_1);
		
		JLabel lblNewLabel_5_2 = new JLabel("Channel:");
		lblNewLabel_5_2.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_2 = new GridBagConstraints();
		gbc_lblNewLabel_5_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_2.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_2.gridx = 0;
		gbc_lblNewLabel_5_2.gridy = 1;
		pnlSecondary.add(lblNewLabel_5_2, gbc_lblNewLabel_5_2);
		
		JComboBox cbSecondaryChannel = new JComboBox();
		cbSecondaryChannel.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbSecondaryChannel.setSelectedIndex(3);
		cbSecondaryChannel.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbSecondaryChannel = new GridBagConstraints();
		gbc_cbSecondaryChannel.anchor = GridBagConstraints.WEST;
		gbc_cbSecondaryChannel.insets = new Insets(0, 0, 5, 0);
		gbc_cbSecondaryChannel.gridx = 1;
		gbc_cbSecondaryChannel.gridy = 1;
		pnlSecondary.add(cbSecondaryChannel, gbc_cbSecondaryChannel);
		
		JLabel lblNewLabel_5_3_1_1 = new JLabel("Background:");
		lblNewLabel_5_3_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_5_3_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_3_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_5_3_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_3_1_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_3_1_1.gridx = 0;
		gbc_lblNewLabel_5_3_1_1.gridy = 2;
		pnlSecondary.add(lblNewLabel_5_3_1_1, gbc_lblNewLabel_5_3_1_1);
		
		JComboBox cbSecondaryBackground = new JComboBox();
		cbSecondaryBackground.setModel(new DefaultComboBoxModel<>(BackgroundType.values()));
		cbSecondaryBackground.setSelectedIndex(0);
		cbSecondaryBackground.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbSecondaryBackground = new GridBagConstraints();
		gbc_cbSecondaryBackground.insets = new Insets(0, 0, 5, 0);
		gbc_cbSecondaryBackground.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbSecondaryBackground.gridx = 1;
		gbc_cbSecondaryBackground.gridy = 2;
		pnlSecondary.add(cbSecondaryBackground, gbc_cbSecondaryBackground);
		
		JPanel pnlSecondaryBGFirstBlur = new JPanel();
		GridBagConstraints gbc_pnlSecondaryBGFirstBlur = new GridBagConstraints();
		gbc_pnlSecondaryBGFirstBlur.anchor = GridBagConstraints.EAST;
		gbc_pnlSecondaryBGFirstBlur.gridwidth = 2;
		gbc_pnlSecondaryBGFirstBlur.insets = new Insets(0, 0, 5, 0);
		gbc_pnlSecondaryBGFirstBlur.fill = GridBagConstraints.VERTICAL;
		gbc_pnlSecondaryBGFirstBlur.gridx = 0;
		gbc_pnlSecondaryBGFirstBlur.gridy = 3;
		pnlSecondary.add(pnlSecondaryBGFirstBlur, gbc_pnlSecondaryBGFirstBlur);
		pnlSecondaryBGFirstBlur.setVisible(false);
		
		JLabel lblNewLabel_7_1 = new JLabel("S1:");
		lblNewLabel_7_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGFirstBlur.add(lblNewLabel_7_1);
		
		JLabel lblNewLabel_6_4_1 = new JLabel("X");
		lblNewLabel_6_4_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_4_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_4_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGFirstBlur.add(lblNewLabel_6_4_1);
		
		txtSecondaryS1X = new JTextField();
		txtSecondaryS1X.setText("0");
		txtSecondaryS1X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryS1X.setColumns(4);
		pnlSecondaryBGFirstBlur.add(txtSecondaryS1X);
		
		JLabel lblNewLabel_6_1_2_1 = new JLabel("Y");
		lblNewLabel_6_1_2_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGFirstBlur.add(lblNewLabel_6_1_2_1);
		
		txtSecondaryS1Y = new JTextField();
		txtSecondaryS1Y.setText("0");
		txtSecondaryS1Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryS1Y.setColumns(4);
		pnlSecondaryBGFirstBlur.add(txtSecondaryS1Y);
		
		JLabel lblNewLabel_6_2_2_1 = new JLabel("Z");
		lblNewLabel_6_2_2_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGFirstBlur.add(lblNewLabel_6_2_2_1);
		
		txtSecondaryS1Z = new JTextField();
		txtSecondaryS1Z.setText("0");
		txtSecondaryS1Z.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryS1Z.setColumns(4);
		pnlSecondaryBGFirstBlur.add(txtSecondaryS1Z);
		
		JPanel pnlSecondaryBGSecondBlur = new JPanel();
		GridBagConstraints gbc_pnlSecondaryBGSecondBlur = new GridBagConstraints();
		gbc_pnlSecondaryBGSecondBlur.anchor = GridBagConstraints.EAST;
		gbc_pnlSecondaryBGSecondBlur.gridwidth = 2;
		gbc_pnlSecondaryBGSecondBlur.insets = new Insets(0, 0, 5, 0);
		gbc_pnlSecondaryBGSecondBlur.fill = GridBagConstraints.VERTICAL;
		gbc_pnlSecondaryBGSecondBlur.gridx = 0;
		gbc_pnlSecondaryBGSecondBlur.gridy = 4;
		pnlSecondary.add(pnlSecondaryBGSecondBlur, gbc_pnlSecondaryBGSecondBlur);
		pnlSecondaryBGSecondBlur.setVisible(false);
		
		JLabel lblNewLabel_7_2 = new JLabel("S2:");
		lblNewLabel_7_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGSecondBlur.add(lblNewLabel_7_2);
		
		JLabel lblNewLabel_6_4_2 = new JLabel("X");
		lblNewLabel_6_4_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_4_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_4_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGSecondBlur.add(lblNewLabel_6_4_2);
		
		txtSecondaryS2X = new JTextField();
		txtSecondaryS2X.setText("0");
		txtSecondaryS2X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryS2X.setColumns(4);
		pnlSecondaryBGSecondBlur.add(txtSecondaryS2X);
		
		JLabel lblNewLabel_6_1_2_2 = new JLabel("Y");
		lblNewLabel_6_1_2_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_2_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_2_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGSecondBlur.add(lblNewLabel_6_1_2_2);
		
		txtSecondaryS2Y = new JTextField();
		txtSecondaryS2Y.setText("0");
		txtSecondaryS2Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryS2Y.setColumns(4);
		pnlSecondaryBGSecondBlur.add(txtSecondaryS2Y);
		
		JLabel lblNewLabel_6_2_2_2 = new JLabel("Z");
		lblNewLabel_6_2_2_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_2_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_2_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGSecondBlur.add(lblNewLabel_6_2_2_2);
		
		txtSecondaryS2Z = new JTextField();
		txtSecondaryS2Z.setText("0");
		txtSecondaryS2Z.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryS2Z.setColumns(4);
		pnlSecondaryBGSecondBlur.add(txtSecondaryS2Z);
		
		JLabel lblNewLabel_5_1_1_1 = new JLabel("Filter:");
		lblNewLabel_5_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_5_1_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_1_1_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_1_1_1.gridx = 0;
		gbc_lblNewLabel_5_1_1_1.gridy = 5;
		pnlSecondary.add(lblNewLabel_5_1_1_1, gbc_lblNewLabel_5_1_1_1);
		
		JComboBox cbSecondaryFilter = new JComboBox();
		cbSecondaryFilter.setModel(new DefaultComboBoxModel<>(FilterType.values()));
		cbSecondaryFilter.setSelectedIndex(0);
		cbSecondaryFilter.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbSecondaryFilter = new GridBagConstraints();
		gbc_cbSecondaryFilter.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbSecondaryFilter.insets = new Insets(0, 0, 5, 5);
		gbc_cbSecondaryFilter.gridx = 1;
		gbc_cbSecondaryFilter.gridy = 5;
		pnlSecondary.add(cbSecondaryFilter, gbc_cbSecondaryFilter);
		
		JPanel pnlSecondaryFirstBlur = new JPanel();
		GridBagConstraints gbc_pnlSecondaryFirstBlur = new GridBagConstraints();
		gbc_pnlSecondaryFirstBlur.anchor = GridBagConstraints.EAST;
		gbc_pnlSecondaryFirstBlur.fill = GridBagConstraints.VERTICAL;
		gbc_pnlSecondaryFirstBlur.gridwidth = 2;
		gbc_pnlSecondaryFirstBlur.insets = new Insets(0, 0, 5, 0);
		gbc_pnlSecondaryFirstBlur.gridx = 0;
		gbc_pnlSecondaryFirstBlur.gridy = 6;
		pnlSecondary.add(pnlSecondaryFirstBlur, gbc_pnlSecondaryFirstBlur);
		pnlSecondaryFirstBlur.setVisible(false);
		
		JLabel lblNewLabel_7_1_3 = new JLabel("S1:");
		lblNewLabel_7_1_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryFirstBlur.add(lblNewLabel_7_1_3);
		
		JLabel lblNewLabel_6_4 = new JLabel("X");
		lblNewLabel_6_4.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_4.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryFirstBlur.add(lblNewLabel_6_4);
		
		txtSecFilterX = new JTextField();
		txtSecFilterX.setText("0");
		txtSecFilterX.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecFilterX.setColumns(4);
		pnlSecondaryFirstBlur.add(txtSecFilterX);
		
		JLabel lblNewLabel_6_1_2 = new JLabel("Y");
		lblNewLabel_6_1_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryFirstBlur.add(lblNewLabel_6_1_2);
		
		txtSecFilterY = new JTextField();
		txtSecFilterY.setText("0");
		txtSecFilterY.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecFilterY.setColumns(4);
		pnlSecondaryFirstBlur.add(txtSecFilterY);
		
		JLabel lblNewLabel_6_2_2 = new JLabel("Z");
		lblNewLabel_6_2_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryFirstBlur.add(lblNewLabel_6_2_2);
		
		txtSecFilterZ = new JTextField();
		txtSecFilterZ.setText("0");
		txtSecFilterZ.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecFilterZ.setColumns(4);
		pnlSecondaryFirstBlur.add(txtSecFilterZ);
		
		JPanel pnlSecondarySecondBlur = new JPanel();
		GridBagConstraints gbc_pnlSecondarySecondBlur = new GridBagConstraints();
		gbc_pnlSecondarySecondBlur.anchor = GridBagConstraints.EAST;
		gbc_pnlSecondarySecondBlur.gridwidth = 2;
		gbc_pnlSecondarySecondBlur.insets = new Insets(0, 0, 5, 0);
		gbc_pnlSecondarySecondBlur.fill = GridBagConstraints.VERTICAL;
		gbc_pnlSecondarySecondBlur.gridx = 0;
		gbc_pnlSecondarySecondBlur.gridy = 7;
		pnlSecondary.add(pnlSecondarySecondBlur, gbc_pnlSecondarySecondBlur);
		pnlSecondarySecondBlur.setVisible(false);
		
		JLabel lblNewLabel_7_2_3 = new JLabel("S2:");
		lblNewLabel_7_2_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondarySecondBlur.add(lblNewLabel_7_2_3);
		
		JLabel lblNewLabel_6_4_3 = new JLabel("X");
		lblNewLabel_6_4_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_4_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_4_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondarySecondBlur.add(lblNewLabel_6_4_3);
		
		txtSecFilter2X = new JTextField();
		txtSecFilter2X.setText("0");
		txtSecFilter2X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecFilter2X.setColumns(4);
		pnlSecondarySecondBlur.add(txtSecFilter2X);
		
		JLabel lblNewLabel_6_1_2_3 = new JLabel("Y");
		lblNewLabel_6_1_2_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_2_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_2_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondarySecondBlur.add(lblNewLabel_6_1_2_3);
		
		txtSecFilter2Y = new JTextField();
		txtSecFilter2Y.setText("0");
		txtSecFilter2Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecFilter2Y.setColumns(4);
		pnlSecondarySecondBlur.add(txtSecFilter2Y);
		
		JLabel lblNewLabel_6_2_2_3 = new JLabel("Z");
		lblNewLabel_6_2_2_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_2_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_2_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondarySecondBlur.add(lblNewLabel_6_2_2_3);
		
		txtSecFilter2Z = new JTextField();
		txtSecFilter2Z.setText("0");
		txtSecFilter2Z.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecFilter2Z.setColumns(4);
		pnlSecondarySecondBlur.add(txtSecFilter2Z);
		
		JLabel lblNewLabel_5_1_2 = new JLabel("Method:");
		lblNewLabel_5_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_5_1_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_1_2.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_1_2.gridx = 0;
		gbc_lblNewLabel_5_1_2.gridy = 8;
		pnlSecondary.add(lblNewLabel_5_1_2, gbc_lblNewLabel_5_1_2);
		
		JComboBox cbSecondaryMethod = new JComboBox();
		
		JButton btnBrowseSecondaryClassifer = new JButton("Load");
		btnBrowseSecondaryClassifer.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnBrowseSecondaryClassifer = new GridBagConstraints();
		gbc_btnBrowseSecondaryClassifer.anchor = GridBagConstraints.EAST;
		gbc_btnBrowseSecondaryClassifer.insets = new Insets(0, 0, 5, 5);
		gbc_btnBrowseSecondaryClassifer.gridx = 0;
		gbc_btnBrowseSecondaryClassifer.gridy = 9;
		pnlSecondary.add(btnBrowseSecondaryClassifer, gbc_btnBrowseSecondaryClassifer);
		btnBrowseSecondaryClassifer.setVisible(false);
		
		
		txtSecondaryClassiferDirectory = new JTextField();
		txtSecondaryClassiferDirectory.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryClassiferDirectory.setColumns(4);
		GridBagConstraints gbc_txtSecondaryClassiferDirectory = new GridBagConstraints();
		gbc_txtSecondaryClassiferDirectory.insets = new Insets(0, 0, 5, 5);
		gbc_txtSecondaryClassiferDirectory.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSecondaryClassiferDirectory.gridx = 1;
		gbc_txtSecondaryClassiferDirectory.gridy = 9;
		pnlSecondary.add(txtSecondaryClassiferDirectory, gbc_txtSecondaryClassiferDirectory);
		txtSecondaryClassiferDirectory.setVisible(false);

		JPanel pnlSecondarySpotSize = new JPanel();
		GridBagConstraints gbc_pnlSecondarySpotSize = new GridBagConstraints();
		gbc_pnlSecondarySpotSize.insets = new Insets(0, 0, 5, 0);
		gbc_pnlSecondarySpotSize.anchor = GridBagConstraints.EAST;
		gbc_pnlSecondarySpotSize.fill = GridBagConstraints.VERTICAL;
		gbc_pnlSecondarySpotSize.gridwidth = 2;
		gbc_pnlSecondarySpotSize.gridx = 0;
		gbc_pnlSecondarySpotSize.gridy = 9;
		pnlSecondary.add(pnlSecondarySpotSize, gbc_pnlSecondarySpotSize);
		
		
		//cbSecondaryMethod.setModel(new DefaultComboBoxModel(new String[] {"Maxima", "Minima", "Trained Classifer"}));
		cbSecondaryMethod.setModel(new DefaultComboBoxModel<>(MethodTypes.values()));
		cbSecondaryMethod.setSelectedIndex(0);
		cbSecondaryMethod.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbSecondaryMethod = new GridBagConstraints();
		gbc_cbSecondaryMethod.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbSecondaryMethod.insets = new Insets(0, 0, 5, 5);
		gbc_cbSecondaryMethod.gridx = 1;
		gbc_cbSecondaryMethod.gridy = 8;
		pnlSecondary.add(cbSecondaryMethod, gbc_cbSecondaryMethod);
		
		
		JPanel pnlSecondaryBGRadius = new JPanel();
		GridBagConstraints gbc_pnlSecondaryBGRadius = new GridBagConstraints();
		gbc_pnlSecondaryBGRadius.insets = new Insets(0, 0, 5, 0);
		gbc_pnlSecondaryBGRadius.anchor = GridBagConstraints.EAST;
		gbc_pnlSecondaryBGRadius.fill = GridBagConstraints.VERTICAL;
		gbc_pnlSecondaryBGRadius.gridx = 1;
		gbc_pnlSecondaryBGRadius.gridy = 3;
		pnlSecondary.add(pnlSecondaryBGRadius, gbc_pnlSecondaryBGRadius);
		pnlSecondaryBGRadius.setVisible(false);
		
		JLabel lblNewLabel_6_3_1_1_2_1 = new JLabel("Radius:");
		lblNewLabel_6_3_1_1_2_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_3_1_1_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_3_1_1_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGRadius.add(lblNewLabel_6_3_1_1_2_1);
		
		txtSecondaryBGRadius = new JTextField();
		txtSecondaryBGRadius.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryBGRadius.setColumns(6);
		pnlSecondaryBGRadius.add(txtSecondaryBGRadius);
		pnlSecondaryBGRadius.setVisible(false);
		
		JLabel lblNewLabel_6_3_1 = new JLabel("X");
		lblNewLabel_6_3_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_3_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondarySpotSize.add(lblNewLabel_6_3_1);
		
		txtSecondaryMethodX = new JTextField();
		txtSecondaryMethodX.setText("0");
		txtSecondaryMethodX.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryMethodX.setColumns(4);
		pnlSecondarySpotSize.add(txtSecondaryMethodX);
		
		JLabel lblNewLabel_6_1_1_1 = new JLabel("Y");
		lblNewLabel_6_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondarySpotSize.add(lblNewLabel_6_1_1_1);
		
		txtSecondaryMethodY = new JTextField();
		txtSecondaryMethodY.setText("0");
		txtSecondaryMethodY.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryMethodY.setColumns(4);
		pnlSecondarySpotSize.add(txtSecondaryMethodY);
		
		JLabel lblNewLabel_6_2_1_1 = new JLabel("Z");
		lblNewLabel_6_2_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondarySpotSize.add(lblNewLabel_6_2_1_1);
		
		txtSecondaryMethodZ = new JTextField();
		txtSecondaryMethodZ.setText("0");
		txtSecondaryMethodZ.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryMethodZ.setColumns(4);
		pnlSecondarySpotSize.add(txtSecondaryMethodZ);
		
		JComboBox<ThresholdType> cbSecondaryMethodThreshold = new JComboBox<>();
		cbSecondaryMethodThreshold.setModel(new DefaultComboBoxModel<>(ThresholdType.values()));
		cbSecondaryMethodThreshold.setSelectedIndex(0);
		cbSecondaryMethodThreshold.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbSecondaryMethodThreshold = new GridBagConstraints();
		gbc_cbSecondaryMethodThreshold.anchor = GridBagConstraints.WEST;
		gbc_cbSecondaryMethodThreshold.insets = new Insets(0, 5, 5, 5);
		gbc_cbSecondaryMethodThreshold.gridx = 0;
		gbc_cbSecondaryMethodThreshold.gridy = 10;
		pnlSecondary.add(cbSecondaryMethodThreshold, gbc_cbSecondaryMethodThreshold);
		
		JPanel pnlSecondaryThreshold = new JPanel();
		GridBagConstraints gbc_pnlSecondaryTreshold = new GridBagConstraints();
		gbc_pnlSecondaryTreshold.insets = new Insets(0, 0, 5, 0);
		gbc_pnlSecondaryTreshold.anchor = GridBagConstraints.EAST;
		gbc_pnlSecondaryTreshold.gridx = 1;
		gbc_pnlSecondaryTreshold.gridy = 10;
		pnlSecondary.add(pnlSecondaryThreshold, gbc_pnlSecondaryTreshold);
		
		JLabel lblNewLabel_6_3_1_1 = new JLabel("Threshold:");
		lblNewLabel_6_3_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_3_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_3_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryThreshold.add(lblNewLabel_6_3_1_1);
		
		txtSecondaryMethodThreshold = new JTextField();
		txtSecondaryMethodThreshold.setText("0");
		txtSecondaryMethodThreshold.setEnabled(false);
		txtSecondaryMethodThreshold.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryMethodThreshold.setColumns(6);
		pnlSecondaryThreshold.add(txtSecondaryMethodThreshold);
		
		JButton btnProcessSecondary = new JButton("Process");
		btnProcessSecondary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Boolean displayOriginal = ckbSecondaryDisplay.isSelected();
				Boolean withOverlay = ckbSecondaryDisplayOverlay.isSelected();
				
				// Build the primary parameter object
				ObjectParameters secondaryParams = ObjectParameters.builder().
						channel(cbSecondaryChannel.getSelectedIndex()).
						backgroundParameters(
								BackgroundParameters.builder().
										backgroundType((BackgroundType) cbSecondaryBackground.getSelectedItem()).
										sigma1(Vector3D.builder().x(Double.parseDouble(txtSecondaryS1X.getText())).y(Double.parseDouble(txtSecondaryS1Y.getText())).z(Double.parseDouble(txtSecondaryS1Z.getText())).build()).
										sigma2(Vector3D.builder().x(Double.parseDouble(txtSecondaryS2X.getText())).y(Double.parseDouble(txtSecondaryS2Y.getText())).z(Double.parseDouble(txtSecondaryS2Z.getText())).build()).
										build()
						).
						filterParameters(
								FilterParameters.builder().
										filterType((FilterType) cbSecondaryFilter.getSelectedItem()).
										sigma1(Vector3D.builder().x(Double.parseDouble(txtSecFilterX.getText())).y(Double.parseDouble(txtSecFilterY.getText())).z(Double.parseDouble(txtSecFilterZ.getText())).build()).
										sigma2(Vector3D.builder().x(Double.parseDouble(txtSecFilter2X.getText())).y(Double.parseDouble(txtSecFilter2Y.getText())).z(Double.parseDouble(txtSecFilter2Z.getText())).build()).
										build()
						).
						methodParameters(
								MethodParameters.builder().
										methodType((MethodTypes) cbSecondaryMethod.getSelectedItem()).
										sigma(Vector3D.builder().x(Double.parseDouble(txtSecondaryMethodX.getText())).y(Double.parseDouble(txtSecondaryMethodY.getText())).z(Double.parseDouble(txtSecondaryMethodZ.getText())).build()).
										thresholdSize(Double.parseDouble(txtSecondaryMethodThreshold.getText())).
										classifierFilename(txtSecondaryClassiferDirectory.getText()).
										thresholdType((ThresholdType) cbSecondaryMethodThreshold.getSelectedItem()).
										build()
						).
						build();
				
				ParameterCollection parameterCollection = ParameterCollection.builder().
						killBorderType(selectedKillBorderOption).
						build();
				
				
				secondaryImg = channelArray[secondaryParams.getChannel()];
				
				try {
					ImagePlus secondaryDuplicate = secondaryImg.duplicate();
					
					secondaryOutput = Segmentation.run(secondaryDuplicate, secondaryParams, parameterCollection);
					IJ.resetMinAndMax(secondaryOutput);
					secondaryOutput.setTitle("Secondary Objects");
					
					if (displayOriginal) {
						ImagePlus outputDuplicate = secondaryOutput.duplicate();
						ImagePlus secondaryDisplayOverlay = optimize.processDisplay(secondaryDuplicate, outputDuplicate, withOverlay);
						secondaryDisplayOverlay.setTitle("Secondary Display");
						secondaryDisplayOverlay.show();
					}
					
					
					secondaryOutput.show();
					IJ.run("Tile", "");

				} catch (Exception e1) {
					IJ.showMessage("No image to process. Select a directory and try again. Ensure your images have the required number of channels.");
					e1.printStackTrace();
				}

			}
		});
		
		
		btnProcessSecondary.setToolTipText("Run secondary object segmentation");
		btnProcessSecondary.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnProcessSecondary = new GridBagConstraints();
		gbc_btnProcessSecondary.insets = new Insets(0, 0, 0, 5);
		gbc_btnProcessSecondary.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnProcessSecondary.gridx = 1;
		gbc_btnProcessSecondary.gridy = 11;
		pnlSecondary.add(btnProcessSecondary, gbc_btnProcessSecondary);
		
		JPanel pnlTertiary = new JPanel();
		pnlTertiary.setBorder(new MatteBorder(0, 1, 0, 0, (Color) new Color(169, 169, 169)));
		pnlMain.add(pnlTertiary);
		GridBagLayout gbl_pnlTertiary = new GridBagLayout();
		gbl_pnlTertiary.columnWidths = new int[] {0, 0};
		gbl_pnlTertiary.rowHeights = new int[]{30, 0, 0, 0, 0, 30, 0, 0, 35, 0, 30, 0, 0};
		gbl_pnlTertiary.columnWeights = new double[]{1.0, 1.0};
		gbl_pnlTertiary.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlTertiary.setLayout(gbl_pnlTertiary);
		
		JCheckBox ckbTertiary = new JCheckBox("Tertiary Object");
		ckbTertiary.setFont(new Font("Arial", Font.BOLD, 14));
		ckbTertiary.setEnabled(true);
		GridBagConstraints gbc_ckbTertiary = new GridBagConstraints();
		gbc_ckbTertiary.gridwidth = 2;
		gbc_ckbTertiary.insets = new Insets(0, 0, 5, 0);
		gbc_ckbTertiary.gridx = 0;
		gbc_ckbTertiary.gridy = 0;
		pnlTertiary.add(ckbTertiary, gbc_ckbTertiary);
		
		JLabel lblNewLabel_5_3 = new JLabel("Channel:");
		lblNewLabel_5_3.setEnabled(false);
		lblNewLabel_5_3.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_3 = new GridBagConstraints();
		gbc_lblNewLabel_5_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_3.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_3.gridx = 0;
		gbc_lblNewLabel_5_3.gridy = 1;
		pnlTertiary.add(lblNewLabel_5_3, gbc_lblNewLabel_5_3);
		
		JComboBox cbTertiaryChannel = new JComboBox();
		cbTertiaryChannel.setEnabled(false);
		cbTertiaryChannel.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbTertiaryChannel.setSelectedIndex(0);
		cbTertiaryChannel.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbTertiaryChannel = new GridBagConstraints();
		gbc_cbTertiaryChannel.anchor = GridBagConstraints.WEST;
		gbc_cbTertiaryChannel.insets = new Insets(0, 0, 5, 0);
		gbc_cbTertiaryChannel.gridx = 1;
		gbc_cbTertiaryChannel.gridy = 1;
		pnlTertiary.add(cbTertiaryChannel, gbc_cbTertiaryChannel);
		
		JLabel lblNewLabel_5_3_1_1_1 = new JLabel("Background:");
		lblNewLabel_5_3_1_1_1.setEnabled(false);
		lblNewLabel_5_3_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_5_3_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_3_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_5_3_1_1_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_3_1_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_3_1_1_1.gridx = 0;
		gbc_lblNewLabel_5_3_1_1_1.gridy = 2;
		pnlTertiary.add(lblNewLabel_5_3_1_1_1, gbc_lblNewLabel_5_3_1_1_1);
		
		JPanel pnlTertiaryBGFirstBlur = new JPanel();
		GridBagConstraints gbc_pnlTertiaryBGFirstBlur = new GridBagConstraints();
		gbc_pnlTertiaryBGFirstBlur.anchor = GridBagConstraints.EAST;
		gbc_pnlTertiaryBGFirstBlur.gridwidth = 2;
		gbc_pnlTertiaryBGFirstBlur.insets = new Insets(0, 0, 5, 0);
		gbc_pnlTertiaryBGFirstBlur.fill = GridBagConstraints.VERTICAL;
		gbc_pnlTertiaryBGFirstBlur.gridx = 0;
		gbc_pnlTertiaryBGFirstBlur.gridy = 3;
		pnlTertiary.add(pnlTertiaryBGFirstBlur, gbc_pnlTertiaryBGFirstBlur);
		pnlTertiaryBGFirstBlur.setVisible(false);
		
		JLabel lblNewLabel_7_1_2 = new JLabel("S1:");
		lblNewLabel_7_1_2.setEnabled(false);
		lblNewLabel_7_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryBGFirstBlur.add(lblNewLabel_7_1_2);
		
		JLabel lblNewLabel_6_4_1_1 = new JLabel("X");
		lblNewLabel_6_4_1_1.setEnabled(false);
		lblNewLabel_6_4_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_4_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_4_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryBGFirstBlur.add(lblNewLabel_6_4_1_1);
		
		txtTertiaryS1X = new JTextField();
		txtTertiaryS1X.setText("0");
		txtTertiaryS1X.setEnabled(false);
		txtTertiaryS1X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertiaryS1X.setColumns(4);
		pnlTertiaryBGFirstBlur.add(txtTertiaryS1X);
		
		JLabel lblNewLabel_6_1_2_1_1 = new JLabel("Y");
		lblNewLabel_6_1_2_1_1.setEnabled(false);
		lblNewLabel_6_1_2_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_2_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_2_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryBGFirstBlur.add(lblNewLabel_6_1_2_1_1);
		
		txtTertiaryS1Y = new JTextField();
		txtTertiaryS1Y.setText("0");
		txtTertiaryS1Y.setEnabled(false);
		txtTertiaryS1Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertiaryS1Y.setColumns(4);
		pnlTertiaryBGFirstBlur.add(txtTertiaryS1Y);
		
		JLabel lblNewLabel_6_2_2_1_1 = new JLabel("Z");
		lblNewLabel_6_2_2_1_1.setEnabled(false);
		lblNewLabel_6_2_2_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_2_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_2_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryBGFirstBlur.add(lblNewLabel_6_2_2_1_1);
		
		txtTertiaryS1Z = new JTextField();
		txtTertiaryS1Z.setText("0");
		txtTertiaryS1Z.setEnabled(false);
		txtTertiaryS1Z.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertiaryS1Z.setColumns(4);
		pnlTertiaryBGFirstBlur.add(txtTertiaryS1Z);
		
		JPanel pnlTertiaryBGSecondBlur = new JPanel();
		GridBagConstraints gbc_pnlTertiaryBGSecondBlur = new GridBagConstraints();
		gbc_pnlTertiaryBGSecondBlur.anchor = GridBagConstraints.EAST;
		gbc_pnlTertiaryBGSecondBlur.gridwidth = 2;
		gbc_pnlTertiaryBGSecondBlur.insets = new Insets(0, 0, 5, 0);
		gbc_pnlTertiaryBGSecondBlur.fill = GridBagConstraints.VERTICAL;
		gbc_pnlTertiaryBGSecondBlur.gridx = 0;
		gbc_pnlTertiaryBGSecondBlur.gridy = 4;
		pnlTertiary.add(pnlTertiaryBGSecondBlur, gbc_pnlTertiaryBGSecondBlur);
		pnlTertiaryBGSecondBlur.setVisible(false);
		
		JLabel lblNewLabel_7_2_2 = new JLabel("S2:");
		lblNewLabel_7_2_2.setEnabled(false);
		lblNewLabel_7_2_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryBGSecondBlur.add(lblNewLabel_7_2_2);
		
		JLabel lblNewLabel_6_4_2_1 = new JLabel("X");
		lblNewLabel_6_4_2_1.setEnabled(false);
		lblNewLabel_6_4_2_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_4_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_4_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryBGSecondBlur.add(lblNewLabel_6_4_2_1);
		
		txtTertiaryS2X = new JTextField();
		txtTertiaryS2X.setText("0");
		txtTertiaryS2X.setEnabled(false);
		txtTertiaryS2X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertiaryS2X.setColumns(4);
		pnlTertiaryBGSecondBlur.add(txtTertiaryS2X);
		
		JLabel lblNewLabel_6_1_2_2_1 = new JLabel("Y");
		lblNewLabel_6_1_2_2_1.setEnabled(false);
		lblNewLabel_6_1_2_2_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_2_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_2_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryBGSecondBlur.add(lblNewLabel_6_1_2_2_1);
		
		txtTertiaryS2Y = new JTextField();
		txtTertiaryS2Y.setText("0");
		txtTertiaryS2Y.setEnabled(false);
		txtTertiaryS2Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertiaryS2Y.setColumns(4);
		pnlTertiaryBGSecondBlur.add(txtTertiaryS2Y);
		
		JLabel lblNewLabel_6_2_2_2_1 = new JLabel("Z");
		lblNewLabel_6_2_2_2_1.setEnabled(false);
		lblNewLabel_6_2_2_2_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_2_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_2_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryBGSecondBlur.add(lblNewLabel_6_2_2_2_1);
		
		txtTertiaryS2Z = new JTextField();
		txtTertiaryS2Z.setText("0");
		txtTertiaryS2Z.setEnabled(false);
		txtTertiaryS2Z.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertiaryS2Z.setColumns(4);
		pnlTertiaryBGSecondBlur.add(txtTertiaryS2Z);
		
		
		JPanel pnlTertiaryBGRadius = new JPanel();
		GridBagConstraints gbc_pnlTertiaryBGRadius = new GridBagConstraints();
		gbc_pnlTertiaryBGRadius.insets = new Insets(0, 0, 5, 0);
		gbc_pnlTertiaryBGRadius.anchor = GridBagConstraints.EAST;
		gbc_pnlTertiaryBGRadius.fill = GridBagConstraints.VERTICAL;
		gbc_pnlTertiaryBGRadius.gridx = 1;
		gbc_pnlTertiaryBGRadius.gridy = 3;
		pnlTertiary.add(pnlTertiaryBGRadius, gbc_pnlTertiaryBGRadius);
		pnlTertiaryBGRadius.setVisible(false);
		
		JLabel lblNewLabel_6_3_1_1_2_1_1 = new JLabel("Radius:");
		lblNewLabel_6_3_1_1_2_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_3_1_1_2_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_3_1_1_2_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryBGRadius.add(lblNewLabel_6_3_1_1_2_1_1);
		
		txtTertiaryBGRadius = new JTextField();
		txtTertiaryBGRadius.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertiaryBGRadius.setColumns(6);
		pnlTertiaryBGRadius.add(txtTertiaryBGRadius);
		
		
		
		JComboBox cbTertiaryBackground = new JComboBox();
		cbTertiaryBackground.setEnabled(false);
		cbTertiaryBackground.setModel(new DefaultComboBoxModel<>(BackgroundType.values()));
		cbTertiaryBackground.setSelectedIndex(0);
		cbTertiaryBackground.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbTertiaryBackground = new GridBagConstraints();
		gbc_cbTertiaryBackground.insets = new Insets(0, 0, 5, 0);
		gbc_cbTertiaryBackground.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbTertiaryBackground.gridx = 1;
		gbc_cbTertiaryBackground.gridy = 2;
		pnlTertiary.add(cbTertiaryBackground, gbc_cbTertiaryBackground);
		
		JLabel lblNewLabel_5_1_1_2 = new JLabel("Filter:");
		lblNewLabel_5_1_1_2.setEnabled(false);
		lblNewLabel_5_1_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_1_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_5_1_1_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_1_1_2.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_1_1_2.gridx = 0;
		gbc_lblNewLabel_5_1_1_2.gridy = 5;
		pnlTertiary.add(lblNewLabel_5_1_1_2, gbc_lblNewLabel_5_1_1_2);
		
		JComboBox cbTertiaryFilter = new JComboBox();
		cbTertiaryFilter.setEnabled(false);
		cbTertiaryFilter.setModel(new DefaultComboBoxModel<>(FilterType.values()));
		cbTertiaryFilter.setSelectedIndex(0);
		cbTertiaryFilter.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbTertiaryFilter = new GridBagConstraints();
		gbc_cbTertiaryFilter.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbTertiaryFilter.insets = new Insets(0, 0, 5, 0);
		gbc_cbTertiaryFilter.gridx = 1;
		gbc_cbTertiaryFilter.gridy = 5;
		pnlTertiary.add(cbTertiaryFilter, gbc_cbTertiaryFilter);
		
		JPanel pnlTertiaryFirstBlur = new JPanel();
		GridBagConstraints gbc_pnlTertiaryFirstBlur = new GridBagConstraints();
		gbc_pnlTertiaryFirstBlur.anchor = GridBagConstraints.EAST;
		gbc_pnlTertiaryFirstBlur.fill = GridBagConstraints.VERTICAL;
		gbc_pnlTertiaryFirstBlur.gridwidth = 2;
		gbc_pnlTertiaryFirstBlur.insets = new Insets(0, 0, 5, 0);
		gbc_pnlTertiaryFirstBlur.gridx = 0;
		gbc_pnlTertiaryFirstBlur.gridy = 6;
		pnlTertiary.add(pnlTertiaryFirstBlur, gbc_pnlTertiaryFirstBlur);
		pnlTertiaryFirstBlur.setVisible(false);
		
		JLabel lblNewLabel_7_1_2_1 = new JLabel("S1:");
		lblNewLabel_7_1_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_7_1_2_1.setEnabled(false);
		pnlTertiaryFirstBlur.add(lblNewLabel_7_1_2_1);
		
		JLabel lblNewLabel_6_5 = new JLabel("X");
		lblNewLabel_6_5.setEnabled(false);
		lblNewLabel_6_5.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_5.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_5.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryFirstBlur.add(lblNewLabel_6_5);
		
		txtTertFilterX = new JTextField();
		txtTertFilterX.setText("0");
		txtTertFilterX.setEnabled(false);
		txtTertFilterX.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertFilterX.setColumns(4);
		pnlTertiaryFirstBlur.add(txtTertFilterX);
		
		JLabel lblNewLabel_6_1_3 = new JLabel("Y");
		lblNewLabel_6_1_3.setEnabled(false);
		lblNewLabel_6_1_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryFirstBlur.add(lblNewLabel_6_1_3);
		
		txtTertFilterY = new JTextField();
		txtTertFilterY.setText("0");
		txtTertFilterY.setEnabled(false);
		txtTertFilterY.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertFilterY.setColumns(4);
		pnlTertiaryFirstBlur.add(txtTertFilterY);
		
		JLabel lblNewLabel_6_2_3 = new JLabel("Z");
		lblNewLabel_6_2_3.setEnabled(false);
		lblNewLabel_6_2_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryFirstBlur.add(lblNewLabel_6_2_3);
		
		txtTertFilterZ = new JTextField();
		txtTertFilterZ.setText("0");
		txtTertFilterZ.setEnabled(false);
		txtTertFilterZ.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertFilterZ.setColumns(4);
		pnlTertiaryFirstBlur.add(txtTertFilterZ);
		
		JPanel pnlTertiarySecondBlur = new JPanel();
		GridBagConstraints gbc_pnlTertiarySecondBlur = new GridBagConstraints();
		gbc_pnlTertiarySecondBlur.anchor = GridBagConstraints.EAST;
		gbc_pnlTertiarySecondBlur.gridwidth = 2;
		gbc_pnlTertiarySecondBlur.insets = new Insets(0, 0, 5, 0);
		gbc_pnlTertiarySecondBlur.fill = GridBagConstraints.VERTICAL;
		gbc_pnlTertiarySecondBlur.gridx = 0;
		gbc_pnlTertiarySecondBlur.gridy = 7;
		pnlTertiary.add(pnlTertiarySecondBlur, gbc_pnlTertiarySecondBlur);
		pnlTertiarySecondBlur.setVisible(false);
		
		
		JLabel lblNewLabel_7_2_2_1 = new JLabel("S2:");
		lblNewLabel_7_2_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_7_2_2_1.setEnabled(false);
		pnlTertiarySecondBlur.add(lblNewLabel_7_2_2_1);
		
		JLabel lblNewLabel_6_5_1 = new JLabel("X");
		lblNewLabel_6_5_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_5_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_5_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_6_5_1.setEnabled(false);
		pnlTertiarySecondBlur.add(lblNewLabel_6_5_1);
		
		txtTertFilter2X = new JTextField();
		txtTertFilter2X.setText("0");
		txtTertFilter2X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertFilter2X.setEnabled(false);
		txtTertFilter2X.setColumns(4);
		pnlTertiarySecondBlur.add(txtTertFilter2X);
		
		JLabel lblNewLabel_6_1_3_1 = new JLabel("Y");
		lblNewLabel_6_1_3_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_3_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_6_1_3_1.setEnabled(false);
		pnlTertiarySecondBlur.add(lblNewLabel_6_1_3_1);
		
		txtTertFilter2Y = new JTextField();
		txtTertFilter2Y.setText("0");
		txtTertFilter2Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertFilter2Y.setEnabled(false);
		txtTertFilter2Y.setColumns(4);
		pnlTertiarySecondBlur.add(txtTertFilter2Y);
		
		JLabel lblNewLabel_6_2_3_1 = new JLabel("Z");
		lblNewLabel_6_2_3_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_3_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_6_2_3_1.setEnabled(false);
		pnlTertiarySecondBlur.add(lblNewLabel_6_2_3_1);
		
		txtTertFilter2Z = new JTextField();
		txtTertFilter2Z.setText("0");
		txtTertFilter2Z.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertFilter2Z.setEnabled(false);
		txtTertFilter2Z.setColumns(4);
		pnlTertiarySecondBlur.add(txtTertFilter2Z);
		
		JLabel lblNewLabel_5_1_3 = new JLabel("Method:");
		lblNewLabel_5_1_3.setEnabled(false);
		lblNewLabel_5_1_3.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_1_3 = new GridBagConstraints();
		gbc_lblNewLabel_5_1_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_1_3.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_1_3.gridx = 0;
		gbc_lblNewLabel_5_1_3.gridy = 8;
		pnlTertiary.add(lblNewLabel_5_1_3, gbc_lblNewLabel_5_1_3);
		
		JComboBox cbTertiaryMethod = new JComboBox();
		cbTertiaryMethod.setEnabled(false);
		
		//cbTertiaryMethod.setModel(new DefaultComboBoxModel(new String[] {"Maxima", "Minima", "Trained Classifer"}));
		cbTertiaryMethod.setModel(new DefaultComboBoxModel<>(MethodTypes.values()));
		cbTertiaryMethod.setSelectedIndex(0);
		cbTertiaryMethod.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbTertiaryMethod = new GridBagConstraints();
		gbc_cbTertiaryMethod.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbTertiaryMethod.insets = new Insets(0, 0, 5, 0);
		gbc_cbTertiaryMethod.gridx = 1;
		gbc_cbTertiaryMethod.gridy = 8;
		pnlTertiary.add(cbTertiaryMethod, gbc_cbTertiaryMethod);
		
		btnBrowseTertiaryClassifer = new JButton("Load");
		btnBrowseTertiaryClassifer.setEnabled(false);
		btnBrowseTertiaryClassifer.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnBrowseTertiaryClassifer = new GridBagConstraints();
		gbc_btnBrowseTertiaryClassifer.insets = new Insets(0, 0, 5, 5);
		gbc_btnBrowseTertiaryClassifer.gridx = 0;
		gbc_btnBrowseTertiaryClassifer.gridy = 9;
		pnlTertiary.add(btnBrowseTertiaryClassifer, gbc_btnBrowseTertiaryClassifer);
		btnBrowseTertiaryClassifer.setVisible(false);
		
		txtTertiaryClassiferDirectory = new JTextField();
		txtTertiaryClassiferDirectory.setEnabled(false);
		txtTertiaryClassiferDirectory.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertiaryClassiferDirectory.setColumns(10);
		GridBagConstraints gbc_txtTertiaryClassiferDirectory = new GridBagConstraints();
		gbc_txtTertiaryClassiferDirectory.insets = new Insets(0, 0, 5, 0);
		gbc_txtTertiaryClassiferDirectory.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTertiaryClassiferDirectory.gridx = 1;
		gbc_txtTertiaryClassiferDirectory.gridy = 9;
		pnlTertiary.add(txtTertiaryClassiferDirectory, gbc_txtTertiaryClassiferDirectory);
		txtTertiaryClassiferDirectory.setVisible(false);
		
		JPanel pnlTertiarySpotSize = new JPanel();
		GridBagConstraints gbc_pnlTertiarySpotSize = new GridBagConstraints();
		gbc_pnlTertiarySpotSize.insets = new Insets(0, 0, 5, 0);
		gbc_pnlTertiarySpotSize.anchor = GridBagConstraints.EAST;
		gbc_pnlTertiarySpotSize.fill = GridBagConstraints.VERTICAL;
		gbc_pnlTertiarySpotSize.gridwidth = 2;
		gbc_pnlTertiarySpotSize.gridx = 0;
		gbc_pnlTertiarySpotSize.gridy = 9;
		pnlTertiary.add(pnlTertiarySpotSize, gbc_pnlTertiarySpotSize);
		
		JLabel lblNewLabel_6_3_2 = new JLabel("X");
		lblNewLabel_6_3_2.setEnabled(false);
		lblNewLabel_6_3_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_3_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_3_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiarySpotSize.add(lblNewLabel_6_3_2);
		
		txtTertiaryMethodX = new JTextField();
		txtTertiaryMethodX.setEnabled(false);
		txtTertiaryMethodX.setText("0");
		txtTertiaryMethodX.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertiaryMethodX.setColumns(4);
		pnlTertiarySpotSize.add(txtTertiaryMethodX);
		
		JLabel lblNewLabel_6_1_1_2 = new JLabel("Y");
		lblNewLabel_6_1_1_2.setEnabled(false);
		lblNewLabel_6_1_1_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiarySpotSize.add(lblNewLabel_6_1_1_2);
		
		txtTertiaryMethodY = new JTextField();
		txtTertiaryMethodY.setEnabled(false);
		txtTertiaryMethodY.setText("0");
		txtTertiaryMethodY.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertiaryMethodY.setColumns(4);
		pnlTertiarySpotSize.add(txtTertiaryMethodY);
		
		JLabel lblNewLabel_6_2_1_2 = new JLabel("Z");
		lblNewLabel_6_2_1_2.setEnabled(false);
		lblNewLabel_6_2_1_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiarySpotSize.add(lblNewLabel_6_2_1_2);
		
		txtTertiaryMethodZ = new JTextField();
		txtTertiaryMethodZ.setEnabled(false);
		txtTertiaryMethodZ.setText("0");
		txtTertiaryMethodZ.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertiaryMethodZ.setColumns(4);
		pnlTertiarySpotSize.add(txtTertiaryMethodZ);
		
		JPanel pnlTertiaryThreshold = new JPanel();
		GridBagConstraints gbc_pnlTertiaryThreshold = new GridBagConstraints();
		gbc_pnlTertiaryThreshold.insets = new Insets(0, 0, 5, 0);
		gbc_pnlTertiaryThreshold.anchor = GridBagConstraints.EAST;
		gbc_pnlTertiaryThreshold.fill = GridBagConstraints.VERTICAL;
		gbc_pnlTertiaryThreshold.gridx = 1;
		gbc_pnlTertiaryThreshold.gridy = 10;
		pnlTertiary.add(pnlTertiaryThreshold, gbc_pnlTertiaryThreshold);
		
		JLabel lblNewLabel_6_3_1_1_1 = new JLabel("Threshold:");
		lblNewLabel_6_3_1_1_1.setEnabled(false);
		lblNewLabel_6_3_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_3_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_3_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryThreshold.add(lblNewLabel_6_3_1_1_1);
		
		txtTertiaryMethodThreshold = new JTextField();
		txtTertiaryMethodThreshold.setText("0");
		txtTertiaryMethodThreshold.setEnabled(false);
		txtTertiaryMethodThreshold.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertiaryMethodThreshold.setColumns(6);
		pnlTertiaryThreshold.add(txtTertiaryMethodThreshold);
		
		JComboBox cbTertiaryMethodThreshold = new JComboBox();
		cbTertiaryMethodThreshold.setModel(new DefaultComboBoxModel<>(ThresholdType.values()));
		cbTertiaryMethodThreshold.setSelectedIndex(0);
		cbTertiaryMethodThreshold.setFont(new Font("Arial", Font.PLAIN, 14));
		cbTertiaryMethodThreshold.setEnabled(false);
		GridBagConstraints gbc_cbTertiaryMethodThreshold = new GridBagConstraints();
		gbc_cbTertiaryMethodThreshold.anchor = GridBagConstraints.WEST;
		gbc_cbTertiaryMethodThreshold.insets = new Insets(0, 5, 5, 5);
		gbc_cbTertiaryMethodThreshold.gridx = 0;
		gbc_cbTertiaryMethodThreshold.gridy = 10;
		pnlTertiary.add(cbTertiaryMethodThreshold, gbc_cbTertiaryMethodThreshold);
		
		JButton btnProcessTertiary = new JButton("Process");
		btnProcessTertiary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				Boolean displayOriginal = ckbTertiaryDisplay.isSelected();
				Boolean withOverlay = ckbTertiaryDisplayOverlay.isSelected();
				
				// Build the primary parameter object
				ObjectParameters tertiaryParams = ObjectParameters.builder().
						channel(cbTertiaryChannel.getSelectedIndex()).
						backgroundParameters(
								BackgroundParameters.builder().
										backgroundType((BackgroundType) cbTertiaryBackground.getSelectedItem()).
										sigma1(Vector3D.builder().x(Double.parseDouble(txtTertiaryS1X.getText())).y(Double.parseDouble(txtTertiaryS1Y.getText())).z(Double.parseDouble(txtTertiaryS1Z.getText())).build()).
										sigma2(Vector3D.builder().x(Double.parseDouble(txtTertiaryS2X.getText())).y(Double.parseDouble(txtTertiaryS2Y.getText())).z(Double.parseDouble(txtTertiaryS2Z.getText())).build()).
										build()
						).
						filterParameters(
								FilterParameters.builder().
										filterType((FilterType) cbTertiaryFilter.getSelectedItem()).
										sigma1(Vector3D.builder().x(Double.parseDouble(txtTertFilterX.getText())).y(Double.parseDouble(txtTertFilterY.getText())).z(Double.parseDouble(txtTertFilterZ.getText())).build()).
										sigma2(Vector3D.builder().x(Double.parseDouble(txtTertFilter2X.getText())).y(Double.parseDouble(txtTertFilter2Y.getText())).z(Double.parseDouble(txtTertFilter2Z.getText())).build()).
										build()
						).
						methodParameters(
								MethodParameters.builder().
										methodType((MethodTypes) cbTertiaryMethod.getSelectedItem()).
										sigma(Vector3D.builder().x(Double.parseDouble(txtTertiaryMethodX.getText())).y(Double.parseDouble(txtTertiaryMethodY.getText())).z(Double.parseDouble(txtTertiaryMethodZ.getText())).build()).
										thresholdSize(Double.parseDouble(txtTertiaryMethodThreshold.getText())).
										classifierFilename(txtTertiaryClassiferDirectory.getText()).
										thresholdType((ThresholdType) cbTertiaryMethodThreshold.getSelectedItem()).
										build()
						).
						build();
				
				ParameterCollection parameterCollection = ParameterCollection.builder().
						killBorderType(selectedKillBorderOption).
						build();
				

				tertiaryImg = channelArray[tertiaryParams.getChannel()];
				
				try {

					ImagePlus tertiaryDuplicate = tertiaryImg.duplicate();
					
					tertiaryOutput = Segmentation.run(tertiaryDuplicate, tertiaryParams, parameterCollection);
					IJ.resetMinAndMax(tertiaryOutput);
					tertiaryOutput.setTitle("Tertiary Objects");
					
					if (displayOriginal) {
						ImagePlus outputDuplicate = tertiaryOutput.duplicate();
						ImagePlus tertiaryDisplayOverlay = optimize.processDisplay(tertiaryDuplicate, outputDuplicate, withOverlay);
						tertiaryDisplayOverlay.setTitle("Tertiary Display");
						tertiaryDisplayOverlay.show();
					}
					
					
					tertiaryOutput.show();
					IJ.run("Tile", "");

				} catch (Exception e1) {
					IJ.showMessage("No image to process. Select a directory and try again. Ensure your images have the required number of channels.");
					e1.printStackTrace();
				}
				
				
				
				
				
			}
		});
		btnProcessTertiary.setToolTipText("Run tertiary object segmentation");
		btnProcessTertiary.setEnabled(false);
		btnProcessTertiary.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnProcessTertiary = new GridBagConstraints();
		gbc_btnProcessTertiary.insets = new Insets(0, 0, 0, 5);
		gbc_btnProcessTertiary.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnProcessTertiary.gridx = 1;
		gbc_btnProcessTertiary.gridy = 11;
		pnlTertiary.add(btnProcessTertiary, gbc_btnProcessTertiary);
		
		
		JPanel pnlFooter = new JPanel();
		GridBagConstraints gbc_pnlFooter = new GridBagConstraints();
		gbc_pnlFooter.fill = GridBagConstraints.BOTH;
		gbc_pnlFooter.insets = new Insets(0, 0, 5, 0);
		gbc_pnlFooter.gridx = 0;
		gbc_pnlFooter.gridy = 2;
		contentPane.add(pnlFooter, gbc_pnlFooter);
		GridBagLayout gbl_pnlFooter = new GridBagLayout();
		gbl_pnlFooter.columnWidths = new int[]{0, 140, 30, 30, 30, 30, 0, 0, 0, 0, 30};
		gbl_pnlFooter.rowHeights = new int[] {0, 0};
		gbl_pnlFooter.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0};
		gbl_pnlFooter.rowWeights = new double[]{0.0, 0.0, 0.0};
		pnlFooter.setLayout(gbl_pnlFooter);
		
		JSeparator horizontalSeparator = new JSeparator();
		horizontalSeparator.setForeground(new Color(169, 169, 169));
		horizontalSeparator.setBackground(new Color(255, 255, 255));
		GridBagConstraints gbc_horizontalSeparator = new GridBagConstraints();
		gbc_horizontalSeparator.fill = GridBagConstraints.HORIZONTAL;
		gbc_horizontalSeparator.gridwidth = 11;
		gbc_horizontalSeparator.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalSeparator.gridx = 0;
		gbc_horizontalSeparator.gridy = 0;
		pnlFooter.add(horizontalSeparator, gbc_horizontalSeparator);
		
		JButton btnBackToMenu = new JButton("Back to Menu");
		btnBackToMenu.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnBackToMenu = new GridBagConstraints();
		gbc_btnBackToMenu.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnBackToMenu.insets = new Insets(0, 0, 5, 5);
		gbc_btnBackToMenu.gridx = 0;
		gbc_btnBackToMenu.gridy = 1;
		pnlFooter.add(btnBackToMenu, gbc_btnBackToMenu);
		
		JButton btnLoadParameters = new JButton("Load Configuration");
		btnLoadParameters.setToolTipText("Browse for a previously saved configuration.");
		btnLoadParameters.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnLoadParameters = new GridBagConstraints();
		gbc_btnLoadParameters.anchor = GridBagConstraints.WEST;
		gbc_btnLoadParameters.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoadParameters.gridx = 1;
		gbc_btnLoadParameters.gridy = 1;
		pnlFooter.add(btnLoadParameters, gbc_btnLoadParameters);
		
		JLabel lblNewLabel_3_1_4_2_1 = new JLabel("Combine Outputs:");
		lblNewLabel_3_1_4_2_1.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_3_1_4_2_1 = new GridBagConstraints();
		gbc_lblNewLabel_3_1_4_2_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1_4_2_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3_1_4_2_1.gridx = 3;
		gbc_lblNewLabel_3_1_4_2_1.gridy = 1;
		pnlFooter.add(lblNewLabel_3_1_4_2_1, gbc_lblNewLabel_3_1_4_2_1);
		
		JCheckBox ckbPrimaryLabel = new JCheckBox("Primary Labels");
		ckbPrimaryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ckbPrimaryLabel = new GridBagConstraints();
		gbc_ckbPrimaryLabel.anchor = GridBagConstraints.WEST;
		gbc_ckbPrimaryLabel.insets = new Insets(0, 0, 5, 5);
		gbc_ckbPrimaryLabel.gridx = 4;
		gbc_ckbPrimaryLabel.gridy = 1;
		pnlFooter.add(ckbPrimaryLabel, gbc_ckbPrimaryLabel);
		
		JCheckBox ckbSecondaryLabel = new JCheckBox("Secondary Labels");
		ckbSecondaryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ckbSecondaryLabel = new GridBagConstraints();
		gbc_ckbSecondaryLabel.anchor = GridBagConstraints.WEST;
		gbc_ckbSecondaryLabel.insets = new Insets(0, 0, 5, 5);
		gbc_ckbSecondaryLabel.gridx = 5;
		gbc_ckbSecondaryLabel.gridy = 1;
		pnlFooter.add(ckbSecondaryLabel, gbc_ckbSecondaryLabel);
		
		JCheckBox ckbTertiaryLabel = new JCheckBox("Tertiary Labels");
		ckbTertiaryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ckbTertiaryLabel = new GridBagConstraints();
		gbc_ckbTertiaryLabel.anchor = GridBagConstraints.WEST;
		gbc_ckbTertiaryLabel.insets = new Insets(0, 0, 5, 5);
		gbc_ckbTertiaryLabel.gridx = 6;
		gbc_ckbTertiaryLabel.gridy = 1;
		pnlFooter.add(ckbTertiaryLabel, gbc_ckbTertiaryLabel);
		
		JButton btnUpdateOverlays = new JButton("Update Overlays");
		btnUpdateOverlays.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnUpdateOverlays = new GridBagConstraints();
		gbc_btnUpdateOverlays.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUpdateOverlays.gridwidth = 2;
		gbc_btnUpdateOverlays.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpdateOverlays.gridx = 7;
		gbc_btnUpdateOverlays.gridy = 1;
		pnlFooter.add(btnUpdateOverlays, gbc_btnUpdateOverlays);
		
		JButton btnSaveConfiguration = new JButton("Save Configuration");
		btnSaveConfiguration.setToolTipText("Save the current configuration.");
		btnSaveConfiguration.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_btnSaveConfiguration = new GridBagConstraints();
		gbc_btnSaveConfiguration.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSaveConfiguration.gridwidth = 2;
		gbc_btnSaveConfiguration.anchor = GridBagConstraints.SOUTH;
		gbc_btnSaveConfiguration.insets = new Insets(0, 0, 0, 5);
		gbc_btnSaveConfiguration.gridx = 0;
		gbc_btnSaveConfiguration.gridy = 2;
		pnlFooter.add(btnSaveConfiguration, gbc_btnSaveConfiguration);
		
		JSeparator verticalSeparator = new JSeparator();
		verticalSeparator.setOrientation(SwingConstants.VERTICAL);
		verticalSeparator.setForeground(new Color(169, 169, 169));
		verticalSeparator.setBackground(Color.WHITE);
		GridBagConstraints gbc_verticalSeparator = new GridBagConstraints();
		gbc_verticalSeparator.anchor = GridBagConstraints.WEST;
		gbc_verticalSeparator.fill = GridBagConstraints.VERTICAL;
		gbc_verticalSeparator.gridheight = 2;
		gbc_verticalSeparator.insets = new Insets(0, 0, 0, 5);
		gbc_verticalSeparator.gridx = 2;
		gbc_verticalSeparator.gridy = 1;
		pnlFooter.add(verticalSeparator, gbc_verticalSeparator);
		
		JCheckBox ckbPrimaryEdge = new JCheckBox("Primary Outlines");
		ckbPrimaryEdge.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ckbPrimaryEdge = new GridBagConstraints();
		gbc_ckbPrimaryEdge.anchor = GridBagConstraints.WEST;
		gbc_ckbPrimaryEdge.insets = new Insets(0, 0, 0, 5);
		gbc_ckbPrimaryEdge.gridx = 4;
		gbc_ckbPrimaryEdge.gridy = 2;
		pnlFooter.add(ckbPrimaryEdge, gbc_ckbPrimaryEdge);
		
		JCheckBox ckbSecondaryEdge = new JCheckBox("Secondary Outlines");
		ckbSecondaryEdge.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ckbSecondaryEdge = new GridBagConstraints();
		gbc_ckbSecondaryEdge.anchor = GridBagConstraints.WEST;
		gbc_ckbSecondaryEdge.insets = new Insets(0, 0, 0, 5);
		gbc_ckbSecondaryEdge.gridx = 5;
		gbc_ckbSecondaryEdge.gridy = 2;
		pnlFooter.add(ckbSecondaryEdge, gbc_ckbSecondaryEdge);
		
		JCheckBox ckbTertiaryEdge = new JCheckBox("Tertiary Outlines");
		ckbTertiaryEdge.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ckbTertiaryEdge = new GridBagConstraints();
		gbc_ckbTertiaryEdge.anchor = GridBagConstraints.WEST;
		gbc_ckbTertiaryEdge.insets = new Insets(0, 0, 0, 5);
		gbc_ckbTertiaryEdge.gridx = 6;
		gbc_ckbTertiaryEdge.gridy = 2;
		pnlFooter.add(ckbTertiaryEdge, gbc_ckbTertiaryEdge);
		
		
	
		
		/*
		 * Action listeners for panel dynamics here to avoid variable scope issues.
		 * 
		 */
		
		
		/*
		 * return to main window. 
		 */
		
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainScreen MainGui = new MainScreen(paramManager);
				MainGui.setLocationRelativeTo(null);
				MainGui.setVisible(true);
				Window win = SwingUtilities.getWindowAncestor(btnBackToMenu);
				win.dispose();
			}
		});
		
		
		
		/*
		 * Disable and enable the tertiary panel components with a checkbox. 
		 */
		
		ckbTertiary.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(!ckbTertiary.isSelected()) {
					for(Component comp:guiHelper.getComponents(pnlTertiary)) {
						comp.setEnabled(false);
					}
					ckbTertiary.setEnabled(true);
					lblTertiaryOverlay.setEnabled(false);
					ckbTertiaryDisplay.setEnabled(false);
					ckbTertiaryDisplayOverlay.setEnabled(false);

				} else if(ckbTertiary.isSelected()) {
					for(Component comp:guiHelper.getComponents(pnlTertiary)) {
						comp.setEnabled(true);
						txtTertiaryMethodThreshold.setEnabled(false);
					}
					lblTertiaryOverlay.setEnabled(true);
					ckbTertiaryDisplay.setEnabled(true);
					ckbTertiaryDisplayOverlay.setEnabled(true);

				}
			}
		});
		
		
		
		/*
		 * Display the correct input panels following background method selection.
		 */
		
		cbPrimaryBackground.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				BackgroundType bg = (BackgroundType) cbPrimaryBackground.getSelectedItem();

				switch (bg) {
				case NONE:
					pnlPrimaryBGFirstBlur.setVisible(false);
					pnlPrimaryBGSecondBlur.setVisible(false);
					pnlPrimaryBGRadius.setVisible(false);
					break;
				case DOG:
					pnlPrimaryBGFirstBlur.setVisible(true);
					pnlPrimaryBGSecondBlur.setVisible(true);
					pnlPrimaryBGRadius.setVisible(false);
					break;
				default:
					pnlPrimaryBGFirstBlur.setVisible(true);
					pnlPrimaryBGSecondBlur.setVisible(false);
					pnlPrimaryBGRadius.setVisible(false);
					break;

				}
			}
		});

		cbSecondaryBackground.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				BackgroundType bg = (BackgroundType) cbSecondaryBackground.getSelectedItem();

				switch (bg) {
				case NONE:
					pnlSecondaryBGFirstBlur.setVisible(false);
					pnlSecondaryBGSecondBlur.setVisible(false);
					pnlSecondaryBGRadius.setVisible(false);
					break;
				case DOG:
					pnlSecondaryBGFirstBlur.setVisible(true);
					pnlSecondaryBGSecondBlur.setVisible(true);
					pnlSecondaryBGRadius.setVisible(false);
					break;
				default:
					pnlSecondaryBGFirstBlur.setVisible(true);
					pnlSecondaryBGSecondBlur.setVisible(false);
					pnlSecondaryBGRadius.setVisible(false);
					break;
				}

			}
		});
		
		cbTertiaryBackground.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				BackgroundType bg = (BackgroundType) cbTertiaryBackground.getSelectedItem();

				switch (bg) {
				case NONE:
					pnlTertiaryBGFirstBlur.setVisible(false);
					pnlTertiaryBGSecondBlur.setVisible(false);
					pnlTertiaryBGRadius.setVisible(false);
					break;
				case DOG:
					pnlTertiaryBGFirstBlur.setVisible(true);
					pnlTertiaryBGSecondBlur.setVisible(true);
					pnlTertiaryBGRadius.setVisible(false);
					break;
				default:
					pnlTertiaryBGFirstBlur.setVisible(true);
					pnlTertiaryBGSecondBlur.setVisible(false);
					pnlTertiaryBGRadius.setVisible(false);
					break;
				}

			}
		});
		

		/* 
		 * Display the correct input panels following segmentation method selection for primary, secondary and tertiary panels.
		 */
		
		cbPrimaryMethod.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				String primaryMethod = cbPrimaryMethod.getSelectedItem().toString(); 
				
				if(primaryMethod.equals("Trained Classifer")) {
					btnBrowsePrimaryClassifer.setVisible(true);
					txtPrimaryClassiferDirectory.setVisible(true);
					pnlPrimarySpotSize.setVisible(false);
					pnlPrimaryMethodThreshold.setVisible(false);
					cbPrimaryMethodThreshold.setVisible(false);
				} else {
					btnBrowsePrimaryClassifer.setVisible(false);
					txtPrimaryClassiferDirectory.setVisible(false);
					pnlPrimarySpotSize.setVisible(true);
					pnlPrimaryMethodThreshold.setVisible(true);
					cbPrimaryMethodThreshold.setVisible(true);
					
				}
			}
		});
		
		cbSecondaryMethod.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				String secondaryMethod = cbSecondaryMethod.getSelectedItem().toString();
				
				if(secondaryMethod.equals("Trained Classifer")) {
					pnlSecondarySpotSize.setVisible(false);
					pnlSecondaryThreshold.setVisible(false);
					cbSecondaryMethodThreshold.setVisible(false);
					
					btnBrowseSecondaryClassifer.setVisible(true);
					txtSecondaryClassiferDirectory.setVisible(true);
				} else {
					pnlSecondarySpotSize.setVisible(true);
					pnlSecondaryThreshold.setVisible(true);
					cbSecondaryMethodThreshold.setVisible(true);
					
					btnBrowseSecondaryClassifer.setVisible(false);
					txtSecondaryClassiferDirectory.setVisible(false);
					
				}
			}
		});
		
		cbTertiaryMethod.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				String tertiaryMethod = cbTertiaryMethod.getSelectedItem().toString();
				
				if(tertiaryMethod.equals("Trained Classifer")) {
					pnlTertiarySpotSize.setVisible(false);
					pnlTertiaryThreshold.setVisible(false);
					cbTertiaryMethodThreshold.setVisible(false);
					
					btnBrowseTertiaryClassifer.setVisible(true);
					txtTertiaryClassiferDirectory.setVisible(true);
				} else {
					pnlTertiarySpotSize.setVisible(true);
					pnlTertiaryThreshold.setVisible(true);
					cbTertiaryMethodThreshold.setVisible(true);
					
					btnBrowseTertiaryClassifer.setVisible(false);
					txtTertiaryClassiferDirectory.setVisible(false);
					
				}	
			}
		});
		
		

		/* 
		 * Display the correct input panels following filter method selection for primary, secondary and tertiary panels.
		 */
		
		cbPrimaryFilter.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
			
				FilterType ft = (FilterType) cbPrimaryFilter.getSelectedItem();

				switch (ft) {
				case NONE:
					pnlPrimarySecondBlur.setVisible(false);
					pnlPrimaryFirstBlur.setVisible(false);
					break;
				case DOG:
					pnlPrimarySecondBlur.setVisible(true);
					pnlPrimaryFirstBlur.setVisible(true);
					break;
				default:
					pnlPrimarySecondBlur.setVisible(false);
					pnlPrimaryFirstBlur.setVisible(true);
					break;
				}
				
			}
		});
		
		cbSecondaryFilter.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				FilterType ft = (FilterType) cbSecondaryFilter.getSelectedItem();

				switch (ft) {
				case NONE:
					pnlSecondarySecondBlur.setVisible(false);
					pnlSecondaryFirstBlur.setVisible(false);
					break;
				case DOG:
					pnlSecondarySecondBlur.setVisible(true);
					pnlSecondaryFirstBlur.setVisible(true);
					break;
				default:
					pnlSecondarySecondBlur.setVisible(false);
					pnlSecondaryFirstBlur.setVisible(true);
					break;
				}

			}
		});
		
		cbTertiaryFilter.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				FilterType ft = (FilterType) cbTertiaryFilter.getSelectedItem();

				switch (ft) {
				case NONE:
					pnlTertiarySecondBlur.setVisible(false);
					pnlTertiaryFirstBlur.setVisible(false);
					break;
				case DOG:
					pnlTertiarySecondBlur.setVisible(true);
					pnlTertiaryFirstBlur.setVisible(true);
					break;
				default:
					pnlTertiarySecondBlur.setVisible(false);
					pnlTertiaryFirstBlur.setVisible(true);
					break;
				}
			}
		});
		
		
		
		/*
		 * Enable and disable the threshold panel following method selections for primary, secondary and tertiary panels.
		 */
		
		cbPrimaryMethodThreshold.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				ThresholdType tt = (ThresholdType) cbPrimaryMethodThreshold.getSelectedItem();

				switch (tt) {
				case GREATERCONSTANT:
					txtPrimaryMethodThreshold.setEnabled(true);
					break;
				default:
					txtPrimaryMethodThreshold.setEnabled(false);
					break;
				}
			}
		});
		
		cbSecondaryMethodThreshold.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				ThresholdType tt = (ThresholdType) cbSecondaryMethodThreshold.getSelectedItem();

				switch (tt) {
				case GREATERCONSTANT:
					txtSecondaryMethodThreshold.setEnabled(true);
					break;
				default:
					txtSecondaryMethodThreshold.setEnabled(false);
					break;

				}
			}
		});

		cbTertiaryMethodThreshold.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {

				ThresholdType tt = (ThresholdType) cbTertiaryMethodThreshold.getSelectedItem();

				switch (tt) {
				case GREATERCONSTANT:
					txtTertiaryMethodThreshold.setEnabled(true);
					break;
				default:
					txtTertiaryMethodThreshold.setEnabled(false);
					break;

				}
			}
		});
		
		/*
		 * Merge selected channel outlines together.
		 */
		
		btnUpdateOverlays.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Boolean primary = ckbPrimaryLabel.isSelected();
				Boolean primaryEdge = ckbPrimaryEdge.isSelected();
				
				Boolean secondary = ckbSecondaryLabel.isSelected();
				Boolean secondaryEdge = ckbSecondaryEdge.isSelected();
				
				Boolean tertiary = ckbTertiaryLabel.isSelected();
				Boolean tertiaryEdge = ckbTertiaryEdge.isSelected();
				
				if(!primary && !primaryEdge && !secondary && !secondaryEdge && !tertiary && !tertiaryEdge) {
					IJ.showMessage("Please select at least one outline to display.");
				} else {
					
					try {
						ArrayList<ImagePlus> labels = new ArrayList<>();
						
						// Primary
						if (primary) {
							if (primaryOutput != null) {
								ImagePlus primaryLab = primaryOutput.duplicate();
								labels.add(primaryLab);
							} 
						}
						
						if (primaryEdge) {
							if(primaryOutput != null) {
								ImagePlus primaryLab = primaryOutput.duplicate();
								ImagePlus primaryOutlines = LabelEditor.detectEdgesLabelled(primaryLab);
								labels.add(primaryOutlines);
							}
						}
						
						// Secondary
						if (secondary) {
							if (secondaryOutput != null) {
								ImagePlus secondaryLab = secondaryOutput.duplicate();
								labels.add(secondaryLab);
							} 
						}
						
						if (secondaryEdge) {
							if(secondaryOutput != null) {
								ImagePlus secondaryLab = secondaryOutput.duplicate();
								ImagePlus secondaryOutlines = LabelEditor.detectEdgesLabelled(secondaryLab);
								labels.add(secondaryOutlines);
							}
						}
						
						// Tertiary
						if (tertiary) {
							if (tertiaryOutput != null) {
								ImagePlus tertiaryLab = tertiaryOutput.duplicate();
								labels.add(tertiaryLab);
							} 
						}
						
						if (tertiaryEdge) {
							if(tertiaryOutput != null) {
								ImagePlus tertiaryLab = tertiaryOutput.duplicate();
								ImagePlus tertiaryOutlines = LabelEditor.detectEdgesLabelled(tertiaryLab);
								labels.add(tertiaryOutlines);
							}
						}
						

						ImagePlus[] selectedLabelsArray = new ImagePlus[labels.size()];
						selectedLabelsArray = labels.toArray(selectedLabelsArray);

						ImagePlus compositeImage = optimize.createComposite(selectedLabelsArray);

						// display
						compositeImage.setTitle("Label Overlays");
						compositeImage.show();

						labels.clear();
					} catch (Exception e1) {
						IJ.showMessage("Process all selected channel before combining their outputs.");
						e1.printStackTrace();
					}
				}
			}
		});
		
		
		btnLoadParameters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String paramDir = IJ.getFilePath("Select the parameter file");
				System.out.println("Getting file from: " + paramDir);
				
				
				try {
					ParameterCollection param = ParameterCollection.loadParameterCollection(paramDir);
					
					// extract parameter components
					ObjectParameters primaryObject = param.getPrimaryObject();
					ObjectParameters secondaryObject = param.getSecondaryObject();
					ObjectParameters tertiaryObject = param.getTertiaryObject();
					
					// set primary inputs
					cbPrimaryChannel.setSelectedIndex(primaryObject.getChannel());
					cbPrimaryBackground.setSelectedItem(primaryObject.getBackgroundParameters().getBackgroundType());
					txtPrimaryS1X.setText(String.valueOf(primaryObject.getBackgroundParameters().getSigma1().getX()));
					txtPrimaryS1Y.setText(String.valueOf(primaryObject.getBackgroundParameters().getSigma1().getY()));
					txtPrimaryS1Z.setText(String.valueOf(primaryObject.getBackgroundParameters().getSigma1().getZ()));
					txtPrimaryS2X.setText(String.valueOf(primaryObject.getBackgroundParameters().getSigma2().getX()));
					txtPrimaryS2Y.setText(String.valueOf(primaryObject.getBackgroundParameters().getSigma2().getY()));
					txtPrimaryS2Z.setText(String.valueOf(primaryObject.getBackgroundParameters().getSigma2().getZ()));
					
					cbPrimaryFilter.setSelectedItem(primaryObject.getFilterParameters().getFilterType());
					txtPriFilterX.setText(String.valueOf(primaryObject.getFilterParameters().getSigma1().getX()));
					txtPriFilterY.setText(String.valueOf(primaryObject.getFilterParameters().getSigma1().getY()));
					txtPriFilterZ.setText(String.valueOf(primaryObject.getFilterParameters().getSigma1().getZ()));
					txtPriFilter2X.setText(String.valueOf(primaryObject.getFilterParameters().getSigma2().getX()));
					txtPriFilter2Y.setText(String.valueOf(primaryObject.getFilterParameters().getSigma2().getY()));
					txtPriFilter2Z.setText(String.valueOf(primaryObject.getFilterParameters().getSigma2().getZ()));
					
					cbPrimaryMethod.setSelectedItem(primaryObject.getMethodParameters().getMethodType());
					txtPriSpotX.setText(String.valueOf(primaryObject.getMethodParameters().getSigma().getX()));
					txtPriSpotY.setText(String.valueOf(primaryObject.getMethodParameters().getSigma().getY()));
					txtPriSpotZ.setText(String.valueOf(primaryObject.getMethodParameters().getSigma().getZ()));
					cbPrimaryMethodThreshold.setSelectedItem(primaryObject.getMethodParameters().getThresholdType());
					txtPrimaryMethodThreshold.setText(String.valueOf(primaryObject.getMethodParameters().getThresholdSize()));
		
					// set secondary inputs
					cbSecondaryChannel.setSelectedIndex(secondaryObject.getChannel());
					cbSecondaryBackground.setSelectedItem(secondaryObject.getBackgroundParameters().getBackgroundType());
					txtSecondaryS1X.setText(String.valueOf(secondaryObject.getBackgroundParameters().getSigma1().getX()));
					txtSecondaryS1Y.setText(String.valueOf(secondaryObject.getBackgroundParameters().getSigma1().getY()));
					txtSecondaryS1Z.setText(String.valueOf(secondaryObject.getBackgroundParameters().getSigma1().getZ()));
					txtSecondaryS2X.setText(String.valueOf(secondaryObject.getBackgroundParameters().getSigma2().getX()));
					txtSecondaryS2Y.setText(String.valueOf(secondaryObject.getBackgroundParameters().getSigma2().getY()));
					txtSecondaryS2Z.setText(String.valueOf(secondaryObject.getBackgroundParameters().getSigma2().getZ()));
					
					cbSecondaryFilter.setSelectedItem(secondaryObject.getFilterParameters().getFilterType());
					txtSecFilterX.setText(String.valueOf(secondaryObject.getFilterParameters().getSigma1().getX()));
					txtSecFilterY.setText(String.valueOf(secondaryObject.getFilterParameters().getSigma1().getY()));
					txtSecFilterZ.setText(String.valueOf(secondaryObject.getFilterParameters().getSigma1().getZ()));
					txtSecFilter2X.setText(String.valueOf(secondaryObject.getFilterParameters().getSigma2().getX()));
					txtSecFilter2Y.setText(String.valueOf(secondaryObject.getFilterParameters().getSigma2().getY()));
					txtSecFilter2Z.setText(String.valueOf(secondaryObject.getFilterParameters().getSigma2().getZ()));
					
					cbSecondaryMethod.setSelectedItem(secondaryObject.getMethodParameters().getMethodType());
					txtSecondaryMethodX.setText(String.valueOf(secondaryObject.getMethodParameters().getSigma().getX()));
					txtSecondaryMethodY.setText(String.valueOf(secondaryObject.getMethodParameters().getSigma().getY()));
					txtSecondaryMethodZ.setText(String.valueOf(secondaryObject.getMethodParameters().getSigma().getZ()));
					cbSecondaryMethodThreshold.setSelectedItem(secondaryObject.getMethodParameters().getThresholdType());
					txtSecondaryMethodThreshold.setText(String.valueOf(secondaryObject.getMethodParameters().getThresholdSize()));
					
					// set tertiary inputs
					cbTertiaryChannel.setSelectedIndex(tertiaryObject.getChannel());
					cbTertiaryBackground.setSelectedItem(tertiaryObject.getBackgroundParameters().getBackgroundType());
					txtTertiaryS1X.setText(String.valueOf(tertiaryObject.getBackgroundParameters().getSigma1().getX()));
					txtTertiaryS1Y.setText(String.valueOf(tertiaryObject.getBackgroundParameters().getSigma1().getY()));
					txtTertiaryS1Z.setText(String.valueOf(tertiaryObject.getBackgroundParameters().getSigma1().getZ()));
					txtTertiaryS2X.setText(String.valueOf(tertiaryObject.getBackgroundParameters().getSigma2().getX()));
					txtTertiaryS2Y.setText(String.valueOf(tertiaryObject.getBackgroundParameters().getSigma2().getY()));
					txtTertiaryS2Z.setText(String.valueOf(tertiaryObject.getBackgroundParameters().getSigma2().getZ()));
					
					cbTertiaryFilter.setSelectedItem(tertiaryObject.getFilterParameters().getFilterType());
					txtTertFilterX.setText(String.valueOf(tertiaryObject.getFilterParameters().getSigma1().getX()));
					txtTertFilterY.setText(String.valueOf(tertiaryObject.getFilterParameters().getSigma1().getY()));
					txtTertFilterZ.setText(String.valueOf(tertiaryObject.getFilterParameters().getSigma1().getZ()));
					txtTertFilter2X.setText(String.valueOf(tertiaryObject.getFilterParameters().getSigma2().getX()));
					txtTertFilter2Y.setText(String.valueOf(tertiaryObject.getFilterParameters().getSigma2().getY()));
					txtTertFilter2Z.setText(String.valueOf(tertiaryObject.getFilterParameters().getSigma2().getZ()));
					
					cbTertiaryMethod.setSelectedItem(tertiaryObject.getMethodParameters().getMethodType());
					txtTertiaryMethodX.setText(String.valueOf(tertiaryObject.getMethodParameters().getSigma().getX()));
					txtTertiaryMethodY.setText(String.valueOf(tertiaryObject.getMethodParameters().getSigma().getY()));
					txtTertiaryMethodZ.setText(String.valueOf(tertiaryObject.getMethodParameters().getSigma().getZ()));
					cbTertiaryMethodThreshold.setSelectedItem(tertiaryObject.getMethodParameters().getThresholdType());
					txtTertiaryMethodThreshold.setText(String.valueOf(tertiaryObject.getMethodParameters().getThresholdSize()));
					
					System.out.println("FOCUST paramter file found and loaded.");
				} catch (IOException e1) {
					System.out.println("Could not locate or load FOCUST parameter file.");
					e1.printStackTrace();
				}
			}
		});
		
		
	
		btnSaveConfiguration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String fileDir = IJ.getDir("Select a directory to save the parameter file.");
				
				
				String inputDir = txtInputDir.getText();
				
				// Primary Object
				ObjectParameters primaryObject = ObjectParameters.builder().
						channel(cbPrimaryChannel.getSelectedIndex()).
						backgroundParameters(
								BackgroundParameters.builder().
										backgroundType((BackgroundType) cbPrimaryBackground.getSelectedItem()).
										sigma1(Vector3D.builder().x(Double.parseDouble(txtPrimaryS1X.getText())).y(Double.parseDouble(txtPrimaryS1Y.getText())).z(Double.parseDouble(txtPrimaryS1Z.getText())).build()).
										sigma2(Vector3D.builder().x(Double.parseDouble(txtPrimaryS2X.getText())).y(Double.parseDouble(txtPrimaryS2Y.getText())).z(Double.parseDouble(txtPrimaryS2Z.getText())).build()).
										build()
						).
						filterParameters(
								FilterParameters.builder().
										filterType((FilterType) cbPrimaryFilter.getSelectedItem()).
										sigma1(Vector3D.builder().x(Double.parseDouble(txtPriFilterX.getText())).y(Double.parseDouble(txtPriFilterY.getText())).z(Double.parseDouble(txtPriFilterZ.getText())).build()).
										sigma2(Vector3D.builder().x(Double.parseDouble(txtPriFilter2X.getText())).y(Double.parseDouble(txtPriFilter2Y.getText())).z(Double.parseDouble(txtPriFilter2Z.getText())).build()).
										build()
						).
						methodParameters(
								MethodParameters.builder().
										methodType((MethodTypes) cbPrimaryMethod.getSelectedItem()).
										sigma(Vector3D.builder().x(Double.parseDouble(txtPriSpotX.getText())).y(Double.parseDouble(txtPriSpotY.getText())).z(Double.parseDouble(txtPriSpotZ.getText())).build()).
										thresholdType((ThresholdType) cbPrimaryMethodThreshold.getSelectedItem()).
										thresholdSize(Double.parseDouble(txtPrimaryMethodThreshold.getText())).
										classifierFilename(txtPrimaryClassiferDirectory.getText()).
										build()
						).
						build();

				// Secondary Object
				ObjectParameters secondaryObject = ObjectParameters.builder().
						channel(cbSecondaryChannel.getSelectedIndex()).
						backgroundParameters(
								BackgroundParameters.builder().
										backgroundType((BackgroundType) cbSecondaryBackground.getSelectedItem()).
										sigma1(Vector3D.builder().x(Double.parseDouble(txtSecondaryS1X.getText())).y(Double.parseDouble(txtSecondaryS1Y.getText())).z(Double.parseDouble(txtSecondaryS1Z.getText())).build()).
										sigma2(Vector3D.builder().x(Double.parseDouble(txtSecondaryS2X.getText())).y(Double.parseDouble(txtSecondaryS2Y.getText())).z(Double.parseDouble(txtSecondaryS2Z.getText())).build()).
										build()
						).
						filterParameters(
								FilterParameters.builder().
										filterType((FilterType) cbSecondaryFilter.getSelectedItem()).
										sigma1(Vector3D.builder().x(Double.parseDouble(txtSecFilterX.getText())).y(Double.parseDouble(txtSecFilterY.getText())).z(Double.parseDouble(txtSecFilterZ.getText())).build()).
										sigma2(Vector3D.builder().x(Double.parseDouble(txtSecFilter2X.getText())).y(Double.parseDouble(txtSecFilter2Y.getText())).z(Double.parseDouble(txtSecFilter2Z.getText())).build()).
										build()
						).
						methodParameters(
								MethodParameters.builder().
										methodType((MethodTypes) cbSecondaryMethod.getSelectedItem()).
										sigma(Vector3D.builder().x(Double.parseDouble(txtSecondaryMethodX.getText())).y(Double.parseDouble(txtSecondaryMethodY.getText())).z(Double.parseDouble(txtSecondaryMethodZ.getText())).build()).
										thresholdType((ThresholdType) cbSecondaryMethodThreshold.getSelectedItem()).
										thresholdSize(Double.parseDouble(txtSecondaryMethodThreshold.getText())).
										classifierFilename(txtSecondaryClassiferDirectory.getText()).
										build()
						).
						build();

				//Tertiary Object
				ObjectParameters tertiaryObject = ObjectParameters.builder().
						channel(cbTertiaryChannel.getSelectedIndex()).
						backgroundParameters(
								BackgroundParameters.builder().
										backgroundType((BackgroundType) cbTertiaryBackground.getSelectedItem()).
										sigma1(Vector3D.builder().x(Double.parseDouble(txtTertiaryS1X.getText())).y(Double.parseDouble(txtTertiaryS1Y.getText())).z(Double.parseDouble(txtTertiaryS1Z.getText())).build()).
										sigma2(Vector3D.builder().x(Double.parseDouble(txtTertiaryS2X.getText())).y(Double.parseDouble(txtTertiaryS2Y.getText())).z(Double.parseDouble(txtTertiaryS2Z.getText())).build()).
										build()
						).
						filterParameters(
								FilterParameters.builder().
										filterType((FilterType) cbTertiaryFilter.getSelectedItem()).
										sigma1(Vector3D.builder().x(Double.parseDouble(txtTertFilterX.getText())).y(Double.parseDouble(txtTertFilterY.getText())).z(Double.parseDouble(txtTertFilterZ.getText())).build()).
										sigma2(Vector3D.builder().x(Double.parseDouble(txtTertFilter2X.getText())).y(Double.parseDouble(txtTertFilter2Y.getText())).z(Double.parseDouble(txtTertFilter2Z.getText())).build()).
										build()
						).
						methodParameters(
								MethodParameters.builder().
										methodType((MethodTypes) cbTertiaryMethod.getSelectedItem()).
										sigma(Vector3D.builder().x(Double.parseDouble(txtTertiaryMethodX.getText())).y(Double.parseDouble(txtTertiaryMethodY.getText())).z(Double.parseDouble(txtTertiaryMethodZ.getText())).build()).
										thresholdType((ThresholdType) cbTertiaryMethodThreshold.getSelectedItem()).
										thresholdSize(Double.parseDouble(txtTertiaryMethodThreshold.getText())).
										classifierFilename(txtTertiaryClassiferDirectory.getText()).
										build()
						).
						build();
				
				// Stratify label parameters
				StratifyParameters stratifyParameters = StratifyParameters.builder().
						primary25(false).
						primary50(false).
						secondary25(false).
						secondary50(false).
						tertiary25(false).
						tertiary50(false).
						build();
				
				// Skeletonize label parameters
				SkeletonParameters skeletonParameters = SkeletonParameters.builder().
						primary(false).
						secondary(false).
						tertairy(false).
						build();
				
				// build the final data object
				ParameterCollection parameterCollection = ParameterCollection.builder().
						inputDir(inputDir).
						outputDir(fileDir).
						analysisOnly(false).
						primaryObject(primaryObject).
						secondaryObject(secondaryObject).
						skeletonParamters(skeletonParameters).
						stratifyParameters(stratifyParameters).
						tertiaryObject(tertiaryObject).
						killBorderType(selectedKillBorderOption).
						tertiaryIsDifference(true).
						processTertiary(ckbTertiary.isSelected()).
						build();
				
				
				// save the parameter object
				try {
					ParameterCollection.saveParameterCollection(parameterCollection, "/FOCUST-Optimization-File.json");
				} catch (IOException e1) {
					System.out.println("Unable to save FOCUST parameter file.");
					e1.printStackTrace();
				}
				
				
				
			}
			
		});
		
		
		
		
		
		
	}


	
	
	private static class __Tmp {
		private static void __tmp() {
			  javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
		}
	}
	
	
	
}
