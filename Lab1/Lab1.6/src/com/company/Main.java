package com.company;

/* Author: Rikard Johansson
 * Date generated: 13/9-21
 * Updated: 13/9-21
 * Solves lab1.6
 * Skapar kö och ordnar int element i storleksordning direkt
 * Code is taken from https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Queue.java.html
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Queue list = new Queue();               //Skapar en kö

        System.out.println(" 0:     To dequeue");
        System.out.println(" 1:     To enqueue");
        System.out.println("-1:     To exit");
        Scanner scan = new Scanner(System.in);  //För inmatning
        int input = scan.nextInt();

        while(input!=-1){
            if(input == 0){
                list.dequeue();
            } else if(input == 1){
                list.enqueue(scan.nextInt());
            } else {
                System.out.println("Fel inmatning. Vänlig välj ett av följande alternativ");
                System.out.println("0:      To dequeue");
                System.out.println("1:      To enqueue");
                System.out.println("-1:  To exit");
            }
            input = scan.nextInt();
        }
    }

    public static class Queue {
        private Node first;    // beginning of queue
        private int n;         // number of elements on queue

        // helper linked list class
        private class Node {
            int item;
            Node next;
            Node prev;
        }

        public Queue() {        //Skapar kö
            first = new Node();
            n = 0;
        }

        public void enqueue(int item) { //Lägger till i kö
            Node newNode = new Node();
            newNode.item = item;

            if(n==0){                   //Om detta är första föremåplet i kön
                first.item = item;
                first.next = first;
                first.prev = first;
            }
            else if(newNode.item < first.item){ //Om den nyinlagda nodens värde är mindre en första nodens värde
                first.prev.next = newNode;      //Så ändra att den kommer före
                newNode.prev = first.prev;
                newNode.next = first;
                first.prev = newNode;
                first = newNode;
            }
            else {                              //Annars gå genom listan tills du hittar rätt plats på den nya noden
                Node temp = first;
                for(int j = 1; j <= n; j++){
                    if(newNode.item >= temp.item){
                        temp = temp.next;
                    }
                }
                newNode.next = temp;
                newNode.prev = temp.prev;
                temp.prev.next = newNode;
                temp.prev = newNode;
            }
            n++;
            print();
        }

        public void dequeue() { //Tar bort i kö
            if (n==0){ System.out.println("Finns inga noder"); }
            else if(n==1){
                first.next = null;      //Om vi tar bort det ända elementet i kön, återställ alla värden
                first.prev = null;
                first = null;
                n = 0;
            }
            else {                      //Tar bort första tillagda element (FIFO)
                first.prev.next = first.next;
                first.next.prev = first.next;
                first = first.next;
                n--;
            }
            print();
        }

        public void print(){        //Skriver ut varje node i kö:n
            Node nd = first;
            for(int i = 1; i <= n; i++){
                if(i == n){
                    System.out.println("[" + nd.item + "], ");
                    break;
                }
                System.out.print("[" + nd.item + "], ");
                nd = nd.next;
            }
        }
    }
}