package com.company;

/* Author: Rikard Johansson
 * Date generated: 9/9-21
 * Updated: 14/9-21
 * Solves lab1.3
 * A generic iterable FIFO-queue based on a double linked circular list
 * Code is taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Queue.java.html
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Queue<String> list = new Queue<String>();

        System.out.println(" 0:     To dequeue");
        System.out.println(" 1:     To enqueue");
        System.out.println("-1:     To exit");

        Scanner scan = new Scanner(System.in);  //För inmatning
        int input = Integer.parseInt(scan.nextLine());

        while(input!=-1){
            if(input == 0){
                list.dequeue();
            } else if(input == 1){
                list.enqueue(scan.nextLine());
            } else {
                System.out.println("Fel inmatning. Vänlig välj ett av följande alternativ");
                System.out.println("0:      To dequeue");
                System.out.println("1:      To enqueue");
                System.out.println("-1:     To exit");
            }
            input = Integer.parseInt(scan.nextLine());
        }
    }

    //Majoritet av kod tagen från https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Queue.java.html
    public static class Queue<Item> implements Iterable<Item> {
        private Node<Item> first;    // Första noden i kön
        private int n;               // Antal element i kön

        // helper linked list class
        private class Node<Item> {
            Item item;              //Föremålet i noden
            Node next;              //Nodens pekare mot nästa i kön
            Node prev;              //Nodens föregående nod i kön
        }

        public Queue() {            //Konstruktorn till en queue
            first = new Node();     //Skapar första noden i kön
            n = 0;                  //0 i kön från början
        }

        public void enqueue(Item item) {    //Tillsätta element i kön
            if(n==0){                       //Då första elementet ska gå in i kön
                first.item = item;          //Första elementets föremål ska vara föremål från innan
                first.next = first;         
                first.prev = first;
            }
            else {
                Node newNode = new Node();
                newNode.item = item;
                newNode.next = first;
                newNode.prev = first.prev;
                newNode.prev.next = newNode;
                first.prev = newNode;
            }
            n++;
            print();
        }

        public void dequeue() {             //Ta bort element i kön
            if (n==0){ System.out.println("Finns inga noder"); }
            else if(n==1){
                first.item = null;
                first.next = null;
                first.prev = null;
                first = null;
                n = 0;
            }
            else {
                first.prev.next = first.next;
                first.next.prev = first.prev;
                first = first.next;
                n--;
            }
            print();
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

        public void print(){                        //skriver ut element
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