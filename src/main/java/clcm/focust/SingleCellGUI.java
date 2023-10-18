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
import java.awt.Component;
import java.awt.Container;

import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ItemEvent;
import javax.swing.border.MatteBorder;
import javax.swing.JProgressBar;

public class SingleCellGUI extends JFrame {

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
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField txtTertiaryThreshold;
	private JTextField textField_1;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField txtPrimaryBGRadius;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textField_16;
	private JTextField textField_17;
	private JTextField textField_18;
	private JTextField textField_19;
	private JTextField txtSecondaryBGRadius;
	private JTextField textField_21;
	private JTextField textField_22;
	private JTextField textField_23;
	private JTextField textField_24;
	private JTextField textField_25;
	private JTextField textField_26;
	private JTextField txtTertiaryBGRadius;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SingleCellGUI frame = new SingleCellGUI();
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
	public SingleCellGUI() {
		setTitle("FOCUST: Single Cell Analysis");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1102, 605);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

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
		
		JCheckBox cbAnalysisOnly = new JCheckBox("Analysis only mode?");
		cbAnalysisOnly.setSelected(true);
		cbAnalysisOnly.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbAnalysisOnly = new GridBagConstraints();
		gbc_cbAnalysisOnly.anchor = GridBagConstraints.WEST;
		gbc_cbAnalysisOnly.insets = new Insets(0, 0, 5, 5);
		gbc_cbAnalysisOnly.gridx = 0;
		gbc_cbAnalysisOnly.gridy = 3;
		pnlHeader.add(cbAnalysisOnly, gbc_cbAnalysisOnly);
		
		JCheckBox chckbxTertiarySecondary = new JCheckBox("Tertiary = Secondary - Primary?");
		chckbxTertiarySecondary.setSelected(true);
		chckbxTertiarySecondary.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_chckbxTertiarySecondary = new GridBagConstraints();
		gbc_chckbxTertiarySecondary.gridwidth = 3;
		gbc_chckbxTertiarySecondary.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxTertiarySecondary.gridx = 1;
		gbc_chckbxTertiarySecondary.gridy = 3;
		pnlHeader.add(chckbxTertiarySecondary, gbc_chckbxTertiarySecondary);
		
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
		pnlMain.setLayout(new GridLayout(0, 4, 3, 0));
		
		JPanel pnlVariable = new JPanel();
		pnlMain.add(pnlVariable);
		GridBagLayout gbl_pnlVariable = new GridBagLayout();
		gbl_pnlVariable.columnWidths = new int[]{0, 0, 0};
		gbl_pnlVariable.rowHeights = new int[] {35, 22, 35, 35, 35, 35, 35, 0};
		gbl_pnlVariable.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_pnlVariable.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		pnlVariable.setLayout(gbl_pnlVariable);
		
		JPanel pnlKillBorders = new JPanel();
		GridBagConstraints gbc_pnlKillBorders = new GridBagConstraints();
		gbc_pnlKillBorders.anchor = GridBagConstraints.NORTH;
		gbc_pnlKillBorders.gridheight = 2;
		gbc_pnlKillBorders.fill = GridBagConstraints.HORIZONTAL;
		gbc_pnlKillBorders.gridwidth = 2;
		gbc_pnlKillBorders.insets = new Insets(0, 0, 5, 0);
		gbc_pnlKillBorders.gridx = 0;
		gbc_pnlKillBorders.gridy = 0;
		pnlVariable.add(pnlKillBorders, gbc_pnlKillBorders);
		
		JLabel lblNewLabel_7 = new JLabel("Kill borders?");
		lblNewLabel_7.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlKillBorders.add(lblNewLabel_7);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("No");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlKillBorders.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnXY = new JRadioButton("X+Y");
		buttonGroup.add(rdbtnXY);
		rdbtnXY.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlKillBorders.add(rdbtnXY);
		
		JRadioButton rdbtnXyz = new JRadioButton("X+Y+Z");
		buttonGroup.add(rdbtnXyz);
		rdbtnXyz.setFont(new Font("Arial", Font.PLAIN, 13));
		pnlKillBorders.add(rdbtnXyz);
		
		JLabel lblNewLabel_3_1 = new JLabel("Name Channel 1:");
		lblNewLabel_3_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_3_1 = new GridBagConstraints();
		gbc_lblNewLabel_3_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_3_1.gridx = 0;
		gbc_lblNewLabel_3_1.gridy = 2;
		pnlVariable.add(lblNewLabel_3_1, gbc_lblNewLabel_3_1);
		
		txtC1 = new JTextField();
		txtC1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_txtC1 = new GridBagConstraints();
		gbc_txtC1.insets = new Insets(0, 0, 5, 5);
		gbc_txtC1.fill = GridBagConstraints.BOTH;
		gbc_txtC1.gridx = 1;
		gbc_txtC1.gridy = 2;
		pnlVariable.add(txtC1, gbc_txtC1);
		txtC1.setColumns(10);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Name Channel 2:");
		lblNewLabel_3_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_3_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_3_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_3_1_1.gridx = 0;
		gbc_lblNewLabel_3_1_1.gridy = 3;
		pnlVariable.add(lblNewLabel_3_1_1, gbc_lblNewLabel_3_1_1);
		
