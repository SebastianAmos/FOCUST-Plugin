package clcm.focust.gui;

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
import java.awt.event.ActionEvent;

public class SpheroidView extends JFrame {

	 private JPanel paneSpheroid;

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
		
		JLabel lblTitleSpheroid = new JLabel("Process a Dataset:");
		lblTitleSpheroid.setBounds(10, 0, 121, 20);
		lblTitleSpheroid.setFont(new Font("Gadugi", Font.BOLD, 14));
		paneSpheroid.add(lblTitleSpheroid);
		
		JLabel lblYouMustHave = new JLabel("You must have optimized your segmentation parameters.");
		lblYouMustHave.setBounds(136, 0, 352, 20);
		lblYouMustHave.setFont(new Font("Gadugi", Font.PLAIN, 14));
		paneSpheroid.add(lblYouMustHave);
		
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
		btnBackToMenu.setBounds(10, 373, 133, 29);
		paneSpheroid.add(btnBackToMenu);
		
		JButton btnRunAnalysis = new JButton("Run Analysis");
		btnRunAnalysis.setFont(new Font("Gadugi", Font.BOLD, 14));
		btnRunAnalysis.setBounds(10, 406, 279, 29);
		paneSpheroid.add(btnRunAnalysis);
	}
}
