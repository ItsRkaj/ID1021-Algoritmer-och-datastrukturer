package com.company;

/* Author: Rikard Johansson
 * Date generated: 9/9-21
 * Updated: 13/9-21
 * Solves lab1.2 iterativa delen
 * This code take a string and reverse it with a Pushdown stack (linked-list implementation).
 * Code is taken from Algorithms, 4th ed. Sedgewick and Wayne.
 */

import java.util.Iterator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Stack<Character> stack = new Stack<Character>();    //Char stack

        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();                       //Sparar inläst rad som en sträng

        char[] c = str.toCharArray();                       //Bryter upp strängen som char array

        for(int i = 0; i < c.length; i++){
            stack.push(c[i]);
        }

        for (int i : stack)                                 //Foreach loop
            System.out.print("[" + stack.pop() + "], ");    //Skriver ut utpopade element från stacken
    }

    public static class Stack<Item> implements Iterable<Item>
    {
        private Node first; // top of stack (most recently added node)
        private int N;      // number of items
        private class Node
        {  // nested class to define nodes
            Item item;
            Node next;
        }

        public boolean isEmpty() {  return first == null; }  // Or: N == 0.
        public int size()        {  return N; }

        public void push(Item item)
        {  // Add item to top of stack.
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            N++;
        }

        public Item pop()
        {  // Remove item from top of stack.
            Item item = first.item;
            first = first.next;
            N--;
            return item;
        }

        public Iterator<Item> iterator()
        {  return new ListIterator();  }
        private class ListIterator implements Iterator<Item>
        {
            private Node current = first;
            public boolean hasNext()
            {  return current != null;  }
            public void remove() { }
            public Item next()
            {
                Item item = current.item;
                current = current.next;
                return item;
            }
        }
    }
}