		txtC2 = new JTextField();
		txtC2.setFont(new Font("Arial", Font.PLAIN, 14));
		txtC2.setColumns(10);
		GridBagConstraints gbc_txtC2 = new GridBagConstraints();
		gbc_txtC2.insets = new Insets(0, 0, 5, 5);
		gbc_txtC2.fill = GridBagConstraints.BOTH;
		gbc_txtC2.gridx = 1;
		gbc_txtC2.gridy = 3;
		pnlVariable.add(txtC2, gbc_txtC2);
		
		JLabel lblNewLabel_3_1_2 = new JLabel("Name Channel 3:");
		lblNewLabel_3_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_3_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_3_1_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1_2.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_3_1_2.gridx = 0;
		gbc_lblNewLabel_3_1_2.gridy = 4;
		pnlVariable.add(lblNewLabel_3_1_2, gbc_lblNewLabel_3_1_2);
		
		txtC3 = new JTextField();
		txtC3.setFont(new Font("Arial", Font.PLAIN, 14));
		txtC3.setColumns(10);
		GridBagConstraints gbc_txtC3 = new GridBagConstraints();
		gbc_txtC3.insets = new Insets(0, 0, 5, 5);
		gbc_txtC3.fill = GridBagConstraints.BOTH;
		gbc_txtC3.gridx = 1;
		gbc_txtC3.gridy = 4;
		pnlVariable.add(txtC3, gbc_txtC3);
		
		JLabel lblNewLabel_3_1_3 = new JLabel("Name Channel 4:");
		lblNewLabel_3_1_3.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_3_1_3 = new GridBagConstraints();
		gbc_lblNewLabel_3_1_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1_3.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_3_1_3.gridx = 0;
		gbc_lblNewLabel_3_1_3.gridy = 5;
		pnlVariable.add(lblNewLabel_3_1_3, gbc_lblNewLabel_3_1_3);
		
		txtC4 = new JTextField();
		txtC4.setFont(new Font("Arial", Font.PLAIN, 14));
		txtC4.setColumns(10);
		GridBagConstraints gbc_txtC4 = new GridBagConstraints();
		gbc_txtC4.insets = new Insets(0, 0, 5, 5);
		gbc_txtC4.fill = GridBagConstraints.BOTH;
		gbc_txtC4.gridx = 1;
		gbc_txtC4.gridy = 5;
		pnlVariable.add(txtC4, gbc_txtC4);
		
		JLabel lblNewLabel_3_1_4 = new JLabel("Grouping Info?");
		lblNewLabel_3_1_4.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_3_1_4 = new GridBagConstraints();
		gbc_lblNewLabel_3_1_4.insets = new Insets(8, 5, 0, 5);
		gbc_lblNewLabel_3_1_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3_1_4.gridx = 0;
		gbc_lblNewLabel_3_1_4.gridy = 6;
		pnlVariable.add(lblNewLabel_3_1_4, gbc_lblNewLabel_3_1_4);
		
		textField = new JTextField();
		textField.setFont(new Font("Arial", Font.PLAIN, 14));
		textField.setColumns(10);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(8, 0, 0, 5);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 6;
		pnlVariable.add(textField, gbc_textField);
		
		JPanel pnlPrimary = new JPanel();
		pnlPrimary.setBorder(new MatteBorder(0, 1, 0, 1, (Color) new Color(169, 169, 169)));
		pnlMain.add(pnlPrimary);
		GridBagLayout gbl_pnlPrimary = new GridBagLayout();
		gbl_pnlPrimary.columnWidths = new int[]{0, 0, 0};
		gbl_pnlPrimary.rowHeights = new int[] {27, 0, 0, 0, 0, 30, 0, 0, 0, 30, 0};
		gbl_pnlPrimary.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_pnlPrimary.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
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
		gbc_lblNewLabel_5_3_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5_3_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5_3_1.gridx = 0;
		gbc_lblNewLabel_5_3_1.gridy = 2;
		pnlPrimary.add(lblNewLabel_5_3_1, gbc_lblNewLabel_5_3_1);
		
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
		
		JPanel pnlPrimaryBlurSize_2 = new JPanel();
		GridBagConstraints gbc_pnlPrimaryBlurSize_2 = new GridBagConstraints();
		gbc_pnlPrimaryBlurSize_2.anchor = GridBagConstraints.EAST;
		gbc_pnlPrimaryBlurSize_2.gridwidth = 2;
		gbc_pnlPrimaryBlurSize_2.insets = new Insets(0, 0, 5, 0);
		gbc_pnlPrimaryBlurSize_2.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimaryBlurSize_2.gridx = 0;
		gbc_pnlPrimaryBlurSize_2.gridy = 3;
		pnlPrimary.add(pnlPrimaryBlurSize_2, gbc_pnlPrimaryBlurSize_2);
		
		JLabel lblNewLabel_6_6_1 = new JLabel("S1:");
		lblNewLabel_6_6_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_6_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_6_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2.add(lblNewLabel_6_6_1);
		
		JLabel lblNewLabel_6_6 = new JLabel("X");
		lblNewLabel_6_6.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_6.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_6.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2.add(lblNewLabel_6_6);
		
		textField_1 = new JTextField();
		textField_1.setText("1");
		textField_1.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_1.setColumns(4);
		pnlPrimaryBlurSize_2.add(textField_1);
		
		JLabel lblNewLabel_6_1_4 = new JLabel("Y");
		lblNewLabel_6_1_4.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_4.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2.add(lblNewLabel_6_1_4);
		
		textField_8 = new JTextField();
		textField_8.setText("1");
		textField_8.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_8.setColumns(4);
		pnlPrimaryBlurSize_2.add(textField_8);
		
