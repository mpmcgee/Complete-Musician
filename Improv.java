import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Improv extends Component {
    //notes attribute
    protected String notes;

    //constructor
    public Improv(int duration) {

        this.componentName = "Improv";
        this.duration = duration;
        this.notes = "None";
    }

    //returns JPanel for user to enter notes during this segment of their session
    @Override
    public JPanel getPanel(User user) {
        //labels, buttons, text area, and scroll pane
        JLabel notesLabel = new JLabel("Notes: ");
        JTextArea notesField = new JTextArea(5, 20);
        notesField.setLineWrap(true);
        JScrollPane jp = new JScrollPane(notesField);
        JButton submit = new JButton("Submit");

        //panels and layout
        JPanel p1 = new JPanel(new GridLayout(1, 1));
        p1.add(notesLabel);

        JPanel p2 = new JPanel(new GridLayout(1, 1));
        p2.add(jp);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(p1, BorderLayout.NORTH);
        panel.add(p2, BorderLayout.CENTER);
        panel.add(submit, BorderLayout.SOUTH);

        //listener for submit button
        submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //sets object notes equal to content in text area
                setNotes(notesField.getText());
            }
        });

        //return this panel
        return panel;

    }

    //set notes
    public void setNotes(String notes) {
        this.notes = notes;
    }

    //get notes
    public String getNotes() {
        return this.notes;
    }

    //return payload
    public String getPayload() {
        return this.notes;
    }

    //return null for songs practiced method promised in abstract class
    @Override
    public LinkedList getSongsPracticed() {
        return null;
    }
}
