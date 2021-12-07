import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RepertoirePanel extends JPanel {
    User user;
    //arraylist to hold band names
    ArrayList<String> bands = new ArrayList<>();
    //hashmap to hold checkboxes and string values
    Map<String, JCheckBox> checkBoxMap = new HashMap<>();
    //arraylist to hold selected repertoire objects
    ArrayList<Repertoire> selectedRepertoires = new ArrayList<>();
    //array list to hold checkboxes for songs
    ArrayList<JCheckBox> songCheckBoxes = new ArrayList();

    //labels, panels, and buttons
    JLabel songInfo = new JLabel("");
    JPanel checkboxesPanel = new JPanel();
    JButton deleteButton = new JButton("Delete");
    JButton addSongButton = new JButton("Add Song");
    JButton backButton = new JButton("Back");
    JPanel addSongPanel = new JPanel();
    JButton saveButton = new JButton("Save");



    //constructor
    public RepertoirePanel(User user) {
        this.user = user;
        //buttons, labels, and panels
        JButton listSongsButton = new JButton("List Songs");
        JLabel title = new JLabel("Select Your Bands:");
        title.setHorizontalAlignment(JLabel.CENTER);
        JPanel p1 = new JPanel(new GridLayout(0, 1));
        JPanel p2 = new JPanel(new GridLayout(0, 3));
        JPanel p3 = new JPanel();

        //add content to panels and set layout
        p1.add(title);
        p1.add(getBandCheckboxPanel());

        p2.add(songInfo);
        p3.add(listSongsButton);
        p3.add(addSongButton);

        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.add(p1, BorderLayout.NORTH);
        panel.add(p2, BorderLayout.CENTER);
        panel.add(p3, BorderLayout.SOUTH);

        //add this panel
        add(panel);

        //addSongButton listener
        addSongButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //refresh content
                refresh();
                //remove all items from p1 and p2
                p1.removeAll();
                p2.removeAll();
                panel.remove(checkboxesPanel);
                //add new label to panel 1
                p1.add(new JLabel("Add a new song"));
                //get addSong panel and add it to p1
                JPanel addSong = addSong();
                p1.add(addSong);
                //remove addSong button
                p3.remove(addSongButton);
                //remove list button
                p3.remove(listSongsButton);
                //add backButton to p3
                p3.add(backButton);
                //write new song content
                try {
                    user.writeStats();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                //refresh content
                refresh();
            }
        });

        //backButtonListener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //refresh content
                refresh();
                //remove all items from p1 and p2
                p1.removeAll();
                p2.removeAll();
                //add title and bandCheckBoxPanel to p1
                p1.add(title);
                setSelectedRepertoires();
                p1.add(getBandCheckboxPanel());
                //remove back button from p3
                p3.remove(backButton);
                //add list song and add song buttons
                p3.add(listSongsButton);
                p3.add(addSongButton);
                //remove delete button and checkboxes panel with songs
                p3.remove(deleteButton);
                panel.remove(checkboxesPanel);
                //refresh content
                refresh();
            }

        });

        //listSongsButtonListener
        listSongsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
                //remove all content from p2
                p2.removeAll();
                //remove previously listed songs
                panel.remove(checkboxesPanel);
                //set the selected repertoires
                setSelectedRepertoires();
                //if at least one repertoire is selected
                if (selectedRepertoires.size() > 0) {
                    //get panel with songs for selected repertoire
                    checkboxesPanel = printSongs();
                    //add this panel
                    panel.add(checkboxesPanel, BorderLayout.CENTER);
                    //add a delete button
                    p3.add(deleteButton);
                  //if no repertoire is selected
                } else {
                    //remove delete button
                    p3.remove(deleteButton);
                    //remove any previously listed songs
                    panel.remove(checkboxesPanel);
                }
                refresh();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //try to delete a song and update the user
                try {
                    deleteSongs();
                    updateUser();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                //remove all items from panel 2
                p2.removeAll();
                //set selected repertoire
                setSelectedRepertoires();
                //refresh panel
                refresh();
                //if repertoire is selected, print items
                if (selectedRepertoires.size() > 0) {
                    checkboxesPanel = printSongs();
                    panel.add(checkboxesPanel, BorderLayout.CENTER);
                    p3.add(deleteButton);
                } else {
                    //otherwise remove delete button and checkboxes
                    p3.remove(deleteButton);
                    panel.remove(checkboxesPanel);
                }
                refresh();
            }
        });

        //saveButton listener
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    //write user data
                    try {
                        user.writeStats();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    //remove save button
                    p3.remove(saveButton);
                    p1.removeAll();
                    //indicate that song was saved
                    p1.add(new JLabel("Song Saved"));
                }
            });
        }
        //write stats for this user
        public void updateUser() throws IOException {
            this.user.writeStats();
        }

        //delete songs
        public void deleteSongs() throws IOException {
            //for each JCheckbox in songCheckBoxes
            for (JCheckBox checkBox : this.songCheckBoxes) {
                //if this checkbox is selected
                if (checkBox.isSelected()) {
                    //for each repertoire attached to the user
                    for(Repertoire repertoire : user.getRepertoires()) {
                        //if selected song corresponding to JCheckbox exists in said repertoire
                        if (repertoire.getSongs().search(checkBox.getText()) != null) {
                            //delete this song from said repertoire
                            repertoire.getSongs().deleteSong(checkBox.getText());
                        }
                    }
                }
            }
            //write user stats to files
            this.user.writeStats();
        }

        //generate panel of checkboxes for each repertoire by band name
        public JPanel getBandCheckboxPanel() {
            //create a new JPanel
            JPanel panel = new JPanel();
            //if the user has a repertoire
            if (this.user.getRepertoires() != null) {
                //for each one that they have
                for (Repertoire repertoire : this.user.getRepertoires()) {
                    //if the arraylist of bands does not contain this band name
                    if (!bands.contains(repertoire.getBand())) {
                        //add it to the list
                        bands.add(repertoire.getBand());
                    }
                }

                //iterate through user repertoires
                for (int i = 0; i < this.user.getRepertoires().size(); i++) {
                    //put a string with the value of i and checkbox with the band name in the checkbox hashmap
                    checkBoxMap.put(String.valueOf(i), new JCheckBox(bands.get(i)));
                }
                //iterate through hashmap
                for (int i = 0; i < checkBoxMap.size(); i++) {
                    //add checkbox to the panel using the key value of i as an identifier
                    panel.add(checkBoxMap.get(String.valueOf(i)));
                }
            }
            //return this panel
            return panel;
        }

    //set which repertoires are selected
    public void setSelectedRepertoires() {
        //create an array list of repertoire objects
        ArrayList<Repertoire> repertoires = new ArrayList<>();
        //for each JCheckbox in the checkBoxMap
        for (JCheckBox checkbox : checkBoxMap.values()) {
                //if said checkbox is selected
                if (checkbox.isSelected()) {
                    //get the repertoire associated with this band from the user object and add it to the List
                    repertoires.add(this.user.getRepertoireByBand(checkbox.getText()));
                }
        }
        //set selectedRepertoires equal to repertoires List
        this.selectedRepertoires = repertoires;
    }

    //print the songs
    public JPanel printSongs() {
        //create a new panel with 1 col and infinite rows
        JPanel p1 = new JPanel(new GridLayout(0, 1));
        //for each repertoire in selectedRepertoires
        for (Repertoire repertoire : this.selectedRepertoires) {
            //get the LLSong of objects for said repertoire and store them in a linked list
            LLSong songs = repertoire.getSongs();
            //get the song checkboxes for this LinkedList of songs
            this.songCheckBoxes = songs.getSongCheckBoxes();
            //add each of these checkboxes to p1 and refresh
            for (JCheckBox checkBox : this.songCheckBoxes) {
                p1.add(checkBox);
                refresh();
            }
        }
        //create panel and layout and return panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(p1, BorderLayout.CENTER);
        return panel;
    }

    //add song panel
    public JPanel addSong() {
        //create a new JPanel
        JPanel panel = new JPanel(new BorderLayout());
        //get and add the addSong panel for this user to the panel
        panel.add(this.user.getAddSongPanel(), BorderLayout.NORTH);
        //return this panel
        return panel;
    }

    public void refresh() {
        invalidate();
        validate();
        repaint();
    }
}