		JLabel lblNewLabel_6_2_4 = new JLabel("Z");
		lblNewLabel_6_2_4.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_4.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_4.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2.add(lblNewLabel_6_2_4);
		
		textField_9 = new JTextField();
		textField_9.setText("1");
		textField_9.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_9.setColumns(4);
		pnlPrimaryBlurSize_2.add(textField_9);
		
		JPanel pnlPrimaryBlurSize_2_1 = new JPanel();
		GridBagConstraints gbc_pnlPrimaryBlurSize_2_1 = new GridBagConstraints();
		gbc_pnlPrimaryBlurSize_2_1.anchor = GridBagConstraints.EAST;
		gbc_pnlPrimaryBlurSize_2_1.gridwidth = 2;
		gbc_pnlPrimaryBlurSize_2_1.insets = new Insets(0, 0, 5, 0);
		gbc_pnlPrimaryBlurSize_2_1.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimaryBlurSize_2_1.gridx = 0;
		gbc_pnlPrimaryBlurSize_2_1.gridy = 4;
		pnlPrimary.add(pnlPrimaryBlurSize_2_1, gbc_pnlPrimaryBlurSize_2_1);
		
		JLabel lblNewLabel_6_6_1_1 = new JLabel("S2:");
		lblNewLabel_6_6_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_6_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_6_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_1.add(lblNewLabel_6_6_1_1);
		
		JLabel lblNewLabel_6_6_2 = new JLabel("X");
		lblNewLabel_6_6_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_6_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_6_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_1.add(lblNewLabel_6_6_2);
		
		textField_10 = new JTextField();
		textField_10.setText("1");
		textField_10.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_10.setColumns(4);
		pnlPrimaryBlurSize_2_1.add(textField_10);
		
		JLabel lblNewLabel_6_1_4_1 = new JLabel("Y");
		lblNewLabel_6_1_4_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_4_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_4_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_1.add(lblNewLabel_6_1_4_1);
		
		textField_11 = new JTextField();
		textField_11.setText("1");
		textField_11.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_11.setColumns(4);
		pnlPrimaryBlurSize_2_1.add(textField_11);
		
		JLabel lblNewLabel_6_2_4_1 = new JLabel("Z");
		lblNewLabel_6_2_4_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_4_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_4_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_1.add(lblNewLabel_6_2_4_1);
		
		textField_12 = new JTextField();
		textField_12.setText("1");
		textField_12.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_12.setColumns(4);
		pnlPrimaryBlurSize_2_1.add(textField_12);
		
		JLabel lblNewLabel_5_1_1 = new JLabel("Filter:");
		lblNewLabel_5_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_5_1_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_1_1.gridx = 0;
		gbc_lblNewLabel_5_1_1.gridy = 5;
		pnlPrimary.add(lblNewLabel_5_1_1, gbc_lblNewLabel_5_1_1);
		
		JComboBox cbPrimaryFilter = new JComboBox();
		cbPrimaryFilter.setModel(new DefaultComboBoxModel(new String[] {"3D Gaussian Blur", "3D Median", "3D Mean"}));
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
		gbc_pnlPrimaryBGRadius.anchor = GridBagConstraints.EAST;
		gbc_pnlPrimaryBGRadius.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimaryBGRadius.gridx = 1;
		gbc_pnlPrimaryBGRadius.gridy = 9;
		pnlPrimary.add(pnlPrimaryBGRadius, gbc_pnlPrimaryBGRadius);
		
		JLabel lblNewLabel_6_3_1_1_3 = new JLabel("Radius:");
		lblNewLabel_6_3_1_1_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_3_1_1_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_3_1_1_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBGRadius.add(lblNewLabel_6_3_1_1_3);
		
		txtPrimaryBGRadius = new JTextField();
		txtPrimaryBGRadius.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPrimaryBGRadius.setColumns(6);
		pnlPrimaryBGRadius.add(txtPrimaryBGRadius);
		
		JPanel pnlSecondary = new JPanel();
		pnlMain.add(pnlSecondary);
		GridBagLayout gbl_pnlSecondary = new GridBagLayout();
		gbl_pnlSecondary.columnWidths = new int[]{0, 0, 0};
		gbl_pnlSecondary.rowHeights = new int[] {27, 0, 0, 0, 0, 30, 0, 0, 0, 30, 0};
		gbl_pnlSecondary.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_pnlSecondary.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
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
		gbc_lblNewLabel_5_3_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5_3_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5_3_1_1.gridx = 0;
		gbc_lblNewLabel_5_3_1_1.gridy = 2;
		pnlSecondary.add(lblNewLabel_5_3_1_1, gbc_lblNewLabel_5_3_1_1);
		
		JComboBox cbSecondaryBackground = new JComboBox();
		cbSecondaryBackground.setModel(new DefaultComboBoxModel(new String[] {"None", "Default", "3D DoG", "3D Top Hat"}));
		cbSecondaryBackground.setSelectedIndex(0);
		cbSecondaryBackground.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbSecondaryBackground = new GridBagConstraints();
		gbc_cbSecondaryBackground.insets = new Insets(0, 0, 5, 0);
		gbc_cbSecondaryBackground.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbSecondaryBackground.gridx = 1;
		gbc_cbSecondaryBackground.gridy = 2;
		pnlSecondary.add(cbSecondaryBackground, gbc_cbSecondaryBackground);
		
