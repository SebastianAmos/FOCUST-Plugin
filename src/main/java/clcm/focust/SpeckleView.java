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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import ij.IJ;
import javax.swing.border.EtchedBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ButtonGroup;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JCheckBox;

public class SpeckleView extends JFrame {

	private JPanel paneSpeckle;
	private JTextField txtInputDir;
	private JTextField txtOutputDir;
	private JTextField txtPriGBx;
	private JTextField txtPriGBy;
	private JTextField txtPriGBz;
	private JTextField txtSpecklePrimaryBG;
	private JTextField txtPriDMx;
	private JTextField txtPriDMy;
	private JTextField txtPriDMz;
	private JTextField txtPriThreshold;
	private JTextField txtSecGBx;
	private JTextField txtSecGBy;
	private JTextField txtSecGBz;
	private JTextField txtSpeckleSecondaryBG;
	private JTextField txtSecDMx;
	private JTextField txtSecDMy;
	private JTextField txtSecDMz;
	private JTextField txtC2Name;
	private JTextField txtC3Name;
	private JTextField txtC4Name;
	private JTextField txtGroupingName;
	private JTextField txtTertGBx;
	private JTextField txtTertGBy;
	private JTextField txtTertGBz;
	private JTextField txtSpeckleTertiaryBG;
	private JTextField txtTertDMx;
	private JTextField txtTertDMy;
	private JTextField txtTertDMz;
	private JTextField txtTertThreshold;
	private JTextField txtSecThreshold;
	private JCheckBox cbAnalysisMode = new JCheckBox("Analysis only mode?");
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();

	public static String killBordersText;
	public static String inputDir;
	public static String channel2Name;
	public static String channel3Name;
	public static String channel4Name;
	public static int primaryChannelChoice;
	public static int secondaryChannelChoice;
	public static int tertiaryChannelChoice;
	public static String groupingInfo;
	public static boolean analysisMode;
	private final ButtonGroup btngrpKillBorders = new ButtonGroup();

	private FilterSpec[] filterSpecs = new FilterSpec[3];

	void saveParameterValues(String outputFileDir){
		// TODO: Not a great way to do this. Hard-coded filename is a no-no.
		// TODO: Add button and input field for parameter filename and dir.
		setFilterParameters(); // Just in case the user didn't set the button.
		Path path = Paths.get(outputFileDir, "FilterSpecs.json");
		FilterSpec.saveFilterSpecs(filterSpecs, path.toString());
	}

	void loadParameterValues(String parameterFilePath){
		FilterSpec[] tempFilterSpecs = FilterSpec.readFilterSpecJSON(parameterFilePath);
		if(tempFilterSpecs == null){
			IJ.error("Error reading parameter file.");
		} else if (tempFilterSpecs.length < 3){
			// Catches the case where the parameter file has less than 3 filter specs.
			System.arraycopy(tempFilterSpecs, 0, filterSpecs, 0, tempFilterSpecs.length);
		} else {
			// Catches both cases where the parameter file has more than 3 filter specs and where it has exactly 3.
			System.arraycopy(tempFilterSpecs, 0, filterSpecs, 0, filterSpecs.length);
		}
	}

	void setFilterParameterUIValues(){
		// TODO: Remove the hard-coding
		if(filterSpecs[0] != null){
			txtPriGBx.setText(String.valueOf(filterSpecs[0].sigma_x));
			txtPriGBy.setText(String.valueOf(filterSpecs[0].sigma_y));
			txtPriGBz.setText(String.valueOf(filterSpecs[0].sigma_z));
			txtPriDMx.setText(String.valueOf(filterSpecs[0].radius_x));
			txtPriDMy.setText(String.valueOf(filterSpecs[0].radius_y));
			txtPriDMz.setText(String.valueOf(filterSpecs[0].radius_z));
			txtPriThreshold.setText(String.valueOf(filterSpecs[0].greaterConstant));
		}
		if(filterSpecs[1] != null){
			txtSecGBx.setText(String.valueOf(filterSpecs[1].sigma_x));
			txtSecGBy.setText(String.valueOf(filterSpecs[1].sigma_y));
			txtSecGBz.setText(String.valueOf(filterSpecs[1].sigma_z));
			txtSecDMx.setText(String.valueOf(filterSpecs[1].radius_x));
			txtSecDMy.setText(String.valueOf(filterSpecs[1].radius_y));
			txtSecDMz.setText(String.valueOf(filterSpecs[1].radius_z));
			txtSecThreshold.setText(String.valueOf(filterSpecs[1].greaterConstant));
		}
		if(filterSpecs[2] != null){
			txtTertGBx.setText(String.valueOf(filterSpecs[2].sigma_x));
			txtTertGBy.setText(String.valueOf(filterSpecs[2].sigma_y));
			txtTertGBz.setText(String.valueOf(filterSpecs[2].sigma_z));
			txtTertDMx.setText(String.valueOf(filterSpecs[2].radius_x));
			txtTertDMy.setText(String.valueOf(filterSpecs[2].radius_y));
			txtTertDMz.setText(String.valueOf(filterSpecs[2].radius_z));
			txtTertThreshold.setText(String.valueOf(filterSpecs[2].greaterConstant));
		}
	}

