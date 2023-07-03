package clcm.focust;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import ij.IJ;
import javax.swing.border.EtchedBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ButtonGroup;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JCheckBox;
import net.miginfocom.swing.MigLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.DropMode;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.SpringLayout;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


@SuppressWarnings("serial")
public class SpheroidView extends JFrame {

	 private JPanel paneSpheroid;
	 private JTextField txtInputDir;
	 private JTextField txtOutputDir;
	 private JTextField txtSpheroidC2Name;
	 private JTextField txtSpheroidC3Name;
	 private JTextField txtSpheroidC4Name;
	 private JTextField txtSpheroidGroupName;
	 private JTextField txtSecGBx;
	 private JTextField txtSecGBy;
	 private JTextField txtSecGBz;
	 private JTextField txtBgSubSecondaryVar;
	 private JTextField txtSecThreshold;
	 private JButton btnOutputDir = new JButton("Browse");
	 private JComboBox cbChannelPrimary = new JComboBox();
	 private JComboBox cbChannelSecondary = new JComboBox();
	 private final ButtonGroup btnGroupOutputDir = new ButtonGroup();
	 private final ButtonGroup btnGroupBGPrimary = new ButtonGroup();
	 private final ButtonGroup btnGroupBGSecondary = new ButtonGroup();
	 private JCheckBox cbAnalysisMode = new JCheckBox("Analysis only mode?");
	 
	 public static String inputDir;
	 public static Double sigma_x;
	 public static Double sigma_y;
	 public static Double sigma_z;
	 public static Double radius_x;
	 public static Double radius_y;
	 public static Double radius_z;
	 public static Double sigma_x2;
	 public static Double sigma_y2;
	 public static Double sigma_z2;
	 public static Double greaterConstant;
	 public static String channel2Name;
	 public static String channel3Name;
	 public static String channel4Name;
	 public static int primaryChannelChoice;
	 public static int secondaryChannelChoice;
	 public static String groupingInfo;
	 public static boolean analysisMode;
	 private JTextField textField;
	 private JTextField txtPriGBx;
	 private JTextField txtPriGBy;
	 private JTextField txtPriGBz;
	 private JTextField txtPriDMx;
	 private JTextField txtPriDMy;
	 private JTextField txtPriDMz;
	 
