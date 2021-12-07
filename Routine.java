import java.util.ArrayList;

public class Routine {
    protected String instrument;
    protected String nickname;
    protected ArrayList<Component> components;

    //parametrized constructor
    public Routine(String instrument, String nickname) {
        this.instrument = instrument;
        this.nickname = nickname;
    }

    //return instrument
    public String getInstrument() {
        return instrument;
    }

    //set instrument
    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    //return routine nickname
    public String getNickname() {
        return this.nickname;
    }


    //return components
    public ArrayList<Component> getComponents() {
        return this.components;
    }

    //set components
    public void setComponents(ArrayList<Component> components) {
        this.components = components;
    }

    //return string data of attributes
    public String getPayload() {
        return this.getNickname() + "," + this.getInstrument();
    }


}