	void setFilterParameters(){
		// TODO: Delcare textboxes programmatically
		filterSpecs[0].sigma_x = Double.parseDouble(txtPriGBx.getText());
		filterSpecs[0].sigma_y = Double.parseDouble(txtPriGBy.getText());
		filterSpecs[0].sigma_z = Double.parseDouble(txtPriGBz.getText());
		filterSpecs[1].sigma_x = Double.parseDouble(txtSecGBx.getText());
		filterSpecs[1].sigma_y = Double.parseDouble(txtSecGBy.getText());
		filterSpecs[1].sigma_z = Double.parseDouble(txtSecGBz.getText());
		filterSpecs[2].sigma_x = Double.parseDouble(txtTertGBx.getText());
		filterSpecs[2].sigma_y = Double.parseDouble(txtTertGBy.getText());
		filterSpecs[2].sigma_z = Double.parseDouble(txtTertGBz.getText());

		filterSpecs[0].radius_x = Double.parseDouble(txtPriDMx.getText());
		filterSpecs[0].radius_y = Double.parseDouble(txtPriDMy.getText());
		filterSpecs[0].radius_z = Double.parseDouble(txtPriDMz.getText());
		filterSpecs[1].radius_x = Double.parseDouble(txtSecDMx.getText());
		filterSpecs[1].radius_y = Double.parseDouble(txtSecDMy.getText());
		filterSpecs[1].radius_z = Double.parseDouble(txtSecDMz.getText());
		filterSpecs[2].radius_x = Double.parseDouble(txtTertDMx.getText());
		filterSpecs[2].radius_y = Double.parseDouble(txtTertDMy.getText());
		filterSpecs[2].radius_z = Double.parseDouble(txtTertDMz.getText());

		filterSpecs[0].greaterConstant = Double.parseDouble(txtPriThreshold.getText());
		filterSpecs[1].greaterConstant = Double.parseDouble(txtSecThreshold.getText());
		filterSpecs[2].greaterConstant = Double.parseDouble(txtTertThreshold.getText());
	}

	/**
	 * Construct the speckle gui.
	 */
	
	public SpeckleView() {

		setTitle("FOCUST: Speckle Analysis");
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(SpeckleView.class.getResource("/icon2.png")));
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
				String iDir = IJ.getDir("Select an Input Directory");
				txtInputDir.setText(iDir.toString());
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

		txtPriGBx = new JTextField();
		txtPriGBx.setColumns(10);
		txtPriGBx.setBackground(new Color(211, 211, 211));
		txtPriGBx.setBounds(20, 73, 41, 20);
		PrimaryObjectPanel.add(txtPriGBx);

		JLabel lblX = new JLabel("x");
		lblX.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX.setBounds(10, 69, 21, 29);
		PrimaryObjectPanel.add(lblX);

		JLabel lblY = new JLabel("y");
		lblY.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY.setBounds(65, 69, 21, 29);
		PrimaryObjectPanel.add(lblY);

		txtPriGBy = new JTextField();
		txtPriGBy.setColumns(10);
		txtPriGBy.setBackground(new Color(211, 211, 211));
		txtPriGBy.setBounds(75, 73, 41, 20);
		PrimaryObjectPanel.add(txtPriGBy);

		JLabel lblZ = new JLabel("z");
		lblZ.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ.setBounds(120, 69, 21, 29);
		PrimaryObjectPanel.add(lblZ);