		JPanel pnlPrimaryBlurSize_2_2 = new JPanel();
		GridBagConstraints gbc_pnlPrimaryBlurSize_2_2 = new GridBagConstraints();
		gbc_pnlPrimaryBlurSize_2_2.anchor = GridBagConstraints.EAST;
		gbc_pnlPrimaryBlurSize_2_2.gridwidth = 2;
		gbc_pnlPrimaryBlurSize_2_2.insets = new Insets(0, 0, 5, 0);
		gbc_pnlPrimaryBlurSize_2_2.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimaryBlurSize_2_2.gridx = 0;
		gbc_pnlPrimaryBlurSize_2_2.gridy = 3;
		pnlSecondary.add(pnlPrimaryBlurSize_2_2, gbc_pnlPrimaryBlurSize_2_2);
		
		JLabel lblNewLabel_6_6_1_2 = new JLabel("S1:");
		lblNewLabel_6_6_1_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_6_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_6_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_2.add(lblNewLabel_6_6_1_2);
		
		JLabel lblNewLabel_6_6_3 = new JLabel("X");
		lblNewLabel_6_6_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_6_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_6_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_2.add(lblNewLabel_6_6_3);
		
		textField_14 = new JTextField();
		textField_14.setText("1");
		textField_14.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_14.setColumns(4);
		pnlPrimaryBlurSize_2_2.add(textField_14);
		
		JLabel lblNewLabel_6_1_4_2 = new JLabel("Y");
		lblNewLabel_6_1_4_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_4_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_4_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_2.add(lblNewLabel_6_1_4_2);
		
		textField_15 = new JTextField();
		textField_15.setText("1");
		textField_15.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_15.setColumns(4);
		pnlPrimaryBlurSize_2_2.add(textField_15);
		
		JLabel lblNewLabel_6_2_4_2 = new JLabel("Z");
		lblNewLabel_6_2_4_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_4_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_4_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_2.add(lblNewLabel_6_2_4_2);
		
		textField_16 = new JTextField();
		textField_16.setText("1");
		textField_16.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_16.setColumns(4);
		pnlPrimaryBlurSize_2_2.add(textField_16);
		
		JPanel pnlPrimaryBlurSize_2_1_1 = new JPanel();
		GridBagConstraints gbc_pnlPrimaryBlurSize_2_1_1 = new GridBagConstraints();
		gbc_pnlPrimaryBlurSize_2_1_1.anchor = GridBagConstraints.EAST;
		gbc_pnlPrimaryBlurSize_2_1_1.gridwidth = 2;
		gbc_pnlPrimaryBlurSize_2_1_1.insets = new Insets(0, 0, 5, 0);
		gbc_pnlPrimaryBlurSize_2_1_1.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimaryBlurSize_2_1_1.gridx = 0;
		gbc_pnlPrimaryBlurSize_2_1_1.gridy = 4;
		pnlSecondary.add(pnlPrimaryBlurSize_2_1_1, gbc_pnlPrimaryBlurSize_2_1_1);
		
		JLabel lblNewLabel_6_6_1_1_1 = new JLabel("S2:");
		lblNewLabel_6_6_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_6_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_6_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_1_1.add(lblNewLabel_6_6_1_1_1);
		
		JLabel lblNewLabel_6_6_2_1 = new JLabel("X");
		lblNewLabel_6_6_2_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_6_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_6_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_1_1.add(lblNewLabel_6_6_2_1);
		
		textField_17 = new JTextField();
		textField_17.setText("1");
		textField_17.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_17.setColumns(4);
		pnlPrimaryBlurSize_2_1_1.add(textField_17);
		
		JLabel lblNewLabel_6_1_4_1_1 = new JLabel("Y");
		lblNewLabel_6_1_4_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_4_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_4_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_1_1.add(lblNewLabel_6_1_4_1_1);
		
		textField_18 = new JTextField();
		textField_18.setText("1");
		textField_18.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_18.setColumns(4);
		pnlPrimaryBlurSize_2_1_1.add(textField_18);
		
		JLabel lblNewLabel_6_2_4_1_1 = new JLabel("Z");
		lblNewLabel_6_2_4_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_4_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_4_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_1_1.add(lblNewLabel_6_2_4_1_1);
		
		textField_19 = new JTextField();
		textField_19.setText("1");
		textField_19.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_19.setColumns(4);
		pnlPrimaryBlurSize_2_1_1.add(textField_19);
		
		JLabel lblNewLabel_5_1_1_1 = new JLabel("Filter:");
		lblNewLabel_5_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_5_1_1_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_1_1_1.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_1_1_1.gridx = 0;
		gbc_lblNewLabel_5_1_1_1.gridy = 5;
		pnlSecondary.add(lblNewLabel_5_1_1_1, gbc_lblNewLabel_5_1_1_1);
		
		JComboBox cbSecondaryFilter = new JComboBox();
		cbSecondaryFilter.setModel(new DefaultComboBoxModel(new String[] {"3D Gaussian Blur", "3D Median"}));
		cbSecondaryFilter.setSelectedIndex(0);
		cbSecondaryFilter.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbSecondaryFilter = new GridBagConstraints();
		gbc_cbSecondaryFilter.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbSecondaryFilter.insets = new Insets(0, 0, 5, 0);
		gbc_cbSecondaryFilter.gridx = 1;
		gbc_cbSecondaryFilter.gridy = 5;
		pnlSecondary.add(cbSecondaryFilter, gbc_cbSecondaryFilter);
		
