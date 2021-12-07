public class Song {
    protected String name;
    protected String key;
    protected String genre;
    protected Song next;
    protected String band;

    //null constructor
    public Song(){
        this.name = "";
        this.key = "";
        this.genre = "";
        this.band = "";
        this.next = null;
    }

    //parametrized constructor
    public Song(String name, String key, String genre, String band, Song next) {
        this.name = name;
        this.key = key;
        this.band = band;
        this.genre = genre;
        this.next = next;
    }

    //constructor for LL object
    public Song(String name, Song next){
        this.name = name;
        this.key = "";
        this.genre = "";
        this.band = "";
        this.next = next;
    } // end constructor

    //return name
    public String getName() {
        return this.name;
    }

    //set name
    public void setName(String name) {
        this.name = name;
    }

    //get key
    public String getKey() {
        return this.key;
    }

    //set key
    public void setKey(String key) {
        this.key = key;
    }

    //get genre
    public String getGenre() {
        return this.genre;
    }

    //set genre
    public void setGenre(String genre) {
        this.genre = genre;
    }

    //gets next song in LL
    public Song getNext() {
        return this.next;
    }

    //sets next song in LL
    public void setNext(Song next) {
        this.next = next;
    }

    //get band
    public String getBand() {
        return this.band;
    }

    //gets string value of attributes
    public String getPayload(){
        // return a string representing contents
        StringBuilder sb = new StringBuilder();
        sb.append(this.name + ", " + this.key + ", " + this.genre);
        return sb.toString();
    }

    //gets string values needed for writing to file
    public String getWritePayload(){
        // return a string representing contents
        StringBuilder sb = new StringBuilder();
        sb.append(this.name + "," + this.key + "," + this.genre);
        return sb.toString();
    }
}
