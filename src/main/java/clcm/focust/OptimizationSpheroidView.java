package clcm.focust;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OptimizationSpheroidView extends JFrame {

	private JPanel contentPane;
	private JTextField txtInputDir;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OptimizationSpheroidView frame = new OptimizationSpheroidView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Make the spheroid gui
	 */
	public OptimizationSpheroidView() {
		setTitle("FOCUST: Spheroid Optimization");
		setIconImage(Toolkit.getDefaultToolkit().getImage(OptimizationSpheroidView.class.getResource("/clcm/focust/resources/icon2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 975, 771);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(SystemColor.controlShadow);
		separator.setBackground(Color.WHITE);
		separator.setBounds(10, 93, 939, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setForeground(SystemColor.controlShadow);
		separator_1.setBackground(Color.WHITE);
		separator_1.setBounds(478, 95, 2, 626);
		contentPane.add(separator_1);
		
		JLabel lblOptimizeADataset = new JLabel("Optimize a Dataset:");
		lblOptimizeADataset.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblOptimizeADataset.setBounds(10, 11, 144, 29);
		contentPane.add(lblOptimizeADataset);
		
		JLabel lblHereYouCan = new JLabel("Adjust the parameters below to configure the optimal segmentation for a given dataset.");
		lblHereYouCan.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblHereYouCan.setBounds(149, 11, 593, 29);
		contentPane.add(lblHereYouCan);
		
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
		btnBackToMenu.setBounds(710, 11, 133, 29);
		contentPane.add(btnBackToMenu);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnHelp.setBounds(853, 11, 96, 29);
		contentPane.add(btnHelp);
		
		JButton btnSaveParameters = new JButton("Save Parameters");
		btnSaveParameters.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnSaveParameters.setBounds(795, 53, 154, 29);
		contentPane.add(btnSaveParameters);
		
		JButton btnLoadNextImage = new JButton("Load Next Image");
		btnLoadNextImage.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnLoadNextImage.setBounds(588, 53, 154, 29);
		contentPane.add(btnLoadNextImage);
		
		txtInputDir = new JTextField();
		txtInputDir.setColumns(10);
		txtInputDir.setBounds(289, 53, 289, 29);
		contentPane.add(txtInputDir);
		
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
		btnInputDir.setBounds(183, 53, 96, 29);
		contentPane.add(btnInputDir);
		
		JLabel lblSelectAnInput = new JLabel("Select an input directory:");
		lblSelectAnInput.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblSelectAnInput.setBounds(10, 53, 167, 29);
		contentPane.add(lblSelectAnInput);
		
		JLabel lblSegmentation = new JLabel("Primary Object Segmentation:");
		lblSegmentation.setHorizontalAlignment(SwingConstants.LEFT);
		lblSegmentation.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblSegmentation.setBounds(20, 102, 219, 29);
		contentPane.add(lblSegmentation);
		
		JLabel lblWhichChannel = new JLabel("Which channel? ");
		lblWhichChannel.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblWhichChannel.setBounds(231, 102, 106, 29);
		contentPane.add(lblWhichChannel);
		
		JComboBox cbChannelPrimary = new JComboBox();
		cbChannelPrimary.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbChannelPrimary.setSelectedIndex(0);
		cbChannelPrimary.setMaximumRowCount(4);
		cbChannelPrimary.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelPrimary.setBounds(335, 105, 48, 25);
		contentPane.add(cbChannelPrimary);
		
		JLabel lblGaussianBlurPrimary = new JLabel("3D Gaussian blur:");
		lblGaussianBlurPrimary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblGaussianBlurPrimary.setBounds(20, 180, 161, 29);
		contentPane.add(lblGaussianBlurPrimary);
		
		JLabel lblX = new JLabel("x");
		lblX.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX.setBounds(60, 204, 21, 29);
		contentPane.add(lblX);
		
		textField_1 = new JTextField();
		textField_1.setText("2");
		textField_1.setColumns(10);
		textField_1.setBackground(Color.WHITE);
		textField_1.setBounds(70, 208, 41, 20);
		contentPane.add(textField_1);
		
		JLabel lblY = new JLabel("y");
		lblY.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY.setBounds(60, 236, 21, 29);
		contentPane.add(lblY);
		
		textField_2 = new JTextField();
		textField_2.setText("2");
		textField_2.setColumns(10);
		textField_2.setBackground(Color.WHITE);
		textField_2.setBounds(70, 240, 41, 20);
		contentPane.add(textField_2);
		
		JLabel lblZ = new JLabel("z");
		lblZ.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ.setBounds(60, 269, 21, 29);
		contentPane.add(lblZ);
		
		textField_3 = new JTextField();
		textField_3.setText("2");
		textField_3.setColumns(10);
		textField_3.setBackground(Color.WHITE);
		textField_3.setBounds(70, 273, 41, 20);
		contentPane.add(textField_3);
		
		JLabel lblBgSubPrimary = new JLabel("Background subtraction?");
		lblBgSubPrimary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBgSubPrimary.setBounds(20, 140, 161, 29);
		contentPane.add(lblBgSubPrimary);
		
		JRadioButton rbSubtractPrimaryNo = new JRadioButton("No");
		rbSubtractPrimaryNo.setSelected(true);
		rbSubtractPrimaryNo.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbSubtractPrimaryNo.setBackground(SystemColor.menu);
		rbSubtractPrimaryNo.setBounds(183, 143, 43, 23);
		contentPane.add(rbSubtractPrimaryNo);
		
		JRadioButton rbSubtractPrimaryYes = new JRadioButton("Yes");
		rbSubtractPrimaryYes.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbSubtractPrimaryYes.setEnabled(false);
		rbSubtractPrimaryYes.setBackground(SystemColor.menu);
		rbSubtractPrimaryYes.setBounds(223, 143, 48, 23);
		contentPane.add(rbSubtractPrimaryYes);
		
		JLabel lblRadius = new JLabel("Radius:\r\n");
		lblRadius.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblRadius.setEnabled(false);
		lblRadius.setBounds(271, 140, 48, 29);
		contentPane.add(lblRadius);
		
		textField_4 = new JTextField();
		textField_4.setEnabled(false);
		textField_4.setColumns(10);
		textField_4.setBackground(Color.WHITE);
		textField_4.setBounds(318, 144, 31, 20);
		contentPane.add(textField_4);
		
		JLabel lblDetectMaximaPrimary = new JLabel("3D detect maxima radius:");
		lblDetectMaximaPrimary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblDetectMaximaPrimary.setBounds(166, 180, 161, 29);
		contentPane.add(lblDetectMaximaPrimary);
		
		JLabel lblThreshold = new JLabel("Threshold:");
		lblThreshold.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblThreshold.setBounds(347, 180, 76, 29);
		contentPane.add(lblThreshold);
		
		textField_5 = new JTextField();
		textField_5.setText("8");
		textField_5.setColumns(10);
		textField_5.setBackground(Color.WHITE);
		textField_5.setBounds(347, 210, 65, 20);
		contentPane.add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setText("5");
		textField_6.setColumns(10);
		textField_6.setBackground(Color.WHITE);
		textField_6.setBounds(220, 208, 41, 20);
		contentPane.add(textField_6);
		
		JLabel lblY_2 = new JLabel("y");
		lblY_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_2.setBounds(210, 236, 21, 29);
		contentPane.add(lblY_2);
		
		textField_7 = new JTextField();
		textField_7.setText("5");
		textField_7.setColumns(10);
		textField_7.setBackground(Color.WHITE);
		textField_7.setBounds(220, 240, 41, 20);
		contentPane.add(textField_7);
		
		JLabel lblZ_2 = new JLabel("z");
		lblZ_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_2.setBounds(210, 269, 21, 29);
		contentPane.add(lblZ_2);
		
		textField_8 = new JTextField();
		textField_8.setText("5");
		textField_8.setColumns(10);
		textField_8.setBackground(Color.WHITE);
		textField_8.setBounds(220, 273, 41, 20);
		contentPane.add(textField_8);
		
		JLabel lblX_2 = new JLabel("x");
		lblX_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_2.setBounds(210, 204, 21, 29);
		contentPane.add(lblX_2);
		
		JButton btnPrimaryProcess = new JButton("Process");
		btnPrimaryProcess.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnPrimaryProcess.setBounds(331, 269, 114, 29);
		contentPane.add(btnPrimaryProcess);
		
		JPanel panelPrimary = new JPanel();
		panelPrimary.setBounds(33, 309, 412, 412);
		contentPane.add(panelPrimary);
		
		JLabel lblSegmentation_1 = new JLabel("Secondary Object Segmentation:");
		lblSegmentation_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblSegmentation_1.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblSegmentation_1.setBounds(501, 102, 239, 29);
		contentPane.add(lblSegmentation_1);
		
		JLabel lblWhichChannel_1 = new JLabel("Which channel? ");
		lblWhichChannel_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblWhichChannel_1.setBounds(728, 102, 106, 29);
		contentPane.add(lblWhichChannel_1);
		
		JComboBox cbChannelSecondary = new JComboBox();
		cbChannelSecondary.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbChannelSecondary.setSelectedIndex(3);
		cbChannelSecondary.setMaximumRowCount(4);
		cbChannelSecondary.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelSecondary.setBounds(833, 104, 48, 25);
		contentPane.add(cbChannelSecondary);
		
		JPanel panelSecondary = new JPanel();
		panelSecondary.setBounds(513, 309, 412, 412);
		contentPane.add(panelSecondary);
		
		JLabel lblGaussianBlurPrimary_1 = new JLabel("3D Gaussian blur:");
		lblGaussianBlurPrimary_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblGaussianBlurPrimary_1.setBounds(500, 182, 161, 29);
		contentPane.add(lblGaussianBlurPrimary_1);
		
		JLabel lblX_1 = new JLabel("x");
		lblX_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_1.setBounds(540, 206, 21, 29);
		contentPane.add(lblX_1);
		
		textField = new JTextField();
		textField.setText("3");
		textField.setColumns(10);
		textField.setBackground(Color.WHITE);
		textField.setBounds(550, 210, 41, 20);
		contentPane.add(textField);
		
		JLabel lblY_1 = new JLabel("y");
		lblY_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_1.setBounds(540, 238, 21, 29);
		contentPane.add(lblY_1);
		
		textField_9 = new JTextField();
		textField_9.setText("3");
		textField_9.setColumns(10);
		textField_9.setBackground(Color.WHITE);
		textField_9.setBounds(550, 242, 41, 20);
		contentPane.add(textField_9);
		
		JLabel lblZ_1 = new JLabel("z");
		lblZ_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_1.setBounds(540, 271, 21, 29);
		contentPane.add(lblZ_1);
		
		textField_10 = new JTextField();
		textField_10.setText("3");
		textField_10.setColumns(10);
		textField_10.setBackground(Color.WHITE);
		textField_10.setBounds(550, 275, 41, 20);
		contentPane.add(textField_10);
		
		JLabel lblBgSubPrimary_1 = new JLabel("Background subtraction?");
		lblBgSubPrimary_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBgSubPrimary_1.setBounds(500, 142, 161, 29);
		contentPane.add(lblBgSubPrimary_1);
		
		JRadioButton rbBgSubPrimaryNo_1 = new JRadioButton("No");
		rbBgSubPrimaryNo_1.setSelected(true);
		rbBgSubPrimaryNo_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbBgSubPrimaryNo_1.setBackground(SystemColor.menu);
		rbBgSubPrimaryNo_1.setBounds(663, 145, 43, 23);
		contentPane.add(rbBgSubPrimaryNo_1);
		
		JRadioButton rbBgSubPrimaryYes_1 = new JRadioButton("Yes");
		rbBgSubPrimaryYes_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbBgSubPrimaryYes_1.setEnabled(false);
		rbBgSubPrimaryYes_1.setBackground(SystemColor.menu);
		rbBgSubPrimaryYes_1.setBounds(703, 145, 48, 23);
		contentPane.add(rbBgSubPrimaryYes_1);
		
		JLabel lblRadius_1 = new JLabel("Radius:\r\n");
		lblRadius_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblRadius_1.setEnabled(false);
		lblRadius_1.setBounds(751, 142, 48, 29);
		contentPane.add(lblRadius_1);
		
		textField_11 = new JTextField();
		textField_11.setEnabled(false);
		textField_11.setColumns(10);
		textField_11.setBackground(Color.WHITE);
		textField_11.setBounds(798, 146, 31, 20);
		contentPane.add(textField_11);
		
		JLabel lblThreshold_1 = new JLabel("Threshold:");
		lblThreshold_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblThreshold_1.setBounds(827, 182, 76, 29);
		contentPane.add(lblThreshold_1);
		
		textField_12 = new JTextField();
		textField_12.setText("2");
		textField_12.setColumns(10);
		textField_12.setBackground(Color.WHITE);
		textField_12.setBounds(827, 212, 65, 20);
		contentPane.add(textField_12);
		
		JButton btnSecondaryProcess = new JButton("Process");
		btnSecondaryProcess.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnSecondaryProcess.setBounds(811, 271, 114, 29);
		contentPane.add(btnSecondaryProcess);
		
		JTextPane txtpnsupportForMultiple = new JTextPane();
		txtpnsupportForMultiple.setText("*Support for multiple spheroid/organoids per image coming soon...");
		txtpnsupportForMultiple.setFont(new Font("Gadugi", Font.PLAIN, 10));
		txtpnsupportForMultiple.setEditable(false);
		txtpnsupportForMultiple.setBackground(SystemColor.controlHighlight);
		txtpnsupportForMultiple.setBounds(658, 231, 106, 67);
		contentPane.add(txtpnsupportForMultiple);
	}
}
