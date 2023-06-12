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
	private JTextField txtPriGBx;
	private JTextField txtPriGBy;
	private JTextField txtPriGBz;
	private JTextField txtPriSubtractRadius;
	private JTextField txtPriThreshold;
	private JTextField txtPriDMx;
	private JTextField txtPriDMy;
	private JTextField txtPriDMz;
	private JTextField txtSecGBx;
	private JTextField txtSecGBy;
	private JTextField txtSecGBz;
	private JTextField txtSecSubtractRadius;
	private JTextField txtSecThreshold;

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
				FOCUST.fileFinder();
				FOCUST.inputDir = FOCUST.imageFiles[0].getParent();
				
				// update the textbox in spheroid view
				txtInputDir.setText(FOCUST.inputDir);
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
		
		txtPriGBx = new JTextField();
		txtPriGBx.setText("2");
		txtPriGBx.setColumns(10);
		txtPriGBx.setBackground(Color.WHITE);
		txtPriGBx.setBounds(70, 208, 41, 20);
		contentPane.add(txtPriGBx);
		
		JLabel lblY = new JLabel("y");
		lblY.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY.setBounds(60, 236, 21, 29);
		contentPane.add(lblY);
		
		txtPriGBy = new JTextField();
		txtPriGBy.setText("2");
		txtPriGBy.setColumns(10);
		txtPriGBy.setBackground(Color.WHITE);
		txtPriGBy.setBounds(70, 240, 41, 20);
		contentPane.add(txtPriGBy);
		
		JLabel lblZ = new JLabel("z");
		lblZ.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ.setBounds(60, 269, 21, 29);
		contentPane.add(lblZ);
		
		txtPriGBz = new JTextField();
		txtPriGBz.setText("2");
		txtPriGBz.setColumns(10);
		txtPriGBz.setBackground(Color.WHITE);
		txtPriGBz.setBounds(70, 273, 41, 20);
		contentPane.add(txtPriGBz);
		
		JLabel lblBgSubPrimary = new JLabel("Background subtraction?");
		lblBgSubPrimary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBgSubPrimary.setBounds(20, 140, 161, 29);
		contentPane.add(lblBgSubPrimary);
		
		JRadioButton rbSubtractPrimaryNo = new JRadioButton("No");
		rbSubtractPrimaryNo.setSelected(true);
		rbSubtractPrimaryNo.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbSubtractPrimaryNo.setBackground(SystemColor.menu);
		rbSubtractPrimaryNo.setBounds(178, 143, 43, 23);
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
		
		txtPriSubtractRadius = new JTextField();
		txtPriSubtractRadius.setEnabled(false);
		txtPriSubtractRadius.setColumns(10);
		txtPriSubtractRadius.setBackground(Color.WHITE);
		txtPriSubtractRadius.setBounds(318, 144, 31, 20);
		contentPane.add(txtPriSubtractRadius);
		
		JLabel lblDetectMaximaPrimary = new JLabel("3D detect maxima radius:");
		lblDetectMaximaPrimary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblDetectMaximaPrimary.setBounds(166, 180, 161, 29);
		contentPane.add(lblDetectMaximaPrimary);
		
		JLabel lblThreshold = new JLabel("Threshold:");
		lblThreshold.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblThreshold.setBounds(347, 180, 76, 29);
		contentPane.add(lblThreshold);
		
		txtPriThreshold = new JTextField();
		txtPriThreshold.setText("8");
		txtPriThreshold.setColumns(10);
		txtPriThreshold.setBackground(Color.WHITE);
		txtPriThreshold.setBounds(347, 210, 65, 20);
		contentPane.add(txtPriThreshold);
		
		txtPriDMx = new JTextField();
		txtPriDMx.setText("5");
		txtPriDMx.setColumns(10);
		txtPriDMx.setBackground(Color.WHITE);
		txtPriDMx.setBounds(220, 208, 41, 20);
		contentPane.add(txtPriDMx);
		
		JLabel lblY_2 = new JLabel("y");
		lblY_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_2.setBounds(210, 236, 21, 29);
		contentPane.add(lblY_2);
		
		txtPriDMy = new JTextField();
		txtPriDMy.setText("5");
		txtPriDMy.setColumns(10);
		txtPriDMy.setBackground(Color.WHITE);
		txtPriDMy.setBounds(220, 240, 41, 20);
		contentPane.add(txtPriDMy);
		
		JLabel lblZ_2 = new JLabel("z");
		lblZ_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_2.setBounds(210, 269, 21, 29);
		contentPane.add(lblZ_2);
		
		txtPriDMz = new JTextField();
		txtPriDMz.setText("5");
		txtPriDMz.setColumns(10);
		txtPriDMz.setBackground(Color.WHITE);
		txtPriDMz.setBounds(220, 273, 41, 20);
		contentPane.add(txtPriDMz);
		
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
		
		txtSecGBx = new JTextField();
		txtSecGBx.setText("3");
		txtSecGBx.setColumns(10);
		txtSecGBx.setBackground(Color.WHITE);
		txtSecGBx.setBounds(550, 210, 41, 20);
		contentPane.add(txtSecGBx);
		
		JLabel lblY_1 = new JLabel("y");
		lblY_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_1.setBounds(540, 238, 21, 29);
		contentPane.add(lblY_1);
		
		txtSecGBy = new JTextField();
		txtSecGBy.setText("3");
		txtSecGBy.setColumns(10);
		txtSecGBy.setBackground(Color.WHITE);
		txtSecGBy.setBounds(550, 242, 41, 20);
		contentPane.add(txtSecGBy);
		
		JLabel lblZ_1 = new JLabel("z");
		lblZ_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_1.setBounds(540, 271, 21, 29);
		contentPane.add(lblZ_1);
		
		txtSecGBz = new JTextField();
		txtSecGBz.setText("3");
		txtSecGBz.setColumns(10);
		txtSecGBz.setBackground(Color.WHITE);
		txtSecGBz.setBounds(550, 275, 41, 20);
		contentPane.add(txtSecGBz);
		
		JLabel lblBgSubPrimary_1 = new JLabel("Background subtraction?");
		lblBgSubPrimary_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBgSubPrimary_1.setBounds(500, 140, 161, 29);
		contentPane.add(lblBgSubPrimary_1);
		
		JRadioButton rbBgSubPrimaryNo_1 = new JRadioButton("No");
		rbBgSubPrimaryNo_1.setSelected(true);
		rbBgSubPrimaryNo_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbBgSubPrimaryNo_1.setBackground(SystemColor.menu);
		rbBgSubPrimaryNo_1.setBounds(658, 143, 43, 23);
		contentPane.add(rbBgSubPrimaryNo_1);
		
		JRadioButton rbBgSubPrimaryYes_1 = new JRadioButton("Yes");
		rbBgSubPrimaryYes_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbBgSubPrimaryYes_1.setEnabled(false);
		rbBgSubPrimaryYes_1.setBackground(SystemColor.menu);
		rbBgSubPrimaryYes_1.setBounds(703, 143, 48, 23);
		contentPane.add(rbBgSubPrimaryYes_1);
		
		JLabel lblRadius_1 = new JLabel("Radius:\r\n");
		lblRadius_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblRadius_1.setEnabled(false);
		lblRadius_1.setBounds(751, 140, 48, 29);
		contentPane.add(lblRadius_1);
		
		txtSecSubtractRadius = new JTextField();
		txtSecSubtractRadius.setEnabled(false);
		txtSecSubtractRadius.setColumns(10);
		txtSecSubtractRadius.setBackground(Color.WHITE);
		txtSecSubtractRadius.setBounds(798, 144, 31, 20);
		contentPane.add(txtSecSubtractRadius);
		
		JLabel lblThreshold_1 = new JLabel("Threshold:");
		lblThreshold_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblThreshold_1.setBounds(827, 182, 76, 29);
		contentPane.add(lblThreshold_1);
		
		txtSecThreshold = new JTextField();
		txtSecThreshold.setText("2");
		txtSecThreshold.setColumns(10);
		txtSecThreshold.setBackground(Color.WHITE);
		txtSecThreshold.setBounds(827, 212, 65, 20);
		contentPane.add(txtSecThreshold);
		
		JButton btnSecondaryProcess = new JButton("Process");
		btnSecondaryProcess.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnSecondaryProcess.setBounds(811, 271, 114, 29);
		contentPane.add(btnSecondaryProcess);
		
		JTextPane txtpnsupportForMultiple = new JTextPane();
		txtpnsupportForMultiple.setText("*Support for multiple spheroid/organoids per image coming soon...");
		txtpnsupportForMultiple.setFont(new Font("Gadugi", Font.PLAIN, 10));
		txtpnsupportForMultiple.setEditable(false);
		txtpnsupportForMultiple.setBackground(SystemColor.controlHighlight);
		txtpnsupportForMultiple.setBounds(658, 231, 141, 67);
		contentPane.add(txtpnsupportForMultiple);
	}
}
