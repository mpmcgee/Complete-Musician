import javax.swing.*;
public abstract class Component {
    protected int duration;
    protected int elapsed = 0;
    protected String componentName;

    //returns time spent on this component
    public int getElapsed() {
        return this.elapsed;
    }

    //sets time spent on this component
    public void setElapsed(int elapsed) {
        this.elapsed = elapsed;
    }

    //gets the duration for this component
    public int getDuration() {
        return this.duration;
    }

    //get the name of this component
    public String getComponentName() {
        return this.componentName;
    }

    //returns a JPanel unique to the component
    public abstract JPanel getPanel(User user);


    public String getPayload() {
        return this.getComponentName() + "," + this.getDuration();
    }


    public abstract LinkedList getSongsPracticed();
}
