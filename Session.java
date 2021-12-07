import java.util.ArrayList;
import java.util.Date;

public class Session {

    protected String date;
    protected int warmUpElapsed;
    protected int techniqueElapsed;
    protected int songsPracticeElapsed;
    protected int improvElapsed;
    protected int maxBPM;
    protected int minBPM;
    protected LinkedList songsPracticed;
    protected String improvNotes;
    protected ArrayList<Component> components;

    //null constructor
    public Session() {
        this.date = new Date().toString();
        setNewSession();
    }

    //constructor using components
    public Session(ArrayList<Component> components) {
        this.date = new Date().toString();
        this.components = components;
        setNewSession();
    }

    //initializes attributes using data from components
    public void setNewSession() {
        //get component values and store in corresponding attributes for this object
        this.warmUpElapsed = this.components.get(0).getElapsed();
        this.techniqueElapsed = this.components.get(1).getElapsed();
        this.songsPracticeElapsed = this.components.get(2).getElapsed();
        this.improvElapsed = this.components.get(3).getElapsed();
        this.improvNotes = this.components.get(3).getPayload();
        this.songsPracticed = this.components.get(2).getSongsPracticed();

        this.minBPM = Integer.parseInt(this.components.get(1).getPayload().split(" ")[0]);
        this.maxBPM = Integer.parseInt(this.components.get(1).getPayload().split(" ")[0]);
    }

    //parametrized constructor
    public Session(String date, int warmUpElapsed, int techniqueElapsed,
                   int songsPracticeElapsed, int improvElapsed, int maxBPM, int minBPM){
        this.date = date;
        this.warmUpElapsed = warmUpElapsed;
        this.techniqueElapsed = techniqueElapsed;
        this.songsPracticeElapsed = songsPracticeElapsed;
        this.improvElapsed = improvElapsed;
        this.maxBPM = maxBPM;
        this.maxBPM = minBPM;

    }

    //return date
    public String getDate() {
        return this.date;
    }

    //set date
    public void setDate(String date) {
        this.date = date;
    }

    //return time spent warming up
    public int getWarmUpElapsed() {
        return this.warmUpElapsed;
    }

    //set time spent warming up
    public void setWarmUpElapsed(int warmUpElapsed) {
        this.warmUpElapsed = warmUpElapsed;
    }

    //return time spent on technique
    public int getTechniqueElapsed() {
        return this.techniqueElapsed;
    }

    //set time spent on technique
    public void setTechniqueElapsed(int techniqueElapsed) {
        this.techniqueElapsed = techniqueElapsed;
    }

    //get time spent practicing repertoire
    public int getSongsPracticeElapsed() {
        return this.songsPracticeElapsed;
    }

    //set time spent practicing repertoire
    public void setSongsPracticeElapsed(int songsPracticeElapsed) {
        this.songsPracticeElapsed = songsPracticeElapsed;
    }

    ////get time spent practicing improv
    public int getImprovElapsed() {
        return this.improvElapsed;
    }

    //set time spent practicing repertoire
    public void setImprovElapsed(int improvElapsed) {
        this.improvElapsed = improvElapsed;
    }

    //get maxBPM for session
    public int getMaxBPM() {
        return this.maxBPM;
    }

    //set maxBPM for session
    public void setMaxBPM(int maxBPM) {
        this.maxBPM = maxBPM;
    }

    //get minBPM for session
    public int getMinBPM() {
        return this.minBPM;
    }

    //set minBPM for session
    public void setMinBPM(int minBPM) {
        this.minBPM = minBPM;
    }

    //get string value of attributes
    public String getPayload() {
        String output = this.date + "," + this.warmUpElapsed + "," + this.techniqueElapsed + "," +
                this.songsPracticeElapsed + "," + this.improvElapsed + "," + this.maxBPM + "," + this.minBPM + "\n";
        return output;
    }

    //get components
    public ArrayList<Component> getComponents(){
        return this.components;
    }

    //return songs practiced
    public LinkedList getSongsPracticed() {
        if (this.components.get(3).getSongsPracticed() != null) {
            return this.components.get(3).getSongsPracticed();
        }
        LinkedList noSongs = new LinkedList();
        noSongs.append("none");
        return noSongs;
    }

}
