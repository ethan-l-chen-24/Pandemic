package Data_Structures;/*

Name: Ethan Chen
Class: LinkedList
Description: Class for a Linked List - a data structure that connects data together in a string through nodes - each
                                        node contains a piece of data and access to the next node, allowing minimal
                                        space allocation, but inefficient time when putting objects on or taking them
                                        off for very large sets

*/

import java.util.Iterator;

public class LinkedList<Item> implements Iterable<Item> {

    // instance variables

    Node<Item> mother; // contains data and pointer

    // constructors

    public LinkedList() { // default empty linked list

    }

    public LinkedList(Node mother) {
        this.mother = mother;
    } // linked list initialized with start node

    // class methods

    public void add(Node<Item> newNode) { // puts a new node on the back of the linked list
        if(isEmpty()) { // empty linked list
            mother = newNode;
        } else {
            Node currNode = mother;
            while(currNode.nextNode != null) { // travel to the end of the linked list
                currNode = currNode.nextNode;
            }
            currNode.nextNode = newNode; // put the new node on the end of the linked list
        }
    }

    public void add(Item newItem) { // puts a new node on back of the linked list, instead taking the data as parameter
        Node newNode = new Node(newItem); // creates the node using the data, then proceeds like the first add method
        if(isEmpty()) {
            mother = newNode;
        } else {
            Node currNode = mother;
            while(currNode.nextNode != null) {
                currNode = currNode.nextNode;
            }
            currNode.nextNode = newNode;
        }
    }

    public Item remove(Item item) { // removes a designated item
        if(mother.data.equals(item)) { // if the top node is the designated item to remove
            Node targetNode = mother;
            if(mother.nextNode!= null) {
                mother = mother.nextNode;
            } else {
                mother = null;
            }
            return (Item) targetNode.data;
        }

        Node currNode = mother;
        while(!currNode.nextNode.data.equals(item) && currNode.nextNode != null) { // look through linked list until
                                                                    // you find the data or reach the end of the list
            currNode = currNode.nextNode;
        }

        Node targetNode = currNode.nextNode; // A => B => C   ---   A => C  return B
        currNode.nextNode = currNode.nextNode.nextNode;
        return (Item) targetNode.data;
    }

    public Item get(int index) { // remove an item from a given index
            Node currNode = mother;
            for(int x = 0; x<index; x++) {
                if(currNode!=null) { // iterates through the list the (index) number of times to get to that index
                    currNode = currNode.nextNode;
                }
            }
            return (Item) currNode.data; // returns that item
    }

    public boolean isEmpty() { // if it is a blank linked list
        if(mother == null) { // if the mother does not exist, it is an empty linked list
            return true;
        }
        return false;
    }

    public int size() { // returns the length of the linked list e.g. a => b => c has size 3
        Node currNode = mother;
        int counter = 0;
        while(currNode != null) { // traverses through linked list and counts until it reaches null - end of linked list
            currNode = currNode.nextNode;
            counter++;
        }
        return counter; // returns the counter
    }

    public boolean contains(Item item) { // returns true if item is in the linked list, false otherwise
       if(!isEmpty()) {
           Node currNode = mother;
           while (currNode.nextNode != null) {
               if (currNode.data.equals(item)) { // if at any point while traversing the linked list we find the data
                                                // return true, otherwise false
                   return true;
               }
               currNode = currNode.nextNode;
           }
           return false;
       }
       return false;
    }

    public LinkedList<Item> copy() { // copy every item in the linked list over to a new linked list
       LinkedList<Item> copy = new LinkedList();
        for(Item item : this) { // used a for : each traversal
            copy.add(item);
        }
        return copy;
    }

    // for : each iterator class and methods

    @Override
    public Iterator<Item> iterator() { // iterator to traverse through linked list
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<Item> {
        Node<Item> currNode = new Node<Item>(mother); // takes the value of the top item
        Item data = currNode.data;

        public boolean hasNext() {
            if(currNode != null) { // has a next object as long as currNode is not null because currNode is set to null
                                   // when there are no more in the list
                return true;
            }
            return false;
        }

        public void remove() {

        }

        public Item next() {
            data = currNode.data; // stores the value of the data of the node
            if(currNode.nextNode != null) {
                currNode = currNode.nextNode; // moves to the next node if it exists
            } else {
                currNode = null; // sets it to null if there is no next - reached end of linked list
            }
            return data; // returns the data
        }
    }

    // to string

    @Override
    public String toString() {
        String cumulativeString = "";
        for(Item item : this) {
            cumulativeString += (item + "  ");
        }
        return cumulativeString;
    }

}
