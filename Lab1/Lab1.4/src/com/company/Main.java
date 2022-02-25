package com.company;

/* Author: Rikard Johansson
 * Date generated: 9/9-21
 * Updated: 14/9-21
 * Solves lab1.4
 * Creates a generic iterable circular linked list which allows the user to insert and remove elements to/from the front and back end of the queue
 * Code is taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Queue.java.html
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Queue<String> list = new Queue<String>();

        //True lägger in efter och tar bort i början
        //False Lägger till innan och tar bort i slutet
        System.out.println(" 0:     To dequeue first");
        System.out.println(" 1:     To dequeue last");
        System.out.println(" 2:     To enqueue first");
        System.out.println(" 3:     To enqueue last");
        System.out.println("-1:     To exit");

        Scanner scan = new Scanner(System.in);          //För inmatning
        int input = Integer.parseInt(scan.nextLine());
        int t;

        while(input!=-1){
            if(input == 0){
                list.dequeue(true);
            } else if(input == 1){
                list.dequeue(false);
            } else if(input == 2){
                list.enqueue(scan.nextLine(), false);
            } else if(input == 3){
                list.enqueue(scan.nextLine(), true);
            } else {
                System.out.println("Fel inmatning. Vänlig välj ett av följande alternativ");
                System.out.println(" 0:     To dequeue first");
                System.out.println(" 1:     To dequeue last");
                System.out.println(" 2:     To enqueue first");
                System.out.println(" 3:     To enqueue last");
                System.out.println("-1:     To exit");
            }
            input = Integer.parseInt(scan.nextLine());
        }
    }

    public static class Queue<Item> implements Iterable<Item> {
        private Node<Item> first;    // beginning of queue
        private int n;               // number of elements on queue

        // helper linked list class
        private class Node<Item> {
            Item item;
            Node next;
            Node prev;
        }

        /**
         * Initializes an empty queue.
         */
        public Queue() {
            first = new Node();
            n = 0;
        }

        /**
         * Returns true if this queue is empty.
         *
         * @return {@code true} if this queue is empty; {@code false} otherwise
         */
        public boolean isEmpty() {
            return first == null;
        }

        /**
         * Returns the number of items in this queue.
         *
         * @return the number of items in this queue
         */
        public int size() {
            return n;
        }

        public void enqueue(Item item, boolean p) {
            if(n==0){
                first.item = item;
                first.next = first;
                first.prev = first;
            }
            else {
                Node nnode = new Node();
                nnode.item = item;
                nnode.next = first;
                nnode.prev = first.prev;
                nnode.prev.next = nnode;
                first.prev = nnode;
                if(!p){
                    first =nnode;
                }
            }
            n++;
            print();
        }

        /**
         * Removes and returns the item on this queue that was least recently added.
         *
         * @return the item on this queue that was least recently added
         * @throws NoSuchElementException if this queue is empty
         */
        public void dequeue(boolean p) {
            if(n==1){
                n = 0;
            }
            else if (n==0){
                System.out.println("Finns inga noder");
            }
            else {
                if(p){
                    first.prev.next = first.next;
                    first.next.prev = first.next;
                    first = first.next;
                }
                else {
                    first.prev.prev.next = first;
                    first.prev = first.prev.prev;
                }
                n--;
                print();
            }

        }

        /**
         * Returns a string representation of this queue.
         *
         * @return the sequence of items in FIFO order, separated by spaces
         */
        public String toString() {
            StringBuilder s = new StringBuilder();
            for (Item item : this) {
                s.append(item);
                s.append(' ');
            }
            return s.toString();
        }

        public Iterator<Item> iterator() {
            return new LinkedIterator(first);
        }

        // an iterator, doesn't implement remove() since it's optional
        private class LinkedIterator implements Iterator<Item> {
            private Node<Item> current;
            boolean isFirst = true;

            public LinkedIterator(Node<Item> first) {
                current = first;
            }

            public boolean hasNext() {
                if(isFirst) {
                    return current != null;
                }
                else {
                    return current != first;
                }
            }

            //Kod från princeton
            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                if(isFirst){ isFirst = false;};
                Item item = current.item;
                current = current.next;
                return item;
            }
        }

        public void print(){                        //Skriver ut element
            StringBuilder sb = new StringBuilder();
            int i = 1;
            for(Item item: this){
                if(i == n){
                    sb.append("[" + item + "], ");
                    break;
                }
                sb.append("[" + item + "], ");
                i++;
            }
            System.out.println(sb);
        }

    }
}