import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Loader {

    //reads the input file
    public static String readFile(String fileName) {
        //create a string builder to hold file data
        StringBuilder data = new StringBuilder();
        //try to open the file
        try {
            //make a new file object
            File myObj = new File(fileName);
            if (myObj != null) {
                //create a scanner instance to read file
                Scanner myReader = new Scanner(myObj);
                if (myReader != null) {
                    //read and parse file by line breaks
                    while (myReader.hasNextLine()) {
                        //append each line to the stringBuilder
                        data.append(myReader.nextLine() + "\n");
                    }
                }
                //close the file
                myReader.close();
            }

        //if the file is not found, throw an exception
        } catch (FileNotFoundException e) {
            data.append("An error occurred.");
            e.printStackTrace();
        }
        //return the file data in string format
        return data.toString();
    }

    //append a user to the file containing registered user info used for login
    public static void appendUser(String username, String password, String name) throws IOException {
        //create a new filewriter to append to usersInput.txt
        FileWriter wr = new FileWriter("usersInput.txt", true);
        //write username and password to file
        wr.write( "\n" + username + "," + password + "," + name);

        //clear and close the writer
        wr.flush();
        wr.close();

    }

    //creates a LinkedList of user objects using data from file containing registered user info
    public static LLUser loadUsers() {
        //create an array where each element is a line in the string returned from the file
        String[] data = readFile("usersInput.txt").split("\n");
        LLUser users = new LLUser();

        //iterate through each comma separated value
        for (int i = 0; i < data.length; i++) {
            String[] attributes = data[i].split(",");

            //store each array item as an attribute name for the object
            String userName = attributes[0];
            String password = attributes[1];
            String name = attributes[2];

            //add user to a LinkedList
            users.append(name, userName, password);

        }
        //return the LinkedList of users
        return users;
    }

    //loads the information for a single user and creates objects with content from files
    public static User loadSession(User user) {
        //create component objects and add to user
        loadComponents(user);
        //create routine objects and add to user
        loadRoutines(user);
        //create repertoire objects and add to user
        loadRepertoires(user);
        //create session objects and add to user
        loadSessionHistory(user);
        //returns this user
        return user;
    }

    //load the data from prior sessions and create corresponding objects, then attach to user
    public static User loadSessionHistory(User user) {
        //read data from user's sessions file and store in an array of data parsed by line break
        String[] data = readFile(user.getUserName() + "_sessions.txt").split("\n");
        //initialize an arraylist of session objects
        ArrayList<Session> sessions = new ArrayList<>();
        //if there is content in this file
        if (!data[0].equals("")) {
            //for each element in the data array
            for (int i = 0; i < data.length; i++) {
                //split the element by comma and return an array
                String[] componentAttributes = data[i].split(",");
                //store the attributes for each session object
                String date = componentAttributes[0];
                int warmUpElapsed = Integer.parseInt(componentAttributes[1]);
                int techniqueElapsed = Integer.parseInt(componentAttributes[2]);
                int songsPracticeElapsed = Integer.parseInt(componentAttributes[3]);
                int improvElapsed = Integer.parseInt(componentAttributes[4]);
                int maxBPM = Integer.parseInt(componentAttributes[5]);
                int minBPM = Integer.parseInt(componentAttributes[6]);

                //create a new session using these stored values
                sessions.add(new Session(date, warmUpElapsed, techniqueElapsed, songsPracticeElapsed, improvElapsed, maxBPM, minBPM));
            }
            //attach these sessions to user
            user.setSessions(sessions);
            //return the user
            return user;
        }
        //if there is no content, return null
        return null;
    }

    //load the data from components file and create corresponding objects, add to an ArrayList, and return
    public static ArrayList<Component> loadComponents(User user) {
        //read data from user's components file and store in an array of data parsed by line break
        String[] data = readFile(user.getUserName() + "_components.txt").split("\n");
        //if there is content in the file
        if (!data[0].equals("")) {
            //initialize an arraylist of component objects
            ArrayList<Component> components = new ArrayList();
            //for each element in the data array
            for (int i = 0; i < data.length; i++) {
                //split the element by comma and return an array
                String[] componentAttributes = data[i].split(",");
                //store the attributes for each session object
                String componentName = componentAttributes[1];
                int duration = Integer.parseInt(componentAttributes[2]);
                //create different class of component depending on type of component
                //append to arrayList of components
                if (componentName.equals("WarmUp")) {
                    Component component = new WarmUp(duration);
                    components.add(component);
                } else if (componentName.equals("Technique")) {
                    Component component = new Technique(duration);
                    components.add(component);
                } else if (componentName.equals("SongPractice")) {
                    Component component = new SongPractice(duration);
                    components.add(component);
                } else if (componentName.equals("Improv")) {
                    Component component = new Improv(duration);
                    components.add(component);
                }
            }

            //return ArrayList of components
            return components;
        }
        //if there is no content in file, return null
        return null;
    }

    //load the data from routine file and create corresponding objects, then attach to user
    public static User loadRoutines(User user) {
        //read data from user's components file and store in an array of data parsed by line break
        String[] data = readFile(user.getUserName() + "_routines.txt").split("\n");
        //if there is content in the file
        if (!data[0].equals("")) {
            //get components for this user
            ArrayList<Component> components = loadComponents(user);
            //initialize an arraylist of grouped components
            ArrayList<Component> groupedComponents;
            //initialize an arraylist of routine objects
            ArrayList<Routine> routines = new ArrayList<>();
            //iterate through data array
            for (int i = 0; i < data.length; i++) {
                //split and store attributes as variables
                String[] componentAttributes = data[i].split(",");
                String instrument = componentAttributes[0];
                String nickname = componentAttributes[1];
                //create a new routine using attributes
                Routine routine = new Routine(nickname, instrument);
                //add routines to ArrayList of routines
                routines.add(routine);
            }

            //for each routine in the array list of routines
            for (Routine routine : routines) {
                //wipe groupedComponents ArrayList
                groupedComponents = new ArrayList<>();
                //for each of the four types of components
                for (int i = 0; i < 4; i++) {
                    //add component to groupedComponent ArrayLists
                    groupedComponents.add(components.get(i));

                }
                //attach these components to the routine
                routine.setComponents(groupedComponents);
            }

            //attach routine to user
            user.setRoutines(routines);

            return user;
        }
        //if there is no content in file, return null
        return null;
    }

    //loads data from songs file for user and creates and returns a LinkedList of songs
    public static LLSong loadSongs(User user) {
        //read and parse data in songs file into an array by line break delimiter
        String[] data = readFile(user.getUserName() + "_songs.txt").split("\n");
        //if there is content in the file
        if (!data[0].equals("")) {
            //crete a new LinkedList of song objects
            LLSong songs = new LLSong();

            //iterate through items in data array
            for (int i = 0; i < data.length; i++) {
                //parse by comma and split into array values
                String[] attributes = data[i].split(",");
                //store corresponding array values as attribute variables for song object
                String band = attributes[0];
                String name = attributes[1];
                String key = attributes[2];
                String genre = attributes[3];

                //append a new song with these attributes
                songs.append(name, key, genre, band);
            }
            //returns this linkedList of Songs
            return songs;
        }
        //return null if file is empty
        return null;
    }

    //load the data from a user's repertoire file and attach them to a user, then return user
    public static User loadRepertoires(User user) {
        //read data from repertoires file and split into an array of data by newline delimiter
        String[] data = readFile(user.getUserName() + "_repertoires.txt").split("\n");
        //if there is content in the file
        if (!data[0].equals("")) {
            //get the songs for this user
            LLSong songs = loadSongs(user);
            //create an arraylist of repertoires
            ArrayList<Repertoire> repertoires = new ArrayList<>();
            //iterate through data array
            for (int i = 0; i < data.length; i++) {
                //split into an array using comma delimiter
                String[] componentAttributes = data[i].split(",");
                //store attributes from file as variables
                String instrument = componentAttributes[0];
                String band = componentAttributes[1];

                //create a new repertoire
                Repertoire repertoire = new Repertoire(instrument, band);
                //if this repertoire has songs, add them to it
                if (songs != null) {
                    //add only songs that correspond to the band associated with this repertoire
                    repertoire.setSongs(songs.findAllByBand(band));
                }
                //add repertoires to repertoire list
                repertoires.add(repertoire);
            }
            //set user repertoires equal to this list
            user.setRepertoires(repertoires);

            return user;
        }
        //return null if file is empty
        return null;

    }
}
