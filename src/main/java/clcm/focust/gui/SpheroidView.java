package clcm.focust.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Font;

public class SpheroidView extends JFrame {

	private JPanel paneSpheroid;

	/**
	 * Launch the application.
	 */
/*	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpheroidView frame = new SpheroidView();
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
	public SpheroidView() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SpheroidView.class.getResource("/ui/resources/icon2.png")));
		setTitle("FOCUST: Spheroid Analysis");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	}

}
