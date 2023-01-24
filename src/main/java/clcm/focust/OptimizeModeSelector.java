package clcm.focust;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Window;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OptimizeModeSelector extends JFrame {

	private JPanel paneSelector;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OptimizeModeSelector frame = new OptimizeModeSelector();
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
	
	public OptimizeModeSelector() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(OptimizeModeSelector.class.getResource("/clcm/focust/resources/icon2.png")));
		setTitle("FOCUST: Optimization Mode Selector");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 346, 338);
		paneSelector = new JPanel();
		paneSelector.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(paneSelector);
		paneSelector.setLayout(null);
		
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
		btnBackToMenu.setBounds(10, 259, 133, 29);
		paneSelector.add(btnBackToMenu);
		
		JLabel lblSelectAMode = new JLabel("Select a mode to optimize:");
		lblSelectAMode.setVerticalAlignment(SwingConstants.TOP);
		lblSelectAMode.setFont(new Font("Gadugi", Font.BOLD, 16));
		lblSelectAMode.setBounds(10, 0, 322, 35);
		paneSelector.add(lblSelectAMode);
		
		JLabel lblSpheroidIcon = new JLabel("");
		lblSpheroidIcon.setIcon(new ImageIcon(OptimizeModeSelector.class.getResource("/clcm/focust/resources/spheroidIcon0.2.png")));
		lblSpheroidIcon.setBounds(34, 36, 77, 67);
		paneSelector.add(lblSpheroidIcon);
		
		JLabel lblSingleCellIcon = new JLabel("");
		lblSingleCellIcon.setIcon(new ImageIcon(OptimizeModeSelector.class.getResource("/clcm/focust/resources/singlecellicon.png")));
		lblSingleCellIcon.setBounds(44, 106, 77, 67);
		paneSelector.add(lblSingleCellIcon);
		
		JLabel lblSpeckleIcon = new JLabel("");
		lblSpeckleIcon.setIcon(new ImageIcon(OptimizeModeSelector.class.getResource("/clcm/focust/resources/speckleIcon.png")));
		lblSpeckleIcon.setBounds(44, 177, 77, 67);
		paneSelector.add(lblSpeckleIcon);
		
		JButton btnSpeckle = new JButton("Speckle Analysis");
		btnSpeckle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					OptimizationSingleCellView optimizeSpeckle = new OptimizationSingleCellView();
					optimizeSpeckle.setVisible(true);
						Window win = SwingUtilities.getWindowAncestor(btnSpeckle);
						win.dispose();
					});
			}
		});
		
		
		btnSpeckle.setFont(new Font("Gadugi", Font.BOLD, 13));
		btnSpeckle.setBounds(111, 193, 154, 35);
		paneSelector.add(btnSpeckle);
		
		JButton btnSingleCell = new JButton("Single Cell Analysis");
		btnSingleCell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					OptimizationSingleCellView optimizeSingleCell = new OptimizationSingleCellView();
					optimizeSingleCell.setVisible(true);
						Window win = SwingUtilities.getWindowAncestor(btnSingleCell);
						win.dispose();
					});
			}
		});
		
		
		btnSingleCell.setFont(new Font("Gadugi", Font.BOLD, 13));
		btnSingleCell.setBounds(111, 122, 154, 35);
		paneSelector.add(btnSingleCell);
		
		JButton btnSpheroid = new JButton("Spheroid Analysis");
		btnSpheroid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					OptimizationSpheroidView optimizeSpheroid = new OptimizationSpheroidView();
					optimizeSpheroid.setVisible(true);
						Window win = SwingUtilities.getWindowAncestor(btnSpheroid);
						win.dispose();
					});
			}
		});
		
		
		btnSpheroid.setFont(new Font("Gadugi", Font.BOLD, 13));
		btnSpheroid.setBounds(111, 52, 154, 35);
		paneSelector.add(btnSpheroid);
	}
}
