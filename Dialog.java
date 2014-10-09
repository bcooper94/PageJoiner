/**
* Created with Simple GUI Extension for BlueJ
* http://home.pf.jcu.cz/~gbluej/
*/
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import javax.swing.border.Border;
import javax.swing.*;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Window;

/**
 * Create a JDialog GUI for a PageJoiner.
 */
public class Dialog extends JDialog
{
    private JButton btnCancel;
    private JButton btnOK;
    private JCheckBox checkbox1;
    private JLabel lblTitle;
    private JList<String> listBox;
    private JPanel panel1;
    private PageJoiner joiner;
   
    /** 
     * Create this dialog from a list of Strings and a PageJoiner.
     * @param lines List of Strings
     * @param joiner a PageJoiner to create a GUI for
     */
    public Dialog(
        Vector<String> lines, 
        PageJoiner joiner)
    {
        DialogMouseListener okButton = null;
        DialogMouseListener cancelButton = null;
        this.joiner = joiner;

        this.setModal(true);
        this.setTitle("Dialog");
        this.setSize(500, 400);
 
        //pane with null layout
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(500, 400));
        contentPane.setBackground(new Color(192, 192, 192));
 
        btnCancel = new JButton();
        btnCancel.setBounds(391, 350, 90, 35);
        btnCancel.setBackground(new Color(214, 217, 223));
        btnCancel.setForeground(new Color(0, 0, 0));
        btnCancel.setEnabled(true);
        btnCancel.setFont(new Font("DejaVu Sans", 0, 12));
        btnCancel.setText("Cancel");
        btnCancel.setVisible(true);
        btnCancel.addMouseListener(new DialogMouseListener(btnCancel));

 
        btnOK = new JButton();
        btnOK.setBounds(288, 350, 90, 35);
        btnOK.setBackground(new Color(214, 217, 223));
        btnOK.setForeground(new Color(0, 0, 0));
        btnOK.setEnabled(true);
        btnOK.setFont(new Font("DejaVu Sans", 0, 12));
        btnOK.setText("OK");
        btnOK.setVisible(true);
        btnOK.addMouseListener(new DialogMouseListener(btnOK));
 
        checkbox1 = new JCheckBox();
        checkbox1.setBounds(16, 350, 130, 35);
        checkbox1.setBackground(new Color(214, 217, 223));
        checkbox1.setForeground(new Color(0, 0, 0));
        checkbox1.setEnabled(true);
        checkbox1.setFont(new Font("DejaVu Sans", 0, 12));
        checkbox1.setText("Retain hyphens");
        checkbox1.setVisible(true);
 
        lblTitle = new JLabel();
        lblTitle.setBounds(7, 5, 279, 16);
        lblTitle.setBackground(new Color(214, 217, 223));
        lblTitle.setForeground(new Color(0, 0, 0));
        lblTitle.setEnabled(true);
        lblTitle.setFont(new Font("DejaVu Sans", 0, 12));
        lblTitle.setText("Select lines that should NOT be joined.");
        lblTitle.setVisible(true);
 
        listBox = new JList<String>(lines);
        listBox.setBounds(5, 29, 477, 311);
        listBox.setBackground(new Color(255, 255, 255));
        listBox.setForeground(new Color(0, 0, 0));
        listBox.setEnabled(true);
        listBox.setFont(new Font("Courier", 0, 12));
        listBox.setVisible(true);
        JScrollPane scrollPane = new JScrollPane(listBox);
        scrollPane.setBounds(5, 29, 477, 311);
       
        panel1 = new JPanel(null);
        panel1.setBorder(BorderFactory.createEtchedBorder(1));
        panel1.setBounds(4, 5, 491, 390);
        panel1.setBackground(new Color(214, 217, 223));
        panel1.setForeground(new Color(0, 0, 0));
        panel1.setEnabled(true);
        panel1.setFont(new Font("DejaVu Sans", 0, 12));
        panel1.setVisible(true);
 
        //adding components to contentPane panel
        panel1.add(btnCancel);
        panel1.add(btnOK);
        panel1.add(checkbox1);
        panel1.add(lblTitle);
        panel1.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(panel1);
 
        //adding panel to JFrame and seting of window position and close operation
        this.add(contentPane);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();
    }
   
    /**
     * Accessor to checkbox state.
     * @return whether checkbox is selected or not
     */
    public boolean retainHyphens()
    {
        return checkbox1.isSelected();
    }

    /**
     * Create a MouseListener to provide Dialog button functionality.
     */
    private class DialogMouseListener implements MouseListener
    {
        private JButton button;

        public DialogMouseListener(JButton button)
        {
            this.button = button;
        }

        public void mouseClicked(MouseEvent event)
        {
            ArrayList<String> mergedLines = null;

            // If OK button is clicked
            if (button.getText().equals("OK"))
            {
                joiner.getMergerInput(listBox.getSelectedIndices());
                mergedLines = joiner.joinLines(retainHyphens());
                joiner.writeToFile(mergedLines);
                dispose();
            }
            // If Cancel button is clicked
            else if (button.getText().equals("Cancel"))
            {
                dispose();
            }
        }

        public void mouseEntered(MouseEvent event)
        {

        }

        public void mouseExited(MouseEvent event)
        {

        }

        public void mousePressed(MouseEvent event)
        {

        }

        public void mouseReleased(MouseEvent event)
        {

        }
    }
}