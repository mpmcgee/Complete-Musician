public class LinkedList{
    Node head;

    //constructor
    public LinkedList(){
        head = null;
    }

    //append item to linked list
    public void append(String value){
        // append this value to the end of the list
        Node currentNode = null;
        // if list is empty, just make the node the new head
        if (head == null){
            head = new Node(value, null);
        } else {
            //if there are already nodes, go to the end
            currentNode = head;
            while (currentNode.getNext() != null){
                currentNode = currentNode.getNext();
            }
            // now the current node is the last node
            // add a new node
            currentNode.setNext(new Node(value, null));
        }
    }

    //prints payload for all items in LinkedList
    public void traverse(){
        Node currentNode = head;
        while (currentNode != null){
            System.out.println(currentNode.getPayload());
            currentNode = currentNode.getNext();
        }
    }

    //returns payload for all items in LinkedList
    public String getPayload() {
        StringBuilder sb = new StringBuilder();
        Node currentNode = head;
        while (currentNode != null){
            sb.append(currentNode.getPayload() + ",");
            currentNode = currentNode.getNext();
        }
        String payload = sb.toString();
        //remove trailing comma
        return(payload.substring(0, payload.length() - 1));
    }

    //searches for an item in LinkedList and returns it if found
    public Node search(String value){
        // return a node containing a string or
        // null if the string is not found
        Node result = null;
        Node currentNode = head;
        while (currentNode.getNext() != null){
            if (currentNode.getPayload().equals(value)){
                result = currentNode;
            } // end if
            currentNode = currentNode.getNext();
        } // end while
        return result;
    } // end search

    //delete a node
    public void deleteNode(String value){
        // check to see that node is there
        if (search(value) == null){
            System.out.println("That node is not here");
        } else {
            //special search preserves previous element
            Node currentNode = head;
            Node prevNode = head;
            String currentVal;
            while (currentNode != null){
                currentVal = currentNode.getPayload();
                if (currentVal.equals(value)){
                    // delete this element
                    Node nextNode = currentNode.getNext();
                    prevNode.setNext(nextNode);
                    // note we don't actually delete anything!
                }
                //move on to next node
                prevNode = currentNode;
                currentNode = currentNode.getNext();
            }
        }
    }
}
