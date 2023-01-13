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
import javax.swing.JFileChooser;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import java.awt.Color;

import javax.swing.border.MatteBorder;
import javax.swing.ButtonGroup;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import javax.swing.border.EtchedBorder;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.swing.JInternalFrame;

public class SingleCellView extends JFrame {

	private JPanel paneSingleCell;
	private JTextField txtbInputDir;
	private JTextField txtPriGBx;
	private JTextField txtPriGBy;
	private JTextField txtPriGBz;
	private JTextField txtBgSubPrimaryVar;
	private JTextField txtPriDMx;
	private JTextField txtPriDMy;
	private JTextField txtPriDMz;
	private JTextField txtPriThreshold;
	private JTextField txtSecGBx;
	private JTextField txtSecGBy;
	private JTextField txtSecGBz;
	private JTextField txtBgSubSecondaryVar;
	private JTextField txtSecDMx;
	private JTextField txtSecDMy;
	private JTextField txtSecDMz;
	private JTextField txtSecThreshold;
	private JTextField txtOutputDir;
	private final ButtonGroup OutputDir = new ButtonGroup();
	private final ButtonGroup PrimaryBgSub = new ButtonGroup();
	private final ButtonGroup SecondaryBgSub = new ButtonGroup();
	private JTextField txtSingleCellChannel3Name;
	private JTextField txtSingleCellChannel4Name;
	private JTextField txtSingleCellGrouping;

	FutureTask<JFileChooser> futureFileChooser = new FutureTask<>(JFileChooser::new);
	
	
	/**
	 * Construct the single cell gui
	 */
	public SingleCellView() {
		
		// init the filechooser to avoid delay
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(futureFileChooser);
		
		// Construct the gui
		setTitle("FOCUST: Single Cell Analysis");
		setIconImage(Toolkit.getDefaultToolkit().getImage(SingleCellView.class.getResource("/clcm/focust/resources/icon2.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 840, 485);
		paneSingleCell = new JPanel();
		paneSingleCell.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(paneSingleCell);
		paneSingleCell.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Process a Dataset:");
		lblNewLabel.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 0, 133, 29);
		paneSingleCell.add(lblNewLabel);
		
		JLabel lblYouMustHave = new JLabel("You must have optimized your segmentation parameters.");
		lblYouMustHave.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblYouMustHave.setBounds(136, 0, 401, 29);
		paneSingleCell.add(lblYouMustHave);
		
		JLabel lblSelectAnInput = new JLabel("Select an input directory:");
		lblSelectAnInput.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblSelectAnInput.setBounds(20, 28, 167, 29);
		paneSingleCell.add(lblSelectAnInput);
		
		JButton btnInputDir = new JButton("Browse");
		btnInputDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Find and set the input directory.
				FOCUST.FileFinder();
				FOCUST.storeDir = FOCUST.imageFiles[0].getParent();
				
				// update the textbox in spheroid view
				txtbInputDir.setText(FOCUST.storeDir);
			}
		});
		btnInputDir.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnInputDir.setBounds(193, 28, 96, 29);
		paneSingleCell.add(btnInputDir);
		
		JLabel lblSeperateOutputDirectory = new JLabel("Seperate output directory?");
		lblSeperateOutputDirectory.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblSeperateOutputDirectory.setBounds(20, 68, 167, 29);
		paneSingleCell.add(lblSeperateOutputDirectory);
		
		JRadioButton rbOutputDirNo = new JRadioButton("No");
		rbOutputDirNo.setSelected(true);
		OutputDir.add(rbOutputDirNo);
		rbOutputDirNo.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbOutputDirNo.setBounds(193, 71, 48, 23);
		paneSingleCell.add(rbOutputDirNo);
		
		JRadioButton rbOutputDirYes = new JRadioButton("Yes");
		OutputDir.add(rbOutputDirYes);
		rbOutputDirYes.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbOutputDirYes.setBounds(243, 71, 48, 23);
		paneSingleCell.add(rbOutputDirYes);
		
		JButton btnOutputDir = new JButton("Browse");
		btnOutputDir.setEnabled(false);
		btnOutputDir.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnOutputDir.setBounds(297, 68, 96, 29);
		paneSingleCell.add(btnOutputDir);
		
