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
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.DefaultComboBoxModel;

public class SpheroidView extends JFrame {

	 private JPanel paneSpheroid;
	 private JTextField txtInputDir;
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
	 private JTextField textField_16;
	 private JTextField textField_17;
	 private JTextField textField_21;

	 
	/**
	 * construct the spheroid gui.
	 */
	public SpheroidView() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(SpheroidView.class.getResource("/clcm/focust/resources/icon2.png")));
		setTitle("FOCUST: Spheroid Analysis");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 806, 485);
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
		btnBackToMenu.setBounds(10, 373, 122, 29);
		paneSpheroid.add(btnBackToMenu);
		
		JButton btnRunAnalysis = new JButton("Run Analysis");
		btnRunAnalysis.setFont(new Font("Gadugi", Font.BOLD, 14));
		btnRunAnalysis.setBounds(10, 406, 279, 29);
		paneSpheroid.add(btnRunAnalysis);
		
		JLabel lblSelectAnInput = new JLabel("Select an input directory:");
		lblSelectAnInput.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblSelectAnInput.setBounds(20, 28, 167, 29);
		paneSpheroid.add(lblSelectAnInput);
		
		JButton btnInputDir = new JButton("Browse");
		btnInputDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Find and set the input directory.
				FOCUST.FileFinder();
				FOCUST.storeDir = FOCUST.imageFiles[0].getParent();
				
				// update the textbox in spheroid view
				txtInputDir.setText(FOCUST.storeDir);
			}
		});
		btnInputDir.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnInputDir.setBounds(193, 28, 96, 29);
		paneSpheroid.add(btnInputDir);
		
		JLabel lblSeperateOutputDirectory = new JLabel("Seperate output directory?");
		lblSeperateOutputDirectory.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblSeperateOutputDirectory.setBounds(20, 68, 167, 29);
		paneSpheroid.add(lblSeperateOutputDirectory);
		
		JRadioButton rbOutputDirNo = new JRadioButton("No");
		rbOutputDirNo.setSelected(true);
		rbOutputDirNo.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbOutputDirNo.setBounds(193, 71, 48, 23);
		paneSpheroid.add(rbOutputDirNo);
		
		JRadioButton rbOutputDirYes = new JRadioButton("Yes");
		rbOutputDirYes.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbOutputDirYes.setBounds(243, 71, 48, 23);
		paneSpheroid.add(rbOutputDirYes);
		
		JButton btnOutputDir = new JButton("Browse");
		btnOutputDir.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnOutputDir.setEnabled(false);
		btnOutputDir.setBounds(297, 68, 96, 29);
		paneSpheroid.add(btnOutputDir);
		
		txtInputDir = new JTextField();
		txtInputDir.setColumns(10);
		txtInputDir.setBounds(299, 28, 289, 29);
		paneSpheroid.add(txtInputDir);
		
		textField_1 = new JTextField();
		textField_1.setEnabled(false);
		textField_1.setColumns(10);
		textField_1.setBounds(403, 68, 289, 29);
		paneSpheroid.add(textField_1);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(169, 169, 169));
		separator.setBackground(Color.WHITE);
		separator.setBounds(12, 108, 768, 2);
		paneSpheroid.add(separator);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnHelp.setBounds(684, 11, 96, 29);
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
		lblHowManyChannels.setBounds(20, 121, 237, 29);
		paneSpheroid.add(lblHowManyChannels);
		
		JComboBox cbChannelTotal = new JComboBox();
		cbChannelTotal.setModel(new DefaultComboBoxModel(new String[] {"2", "3", "4"}));
		cbChannelTotal.setSelectedIndex(1);
		cbChannelTotal.setMaximumRowCount(3);
		cbChannelTotal.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelTotal.setBounds(252, 123, 48, 25);
		paneSpheroid.add(cbChannelTotal);
		
		JLabel lblNameChannel2 = new JLabel("Name Channel 2:");
		lblNameChannel2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblNameChannel2.setBounds(10, 154, 116, 29);
		paneSpheroid.add(lblNameChannel2);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(120, 154, 176, 29);
		paneSpheroid.add(textField_2);
		
		JLabel lblNameChannel3 = new JLabel("Name Channel 3:");
		lblNameChannel3.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblNameChannel3.setBounds(10, 188, 116, 29);
		paneSpheroid.add(lblNameChannel3);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(120, 188, 176, 29);
		paneSpheroid.add(textField_3);
		
		JLabel lblNameChannel4 = new JLabel("Name Channel 4:");
		lblNameChannel4.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblNameChannel4.setBounds(10, 221, 116, 29);
		paneSpheroid.add(lblNameChannel4);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(120, 221, 176, 29);
		paneSpheroid.add(textField_4);
		
		JLabel lblGroupingInfo = new JLabel("Grouping* Info?");
		lblGroupingInfo.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblGroupingInfo.setBounds(10, 261, 116, 29);
		paneSpheroid.add(lblGroupingInfo);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(120, 261, 176, 29);
		paneSpheroid.add(textField_5);
		
		JTextPane txtpnanyConditionsfactorsSpecific = new JTextPane();
		txtpnanyConditionsfactorsSpecific.setText("*Any conditions/factors specific to this dataset that you might wish to group data by. For example, control or experimental variables. This will appear as a seperate column alongside all data run within the same batch. ");
		txtpnanyConditionsfactorsSpecific.setFont(new Font("Gadugi", Font.PLAIN, 10));
		txtpnanyConditionsfactorsSpecific.setEditable(false);
		txtpnanyConditionsfactorsSpecific.setBackground(SystemColor.controlHighlight);
		txtpnanyConditionsfactorsSpecific.setBounds(10, 295, 289, 67);
		paneSpheroid.add(txtpnanyConditionsfactorsSpecific);
		
		JLabel lblPrimaryObjects = new JLabel("Primary Objects\r\n");
		lblPrimaryObjects.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrimaryObjects.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblPrimaryObjects.setBounds(336, 153, 187, 29);
		paneSpheroid.add(lblPrimaryObjects);
		
		JLabel lblSegmentSecondary = new JLabel("Secondary Object");
		lblSegmentSecondary.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegmentSecondary.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblSegmentSecondary.setBounds(590, 153, 187, 29);
		paneSpheroid.add(lblSegmentSecondary);
		
		JPanel PrimaryObjectPanel = new JPanel();
		PrimaryObjectPanel.setLayout(null);
		PrimaryObjectPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		PrimaryObjectPanel.setBackground(new Color(211, 211, 211));
		PrimaryObjectPanel.setBounds(336, 211, 187, 182);
		paneSpheroid.add(PrimaryObjectPanel);
		
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
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBackground(new Color(211, 211, 211));
		textField_6.setBounds(20, 73, 41, 20);
		PrimaryObjectPanel.add(textField_6);
		
		JLabel lblX = new JLabel("x");
		lblX.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX.setBounds(10, 69, 21, 29);
		PrimaryObjectPanel.add(lblX);
		
		JLabel lblY = new JLabel("y");
		lblY.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY.setBounds(65, 69, 21, 29);
		PrimaryObjectPanel.add(lblY);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBackground(new Color(211, 211, 211));
		textField_7.setBounds(75, 73, 41, 20);
		PrimaryObjectPanel.add(textField_7);
		
		JLabel lblZ = new JLabel("z");
		lblZ.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ.setBounds(120, 69, 21, 29);
		PrimaryObjectPanel.add(lblZ);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBackground(new Color(211, 211, 211));
		textField_8.setBounds(130, 73, 41, 20);
		PrimaryObjectPanel.add(textField_8);
		
		textField_9 = new JTextField();
		textField_9.setEnabled(false);
		textField_9.setColumns(10);
		textField_9.setBackground(new Color(211, 211, 211));
		textField_9.setBounds(145, 25, 31, 20);
		PrimaryObjectPanel.add(textField_9);
		
		JLabel lblDetectMaximaPrimary = new JLabel("3D detect maxima radius:");
		lblDetectMaximaPrimary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblDetectMaximaPrimary.setBounds(10, 96, 161, 29);
		PrimaryObjectPanel.add(lblDetectMaximaPrimary);
		
		JLabel lblX_1 = new JLabel("x");
		lblX_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_1.setBounds(10, 117, 21, 29);
		PrimaryObjectPanel.add(lblX_1);
		
		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBackground(new Color(211, 211, 211));
		textField_10.setBounds(20, 121, 41, 20);
		PrimaryObjectPanel.add(textField_10);
		
		JLabel lblY_1 = new JLabel("y");
		lblY_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_1.setBounds(65, 117, 21, 29);
		PrimaryObjectPanel.add(lblY_1);
		
		textField_11 = new JTextField();
		textField_11.setColumns(10);
		textField_11.setBackground(new Color(211, 211, 211));
		textField_11.setBounds(75, 121, 41, 20);
		PrimaryObjectPanel.add(textField_11);
		
		JLabel lblZ_1 = new JLabel("z");
		lblZ_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_1.setBounds(120, 117, 21, 29);
		PrimaryObjectPanel.add(lblZ_1);
		
		textField_12 = new JTextField();
		textField_12.setColumns(10);
		textField_12.setBackground(new Color(211, 211, 211));
		textField_12.setBounds(130, 121, 41, 20);
		PrimaryObjectPanel.add(textField_12);
		
		JLabel lblThreshold = new JLabel("Threshold:");
		lblThreshold.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblThreshold.setBounds(10, 144, 76, 29);
		PrimaryObjectPanel.add(lblThreshold);
		
		textField_13 = new JTextField();
		textField_13.setColumns(10);
		textField_13.setBackground(new Color(211, 211, 211));
		textField_13.setBounds(76, 148, 55, 20);
		PrimaryObjectPanel.add(textField_13);
		
		JLabel lblWhichChannel = new JLabel("Which channel? ");
		lblWhichChannel.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblWhichChannel.setBounds(351, 178, 106, 29);
		paneSpheroid.add(lblWhichChannel);
		
		JComboBox cbChannelPrimary = new JComboBox();
		cbChannelPrimary.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbChannelPrimary.setSelectedIndex(0);
		cbChannelPrimary.setMaximumRowCount(4);
		cbChannelPrimary.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelPrimary.setBounds(464, 180, 48, 25);
		paneSpheroid.add(cbChannelPrimary);
		
		JPanel SecondaryObjectPanel = new JPanel();
		SecondaryObjectPanel.setLayout(null);
		SecondaryObjectPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		SecondaryObjectPanel.setBackground(new Color(211, 211, 211));
		SecondaryObjectPanel.setBounds(590, 211, 187, 182);
		paneSpheroid.add(SecondaryObjectPanel);
		
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
		
		textField_14 = new JTextField();
		textField_14.setColumns(10);
		textField_14.setBackground(new Color(211, 211, 211));
		textField_14.setBounds(20, 73, 41, 20);
		SecondaryObjectPanel.add(textField_14);
		
		JLabel lblX_2 = new JLabel("x");
		lblX_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_2.setBounds(10, 69, 21, 29);
		SecondaryObjectPanel.add(lblX_2);
		
		JLabel lblY_2 = new JLabel("y");
		lblY_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_2.setBounds(65, 69, 21, 29);
		SecondaryObjectPanel.add(lblY_2);
		
		textField_15 = new JTextField();
		textField_15.setColumns(10);
		textField_15.setBackground(new Color(211, 211, 211));
		textField_15.setBounds(75, 73, 41, 20);
		SecondaryObjectPanel.add(textField_15);
		
		JLabel lblZ_2 = new JLabel("z");
		lblZ_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_2.setBounds(120, 69, 21, 29);
		SecondaryObjectPanel.add(lblZ_2);
		
		textField_16 = new JTextField();
		textField_16.setColumns(10);
		textField_16.setBackground(new Color(211, 211, 211));
		textField_16.setBounds(130, 73, 41, 20);
		SecondaryObjectPanel.add(textField_16);
		
		textField_17 = new JTextField();
		textField_17.setEnabled(false);
		textField_17.setColumns(10);
		textField_17.setBackground(new Color(211, 211, 211));
		textField_17.setBounds(145, 25, 31, 20);
		SecondaryObjectPanel.add(textField_17);
		
		JLabel lblThreshold_1 = new JLabel("Threshold:");
		lblThreshold_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblThreshold_1.setBounds(10, 104, 76, 29);
		SecondaryObjectPanel.add(lblThreshold_1);
		
		textField_21 = new JTextField();
		textField_21.setColumns(10);
		textField_21.setBackground(new Color(211, 211, 211));
		textField_21.setBounds(76, 108, 55, 20);
		SecondaryObjectPanel.add(textField_21);
		
		JLabel lblWhichChannel_1 = new JLabel("Which channel? ");
		lblWhichChannel_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblWhichChannel_1.setBounds(603, 178, 106, 29);
		paneSpheroid.add(lblWhichChannel_1);
		
		JComboBox cbChannelSecondary = new JComboBox();
		cbChannelSecondary.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbChannelSecondary.setSelectedIndex(3);
		cbChannelSecondary.setMaximumRowCount(4);
		cbChannelSecondary.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelSecondary.setBounds(716, 180, 48, 25);
		paneSpheroid.add(cbChannelSecondary);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(321, 150, 469, 255);
		paneSpheroid.add(panel);
		
		JLabel lblSegmentationParameters = new JLabel("Segmentation Parameters");
		lblSegmentationParameters.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegmentationParameters.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblSegmentationParameters.setBounds(447, 118, 216, 29);
		paneSpheroid.add(lblSegmentationParameters);
		
		JButton btnLoadConfigSingleCell = new JButton("Load Parameters");
		btnLoadConfigSingleCell.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnLoadConfigSingleCell.setBounds(141, 373, 148, 29);
		paneSpheroid.add(btnLoadConfigSingleCell);
	}
}
