package ui.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.DefaultComboBoxModel;
import java.awt.Component;
import javax.swing.Box;

public class SpeckleView extends JFrame {

	private JPanel paneSpeckle;
	private JTextField txtInputDir;
	private JTextField txtOutputDir;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField txtSpeckle;
	private JTextField textField_17;
	private JTextField textField_18;
	private JTextField textField_19;
	private JTextField textField_16;
	private JTextField textField_20;
	private JTextField textField_21;
	private JTextField textField_22;
	private JTextField textField_23;
	private JTextField textField_24;
	private JTextField textField_25;
	private JTextField textField_26;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpeckleView frame = new SpeckleView();
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
	public SpeckleView() {
		setTitle("FOCUST: Speckle Analysis");
		setIconImage(Toolkit.getDefaultToolkit().getImage(SpeckleView.class.getResource("/ui/resources/icon2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 973, 485);
		paneSpeckle = new JPanel();
		paneSpeckle.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(paneSpeckle);
		paneSpeckle.setLayout(null);
		
		JLabel lblSelectAnInput = new JLabel("Select an input directory:");
		lblSelectAnInput.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblSelectAnInput.setBounds(18, 28, 167, 29);
		paneSpeckle.add(lblSelectAnInput);
		
		JButton btnInputDir = new JButton("Browse");
		btnInputDir.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnInputDir.setBounds(191, 28, 96, 29);
		paneSpeckle.add(btnInputDir);
		
		JLabel lblSeperateOutputDirectory = new JLabel("Seperate output directory?");
		lblSeperateOutputDirectory.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblSeperateOutputDirectory.setBounds(18, 68, 167, 29);
		paneSpeckle.add(lblSeperateOutputDirectory);
		
		JRadioButton rbOutputDirNo = new JRadioButton("No");
		rbOutputDirNo.setSelected(true);
		rbOutputDirNo.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbOutputDirNo.setBounds(191, 71, 48, 23);
		paneSpeckle.add(rbOutputDirNo);
		
		JRadioButton rbOutputDirYes = new JRadioButton("Yes");
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
		separator.setBounds(10, 108, 938, 2);
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
		lblHowManyChannels.setBounds(22, 117, 237, 29);
		paneSpeckle.add(lblHowManyChannels);
		
		JComboBox cbChannelTotal = new JComboBox();
		cbChannelTotal.setModel(new DefaultComboBoxModel(new String[] {"2", "3", "4"}));
		cbChannelTotal.setMaximumRowCount(3);
		cbChannelTotal.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelTotal.setBounds(254, 119, 37, 25);
		paneSpeckle.add(cbChannelTotal);
		
		JLabel lblSegmentPrimary = new JLabel("Primary Object\r\n");
		lblSegmentPrimary.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegmentPrimary.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblSegmentPrimary.setBounds(324, 153, 187, 29);
		paneSpeckle.add(lblSegmentPrimary);
		
		JPanel PrimaryObjectPanel = new JPanel();
		PrimaryObjectPanel.setLayout(null);
		PrimaryObjectPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		PrimaryObjectPanel.setBackground(new Color(211, 211, 211));
		PrimaryObjectPanel.setBounds(324, 211, 187, 182);
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
		lblRadius.setBounds(98, 21, 48, 29);
		PrimaryObjectPanel.add(lblRadius);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBackground(new Color(211, 211, 211));
		textField.setBounds(20, 73, 41, 20);
		PrimaryObjectPanel.add(textField);
		
		JLabel lblX = new JLabel("x");
		lblX.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX.setBounds(10, 69, 21, 29);
		PrimaryObjectPanel.add(lblX);
		
		JLabel lblY = new JLabel("y");
		lblY.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY.setBounds(65, 69, 21, 29);
		PrimaryObjectPanel.add(lblY);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBackground(new Color(211, 211, 211));
		textField_1.setBounds(75, 73, 41, 20);
		PrimaryObjectPanel.add(textField_1);
		
		JLabel lblZ = new JLabel("z");
		lblZ.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ.setBounds(120, 69, 21, 29);
		PrimaryObjectPanel.add(lblZ);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBackground(new Color(211, 211, 211));
		textField_2.setBounds(130, 73, 41, 20);
		PrimaryObjectPanel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setEnabled(false);
		textField_3.setColumns(10);
		textField_3.setBackground(new Color(211, 211, 211));
		textField_3.setBounds(145, 25, 31, 20);
		PrimaryObjectPanel.add(textField_3);
		
		JLabel lblDetectMaximaPrimary = new JLabel("3D detect maxima radius:");
		lblDetectMaximaPrimary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblDetectMaximaPrimary.setBounds(10, 96, 161, 29);
		PrimaryObjectPanel.add(lblDetectMaximaPrimary);
		
		JLabel lblX_1 = new JLabel("x");
		lblX_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_1.setBounds(10, 117, 21, 29);
		PrimaryObjectPanel.add(lblX_1);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBackground(new Color(211, 211, 211));
		textField_4.setBounds(20, 121, 41, 20);
		PrimaryObjectPanel.add(textField_4);
		
		JLabel lblY_1 = new JLabel("y");
		lblY_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_1.setBounds(65, 117, 21, 29);
		PrimaryObjectPanel.add(lblY_1);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBackground(new Color(211, 211, 211));
		textField_5.setBounds(75, 121, 41, 20);
		PrimaryObjectPanel.add(textField_5);
		
		JLabel lblZ_1 = new JLabel("z");
		lblZ_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_1.setBounds(120, 117, 21, 29);
		PrimaryObjectPanel.add(lblZ_1);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBackground(new Color(211, 211, 211));
		textField_6.setBounds(130, 121, 41, 20);
		PrimaryObjectPanel.add(textField_6);
		
		JLabel lblThreshold = new JLabel("Threshold:");
		lblThreshold.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblThreshold.setBounds(10, 144, 76, 29);
		PrimaryObjectPanel.add(lblThreshold);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBackground(new Color(211, 211, 211));
		textField_7.setBounds(76, 148, 55, 20);
		PrimaryObjectPanel.add(textField_7);
		
		JLabel lblWhichChannel = new JLabel("Which channel? ");
		lblWhichChannel.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblWhichChannel.setBounds(339, 178, 106, 29);
		paneSpeckle.add(lblWhichChannel);
		
		JComboBox cbChannelPrimary = new JComboBox();
		cbChannelPrimary.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbChannelPrimary.setMaximumRowCount(4);
		cbChannelPrimary.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelPrimary.setBounds(452, 180, 37, 25);
		paneSpeckle.add(cbChannelPrimary);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(309, 150, 639, 255);
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
		
		textField_16 = new JTextField();
		textField_16.setColumns(10);
		textField_16.setBackground(new Color(211, 211, 211));
		textField_16.setBounds(20, 73, 41, 20);
		SecondaryObjectPanel_1.add(textField_16);
		
		JLabel lblX_2_1 = new JLabel("x");
		lblX_2_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_2_1.setBounds(10, 69, 21, 29);
		SecondaryObjectPanel_1.add(lblX_2_1);
		
		JLabel lblY_2_1 = new JLabel("y");
		lblY_2_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_2_1.setBounds(65, 69, 21, 29);
		SecondaryObjectPanel_1.add(lblY_2_1);
		
		textField_20 = new JTextField();
		textField_20.setColumns(10);
		textField_20.setBackground(new Color(211, 211, 211));
		textField_20.setBounds(75, 73, 41, 20);
		SecondaryObjectPanel_1.add(textField_20);
		
		JLabel lblZ_2_1 = new JLabel("z");
		lblZ_2_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_2_1.setBounds(120, 69, 21, 29);
		SecondaryObjectPanel_1.add(lblZ_2_1);
		
		textField_21 = new JTextField();
		textField_21.setColumns(10);
		textField_21.setBackground(new Color(211, 211, 211));
		textField_21.setBounds(130, 73, 41, 20);
		SecondaryObjectPanel_1.add(textField_21);
		
		textField_22 = new JTextField();
		textField_22.setEnabled(false);
		textField_22.setColumns(10);
		textField_22.setBackground(new Color(211, 211, 211));
		textField_22.setBounds(145, 25, 31, 20);
		SecondaryObjectPanel_1.add(textField_22);
		
		JLabel lblDetectMaximaSecondary_1 = new JLabel("3D detect maxima radius:");
		lblDetectMaximaSecondary_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblDetectMaximaSecondary_1.setBounds(10, 96, 161, 29);
		SecondaryObjectPanel_1.add(lblDetectMaximaSecondary_1);
		
		JLabel lblX_1_1_1 = new JLabel("x");
		lblX_1_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_1_1_1.setBounds(10, 117, 21, 29);
		SecondaryObjectPanel_1.add(lblX_1_1_1);
		
		textField_23 = new JTextField();
		textField_23.setColumns(10);
		textField_23.setBackground(new Color(211, 211, 211));
		textField_23.setBounds(20, 121, 41, 20);
		SecondaryObjectPanel_1.add(textField_23);
		
		JLabel lblY_1_1_1 = new JLabel("y");
		lblY_1_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_1_1_1.setBounds(65, 117, 21, 29);
		SecondaryObjectPanel_1.add(lblY_1_1_1);
		
		textField_24 = new JTextField();
		textField_24.setColumns(10);
		textField_24.setBackground(new Color(211, 211, 211));
		textField_24.setBounds(75, 121, 41, 20);
		SecondaryObjectPanel_1.add(textField_24);
		
		JLabel lblZ_1_1_1 = new JLabel("z");
		lblZ_1_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_1_1_1.setBounds(120, 117, 21, 29);
		SecondaryObjectPanel_1.add(lblZ_1_1_1);
		
		textField_25 = new JTextField();
		textField_25.setColumns(10);
		textField_25.setBackground(new Color(211, 211, 211));
		textField_25.setBounds(130, 121, 41, 20);
		SecondaryObjectPanel_1.add(textField_25);
		
		JLabel lblThreshold_1_1 = new JLabel("Threshold:");
		lblThreshold_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblThreshold_1_1.setBounds(10, 144, 76, 29);
		SecondaryObjectPanel_1.add(lblThreshold_1_1);
		
		textField_26 = new JTextField();
		textField_26.setColumns(10);
		textField_26.setBackground(new Color(211, 211, 211));
		textField_26.setBounds(76, 148, 55, 20);
		SecondaryObjectPanel_1.add(textField_26);
		
		JLabel lblWhichChannel_1_1 = new JLabel("Which channel? ");
		lblWhichChannel_1_1.setEnabled(false);
		lblWhichChannel_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblWhichChannel_1_1.setBounds(448, 28, 106, 29);
		panel.add(lblWhichChannel_1_1);
		
		JComboBox cbChannelSecondary_1 = new JComboBox();
		cbChannelSecondary_1.setEnabled(false);
		cbChannelSecondary_1.setSelectedIndex(3);
		cbChannelSecondary_1.setMaximumRowCount(4);
		cbChannelSecondary_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelSecondary_1.setBounds(561, 30, 37, 25);
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
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBackground(new Color(211, 211, 211));
		textField_8.setBounds(20, 73, 41, 20);
		SecondaryObjectPanel.add(textField_8);
		
		JLabel lblX_2 = new JLabel("x");
		lblX_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_2.setBounds(10, 69, 21, 29);
		SecondaryObjectPanel.add(lblX_2);
		
		JLabel lblY_2 = new JLabel("y");
		lblY_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_2.setBounds(65, 69, 21, 29);
		SecondaryObjectPanel.add(lblY_2);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBackground(new Color(211, 211, 211));
		textField_9.setBounds(75, 73, 41, 20);
		SecondaryObjectPanel.add(textField_9);
		
		JLabel lblZ_2 = new JLabel("z");
		lblZ_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_2.setBounds(120, 69, 21, 29);
		SecondaryObjectPanel.add(lblZ_2);
		
		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBackground(new Color(211, 211, 211));
		textField_10.setBounds(130, 73, 41, 20);
		SecondaryObjectPanel.add(textField_10);
		
		textField_11 = new JTextField();
		textField_11.setEnabled(false);
		textField_11.setColumns(10);
		textField_11.setBackground(new Color(211, 211, 211));
		textField_11.setBounds(145, 25, 31, 20);
		SecondaryObjectPanel.add(textField_11);
		
		JLabel lblDetectMaximaSecondary = new JLabel("3D detect maxima radius:");
		lblDetectMaximaSecondary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblDetectMaximaSecondary.setBounds(10, 96, 161, 29);
		SecondaryObjectPanel.add(lblDetectMaximaSecondary);
		
		JLabel lblX_1_1 = new JLabel("x");
		lblX_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_1_1.setBounds(10, 117, 21, 29);
		SecondaryObjectPanel.add(lblX_1_1);
		
		textField_12 = new JTextField();
		textField_12.setColumns(10);
		textField_12.setBackground(new Color(211, 211, 211));
		textField_12.setBounds(20, 121, 41, 20);
		SecondaryObjectPanel.add(textField_12);
		
		JLabel lblY_1_1 = new JLabel("y");
		lblY_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_1_1.setBounds(65, 117, 21, 29);
		SecondaryObjectPanel.add(lblY_1_1);
		
		textField_13 = new JTextField();
		textField_13.setColumns(10);
		textField_13.setBackground(new Color(211, 211, 211));
		textField_13.setBounds(75, 121, 41, 20);
		SecondaryObjectPanel.add(textField_13);
		
		JLabel lblZ_1_1 = new JLabel("z");
		lblZ_1_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_1_1.setBounds(120, 117, 21, 29);
		SecondaryObjectPanel.add(lblZ_1_1);
		
		textField_14 = new JTextField();
		textField_14.setColumns(10);
		textField_14.setBackground(new Color(211, 211, 211));
		textField_14.setBounds(130, 121, 41, 20);
		SecondaryObjectPanel.add(textField_14);
		
		JLabel lblThreshold_1 = new JLabel("Threshold:");
		lblThreshold_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblThreshold_1.setBounds(10, 144, 76, 29);
		SecondaryObjectPanel.add(lblThreshold_1);
		
		textField_15 = new JTextField();
		textField_15.setColumns(10);
		textField_15.setBackground(new Color(211, 211, 211));
		textField_15.setBounds(76, 148, 55, 20);
		SecondaryObjectPanel.add(textField_15);
		
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
		cbChannelSecondary.setBounds(349, 30, 37, 25);
		panel.add(cbChannelSecondary);
		cbChannelSecondary.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbChannelSecondary.setSelectedIndex(3);
		cbChannelSecondary.setMaximumRowCount(4);
		cbChannelSecondary.setFont(new Font("Gadugi", Font.PLAIN, 13));
		
		JLabel lblSegmentationParameters = new JLabel("Segmentation Parameters");
		lblSegmentationParameters.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegmentationParameters.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblSegmentationParameters.setBounds(546, 118, 216, 29);
		paneSpeckle.add(lblSegmentationParameters);
		
		JButton btnBackToMenu = new JButton("Back to Menu");
		btnBackToMenu.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnBackToMenu.setBounds(12, 373, 133, 29);
		paneSpeckle.add(btnBackToMenu);
		
		JButton btnRunAnalysis = new JButton("Run Analysis");
		btnRunAnalysis.setFont(new Font("Gadugi", Font.BOLD, 14));
		btnRunAnalysis.setBounds(12, 406, 279, 29);
		paneSpeckle.add(btnRunAnalysis);
		
		JLabel lblNameChannel2 = new JLabel("Name Channel 2:");
		lblNameChannel2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblNameChannel2.setBounds(12, 150, 116, 29);
		paneSpeckle.add(lblNameChannel2);
		
		JLabel lblNameChannel3 = new JLabel("Name Channel 3:");
		lblNameChannel3.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblNameChannel3.setBounds(12, 184, 116, 29);
		paneSpeckle.add(lblNameChannel3);
		
		JLabel lblNameChannel4 = new JLabel("Name Channel 4:");
		lblNameChannel4.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblNameChannel4.setBounds(12, 217, 116, 29);
		paneSpeckle.add(lblNameChannel4);
		
		JLabel lblGroupingInfo = new JLabel("Grouping* Info?");
		lblGroupingInfo.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblGroupingInfo.setBounds(12, 257, 116, 29);
		paneSpeckle.add(lblGroupingInfo);
		
		txtSpeckle = new JTextField();
		txtSpeckle.setColumns(10);
		txtSpeckle.setBounds(122, 150, 176, 29);
		paneSpeckle.add(txtSpeckle);
		
		textField_17 = new JTextField();
		textField_17.setColumns(10);
		textField_17.setBounds(122, 184, 176, 29);
		paneSpeckle.add(textField_17);
		
		textField_18 = new JTextField();
		textField_18.setColumns(10);
		textField_18.setBounds(122, 217, 176, 29);
		paneSpeckle.add(textField_18);
		
		textField_19 = new JTextField();
		textField_19.setColumns(10);
		textField_19.setBounds(122, 257, 176, 29);
		paneSpeckle.add(textField_19);
		
		JTextPane txtpnanyConditionsfactorsSpecific = new JTextPane();
		txtpnanyConditionsfactorsSpecific.setText("*Any factors specific to this dataset that you might wish to group data by. For example, control or experimental variables. This will appear as a seperate column alongside all data run within the same batch. ");
		txtpnanyConditionsfactorsSpecific.setFont(new Font("Gadugi", Font.PLAIN, 10));
		txtpnanyConditionsfactorsSpecific.setEditable(false);
		txtpnanyConditionsfactorsSpecific.setBackground(SystemColor.controlHighlight);
		txtpnanyConditionsfactorsSpecific.setBounds(12, 291, 289, 67);
		paneSpeckle.add(txtpnanyConditionsfactorsSpecific);
		
		JButton btnSaveConfigSingleCell = new JButton("Save Parameters");
		btnSaveConfigSingleCell.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnSaveConfigSingleCell.setBounds(609, 406, 156, 29);
		paneSpeckle.add(btnSaveConfigSingleCell);
		
		JButton btnLoadConfigSingleCell = new JButton("Load Parameters");
		btnLoadConfigSingleCell.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnLoadConfigSingleCell.setBounds(443, 406, 156, 29);
		paneSpeckle.add(btnLoadConfigSingleCell);
	}
}