		JPanel pnlPrimaryBlurSize_1 = new JPanel();
		GridBagConstraints gbc_pnlPrimaryBlurSize_1 = new GridBagConstraints();
		gbc_pnlPrimaryBlurSize_1.anchor = GridBagConstraints.EAST;
		gbc_pnlPrimaryBlurSize_1.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimaryBlurSize_1.gridwidth = 2;
		gbc_pnlPrimaryBlurSize_1.insets = new Insets(0, 0, 5, 0);
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
		
		JPanel pnlSecondaryThreshold = new JPanel();
		GridBagConstraints gbc_pnlSecondaryTreshold = new GridBagConstraints();
		gbc_pnlSecondaryTreshold.anchor = GridBagConstraints.EAST;
		gbc_pnlSecondaryTreshold.gridwidth = 2;
		gbc_pnlSecondaryTreshold.insets = new Insets(0, 0, 5, 0);
		gbc_pnlSecondaryTreshold.fill = GridBagConstraints.VERTICAL;
		gbc_pnlSecondaryTreshold.gridx = 0;
		gbc_pnlSecondaryTreshold.gridy = 8;
		pnlSecondary.add(pnlSecondaryThreshold, gbc_pnlSecondaryTreshold);
		pnlSecondaryThreshold.setVisible(false);

		JPanel pnlSecondarySpotSize = new JPanel();
		GridBagConstraints gbc_pnlSecondarySpotSize = new GridBagConstraints();
		gbc_pnlSecondarySpotSize.anchor = GridBagConstraints.EAST;
		gbc_pnlSecondarySpotSize.fill = GridBagConstraints.VERTICAL;
		gbc_pnlSecondarySpotSize.gridwidth = 2;
		gbc_pnlSecondarySpotSize.insets = new Insets(0, 0, 5, 0);
		gbc_pnlSecondarySpotSize.gridx = 0;
		gbc_pnlSecondarySpotSize.gridy = 8;
		pnlSecondary.add(pnlSecondarySpotSize, gbc_pnlSecondarySpotSize);
		
		
		cbSecondaryMethod.setModel(new DefaultComboBoxModel(new String[] {"Marker Controlled", "Greater Constant", "Otsu Threshold"}));
		cbSecondaryMethod.setSelectedIndex(0);
		cbSecondaryMethod.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbSecondaryMethod = new GridBagConstraints();
		gbc_cbSecondaryMethod.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbSecondaryMethod.insets = new Insets(0, 0, 5, 0);
		gbc_cbSecondaryMethod.gridx = 1;
		gbc_cbSecondaryMethod.gridy = 7;
		pnlSecondary.add(cbSecondaryMethod, gbc_cbSecondaryMethod);
		
		
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
		gbc_pnlSecondaryBGRadius.anchor = GridBagConstraints.EAST;
		gbc_pnlSecondaryBGRadius.fill = GridBagConstraints.VERTICAL;
		gbc_pnlSecondaryBGRadius.gridx = 1;
		gbc_pnlSecondaryBGRadius.gridy = 9;
		pnlSecondary.add(pnlSecondaryBGRadius, gbc_pnlSecondaryBGRadius);
		
		JLabel lblNewLabel_6_3_1_1_3_1 = new JLabel("Radius:");
		lblNewLabel_6_3_1_1_3_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_3_1_1_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_3_1_1_3_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlSecondaryBGRadius.add(lblNewLabel_6_3_1_1_3_1);
		
		txtSecondaryBGRadius = new JTextField();
		txtSecondaryBGRadius.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSecondaryBGRadius.setColumns(6);
		pnlSecondaryBGRadius.add(txtSecondaryBGRadius);
		
		JPanel pnlTertiary = new JPanel();
		pnlTertiary.setBorder(new MatteBorder(0, 1, 0, 0, (Color) new Color(169, 169, 169)));
		pnlMain.add(pnlTertiary);
		GridBagLayout gbl_pnlTertiary = new GridBagLayout();
		gbl_pnlTertiary.columnWidths = new int[] {0, 0};
		gbl_pnlTertiary.rowHeights = new int[]{27, 0, 0, 0, 0, 30, 0, 0, 0, 30, 0};
		gbl_pnlTertiary.columnWeights = new double[]{1.0, 1.0};
		gbl_pnlTertiary.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		pnlTertiary.setLayout(gbl_pnlTertiary);
		
		JCheckBox cbTertiary = new JCheckBox("Tertiary Objects");
		cbTertiary.setFont(new Font("Arial", Font.BOLD, 14));
		cbTertiary.setEnabled(true);
		cbTertiary.setSelected(false);
		GridBagConstraints gbc_cbTertiary = new GridBagConstraints();
		gbc_cbTertiary.gridwidth = 2;
		gbc_cbTertiary.insets = new Insets(0, 0, 5, 0);
		gbc_cbTertiary.gridx = 0;
		gbc_cbTertiary.gridy = 0;
		pnlTertiary.add(cbTertiary, gbc_cbTertiary);
		
		JLabel lblNewLabel_5_3 = new JLabel("Channel:");
		lblNewLabel_5_3.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_3 = new GridBagConstraints();
		gbc_lblNewLabel_5_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_3.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_3.gridx = 0;
		gbc_lblNewLabel_5_3.gridy = 1;
		pnlTertiary.add(lblNewLabel_5_3, gbc_lblNewLabel_5_3);
		