		txtPriGBz = new JTextField();
		txtPriGBz.setColumns(10);
		txtPriGBz.setBackground(new Color(211, 211, 211));
		txtPriGBz.setBounds(130, 73, 41, 20);
		PrimaryObjectPanel.add(txtPriGBz);

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

		txtPriDMx = new JTextField();
		txtPriDMx.setColumns(10);
		txtPriDMx.setBackground(new Color(211, 211, 211));
		txtPriDMx.setBounds(20, 121, 41, 20);
		PrimaryObjectPanel.add(txtPriDMx);

		JLabel lblY_1 = new JLabel("y");
		lblY_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_1.setBounds(65, 117, 21, 29);
		PrimaryObjectPanel.add(lblY_1);

		txtPriDMy = new JTextField();
		txtPriDMy.setColumns(10);
		txtPriDMy.setBackground(new Color(211, 211, 211));
		txtPriDMy.setBounds(75, 121, 41, 20);
		PrimaryObjectPanel.add(txtPriDMy);

		JLabel lblZ_1 = new JLabel("z");
		lblZ_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_1.setBounds(120, 117, 21, 29);
		PrimaryObjectPanel.add(lblZ_1);

		txtPriDMz = new JTextField();
		txtPriDMz.setColumns(10);
		txtPriDMz.setBackground(new Color(211, 211, 211));
		txtPriDMz.setBounds(130, 121, 41, 20);
		PrimaryObjectPanel.add(txtPriDMz);

		JLabel lblThreshold = new JLabel("Threshold:");
		lblThreshold.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblThreshold.setBounds(10, 144, 76, 29);
		PrimaryObjectPanel.add(lblThreshold);

		txtPriThreshold = new JTextField();
		txtPriThreshold.setColumns(10);
		txtPriThreshold.setBackground(new Color(211, 211, 211));
		txtPriThreshold.setBounds(76, 148, 55, 20);
		PrimaryObjectPanel.add(txtPriThreshold);

		JLabel lblWhichChannel = new JLabel("Which channel? ");
		lblWhichChannel.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblWhichChannel.setBounds(339, 217, 106, 29);
		paneSpeckle.add(lblWhichChannel);

		JComboBox cbChannelPrimary = new JComboBox();
		cbChannelPrimary.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4" }));
		cbChannelPrimary.setSelectedIndex(0);
		cbChannelPrimary.setMaximumRowCount(4);
		cbChannelPrimary.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelPrimary.setBounds(452, 219, 48, 25);
		paneSpeckle.add(cbChannelPrimary);

		JPanel pnlSegmentation = new JPanel();
		pnlSegmentation.setLayout(null);
		pnlSegmentation.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		pnlSegmentation.setBounds(309, 189, 639, 255);
		paneSpeckle.add(pnlSegmentation);

		JPanel TertiaryObjectPanel = new JPanel();
		TertiaryObjectPanel.setLayout(null);
		TertiaryObjectPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		TertiaryObjectPanel.setBackground(new Color(211, 211, 211));
		TertiaryObjectPanel.setBounds(435, 61, 187, 182);
		pnlSegmentation.add(TertiaryObjectPanel);

		JLabel lblBgSubSecondary_1 = new JLabel("Background subtraction?");
		lblBgSubSecondary_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBgSubSecondary_1.setBounds(10, 0, 161, 29);
		TertiaryObjectPanel.add(lblBgSubSecondary_1);

		JRadioButton rdbtnNewRadioButton_1_1_1 = new JRadioButton("No");
		rdbtnNewRadioButton_1_1_1.setSelected(true);
		rdbtnNewRadioButton_1_1_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rdbtnNewRadioButton_1_1_1.setBackground(new Color(211, 211, 211));
		rdbtnNewRadioButton_1_1_1.setBounds(10, 24, 43, 23);
		TertiaryObjectPanel.add(rdbtnNewRadioButton_1_1_1);

		JRadioButton rdbtnYes_1_1_1 = new JRadioButton("Yes");
		rdbtnYes_1_1_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rdbtnYes_1_1_1.setEnabled(false);
		rdbtnYes_1_1_1.setBackground(new Color(211, 211, 211));
		rdbtnYes_1_1_1.setBounds(50, 24, 48, 23);
		TertiaryObjectPanel.add(rdbtnYes_1_1_1);

