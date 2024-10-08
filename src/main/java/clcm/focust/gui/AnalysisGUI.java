package clcm.focust.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;

import static clcm.focust.utility.SwingIJLoggerUtils.ijLog;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.awt.event.ItemEvent;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.border.MatteBorder;
import clcm.focust.data.DatumUpdateService;
import clcm.focust.filter.BackgroundType;
import clcm.focust.filter.FilterType;
import clcm.focust.mode.ModeProcess;
import clcm.focust.mode.ModeType;
import clcm.focust.filter.Vector3D;
import clcm.focust.parameters.*;
import clcm.focust.segmentation.MethodTypes;
import clcm.focust.threshold.ThresholdType;
import clcm.focust.utility.KillBorderTypes;
import ij.IJ;

import javax.swing.JTabbedPane;
import javax.swing.JProgressBar;

/**
 * Built with WindowsBuilder Editor
 * @author SebastianAmos
 *
 */

public class AnalysisGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtInputDir;
	private String inputDir;
	private String outputDir;
	private JTextField txtOutputDir;
	private JTextField txtC1;
	private JTextField txtC2;
	private JTextField txtC3;
	private JTextField txtC4;
	private JTextField txtGroupingInfo;
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
	private JComboBox<ModeType> cbAnalysisMode = new JComboBox<>();
	private final ButtonGroup killBordersChoice = new ButtonGroup();
	private KillBorderTypes selectedKillBorderOption;
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
	private JTextField txtPrimaryMethodThreshold;
	private JTextField txtPriFilter2X;
	private JTextField txtPriFilter2Y;
	private JTextField txtPriFilter2Z;
	private JTextField txtSecFilter2X;
	private JTextField txtSecFilter2Y;
	private JTextField txtSecFilter2Z;
	private JTextField txtTertFilter2X;
	private JTextField txtTertFilter2Y;
	private JTextField txtTertFilter2Z;
	private String[] channelNumbers = {"1", "2", "3", "4", "-"};
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			AnalysisGUI gui = new AnalysisGUI(null);
			gui.setVisible(true);
			gui.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		});
	}

	/**
	 * Create the frame.
	 */
	public AnalysisGUI(DatumUpdateService<ParameterCollection> paramManager) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnalysisGUI.class.getResource("/clcm/focust/resources/icon.png")));
		setTitle("FOCUST: Run Analysis");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1159, 643);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		GuiHelper guiHelper = new GuiHelper();
		
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{801, 0};
		gbl_contentPane.rowHeights = new int[]{149, 218, 50, 0};
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
		gbl_pnlHeader.columnWidths = new int[]{0, 0, 86, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pnlHeader.rowHeights = new int[]{0, 0, 0, 0, 19, 0};
		gbl_pnlHeader.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlHeader.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlHeader.setLayout(gbl_pnlHeader);
		
		JLabel lblNewLabel = new JLabel("Process a Dataset:");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		pnlHeader.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("You must have optimized your segmentation parameters.");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel_1.gridwidth = 7;
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		pnlHeader.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					Desktop.getDesktop().browse(new URL("https://sebastianamos.github.io/FOCUST-Plugin-Site/").toURI());
				} catch (IOException | URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnHelp.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnHelp = new GridBagConstraints();
		gbc_btnHelp.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHelp.gridwidth = 4;
		gbc_btnHelp.insets = new Insets(5, 0, 0, 5);
		gbc_btnHelp.gridx = 8;
		gbc_btnHelp.gridy = 0;
		pnlHeader.add(btnHelp, gbc_btnHelp);
		
		JLabel lblNewLabel_2 = new JLabel("Select an input directory:");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		pnlHeader.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JButton btnBrowseInput = new JButton("Browse");
		btnBrowseInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputDir = IJ.getDir("Select an Input Directory");
				txtInputDir.setText(inputDir.toString());
			}
		});
		btnBrowseInput.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnBrowseInput = new GridBagConstraints();
		gbc_btnBrowseInput.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBrowseInput.insets = new Insets(0, 0, 5, 5);
		gbc_btnBrowseInput.gridx = 1;
		gbc_btnBrowseInput.gridy = 1;
		pnlHeader.add(btnBrowseInput, gbc_btnBrowseInput);
		
		txtInputDir = new JTextField();
		txtInputDir.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_txtInputDir = new GridBagConstraints();
		gbc_txtInputDir.gridwidth = 6;
		gbc_txtInputDir.fill = GridBagConstraints.BOTH;
		gbc_txtInputDir.anchor = GridBagConstraints.WEST;
		gbc_txtInputDir.insets = new Insets(0, 0, 5, 5);
		gbc_txtInputDir.gridx = 2;
		gbc_txtInputDir.gridy = 1;
		pnlHeader.add(txtInputDir, gbc_txtInputDir);
		txtInputDir.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Separate output directory?");
		lblNewLabel_3.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 2;
		pnlHeader.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JButton btnBrowseOutput = new JButton("Browse");
		btnBrowseOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				outputDir = IJ.getDir("Select an Input Directory");
				txtOutputDir.setText(outputDir.toString());
			}
		});
		btnBrowseOutput.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnBrowseOutput = new GridBagConstraints();
		gbc_btnBrowseOutput.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBrowseOutput.insets = new Insets(0, 0, 5, 5);
		gbc_btnBrowseOutput.gridx = 1;
		gbc_btnBrowseOutput.gridy = 2;
		pnlHeader.add(btnBrowseOutput, gbc_btnBrowseOutput);
		
		txtOutputDir = new JTextField();
		txtOutputDir.setFont(new Font("Arial", Font.PLAIN, 14));
		txtOutputDir.setColumns(10);
		GridBagConstraints gbc_txtOutputDir = new GridBagConstraints();
		gbc_txtOutputDir.gridwidth = 6;
		gbc_txtOutputDir.insets = new Insets(0, 0, 5, 5);
		gbc_txtOutputDir.fill = GridBagConstraints.BOTH;
		gbc_txtOutputDir.gridx = 2;
		gbc_txtOutputDir.gridy = 2;
		pnlHeader.add(txtOutputDir, gbc_txtOutputDir);
		
		JLabel lblNewLabel_5_4 = new JLabel("Select Analysis Mode: ");
		lblNewLabel_5_4.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_5_4 = new GridBagConstraints();
		gbc_lblNewLabel_5_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_4.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_4.gridx = 0;
		gbc_lblNewLabel_5_4.gridy = 3;
		pnlHeader.add(lblNewLabel_5_4, gbc_lblNewLabel_5_4);
		
		//String[] analysisOptions = {"None", "Basic", "Spheroid", "Single Cells", "Speckles"};
		//DefaultComboBoxModel<String> analysisModel = new DefaultComboBoxModel<String>(analysisOptions);
		//cbAnalysisMode = new JComboBox<>(new String[] {"None", "Spheroid", "Single Cells", "Speckles"});
		//cbAnalysisMode.setModel(new DefaultComboBoxModel(new String[] {"None", "Spheroid", "Single Cells", "Speckles"}));
		cbAnalysisMode.setModel(new DefaultComboBoxModel<>(ModeType.values()));
		//cbPrimaryBackground.setModel(new DefaultComboBoxModel<>(BackgroundType.values()));
		cbAnalysisMode.setSelectedIndex(0);
		cbAnalysisMode.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbAnalysisMode = new GridBagConstraints();
		gbc_cbAnalysisMode.anchor = GridBagConstraints.WEST;
		gbc_cbAnalysisMode.insets = new Insets(0, 0, 5, 5);
		gbc_cbAnalysisMode.gridx = 1;
		gbc_cbAnalysisMode.gridy = 3;
		pnlHeader.add(cbAnalysisMode, gbc_cbAnalysisMode);
		
		JCheckBox ckbAnalysisOnly = new JCheckBox("Analysis only mode?");
		ckbAnalysisOnly.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ckbAnalysisOnly = new GridBagConstraints();
		gbc_ckbAnalysisOnly.anchor = GridBagConstraints.WEST;
		gbc_ckbAnalysisOnly.insets = new Insets(0, 0, 5, 5);
		gbc_ckbAnalysisOnly.gridx = 2;
		gbc_ckbAnalysisOnly.gridy = 3;
		pnlHeader.add(ckbAnalysisOnly, gbc_ckbAnalysisOnly);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setForeground(new Color(169, 169, 169));
		separator_1_1.setBackground(Color.WHITE);
		GridBagConstraints gbc_separator_1_1 = new GridBagConstraints();
		gbc_separator_1_1.gridwidth = 12;
		gbc_separator_1_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_1_1.gridx = 0;
		gbc_separator_1_1.gridy = 4;
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
		gbl_pnlVariable.columnWidths = new int[] {0, 0, 0};
		gbl_pnlVariable.rowHeights = new int[] {35, 35, 35, 35, 35, 35, 0, 0, 104, 0};
		gbl_pnlVariable.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_pnlVariable.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlVariable.setLayout(gbl_pnlVariable);
		
		JPanel pnlKillBorders = new JPanel();
		GridBagConstraints gbc_pnlKillBorders = new GridBagConstraints();
		gbc_pnlKillBorders.anchor = GridBagConstraints.NORTHWEST;
		gbc_pnlKillBorders.gridwidth = 2;
		gbc_pnlKillBorders.insets = new Insets(0, 0, 5, 0);
		gbc_pnlKillBorders.gridx = 0;
		gbc_pnlKillBorders.gridy = 5;
		pnlVariable.add(pnlKillBorders, gbc_pnlKillBorders);
		
		JLabel lblNewLabel_7 = new JLabel("Kill borders?");
		lblNewLabel_7.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlKillBorders.add(lblNewLabel_7);
		
		// init as no
		selectedKillBorderOption = KillBorderTypes.NO;
		
		/*
		 *  Build button group from kill border enum model.
		 *  Default to NO.
		 *  Each time the selected button within the button group is updated, selectedKillBorderOption updates.		 
		 */
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
			if (type == KillBorderTypes.NO) {
				btn.setSelected(true);
			}
			pnlKillBorders.add(btn);
		}

		JLabel lblNewLabel_3_1 = new JLabel("Name Channel 1:");
		lblNewLabel_3_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_3_1 = new GridBagConstraints();
		gbc_lblNewLabel_3_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_3_1.gridx = 0;
		gbc_lblNewLabel_3_1.gridy = 1;
		pnlVariable.add(lblNewLabel_3_1, gbc_lblNewLabel_3_1);

		txtC1 = new JTextField();
		txtC1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_txtC1 = new GridBagConstraints();
		gbc_txtC1.insets = new Insets(0, 0, 5, 0);
		gbc_txtC1.fill = GridBagConstraints.BOTH;
		gbc_txtC1.gridx = 1;
		gbc_txtC1.gridy = 1;
		pnlVariable.add(txtC1, gbc_txtC1);
		txtC1.setColumns(10);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Name Channel 2:");
		lblNewLabel_3_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_3_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_3_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_3_1_1.gridx = 0;
		gbc_lblNewLabel_3_1_1.gridy = 2;
		pnlVariable.add(lblNewLabel_3_1_1, gbc_lblNewLabel_3_1_1);
		
		txtC2 = new JTextField();
		txtC2.setFont(new Font("Arial", Font.PLAIN, 14));
		txtC2.setColumns(10);
		GridBagConstraints gbc_txtC2 = new GridBagConstraints();
		gbc_txtC2.insets = new Insets(0, 0, 5, 0);
		gbc_txtC2.fill = GridBagConstraints.BOTH;
		gbc_txtC2.gridx = 1;
		gbc_txtC2.gridy = 2;
		pnlVariable.add(txtC2, gbc_txtC2);
		
		JLabel lblNewLabel_3_1_2 = new JLabel("Name Channel 3:");
		lblNewLabel_3_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_3_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_3_1_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1_2.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_3_1_2.gridx = 0;
		gbc_lblNewLabel_3_1_2.gridy = 3;
		pnlVariable.add(lblNewLabel_3_1_2, gbc_lblNewLabel_3_1_2);
		
		txtC3 = new JTextField();
		txtC3.setFont(new Font("Arial", Font.PLAIN, 14));
		txtC3.setColumns(10);
		GridBagConstraints gbc_txtC3 = new GridBagConstraints();
		gbc_txtC3.insets = new Insets(0, 0, 5, 0);
		gbc_txtC3.fill = GridBagConstraints.BOTH;
		gbc_txtC3.gridx = 1;
		gbc_txtC3.gridy = 3;
		pnlVariable.add(txtC3, gbc_txtC3);
		
		JLabel lblNewLabel_3_1_3 = new JLabel("Name Channel 4:");
		lblNewLabel_3_1_3.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_3_1_3 = new GridBagConstraints();
		gbc_lblNewLabel_3_1_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1_3.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_3_1_3.gridx = 0;
		gbc_lblNewLabel_3_1_3.gridy = 4;
		pnlVariable.add(lblNewLabel_3_1_3, gbc_lblNewLabel_3_1_3);
		
		txtC4 = new JTextField();
		txtC4.setFont(new Font("Arial", Font.PLAIN, 14));
		txtC4.setColumns(10);
		GridBagConstraints gbc_txtC4 = new GridBagConstraints();
		gbc_txtC4.insets = new Insets(0, 0, 5, 0);
		gbc_txtC4.fill = GridBagConstraints.BOTH;
		gbc_txtC4.gridx = 1;
		gbc_txtC4.gridy = 4;
		pnlVariable.add(txtC4, gbc_txtC4);
		
		JLabel lblNewLabel_3_1_4 = new JLabel("Grouping Info?");
		lblNewLabel_3_1_4.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_3_1_4 = new GridBagConstraints();
		gbc_lblNewLabel_3_1_4.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_3_1_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1_4.gridx = 0;
		gbc_lblNewLabel_3_1_4.gridy = 0;
		pnlVariable.add(lblNewLabel_3_1_4, gbc_lblNewLabel_3_1_4);
		
		txtGroupingInfo = new JTextField();
		txtGroupingInfo.setFont(new Font("Arial", Font.PLAIN, 14));
		txtGroupingInfo.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		pnlVariable.add(txtGroupingInfo, gbc_textField);
		
		JCheckBox ckbTertiaryObjectOption = new JCheckBox("Tertiary = Secondary - Primary?");
		ckbTertiaryObjectOption.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ckbCellsTertiaryOption = new GridBagConstraints();
		gbc_ckbCellsTertiaryOption.anchor = GridBagConstraints.WEST;
		gbc_ckbCellsTertiaryOption.gridwidth = 2;
		gbc_ckbCellsTertiaryOption.insets = new Insets(0, 5, 5, 0);
		gbc_ckbCellsTertiaryOption.gridx = 0;
		gbc_ckbCellsTertiaryOption.gridy = 6;
		pnlVariable.add(ckbTertiaryObjectOption, gbc_ckbCellsTertiaryOption);
		ckbTertiaryObjectOption.setVisible(true);
		
		JCheckBox ckbAdditionalProcessing = new JCheckBox("Process Labels");
		
		ckbAdditionalProcessing.setToolTipText("Tick the box to generate a 3D core and periphery (based on the target volume % for the core), calculate the intensity of all channels in the core and periphery regions. Ratios comparing channel intensity (core vs periphery) will also be calculated.");
		ckbAdditionalProcessing.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ckbSpheroidCoreVsPeriphery = new GridBagConstraints();
		gbc_ckbSpheroidCoreVsPeriphery.anchor = GridBagConstraints.WEST;
		gbc_ckbSpheroidCoreVsPeriphery.insets = new Insets(0, 5, 5, 5);
		gbc_ckbSpheroidCoreVsPeriphery.gridx = 0;
		gbc_ckbSpheroidCoreVsPeriphery.gridy = 7;
		pnlVariable.add(ckbAdditionalProcessing, gbc_ckbSpheroidCoreVsPeriphery);
		ckbAdditionalProcessing.setVisible(false);
		
		
		JCheckBox ckbProcessAllObjects = new JCheckBox("Process all");
		ckbProcessAllObjects.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxNewCheckBox.gridx = 1;
		gbc_chckbxNewCheckBox.gridy = 7;
		pnlVariable.add(ckbProcessAllObjects, gbc_chckbxNewCheckBox);
		ckbProcessAllObjects.setVisible(false);
		
	
		
		JTabbedPane pnlAdditionalProcessing = new JTabbedPane(JTabbedPane.TOP);
		pnlAdditionalProcessing.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_pnlStratify = new GridBagConstraints();
		gbc_pnlStratify.fill = GridBagConstraints.VERTICAL;
		gbc_pnlStratify.gridwidth = 2;
		gbc_pnlStratify.gridx = 0;
		gbc_pnlStratify.gridy = 8;
		pnlVariable.add(pnlAdditionalProcessing, gbc_pnlStratify);
		pnlAdditionalProcessing.setVisible(false);
		
		JPanel pnlProcessPrimary = new JPanel();
		pnlAdditionalProcessing.addTab("Primary", null, pnlProcessPrimary, null);
		
		JCheckBox ckbPri25Bands = new JCheckBox("25 % Bands");
		ckbPri25Bands.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlProcessPrimary.add(ckbPri25Bands);
		
		JCheckBox ckbPri50Bands = new JCheckBox("50 % Bands");
		ckbPri50Bands.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlProcessPrimary.add(ckbPri50Bands);
		
		JCheckBox ckbPriSkeletonize = new JCheckBox("Skeletonize");
		ckbPriSkeletonize.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlProcessPrimary.add(ckbPriSkeletonize);
		
		JPanel pnlProcessSecondary = new JPanel();
		pnlAdditionalProcessing.addTab("Secondary", null, pnlProcessSecondary, null);
		
		JCheckBox ckbSec25Bands = new JCheckBox("25 % Bands");
		ckbSec25Bands.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlProcessSecondary.add(ckbSec25Bands);
		
		JCheckBox ckbSec50Bands = new JCheckBox("50 % Bands");
		ckbSec50Bands.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlProcessSecondary.add(ckbSec50Bands);
		
		JCheckBox ckbSecSkeletonize = new JCheckBox("Skeletonize");
		ckbSecSkeletonize.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlProcessSecondary.add(ckbSecSkeletonize);
		
		JPanel pnlProcessTertiary = new JPanel();
		pnlAdditionalProcessing.addTab("Tertiary", null, pnlProcessTertiary, null);
		
		JCheckBox ckbTer25Bands = new JCheckBox("25 % Bands");
		ckbTer25Bands.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlProcessTertiary.add(ckbTer25Bands);
		
		JCheckBox ckbTer50Bands = new JCheckBox("50 % Bands");
		ckbTer50Bands.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlProcessTertiary.add(ckbTer50Bands);
		
		JCheckBox ckbTerSkeletonize = new JCheckBox("Skeletonize");
		ckbTerSkeletonize.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlProcessTertiary.add(ckbTerSkeletonize);

		JPanel pnlPrimary = new JPanel();
		pnlPrimary.setBorder(new MatteBorder(0, 1, 0, 1, (Color) new Color(169, 169, 169)));
		pnlMain.add(pnlPrimary);
		GridBagLayout gbl_pnlPrimary = new GridBagLayout();
		gbl_pnlPrimary.columnWidths = new int[] {95, 0};
		gbl_pnlPrimary.rowHeights = new int[] {30, 0, 0, 0, 0, 30, 0, 0, 35, 0, 30, 0};
		gbl_pnlPrimary.columnWeights = new double[]{1.0, 1.0};
		gbl_pnlPrimary.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		cbPrimaryChannel.setModel(new DefaultComboBoxModel(channelNumbers));
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
		
		JComboBox cbPrimaryBackground = new JComboBox();
		//cbPrimaryBackground.setModel(new DefaultComboBoxModel(new String[] {"None", "Default", "3D DoG", "3D Top Hat"}));
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
		txtPrimaryS1X.setText("1");
		txtPrimaryS1X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryS1X.setColumns(4);
		pnlPrimaryBGFirstBlur.add(txtPrimaryS1X);
		
		JLabel lblNewLabel_6_1_4 = new JLabel("Y");
		lblNewLabel_6_1_4.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_4.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGFirstBlur.add(lblNewLabel_6_1_4);
		
		txtPrimaryS1Y = new JTextField();
		txtPrimaryS1Y.setText("1");
		txtPrimaryS1Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryS1Y.setColumns(4);
		pnlPrimaryBGFirstBlur.add(txtPrimaryS1Y);
		
		JLabel lblNewLabel_6_2_4 = new JLabel("Z");
		lblNewLabel_6_2_4.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_4.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGFirstBlur.add(lblNewLabel_6_2_4);
		
		txtPrimaryS1Z = new JTextField();
		txtPrimaryS1Z.setText("1");
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
		txtPrimaryS2X.setText("1");
		txtPrimaryS2X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryS2X.setColumns(4);
		pnlPrimaryBGSecondBlur.add(txtPrimaryS2X);
		
		JLabel lblNewLabel_6_1_4_1 = new JLabel("Y");
		lblNewLabel_6_1_4_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_4_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_4_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGSecondBlur.add(lblNewLabel_6_1_4_1);
		
		txtPrimaryS2Y = new JTextField();
		txtPrimaryS2Y.setText("1");
		txtPrimaryS2Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryS2Y.setColumns(4);
		pnlPrimaryBGSecondBlur.add(txtPrimaryS2Y);
		
		JLabel lblNewLabel_6_2_4_1 = new JLabel("Z");
		lblNewLabel_6_2_4_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_4_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_4_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGSecondBlur.add(lblNewLabel_6_2_4_1);
		
		txtPrimaryS2Z = new JTextField();
		txtPrimaryS2Z.setText("1");
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
		
		JComboBox cbPrimaryFilter = new JComboBox();
		//cbPrimaryFilter.setModel(new DefaultComboBoxModel(new String[] {"None", "3D Gaussian Blur", "3D DoG", "3D Median", "3D Mean"}));
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
		txtPriFilterX.setText("1");
		txtPriFilterX.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPriFilterX.setColumns(4);
		pnlPrimaryFirstBlur.add(txtPriFilterX);
		
		JLabel lblNewLabel_6_1 = new JLabel("Y");
		lblNewLabel_6_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_6_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1.setHorizontalAlignment(SwingConstants.LEFT);
		pnlPrimaryFirstBlur.add(lblNewLabel_6_1);
		
		txtPriFilterY = new JTextField();
		txtPriFilterY.setText("1");
		txtPriFilterY.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPriFilterY.setColumns(4);
		pnlPrimaryFirstBlur.add(txtPriFilterY);
		
		JLabel lblNewLabel_6_2 = new JLabel("Z");
		lblNewLabel_6_2.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_6_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2.setHorizontalAlignment(SwingConstants.LEFT);
		pnlPrimaryFirstBlur.add(lblNewLabel_6_2);
		
		txtPriFilterZ = new JTextField();
		txtPriFilterZ.setText("1");
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
		txtPriFilter2X.setText("1");
		txtPriFilter2X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPriFilter2X.setColumns(4);
		pnlPrimarySecondBlur.add(txtPriFilter2X);
		
		JLabel lblNewLabel_6_1_5 = new JLabel("Y");
		lblNewLabel_6_1_5.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_5.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_5.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimarySecondBlur.add(lblNewLabel_6_1_5);
		
		txtPriFilter2Y = new JTextField();
		txtPriFilter2Y.setText("1");
		txtPriFilter2Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPriFilter2Y.setColumns(4);
		pnlPrimarySecondBlur.add(txtPriFilter2Y);
		
		JLabel lblNewLabel_6_2_5 = new JLabel("Z");
		lblNewLabel_6_2_5.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_5.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_5.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimarySecondBlur.add(lblNewLabel_6_2_5);
		
		txtPriFilter2Z = new JTextField();
		txtPriFilter2Z.setText("1");
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
		txtPriSpotX.setText("1");
		txtPriSpotX.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPriSpotX.setColumns(4);
		pnlPrimarySpotSize.add(txtPriSpotX);
		
		JLabel lblNewLabel_6_1_1 = new JLabel("Y");
		lblNewLabel_6_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimarySpotSize.add(lblNewLabel_6_1_1);
		
		txtPriSpotY = new JTextField();
		txtPriSpotY.setText("1");
		txtPriSpotY.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPriSpotY.setColumns(4);
		pnlPrimarySpotSize.add(txtPriSpotY);
		
		JLabel lblNewLabel_6_2_1 = new JLabel("Z");
		lblNewLabel_6_2_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimarySpotSize.add(lblNewLabel_6_2_1);
		
		txtPriSpotZ = new JTextField();
		txtPriSpotZ.setText("1");
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
		
		JComboBox cbPrimaryMethodThreshold = new JComboBox();
		//cbPrimaryMethodThreshold.setModel(new DefaultComboBoxModel(new String[] {"Otsu", "G.C", "Huang", "Yen"}));
		cbPrimaryMethodThreshold.setModel(new DefaultComboBoxModel<>(ThresholdType.values()));
		cbPrimaryMethodThreshold.setSelectedIndex(0);
		cbPrimaryMethodThreshold.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbPrimaryMethodThreshold = new GridBagConstraints();
		gbc_cbPrimaryMethodThreshold.anchor = GridBagConstraints.WEST;
		gbc_cbPrimaryMethodThreshold.insets = new Insets(0, 5, 0, 5);
		gbc_cbPrimaryMethodThreshold.gridx = 0;
		gbc_cbPrimaryMethodThreshold.gridy = 10;
		pnlPrimary.add(cbPrimaryMethodThreshold, gbc_cbPrimaryMethodThreshold);
		
		JPanel pnlPrimaryMethodThreshold = new JPanel();
		GridBagConstraints gbc_pnlPrimaryMethodThreshold = new GridBagConstraints();
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
		txtPrimaryMethodThreshold.setText("1");
		txtPrimaryMethodThreshold.setEnabled(false);
		pnlPrimaryMethodThreshold.add(txtPrimaryMethodThreshold);
		txtPrimaryMethodThreshold.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryMethodThreshold.setColumns(4);
		
		JPanel pnlSecondary = new JPanel();
		pnlMain.add(pnlSecondary);
		GridBagLayout gbl_pnlSecondary = new GridBagLayout();
		gbl_pnlSecondary.columnWidths = new int[]{95, 0};
		gbl_pnlSecondary.rowHeights = new int[] {30, 0, 0, 0, 0, 30, 0, 0, 37, 0, 0, 0};
		gbl_pnlSecondary.columnWeights = new double[]{1.0, 1.0};
		gbl_pnlSecondary.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		cbSecondaryChannel.setModel(new DefaultComboBoxModel(channelNumbers));
		cbSecondaryChannel.setSelectedIndex(1);
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
		//cbSecondaryBackground.setModel(new DefaultComboBoxModel(new String[] {"None", "Default", "3D DoG", "3D Top Hat"}));
		cbSecondaryBackground.setModel(new DefaultComboBoxModel<>(BackgroundType.values()));
		cbSecondaryBackground.setSelectedIndex(0);
		cbSecondaryBackground.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbSecondaryBackground = new GridBagConstraints();
		gbc_cbSecondaryBackground.insets = new Insets(0, 0, 5, 5);
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
		txtSecondaryS1X.setText("1");
		txtSecondaryS1X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryS1X.setColumns(4);
		pnlSecondaryBGFirstBlur.add(txtSecondaryS1X);
		
		JLabel lblNewLabel_6_1_2_1 = new JLabel("Y");
		lblNewLabel_6_1_2_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGFirstBlur.add(lblNewLabel_6_1_2_1);
		
		txtSecondaryS1Y = new JTextField();
		txtSecondaryS1Y.setText("1");
		txtSecondaryS1Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryS1Y.setColumns(4);
		pnlSecondaryBGFirstBlur.add(txtSecondaryS1Y);
		
		JLabel lblNewLabel_6_2_2_1 = new JLabel("Z");
		lblNewLabel_6_2_2_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGFirstBlur.add(lblNewLabel_6_2_2_1);
		
		txtSecondaryS1Z = new JTextField();
		txtSecondaryS1Z.setText("1");
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
		txtSecondaryS2X.setText("1");
		txtSecondaryS2X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryS2X.setColumns(4);
		pnlSecondaryBGSecondBlur.add(txtSecondaryS2X);
		
		JLabel lblNewLabel_6_1_2_2 = new JLabel("Y");
		lblNewLabel_6_1_2_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_2_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_2_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGSecondBlur.add(lblNewLabel_6_1_2_2);
		
		txtSecondaryS2Y = new JTextField();
		txtSecondaryS2Y.setText("1");
		txtSecondaryS2Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryS2Y.setColumns(4);
		pnlSecondaryBGSecondBlur.add(txtSecondaryS2Y);
		
		JLabel lblNewLabel_6_2_2_2 = new JLabel("Z");
		lblNewLabel_6_2_2_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_2_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_2_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGSecondBlur.add(lblNewLabel_6_2_2_2);
		
		txtSecondaryS2Z = new JTextField();
		txtSecondaryS2Z.setText("1");
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
		//cbSecondaryFilter.setModel(new DefaultComboBoxModel(new String[] {"None", "3D Gaussian Blur", "3D DoG", "3D Median", "3D Mean"}));
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
		txtSecFilterX.setText("1");
		txtSecFilterX.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecFilterX.setColumns(4);
		pnlSecondaryFirstBlur.add(txtSecFilterX);
		
		JLabel lblNewLabel_6_1_2 = new JLabel("Y");
		lblNewLabel_6_1_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryFirstBlur.add(lblNewLabel_6_1_2);
		
		txtSecFilterY = new JTextField();
		txtSecFilterY.setText("1");
		txtSecFilterY.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecFilterY.setColumns(4);
		pnlSecondaryFirstBlur.add(txtSecFilterY);
		
		JLabel lblNewLabel_6_2_2 = new JLabel("Z");
		lblNewLabel_6_2_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryFirstBlur.add(lblNewLabel_6_2_2);
		
		txtSecFilterZ = new JTextField();
		txtSecFilterZ.setText("1");
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
		txtSecFilter2X.setText("1");
		txtSecFilter2X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecFilter2X.setColumns(4);
		pnlSecondarySecondBlur.add(txtSecFilter2X);
		
		JLabel lblNewLabel_6_1_2_3 = new JLabel("Y");
		lblNewLabel_6_1_2_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_2_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_2_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondarySecondBlur.add(lblNewLabel_6_1_2_3);
		
		txtSecFilter2Y = new JTextField();
		txtSecFilter2Y.setText("1");
		txtSecFilter2Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecFilter2Y.setColumns(4);
		pnlSecondarySecondBlur.add(txtSecFilter2Y);
		
		JLabel lblNewLabel_6_2_2_3 = new JLabel("Z");
		lblNewLabel_6_2_2_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_2_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_2_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondarySecondBlur.add(lblNewLabel_6_2_2_3);
		
		txtSecFilter2Z = new JTextField();
		txtSecFilter2Z.setText("1");
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

		JPanel pnlSecondarySpotSize = new JPanel();
		GridBagConstraints gbc_pnlSecondarySpotSize = new GridBagConstraints();
		gbc_pnlSecondarySpotSize.insets = new Insets(0, 0, 5, 0);
		gbc_pnlSecondarySpotSize.anchor = GridBagConstraints.EAST;
		gbc_pnlSecondarySpotSize.fill = GridBagConstraints.VERTICAL;
		gbc_pnlSecondarySpotSize.gridwidth = 2;
		gbc_pnlSecondarySpotSize.gridx = 0;
		gbc_pnlSecondarySpotSize.gridy = 9;
		pnlSecondary.add(pnlSecondarySpotSize, gbc_pnlSecondarySpotSize);
		
		
		JComboBox<MethodTypes> cbSecondaryMethod = new JComboBox<>();
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
		txtSecondaryMethodX.setText("1");
		txtSecondaryMethodX.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryMethodX.setColumns(4);
		pnlSecondarySpotSize.add(txtSecondaryMethodX);
		
		JLabel lblNewLabel_6_1_1_1 = new JLabel("Y");
		lblNewLabel_6_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondarySpotSize.add(lblNewLabel_6_1_1_1);
		
		txtSecondaryMethodY = new JTextField();
		txtSecondaryMethodY.setText("1");
		txtSecondaryMethodY.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryMethodY.setColumns(4);
		pnlSecondarySpotSize.add(txtSecondaryMethodY);
		
		JLabel lblNewLabel_6_2_1_1 = new JLabel("Z");
		lblNewLabel_6_2_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondarySpotSize.add(lblNewLabel_6_2_1_1);
		
		txtSecondaryMethodZ = new JTextField();
		txtSecondaryMethodZ.setText("1");
		txtSecondaryMethodZ.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryMethodZ.setColumns(4);
		pnlSecondarySpotSize.add(txtSecondaryMethodZ);
		
		JComboBox cbSecondaryMethodThreshold = new JComboBox();
		//cbSecondaryMethodThreshold.setModel(new DefaultComboBoxModel(new String[] {"Otsu", "G.C", "Huang", "Yen"}));
		cbSecondaryMethodThreshold.setModel(new DefaultComboBoxModel<>(ThresholdType.values()));
		cbSecondaryMethodThreshold.setSelectedIndex(0);
		cbSecondaryMethodThreshold.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbSecondaryMethodThreshold = new GridBagConstraints();
		gbc_cbSecondaryMethodThreshold.anchor = GridBagConstraints.WEST;
		gbc_cbSecondaryMethodThreshold.insets = new Insets(0, 5, 0, 0);
		gbc_cbSecondaryMethodThreshold.gridx = 0;
		gbc_cbSecondaryMethodThreshold.gridy = 10;
		pnlSecondary.add(cbSecondaryMethodThreshold, gbc_cbSecondaryMethodThreshold);
		
		JPanel pnlSecondaryThreshold = new JPanel();
		GridBagConstraints gbc_pnlSecondaryTreshold = new GridBagConstraints();
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
		txtSecondaryMethodThreshold.setText("1");
		txtSecondaryMethodThreshold.setEnabled(false);
		txtSecondaryMethodThreshold.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryMethodThreshold.setColumns(4);
		pnlSecondaryThreshold.add(txtSecondaryMethodThreshold);
		
		JPanel pnlTertiary = new JPanel();
		pnlTertiary.setBorder(new MatteBorder(0, 1, 0, 0, (Color) new Color(169, 169, 169)));
		pnlMain.add(pnlTertiary);
		GridBagLayout gbl_pnlTertiary = new GridBagLayout();
		gbl_pnlTertiary.columnWidths = new int[] {95, 0};
		gbl_pnlTertiary.rowHeights = new int[]{30, 0, 0, 0, 0, 30, 0, 0, 35, 0, 30, 0};
		gbl_pnlTertiary.columnWeights = new double[]{1.0, 1.0};
		gbl_pnlTertiary.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		cbTertiaryChannel.setModel(new DefaultComboBoxModel(channelNumbers));
		cbTertiaryChannel.setSelectedIndex(2);
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
		txtTertiaryS1X.setEnabled(false);
		txtTertiaryS1X.setText("1");
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
		txtTertiaryS1Y.setEnabled(false);
		txtTertiaryS1Y.setText("1");
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
		txtTertiaryS1Z.setEnabled(false);
		txtTertiaryS1Z.setText("1");
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
		txtTertiaryS2X.setEnabled(false);
		txtTertiaryS2X.setText("1");
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
		txtTertiaryS2Y.setEnabled(false);
		txtTertiaryS2Y.setText("1");
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
		txtTertiaryS2Z.setEnabled(false);
		txtTertiaryS2Z.setText("1");
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
		gbc_cbTertiaryBackground.insets = new Insets(0, 0, 5, 5);
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
		gbc_cbTertiaryFilter.insets = new Insets(0, 0, 5, 5);
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
		txtTertFilterX.setEnabled(false);
		txtTertFilterX.setText("1");
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
		txtTertFilterY.setEnabled(false);
		txtTertFilterY.setText("1");
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
		txtTertFilterZ.setEnabled(false);
		txtTertFilterZ.setText("1");
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
		txtTertFilter2X.setText("1");
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
		txtTertFilter2Y.setText("1");
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
		txtTertFilter2Z.setText("1");
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
			
		JComboBox<MethodTypes> cbTertiaryMethod = new JComboBox<>();
		cbTertiaryMethod.setEnabled(false);
		cbTertiaryMethod.setModel(new DefaultComboBoxModel<>(MethodTypes.values()));
		cbTertiaryMethod.setSelectedIndex(0);
		
		cbTertiaryMethod.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbTertiaryMethod = new GridBagConstraints();
		gbc_cbTertiaryMethod.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbTertiaryMethod.insets = new Insets(0, 0, 5, 5);
		gbc_cbTertiaryMethod.gridx = 1;
		gbc_cbTertiaryMethod.gridy = 8;
		pnlTertiary.add(cbTertiaryMethod, gbc_cbTertiaryMethod);
		
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
		txtTertiaryMethodX.setText("1");
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
		txtTertiaryMethodY.setText("1");
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
		txtTertiaryMethodZ.setText("1");
		txtTertiaryMethodZ.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertiaryMethodZ.setColumns(4);
		pnlTertiarySpotSize.add(txtTertiaryMethodZ);
		
		JPanel pnlTertiaryThreshold = new JPanel();
		GridBagConstraints gbc_pnlTertiaryThreshold = new GridBagConstraints();
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
		txtTertiaryMethodThreshold.setText("1");
		txtTertiaryMethodThreshold.setEnabled(false);
		txtTertiaryMethodThreshold.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertiaryMethodThreshold.setColumns(4);
		pnlTertiaryThreshold.add(txtTertiaryMethodThreshold);
		
		JComboBox cbTertiaryMethodThreshold = new JComboBox();
		cbTertiaryMethodThreshold.setModel(new DefaultComboBoxModel<>(ThresholdType.values()));
		cbTertiaryMethodThreshold.setSelectedIndex(0);
		cbTertiaryMethodThreshold.setFont(new Font("Arial", Font.PLAIN, 14));
		cbTertiaryMethodThreshold.setEnabled(false);
		GridBagConstraints gbc_cbTertiaryMethodThreshold = new GridBagConstraints();
		gbc_cbTertiaryMethodThreshold.anchor = GridBagConstraints.WEST;
		gbc_cbTertiaryMethodThreshold.insets = new Insets(0, 5, 0, 0);
		gbc_cbTertiaryMethodThreshold.gridx = 0;
		gbc_cbTertiaryMethodThreshold.gridy = 10;
		pnlTertiary.add(cbTertiaryMethodThreshold, gbc_cbTertiaryMethodThreshold);
		
		
		JPanel pnlFooter = new JPanel();
		GridBagConstraints gbc_pnlFooter = new GridBagConstraints();
		gbc_pnlFooter.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlFooter.anchor = GridBagConstraints.SOUTH;
		gbc_pnlFooter.insets = new Insets(0, 0, 5, 0);
		gbc_pnlFooter.gridx = 0;
		gbc_pnlFooter.gridy = 2;
		contentPane.add(pnlFooter, gbc_pnlFooter);
		GridBagLayout gbl_pnlFooter = new GridBagLayout();
		gbl_pnlFooter.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_pnlFooter.rowHeights = new int[]{0, 0, 0, 0};
		gbl_pnlFooter.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, 1.0};
		gbl_pnlFooter.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlFooter.setLayout(gbl_pnlFooter);
		
		JButton btnBackToMenu = new JButton("Back to Menu");
		btnBackToMenu.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnBackToMenu = new GridBagConstraints();
		gbc_btnBackToMenu.insets = new Insets(0, 0, 5, 5);
		gbc_btnBackToMenu.gridx = 0;
		gbc_btnBackToMenu.gridy = 1;
		pnlFooter.add(btnBackToMenu, gbc_btnBackToMenu);
		
		JButton btnLoadParameters = new JButton("Load Configuration");
		btnLoadParameters.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnLoadParameters = new GridBagConstraints();
		gbc_btnLoadParameters.insets = new Insets(0, 0, 5, 5);
		gbc_btnLoadParameters.gridx = 1;
		gbc_btnLoadParameters.gridy = 1;
		pnlFooter.add(btnLoadParameters, gbc_btnLoadParameters);
		
		JSeparator separator_1_1_1 = new JSeparator();
		separator_1_1_1.setForeground(new Color(169, 169, 169));
		separator_1_1_1.setBackground(Color.WHITE);
		GridBagConstraints gbc_separator_1_1_1 = new GridBagConstraints();
		gbc_separator_1_1_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_separator_1_1_1.gridwidth = 10;
		gbc_separator_1_1_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1_1_1.gridx = 0;
		gbc_separator_1_1_1.gridy = 0;
		pnlFooter.add(separator_1_1_1, gbc_separator_1_1_1);
		
		JButton btnRunAnalysis = new JButton("Run Analysis");
		btnRunAnalysis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Collect parameters from GUI
				String inputDir = txtInputDir.getText();
				String outputDir = txtOutputDir.getText();
				
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
										build()
						).
						build();
				
				// Stratify label parameters
				StratifyParameters stratifyParameters = StratifyParameters.builder().
						primary25(ckbPri25Bands.isSelected()).
						primary50(ckbPri50Bands.isSelected()).
						secondary25(ckbSec25Bands.isSelected()).
						secondary50(ckbSec50Bands.isSelected()).
						tertiary25(ckbTer25Bands.isSelected()).
						tertiary50(ckbTer50Bands.isSelected()).
						build();
				
				// Skeletonize label parameters
				SkeletonParameters skeletonParameters = SkeletonParameters.builder().
						primary(ckbPriSkeletonize.isSelected()).
						secondary(ckbSecSkeletonize.isSelected()).
						tertairy(ckbTerSkeletonize.isSelected()).
						build();
				
				// build the final data object
				ParameterCollection parameterCollection = ParameterCollection.builder().
						inputDir(inputDir).
						outputDir(outputDir).
						analysisOnly(ckbAnalysisOnly.isSelected()).
						mode((ModeType) cbAnalysisMode.getSelectedItem()).
						primaryObject(primaryObject).
						secondaryObject(secondaryObject).
						tertiaryObject(tertiaryObject).
						killBorderType(selectedKillBorderOption).
						groupingInfo(txtGroupingInfo.getText()).
						nameChannel1(txtC1.getText()).
						nameChannel2(txtC2.getText()).
						nameChannel3(txtC3.getText()).
						nameChannel4(txtC4.getText()).
						processTertiary(ckbTertiary.isSelected()).
						tertiaryIsDifference(ckbTertiaryObjectOption.isSelected()).
						stratifyParameters(stratifyParameters).
						skeletonParameters(skeletonParameters).
						build();

				// Hand off to DatumUpdateService
				paramManager.notifyUpdated(parameterCollection);

			}
		});
		btnRunAnalysis.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_btnRunAnalysis = new GridBagConstraints();
		gbc_btnRunAnalysis.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRunAnalysis.gridwidth = 2;
		gbc_btnRunAnalysis.insets = new Insets(0, 0, 0, 5);
		gbc_btnRunAnalysis.gridx = 0;
		gbc_btnRunAnalysis.gridy = 2;
		pnlFooter.add(btnRunAnalysis, gbc_btnRunAnalysis);
		
		

		
		/*
		 * Action listeners for panel dynamics here to avoid variable scope issues.
		 * 
		 */
		
		
		/*
		 * return to main window. 
		 */
		
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainScreen2 MainGui = new MainScreen2(paramManager);
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
				} else if(ckbTertiary.isSelected()) {
					for(Component comp:guiHelper.getComponents(pnlTertiary)) {
						comp.setEnabled(true);
						txtTertiaryMethodThreshold.setEnabled(true);
					}
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
				
				if(primaryMethod.equals("Trained Classifier")) {
					pnlPrimarySpotSize.setVisible(false);
					pnlPrimaryMethodThreshold.setVisible(false);
					cbPrimaryMethodThreshold.setVisible(false);
				} else {
					pnlPrimarySpotSize.setVisible(true);
					cbPrimaryMethodThreshold.setVisible(true);
					
				}
			}
		});
		
		cbSecondaryMethod.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				String secondaryMethod = cbSecondaryMethod.getSelectedItem().toString();
				
				if(secondaryMethod.equals("Trained Classifier")) {
					pnlSecondarySpotSize.setVisible(false);
					pnlSecondaryThreshold.setVisible(false);
					cbSecondaryMethodThreshold.setVisible(false);
				} else {
					pnlSecondarySpotSize.setVisible(true);
					pnlSecondaryThreshold.setVisible(true);
					cbSecondaryMethodThreshold.setVisible(true);
					
				}
			}
		});
		
		cbTertiaryMethod.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				String tertiaryMethod = cbTertiaryMethod.getSelectedItem().toString();
				
				if(tertiaryMethod.equals("Trained Classifier")) {
					pnlTertiarySpotSize.setVisible(false);
					pnlTertiaryThreshold.setVisible(false);
					cbTertiaryMethodThreshold.setVisible(false);
				} else {
					pnlTertiarySpotSize.setVisible(true);
					pnlTertiaryThreshold.setVisible(true);
					cbTertiaryMethodThreshold.setVisible(true);
					
				}	
			}
		});
		
		
		/* 
		 * Toggle analysis mode panels
		 */
		
		cbAnalysisMode.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				ModeType mode = (ModeType) cbAnalysisMode.getSelectedItem();
				
				switch (mode) {
				case NONE:
					pnlAdditionalProcessing.setVisible(false);
					ckbAdditionalProcessing.setVisible(false);
					ckbTertiaryObjectOption.setVisible(true);
					break;
				case BASIC:
					ckbAdditionalProcessing.setVisible(true);
					ckbAdditionalProcessing.setSelected(false);
					ckbProcessAllObjects.setVisible(false);
					ckbTertiaryObjectOption.setVisible(true);
					break;
				case SPHEROID:
					ckbAdditionalProcessing.setVisible(true);
					ckbAdditionalProcessing.setSelected(false);
					ckbProcessAllObjects.setVisible(false);
					ckbTertiaryObjectOption.setVisible(true);
					break;
				case SINGLECELL:
					ckbTertiaryObjectOption.setVisible(true);
					ckbAdditionalProcessing.setSelected(false);
					ckbProcessAllObjects.setVisible(false);
					ckbAdditionalProcessing.setVisible(true);
					break;
				case SPECKLE:
					ckbAdditionalProcessing.setVisible(true);
					ckbAdditionalProcessing.setSelected(false);
					ckbProcessAllObjects.setVisible(false);
					ckbTertiaryObjectOption.setVisible(false);
					break;
				default:
					pnlAdditionalProcessing.setVisible(false);
					ckbAdditionalProcessing.setSelected(false);
					ckbAdditionalProcessing.setVisible(false);
					ckbTertiaryObjectOption.setVisible(true);
					ckbProcessAllObjects.setVisible(false);
					break;
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
		 * Options for stratification.
		 */
		// when process all is ticked, activate all stratification options
		ckbProcessAllObjects.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (ckbProcessAllObjects.isSelected()) {
					ckbPri25Bands.setSelected(true);
					ckbSec25Bands.setSelected(true);
					ckbTer25Bands.setSelected(true);
					ckbPri50Bands.setSelected(true);
					ckbSec50Bands.setSelected(true);
					ckbTer50Bands.setSelected(true);
					ckbPriSkeletonize.setSelected(true);
					ckbSecSkeletonize.setSelected(true);
					ckbTerSkeletonize.setSelected(true);
					
				} else {
					ckbPri25Bands.setSelected(false);
					ckbSec25Bands.setSelected(false);
					ckbTer25Bands.setSelected(false);
					ckbPri50Bands.setSelected(false);
					ckbSec50Bands.setSelected(false);
					ckbTer50Bands.setSelected(false);
					ckbPriSkeletonize.setSelected(false);
					ckbSecSkeletonize.setSelected(false);
					ckbTerSkeletonize.setSelected(false);
				}
				
			}
		});
		
		ckbAdditionalProcessing.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (ckbAdditionalProcessing.isSelected()) {
					pnlAdditionalProcessing.setVisible(true);
					ckbProcessAllObjects.setVisible(true);
				} else {
					pnlAdditionalProcessing.setVisible(false);
					ckbProcessAllObjects.setVisible(false);
				}
			}
		});
		
		
		btnLoadParameters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String paramDir = IJ.getFilePath("Select the parameter or optimisation file");
				System.out.println("Getting file from: " + paramDir);
				
				
				try {
					ParameterCollection param = ParameterCollection.loadParameterCollection(paramDir);
					
					// extract parameter components
					ObjectParameters primaryObject = param.getPrimaryObject();
					ObjectParameters secondaryObject = param.getSecondaryObject();
					ObjectParameters tertiaryObject = param.getTertiaryObject();
					SkeletonParameters skeletonParams = param.getSkeletonParameters();
					StratifyParameters stratifyParams = param.getStratifyParameters();
					
					
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
					
					// set variable inputs
					txtGroupingInfo.setText(param.getGroupingInfo());
					txtC1.setText(param.getNameChannel1());
					txtC2.setText(param.getNameChannel2());
					txtC3.setText(param.getNameChannel3());
					txtC4.setText(param.getNameChannel4());
					selectedKillBorderOption = param.getKillBorderType();
					
					// default mode to NONE
					try {
						cbAnalysisMode.setSelectedItem(param.getMode());
					} catch (Exception e1) {
						cbAnalysisMode.setSelectedItem(ModeType.NONE);
					}
				
					ckbAnalysisOnly.setSelected(param.getAnalysisOnly());
					ckbTertiary.setSelected(param.getProcessTertiary());
					ckbTertiaryObjectOption.setSelected(param.getTertiaryIsDifference());

					// set skeleton inputs 
					ckbPriSkeletonize.setSelected(skeletonParams.getPrimary());
					ckbSecSkeletonize.setSelected(skeletonParams.getSecondary());
					ckbTerSkeletonize.setSelected(skeletonParams.getTertairy());
					
					// set stratify inputs
					ckbPri25Bands.setSelected(stratifyParams.getPrimary25());
					ckbPri50Bands.setSelected(stratifyParams.getPrimary50());
					ckbSec25Bands.setSelected(stratifyParams.getSecondary25());
					ckbSec50Bands.setSelected(stratifyParams.getSecondary50());
					ckbTer25Bands.setSelected(stratifyParams.getTertiary25());
					ckbTer50Bands.setSelected(stratifyParams.getTertiary50());
					
				
					System.out.println("FOCUST paramter file found and loaded.");
				} catch (IOException e1) {
					System.out.println("Could not locate or load FOCUST parameter file.");
					e1.printStackTrace();
				}

			}
		});
		

	}
	
	/**
	 * Set the mode of the analysis.
	 * @param index
	 */
	public void setMode(int index) {
		cbAnalysisMode.setSelectedIndex(index);
	}


	private static class __Tmp {
		private static void __tmp() {
			  javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
		}
	}

}