		JComboBox cbTertiaryChannel = new JComboBox();
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
		lblNewLabel_5_3_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_5_3_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_3_1_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_5_3_1_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_5_3_1_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5_3_1_1_1.gridx = 0;
		gbc_lblNewLabel_5_3_1_1_1.gridy = 2;
		pnlTertiary.add(lblNewLabel_5_3_1_1_1, gbc_lblNewLabel_5_3_1_1_1);
		
		JComboBox cbTertiaryBackground = new JComboBox();
		cbTertiaryBackground.setModel(new DefaultComboBoxModel(new String[] {"None", "Default", "3D DoG", "3D Top Hat"}));
		cbTertiaryBackground.setSelectedIndex(0);
		cbTertiaryBackground.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbTertiaryBackground = new GridBagConstraints();
		gbc_cbTertiaryBackground.insets = new Insets(0, 0, 5, 0);
		gbc_cbTertiaryBackground.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbTertiaryBackground.gridx = 1;
		gbc_cbTertiaryBackground.gridy = 2;
		pnlTertiary.add(cbTertiaryBackground, gbc_cbTertiaryBackground);
		
		JPanel pnlPrimaryBlurSize_2_2_1 = new JPanel();
		GridBagConstraints gbc_pnlPrimaryBlurSize_2_2_1 = new GridBagConstraints();
		gbc_pnlPrimaryBlurSize_2_2_1.anchor = GridBagConstraints.EAST;
		gbc_pnlPrimaryBlurSize_2_2_1.gridwidth = 2;
		gbc_pnlPrimaryBlurSize_2_2_1.insets = new Insets(0, 0, 5, 0);
		gbc_pnlPrimaryBlurSize_2_2_1.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimaryBlurSize_2_2_1.gridx = 0;
		gbc_pnlPrimaryBlurSize_2_2_1.gridy = 3;
		pnlTertiary.add(pnlPrimaryBlurSize_2_2_1, gbc_pnlPrimaryBlurSize_2_2_1);
		
		JLabel lblNewLabel_6_6_1_2_1 = new JLabel("S1:");
		lblNewLabel_6_6_1_2_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_6_1_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_6_1_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_2_1.add(lblNewLabel_6_6_1_2_1);
		
		JLabel lblNewLabel_6_6_3_1 = new JLabel("X");
		lblNewLabel_6_6_3_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_6_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_6_3_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_2_1.add(lblNewLabel_6_6_3_1);
		
		textField_21 = new JTextField();
		textField_21.setText("1");
		textField_21.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_21.setColumns(4);
		pnlPrimaryBlurSize_2_2_1.add(textField_21);
		
		JLabel lblNewLabel_6_1_4_2_1 = new JLabel("Y");
		lblNewLabel_6_1_4_2_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_4_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_4_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_2_1.add(lblNewLabel_6_1_4_2_1);
		
		textField_22 = new JTextField();
		textField_22.setText("1");
		textField_22.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_22.setColumns(4);
		pnlPrimaryBlurSize_2_2_1.add(textField_22);
		
		JLabel lblNewLabel_6_2_4_2_1 = new JLabel("Z");
		lblNewLabel_6_2_4_2_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_4_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_4_2_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_2_1.add(lblNewLabel_6_2_4_2_1);
		
		textField_23 = new JTextField();
		textField_23.setText("1");
		textField_23.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_23.setColumns(4);
		pnlPrimaryBlurSize_2_2_1.add(textField_23);
		
		JPanel pnlPrimaryBlurSize_2_1_1_1 = new JPanel();
		GridBagConstraints gbc_pnlPrimaryBlurSize_2_1_1_1 = new GridBagConstraints();
		gbc_pnlPrimaryBlurSize_2_1_1_1.anchor = GridBagConstraints.EAST;
		gbc_pnlPrimaryBlurSize_2_1_1_1.gridwidth = 2;
		gbc_pnlPrimaryBlurSize_2_1_1_1.insets = new Insets(0, 0, 5, 0);
		gbc_pnlPrimaryBlurSize_2_1_1_1.fill = GridBagConstraints.VERTICAL;
		gbc_pnlPrimaryBlurSize_2_1_1_1.gridx = 0;
		gbc_pnlPrimaryBlurSize_2_1_1_1.gridy = 4;
		pnlTertiary.add(pnlPrimaryBlurSize_2_1_1_1, gbc_pnlPrimaryBlurSize_2_1_1_1);
		
		JLabel lblNewLabel_6_6_1_1_1_1 = new JLabel("S2:");
		lblNewLabel_6_6_1_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_6_1_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_6_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_1_1_1.add(lblNewLabel_6_6_1_1_1_1);
		
		JLabel lblNewLabel_6_6_2_1_1 = new JLabel("X");
		lblNewLabel_6_6_2_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_6_2_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_6_2_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_1_1_1.add(lblNewLabel_6_6_2_1_1);
		
		textField_24 = new JTextField();
		textField_24.setText("1");
		textField_24.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_24.setColumns(4);
		pnlPrimaryBlurSize_2_1_1_1.add(textField_24);
		
		JLabel lblNewLabel_6_1_4_1_1_1 = new JLabel("Y");
		lblNewLabel_6_1_4_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_4_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_4_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_1_1_1.add(lblNewLabel_6_1_4_1_1_1);
		
		textField_25 = new JTextField();
		textField_25.setText("1");
		textField_25.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_25.setColumns(4);
		pnlPrimaryBlurSize_2_1_1_1.add(textField_25);
		
