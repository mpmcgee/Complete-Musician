import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Technique extends Component {


    protected int maxBpm;
    protected int minBpm;

    //null constructor
    public Technique(){}

    //parametrized constructor
    public Technique(int duration) {

        this.componentName = "Technique";
        this.duration = duration;
        this.maxBpm = 0;
        this.minBpm = 0;
    }

    @Override
    public JPanel getPanel(User user) {
        //labels, fields, and buttons
        JLabel max = new JLabel("Max BPM for this session?");
        JLabel min = new JLabel("Min BPM for this session?");
        JTextField maxField = new JTextField(3);
        JTextField minField = new JTextField(3);
        JButton submit = new JButton("Submit");
        JLabel status = new JLabel("");
        status.setForeground(Color.red);

        //create panels and layout and add them
        JPanel p1 = new JPanel(new GridLayout(2, 1));
        p1.add(min);
        p1.add(minField);

        JPanel p2 = new JPanel(new GridLayout(3, 1));
        p2.add(max);
        p2.add(maxField);
        p2.add(status);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(p1, BorderLayout.NORTH);
        panel.add(p2, BorderLayout.CENTER);
        panel.add(submit, BorderLayout.SOUTH);

        //submit button listener
        submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //clear status
                status.setText("");
                //call set method using values from min and max BPM textfields
                if (!minField.getText().equals("") || !maxField.getText().equals("")) {
                    try {
                        setMinBPM(Integer.parseInt(minField.getText()));
                        setMaxBPM(Integer.parseInt(maxField.getText()));
                    } catch (NumberFormatException exception) {
                        //if values empty or not integers, print a message and do not set values
                        status.setText("Please enter whole numbers");
                    }
                }

            }
        });

        //return this panel
        return panel;

    }

    //set maxBPM
    public void setMaxBPM(int maxBpm) {
        this.maxBpm = maxBpm;
    }

    //set minBPM
    public void setMinBPM(int minBpm) {
        this.minBpm = minBpm;
    }

    //getMaxBPM
    public int getMaxBPM() {
        return this.maxBpm;
    }

    //getMinBPM
    public int getMinBPM() {
        return this.minBpm;
    }

    //return null for promised getSongsPracticed method from abstract parent class
    @Override
    public LinkedList getSongsPracticed() {
        return null;
    }

    //return payload for this child of abstract object
    @Override
    public String getPayload() {
        return (minBpm + " " + maxBpm);
    }


}
