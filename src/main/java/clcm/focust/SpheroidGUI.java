package clcm.focust;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import net.miginfocom.swing.MigLayout;
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
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.border.MatteBorder;

import ij.IJ;

public class SpheroidGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtInputDir;
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
	private JTextField txtSecSpotX;
	private JTextField txtSecSpotY;
	private JTextField txtSecSpotZ;
	private JTextField txtSecondaryThreshold;
	private JTextField txtCoreProportion;
	private JTextField txtPrimaryS1X;
	private JTextField txtPrimaryS1Y;
	private JTextField txtPrimaryS1Z;
	private JTextField txtPrimaryS2X;
	private JTextField txtPrimaryS2Y;
	private JTextField txtPrimaryS2Z;
	private JTextField txtPrimaryBGRadius;
	private JTextField txtSecondaryS2X;
	private JTextField txtSecondaryS2Y;
	private JTextField txtSecondaryS2Z;
	private JTextField txtSecondaryS1X;
	private JTextField txtSecondaryS1Y;
	private JTextField txtSecondaryS1Z;
	private JTextField txtSecondaryBGRadius;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpheroidGUI frame = new SpheroidGUI();
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
	public SpheroidGUI() {
		setTitle("FOCUST: Spheroid Analysis");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 870, 633);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{801, 0};
		gbl_contentPane.rowHeights = new int[]{149, 341, 76, 0};
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
		gbl_pnlHeader.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_pnlHeader.rowHeights = new int[]{0, 0, 0, 0, 19, 0};
		gbl_pnlHeader.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_pnlHeader.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlHeader.setLayout(gbl_pnlHeader);
		
		JLabel lblNewLabel = new JLabel("Process a Dataset:");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		pnlHeader.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("You must have optimized your segmentation parameters.");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel_1.gridwidth = 4;
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		pnlHeader.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnHelp = new GridBagConstraints();
		gbc_btnHelp.insets = new Insets(0, 0, 5, 0);
		gbc_btnHelp.gridx = 8;
		gbc_btnHelp.gridy = 0;
		pnlHeader.add(btnHelp, gbc_btnHelp);
		
		JLabel lblNewLabel_2 = new JLabel("Select an input directory:");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 1;
		pnlHeader.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JButton btnBrowseInput = new JButton("Browse");
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
		gbc_txtInputDir.gridwidth = 3;
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
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
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
		gbc_txtOutputDir.gridwidth = 3;
		gbc_txtOutputDir.insets = new Insets(0, 0, 5, 5);
		gbc_txtOutputDir.fill = GridBagConstraints.BOTH;
		gbc_txtOutputDir.gridx = 2;
		gbc_txtOutputDir.gridy = 2;
		pnlHeader.add(txtOutputDir, gbc_txtOutputDir);
		
		btnBrowseInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String iDir = IJ.getDir("Select an Input Directory");
				txtInputDir.setText(iDir.toString());
				txtOutputDir.setText(iDir.toString());
			}
		});
		
		btnBrowseOutput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String oDir = IJ.getDir("Select an Input Directory");
				txtOutputDir.setText(oDir.toString());
			}
		});
		
		JCheckBox cbAnalysisOnly = new JCheckBox("Analysis only mode?");
		cbAnalysisOnly.setToolTipText("Runs analysis where the user provides labelled and original images.");
		cbAnalysisOnly.setSelected(true);
		cbAnalysisOnly.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbAnalysisOnly = new GridBagConstraints();
		gbc_cbAnalysisOnly.anchor = GridBagConstraints.WEST;
		gbc_cbAnalysisOnly.insets = new Insets(0, 0, 5, 5);
		gbc_cbAnalysisOnly.gridx = 0;
		gbc_cbAnalysisOnly.gridy = 3;
		pnlHeader.add(cbAnalysisOnly, gbc_cbAnalysisOnly);
		
		JCheckBox cbCoreVsPeriphery = new JCheckBox("Core vs Periphery?");
		cbCoreVsPeriphery.setToolTipText("Tick the box to generate a 3D core and periphery (based on the target volume % for the core), calculate the intensity of all channels in the core and periphery regions. Ratios comparing channel intensity (core vs periphery) will also be calculated.");
		cbCoreVsPeriphery.setSelected(true);
		GridBagConstraints gbc_cbCoreVsPeriphery = new GridBagConstraints();
		gbc_cbCoreVsPeriphery.gridwidth = 2;
		gbc_cbCoreVsPeriphery.insets = new Insets(0, 0, 5, 5);
		gbc_cbCoreVsPeriphery.gridx = 1;
		gbc_cbCoreVsPeriphery.gridy = 3;
		pnlHeader.add(cbCoreVsPeriphery, gbc_cbCoreVsPeriphery);
		cbCoreVsPeriphery.setFont(new Font("Arial", Font.PLAIN, 14));
		
			
		
		JLabel lblCoreVolValue = new JLabel("Core Volume % (0:1)");
		lblCoreVolValue.setToolTipText("Provide the volume % to reduce the \"core\" of the spheroid to. i.e 0.5 = 50 %.");
		GridBagConstraints gbc_lblCoreVolValue = new GridBagConstraints();
		gbc_lblCoreVolValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblCoreVolValue.gridx = 3;
		gbc_lblCoreVolValue.gridy = 3;
		pnlHeader.add(lblCoreVolValue, gbc_lblCoreVolValue);
		lblCoreVolValue.setFont(new Font("Arial", Font.PLAIN, 14));
		
		txtCoreProportion = new JTextField();
		txtCoreProportion.setText("0.5");
		GridBagConstraints gbc_txtCoreProportion = new GridBagConstraints();
		gbc_txtCoreProportion.anchor = GridBagConstraints.WEST;
		gbc_txtCoreProportion.insets = new Insets(0, 0, 5, 5);
		gbc_txtCoreProportion.gridx = 4;
		gbc_txtCoreProportion.gridy = 3;
		pnlHeader.add(txtCoreProportion, gbc_txtCoreProportion);
		txtCoreProportion.setFont(new Font("Arial", Font.PLAIN, 14));
		txtCoreProportion.setColumns(5);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setForeground(new Color(169, 169, 169));
		separator_1_1.setBackground(Color.WHITE);
		GridBagConstraints gbc_separator_1_1 = new GridBagConstraints();
		gbc_separator_1_1.gridwidth = 9;
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
		pnlMain.setLayout(new GridLayout(1, 3, 3, 0));
		
		JPanel pnlVariable = new JPanel();
		pnlMain.add(pnlVariable);
		GridBagLayout gbl_pnlVariable = new GridBagLayout();
		gbl_pnlVariable.columnWidths = new int[]{0, 0, 0};
		gbl_pnlVariable.rowHeights = new int[] {35, 35, 35, 35, 45, 35, 0};
		gbl_pnlVariable.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_pnlVariable.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlVariable.setLayout(gbl_pnlVariable);
		
		JLabel lblNewLabel_3_1 = new JLabel("Name Channel 1:");
		lblNewLabel_3_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_3_1 = new GridBagConstraints();
		gbc_lblNewLabel_3_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_3_1.gridx = 0;
		gbc_lblNewLabel_3_1.gridy = 0;
		pnlVariable.add(lblNewLabel_3_1, gbc_lblNewLabel_3_1);
		
		txtC1 = new JTextField();
		txtC1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_txtC1 = new GridBagConstraints();
		gbc_txtC1.insets = new Insets(0, 0, 5, 5);
		gbc_txtC1.fill = GridBagConstraints.BOTH;
		gbc_txtC1.gridx = 1;
		gbc_txtC1.gridy = 0;
		pnlVariable.add(txtC1, gbc_txtC1);
		txtC1.setColumns(10);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Name Channel 2:");
		lblNewLabel_3_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_3_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_3_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3_1_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_3_1_1.gridx = 0;
		gbc_lblNewLabel_3_1_1.gridy = 1;
		pnlVariable.add(lblNewLabel_3_1_1, gbc_lblNewLabel_3_1_1);
		
		txtC2 = new JTextField();
		txtC2.setFont(new Font("Arial", Font.PLAIN, 14));
		txtC2.setColumns(10);
		GridBagConstraints gbc_txtC2 = new GridBagConstraints();
		gbc_txtC2.insets = new Insets(0, 0, 5, 5);
		gbc_txtC2.fill = GridBagConstraints.BOTH;
		gbc_txtC2.gridx = 1;
		gbc_txtC2.gridy = 1;
		pnlVariable.add(txtC2, gbc_txtC2);
		
		JLabel lblNewLabel_3_1_2 = new JLabel("Name Channel 3:");
		lblNewLabel_3_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_3_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_3_1_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3_1_2.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_3_1_2.gridx = 0;
		gbc_lblNewLabel_3_1_2.gridy = 2;
		pnlVariable.add(lblNewLabel_3_1_2, gbc_lblNewLabel_3_1_2);
		
		txtC3 = new JTextField();
		txtC3.setFont(new Font("Arial", Font.PLAIN, 14));
		txtC3.setColumns(10);
		GridBagConstraints gbc_txtC3 = new GridBagConstraints();
		gbc_txtC3.insets = new Insets(0, 0, 5, 5);
		gbc_txtC3.fill = GridBagConstraints.BOTH;
		gbc_txtC3.gridx = 1;
		gbc_txtC3.gridy = 2;
		pnlVariable.add(txtC3, gbc_txtC3);
		
		JLabel lblNewLabel_3_1_3 = new JLabel("Name Channel 4:");
		lblNewLabel_3_1_3.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_3_1_3 = new GridBagConstraints();
		gbc_lblNewLabel_3_1_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3_1_3.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_3_1_3.gridx = 0;
		gbc_lblNewLabel_3_1_3.gridy = 3;
		pnlVariable.add(lblNewLabel_3_1_3, gbc_lblNewLabel_3_1_3);
		
		txtC4 = new JTextField();
		txtC4.setFont(new Font("Arial", Font.PLAIN, 14));
		txtC4.setColumns(10);
		GridBagConstraints gbc_txtC4 = new GridBagConstraints();
		gbc_txtC4.insets = new Insets(0, 0, 5, 5);
		gbc_txtC4.fill = GridBagConstraints.BOTH;
		gbc_txtC4.gridx = 1;
		gbc_txtC4.gridy = 3;
		pnlVariable.add(txtC4, gbc_txtC4);
		
		JLabel lblNewLabel_3_1_4 = new JLabel("Grouping Info?");
		lblNewLabel_3_1_4.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_3_1_4 = new GridBagConstraints();
		gbc_lblNewLabel_3_1_4.insets = new Insets(8, 5, 5, 5);
		gbc_lblNewLabel_3_1_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1_4.gridx = 0;
		gbc_lblNewLabel_3_1_4.gridy = 4;
		pnlVariable.add(lblNewLabel_3_1_4, gbc_lblNewLabel_3_1_4);
		
		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 14));
		textField.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(8, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 4;
		pnlVariable.add(textField, gbc_textField);
		
		JPanel pnlPrimary = new JPanel();
		pnlPrimary.setBorder(new MatteBorder(0, 1, 0, 1, (Color) new Color(169, 169, 169)));
		pnlMain.add(pnlPrimary);
		GridBagLayout gbl_pnlPrimary = new GridBagLayout();
		gbl_pnlPrimary.columnWidths = new int[]{0, 0, 0};
		gbl_pnlPrimary.rowHeights = new int[] {0, 0, 0, 0, 0, 30, 0, 0, 0, 30, 0};
		gbl_pnlPrimary.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_pnlPrimary.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		
		JLabel lblNewLabel_5_3 = new JLabel("Background:");
		lblNewLabel_5_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_5_3.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 2;
		pnlPrimary.add(lblNewLabel_5_3, gbc);
		
		JComboBox cbPrimaryBackground = new JComboBox();
		cbPrimaryBackground.setModel(new DefaultComboBoxModel(new String[] {"None", "Default", "3D DoG", "3D Top Hat"}));
		cbPrimaryBackground.setSelectedIndex(0);
		cbPrimaryBackground.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbPrimaryBackground = new GridBagConstraints();
		gbc_cbPrimaryBackground.insets = new Insets(0, 0, 5, 0);
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
		
		JLabel lblNewLabel_7 = new JLabel("S1:");
		lblNewLabel_7.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGFirstBlur.add(lblNewLabel_7);
		
		JLabel lblNewLabel_6_5 = new JLabel("X");
		lblNewLabel_6_5.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_5.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_5.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGFirstBlur.add(lblNewLabel_6_5);
		
		txtPrimaryS1X = new JTextField();
		txtPrimaryS1X.setText("1");
		txtPrimaryS1X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryS1X.setColumns(4);
		pnlPrimaryBGFirstBlur.add(txtPrimaryS1X);
		
		JLabel lblNewLabel_6_1_3 = new JLabel("Y");
		lblNewLabel_6_1_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGFirstBlur.add(lblNewLabel_6_1_3);
		
		txtPrimaryS1Y = new JTextField();
		txtPrimaryS1Y.setText("1");
		txtPrimaryS1Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryS1Y.setColumns(4);
		pnlPrimaryBGFirstBlur.add(txtPrimaryS1Y);
		
		JLabel lblNewLabel_6_2_3 = new JLabel("Z");
		lblNewLabel_6_2_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGFirstBlur.add(lblNewLabel_6_2_3);
		
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
		
		JLabel lblNewLabel_7_1 = new JLabel("S2:");
		lblNewLabel_7_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGSecondBlur.add(lblNewLabel_7_1);
		
		
		JLabel lblNewLabel_6_5_1 = new JLabel("X");
		lblNewLabel_6_5_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_5_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_5_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGSecondBlur.add(lblNewLabel_6_5_1);
		
		txtPrimaryS2X = new JTextField();
		txtPrimaryS2X.setText("1");
		txtPrimaryS2X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryS2X.setColumns(4);
		pnlPrimaryBGSecondBlur.add(txtPrimaryS2X);
		
		JLabel lblNewLabel_6_1_3_1 = new JLabel("Y");
		lblNewLabel_6_1_3_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_3_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGSecondBlur.add(lblNewLabel_6_1_3_1);
		
		txtPrimaryS2Y = new JTextField();
		txtPrimaryS2Y.setText("1");
		txtPrimaryS2Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryS2Y.setColumns(4);
		pnlPrimaryBGSecondBlur.add(txtPrimaryS2Y);
		
		JLabel lblNewLabel_6_2_3_1 = new JLabel("Z");
		lblNewLabel_6_2_3_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_3_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGSecondBlur.add(lblNewLabel_6_2_3_1);
		
		txtPrimaryS2Z = new JTextField();
		txtPrimaryS2Z.setText("1");
		txtPrimaryS2Z.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryS2Z.setColumns(4);
		pnlPrimaryBGSecondBlur.add(txtPrimaryS2Z);
		
		JLabel lblNewLabel_5_1_1 = new JLabel("Filter:");
		lblNewLabel_5_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_5_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_1_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_1_1.gridx = 0;
		gbc_lblNewLabel_5_1_1.gridy = 5;
		pnlPrimary.add(lblNewLabel_5_1_1, gbc_lblNewLabel_5_1_1);
		
		JComboBox cbPrimaryFilter = new JComboBox();
		cbPrimaryFilter.setModel(new DefaultComboBoxModel(new String[] {"3D Gaussian Blur", "3D Median", "3D Mean", "None"}));
		cbPrimaryFilter.setSelectedIndex(0);
		cbPrimaryFilter.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbPrimaryFilter = new GridBagConstraints();
		gbc_cbPrimaryFilter.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbPrimaryFilter.insets = new Insets(0, 0, 5, 0);
		gbc_cbPrimaryFilter.gridx = 1;
		gbc_cbPrimaryFilter.gridy = 5;
		pnlPrimary.add(cbPrimaryFilter, gbc_cbPrimaryFilter);
		
		JPanel pnlPrimaryBlurSize = new JPanel();
		GridBagConstraints gbc_pnlPrimaryBlurSize = new GridBagConstraints();
		gbc_pnlPrimaryBlurSize.anchor = GridBagConstraints.EAST;
		gbc_pnlPrimaryBlurSize.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimaryBlurSize.insets = new Insets(0, 0, 5, 0);
		gbc_pnlPrimaryBlurSize.gridwidth = 2;
		gbc_pnlPrimaryBlurSize.gridx = 0;
		gbc_pnlPrimaryBlurSize.gridy = 6;
		pnlPrimary.add(pnlPrimaryBlurSize, gbc_pnlPrimaryBlurSize);
		
		JLabel lblNewLabel_6 = new JLabel("X");
		lblNewLabel_6.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6.setVerticalAlignment(SwingConstants.TOP);
		pnlPrimaryBlurSize.add(lblNewLabel_6);
		
		txtPriFilterX = new JTextField();
		txtPriFilterX.setText("1");
		txtPriFilterX.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPriFilterX.setColumns(4);
		pnlPrimaryBlurSize.add(txtPriFilterX);
		
		JLabel lblNewLabel_6_1 = new JLabel("Y");
		lblNewLabel_6_1.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_6_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1.setHorizontalAlignment(SwingConstants.LEFT);
		pnlPrimaryBlurSize.add(lblNewLabel_6_1);
		
		txtPriFilterY = new JTextField();
		txtPriFilterY.setText("1");
		txtPriFilterY.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPriFilterY.setColumns(4);
		pnlPrimaryBlurSize.add(txtPriFilterY);
		
		JLabel lblNewLabel_6_2 = new JLabel("Z");
		lblNewLabel_6_2.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel_6_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2.setHorizontalAlignment(SwingConstants.LEFT);
		pnlPrimaryBlurSize.add(lblNewLabel_6_2);
		
		txtPriFilterZ = new JTextField();
		txtPriFilterZ.setText("1");
		txtPriFilterZ.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPriFilterZ.setColumns(4);
		pnlPrimaryBlurSize.add(txtPriFilterZ);
		
		JLabel lblNewLabel_5_1 = new JLabel("Method:");
		lblNewLabel_5_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_1 = new GridBagConstraints();
		gbc_lblNewLabel_5_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_1.gridx = 0;
		gbc_lblNewLabel_5_1.gridy = 7;
		pnlPrimary.add(lblNewLabel_5_1, gbc_lblNewLabel_5_1);
		
		JComboBox cbPrimaryMethod = new JComboBox();
		cbPrimaryMethod.setModel(new DefaultComboBoxModel(new String[] {"Marker Controlled"}));
		cbPrimaryMethod.setSelectedIndex(0);
		cbPrimaryMethod.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbPrimaryMethod = new GridBagConstraints();
		gbc_cbPrimaryMethod.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbPrimaryMethod.insets = new Insets(0, 0, 5, 0);
		gbc_cbPrimaryMethod.gridx = 1;
		gbc_cbPrimaryMethod.gridy = 7;
		pnlPrimary.add(cbPrimaryMethod, gbc_cbPrimaryMethod);
		
		JPanel pnlPrimarySpotSize = new JPanel();
		GridBagConstraints gbc_pnlPrimarySpotSize = new GridBagConstraints();
		gbc_pnlPrimarySpotSize.anchor = GridBagConstraints.EAST;
		gbc_pnlPrimarySpotSize.insets = new Insets(0, 0, 5, 0);
		gbc_pnlPrimarySpotSize.gridwidth = 2;
		gbc_pnlPrimarySpotSize.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimarySpotSize.gridx = 0;
		gbc_pnlPrimarySpotSize.gridy = 8;
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
		gbc_pnlPrimaryBGRadius.gridwidth = 2;
		gbc_pnlPrimaryBGRadius.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimaryBGRadius.gridx = 0;
		gbc_pnlPrimaryBGRadius.gridy = 3;
		pnlPrimary.add(pnlPrimaryBGRadius, gbc_pnlPrimaryBGRadius);
		pnlPrimaryBGRadius.setVisible(false);
		
		JLabel lblNewLabel_6_3_1_1_1 = new JLabel("Radius:");
		lblNewLabel_6_3_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_3_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_3_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGRadius.add(lblNewLabel_6_3_1_1_1);
		
		txtPrimaryBGRadius = new JTextField();
		txtPrimaryBGRadius.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryBGRadius.setColumns(6);
		pnlPrimaryBGRadius.add(txtPrimaryBGRadius);
		
		JPanel pnlSecondary = new JPanel();
		pnlMain.add(pnlSecondary);
		GridBagLayout gbl_pnlSecondary = new GridBagLayout();
		gbl_pnlSecondary.columnWidths = new int[]{0, 0, 0};
		gbl_pnlSecondary.rowHeights = new int[] {0, 0, 0, 0, 0, 30, 0, 0, 0, 30, 0};
		gbl_pnlSecondary.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_pnlSecondary.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlSecondary.setLayout(gbl_pnlSecondary);
		
		JLabel lblNewLabel_4_1 = new JLabel("Secondary Object");
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
		
		JLabel lblNewLabel_5_3_1 = new JLabel("Background:");
		lblNewLabel_5_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_5_3_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_3_1 = new GridBagConstraints();
		gbc_lblNewLabel_5_3_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_3_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_3_1.gridx = 0;
		gbc_lblNewLabel_5_3_1.gridy = 2;
		pnlSecondary.add(lblNewLabel_5_3_1, gbc_lblNewLabel_5_3_1);
		
		JComboBox cbSecondaryBackground = new JComboBox();
		cbSecondaryBackground.setModel(new DefaultComboBoxModel(new String[] {"None", "Default", "3D DoG", "3D Top Hat"}));
		cbSecondaryBackground.setSelectedIndex(0);
		cbSecondaryBackground.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbSecondaryBackground = new GridBagConstraints();
		gbc_cbSecondaryBackground.insets = new Insets(0, 0, 5, 5);
		gbc_cbSecondaryBackground.fill = GridBagConstraints.BOTH;
		gbc_cbSecondaryBackground.gridx = 1;
		gbc_cbSecondaryBackground.gridy = 2;
		pnlSecondary.add(cbSecondaryBackground, gbc_cbSecondaryBackground);
		
		JPanel pnlSecondaryBGFirstBlur = new JPanel();
		GridBagConstraints gbc_pnlSecondaryBGFirstBlur = new GridBagConstraints();
		gbc_pnlSecondaryBGFirstBlur.anchor = GridBagConstraints.EAST;
		gbc_pnlSecondaryBGFirstBlur.gridwidth = 2;
		gbc_pnlSecondaryBGFirstBlur.insets = new Insets(0, 0, 5, 5);
		gbc_pnlSecondaryBGFirstBlur.fill = GridBagConstraints.VERTICAL;
		gbc_pnlSecondaryBGFirstBlur.gridx = 0;
		gbc_pnlSecondaryBGFirstBlur.gridy = 3;
		pnlSecondary.add(pnlSecondaryBGFirstBlur, gbc_pnlSecondaryBGFirstBlur);
		pnlSecondaryBGFirstBlur.setVisible(false);
		
		JLabel lblNewLabel_7_2 = new JLabel("S1:");
		lblNewLabel_7_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGFirstBlur.add(lblNewLabel_7_2);
		
		JLabel lblNewLabel_6_4_1_1 = new JLabel("X");
		lblNewLabel_6_4_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_4_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_4_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGFirstBlur.add(lblNewLabel_6_4_1_1);
		
		txtSecondaryS1X = new JTextField();
		txtSecondaryS1X.setText("1");
		txtSecondaryS1X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryS1X.setColumns(4);
		pnlSecondaryBGFirstBlur.add(txtSecondaryS1X);
		
		JLabel lblNewLabel_6_1_2_1_1 = new JLabel("Y");
		lblNewLabel_6_1_2_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_2_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_2_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGFirstBlur.add(lblNewLabel_6_1_2_1_1);
		
		txtSecondaryS1Y = new JTextField();
		txtSecondaryS1Y.setText("1");
		txtSecondaryS1Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryS1Y.setColumns(4);
		pnlSecondaryBGFirstBlur.add(txtSecondaryS1Y);
		
		JLabel lblNewLabel_6_2_2_1_1 = new JLabel("Z");
		lblNewLabel_6_2_2_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_2_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_2_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGFirstBlur.add(lblNewLabel_6_2_2_1_1);
		
		txtSecondaryS1Z = new JTextField();
		txtSecondaryS1Z.setText("1");
		txtSecondaryS1Z.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryS1Z.setColumns(4);
		pnlSecondaryBGFirstBlur.add(txtSecondaryS1Z);
		
		JPanel pnlSecondaryBGSecondBlur = new JPanel();
		GridBagConstraints gbc_pnlSecondaryBGSecondBlur = new GridBagConstraints();
		gbc_pnlSecondaryBGSecondBlur.anchor = GridBagConstraints.EAST;
		gbc_pnlSecondaryBGSecondBlur.gridwidth = 2;
		gbc_pnlSecondaryBGSecondBlur.insets = new Insets(0, 0, 5, 5);
		gbc_pnlSecondaryBGSecondBlur.fill = GridBagConstraints.VERTICAL;
		gbc_pnlSecondaryBGSecondBlur.gridx = 0;
		gbc_pnlSecondaryBGSecondBlur.gridy = 4;
		pnlSecondary.add(pnlSecondaryBGSecondBlur, gbc_pnlSecondaryBGSecondBlur);
		pnlSecondaryBGSecondBlur.setVisible(false);
		
		JLabel lblNewLabel_7_1_1 = new JLabel("S2:");
		lblNewLabel_7_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGSecondBlur.add(lblNewLabel_7_1_1);
		
		JLabel lblNewLabel_6_4_1 = new JLabel("X");
		lblNewLabel_6_4_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_4_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_4_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGSecondBlur.add(lblNewLabel_6_4_1);
		
		txtSecondaryS2X = new JTextField();
		txtSecondaryS2X.setText("1");
		txtSecondaryS2X.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryS2X.setColumns(4);
		pnlSecondaryBGSecondBlur.add(txtSecondaryS2X);
		
		JLabel lblNewLabel_6_1_2_1 = new JLabel("Y");
		lblNewLabel_6_1_2_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGSecondBlur.add(lblNewLabel_6_1_2_1);
		
		txtSecondaryS2Y = new JTextField();
		txtSecondaryS2Y.setText("1");
		txtSecondaryS2Y.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryS2Y.setColumns(4);
		pnlSecondaryBGSecondBlur.add(txtSecondaryS2Y);
		
		JLabel lblNewLabel_6_2_2_1 = new JLabel("Z");
		lblNewLabel_6_2_2_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGSecondBlur.add(lblNewLabel_6_2_2_1);
		
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
		cbSecondaryFilter.setModel(new DefaultComboBoxModel(new String[] {"3D Gaussian Blur", "3D Median", "None"}));
		cbSecondaryFilter.setSelectedIndex(0);
		cbSecondaryFilter.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbSecondaryFilter = new GridBagConstraints();
		gbc_cbSecondaryFilter.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbSecondaryFilter.insets = new Insets(0, 0, 5, 5);
		gbc_cbSecondaryFilter.gridx = 1;
		gbc_cbSecondaryFilter.gridy = 5;
		pnlSecondary.add(cbSecondaryFilter, gbc_cbSecondaryFilter);
		
		JPanel pnlPrimaryBlurSize_1 = new JPanel();
		GridBagConstraints gbc_pnlPrimaryBlurSize_1 = new GridBagConstraints();
		gbc_pnlPrimaryBlurSize_1.anchor = GridBagConstraints.EAST;
		gbc_pnlPrimaryBlurSize_1.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimaryBlurSize_1.gridwidth = 2;
		gbc_pnlPrimaryBlurSize_1.insets = new Insets(0, 0, 5, 5);
		gbc_pnlPrimaryBlurSize_1.gridx = 0;
		gbc_pnlPrimaryBlurSize_1.gridy = 6;
		pnlSecondary.add(pnlPrimaryBlurSize_1, gbc_pnlPrimaryBlurSize_1);
		
		JLabel lblNewLabel_6_4 = new JLabel("X");
		lblNewLabel_6_4.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_4.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_1.add(lblNewLabel_6_4);
		
		txtSecFilterX = new JTextField();
		txtSecFilterX.setText("1");
		txtSecFilterX.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecFilterX.setColumns(4);
		pnlPrimaryBlurSize_1.add(txtSecFilterX);
		
		JLabel lblNewLabel_6_1_2 = new JLabel("Y");
		lblNewLabel_6_1_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_1.add(lblNewLabel_6_1_2);
		
		txtSecFilterY = new JTextField();
		txtSecFilterY.setText("1");
		txtSecFilterY.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecFilterY.setColumns(4);
		pnlPrimaryBlurSize_1.add(txtSecFilterY);
		
		JLabel lblNewLabel_6_2_2 = new JLabel("Z");
		lblNewLabel_6_2_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_1.add(lblNewLabel_6_2_2);
		
		txtSecFilterZ = new JTextField();
		txtSecFilterZ.setText("1");
		txtSecFilterZ.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecFilterZ.setColumns(4);
		pnlPrimaryBlurSize_1.add(txtSecFilterZ);
		
		JLabel lblNewLabel_5_1_2 = new JLabel("Method:");
		lblNewLabel_5_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_5_1_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_1_2.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_1_2.gridx = 0;
		gbc_lblNewLabel_5_1_2.gridy = 7;
		pnlSecondary.add(lblNewLabel_5_1_2, gbc_lblNewLabel_5_1_2);
		
		JComboBox cbSecondaryMethod = new JComboBox();
		
		
		cbSecondaryMethod.setModel(new DefaultComboBoxModel(new String[] {"Marker Controlled", "Greater Constant", "Otsu Threshold"}));
		cbSecondaryMethod.setSelectedIndex(1);
		cbSecondaryMethod.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbSecondaryMethod = new GridBagConstraints();
		gbc_cbSecondaryMethod.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbSecondaryMethod.insets = new Insets(0, 0, 5, 5);
		gbc_cbSecondaryMethod.gridx = 1;
		gbc_cbSecondaryMethod.gridy = 7;
		pnlSecondary.add(cbSecondaryMethod, gbc_cbSecondaryMethod);
		
		
		
		JPanel pnlSecondaryThreshold = new JPanel();
		GridBagConstraints gbc_pnlSecondaryTreshold = new GridBagConstraints();
		gbc_pnlSecondaryTreshold.anchor = GridBagConstraints.EAST;
		gbc_pnlSecondaryTreshold.gridwidth = 2;
		gbc_pnlSecondaryTreshold.insets = new Insets(0, 0, 5, 5);
		gbc_pnlSecondaryTreshold.fill = GridBagConstraints.VERTICAL;
		gbc_pnlSecondaryTreshold.gridx = 0;
		gbc_pnlSecondaryTreshold.gridy = 8;
		pnlSecondary.add(pnlSecondaryThreshold, gbc_pnlSecondaryTreshold);

		JPanel pnlSecondarySpotSize = new JPanel();
		GridBagConstraints gbc_pnlSecondarySpotSize = new GridBagConstraints();
		gbc_pnlSecondarySpotSize.anchor = GridBagConstraints.EAST;
		gbc_pnlSecondarySpotSize.fill = GridBagConstraints.VERTICAL;
		gbc_pnlSecondarySpotSize.gridwidth = 2;
		gbc_pnlSecondarySpotSize.insets = new Insets(0, 0, 5, 5);
		gbc_pnlSecondarySpotSize.gridx = 0;
		gbc_pnlSecondarySpotSize.gridy = 8;
		pnlSecondary.add(pnlSecondarySpotSize, gbc_pnlSecondarySpotSize);
		pnlSecondarySpotSize.setVisible(false);
		
		
	
		JLabel lblNewLabel_6_3_1_1 = new JLabel("Threshold:");
		lblNewLabel_6_3_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_3_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_3_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryThreshold.add(lblNewLabel_6_3_1_1);
		
		txtSecondaryThreshold = new JTextField();
		txtSecondaryThreshold.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryThreshold.setColumns(6);
		pnlSecondaryThreshold.add(txtSecondaryThreshold);
		
		
		JLabel lblNewLabel_6_3_1 = new JLabel("X");
		lblNewLabel_6_3_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_3_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondarySpotSize.add(lblNewLabel_6_3_1);
		
		txtSecSpotX = new JTextField();
		txtSecSpotX.setText("1");
		txtSecSpotX.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecSpotX.setColumns(4);
		pnlSecondarySpotSize.add(txtSecSpotX);
		
		JLabel lblNewLabel_6_1_1_1 = new JLabel("Y");
		lblNewLabel_6_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondarySpotSize.add(lblNewLabel_6_1_1_1);
		
		txtSecSpotY = new JTextField();
		txtSecSpotY.setText("1");
		txtSecSpotY.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecSpotY.setColumns(4);
		pnlSecondarySpotSize.add(txtSecSpotY);
		
		JLabel lblNewLabel_6_2_1_1 = new JLabel("Z");
		lblNewLabel_6_2_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondarySpotSize.add(lblNewLabel_6_2_1_1);
		
		txtSecSpotZ = new JTextField();
		txtSecSpotZ.setText("1");
		txtSecSpotZ.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecSpotZ.setColumns(4);
		pnlSecondarySpotSize.add(txtSecSpotZ);
		
		JPanel pnlSecondaryBGRadius = new JPanel();
		GridBagConstraints gbc_pnlSecondaryBGRadius = new GridBagConstraints();
		gbc_pnlSecondaryBGRadius.insets = new Insets(0, 0, 0, 5);
		gbc_pnlSecondaryBGRadius.anchor = GridBagConstraints.EAST;
		gbc_pnlSecondaryBGRadius.fill = GridBagConstraints.VERTICAL;
		gbc_pnlSecondaryBGRadius.gridx = 1;
		gbc_pnlSecondaryBGRadius.gridy = 3;
		pnlSecondary.add(pnlSecondaryBGRadius, gbc_pnlSecondaryBGRadius);
		pnlSecondaryBGRadius.setVisible(false);
		
		JLabel lblNewLabel_6_3_1_1_2 = new JLabel("Radius:");
		lblNewLabel_6_3_1_1_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_3_1_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_3_1_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGRadius.add(lblNewLabel_6_3_1_1_2);
		
		txtSecondaryBGRadius = new JTextField();
		txtSecondaryBGRadius.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryBGRadius.setColumns(6);
		pnlSecondaryBGRadius.add(txtSecondaryBGRadius);
		
		
		
		JPanel pnlFooter = new JPanel();
		GridBagConstraints gbc_pnlFooter = new GridBagConstraints();
		gbc_pnlFooter.anchor = GridBagConstraints.SOUTH;
		gbc_pnlFooter.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlFooter.insets = new Insets(0, 0, 5, 0);
		gbc_pnlFooter.gridx = 0;
		gbc_pnlFooter.gridy = 2;
		contentPane.add(pnlFooter, gbc_pnlFooter);
		GridBagLayout gbl_pnlFooter = new GridBagLayout();
		gbl_pnlFooter.columnWidths = new int[]{0, 0, 0, 0};
		gbl_pnlFooter.rowHeights = new int[]{0, 0, 0, 0};
		gbl_pnlFooter.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0};
		gbl_pnlFooter.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlFooter.setLayout(gbl_pnlFooter);
		
		JButton btnBackToMenu = new JButton("Back to Menu");
		btnBackToMenu.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnBackToMenu = new GridBagConstraints();
		gbc_btnBackToMenu.insets = new Insets(0, 0, 5, 5);
		gbc_btnBackToMenu.gridx = 0;
		gbc_btnBackToMenu.gridy = 1;
		pnlFooter.add(btnBackToMenu, gbc_btnBackToMenu);
		
		JButton btnLoadParameters = new JButton("Load Parameters");
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
		gbc_separator_1_1_1.gridwidth = 8;
		gbc_separator_1_1_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1_1_1.gridx = 0;
		gbc_separator_1_1_1.gridy = 0;
		pnlFooter.add(separator_1_1_1, gbc_separator_1_1_1);
		
		JButton btnRunAnalysis = new JButton("Run Analysis");
		btnRunAnalysis.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_btnRunAnalysis = new GridBagConstraints();
		gbc_btnRunAnalysis.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRunAnalysis.gridwidth = 2;
		gbc_btnRunAnalysis.insets = new Insets(0, 0, 0, 5);
		gbc_btnRunAnalysis.gridx = 0;
		gbc_btnRunAnalysis.gridy = 2;
		pnlFooter.add(btnRunAnalysis, gbc_btnRunAnalysis);
		
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainScreen MainGui = new MainScreen();
				MainGui.setVisible(true);
				Window win = SwingUtilities.getWindowAncestor(btnBackToMenu);
				win.dispose();
			}
		});
		

		cbCoreVsPeriphery.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbCoreVsPeriphery.isSelected()) {
					lblCoreVolValue.setVisible(true);
					txtCoreProportion.setVisible(true);
				} else {
					lblCoreVolValue.setVisible(false);
					txtCoreProportion.setVisible(false);
				}
			}
		});
		

		cbSecondaryMethod.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbSecondaryMethod.getSelectedItem().toString().equals("Marker Controlled")) {
					pnlSecondarySpotSize.setVisible(true);
					pnlSecondaryThreshold.setVisible(false);
				} else {
					pnlSecondarySpotSize.setVisible(false);
					pnlSecondaryThreshold.setVisible(true);
					
				}
				if(cbSecondaryMethod.getSelectedItem().toString().equals("Otsu Threshold")) {
					txtSecondaryThreshold.setEnabled(false);
				} else {
					txtSecondaryThreshold.setEnabled(true);
				}
			}
		});
		
		cbPrimaryBackground.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbPrimaryBackground.getSelectedItem().toString().equals("3D DoG")) {
					pnlPrimaryBGFirstBlur.setVisible(true);
					pnlPrimaryBGSecondBlur.setVisible(true);
					pnlPrimaryBGRadius.setVisible(false);
				} 
				if(cbPrimaryBackground.getSelectedItem().toString().equals("3D Top Hat")) {
					pnlPrimaryBGFirstBlur.setVisible(true);
					pnlPrimaryBGSecondBlur.setVisible(false);
					pnlPrimaryBGRadius.setVisible(false);
				}
				if(cbPrimaryBackground.getSelectedItem().toString().equals("Default")) {
					pnlPrimaryBGRadius.setVisible(true);
					pnlPrimaryBGFirstBlur.setVisible(false);
					pnlPrimaryBGSecondBlur.setVisible(false);
				} 
				if(cbPrimaryBackground.getSelectedItem().toString().equals("None")) {
					pnlPrimaryBGRadius.setVisible(false);
					pnlPrimaryBGFirstBlur.setVisible(false);
					pnlPrimaryBGSecondBlur.setVisible(false);
				}
			}
		});
		
		cbSecondaryBackground.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbSecondaryBackground.getSelectedItem().toString().equals("3D DoG")) {
					pnlSecondaryBGFirstBlur.setVisible(true);
					pnlSecondaryBGSecondBlur.setVisible(true);
					pnlSecondaryBGRadius.setVisible(false);
				} 
				if(cbSecondaryBackground.getSelectedItem().toString().equals("3D Top Hat")) {
					pnlSecondaryBGFirstBlur.setVisible(true);
					pnlSecondaryBGSecondBlur.setVisible(false);
					pnlSecondaryBGRadius.setVisible(false);
				}
				if(cbSecondaryBackground.getSelectedItem().toString().equals("Default")) {
					pnlSecondaryBGRadius.setVisible(true);
					pnlSecondaryBGFirstBlur.setVisible(false);
					pnlSecondaryBGSecondBlur.setVisible(false);
				} 
				if(cbSecondaryBackground.getSelectedItem().toString().equals("None")) {
					pnlSecondaryBGRadius.setVisible(false);
					pnlSecondaryBGFirstBlur.setVisible(false);
					pnlSecondaryBGSecondBlur.setVisible(false);
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
