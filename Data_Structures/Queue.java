package Data_Structures;/*

Name: Ethan Chen
Class: Queue
Description: Class for a Queue - A First In First Out (FIFO) data structure, where items are placed in a queue and can
                                 only be taken off once it is at the front of the line, or everything in front of it
                                 has been taken off.

*/


import java.util.Iterator;

public class Queue<Item> implements Iterable<Item>{

    // instance variables

    Node front;

    // constructors

    public Queue() { // default empty queue

    }

    public Queue(Node top) {
        this.front = top;
    } // queue initialized with first item in line

    public void enqueue(Item item) { // putting an item at the back of the line
        Node newNode = new Node(item);
        enqueue(newNode);
    }

    private void enqueue(Node newNode) { // helper method for enqueue
        if(isEmpty()) { // if the list is empty, put the new node at the front
            front = newNode;
        } else {
            Node currNode = front;
            while(currNode.nextNode != null) { // traverse until at the back of the line
                currNode = currNode.nextNode;
            }
            currNode.nextNode = newNode; // put the new node at the back
        }
    }

    public Item dequeue() { // remove the front node
        if(isEmpty()) { // don't do anything if it is empty
            return null;
        } else {
            Node tempNode = front; // set the front as the next node in line, and return the front node
            front = front.nextNode;
            return (Item) tempNode.data;
        }
    }

    public boolean isEmpty() { // if the queue has no items in it
        if(front == null) {
            return true;
        }
        return false;
    }

    public Queue<Item> copy() { // copy over every item in the queue to a new queue
        Queue<Item> newQueue = new Queue<>();
        for(Item x : this) { // uses the iterator
            if(x != null) {
                newQueue.enqueue(x);
            }
        }
        return newQueue;
    }

    public int size() { // returns the length of the queue
        Node currNode = front;
        int counter = 0;
        while(currNode != null) { // counts how many objects you need to traverse to to make it to the end of the queue
            currNode = currNode.nextNode;
            counter++;
        }
        return counter;
    }

    // iterator class and methods

    public Iterator<Item> iterator() { // iterator to traverse through linked list
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {

        Node<Item> currNode = new Node<Item>(front); // takes the value of the front item
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
                currNode = null; // sets it to null if there is no next - reached end of Queue
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

