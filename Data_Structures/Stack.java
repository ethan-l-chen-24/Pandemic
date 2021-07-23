package Data_Structures;/*

Name: Ethan Chen
Class: Stack
Description: Class for a Stack - A Last In First Out (LIFO) data structure, where items are placed on a stack and can
                                 only be taken off if it is at the top of the stack; if there are things above it, those
                                 must be taken off first

*/


import java.util.Iterator;
import java.util.Random;

public class Stack<Item> implements Iterable<Item> {

    // instance variables

    Node top;

    // constructors

    public Stack() { // default empty stack

    }

    public Stack(Node top) {
        this.top = top;
    } // stack initialized with a single item

    public void push(Item item) { // add an item to the top of the stack
        Node newNode = new Node(item);
        push(newNode);
    }

    private void push(Node newNode) { // helper method
        if(isEmpty()) { // if it is empty, the new node is the top node
            top = newNode;
        } else {
            Node tempNode = top; // the new node is the new top node
            top = newNode;
            newNode.nextNode = tempNode;
        }
    }

    public Item pop() { // removes the item on top
        if(isEmpty()) { // don't do anything if it is empty
            return null;
        } else {
            Node tempNode = top; // take off and return the top node, and set the top as the next node
            top = top.nextNode;
            return (Item) tempNode.data;
        }
    }

    public Item popFromIndex(int index) { // remove from a specific index (important for shuffling)
        if(index == 0) { // effectively a pop
            Node targetNode = top;
            if(targetNode.nextNode!=null) {
                top = targetNode.nextNode;
            } else {
                top = null;
            }
            return (Item) targetNode.data;
        }

        if(index<stackHeight()) {
            Node currNode = top;
            for(int x = 0; x<index-1; x++) { // travel to item at this index
                currNode = currNode.nextNode;
            }
            Node targetNode = currNode.nextNode; // remove it the same way you would remove an item from a linked list
            currNode.nextNode = currNode.nextNode.nextNode;
            return (Item) targetNode.data;
        } else {
            return null;
        }
    }

    public Item pullBottom() { // take off the bottom of the deck (important for epidemics)
        return popFromIndex(stackHeight()-1);
    }

    public boolean isEmpty() { // if the stack has no items in it
        if(top == null) {
            return true;
        }
        return false;
    }

    private int stackHeight() { // length of the stack
        Node currNode = top;
        int counter = 0;
        while(currNode != null) { // counts how many items you have to move to to get to the end of the stack
            currNode = currNode.nextNode;
            counter++;
        }
        return counter; // returns the counter
    }

    public void shuffle() { // shuffles all of the items in a stack
        if(stackHeight() == 1) {
            return;
        }

        for(int x = 0; x<stackHeight()*5; x++) { // do this 5x the size of the stack
            Random r = new Random();
            int randIndex = r.nextInt(stackHeight()); // pick a random integer within the bounds of the indices of the
                                                      // stack
            push(popFromIndex(randIndex)); // Take out that random card and put it on top
        }
    }

    public int size() { // same as stackHeight()
        Node currNode = top;
        int counter = 0;
        while(currNode != null) {
            currNode = currNode.nextNode;
            counter++;
        }
        return counter;
    }

    public Stack<Item> copy() { // copies every item into a new stack
        Stack<Item> newStack = new Stack();
        for(Item x : this) { // uses the iterator
            if(x != null) {
                newStack.push(x);
            }
        }
        return newStack;
    }

    // iterator class and methods

    public Iterator<Item> iterator() { // iterator to traverse through linked list
        return new StackIterator();
    }

    private class StackIterator implements Iterator<Item> {

        Node<Item> currNode = new Node<Item>(top); // takes the value of the top item
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
                currNode = null; // sets it to null if there is no next - reached end of Stack
            }
            return data; // returns the data
        }
    }

    // to string

    @Override
    public String toString() {
        String cumulativeString = "";
        for(Item item : this) {
            cumulativeString += (item + " ");
        }
        return cumulativeString;
    }
}