		JLabel lblNewLabel_6_2_4_1_1_1 = new JLabel("Z");
		lblNewLabel_6_2_4_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_4_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_4_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlPrimaryBlurSize_2_1_1_1.add(lblNewLabel_6_2_4_1_1_1);
		
		textField_26 = new JTextField();
		textField_26.setText("1");
		textField_26.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_26.setColumns(4);
		pnlPrimaryBlurSize_2_1_1_1.add(textField_26);
		
		JLabel lblNewLabel_5_1_1_2 = new JLabel("Filter:");
		lblNewLabel_5_1_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_1_1_2 = new GridBagConstraints();
		gbc_lblNewLabel_5_1_1_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_1_1_2.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_1_1_2.gridx = 0;
		gbc_lblNewLabel_5_1_1_2.gridy = 5;
		pnlTertiary.add(lblNewLabel_5_1_1_2, gbc_lblNewLabel_5_1_1_2);
		
		JComboBox cbTertiaryFilter = new JComboBox();
		cbTertiaryFilter.setModel(new DefaultComboBoxModel(new String[] {"3D Gaussian Blur", "3D Median"}));
		cbTertiaryFilter.setSelectedIndex(0);
		cbTertiaryFilter.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbTertiaryFilter = new GridBagConstraints();
		gbc_cbTertiaryFilter.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbTertiaryFilter.insets = new Insets(0, 0, 5, 0);
		gbc_cbTertiaryFilter.gridx = 1;
		gbc_cbTertiaryFilter.gridy = 5;
		pnlTertiary.add(cbTertiaryFilter, gbc_cbTertiaryFilter);
		
		JPanel pnlTertiaryBlurSize = new JPanel();
		GridBagConstraints gbc_pnlTertiaryBlurSize = new GridBagConstraints();
		gbc_pnlTertiaryBlurSize.anchor = GridBagConstraints.EAST;
		gbc_pnlTertiaryBlurSize.fill = GridBagConstraints.VERTICAL;
		gbc_pnlTertiaryBlurSize.gridwidth = 2;
		gbc_pnlTertiaryBlurSize.insets = new Insets(0, 0, 5, 0);
		gbc_pnlTertiaryBlurSize.gridx = 0;
		gbc_pnlTertiaryBlurSize.gridy = 6;
		pnlTertiary.add(pnlTertiaryBlurSize, gbc_pnlTertiaryBlurSize);
		
		JLabel lblNewLabel_6_5 = new JLabel("X");
		lblNewLabel_6_5.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_5.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_5.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryBlurSize.add(lblNewLabel_6_5);
		
		textField_2 = new JTextField();
		textField_2.setText("1");
		textField_2.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_2.setColumns(4);
		pnlTertiaryBlurSize.add(textField_2);
		
		JLabel lblNewLabel_6_1_3 = new JLabel("Y");
		lblNewLabel_6_1_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryBlurSize.add(lblNewLabel_6_1_3);
		
		textField_3 = new JTextField();
		textField_3.setText("1");
		textField_3.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_3.setColumns(4);
		pnlTertiaryBlurSize.add(textField_3);
		
		JLabel lblNewLabel_6_2_3 = new JLabel("Z");
		lblNewLabel_6_2_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_3.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryBlurSize.add(lblNewLabel_6_2_3);
		
		textField_4 = new JTextField();
		textField_4.setText("1");
		textField_4.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_4.setColumns(4);
		pnlTertiaryBlurSize.add(textField_4);
		
		JLabel lblNewLabel_5_1_3 = new JLabel("Method:");
		lblNewLabel_5_1_3.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_5_1_3 = new GridBagConstraints();
		gbc_lblNewLabel_5_1_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_5_1_3.insets = new Insets(0, 5, 5, 5);
		gbc_lblNewLabel_5_1_3.gridx = 0;
		gbc_lblNewLabel_5_1_3.gridy = 7;
		pnlTertiary.add(lblNewLabel_5_1_3, gbc_lblNewLabel_5_1_3);
		
		JComboBox cbTertiaryMethod = new JComboBox();
		
		
		
		
		cbTertiaryMethod.setModel(new DefaultComboBoxModel(new String[] {"Marker Controlled", "Greater Constant", "Otsu Threshold"}));
		cbTertiaryMethod.setSelectedIndex(0);
		cbTertiaryMethod.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_cbTertiaryMethod = new GridBagConstraints();
		gbc_cbTertiaryMethod.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbTertiaryMethod.insets = new Insets(0, 0, 5, 0);
		gbc_cbTertiaryMethod.gridx = 1;
		gbc_cbTertiaryMethod.gridy = 7;
		pnlTertiary.add(cbTertiaryMethod, gbc_cbTertiaryMethod);
		
		JPanel pnlTertiarySpotSize = new JPanel();
		GridBagConstraints gbc_pnlTertiarySpotSize = new GridBagConstraints();
		gbc_pnlTertiarySpotSize.anchor = GridBagConstraints.EAST;
		gbc_pnlTertiarySpotSize.fill = GridBagConstraints.VERTICAL;
		gbc_pnlTertiarySpotSize.gridwidth = 2;
		gbc_pnlTertiarySpotSize.insets = new Insets(0, 0, 5, 0);
		gbc_pnlTertiarySpotSize.gridx = 0;
		gbc_pnlTertiarySpotSize.gridy = 8;
		pnlTertiary.add(pnlTertiarySpotSize, gbc_pnlTertiarySpotSize);
		
