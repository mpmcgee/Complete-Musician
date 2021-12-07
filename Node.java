class Node {
    String payload;
    Node next;

    //null constructor
    public Node(){
        payload = "";
        next = null;
    }

    //parametrized constructor
    public Node(String payload, Node next){
        this.payload = payload;
        this.next = next;
    }

    //get payload
    public String getPayload(){
        return this.payload;
    }

    //set payload
    public void setPayload(String payload){
        this.payload = payload;
    } // end setPayload

    //get next node
    public Node getNext(){
        return this.next;
    }

    //set next node
    public void setNext(Node next){
        this.next = next;
    }

}
