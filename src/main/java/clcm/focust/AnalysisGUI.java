package clcm.focust;

import java.awt.EventQueue;
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
import java.awt.event.ItemEvent;
import javax.swing.border.MatteBorder;
import clcm.focust.filter.BackgroundType;
import clcm.focust.filter.FilterType;
import clcm.focust.threshold.ThresholdType;
import ij.IJ;
import ij.ImagePlus;

import java.awt.Toolkit;

public class AnalysisGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtInputDir;
	private String inputDir;
	private final ButtonGroup rbtnOutputDir = new ButtonGroup();
	private JTextField txtOutputDir;
	private JTextField txtC1;
	private JTextField txtC2;
	private JTextField txtC3;
	private JTextField txtC4;
	private JTextField textField;
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
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
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
	private JTextField txtCoreVolValue;
	private JTextField txtPrimaryClassiferDirectory;
	private JTextField txtPrimaryMethodThreshold;
	private JComboBox<String> cbAnalysisMode;
	private JTextField textField_1;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField txtSecondaryClassiferDirectory;
	private JTextField txtTertiaryClassiferDirectory;
	private JButton btnBrowseTertiaryClassifer;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnalysisGUI frame = new AnalysisGUI();
					frame.setLocationRelativeTo(null);
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
	public AnalysisGUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AnalysisGUI.class.getResource("/clcm/focust/resources/icon2.png")));
		setTitle("FOCUST: Run Analysis");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1102, 638);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		GuiHelper guiHelper = new GuiHelper();
		
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{801, 0};
		gbl_contentPane.rowHeights = new int[]{149, 218, 76, 0};
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
		
		String[] analysisOptions = {"None", "Spheroid", "Single Cells", "Speckles"};
		DefaultComboBoxModel<String> analysisModel = new DefaultComboBoxModel<String>(analysisOptions);
		//cbAnalysisMode = new JComboBox<>(new String[] {"None", "Spheroid", "Single Cells", "Speckles"});
		//cbAnalysisMode.setModel(new DefaultComboBoxModel(new String[] {"None", "Spheroid", "Single Cells", "Speckles"}));
		cbAnalysisMode = new JComboBox<String>(analysisModel);
		cbAnalysisMode.setSelectedIndex(0);
		cbAnalysisMode.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbAnalysisMode = new GridBagConstraints();
		gbc_cbAnalysisMode.anchor = GridBagConstraints.WEST;
		gbc_cbAnalysisMode.insets = new Insets(0, 0, 5, 5);
		gbc_cbAnalysisMode.gridx = 1;
		gbc_cbAnalysisMode.gridy = 3;
		pnlHeader.add(cbAnalysisMode, gbc_cbAnalysisMode);
		
		JCheckBox ckbAnalysisOnly = new JCheckBox("Analysis only mode?");
		ckbAnalysisOnly.setSelected(true);
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
		gbl_pnlVariable.columnWidths = new int[]{0, 0, 0};
		gbl_pnlVariable.rowHeights = new int[] {35, 35, 35, 35, 35, 35, 0, 0, 0, 35, 0};
		gbl_pnlVariable.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_pnlVariable.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		
		/*
		 * JRadioButton rdbtnNewRadioButton = new
		 * JRadioButton(KillBorderTypes.NO.toString());
		 * killBordersChoice.add(rdbtnNewRadioButton);
		 * rdbtnNewRadioButton.setSelected(true); rdbtnNewRadioButton.setFont(new
		 * Font("Arial", Font.PLAIN, 13)); //pnlKillBorders.add(rdbtnNewRadioButton);
		 * 
		 * JRadioButton rdbtnXY = new JRadioButton(KillBorderTypes.XY.toString());
		 * killBordersChoice.add(rdbtnXY); rdbtnXY.setFont(new Font("Arial", Font.PLAIN,
		 * 13)); //pnlKillBorders.add(rdbtnXY);
		 * 
		 * JRadioButton rdbtnXyz = new JRadioButton(KillBorderTypes.XYZ.toString());
		 * killBordersChoice.add(rdbtnXyz); rdbtnXyz.setFont(new Font("Arial",
		 * Font.PLAIN, 13)); //pnlKillBorders.add(rdbtnXyz);
		 */		
		
		
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
			if(type == KillBorderTypes.NO) {
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
		
		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 14));
		textField.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		pnlVariable.add(textField, gbc_textField);
		
		JCheckBox ckbSpeckleSkeletons = new JCheckBox("Skeleton-based elongation?");
		ckbSpeckleSkeletons.setSelected(true);
		ckbSpeckleSkeletons.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ckbSpeckleSkeletons = new GridBagConstraints();
		gbc_ckbSpeckleSkeletons.anchor = GridBagConstraints.WEST;
		gbc_ckbSpeckleSkeletons.gridwidth = 2;
		gbc_ckbSpeckleSkeletons.insets = new Insets(0, 5, 5, 0);
		gbc_ckbSpeckleSkeletons.gridx = 0;
		gbc_ckbSpeckleSkeletons.gridy = 6;
		pnlVariable.add(ckbSpeckleSkeletons, gbc_ckbSpeckleSkeletons);
		ckbSpeckleSkeletons.setVisible(false);
		
		JCheckBox ckbTertiaryObjectOption = new JCheckBox("Tertiary = Secondary - Primary?");
		ckbTertiaryObjectOption.setSelected(true);
		ckbTertiaryObjectOption.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ckbCellsTertiaryOption = new GridBagConstraints();
		gbc_ckbCellsTertiaryOption.anchor = GridBagConstraints.WEST;
		gbc_ckbCellsTertiaryOption.gridwidth = 2;
		gbc_ckbCellsTertiaryOption.insets = new Insets(0, 5, 5, 0);
		gbc_ckbCellsTertiaryOption.gridx = 0;
		gbc_ckbCellsTertiaryOption.gridy = 6;
		pnlVariable.add(ckbTertiaryObjectOption, gbc_ckbCellsTertiaryOption);
		ckbTertiaryObjectOption.setVisible(false);
		
		JCheckBox ckbSpheroidCoreVsPeriphery = new JCheckBox("Core vs Periphery?");
		
		ckbSpheroidCoreVsPeriphery.setToolTipText("Tick the box to generate a 3D core and periphery (based on the target volume % for the core), calculate the intensity of all channels in the core and periphery regions. Ratios comparing channel intensity (core vs periphery) will also be calculated.");
		ckbSpheroidCoreVsPeriphery.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_ckbSpheroidCoreVsPeriphery = new GridBagConstraints();
		gbc_ckbSpheroidCoreVsPeriphery.anchor = GridBagConstraints.WEST;
		gbc_ckbSpheroidCoreVsPeriphery.gridwidth = 2;
		gbc_ckbSpheroidCoreVsPeriphery.insets = new Insets(0, 5, 5, 0);
		gbc_ckbSpheroidCoreVsPeriphery.gridx = 0;
		gbc_ckbSpheroidCoreVsPeriphery.gridy = 7;
		pnlVariable.add(ckbSpheroidCoreVsPeriphery, gbc_ckbSpheroidCoreVsPeriphery);
		ckbSpheroidCoreVsPeriphery.setVisible(false);
		
		JPanel pnlCoreVolValue = new JPanel();
		GridBagConstraints gbc_pnlCoreVolValue = new GridBagConstraints();
		gbc_pnlCoreVolValue.anchor = GridBagConstraints.WEST;
		gbc_pnlCoreVolValue.fill = GridBagConstraints.VERTICAL;
		gbc_pnlCoreVolValue.gridwidth = 2;
		gbc_pnlCoreVolValue.insets = new Insets(0, 0, 0, 5);
		gbc_pnlCoreVolValue.gridx = 0;
		gbc_pnlCoreVolValue.gridy = 8;
		pnlVariable.add(pnlCoreVolValue, gbc_pnlCoreVolValue);
		pnlCoreVolValue.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pnlCoreVolValue.setVisible(false);
		
		JLabel lblCoreVolValue = new JLabel("Core Volume % (0:1)");
		pnlCoreVolValue.add(lblCoreVolValue);
		lblCoreVolValue.setToolTipText("Provide the volume % to reduce the \"core\" of the spheroid to. i.e 0.5 = 50 %.");
		lblCoreVolValue.setFont(new Font("Arial", Font.PLAIN, 14));
		
		txtCoreVolValue = new JTextField();
		pnlCoreVolValue.add(txtCoreVolValue);
		txtCoreVolValue.setText("0.5");
		txtCoreVolValue.setFont(new Font("Arial", Font.PLAIN, 14));
		txtCoreVolValue.setColumns(5);
		
		JPanel pnlPrimary = new JPanel();
		pnlPrimary.setBorder(new MatteBorder(0, 1, 0, 1, (Color) new Color(169, 169, 169)));
		pnlMain.add(pnlPrimary);
		GridBagLayout gbl_pnlPrimary = new GridBagLayout();
		gbl_pnlPrimary.columnWidths = new int[]{0, 0, 0};
		gbl_pnlPrimary.rowHeights = new int[] {30, 0, 0, 0, 0, 30, 0, 0, 35, 0, 30, 0};
		gbl_pnlPrimary.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
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
		cbPrimaryChannel.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "-"}));
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
		gbc_pnlPrimaryFirstBlur.insets = new Insets(0, 0, 5, 5);
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
		gbc_pnlPrimarySecondBlur.insets = new Insets(0, 0, 5, 5);
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
		
		textField_1 = new JTextField();
		textField_1.setText("1");
		textField_1.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_1.setColumns(4);
		pnlPrimarySecondBlur.add(textField_1);
		
		JLabel lblNewLabel_6_1_5 = new JLabel("Y");
		lblNewLabel_6_1_5.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_5.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_5.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimarySecondBlur.add(lblNewLabel_6_1_5);
		
		textField_8 = new JTextField();
		textField_8.setText("1");
		textField_8.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_8.setColumns(4);
		pnlPrimarySecondBlur.add(textField_8);
		
		JLabel lblNewLabel_6_2_5 = new JLabel("Z");
		lblNewLabel_6_2_5.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_5.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_5.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimarySecondBlur.add(lblNewLabel_6_2_5);
		
		textField_9 = new JTextField();
		textField_9.setText("1");
		textField_9.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_9.setColumns(4);
		pnlPrimarySecondBlur.add(textField_9);
		
		JLabel lblNewLabel_5_1 = new JLabel("Method:");
		lblNewLabel_5_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_1 = new GridBagConstraints();
		gbc_lblNewLabel_5_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_1.gridx = 0;
		gbc_lblNewLabel_5_1.gridy = 8;
		pnlPrimary.add(lblNewLabel_5_1, gbc_lblNewLabel_5_1);
		
		JComboBox cbPrimaryMethod = new JComboBox();
		//cbPrimaryMethod.setModel(new DefaultComboBoxModel(new String[] {"M.C.W Maxima", "M.C.W Minima", "Trained Classifer"}));
		cbPrimaryMethod.setModel(new DefaultComboBoxModel(new String[] {"Maxima", "Minima", "Trained Classifier"}));
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
		txtPrimaryMethodThreshold.setEnabled(false);
		pnlPrimaryMethodThreshold.add(txtPrimaryMethodThreshold);
		txtPrimaryMethodThreshold.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryMethodThreshold.setColumns(6);
		pnlPrimaryMethodThreshold.setVisible(true);
		txtPrimaryClassiferDirectory.setVisible(false);
		
		JPanel pnlSecondary = new JPanel();
		pnlMain.add(pnlSecondary);
		GridBagLayout gbl_pnlSecondary = new GridBagLayout();
		gbl_pnlSecondary.columnWidths = new int[]{0, 0};
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
		cbSecondaryChannel.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "-"}));
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
		
		textField_10 = new JTextField();
		textField_10.setText("1");
		textField_10.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_10.setColumns(4);
		pnlSecondarySecondBlur.add(textField_10);
		
		JLabel lblNewLabel_6_1_2_3 = new JLabel("Y");
		lblNewLabel_6_1_2_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_2_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_2_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondarySecondBlur.add(lblNewLabel_6_1_2_3);
		
		textField_11 = new JTextField();
		textField_11.setText("1");
		textField_11.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_11.setColumns(4);
		pnlSecondarySecondBlur.add(textField_11);
		
		JLabel lblNewLabel_6_2_2_3 = new JLabel("Z");
		lblNewLabel_6_2_2_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_2_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_2_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondarySecondBlur.add(lblNewLabel_6_2_2_3);
		
		textField_12 = new JTextField();
		textField_12.setText("1");
		textField_12.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_12.setColumns(4);
		pnlSecondarySecondBlur.add(textField_12);
		
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
		
		
		//cbSecondaryMethod.setModel(new DefaultComboBoxModel(new String[] {"M.C.W Maxima", "M.C.W Minima", "Trained Classifer"}));
		cbSecondaryMethod.setModel(new DefaultComboBoxModel(new String[] {"Maxima", "Minima", "Trained Classifier"}));
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
		txtSecondaryMethodThreshold.setEnabled(false);
		txtSecondaryMethodThreshold.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryMethodThreshold.setColumns(6);
		pnlSecondaryThreshold.add(txtSecondaryMethodThreshold);
		
		JPanel pnlTertiary = new JPanel();
		pnlTertiary.setBorder(new MatteBorder(0, 1, 0, 0, (Color) new Color(169, 169, 169)));
		pnlMain.add(pnlTertiary);
		GridBagLayout gbl_pnlTertiary = new GridBagLayout();
		gbl_pnlTertiary.columnWidths = new int[] {0, 0};
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
		cbTertiaryChannel.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "-"}));
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
		//cbTertiaryBackground.setModel(new DefaultComboBoxModel(new String[] {"None", "Default", "3D DoG", "3D Top Hat"}));
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
		//cbTertiaryFilter.setModel(new DefaultComboBoxModel(new String[] {"None", "3D Gaussian Blur", "3D DoG", "3D Median", "3D Mean"}));
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
		
		textField_2 = new JTextField();
		textField_2.setEnabled(false);
		textField_2.setText("1");
		textField_2.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_2.setColumns(4);
		pnlTertiaryFirstBlur.add(textField_2);
		
		JLabel lblNewLabel_6_1_3 = new JLabel("Y");
		lblNewLabel_6_1_3.setEnabled(false);
		lblNewLabel_6_1_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryFirstBlur.add(lblNewLabel_6_1_3);
		
		textField_3 = new JTextField();
		textField_3.setEnabled(false);
		textField_3.setText("1");
		textField_3.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_3.setColumns(4);
		pnlTertiaryFirstBlur.add(textField_3);
		
		JLabel lblNewLabel_6_2_3 = new JLabel("Z");
		lblNewLabel_6_2_3.setEnabled(false);
		lblNewLabel_6_2_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryFirstBlur.add(lblNewLabel_6_2_3);
		
		textField_4 = new JTextField();
		textField_4.setEnabled(false);
		textField_4.setText("1");
		textField_4.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_4.setColumns(4);
		pnlTertiaryFirstBlur.add(textField_4);
		
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
		
		textField_13 = new JTextField();
		textField_13.setText("1");
		textField_13.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_13.setEnabled(false);
		textField_13.setColumns(4);
		pnlTertiarySecondBlur.add(textField_13);
		
		JLabel lblNewLabel_6_1_3_1 = new JLabel("Y");
		lblNewLabel_6_1_3_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_3_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_6_1_3_1.setEnabled(false);
		pnlTertiarySecondBlur.add(lblNewLabel_6_1_3_1);
		
		textField_14 = new JTextField();
		textField_14.setText("1");
		textField_14.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_14.setEnabled(false);
		textField_14.setColumns(4);
		pnlTertiarySecondBlur.add(textField_14);
		
		JLabel lblNewLabel_6_2_3_1 = new JLabel("Z");
		lblNewLabel_6_2_3_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_3_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_6_2_3_1.setEnabled(false);
		pnlTertiarySecondBlur.add(lblNewLabel_6_2_3_1);
		
		textField_15 = new JTextField();
		textField_15.setText("1");
		textField_15.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_15.setEnabled(false);
		textField_15.setColumns(4);
		pnlTertiarySecondBlur.add(textField_15);
		
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
		
		//cbTertiaryMethod.setModel(new DefaultComboBoxModel(new String[] {"M.C.W Maxima", "M.C.W Minima", "Trained Classifer"}));
		cbTertiaryMethod.setModel(new DefaultComboBoxModel(new String[] {"Maxima", "Minima", "Trained Classifier"}));
		cbTertiaryMethod.setSelectedIndex(0);
		cbTertiaryMethod.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbTertiaryMethod = new GridBagConstraints();
		gbc_cbTertiaryMethod.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbTertiaryMethod.insets = new Insets(0, 0, 5, 5);
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
		
		textField_5 = new JTextField();
		textField_5.setEnabled(false);
		textField_5.setText("1");
		textField_5.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_5.setColumns(4);
		pnlTertiarySpotSize.add(textField_5);
		
		JLabel lblNewLabel_6_1_1_2 = new JLabel("Y");
		lblNewLabel_6_1_1_2.setEnabled(false);
		lblNewLabel_6_1_1_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiarySpotSize.add(lblNewLabel_6_1_1_2);
		
		textField_6 = new JTextField();
		textField_6.setEnabled(false);
		textField_6.setText("1");
		textField_6.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_6.setColumns(4);
		pnlTertiarySpotSize.add(textField_6);
		
		JLabel lblNewLabel_6_2_1_2 = new JLabel("Z");
		lblNewLabel_6_2_1_2.setEnabled(false);
		lblNewLabel_6_2_1_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiarySpotSize.add(lblNewLabel_6_2_1_2);
		
		textField_7 = new JTextField();
		textField_7.setEnabled(false);
		textField_7.setText("1");
		textField_7.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_7.setColumns(4);
		pnlTertiarySpotSize.add(textField_7);
		
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
		txtTertiaryMethodThreshold.setEnabled(false);
		txtTertiaryMethodThreshold.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertiaryMethodThreshold.setColumns(6);
		pnlTertiaryThreshold.add(txtTertiaryMethodThreshold);
		
		JComboBox cbTertiaryMethodThreshold = new JComboBox();
		//cbTertiaryMethodThreshold.setModel(new DefaultComboBoxModel(new String[] {"Otsu", "G.C", "Huang", "Yen"}));
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
				
				// testing kill borders methods
				
				System.out.println(selectedKillBorderOption);
				
				File f = new File(inputDir);
				String[] list = f.list();
				
				for (int i = 0; i < list.length; i++) {
					String path = inputDir + list[i];
					ImagePlus imp = IJ.openImage(path);
					
					// Testing functionality below
					
				}
				
				
				
			}
		});
		btnRunAnalysis.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_btnRunAnalysis = new GridBagConstraints();
		gbc_btnRunAnalysis.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRunAnalysis.gridwidth = 2;
		gbc_btnRunAnalysis.insets = new Insets(0, 0, 5, 5);
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
				MainScreen MainGui = new MainScreen();
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
						txtTertiaryMethodThreshold.setEnabled(false);
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
					btnBrowsePrimaryClassifer.setVisible(true);
					txtPrimaryClassiferDirectory.setVisible(true);
					pnlPrimarySpotSize.setVisible(false);
					pnlPrimaryMethodThreshold.setVisible(false);
					cbPrimaryMethodThreshold.setVisible(false);
				} else {
					btnBrowsePrimaryClassifer.setVisible(false);
					txtPrimaryClassiferDirectory.setVisible(false);
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
				
				if(tertiaryMethod.equals("Trained Classifier")) {
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
		 * Toggle analysis mode panels
		 */
		
		cbAnalysisMode.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				String analysisMode = cbAnalysisMode.getSelectedItem().toString();
				
				if(analysisMode.equals("None")) {
					ckbSpeckleSkeletons.setVisible(false);
					ckbSpheroidCoreVsPeriphery.setVisible(false);
					ckbTertiaryObjectOption.setVisible(false);
					pnlCoreVolValue.setVisible(false);
				} 
				if(analysisMode.equals("Spheroid")) {
					ckbSpheroidCoreVsPeriphery.setVisible(true);
					pnlCoreVolValue.setVisible(false);
					ckbSpeckleSkeletons.setVisible(false);
					ckbTertiaryObjectOption.setVisible(true);
				}
				if(analysisMode.equals("SingleCells")) {
					ckbTertiaryObjectOption.setVisible(true);
					ckbSpeckleSkeletons.setVisible(false);
					ckbSpheroidCoreVsPeriphery.setVisible(false);
					pnlCoreVolValue.setVisible(false);
				} 
				if(analysisMode.equals("Speckles")) {
					ckbSpeckleSkeletons.setVisible(true);
					ckbSpheroidCoreVsPeriphery.setVisible(false);
					ckbTertiaryObjectOption.setVisible(false);
					pnlCoreVolValue.setVisible(false);
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
		 * Display the core fraction input panel.
		 */

		ckbSpheroidCoreVsPeriphery.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (ckbSpheroidCoreVsPeriphery.isSelected()) {
					pnlCoreVolValue.setVisible(true);
				} else {
					pnlCoreVolValue.setVisible(false);
				}
			}
		});

	}

	public void setMode(int index) {
		cbAnalysisMode.setSelectedIndex(index);
	}
	
	private static class __Tmp {
		private static void __tmp() {
			  javax.swing.JPanel __wbp_panel = new javax.swing.JPanel();
		}
	}
	
	
	
}
