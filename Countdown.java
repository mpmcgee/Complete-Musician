import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
class Countdown extends JPanel {
    //create a new timer object
    Timer timer = new Timer();
    //crete a new JLable to hold time
    JLabel jLabel = new JLabel();
    //user attribute
    User user;
    //remaining time
    int remaining;

    //constructor
    public Countdown(User user, Component selectedComponent) {
        //set the user
        this.user = user;
        //make panel visible
        setVisible(true);
        //add label for timer
        add(new JLabel("Remaining: "));
        //add label to panel
        add(jLabel);
        //calculate remaining time
        remaining = selectedComponent.getDuration() - selectedComponent.getElapsed();
        //set up the timer
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                //set text of jlabel equal to calculated remaining time
                jLabel.setText((String.format("%02d:%02d", remaining / 60, remaining % 60)));
                //subtract 1 from remaining
                remaining--;

                //if time is up
                if (remaining <= 0) {
                    //end timer
                    timer.cancel();
                    //set text for JLabel
                    jLabel.setText((String.format("%02d:%02d", remaining / 60, remaining % 60)));
                }
            //do this every second
            }
        }, 0, 1000);
    }

    //get remaining time
    public int getRemaining() {
        return this.remaining;
    }
}
