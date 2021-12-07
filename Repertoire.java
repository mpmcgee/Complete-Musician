public class Repertoire {
    protected String instrument;
    protected String band;
    protected LLSong songs;

    //constructor for repertoire
    public Repertoire(String instrument, String band) {
        this.instrument = instrument;
        this.band = band;
        this.songs = new LLSong();
    }

    //return instrument associated with this repertoire
    public String getInstrument() {
        return this.instrument;
    }

    //set instrument attribute
    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    //return band attribute
    public String getBand() {
        return this.band;
    }

    //return LinkedList of songs for this repertoire
    public LLSong getSongs() {
        return this.songs;
    }

    //set the songs for the repertoire
    public void setSongs(LLSong songs) {
        this.songs = songs;
    }

    //return string values of content
    public String getPayload() {
        return(this.getInstrument() + "," + this.getBand());
    }
}