	/**
	 * A class that defines and builds the gui for the spheroid analysis mode.
	 * 
	 * @author SebastianAmos
	 */
	 
	 
	public SpheroidView() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(SpheroidView.class.getResource("/clcm/focust/resources/icon2.png")));
		setTitle("FOCUST: Spheroid Analysis");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 827, 485);
		paneSpheroid = new JPanel();
		paneSpheroid.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(paneSpheroid);
		paneSpheroid.setLayout(null);
		
		JButton btnBackToMenu = new JButton("Back to Menu");
		btnBackToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainScreen MainGui = new MainScreen();
				MainGui.setVisible(true);
				Window win = SwingUtilities.getWindowAncestor(btnBackToMenu);
				win.dispose();
			}
		});
		btnBackToMenu.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnBackToMenu.setBounds(10, 373, 133, 29);
		paneSpheroid.add(btnBackToMenu);
		
		JButton btnRunAnalysis = new JButton("Run Analysis");
		btnRunAnalysis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!cbAnalysisMode.isSelected()) {
					sigma_x = Double.parseDouble(txtPriGBx.getText());
					sigma_y = Double.parseDouble(txtPriGBy.getText());
					sigma_z = Double.parseDouble(txtPriGBz.getText());
					radius_x = Double.parseDouble(txtPriDMx.getText());
					radius_y = Double.parseDouble(txtPriDMy.getText());
					radius_z = Double.parseDouble(txtPriDMz.getText());
					sigma_x2 = Double.parseDouble(txtSecGBx.getText());
					sigma_y2 = Double.parseDouble(txtSecGBy.getText());
					sigma_z2 = Double.parseDouble(txtSecGBz.getText());
					greaterConstant = Double.parseDouble(txtSecThreshold.getText());
				}
				channel2Name = txtSpheroidC2Name.getText();
				channel2Name = txtSpheroidC3Name.getText();
				channel3Name = txtSpheroidC4Name.getText();
				primaryChannelChoice = cbChannelPrimary.getSelectedIndex();
				secondaryChannelChoice = cbChannelSecondary.getSelectedIndex();
				groupingInfo = txtSpheroidGroupName.getText();
				Segment.processSpheroid(cbAnalysisMode.isSelected());
			}
		});
		btnRunAnalysis.setFont(new Font("Gadugi", Font.BOLD, 14));
		btnRunAnalysis.setBounds(10, 406, 286, 29);
		paneSpheroid.add(btnRunAnalysis);
		
		JLabel lblSelectAnInput = new JLabel("Select an input directory:");
		lblSelectAnInput.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblSelectAnInput.setBounds(20, 28, 202, 29);
		paneSpheroid.add(lblSelectAnInput);
		
		JButton btnInputDir = new JButton("Browse");
		btnInputDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Find and set the input directory.
				inputDir = IJ.getDir("Select an Input Directory:");
				String inputDirSt = inputDir.toString();
				txtInputDir.setText(inputDirSt);
				
			
				
				//FOCUST.FileFinder();
				
				// update the textbox in spheroid view to selected path
				//String inputDirSt = FOCUST.inputPath.toString(); 
				//txtInputDir.setText(inputDirSt);
			}
		});
		
		btnInputDir.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnInputDir.setBounds(214, 28, 96, 29);
		paneSpheroid.add(btnInputDir);
		
		JLabel lblSeperateOutputDirectory = new JLabel("Seperate output directory?");
		lblSeperateOutputDirectory.setToolTipText("Selecting yes will save output files to the specified directory.");
		lblSeperateOutputDirectory.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblSeperateOutputDirectory.setBounds(20, 68, 202, 29);
		paneSpheroid.add(lblSeperateOutputDirectory);
		
	
		btnOutputDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnOutputDir.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnOutputDir.setEnabled(false);
		btnOutputDir.setBounds(349, 68, 96, 29);
		paneSpheroid.add(btnOutputDir);
		
		txtInputDir = new JTextField();
		txtInputDir.setColumns(10);
		txtInputDir.setBounds(322, 28, 289, 29);
		paneSpheroid.add(txtInputDir);
		
		txtOutputDir = new JTextField();
		txtOutputDir.setEnabled(false);
		txtOutputDir.setColumns(10);
		txtOutputDir.setBounds(455, 68, 289, 29);
		paneSpheroid.add(txtOutputDir);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(169, 169, 169));
		separator.setBackground(Color.WHITE);
		separator.setBounds(12, 113, 778, -3);
		paneSpheroid.add(separator);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnHelp.setBounds(694, 16, 96, 29);
		paneSpheroid.add(btnHelp);
		
		JLabel lblYouMustHave = new JLabel("You must have optimized your segmentation parameters.");
		lblYouMustHave.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblYouMustHave.setBounds(136, 0, 401, 29);
		paneSpheroid.add(lblYouMustHave);
		
		JLabel lblNewLabel = new JLabel("Process a Dataset:");
		lblNewLabel.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 0, 133, 29);
		paneSpheroid.add(lblNewLabel);
		
		JLabel lblHowManyChannels = new JLabel("Total number of channels per image?");
		lblHowManyChannels.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblHowManyChannels.setBounds(10, 178, 237, 29);
		paneSpheroid.add(lblHowManyChannels);
		
		JComboBox cbChannelTotal = new JComboBox();
		cbChannelTotal.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// enable name input for C4 if 4 channels declared
				if(cbChannelTotal.getSelectedItem().toString().equals("4")) {
					txtSpheroidC4Name.setEnabled(true);
				} else {
					txtSpheroidC4Name.setEnabled(false);
				}	
				
				// enable name input for C3 if 3 or 4 channels declared. 
				if (cbChannelTotal.getSelectedItem().toString().equals("3")) {
					txtSpheroidC3Name.setEnabled(true);
				} else if (cbChannelTotal.getSelectedItem().toString().equals("4")) {
					txtSpheroidC3Name.setEnabled(true);
				} else {
					txtSpheroidC3Name.setEnabled(false);
				}
			}
		});
		cbChannelTotal.setModel(new DefaultComboBoxModel(new String[] {"2", "3", "4"}));
		cbChannelTotal.setSelectedIndex(0);
		cbChannelTotal.setMaximumRowCount(3);
		cbChannelTotal.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelTotal.setBounds(248, 180, 48, 25);
		paneSpheroid.add(cbChannelTotal);
		
		JLabel lblNameChannel2 = new JLabel("Name Channel 2:");
		lblNameChannel2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblNameChannel2.setBounds(10, 216, 116, 29);
		paneSpheroid.add(lblNameChannel2);
		
		txtSpheroidC2Name = new JTextField();
		txtSpheroidC2Name.setColumns(10);
		txtSpheroidC2Name.setBounds(120, 216, 176, 29);
		paneSpheroid.add(txtSpheroidC2Name);
		
		JLabel lblNameChannel3 = new JLabel("Name Channel 3:");
		lblNameChannel3.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblNameChannel3.setBounds(10, 250, 116, 29);
		paneSpheroid.add(lblNameChannel3);
		
		txtSpheroidC3Name = new JTextField();
		txtSpheroidC3Name.setEnabled(false);
		txtSpheroidC3Name.setColumns(10);
		txtSpheroidC3Name.setBounds(120, 250, 176, 29);
		paneSpheroid.add(txtSpheroidC3Name);
		
		JLabel lblNameChannel4 = new JLabel("Name Channel 4:");
		lblNameChannel4.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblNameChannel4.setBounds(10, 283, 116, 29);
		paneSpheroid.add(lblNameChannel4);
		
		txtSpheroidC4Name = new JTextField();
		txtSpheroidC4Name.setEnabled(false);
		txtSpheroidC4Name.setColumns(10);
		txtSpheroidC4Name.setBounds(120, 283, 176, 29);
		paneSpheroid.add(txtSpheroidC4Name);
		
		JLabel lblGroupingInfo = new JLabel("Grouping* Info?");
		lblGroupingInfo.setToolTipText("Any conditions/factors specific to this dataset that you might wish to group data by. \r\nThis will appear as a seperate column alongside all data run within the same batch. ");
		lblGroupingInfo.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblGroupingInfo.setBounds(10, 323, 116, 29);
		paneSpheroid.add(lblGroupingInfo);
		
		txtSpheroidGroupName = new JTextField();
		txtSpheroidGroupName.setToolTipText("Any conditions/factors specific to this dataset that you might wish to group data by. \r\nThis will appear as a seperate column alongside all data run within the same batch. ");
		txtSpheroidGroupName.setColumns(10);
		txtSpheroidGroupName.setBounds(120, 323, 176, 29);
		paneSpheroid.add(txtSpheroidGroupName);
		
		JLabel lblPrimaryObjects = new JLabel("Primary Objects\r\n");
		lblPrimaryObjects.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrimaryObjects.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblPrimaryObjects.setBounds(336, 183, 187, 29);
		paneSpheroid.add(lblPrimaryObjects);
		
		JLabel lblSegmentSecondary = new JLabel("Secondary Object");
		lblSegmentSecondary.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegmentSecondary.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblSegmentSecondary.setBounds(590, 183, 187, 29);
		paneSpheroid.add(lblSegmentSecondary);
		
		
		cbChannelPrimary.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbChannelPrimary.setSelectedIndex(0);
		cbChannelPrimary.setMaximumRowCount(4);
		cbChannelPrimary.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelPrimary.setBounds(464, 210, 59, 25);
		paneSpheroid.add(cbChannelPrimary);
		
		JPanel SecondaryObjectPanel = new JPanel();
		SecondaryObjectPanel.setLayout(null);
		SecondaryObjectPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		SecondaryObjectPanel.setBackground(new Color(211, 211, 211));
		SecondaryObjectPanel.setBounds(590, 241, 187, 182);
		paneSpheroid.add(SecondaryObjectPanel);
		
		JLabel lblBgSubSecondary = new JLabel("Background subtraction?");
		lblBgSubSecondary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBgSubSecondary.setBounds(10, 0, 161, 29);
		SecondaryObjectPanel.add(lblBgSubSecondary);
		
		JRadioButton rdbtnNewRadioButton_1_1 = new JRadioButton("No");
		btnGroupBGSecondary.add(rdbtnNewRadioButton_1_1);
		rdbtnNewRadioButton_1_1.setSelected(true);
		rdbtnNewRadioButton_1_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rdbtnNewRadioButton_1_1.setBackground(new Color(211, 211, 211));
		rdbtnNewRadioButton_1_1.setBounds(10, 24, 43, 23);
		SecondaryObjectPanel.add(rdbtnNewRadioButton_1_1);
		
		JRadioButton rdbtnYes_1_1 = new JRadioButton("Yes");
		btnGroupBGSecondary.add(rdbtnYes_1_1);
		rdbtnYes_1_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rdbtnYes_1_1.setBackground(new Color(211, 211, 211));
		rdbtnYes_1_1.setBounds(50, 24, 48, 23);
		SecondaryObjectPanel.add(rdbtnYes_1_1);
		
		JLabel lblGaussianBlurSecondary = new JLabel("3D Gaussian blur:");
		lblGaussianBlurSecondary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblGaussianBlurSecondary.setBounds(10, 47, 161, 29);
		SecondaryObjectPanel.add(lblGaussianBlurSecondary);
		
		JLabel lblRadius_1 = new JLabel("Radius:\r\n");
		lblRadius_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblRadius_1.setEnabled(false);
		lblRadius_1.setBounds(98, 21, 48, 29);
		SecondaryObjectPanel.add(lblRadius_1);
		
		txtSecGBx = new JTextField();
		txtSecGBx.setText("1");
		txtSecGBx.setColumns(10);
		txtSecGBx.setBackground(new Color(211, 211, 211));
		txtSecGBx.setBounds(20, 73, 41, 20);
		SecondaryObjectPanel.add(txtSecGBx);
		
		JLabel lblX_2 = new JLabel("x");
		lblX_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_2.setBounds(10, 69, 21, 29);
		SecondaryObjectPanel.add(lblX_2);
		
		JLabel lblY_2 = new JLabel("y");
		lblY_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_2.setBounds(65, 69, 21, 29);
		SecondaryObjectPanel.add(lblY_2);
		
		txtSecGBy = new JTextField();
		txtSecGBy.setText("1");
		txtSecGBy.setColumns(10);
		txtSecGBy.setBackground(new Color(211, 211, 211));
		txtSecGBy.setBounds(75, 73, 41, 20);
		SecondaryObjectPanel.add(txtSecGBy);
		
		JLabel lblZ_2 = new JLabel("z");
		lblZ_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_2.setBounds(120, 69, 21, 29);
		SecondaryObjectPanel.add(lblZ_2);
		
		txtSecGBz = new JTextField();
		txtSecGBz.setText("1");
		txtSecGBz.setColumns(10);
		txtSecGBz.setBackground(new Color(211, 211, 211));
		txtSecGBz.setBounds(130, 73, 41, 20);
		SecondaryObjectPanel.add(txtSecGBz);
		
		txtBgSubSecondaryVar = new JTextField();
		txtBgSubSecondaryVar.setEnabled(false);
		txtBgSubSecondaryVar.setColumns(10);
		txtBgSubSecondaryVar.setBackground(new Color(211, 211, 211));
		txtBgSubSecondaryVar.setBounds(145, 25, 31, 20);
		SecondaryObjectPanel.add(txtBgSubSecondaryVar);
		
		JLabel lblThreshold_1 = new JLabel("Threshold:");
		lblThreshold_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblThreshold_1.setBounds(10, 104, 76, 29);
		SecondaryObjectPanel.add(lblThreshold_1);
		
		txtSecThreshold = new JTextField();
		txtSecThreshold.setText("1");
		txtSecThreshold.setColumns(10);
		txtSecThreshold.setBackground(new Color(211, 211, 211));
		txtSecThreshold.setBounds(76, 108, 55, 20);
		SecondaryObjectPanel.add(txtSecThreshold);
		
		JLabel lblWhichChannel_1 = new JLabel("Which channel? ");
		lblWhichChannel_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblWhichChannel_1.setBounds(590, 208, 106, 29);
		paneSpheroid.add(lblWhichChannel_1);
		
		
		cbChannelSecondary.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbChannelSecondary.setSelectedIndex(3);
		cbChannelSecondary.setMaximumRowCount(4);
		cbChannelSecondary.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelSecondary.setBounds(716, 210, 61, 25);
		paneSpheroid.add(cbChannelSecondary);
		
		JLabel lblSegmentationParameters = new JLabel("Segmentation Parameters");
		lblSegmentationParameters.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegmentationParameters.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblSegmentationParameters.setBounds(447, 148, 216, 29);
		paneSpheroid.add(lblSegmentationParameters);
		
		JButton btnLoadConfigSingleCell = new JButton("Load Parameters");
		btnLoadConfigSingleCell.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnLoadConfigSingleCell.setBounds(148, 373, 148, 29);
		paneSpheroid.add(btnLoadConfigSingleCell);
		cbAnalysisMode.setToolTipText("Runs analysis where the user provides labelled and original images.");
		

		cbAnalysisMode.setSelected(true);
		cbAnalysisMode.setFont(new Font("Gadugi", Font.PLAIN, 14));
		cbAnalysisMode.setBounds(20, 104, 157, 23);
		paneSpheroid.add(cbAnalysisMode);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(new Color(169, 169, 169));
		separator_1.setBackground(Color.WHITE);
		separator_1.setBounds(10, 135, 780, 2);
		paneSpheroid.add(separator_1);
		
		JRadioButton rbOutputDirYes = new JRadioButton("Yes");
		rbOutputDirYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbOutputDirYes.isSelected()) {
					btnOutputDir.setEnabled(true);
					txtOutputDir.setEnabled(true);
				} else {
					btnOutputDir.setEnabled(false);
					txtOutputDir.setEnabled(false);
				}
			}
		});
		
		
		btnGroupOutputDir.add(rbOutputDirYes);
		rbOutputDirYes.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbOutputDirYes.setBounds(278, 71, 79, 23);
		paneSpheroid.add(rbOutputDirYes);
		
		JRadioButton rbOutputDirNo = new JRadioButton("No");
		btnGroupOutputDir.add(rbOutputDirNo);
		rbOutputDirNo.setSelected(true);
		rbOutputDirNo.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbOutputDirNo.setBounds(214, 71, 67, 23);
		paneSpheroid.add(rbOutputDirNo);
		
		JLabel lblWhichChannel_1_1 = new JLabel("Which channel? ");
		lblWhichChannel_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblWhichChannel_1_1.setBounds(336, 211, 106, 29);
		paneSpheroid.add(lblWhichChannel_1_1);
		
		JPanel PrimaryObjectPanel = new JPanel();
		PrimaryObjectPanel.setBounds(336, 241, 207, 182);
		paneSpheroid.add(PrimaryObjectPanel);
		PrimaryObjectPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		PrimaryObjectPanel.setBackground(new Color(211, 211, 211));
		PrimaryObjectPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblBgSubSecondary_1 = new JLabel("Background subtraction?    ");
		lblBgSubSecondary_1.setAlignmentX(0.5f);
		lblBgSubSecondary_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		PrimaryObjectPanel.add(lblBgSubSecondary_1);
		
		JRadioButton rdbtnNewRadioButton_1_1_1 = new JRadioButton("No");
		rdbtnNewRadioButton_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtnNewRadioButton_1_1_1.setSelected(true);
		rdbtnNewRadioButton_1_1_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rdbtnNewRadioButton_1_1_1.setBackground(new Color(211, 211, 211));
		PrimaryObjectPanel.add(rdbtnNewRadioButton_1_1_1);
		
		JRadioButton rdbtnYes_1_1_1 = new JRadioButton("Yes");
		rdbtnYes_1_1_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rdbtnYes_1_1_1.setBackground(new Color(211, 211, 211));
		PrimaryObjectPanel.add(rdbtnYes_1_1_1);
		
		JLabel lblRadius_1_1 = new JLabel("Radius:\r\n");
		lblRadius_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblRadius_1_1.setEnabled(false);
		PrimaryObjectPanel.add(lblRadius_1_1);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setColumns(3);
		textField.setBackground(new Color(211, 211, 211));
		PrimaryObjectPanel.add(textField);
		
		JLabel lbldGaussianBlur = new JLabel("3D Gaussian Blur:                     ");
		lbldGaussianBlur.setFont(new Font("Gadugi", Font.PLAIN, 14));
		PrimaryObjectPanel.add(lbldGaussianBlur);
		
		JLabel lblX_2_1 = new JLabel("x");
		lblX_2_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		PrimaryObjectPanel.add(lblX_2_1);
		
		txtPriGBx = new JTextField();
		txtPriGBx.setHorizontalAlignment(SwingConstants.LEFT);
		txtPriGBx.setText("1");
		txtPriGBx.setColumns(3);
		txtPriGBx.setBackground(new Color(211, 211, 211));
		PrimaryObjectPanel.add(txtPriGBx);
		
		JLabel lblY_2_1 = new JLabel("y");
		lblY_2_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		PrimaryObjectPanel.add(lblY_2_1);
		
		txtPriGBy = new JTextField();
		txtPriGBy.setText("1");
		txtPriGBy.setColumns(3);
		txtPriGBy.setBackground(new Color(211, 211, 211));
		PrimaryObjectPanel.add(txtPriGBy);
		
		JLabel lblZ_2_1 = new JLabel("z");
		lblZ_2_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		PrimaryObjectPanel.add(lblZ_2_1);
		
		txtPriGBz = new JTextField();
		txtPriGBz.setText("1");
		txtPriGBz.setColumns(3);
		txtPriGBz.setBackground(new Color(211, 211, 211));
		PrimaryObjectPanel.add(txtPriGBz);
		
		JLabel lbldMaxima = new JLabel("3D Maxima Radius:                    ");
		lbldMaxima.setFont(new Font("Gadugi", Font.PLAIN, 14));
		PrimaryObjectPanel.add(lbldMaxima);
		
		JLabel lblX_2_1_1 = new JLabel("x");
		lblX_2_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		PrimaryObjectPanel.add(lblX_2_1_1);
		
		txtPriDMx = new JTextField();
		txtPriDMx.setText("1");
		txtPriDMx.setHorizontalAlignment(SwingConstants.LEFT);
		txtPriDMx.setColumns(3);
		txtPriDMx.setBackground(new Color(211, 211, 211));
		PrimaryObjectPanel.add(txtPriDMx);
		
		JLabel lblY_2_1_1 = new JLabel("y");
		lblY_2_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		PrimaryObjectPanel.add(lblY_2_1_1);
		
		txtPriDMy = new JTextField();
		txtPriDMy.setText("1");
		txtPriDMy.setColumns(3);
		txtPriDMy.setBackground(new Color(211, 211, 211));
		PrimaryObjectPanel.add(txtPriDMy);
		
		JLabel lblZ_2_1_1 = new JLabel("z");
		lblZ_2_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		PrimaryObjectPanel.add(lblZ_2_1_1);
		
		txtPriDMz = new JTextField();
		txtPriDMz.setText("1");
		txtPriDMz.setColumns(3);
		txtPriDMz.setBackground(new Color(211, 211, 211));
		PrimaryObjectPanel.add(txtPriDMz);
	}
}
