import javax.swing.*;
import java.util.ArrayList;

public class LLSong {
    Song head;

    //constructor
    public LLSong() {
        head = null;
    }

    //add a new song
    public void append(String name, String key, String genre, String band) {

        Song newSong = new Song(name, key, genre, band, null);

        //append this object to end if list
        Song currentSong = null;

        //if list is empty, make this node the new head
        if (head == null) {
            head = newSong;
        } else {
            //if there are nodes already, go to the end
            currentSong = head;
            while (currentSong.getNext() != null) {
                currentSong = currentSong.getNext();
            }
            //current node is now the last node
            //add a new node
            currentSong.setNext(newSong);
        }

    }

    //print payload for all nodes in LinkedList
    public void traverse(){
        Song currentSong = head;
        while (currentSong != null){
            System.out.println(currentSong.getPayload());
            currentSong = currentSong.getNext();
        }
    }

    //return all payload for all objects
    public String getAllPayload() {
        StringBuilder sb = new StringBuilder();
        Song currentSong = head;
        while (currentSong != null){
            sb.append(currentSong.getPayload() + "\n");
            currentSong = currentSong.getNext();
        }

        return sb.toString();
    }

    //gets only payload needed to be written to files
    public String getAllWritePayload() {
        StringBuilder sb = new StringBuilder();
        Song currentSong = head;
        while (currentSong != null){
            sb.append(currentSong.getWritePayload() + "\n");
            currentSong = currentSong.getNext();
        }

        return sb.toString();
    }

    //returns an array of strings containing the names of all of the songs
    public String[] getNames() {
        Song currentSong = head;
        StringBuilder sb1 = new StringBuilder();
        int i = 0;
        while (currentSong != null){
            sb1.append(currentSong.getName() + " ");
            currentSong = currentSong.getNext();
            i++;
        }
        String[] names = sb1.toString().split(" ");
        return names;

    }

    //returns an arraylist of checkboxes with song info for each song
    public ArrayList<JCheckBox> getSongCheckBoxes() {
        ArrayList<JCheckBox> songCheckBoxes = new ArrayList();
        Song currentSong = head;
        while (currentSong != null) {
            songCheckBoxes.add(new JCheckBox(currentSong.getPayload()));
            currentSong = currentSong.getNext();
        }
        return songCheckBoxes;
    }

    //returns all keys for songs as an array of Strings
    public String[] getKeys() {
        Song currentSong = head;
        StringBuilder sb1 = new StringBuilder();
        while (currentSong != null){
            sb1.append(currentSong.getKey() + " ");
            currentSong = currentSong.getNext();
        }
        String[] keys = sb1.toString().split(" ");
        return keys;
    }

    //returns all genres for songs as an array of Strings
    public String[] getGenres() {
        Song currentSong = head;
        StringBuilder sb1 = new StringBuilder();
        while (currentSong != null){
            sb1.append(currentSong.getGenre() + " ");
            currentSong = currentSong.getNext();
        }
        String[] genres = sb1.toString().split(" ");
        return genres;
    }


    //searches for a song object and returns it if it exists
    public Song search(String value){
        Song result = null;
        Song currentSong = head;
        while (currentSong != null){
            if(currentSong.getPayload().equals(value)){
                result = currentSong;
            }
            currentSong = currentSong.getNext();
        }
        return result;
    }

    //finds and returns a LinkedList of all song objects with a given band name
    public LLSong findAllByBand(String value){
        LLSong resultLLSong = new LLSong();
        Song result = null;
        Song currentSong = head;
        while (currentSong != null){
            if(currentSong.getBand().equals(value)){
                result = currentSong;
                resultLLSong.append(result.name, result.key, result.genre, result.band);
            }
            currentSong = currentSong.getNext();
        }
        return resultLLSong;
    }

    //delete a song object
    public void deleteSong(String value){
        //check to see if node exists
        if(search(value) == null){
            System.out.println("Song not found");
        //preserve previous element
        } else {
            Song currentSong = head;
            Song prevSong = head;
            String currentVal;
            while (currentSong != null) {
                currentVal = currentSong.getPayload();

                if (currentVal.equals(value)) {
                    //delete this element
                    Song nextSong = currentSong.getNext();
                    prevSong.setNext(nextSong);
                }

                prevSong = currentSong;
                currentSong = currentSong.getNext();
            }
        }
    }
}
