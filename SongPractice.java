import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SongPractice extends Component {

    protected LinkedList songsPracticed;

    public SongPractice(int duration) {

        this.componentName = "Repertoire Practice";
        this.duration = duration;
        this.songsPracticed = new LinkedList();
        this.songsPracticed.append("none");
    }

    //returns panel that sets songs practices on submit
    @Override
    public JPanel getPanel(User user) {
        //labels and buttons created
        JLabel songsLabel = new JLabel("Songs Practiced:");
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel panel = new JPanel();
        JButton submit = new JButton("Submit");
        //create a new arraylist of checkboxes
        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
        //add songsLabel to p1
        p1.add(songsLabel);

        //if user has repertoires
        if(user.getRepertoires() != null) {
            //for each repertoire the user has
            for (Repertoire repertoire : user.getRepertoires()) {
                //get the array of String song names and store
                String[] songNames = repertoire.getSongs().getNames();

                //iterate over songNames
                for (int i = 0; i < songNames.length; i++) {
                    //create a new checkbox with the songName element as text
                    JCheckBox checkBox = new JCheckBox(songNames[i]);
                    //add this checkbox to p2
                    p2.add(checkBox);
                    //add checkbox to checkBox ArrayList
                    checkBoxes.add(checkBox);
                }
            }
        }

        //create and add layout
        p2.setLayout(new GridLayout(0, 1));
        p2.add(submit);

        panel.add(p1);
        panel.add(p2);

        //submit button listener
        submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //for each checkbox in checkboxes ArrayList
                for (JCheckBox checkBox : checkBoxes) {
                    //if this checkbox is selected
                    if (checkBox.isSelected()) {
                        //create a new linked list of Strings
                        LinkedList songsPracticed = new LinkedList();
                        //append the text from the song name checkbox to this LinkedList
                        songsPracticed.append(checkBox.getText());
                        //set the songsPracticed for this object equal to this LinkedList
                        setSongsPracticed(songsPracticed);
                    }
                }
            }
        });

        //return this panel
        return panel;

    }

    //get songs practiced
    public LinkedList getSongsPracticed() {
        return this.songsPracticed;
    }

    //set songs practiced
    public void setSongsPracticed(LinkedList songsPracticed) {
        this.songsPracticed = songsPracticed;
    }


}
