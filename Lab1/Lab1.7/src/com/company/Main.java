package com.company;

/* Author: Rikard Johansson
 * Date generated: 13/9-21
 * Updated: 13/9-21
 * Solves lab1.7
 * Checks if a series of parentheses are "balanced" or not.
 * Code is taken from Algorithms, 4th ed. Sedgewick and Wayne.
 *
 * Worst case
 * Time Complexity O(n) se for loopen (i worst case loopar till c.lenght, alltså n gånger)
 * Memory Complexity O(n) pga stack n som i worst case behöver lägga in alla element i stacken
 *
 * Balancerade paranteser: ({})[], ()
 * Obalancerade paranteser: ({)}, (()
 */

import java.util.Iterator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();

        System.out.println(isBalanced(str));
    }

    public static char[][] TOKENS = {{'{', '}'}, {'[', ']'}, {'(', ')'}}; //Intressanta tecken

    public static boolean isOpenTerm(char c){                       //Kollar om tecknet c är en paratesöppnare alltså. (,[,{
        char[][] array = TOKENS;
        for(int i = 0; i < TOKENS.length; i++){
            if(array[i][0] == c){
                return true;                                        //Isf returnar den true
            }
        }
        return false;                                               //Annars false
    }

    public static boolean matches(char openTerm, char closeTerm){   //Kollar om en öppen parantes har en matchade "parantesstängare" ex. ),],}
        char[][] array = TOKENS;
        for(int i = 0; i < TOKENS.length; i++){
            if(array[i][0] == openTerm){
                return array[i][1] == closeTerm;
            }
        }
        return false;
    }

    public static boolean isBalanced (String str){                      //Tar reda på om inmatade strängen har balancerade baranteser
        Stack<Character> stack = new Stack<Character>();                        //Skapar char stack
        char[] c = str.toCharArray();                                   //Omvandlar string parametern till char array
        for(int i = 0; i <  c.length; i++){
            if(isOpenTerm(c[i])){                                       //Ser om inmatat tecken är en parantesöppnare ex. (,[,{
                stack.push(c[i]);                                       //Isf lägger den in i stacken
            }
            else {
                if(stack.isEmpty() || !matches(stack.pop(), c[i])){     //Om stacken är tom ELLER översta elementet i stacken inte matchar med nuvarande parantes
                    return false;                                       //returnera false
                }
            }
            stack.print();
        }
        return stack.isEmpty();                                         //Om stacken är tom beyder det att paranteserna
    }

    //Stack tagen från boken (Samma som i lab1.2)
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
        public int size()        {  return N; }              //Behövs ej i detta program

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

        public void print(){                            //Skriver ut elementen
            StringBuilder sb = new StringBuilder();
            int i = 1;
            for(Item item: this){
                if(i == N){
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