		JLabel lblGaussianBlurSecondary_1 = new JLabel("3D Gaussian blur:");
		lblGaussianBlurSecondary_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblGaussianBlurSecondary_1.setBounds(10, 47, 161, 29);
		TertiaryObjectPanel.add(lblGaussianBlurSecondary_1);

		JLabel lblRadius_1_1 = new JLabel("Radius:\r\n");
		lblRadius_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblRadius_1_1.setEnabled(false);
		lblRadius_1_1.setBounds(98, 21, 48, 29);
		TertiaryObjectPanel.add(lblRadius_1_1);

		txtTertGBx = new JTextField();
		txtTertGBx.setColumns(10);
		txtTertGBx.setBackground(new Color(211, 211, 211));
		txtTertGBx.setBounds(20, 73, 41, 20);
		TertiaryObjectPanel.add(txtTertGBx);

		JLabel lblX_2_1 = new JLabel("x");
		lblX_2_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_2_1.setBounds(10, 69, 21, 29);
		TertiaryObjectPanel.add(lblX_2_1);

		JLabel lblY_2_1 = new JLabel("y");
		lblY_2_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_2_1.setBounds(65, 69, 21, 29);
		TertiaryObjectPanel.add(lblY_2_1);

		txtTertGBy = new JTextField();
		txtTertGBy.setColumns(10);
		txtTertGBy.setBackground(new Color(211, 211, 211));
		txtTertGBy.setBounds(75, 73, 41, 20);
		TertiaryObjectPanel.add(txtTertGBy);

		JLabel lblZ_2_1 = new JLabel("z");
		lblZ_2_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_2_1.setBounds(120, 69, 21, 29);
		TertiaryObjectPanel.add(lblZ_2_1);

		txtTertGBz = new JTextField();
		txtTertGBz.setColumns(10);
		txtTertGBz.setBackground(new Color(211, 211, 211));
		txtTertGBz.setBounds(130, 73, 41, 20);
		TertiaryObjectPanel.add(txtTertGBz);

		txtSpeckleTertiaryBG = new JTextField();
		txtSpeckleTertiaryBG.setEnabled(false);
		txtSpeckleTertiaryBG.setColumns(10);
		txtSpeckleTertiaryBG.setBackground(new Color(211, 211, 211));
		txtSpeckleTertiaryBG.setBounds(145, 25, 31, 20);
		TertiaryObjectPanel.add(txtSpeckleTertiaryBG);

		JLabel lblDetectMaximaSecondary_1 = new JLabel("3D detect maxima radius:");
		lblDetectMaximaSecondary_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblDetectMaximaSecondary_1.setBounds(10, 96, 161, 29);
		TertiaryObjectPanel.add(lblDetectMaximaSecondary_1);

		JLabel lblX_1_1_1 = new JLabel("x");
		lblX_1_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_1_1_1.setBounds(10, 117, 21, 29);
		TertiaryObjectPanel.add(lblX_1_1_1);

		txtTertDMx = new JTextField();
		txtTertDMx.setColumns(10);
		txtTertDMx.setBackground(new Color(211, 211, 211));
		txtTertDMx.setBounds(20, 121, 41, 20);
		TertiaryObjectPanel.add(txtTertDMx);

		JLabel lblY_1_1_1 = new JLabel("y");
		lblY_1_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_1_1_1.setBounds(65, 117, 21, 29);
		TertiaryObjectPanel.add(lblY_1_1_1);

		txtTertDMy = new JTextField();
		txtTertDMy.setColumns(10);
		txtTertDMy.setBackground(new Color(211, 211, 211));
		txtTertDMy.setBounds(75, 121, 41, 20);
		TertiaryObjectPanel.add(txtTertDMy);

		JLabel lblZ_1_1_1 = new JLabel("z");
		lblZ_1_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_1_1_1.setBounds(120, 117, 21, 29);
		TertiaryObjectPanel.add(lblZ_1_1_1);

		txtTertDMz = new JTextField();
		txtTertDMz.setColumns(10);
		txtTertDMz.setBackground(new Color(211, 211, 211));
		txtTertDMz.setBounds(130, 121, 41, 20);
		TertiaryObjectPanel.add(txtTertDMz);

		JLabel lblThreshold_1_1 = new JLabel("Threshold:");
		lblThreshold_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblThreshold_1_1.setBounds(10, 144, 76, 29);
		TertiaryObjectPanel.add(lblThreshold_1_1);

