package clcm.focust;

import java.awt.EventQueue;

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
import javax.swing.SwingUtilities;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import java.awt.SystemColor;

public class OptimizationSingleCellView extends JFrame {

	private JPanel paneSingleCellOptimization;
	private JTextField txtDir;
	
	FutureTask<JFileChooser> futureFileChooser = new FutureTask<>(JFileChooser::new);
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
	private JTextField txtSecDMx;
	private JTextField txtSecDMy;
	private JTextField txtSecDMz;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OptimizationSingleCellView frame = new OptimizationSingleCellView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	

	/**
	 * Make the single cell gui
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public OptimizationSingleCellView() {
		setAlwaysOnTop(true);
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(futureFileChooser);
		
		setTitle("FOCUST: Single Cell and Speckle Optimization");
		setIconImage(Toolkit.getDefaultToolkit().getImage(OptimizationSingleCellView.class.getResource("/icon2.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 975, 771);
		paneSingleCellOptimization = new JPanel();
		paneSingleCellOptimization.setBackground(UIManager.getColor("InternalFrame.borderColor"));
		paneSingleCellOptimization.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(paneSingleCellOptimization);
		paneSingleCellOptimization.setLayout(null);
		
		JLabel lblSelectAnInput = new JLabel("Select an input directory:");
		lblSelectAnInput.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblSelectAnInput.setBounds(10, 53, 167, 29);
		paneSingleCellOptimization.add(lblSelectAnInput);
		
		JButton btnInputDir = new JButton("Browse");
		btnInputDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Find and set the input directory.
				FOCUST.FileFinder();
				FOCUST.inputDir = FOCUST.imageFiles[0].getParent();
			
				// update the textbox 
				txtDir.setText(FOCUST.inputDir);
			}
		});
		
		
		btnInputDir.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnInputDir.setBounds(183, 53, 96, 29);
		paneSingleCellOptimization.add(btnInputDir);
		
		txtDir = new JTextField();
		txtDir.setColumns(10);
		txtDir.setBounds(289, 53, 289, 29);
		paneSingleCellOptimization.add(txtDir);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(UIManager.getColor("Button.shadow"));
		separator.setBackground(Color.WHITE);
		separator.setBounds(10, 93, 939, 2);
		paneSingleCellOptimization.add(separator);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnHelp.setBounds(853, 11, 96, 29);
		paneSingleCellOptimization.add(btnHelp);
		
		JButton btnLoadNextImage = new JButton("Load Next Image");
		btnLoadNextImage.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnLoadNextImage.setBounds(588, 53, 154, 29);
		paneSingleCellOptimization.add(btnLoadNextImage);
		
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
		paneSingleCellOptimization.add(btnBackToMenu);
		
		JPanel panelPrimary = new JPanel();
		panelPrimary.setBounds(33, 309, 412, 412);
		paneSingleCellOptimization.add(panelPrimary);
		
		JLabel lblSegmentation = new JLabel("Primary Object Segmentation:");
		lblSegmentation.setHorizontalAlignment(SwingConstants.LEFT);
		lblSegmentation.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblSegmentation.setBounds(20, 102, 219, 29);
		paneSingleCellOptimization.add(lblSegmentation);
		
		JLabel lblSegmentation_1 = new JLabel("Secondary Object Segmentation:");
		lblSegmentation_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblSegmentation_1.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblSegmentation_1.setBounds(501, 102, 239, 29);
		paneSingleCellOptimization.add(lblSegmentation_1);
		
		JLabel lblWhichChannel = new JLabel("Which channel? ");
		lblWhichChannel.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblWhichChannel.setBounds(231, 102, 106, 29);
		paneSingleCellOptimization.add(lblWhichChannel);
		
		JComboBox cbChannelPrimary = new JComboBox();
		cbChannelPrimary.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbChannelPrimary.setSelectedIndex(0);
		cbChannelPrimary.setMaximumRowCount(4);
		cbChannelPrimary.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelPrimary.setBounds(335, 105, 48, 25);
		paneSingleCellOptimization.add(cbChannelPrimary);
		
		JLabel lblWhichChannel_1 = new JLabel("Which channel? ");
		lblWhichChannel_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblWhichChannel_1.setBounds(728, 102, 106, 29);
		paneSingleCellOptimization.add(lblWhichChannel_1);
		
		JComboBox cbChannelSecondary = new JComboBox();
		cbChannelSecondary.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbChannelSecondary.setSelectedIndex(3);
		cbChannelSecondary.setMaximumRowCount(4);
		cbChannelSecondary.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelSecondary.setBounds(833, 104, 48, 25);
		paneSingleCellOptimization.add(cbChannelSecondary);
		
		JLabel lblOptimizeADataset = new JLabel("Optimize a Dataset:");
		lblOptimizeADataset.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblOptimizeADataset.setBounds(10, 11, 144, 29);
		paneSingleCellOptimization.add(lblOptimizeADataset);
		
		JLabel lblHereYouCan = new JLabel("Adjust the parameters below to configure the optimal segmentation for a given dataset.");
		lblHereYouCan.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblHereYouCan.setBounds(149, 11, 593, 29);
		paneSingleCellOptimization.add(lblHereYouCan);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBackground(Color.WHITE);
		separator_1.setForeground(UIManager.getColor("Button.shadow"));
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(478, 95, 2, 626);
		paneSingleCellOptimization.add(separator_1);
		
		JLabel lblGaussianBlurPrimary = new JLabel("3D Gaussian blur:");
		lblGaussianBlurPrimary.setBounds(20, 180, 161, 29);
		paneSingleCellOptimization.add(lblGaussianBlurPrimary);
		lblGaussianBlurPrimary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		
		JLabel lblX = new JLabel("x");
		lblX.setBounds(60, 204, 21, 29);
		paneSingleCellOptimization.add(lblX);
		lblX.setFont(new Font("Gadugi", Font.PLAIN, 14));
		
		JTextField txtPriGBx = new JTextField();
		txtPriGBx.setText("2");
		txtPriGBx.setBounds(70, 208, 41, 20);
		paneSingleCellOptimization.add(txtPriGBx);
		txtPriGBx.setColumns(10);
		txtPriGBx.setBackground(UIManager.getColor("TextArea.background"));
		
		JLabel lblY = new JLabel("y");
		lblY.setBounds(60, 236, 21, 29);
		paneSingleCellOptimization.add(lblY);
		lblY.setFont(new Font("Gadugi", Font.PLAIN, 14));
		
		txtPriGBy = new JTextField();
		txtPriGBy.setText("2");
		txtPriGBy.setBounds(70, 240, 41, 20);
		paneSingleCellOptimization.add(txtPriGBy);
		txtPriGBy.setColumns(10);
		txtPriGBy.setBackground(UIManager.getColor("TextArea.background"));
		
		JLabel lblZ = new JLabel("z");
		lblZ.setBounds(60, 269, 21, 29);
		paneSingleCellOptimization.add(lblZ);
		lblZ.setFont(new Font("Gadugi", Font.PLAIN, 14));
		
		txtPriGBz = new JTextField();
		txtPriGBz.setText("2");
		txtPriGBz.setBounds(70, 273, 41, 20);
		paneSingleCellOptimization.add(txtPriGBz);
		txtPriGBz.setColumns(10);
		txtPriGBz.setBackground(UIManager.getColor("TextArea.background"));
		
		JLabel lblBgSubPrimary = new JLabel("Background subtraction?");
		lblBgSubPrimary.setBounds(20, 140, 161, 29);
		paneSingleCellOptimization.add(lblBgSubPrimary);
		lblBgSubPrimary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		
		JRadioButton rbSubtractPrimaryNo = new JRadioButton("No");
		rbSubtractPrimaryNo.setBounds(183, 143, 43, 23);
		paneSingleCellOptimization.add(rbSubtractPrimaryNo);
		rbSubtractPrimaryNo.setSelected(true);
		rbSubtractPrimaryNo.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbSubtractPrimaryNo.setBackground(UIManager.getColor("InternalFrame.borderColor"));
		
		JRadioButton rbSubtractPrimaryYes = new JRadioButton("Yes");
		rbSubtractPrimaryYes.setBounds(223, 143, 48, 23);
		paneSingleCellOptimization.add(rbSubtractPrimaryYes);
		rbSubtractPrimaryYes.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbSubtractPrimaryYes.setEnabled(false);
		rbSubtractPrimaryYes.setBackground(UIManager.getColor("InternalFrame.borderColor"));
		
		JLabel lblRadius = new JLabel("Radius:\r\n");
		lblRadius.setBounds(271, 140, 48, 29);
		paneSingleCellOptimization.add(lblRadius);
		lblRadius.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblRadius.setEnabled(false);
		
		txtPriSubtractRadius = new JTextField();
		txtPriSubtractRadius.setBounds(318, 144, 31, 20);
		paneSingleCellOptimization.add(txtPriSubtractRadius);
		txtPriSubtractRadius.setEnabled(false);
		txtPriSubtractRadius.setColumns(10);
		txtPriSubtractRadius.setBackground(UIManager.getColor("TextPane.background"));
		
		JLabel lblDetectMaximaPrimary = new JLabel("3D detect maxima radius:");
		lblDetectMaximaPrimary.setBounds(166, 180, 161, 29);
		paneSingleCellOptimization.add(lblDetectMaximaPrimary);
		lblDetectMaximaPrimary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		
		JLabel lblThreshold = new JLabel("Threshold:");
		lblThreshold.setBounds(347, 180, 76, 29);
		paneSingleCellOptimization.add(lblThreshold);
		lblThreshold.setFont(new Font("Gadugi", Font.PLAIN, 14));
		
		txtPriThreshold = new JTextField();
		txtPriThreshold.setText("8");
		txtPriThreshold.setBounds(347, 210, 65, 20);
		paneSingleCellOptimization.add(txtPriThreshold);
		txtPriThreshold.setColumns(10);
		txtPriThreshold.setBackground(UIManager.getColor("TextArea.background"));
		
		txtPriDMx = new JTextField();
		txtPriDMx.setText("5");
		txtPriDMx.setColumns(10);
		txtPriDMx.setBackground(Color.WHITE);
		txtPriDMx.setBounds(220, 208, 41, 20);
		paneSingleCellOptimization.add(txtPriDMx);
		
		JLabel lblY_2 = new JLabel("y");
		lblY_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_2.setBounds(210, 236, 21, 29);
		paneSingleCellOptimization.add(lblY_2);
		
		txtPriDMy = new JTextField();
		txtPriDMy.setText("5");
		txtPriDMy.setColumns(10);
		txtPriDMy.setBackground(Color.WHITE);
		txtPriDMy.setBounds(220, 240, 41, 20);
		paneSingleCellOptimization.add(txtPriDMy);
		
		JLabel lblZ_2 = new JLabel("z");
		lblZ_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_2.setBounds(210, 269, 21, 29);
		paneSingleCellOptimization.add(lblZ_2);
		
		txtPriDMz = new JTextField();
		txtPriDMz.setText("5");
		txtPriDMz.setColumns(10);
		txtPriDMz.setBackground(Color.WHITE);
		txtPriDMz.setBounds(220, 273, 41, 20);
		paneSingleCellOptimization.add(txtPriDMz);
		
		JLabel lblX_2 = new JLabel("x");
		lblX_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_2.setBounds(210, 204, 21, 29);
		paneSingleCellOptimization.add(lblX_2);
		
		JPanel panelSecondary = new JPanel();
		panelSecondary.setBounds(513, 309, 412, 412);
		paneSingleCellOptimization.add(panelSecondary);
		
		JButton btnPrimaryProcess = new JButton("Process");
		btnPrimaryProcess.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnPrimaryProcess.setBounds(331, 269, 114, 29);
		paneSingleCellOptimization.add(btnPrimaryProcess);
		
		JLabel lblGaussianBlurPrimary_1 = new JLabel("3D Gaussian blur:");
		lblGaussianBlurPrimary_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblGaussianBlurPrimary_1.setBounds(500, 182, 161, 29);
		paneSingleCellOptimization.add(lblGaussianBlurPrimary_1);
		
		JLabel lblX_1 = new JLabel("x");
		lblX_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_1.setBounds(540, 206, 21, 29);
		paneSingleCellOptimization.add(lblX_1);
		
		txtSecGBx = new JTextField();
		txtSecGBx.setText("3");
		txtSecGBx.setColumns(10);
		txtSecGBx.setBackground(Color.WHITE);
		txtSecGBx.setBounds(550, 210, 41, 20);
		paneSingleCellOptimization.add(txtSecGBx);
		
		JLabel lblY_1 = new JLabel("y");
		lblY_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_1.setBounds(540, 238, 21, 29);
		paneSingleCellOptimization.add(lblY_1);
		
		txtSecGBy = new JTextField();
		txtSecGBy.setText("3");
		txtSecGBy.setColumns(10);
		txtSecGBy.setBackground(Color.WHITE);
		txtSecGBy.setBounds(550, 242, 41, 20);
		paneSingleCellOptimization.add(txtSecGBy);
		
		JLabel lblZ_1 = new JLabel("z");
		lblZ_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_1.setBounds(540, 271, 21, 29);
		paneSingleCellOptimization.add(lblZ_1);
		
		txtSecGBz = new JTextField();
		txtSecGBz.setText("3");
		txtSecGBz.setColumns(10);
		txtSecGBz.setBackground(Color.WHITE);
		txtSecGBz.setBounds(550, 275, 41, 20);
		paneSingleCellOptimization.add(txtSecGBz);
		
		JLabel lblBgSubPrimary_1 = new JLabel("Background subtraction?");
		lblBgSubPrimary_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBgSubPrimary_1.setBounds(500, 142, 161, 29);
		paneSingleCellOptimization.add(lblBgSubPrimary_1);
		
		JRadioButton rbBgSubPrimaryNo_1 = new JRadioButton("No");
		rbBgSubPrimaryNo_1.setSelected(true);
		rbBgSubPrimaryNo_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbBgSubPrimaryNo_1.setBackground(SystemColor.menu);
		rbBgSubPrimaryNo_1.setBounds(663, 145, 43, 23);
		paneSingleCellOptimization.add(rbBgSubPrimaryNo_1);
		
		JRadioButton rbBgSubPrimaryYes_1 = new JRadioButton("Yes");
		rbBgSubPrimaryYes_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbBgSubPrimaryYes_1.setEnabled(false);
		rbBgSubPrimaryYes_1.setBackground(SystemColor.menu);
		rbBgSubPrimaryYes_1.setBounds(703, 145, 48, 23);
		paneSingleCellOptimization.add(rbBgSubPrimaryYes_1);
		
		JLabel lblRadius_1 = new JLabel("Radius:\r\n");
		lblRadius_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblRadius_1.setEnabled(false);
		lblRadius_1.setBounds(751, 142, 48, 29);
		paneSingleCellOptimization.add(lblRadius_1);
		
		txtSecSubtractRadius = new JTextField();
		txtSecSubtractRadius.setEnabled(false);
		txtSecSubtractRadius.setColumns(10);
		txtSecSubtractRadius.setBackground(Color.WHITE);
		txtSecSubtractRadius.setBounds(798, 146, 31, 20);
		paneSingleCellOptimization.add(txtSecSubtractRadius);
		
		JLabel lblDetectMaximaPrimary_1 = new JLabel("3D detect maxima radius:");
		lblDetectMaximaPrimary_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblDetectMaximaPrimary_1.setBounds(646, 182, 161, 29);
		paneSingleCellOptimization.add(lblDetectMaximaPrimary_1);
		
		JLabel lblThreshold_1 = new JLabel("Threshold:");
		lblThreshold_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblThreshold_1.setBounds(827, 182, 76, 29);
		paneSingleCellOptimization.add(lblThreshold_1);
		
		txtSecThreshold = new JTextField();
		txtSecThreshold.setText("2");
		txtSecThreshold.setColumns(10);
		txtSecThreshold.setBackground(Color.WHITE);
		txtSecThreshold.setBounds(827, 212, 65, 20);
		paneSingleCellOptimization.add(txtSecThreshold);
		
		txtSecDMx = new JTextField();
		txtSecDMx.setText("15");
		txtSecDMx.setColumns(10);
		txtSecDMx.setBackground(Color.WHITE);
		txtSecDMx.setBounds(700, 210, 41, 20);
		paneSingleCellOptimization.add(txtSecDMx);
		
		JLabel lblY_2_1 = new JLabel("y");
		lblY_2_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblY_2_1.setBounds(690, 238, 21, 29);
		paneSingleCellOptimization.add(lblY_2_1);
		
		txtSecDMy = new JTextField();
		txtSecDMy.setText("15");
		txtSecDMy.setColumns(10);
		txtSecDMy.setBackground(Color.WHITE);
		txtSecDMy.setBounds(700, 242, 41, 20);
		paneSingleCellOptimization.add(txtSecDMy);
		
		JLabel lblZ_2_1 = new JLabel("z");
		lblZ_2_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblZ_2_1.setBounds(690, 271, 21, 29);
		paneSingleCellOptimization.add(lblZ_2_1);
		
		txtSecDMz = new JTextField();
		txtSecDMz.setText("15");
		txtSecDMz.setColumns(10);
		txtSecDMz.setBackground(Color.WHITE);
		txtSecDMz.setBounds(700, 275, 41, 20);
		paneSingleCellOptimization.add(txtSecDMz);
		
		JLabel lblX_2_1 = new JLabel("x");
		lblX_2_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblX_2_1.setBounds(690, 206, 21, 29);
		paneSingleCellOptimization.add(lblX_2_1);
		
		JButton btnSecondaryProcess = new JButton("Process");
		btnSecondaryProcess.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnSecondaryProcess.setBounds(811, 271, 114, 29);
		paneSingleCellOptimization.add(btnSecondaryProcess);
		
		JButton btnSaveParameters = new JButton("Save Parameters");
		btnSaveParameters.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnSaveParameters.setBounds(795, 53, 154, 29);
		paneSingleCellOptimization.add(btnSaveParameters);
	}
}
