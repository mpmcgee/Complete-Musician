import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoutinePanel extends JPanel {
    User user;
    //JButtons
    JButton selectButton = new JButton("Select");
    JButton saveButton = new JButton("Save ");
    JButton startButton = new JButton("Start ");
    JButton pauseButton = new JButton("Pause");
    JButton skipButton = new JButton("Skip");
    //JLabels
    JLabel title = new JLabel("Select a Routine");
    JLabel componentName = new JLabel("Warm up");
    //ArrayList to hold components for this session
    ArrayList<Component> sessionComponents = new ArrayList<>();
    //group for radio buttons
    ButtonGroup group = new ButtonGroup();
    //ArrayList to routines
    ArrayList<String> routines = new ArrayList<>();
    //Hashmap to hold radio buttons and string labels
    Map<String, JRadioButton> radioButtonMap = new HashMap<>();
    //Countdown timer object
    Countdown countdown;
    //Variable to hold the component that is active at any particular time
    Component selectedComponent;
    //Session object for this practice session
    Session session;
    // counter
    int i = 0;

    //constructor
    RoutinePanel(User user) {
        this.user = user;
        //set the size of panel
        setSize(100, 200);
        //initialize with user
        initialize(user);
    }

    //finds the name of the selected routine
    public String getSelectedRoutineName() {
            //initialize routine name String variable
            String routineName = "";
            //iterate through routines
            for (int i = 0; i < routines.size(); i++) {
                //if the radioButton for this routine is selected
                if (radioButtonMap.get(String.valueOf(i)).isSelected()) {
                    //set routineName equal to the text value of this button
                    routineName =  routines.get(i);
                }
            }
            //return routineName
            return routineName;

        }

    //set the selected routine
    public void setSelectedRoutine(String routineName) {
        //for each routine attached to the user
        for (Routine routine : user.getRoutines()) {
            //if the nickname of the routine is equal to routineName value passed into function
            if (routine.getNickname().equals(routineName)) {
                //set the selectedRoutine for the user equal to said routine
                this.user.setSelectedRoutine(routine);
            }
        }
        //set the selectedComponent equal to the selected routine component with the index of the counter
        this.selectedComponent = this.user.getSelectedRoutine().getComponents().get(this.i);
    }

    //starts the countdown timer
    public Countdown startCountdown() {
        //creates new countdown with user and selected component parameters
        this.countdown = new Countdown(this.user, this.selectedComponent);
        //returns countdown object
        return this.countdown;
    }

    //pauseTimer
    public JLabel pauseTimer (int remaining) {
        /*set the elapsed time of the selected component equal to the duration of the selected component minus the
        remaining time*/
        selectedComponent.setElapsed(selectedComponent.duration - remaining);
        //create and return a new JLabel with text value of remaining time in MM:SS format
        return new JLabel((String.format("%02d:%02d", remaining / 60, remaining % 60)));
    }

    //update the time for a component
    public void updateTime(int remaining) {
        /*create and return a new JLabel with text value of remaining time in MM:SS format*/
        selectedComponent.setElapsed(selectedComponent.duration - remaining);
    }

    //refresh panel content
    public void refresh() {
        invalidate();
        validate();
        repaint();
    }

    //selects next component in user's selected routine
    public void moveToNextComponent() throws IOException {
        //if the counter is less than 3
        if (this.i < 3) {
            //add one to the counter
            this.i++;
            //set selected component equal to the component in the user's Arraylist of selected routines with the index of i
            this.selectedComponent = this.user.getSelectedRoutine().getComponents().get(this.i);
        }
        //otherwise save the session
        else {
            this.saveSession();
        }
    }

    //Saves information about practice session
    public void saveSession() throws IOException {
        //create an arraylist of Sessions attached to the user
        ArrayList<Session> currentSessions = user.getSessions();
        //add a new session with these components as a parameter
        currentSessions.add(new Session(sessionComponents));
        //set the user's sessions attribute equal to the currentSessions ArrayList
        user.setSessions(currentSessions);
        //write user data
        user.writeStats();
        //reset counter
        this.i = 0;
    }

    //starts the initial panel
    public JPanel initialize(User user){
            //center the title JLabel
            title.setHorizontalAlignment(JLabel.CENTER);
            //center the component name
            componentName.setHorizontalAlignment(JLabel.CENTER);
            //create panels and layout and add
            GridLayout spacedGrid = new GridLayout(0, 1);
            spacedGrid.setVgap(8);
            selectButton.setBounds(200, 100, 100, 20);
            JPanel p2 = new JPanel(new GridLayout(0, 1));

            JPanel p1 = new JPanel(spacedGrid);
            JPanel p3 = new JPanel();

            p1.add(this.user.getSelectRoutinePanel());
            p3.add(selectButton);
            p3.add(skipButton);
            refresh();

            JPanel panel = new JPanel(new BorderLayout(0, 20));
            panel.add(p1, BorderLayout.NORTH);
            panel.add(p2, BorderLayout.CENTER);
            panel.add(p3, BorderLayout.SOUTH);

            add(panel);

            //selectButton listener
            selectButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //if the user has selected a routine radio button
                    if(user.selectedRoutine != null) {
                        setSelectedRoutine(getSelectedRoutineName());
                        //remove all content from p1, p2, p3
                        p1.removeAll();
                        p2.removeAll();
                        p3.removeAll();
                        //set title text and add title to p1
                        title.setText(user.getSelectedRoutine().getNickname());
                        p1.add(title);
                        //add componentName to p1
                        p1.add(componentName);
                        //add start and skip buttons to p3
                        p3.add(startButton);
                        p3.add(skipButton);
                        //refresh panel content
                        refresh();
                    }
                }
            });

            //startButton listener
            startButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //get and set text for selectedCompoenent JLabel
                    componentName.setText(selectedComponent.getComponentName());
                    //if the component name is 'improv' (final component)
                    if(componentName.getText().equals("Improv")) {
                        //add a save button
                        p3.add(saveButton);
                    } else {
                        //otherwise add a skip button
                        p3.add(skipButton);
                    }
                    //add the countdown to p3
                    p3.add(startCountdown());
                    //remove start button
                    p3.remove(startButton);
                    //add pause button to p3
                    p3.add(pauseButton);
                    //refresh panel content
                    refresh();
                }
            });

            //skipButton listener
            skipButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //update elapsed time for component
                    updateTime(countdown.getRemaining());
                    //delete the timer
                    countdown.timer.cancel();
                    //remove component name from p1 and everything from p2 and p3
                    p1.remove(componentName);
                    p2.removeAll();
                    p3.removeAll();
                    //add selectedComponent to session components
                    sessionComponents.add(selectedComponent);
                    //try to move to the next component
                    try {
                        moveToNextComponent();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    //set text for selectedComponent
                    componentName.setText(selectedComponent.getComponentName());
                    //add component name, selected component panel
                    p1.add(componentName);
                    //add unique panel for current component
                    p2.add(selectedComponent.getPanel(user));
                    //add start button
                    p3.add(startButton);
                    //refresh panel
                    refresh();
                }
            });

            //pauseButton listener
            pauseButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    //remove countdown and pause button
                    p2.remove(countdown);
                    p2.remove(pauseButton);
                    //add JLabel with remaining time to display frozen time
                    p2.add(new JLabel("Remaining: "));
                    //pause the timer and update remaining
                    p2.add(pauseTimer(countdown.getRemaining()));
                    //add the start button
                    p2.add(startButton);
                    //delete the timer
                    countdown.timer.cancel();
                    //refresh panel
                    refresh();
                }
            });

            //saveButton listener
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //remove all from p1, p2, p3
                    p1.removeAll();
                    p2.removeAll();
                    p3.removeAll();
                    //add selectedComponent to sessionComponents
                    sessionComponents.add(selectedComponent);
                    //save session
                    try {
                        saveSession();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    //indicate that session was saved and for user to rest and come back for another session
                    p1.add(new JLabel("Routine Saved."));
                    p1.add(new JLabel("Rest and come back another time for more practice!"));
                    //refresh panel
                    refresh();
                }
            });

        //return this panel
        return panel;
    }
}


