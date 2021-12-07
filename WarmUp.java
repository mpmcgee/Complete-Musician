import javax.swing.*;

public class WarmUp extends Component {

    //parametrized constructor
    public WarmUp(int duration) {
        this.componentName = "Warm up";
        this.duration = duration;
    }

    //return an empty jPanel to fulfill promised method in abstract parent class
    @Override
    public JPanel getPanel(User user) {
        return new JPanel();

    }

    //return null to fulfill promised method in abstract parent class
    @Override
    public LinkedList getSongsPracticed() {
        return null;
    }
}
