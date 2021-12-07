public class LLUser {
    User head;

    //constructor
    public LLUser(){
        head = null;
    }


    //append a user
    public void append(String name, String userName, String password){
        //create a new user object
        User newUser = new User(name, userName, password, null);

        // append this value to the end of the list
        User currentUser = null;
        // if list is empty, just make the node the new head
        if (head == null){
            head = newUser;
        } else {
            //if there are already nodes, go to the end
            currentUser = head;
            while (currentUser.getNext() != null){
                currentUser = currentUser.getNext();
            }
            // now the current node is the last node
            // add a new node
            currentUser.setNext(newUser);
        }
    }


    //prints payload for each user in the LinkedList
    public void traverse(){
        User currentUser = head;
        while (currentUser != null){
            System.out.println(currentUser.getPayload());
            currentUser = currentUser.getNext();
        }
    }

    //searches for a user and returns it if it exists
    public User search(String value){
        User result = null;
        User currentUser = head;
        while (currentUser.getNext() != null){
            if (currentUser.getUserName().equals(value)){
                result = currentUser;
            }
            currentUser = currentUser.getNext();
        }
        return result;
    }

    //logs user in
    public User logIn(String username, String password) {
            User currentUser = head;
            String currentUsername;
            String currentPassword;
            //iterate through each user in LinkedList
            while (currentUser != null) {
                //get username and password values for current node
                currentUsername = currentUser.getUserName();
                currentPassword = currentUser.getPassword();
                //if the username and password for current node are equal to the corresponding values passed into function
                if (currentUsername.equals(username) && currentPassword.equals(password)) {
                    //return the corresponding user
                    return currentUser;
                }

                //move on to next node
                currentUser = currentUser.getNext();
            }
        //if login failed, return null
        return null;
    }

    //delete a user
    public void deleteUser(String value){
        //check to see if node exists
        if(search(value) == null){
            System.out.println("User not found");
            //preserve previous element
        } else {
            User currentUser = head;
            User prevUser = head;
            String currentVal;
            while (currentUser != null) {
                currentVal = currentUser.getPayload();

                if (currentVal.equals(value)) {
                    //delete this element
                    User nextUser = currentUser.getNext();
                    prevUser.setNext(nextUser);
                }

                prevUser = currentUser;
                currentUser = currentUser.getNext();
            }
        }
    }
}

