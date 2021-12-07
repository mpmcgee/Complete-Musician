import java.util.ArrayList;

public class Report {

    protected User user;
    protected int totalPracticeTime;
    protected int warmUpPracticeTime;
    protected int techniquePracticeTime;
    protected int improvPracticeTime;
    protected int songPracticeTime;

    //constructor with user parameter
    public Report(User user) {
        this.user = user;
        setPracticeTimes();
    }

    //constructor with sessions parameter
    public ArrayList<Session> getSessions() {
        return this.user.getSessions();
    }

    //determine and set practice times using info from the user
    public void setPracticeTimes() {
        //initialize variables to hold values for each attribute
        int warmUp = 0;
        int improv = 0;
        int songPractice = 0;
        int technique = 0;
        //for each session in this report
        for(Session session: this.getSessions()) {
            //store a running sum of the elapsed time values for each component
            warmUp += session.getWarmUpElapsed();
            improv += session.getImprovElapsed();
            songPractice += session.getSongsPracticeElapsed();
            technique += session.getTechniqueElapsed();

        }
        //set these attributes equal to the sums
        this.improvPracticeTime = improv;
        this.warmUpPracticeTime = warmUp;
        this.songPracticeTime = songPractice;
        this.techniquePracticeTime = technique;
        //set the total practice time equal to the sum of the above items
        this.totalPracticeTime = improv + warmUp + songPractice + technique;

    }

    //converts integer time in seconds to String MM:SS format and returns value
    public String convertTime(int time) {
       return(String.format("%02d:%02d", time / 60, time % 60));
    }

    //converts and returns total practice time
    public String getFormattedTotalPracticeTime() {
        return convertTime(this.totalPracticeTime);
    }

    //converts and returns total warmup time
    public String getConvertedWarmUpTime() {
        return convertTime(this.warmUpPracticeTime);
    }

    //converts and returns total improv time
    public String getConvertedImprovPracticeTime() {
        return convertTime(this.improvPracticeTime);
    }

    //converts and returns total song practice time
    public String getConvertedSongPracticeTime() {
        return convertTime(this.songPracticeTime);
    }

    //converts and returns total technique practice time
    public String getConvertedTechniquePracticeTime() {
        return convertTime(this.techniquePracticeTime);
    }
}
