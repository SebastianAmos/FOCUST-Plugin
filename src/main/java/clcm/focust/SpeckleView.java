package clcm.focust;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class SpeckleView extends JFrame {

	private JPanel paneSpeckle;
	private JTextField txtInputDir;
	private JTextField txtOutputDir;
	private JTextField txtSpecklePrimaryGBx;
	private JTextField txtSpecklePrimaryGBy;
	private JTextField txtSpecklePrimaryGBz;
	private JTextField txtSpecklePrimaryBG;
	private JTextField txtSpecklePrimaryDMx;
	private JTextField txtSpecklePrimaryDMy;
	private JTextField txtSpecklePrimaryDMz;
	private JTextField txtSpecklePrimaryThreshold;
	private JTextField txtSpeckleSecondaryGBx;
	private JTextField txtSpeckleSecondaryGBy;
	private JTextField txtSpeckleSecondaryGBz;
	private JTextField txtSpeckleSecondaryBG;
	private JTextField txtSpeckleSecondaryDMx;
	private JTextField txtSpeckleSecondaryDMy;
	private JTextField txtSpeckleSecondaryDMz;
	private JTextField txtSpeckleSecondaryThreshold;
	private JTextField txtC2Name;
	private JTextField txtC3Name;
	private JTextField txtC4Name;
	private JTextField txtGroupingName;
	private JTextField txtSpeckleTertiaryGBx;
	private JTextField txtSpeckleTertiaryGBy;
	private JTextField txtSpeckleTertiaryGBz;
	private JTextField txtSpeckleTertiaryBG;
	private JTextField txtSpeckleTertiaryDMx;
	private JTextField txtSpeckleTertiaryDMy;
	private JTextField txtSpeckleTertiaryDMz;
	private JTextField txtSpeckleTertiaryThreshold;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();

	/**
	 * Construct the speckle gui.
	 */
	public SpeckleView() {
		
		setTitle("FOCUST: Speckle Analysis");
		setIconImage(Toolkit.getDefaultToolkit().getImage(SpeckleView.class.getResource("/clcm/focust/resources/icon2.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 980, 497);
		paneSpeckle = new JPanel();
		paneSpeckle.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(paneSpeckle);
		paneSpeckle.setLayout(null);
		
		JLabel lblSelectAnInput = new JLabel("Select an input directory:");
		lblSelectAnInput.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblSelectAnInput.setBounds(18, 28, 167, 29);
		paneSpeckle.add(lblSelectAnInput);
		
		JButton btnInputDir = new JButton("Browse");
		btnInputDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Find and set the input directory.
				FOCUST.fileFinder();
				FOCUST.inputDir = FOCUST.imageFiles[0].getParent();
				
				// update the textbox in spheroid view
				txtInputDir.setText(FOCUST.inputDir);
			}
		});
		
		btnInputDir.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnInputDir.setBounds(191, 28, 96, 29);
		paneSpeckle.add(btnInputDir);
		
		JLabel lblSeperateOutputDirectory = new JLabel("Seperate output directory?");
		lblSeperateOutputDirectory.setToolTipText("Selecting yes will save output files to the specified directory.");
		lblSeperateOutputDirectory.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblSeperateOutputDirectory.setBounds(18, 68, 167, 29);
		paneSpeckle.add(lblSeperateOutputDirectory);
		
		JRadioButton rbOutputDirNo = new JRadioButton("No");
		buttonGroup_1.add(rbOutputDirNo);
		rbOutputDirNo.setSelected(true);
		rbOutputDirNo.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbOutputDirNo.setBounds(191, 71, 48, 23);
		paneSpeckle.add(rbOutputDirNo);
		
		JRadioButton rbOutputDirYes = new JRadioButton("Yes");
		buttonGroup_1.add(rbOutputDirYes);
		rbOutputDirYes.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbOutputDirYes.setBounds(241, 71, 48, 23);
		paneSpeckle.add(rbOutputDirYes);
		
		JButton btnOutputDir = new JButton("Browse");
		btnOutputDir.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnOutputDir.setEnabled(false);
		btnOutputDir.setBounds(295, 68, 96, 29);
		paneSpeckle.add(btnOutputDir);
		
		txtInputDir = new JTextField();
		txtInputDir.setColumns(10);
		txtInputDir.setBounds(297, 28, 289, 29);
		paneSpeckle.add(txtInputDir);
		
		txtOutputDir = new JTextField();
		txtOutputDir.setEnabled(false);
		txtOutputDir.setColumns(10);
		txtOutputDir.setBounds(401, 68, 289, 29);
		paneSpeckle.add(txtOutputDir);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(169, 169, 169));
		separator.setBackground(Color.WHITE);
		separator.setBounds(10, 134, 938, 2);
		paneSpeckle.add(separator);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnHelp.setBounds(852, 11, 96, 29);
		paneSpeckle.add(btnHelp);
		
		JLabel lblNewLabel = new JLabel("Process a Dataset:");
		lblNewLabel.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 0, 133, 29);
		paneSpeckle.add(lblNewLabel);
		
		JLabel lblYouMustHave = new JLabel("You must have optimized your segmentation parameters.");
		lblYouMustHave.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblYouMustHave.setBounds(136, 0, 401, 29);
		paneSpeckle.add(lblYouMustHave);
		
		JLabel lblHowManyChannels = new JLabel("Total number of channels per image?");
		lblHowManyChannels.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblHowManyChannels.setBounds(13, 187, 237, 29);
		paneSpeckle.add(lblHowManyChannels);
		
		JComboBox cbChannelTotal = new JComboBox();
		cbChannelTotal.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// enable name input for C4 if 4 channels declared
				if(cbChannelTotal.getSelectedItem().toString().equals("4")) {
					txtC4Name.setEnabled(true);
				} else {
					txtC4Name.setEnabled(false);
				}	
				
				// enable name input for C3 if 3 or 4 channels declared. 
				if (cbChannelTotal.getSelectedItem().toString().equals("3")) {
					txtC3Name.setEnabled(true);
				} else if (cbChannelTotal.getSelectedItem().toString().equals("4")) {
					txtC3Name.setEnabled(true);
				} else {
					txtC3Name.setEnabled(false);
				}
			}
		});
		
		cbChannelTotal.setModel(new DefaultComboBoxModel(new String[] {"2", "3", "4"}));
		cbChannelTotal.setSelectedIndex(0);
		cbChannelTotal.setMaximumRowCount(3);
		cbChannelTotal.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelTotal.setBounds(251, 189, 48, 25);
		paneSpeckle.add(cbChannelTotal);
		
		JLabel lblSegmentPrimary = new JLabel("Primary Object\r\n");
		lblSegmentPrimary.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegmentPrimary.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblSegmentPrimary.setBounds(324, 192, 187, 29);
		paneSpeckle.add(lblSegmentPrimary);
		
		JPanel PrimaryObjectPanel = new JPanel();
		PrimaryObjectPanel.setLayout(null);
		PrimaryObjectPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		PrimaryObjectPanel.setBackground(new Color(211, 211, 211));
		PrimaryObjectPanel.setBounds(324, 250, 187, 182);
		paneSpeckle.add(PrimaryObjectPanel);
		
		JLabel lblBgSubPrimary = new JLabel("Background subtraction?");
		lblBgSubPrimary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBgSubPrimary.setBounds(10, 0, 161, 29);
		PrimaryObjectPanel.add(lblBgSubPrimary);
		
		JRadioButton rbBgSubPrimaryNo = new JRadioButton("No");
		rbBgSubPrimaryNo.setSelected(true);
		rbBgSubPrimaryNo.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbBgSubPrimaryNo.setBackground(new Color(211, 211, 211));
		rbBgSubPrimaryNo.setBounds(10, 24, 43, 23);
		PrimaryObjectPanel.add(rbBgSubPrimaryNo);
		
		JRadioButton rbBgSubPrimaryYes = new JRadioButton("Yes");
		rbBgSubPrimaryYes.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbBgSubPrimaryYes.setEnabled(false);
		rbBgSubPrimaryYes.setBackground(new Color(211, 211, 211));
		rbBgSubPrimaryYes.setBounds(50, 24, 48, 23);
		PrimaryObjectPanel.add(rbBgSubPrimaryYes);
		
		JLabel lblGaussianBlurPrimary = new JLabel("3D Gaussian blur:");
		lblGaussianBlurPrimary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblGaussianBlurPrimary.setBounds(10, 47, 161, 29);
		PrimaryObjectPanel.add(lblGaussianBlurPrimary);
		
		JLabel lblRadius = new JLabel("Radius:\r\n");
		lblRadius.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblRadius.setEnabled(false);
		lblRadius.setBounds(97, 21, 48, 29);
		PrimaryObjectPanel.add(lblRadius);
		
		txtSpecklePrimaryGBx = new JTextField();
		txtSpecklePrimaryGBx.setColumns(10);
		txtSpecklePrimaryGBx.setBackground(new Color(211, 211, 211));
		txtSpecklePrimaryGBx.setBounds(20, 73, 41, 20);
		PrimaryObjectPanel.add(txtSpecklePrimaryGBx);
		
		JLabel lblX = new JLabel("x");
		lblX.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX.setBounds(10, 69, 21, 29);
		PrimaryObjectPanel.add(lblX);
		
		JLabel lblY = new JLabel("y");
		lblY.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY.setBounds(65, 69, 21, 29);
		PrimaryObjectPanel.add(lblY);
		
		txtSpecklePrimaryGBy = new JTextField();
		txtSpecklePrimaryGBy.setColumns(10);
		txtSpecklePrimaryGBy.setBackground(new Color(211, 211, 211));
		txtSpecklePrimaryGBy.setBounds(75, 73, 41, 20);
		PrimaryObjectPanel.add(txtSpecklePrimaryGBy);
		
		JLabel lblZ = new JLabel("z");
		lblZ.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ.setBounds(120, 69, 21, 29);
		PrimaryObjectPanel.add(lblZ);
		
		txtSpecklePrimaryGBz = new JTextField();
		txtSpecklePrimaryGBz.setColumns(10);
		txtSpecklePrimaryGBz.setBackground(new Color(211, 211, 211));
		txtSpecklePrimaryGBz.setBounds(130, 73, 41, 20);
		PrimaryObjectPanel.add(txtSpecklePrimaryGBz);
		
		txtSpecklePrimaryBG = new JTextField();
		txtSpecklePrimaryBG.setEnabled(false);
		txtSpecklePrimaryBG.setColumns(10);
		txtSpecklePrimaryBG.setBackground(new Color(211, 211, 211));
		txtSpecklePrimaryBG.setBounds(145, 25, 35, 20);
		PrimaryObjectPanel.add(txtSpecklePrimaryBG);
		
		JLabel lblDetectMaximaPrimary = new JLabel("3D detect maxima radius:");
		lblDetectMaximaPrimary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblDetectMaximaPrimary.setBounds(10, 96, 161, 29);
		PrimaryObjectPanel.add(lblDetectMaximaPrimary);
		
		JLabel lblX_1 = new JLabel("x");
		lblX_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_1.setBounds(10, 117, 21, 29);
		PrimaryObjectPanel.add(lblX_1);
		
		txtSpecklePrimaryDMx = new JTextField();
		txtSpecklePrimaryDMx.setColumns(10);
		txtSpecklePrimaryDMx.setBackground(new Color(211, 211, 211));
		txtSpecklePrimaryDMx.setBounds(20, 121, 41, 20);
		PrimaryObjectPanel.add(txtSpecklePrimaryDMx);
		
		JLabel lblY_1 = new JLabel("y");
		lblY_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_1.setBounds(65, 117, 21, 29);
		PrimaryObjectPanel.add(lblY_1);
		
		txtSpecklePrimaryDMy = new JTextField();
		txtSpecklePrimaryDMy.setColumns(10);
		txtSpecklePrimaryDMy.setBackground(new Color(211, 211, 211));
		txtSpecklePrimaryDMy.setBounds(75, 121, 41, 20);
		PrimaryObjectPanel.add(txtSpecklePrimaryDMy);
		
		JLabel lblZ_1 = new JLabel("z");
		lblZ_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_1.setBounds(120, 117, 21, 29);
		PrimaryObjectPanel.add(lblZ_1);
		
		txtSpecklePrimaryDMz = new JTextField();
		txtSpecklePrimaryDMz.setColumns(10);
		txtSpecklePrimaryDMz.setBackground(new Color(211, 211, 211));
		txtSpecklePrimaryDMz.setBounds(130, 121, 41, 20);
		PrimaryObjectPanel.add(txtSpecklePrimaryDMz);
		
		JLabel lblThreshold = new JLabel("Threshold:");
		lblThreshold.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblThreshold.setBounds(10, 144, 76, 29);
		PrimaryObjectPanel.add(lblThreshold);
		
		txtSpecklePrimaryThreshold = new JTextField();
		txtSpecklePrimaryThreshold.setColumns(10);
		txtSpecklePrimaryThreshold.setBackground(new Color(211, 211, 211));
		txtSpecklePrimaryThreshold.setBounds(76, 148, 55, 20);
		PrimaryObjectPanel.add(txtSpecklePrimaryThreshold);
		
		JLabel lblWhichChannel = new JLabel("Which channel? ");
		lblWhichChannel.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblWhichChannel.setBounds(339, 217, 106, 29);
		paneSpeckle.add(lblWhichChannel);
		
		JComboBox cbChannelPrimary = new JComboBox();
		cbChannelPrimary.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbChannelPrimary.setSelectedIndex(0);
		cbChannelPrimary.setMaximumRowCount(4);
		cbChannelPrimary.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelPrimary.setBounds(452, 219, 48, 25);
		paneSpeckle.add(cbChannelPrimary);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(309, 189, 639, 255);
		paneSpeckle.add(panel);
		
		JPanel SecondaryObjectPanel_1 = new JPanel();
		SecondaryObjectPanel_1.setLayout(null);
		SecondaryObjectPanel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		SecondaryObjectPanel_1.setBackground(new Color(211, 211, 211));
		SecondaryObjectPanel_1.setBounds(435, 61, 187, 182);
		panel.add(SecondaryObjectPanel_1);
		
		JLabel lblBgSubSecondary_1 = new JLabel("Background subtraction?");
		lblBgSubSecondary_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBgSubSecondary_1.setBounds(10, 0, 161, 29);
		SecondaryObjectPanel_1.add(lblBgSubSecondary_1);
		
		JRadioButton rdbtnNewRadioButton_1_1_1 = new JRadioButton("No");
		rdbtnNewRadioButton_1_1_1.setSelected(true);
		rdbtnNewRadioButton_1_1_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rdbtnNewRadioButton_1_1_1.setBackground(new Color(211, 211, 211));
		rdbtnNewRadioButton_1_1_1.setBounds(10, 24, 43, 23);
		SecondaryObjectPanel_1.add(rdbtnNewRadioButton_1_1_1);
		
		JRadioButton rdbtnYes_1_1_1 = new JRadioButton("Yes");
		rdbtnYes_1_1_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rdbtnYes_1_1_1.setEnabled(false);
		rdbtnYes_1_1_1.setBackground(new Color(211, 211, 211));
		rdbtnYes_1_1_1.setBounds(50, 24, 48, 23);
		SecondaryObjectPanel_1.add(rdbtnYes_1_1_1);
		
		JLabel lblGaussianBlurSecondary_1 = new JLabel("3D Gaussian blur:");
		lblGaussianBlurSecondary_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblGaussianBlurSecondary_1.setBounds(10, 47, 161, 29);
		SecondaryObjectPanel_1.add(lblGaussianBlurSecondary_1);
		
		JLabel lblRadius_1_1 = new JLabel("Radius:\r\n");
		lblRadius_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblRadius_1_1.setEnabled(false);
		lblRadius_1_1.setBounds(98, 21, 48, 29);
		SecondaryObjectPanel_1.add(lblRadius_1_1);
		
		txtSpeckleTertiaryGBx = new JTextField();
		txtSpeckleTertiaryGBx.setColumns(10);
		txtSpeckleTertiaryGBx.setBackground(new Color(211, 211, 211));
		txtSpeckleTertiaryGBx.setBounds(20, 73, 41, 20);
		SecondaryObjectPanel_1.add(txtSpeckleTertiaryGBx);
		
		JLabel lblX_2_1 = new JLabel("x");
		lblX_2_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_2_1.setBounds(10, 69, 21, 29);
		SecondaryObjectPanel_1.add(lblX_2_1);
		
		JLabel lblY_2_1 = new JLabel("y");
		lblY_2_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_2_1.setBounds(65, 69, 21, 29);
		SecondaryObjectPanel_1.add(lblY_2_1);
		
		txtSpeckleTertiaryGBy = new JTextField();
		txtSpeckleTertiaryGBy.setColumns(10);
		txtSpeckleTertiaryGBy.setBackground(new Color(211, 211, 211));
		txtSpeckleTertiaryGBy.setBounds(75, 73, 41, 20);
		SecondaryObjectPanel_1.add(txtSpeckleTertiaryGBy);
		
		JLabel lblZ_2_1 = new JLabel("z");
		lblZ_2_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_2_1.setBounds(120, 69, 21, 29);
		SecondaryObjectPanel_1.add(lblZ_2_1);
		
		txtSpeckleTertiaryGBz = new JTextField();
		txtSpeckleTertiaryGBz.setColumns(10);
		txtSpeckleTertiaryGBz.setBackground(new Color(211, 211, 211));
		txtSpeckleTertiaryGBz.setBounds(130, 73, 41, 20);
		SecondaryObjectPanel_1.add(txtSpeckleTertiaryGBz);
		
		txtSpeckleTertiaryBG = new JTextField();
		txtSpeckleTertiaryBG.setEnabled(false);
		txtSpeckleTertiaryBG.setColumns(10);
		txtSpeckleTertiaryBG.setBackground(new Color(211, 211, 211));
		txtSpeckleTertiaryBG.setBounds(145, 25, 31, 20);
		SecondaryObjectPanel_1.add(txtSpeckleTertiaryBG);
		
		JLabel lblDetectMaximaSecondary_1 = new JLabel("3D detect maxima radius:");
		lblDetectMaximaSecondary_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblDetectMaximaSecondary_1.setBounds(10, 96, 161, 29);
		SecondaryObjectPanel_1.add(lblDetectMaximaSecondary_1);
		
		JLabel lblX_1_1_1 = new JLabel("x");
		lblX_1_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_1_1_1.setBounds(10, 117, 21, 29);
		SecondaryObjectPanel_1.add(lblX_1_1_1);
		
		txtSpeckleTertiaryDMx = new JTextField();
		txtSpeckleTertiaryDMx.setColumns(10);
		txtSpeckleTertiaryDMx.setBackground(new Color(211, 211, 211));
		txtSpeckleTertiaryDMx.setBounds(20, 121, 41, 20);
		SecondaryObjectPanel_1.add(txtSpeckleTertiaryDMx);
		
		JLabel lblY_1_1_1 = new JLabel("y");
		lblY_1_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_1_1_1.setBounds(65, 117, 21, 29);
		SecondaryObjectPanel_1.add(lblY_1_1_1);
		
		txtSpeckleTertiaryDMy = new JTextField();
		txtSpeckleTertiaryDMy.setColumns(10);
		txtSpeckleTertiaryDMy.setBackground(new Color(211, 211, 211));
		txtSpeckleTertiaryDMy.setBounds(75, 121, 41, 20);
		SecondaryObjectPanel_1.add(txtSpeckleTertiaryDMy);
		
		JLabel lblZ_1_1_1 = new JLabel("z");
		lblZ_1_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_1_1_1.setBounds(120, 117, 21, 29);
		SecondaryObjectPanel_1.add(lblZ_1_1_1);
		
		txtSpeckleTertiaryDMz = new JTextField();
		txtSpeckleTertiaryDMz.setColumns(10);
		txtSpeckleTertiaryDMz.setBackground(new Color(211, 211, 211));
		txtSpeckleTertiaryDMz.setBounds(130, 121, 41, 20);
		SecondaryObjectPanel_1.add(txtSpeckleTertiaryDMz);
		
		JLabel lblThreshold_1_1 = new JLabel("Threshold:");
		lblThreshold_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblThreshold_1_1.setBounds(10, 144, 76, 29);
		SecondaryObjectPanel_1.add(lblThreshold_1_1);
		
		txtSpeckleTertiaryThreshold = new JTextField();
		txtSpeckleTertiaryThreshold.setColumns(10);
		txtSpeckleTertiaryThreshold.setBackground(new Color(211, 211, 211));
		txtSpeckleTertiaryThreshold.setBounds(76, 148, 55, 20);
		SecondaryObjectPanel_1.add(txtSpeckleTertiaryThreshold);
		
		JLabel lblWhichChannel_1_1 = new JLabel("Which channel? ");
		lblWhichChannel_1_1.setEnabled(false);
		lblWhichChannel_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblWhichChannel_1_1.setBounds(448, 28, 106, 29);
		panel.add(lblWhichChannel_1_1);
		
		JComboBox cbChannelSecondary_1 = new JComboBox();
		cbChannelSecondary_1.setEnabled(false);
		cbChannelSecondary_1.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbChannelSecondary_1.setSelectedIndex(2);
		cbChannelSecondary_1.setMaximumRowCount(4);
		cbChannelSecondary_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelSecondary_1.setBounds(561, 30, 48, 25);
		panel.add(cbChannelSecondary_1);
		
		JLabel lblSegmentSecondary_1 = new JLabel("Tertiary Object");
		lblSegmentSecondary_1.setEnabled(false);
		lblSegmentSecondary_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegmentSecondary_1.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblSegmentSecondary_1.setBounds(435, 3, 187, 29);
		panel.add(lblSegmentSecondary_1);
		
		JPanel SecondaryObjectPanel = new JPanel();
		SecondaryObjectPanel.setBounds(223, 61, 187, 182);
		panel.add(SecondaryObjectPanel);
		SecondaryObjectPanel.setLayout(null);
		SecondaryObjectPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		SecondaryObjectPanel.setBackground(new Color(211, 211, 211));
		
		JLabel lblBgSubSecondary = new JLabel("Background subtraction?");
		lblBgSubSecondary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBgSubSecondary.setBounds(10, 0, 161, 29);
		SecondaryObjectPanel.add(lblBgSubSecondary);
		
		JRadioButton rdbtnNewRadioButton_1_1 = new JRadioButton("No");
		rdbtnNewRadioButton_1_1.setSelected(true);
		rdbtnNewRadioButton_1_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rdbtnNewRadioButton_1_1.setBackground(new Color(211, 211, 211));
		rdbtnNewRadioButton_1_1.setBounds(10, 24, 43, 23);
		SecondaryObjectPanel.add(rdbtnNewRadioButton_1_1);
		
		JRadioButton rdbtnYes_1_1 = new JRadioButton("Yes");
		rdbtnYes_1_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rdbtnYes_1_1.setEnabled(false);
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
		
		txtSpeckleSecondaryGBx = new JTextField();
		txtSpeckleSecondaryGBx.setColumns(10);
		txtSpeckleSecondaryGBx.setBackground(new Color(211, 211, 211));
		txtSpeckleSecondaryGBx.setBounds(20, 73, 41, 20);
		SecondaryObjectPanel.add(txtSpeckleSecondaryGBx);
		
		JLabel lblX_2 = new JLabel("x");
		lblX_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_2.setBounds(10, 69, 21, 29);
		SecondaryObjectPanel.add(lblX_2);
		
		JLabel lblY_2 = new JLabel("y");
		lblY_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_2.setBounds(65, 69, 21, 29);
		SecondaryObjectPanel.add(lblY_2);
		
		txtSpeckleSecondaryGBy = new JTextField();
		txtSpeckleSecondaryGBy.setColumns(10);
		txtSpeckleSecondaryGBy.setBackground(new Color(211, 211, 211));
		txtSpeckleSecondaryGBy.setBounds(75, 73, 41, 20);
		SecondaryObjectPanel.add(txtSpeckleSecondaryGBy);
		
		JLabel lblZ_2 = new JLabel("z");
		lblZ_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_2.setBounds(120, 69, 21, 29);
		SecondaryObjectPanel.add(lblZ_2);
		
		txtSpeckleSecondaryGBz = new JTextField();
		txtSpeckleSecondaryGBz.setColumns(10);
		txtSpeckleSecondaryGBz.setBackground(new Color(211, 211, 211));
		txtSpeckleSecondaryGBz.setBounds(130, 73, 41, 20);
		SecondaryObjectPanel.add(txtSpeckleSecondaryGBz);
		
		txtSpeckleSecondaryBG = new JTextField();
		txtSpeckleSecondaryBG.setEnabled(false);
		txtSpeckleSecondaryBG.setColumns(10);
		txtSpeckleSecondaryBG.setBackground(new Color(211, 211, 211));
		txtSpeckleSecondaryBG.setBounds(145, 25, 31, 20);
		SecondaryObjectPanel.add(txtSpeckleSecondaryBG);
		
		JLabel lblDetectMaximaSecondary = new JLabel("3D detect maxima radius:");
		lblDetectMaximaSecondary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblDetectMaximaSecondary.setBounds(10, 96, 161, 29);
		SecondaryObjectPanel.add(lblDetectMaximaSecondary);
		
		JLabel lblX_1_1 = new JLabel("x");
		lblX_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_1_1.setBounds(10, 117, 21, 29);
		SecondaryObjectPanel.add(lblX_1_1);
		
		txtSpeckleSecondaryDMx = new JTextField();
		txtSpeckleSecondaryDMx.setColumns(10);
		txtSpeckleSecondaryDMx.setBackground(new Color(211, 211, 211));
		txtSpeckleSecondaryDMx.setBounds(20, 121, 41, 20);
		SecondaryObjectPanel.add(txtSpeckleSecondaryDMx);
		
		JLabel lblY_1_1 = new JLabel("y");
		lblY_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_1_1.setBounds(65, 117, 21, 29);
		SecondaryObjectPanel.add(lblY_1_1);
		
		txtSpeckleSecondaryDMy = new JTextField();
		txtSpeckleSecondaryDMy.setColumns(10);
		txtSpeckleSecondaryDMy.setBackground(new Color(211, 211, 211));
		txtSpeckleSecondaryDMy.setBounds(75, 121, 41, 20);
		SecondaryObjectPanel.add(txtSpeckleSecondaryDMy);
		
		JLabel lblZ_1_1 = new JLabel("z");
		lblZ_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_1_1.setBounds(120, 117, 21, 29);
		SecondaryObjectPanel.add(lblZ_1_1);
		
		txtSpeckleSecondaryDMz = new JTextField();
		txtSpeckleSecondaryDMz.setColumns(10);
		txtSpeckleSecondaryDMz.setBackground(new Color(211, 211, 211));
		txtSpeckleSecondaryDMz.setBounds(130, 121, 41, 20);
		SecondaryObjectPanel.add(txtSpeckleSecondaryDMz);
		
		JLabel lblThreshold_1 = new JLabel("Threshold:");
		lblThreshold_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblThreshold_1.setBounds(10, 144, 76, 29);
		SecondaryObjectPanel.add(lblThreshold_1);
		
		txtSpeckleSecondaryThreshold = new JTextField();
		txtSpeckleSecondaryThreshold.setColumns(10);
		txtSpeckleSecondaryThreshold.setBackground(new Color(211, 211, 211));
		txtSpeckleSecondaryThreshold.setBounds(76, 148, 55, 20);
		SecondaryObjectPanel.add(txtSpeckleSecondaryThreshold);
		
		JLabel lblSegmentSecondary = new JLabel("Secondary Object");
		lblSegmentSecondary.setBounds(223, 3, 187, 29);
		panel.add(lblSegmentSecondary);
		lblSegmentSecondary.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegmentSecondary.setFont(new Font("Gadugi", Font.BOLD, 14));
		
		JLabel lblWhichChannel_1 = new JLabel("Which channel? ");
		lblWhichChannel_1.setBounds(236, 28, 106, 29);
		panel.add(lblWhichChannel_1);
		lblWhichChannel_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		
		JComboBox cbChannelSecondary = new JComboBox();
		cbChannelSecondary.setBounds(349, 30, 48, 25);
		panel.add(cbChannelSecondary);
		cbChannelSecondary.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbChannelSecondary.setSelectedIndex(1);
		cbChannelSecondary.setMaximumRowCount(4);
		cbChannelSecondary.setFont(new Font("Gadugi", Font.PLAIN, 13));
		
		JLabel lblSegmentationParameters = new JLabel("Segmentation Parameters");
		lblSegmentationParameters.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegmentationParameters.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblSegmentationParameters.setBounds(546, 157, 216, 29);
		paneSpeckle.add(lblSegmentationParameters);
		
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
		btnBackToMenu.setBounds(13, 382, 133, 29);
		paneSpeckle.add(btnBackToMenu);
		
		JButton btnRunAnalysis = new JButton("Run Analysis");
		btnRunAnalysis.setFont(new Font("Gadugi", Font.BOLD, 14));
		btnRunAnalysis.setBounds(13, 415, 286, 29);
		paneSpeckle.add(btnRunAnalysis);
		
		JLabel lblNameChannel2 = new JLabel("Name Channel 2:");
		lblNameChannel2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblNameChannel2.setBounds(13, 225, 116, 29);
		paneSpeckle.add(lblNameChannel2);
		
		JLabel lblNameChannel3 = new JLabel("Name Channel 3:");
		lblNameChannel3.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblNameChannel3.setBounds(13, 259, 116, 29);
		paneSpeckle.add(lblNameChannel3);
		
		JLabel lblNameChannel4 = new JLabel("Name Channel 4:");
		lblNameChannel4.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblNameChannel4.setBounds(13, 292, 116, 29);
		paneSpeckle.add(lblNameChannel4);
		
		JLabel lblGroupingInfo = new JLabel("Grouping* Info?");
		lblGroupingInfo.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblGroupingInfo.setBounds(13, 332, 116, 29);
		paneSpeckle.add(lblGroupingInfo);
		
		txtC2Name = new JTextField();
		txtC2Name.setColumns(10);
		txtC2Name.setBounds(123, 225, 176, 29);
		paneSpeckle.add(txtC2Name);
		
		txtC3Name = new JTextField();
		txtC3Name.setEnabled(false);
		txtC3Name.setColumns(10);
		txtC3Name.setBounds(123, 259, 176, 29);
		paneSpeckle.add(txtC3Name);
		
		txtC4Name = new JTextField();
		txtC4Name.setEnabled(false);
		txtC4Name.setColumns(10);
		txtC4Name.setBounds(123, 292, 176, 29);
		paneSpeckle.add(txtC4Name);
		
		txtGroupingName = new JTextField();
		txtGroupingName.setColumns(10);
		txtGroupingName.setBounds(123, 332, 176, 29);
		paneSpeckle.add(txtGroupingName);
		
		JButton btnLoadConfigSingleCell = new JButton("Load Parameters");
		btnLoadConfigSingleCell.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnLoadConfigSingleCell.setBounds(152, 382, 147, 29);
		paneSpeckle.add(btnLoadConfigSingleCell);
		
		JLabel lblPerformColocalizationAnalysis = new JLabel("Perform colocalization analysis*?");
		lblPerformColocalizationAnalysis.setToolTipText("There must be secondary and tertiary objects to perform colocalization.");
		lblPerformColocalizationAnalysis.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblPerformColocalizationAnalysis.setBounds(10, 147, 206, 29);
		paneSpeckle.add(lblPerformColocalizationAnalysis);
		
		JRadioButton rbOutputDirNo_1 = new JRadioButton("No");
		buttonGroup.add(rbOutputDirNo_1);
		rbOutputDirNo_1.setSelected(true);
		rbOutputDirNo_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbOutputDirNo_1.setBounds(210, 150, 48, 23);
		paneSpeckle.add(rbOutputDirNo_1);
		
		JRadioButton rbOutputDirYes_1 = new JRadioButton("Yes");
		buttonGroup.add(rbOutputDirYes_1);
		rbOutputDirYes_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbOutputDirYes_1.setBounds(260, 150, 48, 23);
		paneSpeckle.add(rbOutputDirYes_1);
		
		JCheckBox cbAnalysisMode = new JCheckBox("Analysis only mode?");
		cbAnalysisMode.setToolTipText("Runs analysis where the user provides labelled and original images.");
		cbAnalysisMode.setSelected(true);
		cbAnalysisMode.setFont(new Font("Gadugi", Font.PLAIN, 14));
		cbAnalysisMode.setBounds(18, 104, 157, 23);
		paneSpeckle.add(cbAnalysisMode);
	}
}
