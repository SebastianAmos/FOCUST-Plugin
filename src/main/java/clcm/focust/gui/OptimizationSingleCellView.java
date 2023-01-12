package clcm.focust.gui;

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
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class OptimizationSingleCellView extends JFrame {

	private JPanel paneOptimization;
	private JTextField textField;

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
	 * Create the frame.
	 */
	public OptimizationSingleCellView() {
		setTitle("FOCUST: Optimization");
		setIconImage(Toolkit.getDefaultToolkit().getImage(OptimizationSingleCellView.class.getResource("/clcm/focust/resources/icon2.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1207, 719);
		paneOptimization = new JPanel();
		paneOptimization.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(paneOptimization);
		paneOptimization.setLayout(null);
		
		JLabel lblSelectAnInput = new JLabel("Select an input directory:");
		lblSelectAnInput.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblSelectAnInput.setBounds(10, 53, 167, 29);
		paneOptimization.add(lblSelectAnInput);
		
		JButton btnInputDir = new JButton("Browse");
		btnInputDir.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnInputDir.setBounds(183, 53, 96, 29);
		paneOptimization.add(btnInputDir);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(289, 53, 289, 29);
		paneOptimization.add(textField);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(169, 169, 169));
		separator.setBackground(Color.WHITE);
		separator.setBounds(10, 93, 1169, 2);
		paneOptimization.add(separator);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnHelp.setBounds(1085, 11, 96, 29);
		paneOptimization.add(btnHelp);
		
		JButton btnLoadNextImage = new JButton("Load Next Image");
		btnLoadNextImage.setFont(new Font("Gadugi", Font.PLAIN, 14));
		btnLoadNextImage.setBounds(588, 53, 137, 29);
		paneOptimization.add(btnLoadNextImage);
		
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
		btnBackToMenu.setBounds(10, 640, 133, 29);
		paneOptimization.add(btnBackToMenu);
		
		JPanel panelSegmented = new JPanel();
		panelSegmented.setBounds(10, 260, 370, 370);
		paneOptimization.add(panelSegmented);
		
		JPanel panelSegmented_1 = new JPanel();
		panelSegmented_1.setBounds(408, 260, 370, 370);
		paneOptimization.add(panelSegmented_1);
		
		JLabel lblOriginalImage = new JLabel("Original Image:");
		lblOriginalImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblOriginalImage.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblOriginalImage.setBounds(140, 230, 111, 29);
		paneOptimization.add(lblOriginalImage);
		
		JLabel lblSegmentation = new JLabel("Segmentation:");
		lblSegmentation.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegmentation.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblSegmentation.setBounds(540, 230, 111, 29);
		paneOptimization.add(lblSegmentation);
		
		JPanel panelSegmented_1_1 = new JPanel();
		panelSegmented_1_1.setBounds(809, 260, 370, 370);
		paneOptimization.add(panelSegmented_1_1);
		
		JLabel lblSegmentation_1 = new JLabel("Segmentation:");
		lblSegmentation_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSegmentation_1.setFont(new Font("Gadugi", Font.BOLD, 14));
		lblSegmentation_1.setBounds(941, 230, 111, 29);
		paneOptimization.add(lblSegmentation_1);
	}
}
