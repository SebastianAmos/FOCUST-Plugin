package clcm.focust;




import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.*;


@SuppressWarnings("serial")
public class MyPanel extends JPanel {

    public MyPanel() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbcHeader = new GridBagConstraints();
        gbcHeader.gridx = 0;
        gbcHeader.gridy = 0;
        gbcHeader.gridwidth = 2;
        gbcHeader.anchor = GridBagConstraints.WEST;
        //gbcHeader.insets = new Insets(5, 5, 5, 5);

        JLabel header = new JLabel("Process a dataset:");
        add(header, gbcHeader);

        GridBagConstraints gbcHeader2 = new GridBagConstraints();
        gbcHeader2.gridx = 2;
        gbcHeader2.gridy = 0;
        gbcHeader2.gridwidth = 4;
        gbcHeader2.anchor = GridBagConstraints.WEST;
        //gbcHeader2.insets = new Insets(5, 5, 5, 5);

        JLabel header2 = new JLabel("You must have optimised your segmentation parameters.");
        add(header2, gbcHeader2);
        
        
        GridBagConstraints gbcHelpBtn = new GridBagConstraints();
        gbcHelpBtn.gridx = 8;
        gbcHelpBtn.gridy = 0;
        gbcHelpBtn.gridwidth = 1;
        gbcHelpBtn.anchor = GridBagConstraints.WEST;
        
        JButton btnHelp = new JButton("Help");
        add(btnHelp, gbcHelpBtn);
        

        GridBagConstraints gbcInputlbl = new GridBagConstraints();
        gbcInputlbl.gridx = 0;
        gbcInputlbl.gridy = 1;
        gbcInputlbl.gridwidth = 2;
        gbcInputlbl.anchor = GridBagConstraints.WEST;
        gbcInputlbl.insets = new Insets(5, 5, 5, 5);

        JLabel lblInput = new JLabel("Select an Input Directory:");
        add(lblInput, gbcInputlbl);
        
        
        GridBagConstraints gbcInputTxt = new GridBagConstraints();
        gbcInputTxt.gridx = 3;
        gbcInputTxt.gridy = 1;
        gbcInputTxt.gridwidth = 4;
        gbcInputTxt.anchor = GridBagConstraints.WEST;
        gbcInputTxt.insets = new Insets(5, 5, 5, 5);
        
        JButton btnInput = new JButton("Browse");
        add(btnInput, gbcInputTxt);
        
        
        
        
    }
/*
    public void addComboBoxPanel() {
        GridBagConstraints gbcComboBoxPanel = new GridBagConstraints();
        gbcComboBoxPanel.gridx = 0;
        gbcComboBoxPanel.gridy = 0;
        gbcComboBoxPanel.gridwidth = 4;
        gbcComboBoxPanel.anchor = GridBagConstraints.WEST;
        gbcComboBoxPanel.insets = new Insets(5, 5, 5, 5);

        JPanel comboBoxPanel = new JPanel(new GridBagLayout());
        comboBoxPanel.setBorder(BorderFactory.createEtchedBorder());

        GridBagConstraints gbcComboBoxLabel = new GridBagConstraints();
        gbcComboBoxLabel.gridx = 0;
        gbcComboBoxLabel.gridy = 0;
        gbcComboBoxLabel.gridwidth = 1;
        gbcComboBoxLabel.anchor = GridBagConstraints.WEST;
        gbcComboBoxLabel.insets = new Insets(5, 5, 5, 5);

        JLabel comboBoxLabel = new JLabel("Select an option:");
        comboBoxPanel.add(comboBoxLabel, gbcComboBoxLabel);

        GridBagConstraints gbcComboBox = new GridBagConstraints();
        gbcComboBox.gridx = 1;
        gbcComboBox.gridy = 0;
        gbcComboBox.gridwidth = 1;
        gbcComboBox.anchor = GridBagConstraints.WEST;
        gbcComboBox.insets = new Insets(5, 5, 5, 5);

        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});
        comboBoxPanel.add(comboBox, gbcComboBox);

        add(comboBoxPanel, gbcComboBoxPanel);
    }
*/
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("GridBagLayout Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridBagLayout());
            
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.anchor = GridBagConstraints.WEST;

            MyPanel panel = new MyPanel();
            //panel.addComboBoxPanel();
       
            frame.add(panel, c);
            frame.setPreferredSize(new Dimension(800, 485));
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
