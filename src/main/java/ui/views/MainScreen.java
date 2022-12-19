package ui.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.JButton;

public class MainScreen extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen frame = new MainScreen();
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
	public MainScreen() {
		setTitle("FOCUST");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainScreen.class.getResource("/ui/resources/icon2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 806, 476);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Select a mode to begin:");
		lblNewLabel.setFont(new Font("Gadugi", Font.BOLD, 16));
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setBounds(20, 134, 192, 35);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Optimize");
		btnNewButton.setFont(new Font("Gadugi", Font.BOLD, 13));
		btnNewButton.setBounds(87, 169, 154, 51);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Spheroid Analysis");
		btnNewButton_1.setFont(new Font("Gadugi", Font.BOLD, 13));
		btnNewButton_1.setBounds(87, 247, 154, 51);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(MainScreen.class.getResource("/ui/resources/spheroidIcon0.2.png")));
		lblNewLabel_2.setBounds(10, 241, 77, 67);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("");
		lblNewLabel_2_1.setIcon(new ImageIcon(MainScreen.class.getResource("/ui/resources/singlecellicon.png")));
		lblNewLabel_2_1.setBounds(20, 302, 77, 67);
		contentPane.add(lblNewLabel_2_1);
		
		JButton btnNewButton_1_1 = new JButton("Single Cell Analysis");
		btnNewButton_1_1.setFont(new Font("Gadugi", Font.BOLD, 13));
		btnNewButton_1_1.setBounds(87, 309, 154, 51);
		contentPane.add(btnNewButton_1_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("");
		lblNewLabel_2_1_1.setIcon(new ImageIcon(MainScreen.class.getResource("/ui/resources/speckleIcon.png")));
		lblNewLabel_2_1_1.setBounds(20, 359, 77, 67);
		contentPane.add(lblNewLabel_2_1_1);
		
		JButton btnNewButton_1_1_1 = new JButton("Speckle Analysis");
		btnNewButton_1_1_1.setFont(new Font("Gadugi", Font.BOLD, 13));
		btnNewButton_1_1_1.setBounds(87, 371, 154, 51);
		contentPane.add(btnNewButton_1_1_1);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setFont(new Font("Gadugi", Font.BOLD, 13));
		btnHelp.setBounds(690, 11, 90, 35);
		contentPane.add(btnHelp);
		
		JLabel lblDetermineTheParameters = new JLabel("Determine the parameters for segmenting a new dataset.");
		lblDetermineTheParameters.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblDetermineTheParameters.setBounds(251, 176, 360, 35);
		contentPane.add(lblDetermineTheParameters);
		
		JLabel lblBatchProcessA = new JLabel("Batch process a dataset containing spheroids or organoids.");
		lblBatchProcessA.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBatchProcessA.setBounds(251, 254, 388, 35);
		contentPane.add(lblBatchProcessA);
		
		JLabel lblBatchProcessA_2 = new JLabel("Batch process a dataset that contains primary objects and secondary objects.");
		lblBatchProcessA_2.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBatchProcessA_2.setBounds(251, 316, 489, 35);
		contentPane.add(lblBatchProcessA_2);
		
		JLabel lblBatchProcessA_2_1 = new JLabel("Batch process a dataset that contains multiple secondary objects per primary object. ");
		lblBatchProcessA_2_1.setFont(new Font("Gadugi", Font.PLAIN, 14));
		lblBatchProcessA_2_1.setBounds(251, 378, 522, 35);
		contentPane.add(lblBatchProcessA_2_1);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(MainScreen.class.getResource("/ui/resources/FullLogo2.png")));
		lblNewLabel_1.setBounds(10, 0, 445, 114);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("");
		lblNewLabel_2_2.setIcon(new ImageIcon(MainScreen.class.getResource("/ui/resources/iconquestionmark.png")));
		lblNewLabel_2_2.setBounds(30, 161, 43, 67);
		contentPane.add(lblNewLabel_2_2);
	}
}