		txtTertThreshold = new JTextField();
		txtTertThreshold.setColumns(10);
		txtTertThreshold.setBackground(new Color(211, 211, 211));
		txtTertThreshold.setBounds(76, 148, 55, 20);
		TertiaryObjectPanel.add(txtTertThreshold);

		JLabel lblWhichChannel_1_1 = new JLabel("Which channel? ");
		lblWhichChannel_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblWhichChannel_1_1.setBounds(448, 28, 106, 29);
		pnlSegmentation.add(lblWhichChannel_1_1);

		JComboBox cbChannelTertiary = new JComboBox();
		cbChannelTertiary.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4" }));
		cbChannelTertiary.setSelectedIndex(2);
		cbChannelTertiary.setMaximumRowCount(4);
		cbChannelTertiary.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelTertiary.setBounds(561, 30, 48, 25);
		pnlSegmentation.add(cbChannelTertiary);

		JPanel SecondaryObjectPanel = new JPanel();
		SecondaryObjectPanel.setBounds(223, 61, 187, 182);
		pnlSegmentation.add(SecondaryObjectPanel);
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

		txtSecGBx = new JTextField();
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
		txtSecGBy.setColumns(10);
		txtSecGBy.setBackground(new Color(211, 211, 211));
		txtSecGBy.setBounds(75, 73, 41, 20);
		SecondaryObjectPanel.add(txtSecGBy);

		JLabel lblZ_2 = new JLabel("z");
		lblZ_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_2.setBounds(120, 69, 21, 29);
		SecondaryObjectPanel.add(lblZ_2);

		txtSecGBz = new JTextField();
		txtSecGBz.setColumns(10);
		txtSecGBz.setBackground(new Color(211, 211, 211));
		txtSecGBz.setBounds(130, 73, 41, 20);
		SecondaryObjectPanel.add(txtSecGBz);

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

		txtSecDMx = new JTextField();
		txtSecDMx.setColumns(10);
		txtSecDMx.setBackground(new Color(211, 211, 211));
		txtSecDMx.setBounds(20, 121, 41, 20);
		SecondaryObjectPanel.add(txtSecDMx);

		JLabel lblY_1_1 = new JLabel("y");
		lblY_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_1_1.setBounds(65, 117, 21, 29);
		SecondaryObjectPanel.add(lblY_1_1);

		txtSecDMy = new JTextField();
		txtSecDMy.setColumns(10);
		txtSecDMy.setBackground(new Color(211, 211, 211));
		txtSecDMy.setBounds(75, 121, 41, 20);
		SecondaryObjectPanel.add(txtSecDMy);

		JLabel lblZ_1_1 = new JLabel("z");
		lblZ_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_1_1.setBounds(120, 117, 21, 29);
		SecondaryObjectPanel.add(lblZ_1_1);

		txtSecDMz = new JTextField();
		txtSecDMz.setColumns(10);
		txtSecDMz.setBackground(new Color(211, 211, 211));
		txtSecDMz.setBounds(130, 121, 41, 20);
		SecondaryObjectPanel.add(txtSecDMz);

		JLabel lblThreshold_1 = new JLabel("Threshold:");
		lblThreshold_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblThreshold_1.setBounds(10, 144, 76, 29);
		SecondaryObjectPanel.add(lblThreshold_1);

		txtSecThreshold = new JTextField();
		txtSecThreshold.setColumns(10);
		txtSecThreshold.setBackground(new Color(211, 211, 211));
		txtSecThreshold.setBounds(76, 148, 55, 20);
		SecondaryObjectPanel.add(txtSecThreshold);

		JLabel lblSegmentSecondary = new JLabel("Secondary Object");
		lblSegmentSecondary.setBounds(223, 3, 187, 29);
		pnlSegmentation.add(lblSegmentSecondary);
		lblSegmentSecondary.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegmentSecondary.setFont(new Font("Gadugi", Font.BOLD, 14));

		JLabel lblWhichChannel_1 = new JLabel("Which channel? ");
		lblWhichChannel_1.setBounds(236, 28, 106, 29);
		pnlSegmentation.add(lblWhichChannel_1);
		lblWhichChannel_1.setFont(new Font("Gadugi", Font.PLAIN, 14));

