package com.company;

/* Author: Rikard Johansson
 * Date generated: 9/9-21
 * Updated: 14/9-21
 * Solves lab1.5
 * En generaliserad kö som låter användaren ta bort önskat element (k) från kön
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
                list.dequeue(Integer.parseInt(scan.nextLine()));
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

    public static class Queue<Item> implements Iterable<Item> {
        private Node<Item> first;    // beginning of queue
        private int n;               // number of elements on queue

        // helper linked list class
        private class Node<Item> {
            Item item;
            Node next;
        }

        public Queue() {
            first = new Node();
            n = 0;
        }

        public void enqueue(Item item) {        //Lägger till föremål i köm
            if(n==0){
                first.item = item;
                first.next = first;
            }
            else {
                Node nnode = new Node();
                nnode.item = item;
                nnode.next = first;
                first = nnode;
            }
            n++;
            print();
        }

        public void dequeue(int e) {            //Tar bort föremål i kön på önskad plats av användaren
            if(e <= n){
                if(n==1){
                    n = 0;
                }
                else if(n==0){
                    System.out.println("Finns ingen nod");
                }
                else{
                    if(e == 1){
                        first = first.next;
                    }
                    else{
                        Node temp = first;
                        for(int i = 0; i < e - 2; i++){
                            temp = temp.next;
                        }
                        temp.next = temp.next.next;
                    }
                    n--;
                    print();
                }
            }
            else {
                System.out.println("Finns inte så många element i kön");
            }
        }

        //Från princeton
        public Iterator<Item> iterator() {
            return new LinkedIterator(first);
        }

        // an iterator, doesn't implement remove() since it's optional
        private class LinkedIterator implements Iterator<Item> {
            private Node<Item> current;

            public LinkedIterator(Node<Item> first) {
                current = first;
            }

            public boolean hasNext() {
                return current != null;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = current.item;
                current = current.next;
                return item;
            }
        }

        public void print(){                            //Skriver ut föremål
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