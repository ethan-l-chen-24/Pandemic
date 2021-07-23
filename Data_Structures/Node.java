package Data_Structures;/*

Name: Ethan Chen
Class: Node
Description: Class for a Node - contains data and a pointer to another node - for use in linked lists, stacks, queues,
                                                                              and other data structures

*/

public class Node<Item> {

    // instance variables

    Item data;
    Node nextNode;

    // constructors

    public Node(Item data) { // constructor for a piece of data
        this.data = data;
    }

    public Node(Node node) { // constructor to create a node given a preexisting node
        if(node != null) {
            this.data = (Item) node.data;
            this.nextNode = node.nextNode;
        }
    }

}