		JComboBox cbChannelSecondary = new JComboBox();
		cbChannelSecondary.setBounds(349, 30, 48, 25);
		pnlSegmentation.add(cbChannelSecondary);
		cbChannelSecondary.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4" }));
		cbChannelSecondary.setSelectedIndex(1);
		cbChannelSecondary.setMaximumRowCount(4);
		cbChannelSecondary.setFont(new Font("Gadugi", Font.PLAIN, 13));
		
		JCheckBox cbTertiaryObject = new JCheckBox("Tertiary Object");
		cbTertiaryObject.setSelected(true);
		cbTertiaryObject.setBounds(435, 6, 187, 23);
		pnlSegmentation.add(cbTertiaryObject);
		cbTertiaryObject.setHorizontalAlignment(SwingConstants.CENTER);
		cbTertiaryObject.setToolTipText("Toggle the processing of an additional speckle channel on/off.");
		cbTertiaryObject.setFont(new Font("Gadugi", Font.BOLD, 14));
		
		JPanel pnlSegmentationHeader = new JPanel();
		pnlSegmentationHeader.setBounds(546, 157, 216, 29);
		paneSpeckle.add(pnlSegmentationHeader);
		pnlSegmentationHeader.setLayout(null);

		JLabel lblSegmentationParameters = new JLabel("Segmentation Parameters");
		lblSegmentationParameters.setBounds(0, 0, 216, 29);
		pnlSegmentationHeader.add(lblSegmentationParameters);
		lblSegmentationParameters.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegmentationParameters.setFont(new Font("Gadugi", Font.BOLD, 14));

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
		btnRunAnalysis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!cbAnalysisMode.isSelected()) {
					setFilterParameters();
				}
				
				killBordersText = GuiHelper.getSelectedButton(btngrpKillBorders);
				channel2Name = txtC2Name.getText();
				channel3Name = txtC3Name.getText();
				channel4Name = txtC4Name.getText();
				inputDir = txtInputDir.getText();
				primaryChannelChoice = cbChannelPrimary.getSelectedIndex();
				secondaryChannelChoice = cbChannelSecondary.getSelectedIndex();
				tertiaryChannelChoice = cbChannelTertiary.getSelectedIndex();
				groupingInfo = txtGroupingName.getText();
				
				
				
				Segment segment = new Segment(filterSpecs);
				segment.processSpeckles(cbAnalysisMode.isSelected(), inputDir);
				
			}
		});
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
		txtC3Name.setColumns(10);
		txtC3Name.setBounds(123, 259, 176, 29);
		paneSpeckle.add(txtC3Name);

		txtC4Name = new JTextField();
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
		btnLoadConfigSingleCell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String iFile = IJ.getFilePath("Select a parameter file.");
				loadParameterValues(iFile);
				setFilterParameterUIValues();
			}
		});
		paneSpeckle.add(btnLoadConfigSingleCell);

		cbAnalysisMode.setToolTipText("Runs analysis where the user provides labelled and original images.");
		cbAnalysisMode.setSelected(true);
		cbAnalysisMode.setFont(new Font("Gadugi", Font.PLAIN, 14));
		cbAnalysisMode.setBounds(18, 104, 157, 23);
		paneSpeckle.add(cbAnalysisMode);
		
		JLabel lblKillBorders = new JLabel("Kill Borders? ");
		lblKillBorders.setToolTipText("Selecting yes will save output files to the specified directory.");
		lblKillBorders.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblKillBorders.setBounds(13, 192, 96, 29);
		paneSpeckle.add(lblKillBorders);
		
		JRadioButton rbKillBordersNO = new JRadioButton("No");
		rbKillBordersNO.setSelected(true);
		btngrpKillBorders.add(rbKillBordersNO);
		rbKillBordersNO.setBounds(97, 195, 54, 23);
		paneSpeckle.add(rbKillBordersNO);
		
		JRadioButton rdKillBordersXY = new JRadioButton("X + Y");
		btngrpKillBorders.add(rdKillBordersXY);
		rdKillBordersXY.setBounds(147, 195, 54, 23);
		paneSpeckle.add(rdKillBordersXY);
		
		JRadioButton rbKillBordersXYZ = new JRadioButton("X + Y + Z");
		btngrpKillBorders.add(rbKillBordersXYZ);
		rbKillBordersXYZ.setBounds(213, 195, 84, 23);
		paneSpeckle.add(rbKillBordersXYZ);
	}
}