		txtbInputDir = new JTextField();
		txtbInputDir.setBounds(299, 28, 289, 29);
		paneSingleCell.add(txtbInputDir);
		txtbInputDir.setColumns(10);
		
		txtOutputDir = new JTextField();
		txtOutputDir.setEnabled(false);
		txtOutputDir.setColumns(10);
		txtOutputDir.setBounds(403, 68, 289, 29);
		paneSingleCell.add(txtOutputDir);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(255, 255, 255));
		separator.setForeground(new Color(169, 169, 169));
		separator.setBounds(12, 108, 768, 2);
		paneSingleCell.add(separator);
		
		JLabel lblHowManyChannels = new JLabel("Total number of channels per image?");
		lblHowManyChannels.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblHowManyChannels.setBounds(10, 117, 237, 29);
		paneSingleCell.add(lblHowManyChannels);
		
		JComboBox cbChannelTotal = new JComboBox();
		cbChannelTotal.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelTotal.setModel(new DefaultComboBoxModel(new String[] {"2", "3", "4"}));
		cbChannelTotal.setMaximumRowCount(3);
		cbChannelTotal.setBounds(248, 119, 48, 25);
		paneSingleCell.add(cbChannelTotal);
		
		JLabel lblSegmentPrimary = new JLabel("Primary Object\r\n");
		lblSegmentPrimary.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegmentPrimary.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblSegmentPrimary.setBounds(326, 166, 187, 29);
		paneSingleCell.add(lblSegmentPrimary);
		
		JLabel lblSegmentSecondary = new JLabel("Secondary Object");
		lblSegmentSecondary.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegmentSecondary.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblSegmentSecondary.setBounds(580, 166, 187, 29);
		paneSingleCell.add(lblSegmentSecondary);
		
		JPanel PrimaryObjectPanel = new JPanel();
		PrimaryObjectPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		PrimaryObjectPanel.setBackground(new Color(211, 211, 211));
		PrimaryObjectPanel.setBounds(326, 224, 187, 182);
		paneSingleCell.add(PrimaryObjectPanel);
		PrimaryObjectPanel.setLayout(null);
		
		JLabel lblBgSubPrimary = new JLabel("Background subtraction?");
		lblBgSubPrimary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBgSubPrimary.setBounds(10, 0, 161, 29);
		PrimaryObjectPanel.add(lblBgSubPrimary);
		
		JRadioButton rbBgSubPrimaryNo = new JRadioButton("No");
		rbBgSubPrimaryNo.setSelected(true);
		PrimaryBgSub.add(rbBgSubPrimaryNo);
		rbBgSubPrimaryNo.setBackground(new Color(211, 211, 211));
		rbBgSubPrimaryNo.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbBgSubPrimaryNo.setBounds(10, 24, 43, 23);
		PrimaryObjectPanel.add(rbBgSubPrimaryNo);
		
		JRadioButton rbBgSubPrimaryYes = new JRadioButton("Yes");
		rbBgSubPrimaryYes.setEnabled(false);
		PrimaryBgSub.add(rbBgSubPrimaryYes);
		rbBgSubPrimaryYes.setBackground(new Color(211, 211, 211));
		rbBgSubPrimaryYes.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rbBgSubPrimaryYes.setBounds(50, 24, 48, 23);
		PrimaryObjectPanel.add(rbBgSubPrimaryYes);
		
		JLabel lblGaussianBlurPrimary = new JLabel("3D Gaussian blur:");
		lblGaussianBlurPrimary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblGaussianBlurPrimary.setBounds(10, 47, 161, 29);
		PrimaryObjectPanel.add(lblGaussianBlurPrimary);
		
		JLabel lblRadius = new JLabel("Radius:\r\n");
		lblRadius.setEnabled(false);
		lblRadius.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblRadius.setBounds(98, 21, 48, 29);
		PrimaryObjectPanel.add(lblRadius);
		
		txtPriGBx = new JTextField();
		txtPriGBx.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c)) {
					e.consume();
				}
			}
		});
		txtPriGBx.setBackground(new Color(211, 211, 211));
		txtPriGBx.setBounds(20, 73, 41, 20);
		PrimaryObjectPanel.add(txtPriGBx);
		txtPriGBx.setColumns(10);
		
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
		
		txtBgSubPrimaryVar = new JTextField();
		txtBgSubPrimaryVar.setEnabled(false);
		txtBgSubPrimaryVar.setColumns(10);
		txtBgSubPrimaryVar.setBackground(new Color(211, 211, 211));
		txtBgSubPrimaryVar.setBounds(145, 25, 31, 20);
		PrimaryObjectPanel.add(txtBgSubPrimaryVar);
		
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
		lblWhichChannel.setBounds(341, 191, 106, 29);
		paneSingleCell.add(lblWhichChannel);
		
		JComboBox cbChannelPrimary = new JComboBox();
		cbChannelPrimary.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbChannelPrimary.setMaximumRowCount(4);
		cbChannelPrimary.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelPrimary.setBounds(454, 193, 48, 25);
		paneSingleCell.add(cbChannelPrimary);
		
		JPanel SecondaryObjectPanel = new JPanel();
		SecondaryObjectPanel.setLayout(null);
		SecondaryObjectPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		SecondaryObjectPanel.setBackground(new Color(211, 211, 211));
		SecondaryObjectPanel.setBounds(580, 224, 187, 182);
		paneSingleCell.add(SecondaryObjectPanel);
		
		JLabel lblBgSubSecondary = new JLabel("Background subtraction?");
		lblBgSubSecondary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBgSubSecondary.setBounds(10, 0, 161, 29);
		SecondaryObjectPanel.add(lblBgSubSecondary);
		
		JRadioButton rdbtnNewRadioButton_1_1 = new JRadioButton("No");
		rdbtnNewRadioButton_1_1.setSelected(true);
		SecondaryBgSub.add(rdbtnNewRadioButton_1_1);
		rdbtnNewRadioButton_1_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rdbtnNewRadioButton_1_1.setBackground(new Color(211, 211, 211));
		rdbtnNewRadioButton_1_1.setBounds(10, 24, 43, 23);
		SecondaryObjectPanel.add(rdbtnNewRadioButton_1_1);
		
		JRadioButton rdbtnYes_1_1 = new JRadioButton("Yes");
		rdbtnYes_1_1.setEnabled(false);
		SecondaryBgSub.add(rdbtnYes_1_1);
		rdbtnYes_1_1.setFont(new Font("Gadugi", Font.PLAIN, 13));
		rdbtnYes_1_1.setBackground(new Color(211, 211, 211));
		rdbtnYes_1_1.setBounds(50, 24, 48, 23);
		SecondaryObjectPanel.add(rdbtnYes_1_1);
		
		JLabel lblGaussianBlurSecondary = new JLabel("3D Gaussian blur:");
		lblGaussianBlurSecondary.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblGaussianBlurSecondary.setBounds(10, 47, 161, 29);
		SecondaryObjectPanel.add(lblGaussianBlurSecondary);
		
		JLabel lblRadius_1 = new JLabel("Radius:\r\n");
		lblRadius_1.setEnabled(false);
		lblRadius_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
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
		
		txtBgSubSecondaryVar = new JTextField();
		txtBgSubSecondaryVar.setEnabled(false);
		txtBgSubSecondaryVar.setColumns(10);
		txtBgSubSecondaryVar.setBackground(new Color(211, 211, 211));
		txtBgSubSecondaryVar.setBounds(145, 25, 31, 20);
		SecondaryObjectPanel.add(txtBgSubSecondaryVar);
		
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
		
		JLabel lblWhichChannel_1 = new JLabel("Which channel? ");
		lblWhichChannel_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblWhichChannel_1.setBounds(593, 191, 106, 29);
		paneSingleCell.add(lblWhichChannel_1);
		
		JComboBox cbChannelSecondary = new JComboBox();
		cbChannelSecondary.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4"}));
		cbChannelSecondary.setSelectedIndex(3);
		cbChannelSecondary.setMaximumRowCount(4);
		cbChannelSecondary.setFont(new Font("Gadugi", Font.PLAIN, 13));
		cbChannelSecondary.setBounds(706, 193, 48, 25);
		paneSingleCell.add(cbChannelSecondary);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(311, 163, 469, 255);
		paneSingleCell.add(panel);
		panel.setLayout(null);
		
		JLabel lblSegmentationParameters = new JLabel("Segmentation Parameters");
		lblSegmentationParameters.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegmentationParameters.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblSegmentationParameters.setBounds(437, 131, 216, 29);
		paneSingleCell.add(lblSegmentationParameters);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnHelp.setBounds(684, 11, 96, 29);
		paneSingleCell.add(btnHelp);
		
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
		btnBackToMenu.setBounds(10, 373, 125, 29);
		paneSingleCell.add(btnBackToMenu);
		
		JButton btnRunAnalysis = new JButton("Run Analysis");
		btnRunAnalysis.setFont(new Font("Gadugi", Font.BOLD, 14));
		btnRunAnalysis.setBounds(10, 406, 279, 29);
		paneSingleCell.add(btnRunAnalysis);
		
		JLabel lblNameChannel2 = new JLabel("Name Channel 2:");
		lblNameChannel2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblNameChannel2.setBounds(10, 150, 116, 29);
		paneSingleCell.add(lblNameChannel2);
		
		JLabel lblNameChannel3 = new JLabel("Name Channel 3:");
		lblNameChannel3.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblNameChannel3.setBounds(10, 184, 116, 29);
		paneSingleCell.add(lblNameChannel3);
		
		JLabel lblNameChannel4 = new JLabel("Name Channel 4:");
		lblNameChannel4.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblNameChannel4.setBounds(10, 217, 116, 29);
		paneSingleCell.add(lblNameChannel4);
		
		JLabel lblGroupingInfo = new JLabel("Grouping* Info?");
		lblGroupingInfo.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblGroupingInfo.setBounds(10, 257, 116, 29);
		paneSingleCell.add(lblGroupingInfo);
		
		JTextField txtSingleCellChannel2Name = new JTextField();
		txtSingleCellChannel2Name.setColumns(10);
		txtSingleCellChannel2Name.setBounds(120, 150, 176, 29);
		paneSingleCell.add(txtSingleCellChannel2Name);
		
		txtSingleCellChannel3Name = new JTextField();
		txtSingleCellChannel3Name.setColumns(10);
		txtSingleCellChannel3Name.setBounds(120, 184, 176, 29);
		paneSingleCell.add(txtSingleCellChannel3Name);
		
		txtSingleCellChannel4Name = new JTextField();
		txtSingleCellChannel4Name.setColumns(10);
		txtSingleCellChannel4Name.setBounds(120, 217, 176, 29);
		paneSingleCell.add(txtSingleCellChannel4Name);
		
		txtSingleCellGrouping = new JTextField();
		txtSingleCellGrouping.setColumns(10);
		txtSingleCellGrouping.setBounds(120, 257, 176, 29);
		paneSingleCell.add(txtSingleCellGrouping);
		
		JTextPane txtpnanyConditionsfactorsSpecific = new JTextPane();
		txtpnanyConditionsfactorsSpecific.setBackground(UIManager.getColor("CheckBox.light"));
		txtpnanyConditionsfactorsSpecific.setFont(new Font("Gadugi", Font.PLAIN, 10));
		txtpnanyConditionsfactorsSpecific.setEditable(false);
		txtpnanyConditionsfactorsSpecific.setText("*Any conditions/factors specific to this dataset that you might wish to group data by. For example, control or experimental variables. This will appear as a seperate column alongside all data run within the same batch. ");
		txtpnanyConditionsfactorsSpecific.setBounds(10, 291, 289, 67);
		paneSingleCell.add(txtpnanyConditionsfactorsSpecific);
		
		JButton btnLoadConfigSingleCell = new JButton("Load Parameters");
		btnLoadConfigSingleCell.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnLoadConfigSingleCell.setBounds(148, 373, 141, 29);
		paneSingleCell.add(btnLoadConfigSingleCell);
	}
}
