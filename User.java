import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    protected String name;
    protected String userName;
    protected String password;
    protected Routine selectedRoutine;
    protected ArrayList<Routine> routines;
    protected ArrayList<Repertoire> repertoires;
    protected ArrayList<Session> sessions;
    protected User next;

    //null constructor
    public User(){
        this.name = "";
        this.userName = "";
        this.password = "";
        this.next = null;
        this.sessions = new ArrayList<>();
    }

    //parametrized constructor
    public User(String name, String userName, String password, User next) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.sessions = new ArrayList<>();
        this.routines = new ArrayList<>();
        this.next = next;
    }

    //parametrized constructor for LL
    public User(String name, User next){
        this.name = name;
        this.userName = "";
        this.password = "";
        this.sessions = new ArrayList<>();
        this.routines = new ArrayList<>();
        this.next = next;
    }

    //set selected routine
    public void setSelectedRoutine(Routine routine) {
        this.selectedRoutine = routine;
    }

    //set selected routine if its name matches the string value parameter
    public void setSelectedRoutineByName(String routineName) {
        for (Routine routine : this.getRoutines()) {
            if (routine.getNickname().equals(routineName)) {
                setSelectedRoutine(routine);
            }
        }
    }

    //set sessions
    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }

    //panel to add a song
    public JPanel getAddSongPanel() {
        //Create labels
        JLabel title = new JLabel("Add a new song:");
        JButton submitButton = new JButton("Submit");
        JLabel bandNameLabel = new JLabel("Band Name:");
        JLabel songNameLabel = new JLabel("Song Title:");
        JLabel keyLabel = new JLabel("Key");
        JLabel genreLabel = new JLabel("Genre");
        JLabel status = new JLabel("");
        status.setForeground(Color.red);

        //create text fields
        JTextField bandNameField = new JTextField(15);
        JTextField songNameField = new JTextField(15);
        JTextField keyField = new JTextField(15);
        JTextField genreField = new JTextField(15);

        //create panels and layout and add
        JPanel p1 = new JPanel();
        p1.add(title);

        JPanel p2 = new JPanel(new GridLayout(0, 2));
        p2.add(bandNameLabel);
        p2.add(bandNameField);
        p2.add(songNameLabel);
        p2.add(songNameField);
        p2.add(keyLabel);
        p2.add(keyField);
        p2.add(genreLabel);
        p2.add(genreField);

        JPanel p3 = new JPanel(new GridLayout(0, 1));
        p3.add(status);
        p3.add(submitButton);

        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.add(p1, BorderLayout.NORTH);
        panel.add(p2, BorderLayout.CENTER);
        panel.add(p3, BorderLayout.SOUTH);

        //submitButton listener
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if no fields are empty
                if (!bandNameField.getText().equals("") && !songNameField.getText().equals("") && !keyField.equals("")
                        && !genreField.getText().equals("")) {
                    //if a repertoire does not exist for the user with band name that was entered for the new song
                    if (getRepertoireByBand(bandNameField.getText()) == null) {
                        //print message
                        status.setText("You must enter a band you have already added");
                    } else {
                        //otherwise get the user's repertoire and append the new song to the corresponding songs LL
                        Repertoire repertoire = getRepertoireByBand(bandNameField.getText());
                        repertoire.getSongs().append(songNameField.getText(), keyField.getText(), genreField.getText(), bandNameField.getText());
                        try {
                            //write user info to files
                            writeStats();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        //print message
                        status.setText("Song added");
                        //remove all content from panel
                        panel.removeAll();
                    }
                }
                else{
                    //if a field is empty, print message
                    status.setText("All fields required");
                }
            }
        });

        //return this panel
        return  panel;
    }

    //panel to add a new practice routine
    public JPanel addRoutinePanel() {

        //add labels
        JLabel title = new JLabel("Add a New Routine");
        JLabel nicknameLabel = new JLabel("Name for routine:");
        JLabel instrumentLabel = new JLabel("Instrument");
        JLabel timePerComponentLabel = new JLabel("Time per component (minutes):");
        JLabel timeWarmUpLabel = new JLabel("Warm up");
        JLabel techniqueTimeLabel = new JLabel("Technique");
        JLabel songsTimeLabel = new JLabel("Repertoire");
        JLabel improvTimeLabel = new JLabel("improv");
        JButton submit = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        //add fields
        JTextField nicknameField = new JTextField(15);
        JTextField instrumentField = new JTextField(15);
        JTextField timeWarmUpField = new JTextField(4);
        JTextField timeTechniqueField = new JTextField(4);
        JTextField timeSongsField = new JTextField(4);
        JTextField timeImprovField = new JTextField(4);

        //add panels and layout
        JPanel p1 = new JPanel();
        p1.add(title);

        JPanel p2 = new JPanel(new GridLayout(2, 2));
        p2.add(nicknameLabel);
        p2.add(instrumentLabel);
        p2.add(nicknameField);
        p2.add(instrumentField);

        JPanel p3 = new JPanel();
        p3.add(timePerComponentLabel);

        JPanel p4 = new JPanel(new GridLayout(4, 2));
        p4.add(timeWarmUpLabel);
        p4.add(timeWarmUpField);
        p4.add(techniqueTimeLabel);
        p4.add(timeTechniqueField);
        p4.add(songsTimeLabel);
        p4.add(timeSongsField);
        p4.add(improvTimeLabel);
        p4.add(timeImprovField);

        JPanel panel1 = new JPanel(new BorderLayout());
        panel1.add(p1, BorderLayout.NORTH);
        panel1.add(p2, BorderLayout.SOUTH);

        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.add(p3, BorderLayout.NORTH);
        panel2.add(p4, BorderLayout.SOUTH);

        JPanel panel3 = new JPanel(new FlowLayout());
        panel3.add(cancelButton);
        panel3.add(submit);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(panel1, BorderLayout.NORTH);
        panel.add(panel2, BorderLayout.CENTER);
        panel.add(panel3, BorderLayout.SOUTH);

        //cancelButton listener
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //remove all from this panel
                panel.removeAll();
                //add the getSelectedRoutine panel
                panel.add(getSelectRoutinePanel());
            }
        });

        //submit button listener
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if no field is empty
                if (!instrumentField.getText().equals("") && !nicknameField.getText().equals("") && !timeWarmUpField.equals("")
                        && !timeTechniqueField.getText().equals("") && !timeSongsField.getText().equals("")
                        && !timeImprovField.getText().equals("")) {
                    //create new routine and components
                    Routine routine = new Routine(instrumentField.getText(), nicknameField.getText());
                    WarmUp warmup = new WarmUp(Integer.parseInt(timeWarmUpField.getText()) * 60);
                    Technique technique = new Technique(Integer.parseInt(timeTechniqueField.getText()) * 60);
                    SongPractice songPractice = new SongPractice(Integer.parseInt(timeSongsField.getText()) * 60);
                    Improv improv = new Improv(Integer.parseInt(timeImprovField.getText()) * 60);
                    //add components to arraylist
                    ArrayList<Component> componentArrayList = new ArrayList<>();
                    componentArrayList.add(warmup);
                    componentArrayList.add(technique);
                    componentArrayList.add(songPractice);
                    componentArrayList.add(improv);

                    //set routine equal to new array list
                    routine.setComponents(componentArrayList);
                    //append the routine to the user
                    appendRoutine(routine);
                    //remove all panels
                    panel1.removeAll();
                    panel2.removeAll();
                    panel3.removeAll();
                }
            }
        });
        //return this panel
        return panel;
    }

    //returns panel to select routine
    public JPanel getSelectRoutinePanel() {
        //labels, buttons, panels, and formatting
        JLabel title = new JLabel("Select a Routine");
        JButton addRoutineButton = new JButton("Add routine");
        ButtonGroup group = new ButtonGroup();
        title.setHorizontalAlignment(JLabel.CENTER);
        GridLayout spacedGrid = new GridLayout(0, 1);
        spacedGrid.setVgap(8);
        JButton selectButton = new JButton("Select Routine");
        selectButton.setBounds(200, 100, 100, 20);
        Map<String, JRadioButton> radioButtonMap = new HashMap<>();
        ArrayList<String> routineNames = new ArrayList<>();
        JPanel p1 = new JPanel(spacedGrid);
        JPanel p2 = new JPanel(new GridLayout(0, 1));
        JPanel p3 = new JPanel();

        //if the user has at least one routine
        if (this.getRoutines() != null) {
            //for each routine that this user has
            for (Routine routine : this.getRoutines()) {
                //if the routineNames arraylist does not contain this routine nickname
                if (!routineNames.contains(routine.getNickname())) {
                    //add this routine nickname to the routine names ArrayList
                    routineNames.add(routine.getNickname());
                }
            }

            //iterate over routines attached to user
            for (int i = 0; i < this.getRoutines().size(); i++) {
                //add a string with the value of i and a radio button with the routine name for this routine
                radioButtonMap.put(String.valueOf(i), new JRadioButton(routineNames.get(i)));
                //add these radio buttons to p2
                p2.add(radioButtonMap.get(String.valueOf(i)));
            }

            //add title to p1
            p1.add(title);

            //iterate over routines
            for (int i = 0; i < routines.size();  i++) {
                //add radio button to group
                group.add(radioButtonMap.get(String.valueOf(i)));
                //add radio button to panel
                p2.add(radioButtonMap.get(String.valueOf(i)));
            }


        } else {
            //otherwise, just add the title to p1
            p1.add(title);
        }

        //add the addRoutine button to p3
        p3.add(addRoutineButton);

        //create panels and layout
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.add(p1, BorderLayout.NORTH);
        panel.add(p2, BorderLayout.CENTER);
        panel.add(p3, BorderLayout.SOUTH);


        //addRoutine button listener
        addRoutineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //remove all from p1, p2, p3
                p1.removeAll();
                p2.removeAll();
                p3.removeAll();
                p3.remove(selectButton);
                //add routine panel to p3
                p3.add(addRoutinePanel());
            }
        });

        //add event listeners to each radio button
        for (JRadioButton radioButton : radioButtonMap.values()) {
            radioButton.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                        //if button is selected, set selectedRoutine equal to this routine
                        if (radioButton.isSelected()) {
                            setSelectedRoutineByName(radioButton.getText());
                        }
                    }
            });
        }

        //selectButton listener
        selectButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //reset routinename
                String routineName = "";
                //if radio button for routine name is selected, set routine name equal to this value
                for (int i = 0; i < routines.size(); i++) {
                    if (radioButtonMap.get(String.valueOf(i)).isSelected()) {
                        routineName =  routineNames.get(i);
                    }
                }
                //if routineName is not null
                if (routineName != null) {
                    //set selected routine by name
                    setSelectedRoutineByName(routineName);
                    //remove all from p1, p2, p3
                    p1.removeAll();
                    p2.removeAll();
                    p3.removeAll();
                    //set and add title as routine name
                    title.setText(getSelectedRoutine().getNickname());
                    p1.add(title);
                }
            }
        });
        //return this panel
        return panel;

    }

    //return selected routine
    public Routine getSelectedRoutine() {
        return this.selectedRoutine;
    }

    //return name
    public String getName() {
        return this.name;
    }

    //set name
    public void setName(String name) {
        this.name = name;
    }

    //return username
    public String getUserName() {
        return this.userName;
    }

    //set username
    public void setUserName(String userName) {
        this.userName = userName;
    }

    //return password
    public String getPassword() {
        return this.password;
    }

    //set password
    public void setPassword(String password) {
        this.password = password;
    }

    //return routines
    public ArrayList<Routine> getRoutines() {
        return this.routines;
    }

    //set routines
    public void setRoutines(ArrayList<Routine> routines) {
        this.routines = routines;
    }

    //append a routine
    public void appendRoutines(Routine routine) {
        this.routines.add(routine);
    }

    //set repertoire
    public void setRepertoires(ArrayList<Repertoire> repertoires) {
        this.repertoires = repertoires;
    }

    //return repertoire by band name
    public Repertoire getRepertoireByBand(String band) {
        //for each repertoire in this user object
        for (Repertoire repertoire : this.repertoires) {
            //if the band name of the repertoire equals the string passed into this method
            if (repertoire.getBand().equals(band)) {
                //return said repertoire
                return repertoire;
            }
        }
        //return null when no matches
        return null;
    }

    //return repertoires
    public ArrayList<Repertoire> getRepertoires() {
        return this.repertoires;
    }

    //return next for LL
    public User getNext() {
        return next;
    }

    //set next for LL
    public void setNext(User next) {
        this.next = next;
    }

    //append a routine
    public void appendRoutine (Routine routine) {
        this.routines.add(routine);
    }
    //return sessions
    public ArrayList<Session> getSessions() {
        return this.sessions;
    }

    //return payload
    public String getPayload(){
        // return a string representing contents
        String output = this.userName + ", ";
        output += this.password;
        return output;
    }

    //write data for user
    public void writeStats() throws IOException {
        //if the user has routines
        if (this.getRoutines()!=null) {
            //create a writer and write payload returned from each routine to user's routines file
            Writer wr = new FileWriter(this.getUserName() + "_RoutinesOutput.txt");
            for (Routine routine : this.getRoutines()) {
                wr.write(routine.getPayload() + "\n");
            }
            //clear and close the writer
            wr.flush();
            wr.close();

            //create a writer and write payload returned from each component for each routine in the user's components file
            wr = new FileWriter(this.getUserName() + "_componentsOutput.txt");
            for (Routine routine : this.getRoutines()) {
                if (routine.getComponents() != null) {
                    for (Component component : routine.getComponents())
                        wr.write(routine.getNickname() + "," + component.getPayload() + "\n");
                }
            }
            //clear and close the writer
            wr.flush();
            wr.close();
        }

        //if the user has repertoires
        if (this.getRepertoires()!=null) {
            //create a writer and write payload returned from each repertoire to user's repertoires file
            Writer wr = new FileWriter(this.getUserName() + "_repertoiresOutput.txt");
            for (Repertoire repertoire : this.getRepertoires()) {
                wr.write(repertoire.getPayload() + "\n");
            }

            //clear and close the writer
            wr.flush();
            wr.close();

            //create a writer and write payload returned from each song for each repertoire in the user's songs file
            wr = new FileWriter(this.getUserName() + "_songsOutput.txt");
            if (this.getRepertoires()!=null) {
                for (Repertoire repertoire : this.getRepertoires()) {
                    if (repertoire.getSongs() != null) {
                            wr.write(repertoire.getSongs().getAllWritePayload());
                    }
                }
            }
            //clear and close the writer
            wr.flush();
            wr.close();
        }

        //if the user has repertoires
        if (this.getSessions()!=null) {
            //create a writer and write payload returned from each session to user's sessions file
            FileWriter wr = new FileWriter(this.getUserName() + "_sessionsOutput.txt");
            for (Session session : this.getSessions()) {
                wr.write(session.getPayload());
            }
            //clear and close the writer
            wr.flush();
            wr.close();
        }

    }
}