		JLabel lblNewLabel_6_3_2 = new JLabel("X");
		lblNewLabel_6_3_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_3_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_3_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiarySpotSize.add(lblNewLabel_6_3_2);
		
		textField_5 = new JTextField();
		textField_5.setText("1");
		textField_5.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_5.setColumns(4);
		pnlTertiarySpotSize.add(textField_5);
		
		JLabel lblNewLabel_6_1_1_2 = new JLabel("Y");
		lblNewLabel_6_1_1_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_1_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_1_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiarySpotSize.add(lblNewLabel_6_1_1_2);
		
		textField_6 = new JTextField();
		textField_6.setText("1");
		textField_6.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_6.setColumns(4);
		pnlTertiarySpotSize.add(textField_6);
		
		JLabel lblNewLabel_6_2_1_2 = new JLabel("Z");
		lblNewLabel_6_2_1_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_2_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_2_1_2.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiarySpotSize.add(lblNewLabel_6_2_1_2);
		
		textField_7 = new JTextField();
		textField_7.setText("1");
		textField_7.setFont(new Font("Arial", Font.PLAIN, 14));
		textField_7.setColumns(4);
		pnlTertiarySpotSize.add(textField_7);
		
		JPanel pnlTertiaryThreshold = new JPanel();
		GridBagConstraints gbc_pnlTertiaryThreshold = new GridBagConstraints();
		gbc_pnlTertiaryThreshold.insets = new Insets(0, 0, 5, 0);
		gbc_pnlTertiaryThreshold.gridwidth = 2;
		gbc_pnlTertiaryThreshold.fill = GridBagConstraints.BOTH;
		gbc_pnlTertiaryThreshold.gridx = 0;
		gbc_pnlTertiaryThreshold.gridy = 8;
		pnlTertiary.add(pnlTertiaryThreshold, gbc_pnlTertiaryThreshold);
		pnlTertiaryThreshold.setVisible(false);
		
		JLabel lblNewLabel_6_3_1_1_1 = new JLabel("Threshold:");
		lblNewLabel_6_3_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_3_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_3_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryThreshold.add(lblNewLabel_6_3_1_1_1);
		
		txtTertiaryThreshold = new JTextField();
		txtTertiaryThreshold.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertiaryThreshold.setColumns(6);
		pnlTertiaryThreshold.add(txtTertiaryThreshold);
		
		JPanel pnlTertiaryBGRadius = new JPanel();
		GridBagConstraints gbc_pnlTertiaryBGRadius = new GridBagConstraints();
		gbc_pnlTertiaryBGRadius.anchor = GridBagConstraints.EAST;
		gbc_pnlTertiaryBGRadius.fill = GridBagConstraints.VERTICAL;
		gbc_pnlTertiaryBGRadius.gridx = 1;
		gbc_pnlTertiaryBGRadius.gridy = 9;
		pnlTertiary.add(pnlTertiaryBGRadius, gbc_pnlTertiaryBGRadius);
		
		JLabel lblNewLabel_6_3_1_1_3_1_1 = new JLabel("Radius:");
		lblNewLabel_6_3_1_1_3_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_6_3_1_1_3_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_6_3_1_1_3_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		pnlTertiaryBGRadius.add(lblNewLabel_6_3_1_1_3_1_1);
		
		txtTertiaryBGRadius = new JTextField();
		txtTertiaryBGRadius.setFont(new Font("Arial", Font.PLAIN, 14));
		txtTertiaryBGRadius.setColumns(6);
		pnlTertiaryBGRadius.add(txtTertiaryBGRadius);
		
		
		
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
		
		JButton btnLoadParameters = new JButton("Load Parameters");
		btnLoadParameters.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnLoadParameters = new GridBagConstraints();
		gbc_btnLoadParameters.fill = GridBagConstraints.VERTICAL;
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
		btnRunAnalysis.setFont(new Font("Arial", Font.BOLD, 14));
		GridBagConstraints gbc_btnRunAnalysis = new GridBagConstraints();
		gbc_btnRunAnalysis.fill = GridBagConstraints.BOTH;
		gbc_btnRunAnalysis.gridwidth = 2;
		gbc_btnRunAnalysis.insets = new Insets(0, 0, 0, 5);
		gbc_btnRunAnalysis.gridx = 0;
		gbc_btnRunAnalysis.gridy = 2;
		pnlFooter.add(btnRunAnalysis, gbc_btnRunAnalysis);
		
		JProgressBar progressBar = new JProgressBar();
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.BOTH;
		gbc_progressBar.insets = new Insets(0, 0, 0, 5);
		gbc_progressBar.gridx = 3;
		gbc_progressBar.gridy = 2;
		pnlFooter.add(progressBar, gbc_progressBar);
		
	
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainScreen MainGui = new MainScreen();
				MainGui.setVisible(true);
				Window win = SwingUtilities.getWindowAncestor(btnBackToMenu);
				win.dispose();
			}
		});
		
		
		cbTertiaryMethod.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbTertiaryMethod.getSelectedItem().toString().equals("Marker Controlled")) {
					pnlTertiarySpotSize.setVisible(true);
					pnlTertiaryThreshold.setVisible(false);
				} else {
					pnlTertiarySpotSize.setVisible(false);
					pnlTertiaryThreshold.setVisible(true);
					
				}
				if(cbTertiaryMethod.getSelectedItem().toString().equals("Otsu Threshold")) {
					txtTertiaryThreshold.setEnabled(false);
				} else {
					txtTertiaryThreshold.setEnabled(true);
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
