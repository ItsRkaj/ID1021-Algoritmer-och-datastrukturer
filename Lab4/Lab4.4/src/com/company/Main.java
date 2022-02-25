package com.company;

/* Author: Rikard Johansson
 * Date generated: 8/10-21
 * Updated 11/10-21
 * Solves Lab4.4
 * Description: A program that can answer if there is a path between any to vertices.
 * Code taken from: https://algs4.cs.princeton.edu/code/
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        SymbolDigraph SD = new SymbolDigraph("lab4.txt");

        Scanner in = new Scanner(System.in);

        System.out.print("Find the a path from: ");
        String f = in.nextLine().toUpperCase();
        int s = SD.indexOf(f); 

        System.out.print("To: ");
        String t = in.nextLine().toUpperCase();
        int e = SD.indexOf(t);

        Digraph G = SD.digraph();

        DirectedDFS dfs = new DirectedDFS(G,s);

        System.out.printf("Is there a way between %s and %s? ", SD.nameOf(s), SD.nameOf(e));
        if(dfs.marked(e)){
            System.out.println("Yes");
        }else{
            System.out.println("No");
        }
        in.close();
    }

    public static class DirectedDFS {
        private boolean[] marked;  // marked[v] = true iff v is reachable from source(s)
        private int count;         // number of vertices reachable from source(s)

        /**
         * Computes the vertices in digraph {@code G} that are
         * reachable from the source vertex {@code s}.
         * @param G the digraph
         * @param s the source vertex
         * @throws IllegalArgumentException unless {@code 0 <= s < V}
         */
        public DirectedDFS(Digraph G, int s) {
            marked = new boolean[G.V()];
            validateVertex(s);
            dfs(G, s);
        }

        /**
         * Computes the vertices in digraph {@code G} that are
         * connected to any of the source vertices {@code sources}.
         * @param G the graph
         * @param sources the source vertices
         * @throws IllegalArgumentException if {@code sources} is {@code null}
         * @throws IllegalArgumentException unless {@code 0 <= s < V}
         *         for each vertex {@code s} in {@code sources}
         */
        public DirectedDFS(Digraph G, Iterable<Integer> sources) {
            marked = new boolean[G.V()];
            validateVertices(sources);
            for (int v : sources) {
                if (!marked[v]) dfs(G, v);
            }
        }

        private void dfs(Digraph G, int v) {
            count++;
            marked[v] = true;
            for (int w : G.adj(v)) {
                if (!marked[w]) dfs(G, w);
            }
        }

        /**
         * Is there a directed path from the source vertex (or any
         * of the source vertices) and vertex {@code v}?
         * @param  v the vertex
         * @return {@code true} if there is a directed path, {@code false} otherwise
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public boolean marked(int v) {
            validateVertex(v);
            return marked[v];
        }

        /**
         * Returns the number of vertices reachable from the source vertex
         * (or source vertices).
         * @return the number of vertices reachable from the source vertex
         *   (or source vertices)
         */
        public int count() {
            return count;
        }

        // throw an IllegalArgumentException unless {@code 0 <= v < V}
        private void validateVertex(int v) {
            int V = marked.length;
            if (v < 0 || v >= V)
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
        }

        // throw an IllegalArgumentException if vertices is null, has zero vertices,
        // or has a vertex not between 0 and V-1
        private void validateVertices(Iterable<Integer> vertices) {
            if (vertices == null) {
                throw new IllegalArgumentException("argument is null");
            }
            int V = marked.length;
            int count = 0;
            for (Integer v : vertices) {
                count++;
                if (v == null) {
                    throw new IllegalArgumentException("vertex is null");
                }
                validateVertex(v);
            }
            if (count == 0) {
                throw new IllegalArgumentException("zero vertices");
            }
        }
    }

    public static class Digraph {
        private static final String NEWLINE = System.getProperty("line.separator");

        private final int V;           // number of vertices in this digraph
        private int E;                 // number of edges in this digraph
        private Bag<Integer>[] adj;    // adj[v] = adjacency list for vertex v
        private int[] indegree;        // indegree[v] = indegree of vertex v

        /**
         * Initializes an empty digraph with <em>V</em> vertices.
         *
         * @param  V the number of vertices
         * @throws IllegalArgumentException if {@code V < 0}
         */
        public Digraph(int V) {
            if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be non-negative");
            this.V = V;
            this.E = 0;
            indegree = new int[V];
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<Integer>();
            }
        }

        /**
         * Initializes a digraph from the specified input stream.
         * The format is the number of vertices <em>V</em>,
         * followed by the number of edges <em>E</em>,
         * followed by <em>E</em> pairs of vertices, with each entry separated by whitespace.
         *
         * @param  in the input stream
         * @throws IllegalArgumentException if {@code in} is {@code null}
         * @throws IllegalArgumentException if the endpoints of any edge are not in prescribed range
         * @throws IllegalArgumentException if the number of vertices or edges is negative
         * @throws IllegalArgumentException if the input stream is in the wrong format
         */
        public Digraph(Scanner in) {
            if (in == null) throw new IllegalArgumentException("argument is null");
            try {
                this.V = in.nextInt();
                if (V < 0) throw new IllegalArgumentException("number of vertices in a Digraph must be non-negative");
                indegree = new int[V];
                adj = (Bag<Integer>[]) new Bag[V];
                for (int v = 0; v < V; v++) {
                    adj[v] = new Bag<Integer>();
                }
                int E = in.nextInt();
                if (E < 0) throw new IllegalArgumentException("number of edges in a Digraph must be non-negative");
                for (int i = 0; i < E; i++) {
                    int v = in.nextInt();
                    int w = in.nextInt();
                    addEdge(v, w);
                }
            }
            catch (NoSuchElementException e) {
                throw new IllegalArgumentException("invalid input format in Digraph constructor", e);
            }
        }

        /**
         * Initializes a new digraph that is a deep copy of the specified digraph.
         *
         * @param  G the digraph to copy
         * @throws IllegalArgumentException if {@code G} is {@code null}
         */
        public Digraph(Digraph G) {
            if (G == null) throw new IllegalArgumentException("argument is null");

            this.V = G.V();
            this.E = G.E();
            if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be non-negative");

            // update indegrees
            indegree = new int[V];
            for (int v = 0; v < V; v++)
                this.indegree[v] = G.indegree(v);

            // update adjacency lists
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<Integer>();
            }

            for (int v = 0; v < G.V(); v++) {
                // reverse so that adjacency list is in same order as original
                Stack<Integer> reverse = new Stack<Integer>();
                for (int w : G.adj[v]) {
                    reverse.push(w);
                }
                for (int w : reverse) {
                    adj[v].add(w);
                }
            }
        }

        /**
         * Returns the number of vertices in this digraph.
         *
         * @return the number of vertices in this digraph
         */
        public int V() {
            return V;
        }

        /**
         * Returns the number of edges in this digraph.
         *
         * @return the number of edges in this digraph
         */
        public int E() {
            return E;
        }


        // throw an IllegalArgumentException unless {@code 0 <= v < V}
        private void validateVertex(int v) {
            if (v < 0 || v >= V)
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
        }

        /**
         * Adds the directed edge v→w to this digraph.
         *
         * @param  v the tail vertex
         * @param  w the head vertex
         * @throws IllegalArgumentException unless both {@code 0 <= v < V} and {@code 0 <= w < V}
         */
        public void addEdge(int v, int w) {
            validateVertex(v);
            validateVertex(w);
            adj[v].add(w);
            indegree[w]++;
            E++;
        }

        /**
         * Returns the vertices adjacent from vertex {@code v} in this digraph.
         *
         * @param  v the vertex
         * @return the vertices adjacent from vertex {@code v} in this digraph, as an iterable
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public Iterable<Integer> adj(int v) {
            validateVertex(v);
            return adj[v];
        }

        /**
         * Returns the number of directed edges incident from vertex {@code v}.
         * This is known as the <em>outdegree</em> of vertex {@code v}.
         *
         * @param  v the vertex
         * @return the outdegree of vertex {@code v}
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public int outdegree(int v) {
            validateVertex(v);
            return adj[v].size();
        }

        /**
         * Returns the number of directed edges incident to vertex {@code v}.
         * This is known as the <em>indegree</em> of vertex {@code v}.
         *
         * @param  v the vertex
         * @return the indegree of vertex {@code v}
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public int indegree(int v) {
            validateVertex(v);
            return indegree[v];
        }

        /**
         * Returns the reverse of the digraph.
         *
         * @return the reverse of the digraph
         */
        public Digraph reverse() {
            Digraph reverse = new Digraph(V);
            for (int v = 0; v < V; v++) {
                for (int w : adj(v)) {
                    reverse.addEdge(w, v);
                }
            }
            return reverse;
        }

        /**
         * Returns a string representation of the graph.
         *
         * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
         *         followed by the <em>V</em> adjacency lists
         */
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(V + " vertices, " + E + " edges " + NEWLINE);
            for (int v = 0; v < V; v++) {
                s.append(String.format("%d: ", v));
                for (int w : adj[v]) {
                    s.append(String.format("%d ", w));
                }
                s.append(NEWLINE);
            }
            return s.toString();
        }
    }

    public static class Bag<Item> implements Iterable<Item> {
        private Node<Item> first;    // beginning of bag
        private int n;               // number of elements in bag

        // helper linked list class
        private static class Node<Item> {
            private Item item;
            private Node<Item> next;
        }

        /**
         * Initializes an empty bag.
         */
        public Bag() {
            first = null;
            n = 0;
        }

        /**
         * Returns true if this bag is empty.
         *
         * @return {@code true} if this bag is empty;
         *         {@code false} otherwise
         */
        public boolean isEmpty() {
            return first == null;
        }

        /**
         * Returns the number of items in this bag.
         *
         * @return the number of items in this bag
         */
        public int size() {
            return n;
        }

        /**
         * Adds the item to this bag.
         *
         * @param  item the item to add to this bag
         */
        public void add(Item item) {
            Node<Item> oldfirst = first;
            first = new Node<Item>();
            first.item = item;
            first.next = oldfirst;
            n++;
        }


        /**
         * Returns an iterator that iterates over the items in this bag in arbitrary order.
         *
         * @return an iterator that iterates over the items in this bag in arbitrary order
         */
        public Iterator<Item> iterator()  {
            return new LinkedIterator(first);
        }

        // an iterator, doesn't implement remove() since it's optional
        private class LinkedIterator implements Iterator<Item> {
            private Node<Item> current;

            public LinkedIterator(Node<Item> first) {
                current = first;
            }

            public boolean hasNext()  { return current != null;                     }
            public void remove()      { throw new UnsupportedOperationException();  }

            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = current.item;
                current = current.next;
                return item;
            }
        }
    }

    public static class Stack<Item> implements Iterable<Item> {
        private Node<Item> first;     // top of stack
        private int n;                // size of the stack

        // helper linked list class
        private static class Node<Item> {
            private Item item;
            private Node<Item> next;
        }

        /**
         * Initializes an empty stack.
         */
        public Stack() {
            first = null;
            n = 0;
        }

        /**
         * Returns true if this stack is empty.
         *
         * @return true if this stack is empty; false otherwise
         */
        public boolean isEmpty() {
            return first == null;
        }

        /**
         * Returns the number of items in this stack.
         *
         * @return the number of items in this stack
         */
        public int size() {
            return n;
        }

        /**
         * Adds the item to this stack.
         *
         * @param  item the item to add
         */
        public void push(Item item) {
            Node<Item> oldfirst = first;
            first = new Node<Item>();
            first.item = item;
            first.next = oldfirst;
            n++;
        }

        /**
         * Removes and returns the item most recently added to this stack.
         *
         * @return the item most recently added
         * @throws NoSuchElementException if this stack is empty
         */
        public Item pop() {
            if (isEmpty()) throw new NoSuchElementException("Stack underflow");
            Item item = first.item;        // save item to return
            first = first.next;            // delete first node
            n--;
            return item;                   // return the saved item
        }


        /**
         * Returns (but does not remove) the item most recently added to this stack.
         *
         * @return the item most recently added to this stack
         * @throws NoSuchElementException if this stack is empty
         */
        public Item peek() {
            if (isEmpty()) throw new NoSuchElementException("Stack underflow");
            return first.item;
        }

        /**
         * Returns a string representation of this stack.
         *
         * @return the sequence of items in this stack in LIFO order, separated by spaces
         */
        public String toString() {
            StringBuilder s = new StringBuilder();
            for (Item item : this) {
                s.append(item);
                s.append(' ');
            }
            return s.toString();
        }


        /**
         * Returns an iterator to this stack that iterates through the items in LIFO order.
         *
         * @return an iterator to this stack that iterates through the items in LIFO order
         */
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
    }
    public static class SymbolDigraph {
        private SequentialSearchST<String, Integer> st;  // string -> index
        private String[] keys;           // index  -> string
        private Digraph graph;           // the underlying digraph

        /**
         * Initializes a digraph from a file using the specified delimiter.
         * Each line in the file contains
         * the name of a vertex, followed by a list of the names
         * of the vertices adjacent to that vertex, separated by the delimiter.
         * @param filename the name of the file
         *
         */
        public SymbolDigraph(String filename) throws FileNotFoundException {
            st = new SequentialSearchST<String, Integer>();

            // First pass builds the index by reading strings to associate
            // distinct strings with an index
            Scanner in = new Scanner(new FileReader(filename));
            while (in.hasNextLine()) {
                String[] a = in.nextLine().split(" ");
                for (int i = 0; i < a.length; i++) {
                    if (!st.contains(a[i]))
                        st.put(a[i], st.size());
                }
            }

            // inverted index to get string keys in an array
            keys = new String[st.size()];
            for (String name : st.keys()) {
                keys[st.get(name)] = name;
            }

            // second pass builds the digraph by connecting first vertex on each
            // line to all others
            graph = new Digraph(st.size());
            in = new Scanner(new FileReader(filename));
            while (in.hasNextLine()) {
                String[] a = in.nextLine().split(" ");
                int v = st.get(a[0]);
                for (int i = 1; i < a.length; i++) {
                    int w = st.get(a[i]);
                    graph.addEdge(v, w);
                }
            }
        }

        /**
         * Does the digraph contain the vertex named {@code s}?
         * @param s the name of a vertex
         * @return {@code true} if {@code s} is the name of a vertex, and {@code false} otherwise
         */
        public boolean contains(String s) {
            return st.contains(s);
        }

        /**
         * Returns the integer associated with the vertex named {@code s}.
         * @param s the name of a vertex
         * @return the integer (between 0 and <em>V</em> - 1) associated with the vertex named {@code s}
         * @deprecated Replaced by {@link #indexOf(String)}.
         */
        @Deprecated
        public int index(String s) {
            return st.get(s);
        }

        /**
         * Returns the integer associated with the vertex named {@code s}.
         * @param s the name of a vertex
         * @return the integer (between 0 and <em>V</em> - 1) associated with the vertex named {@code s}
         */
        public int indexOf(String s) {
            return st.get(s);
        }

        /**
         * Returns the name of the vertex associated with the integer {@code v}.
         * @param  v the integer corresponding to a vertex (between 0 and <em>V</em> - 1)
         * @return the name of the vertex associated with the integer {@code v}
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         * @deprecated Replaced by {@link #nameOf(int)}.
         */
        @Deprecated
        public String name(int v) {
            validateVertex(v);
            return keys[v];
        }

        /**
         * Returns the name of the vertex associated with the integer {@code v}.
         * @param  v the integer corresponding to a vertex (between 0 and <em>V</em> - 1)
         * @return the name of the vertex associated with the integer {@code v}
         * @throws IllegalArgumentException unless {@code 0 <= v < V}
         */
        public String nameOf(int v) {
            validateVertex(v);
            return keys[v];
        }

        /**
         * Returns the digraph assoicated with the symbol graph. It is the client's responsibility
         * not to mutate the digraph.
         *
         * @return the digraph associated with the symbol digraph
         * @deprecated Replaced by {@link #digraph()}.
         */
        @Deprecated
        public Digraph G() {
            return graph;
        }

        /**
         * Returns the digraph assoicated with the symbol graph. It is the client's responsibility
         * not to mutate the digraph.
         *
         * @return the digraph associated with the symbol digraph
         */
        public Digraph digraph() {
            return graph;
        }

        // throw an IllegalArgumentException unless {@code 0 <= v < V}
        private void validateVertex(int v) {
            int V = graph.V();
            if (v < 0 || v >= V)
                throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
        }
    }
    public static class SequentialSearchST<Key, Value> {
        private int n;           // number of key-value pairs
        private Node first;      // the linked list of key-value pairs

        // a helper linked list data type
        private class Node {
            private Key key;
            private Value val;
            private Node next;

            public Node(Key key, Value val, Node next)  {
                this.key  = key;
                this.val  = val;
                this.next = next;
            }
        }

        /**
         * Initializes an empty symbol table.
         */
        public SequentialSearchST() {
        }

        /**
         * Returns the number of key-value pairs in this symbol table.
         *
         * @return the number of key-value pairs in this symbol table
         */
        public int size() {
            return n;
        }

        /**
         * Returns true if this symbol table is empty.
         *
         * @return {@code true} if this symbol table is empty;
         *         {@code false} otherwise
         */
        public boolean isEmpty() {
            return size() == 0;
        }

        /**
         * Returns true if this symbol table contains the specified key.
         *
         * @param  key the key
         * @return {@code true} if this symbol table contains {@code key};
         *         {@code false} otherwise
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public boolean contains(Key key) {
            if (key == null) throw new IllegalArgumentException("argument to contains() is null");
            return get(key) != null;
        }

        /**
         * Returns the value associated with the given key in this symbol table.
         *
         * @param  key the key
         * @return the value associated with the given key if the key is in the symbol table
         *     and {@code null} if the key is not in the symbol table
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public Value get(Key key) {
            if (key == null) throw new IllegalArgumentException("argument to get() is null");
            for (Node x = first; x != null; x = x.next) {
                if (key.equals(x.key))
                    return x.val;
            }
            return null;
        }

        /**
         * Inserts the specified key-value pair into the symbol table, overwriting the old
         * value with the new value if the symbol table already contains the specified key.
         * Deletes the specified key (and its associated value) from this symbol table
         * if the specified value is {@code null}.
         *
         * @param  key the key
         * @param  val the value
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public void put(Key key, Value val) {
            if (key == null) throw new IllegalArgumentException("first argument to put() is null");
            if (val == null) {
                delete(key);
                return;
            }

            for (Node x = first; x != null; x = x.next) {
                if (key.equals(x.key)) {
                    x.val = val;
                    return;
                }
            }
            first = new Node(key, val, first);
            n++;
        }

        /**
         * Removes the specified key and its associated value from this symbol table
         * (if the key is in this symbol table).
         *
         * @param  key the key
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public void delete(Key key) {
            if (key == null) throw new IllegalArgumentException("argument to delete() is null");
            first = delete(first, key);
        }

        // delete key in linked list beginning at Node x
        // warning: function call stack too large if table is large
        private Node delete(Node x, Key key) {
            if (x == null) return null;
            if (key.equals(x.key)) {
                n--;
                return x.next;
            }
            x.next = delete(x.next, key);
            return x;
        }


        /**
         * Returns all keys in the symbol table as an {@code Iterable}.
         * To iterate over all of the keys in the symbol table named {@code st},
         * use the foreach notation: {@code for (Key key : st.keys())}.
         *
         * @return all keys in the symbol table
         */
        public Iterable<Key> keys()  {
            Queue<Key> queue = new Queue<Key>();
            for (Node x = first; x != null; x = x.next)
                queue.enqueue(x.key);
            return queue;
        }
    }

    public static class Queue<Item> implements Iterable<Item> {
        private Node<Item> first;    // beginning of queue
        private Node<Item> last;     // end of queue
        private int n;               // number of elements on queue

        // helper linked list class
        private static class Node<Item> {
            private Item item;
            private Node<Item> next;
        }

        /**
         * Initializes an empty queue.
         */
        public Queue() {
            first = null;
            last  = null;
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

        /**
         * Returns the item least recently added to this queue.
         *
         * @return the item least recently added to this queue
         * @throws NoSuchElementException if this queue is empty
         */
        public Item peek() {
            if (isEmpty()) throw new NoSuchElementException("Queue underflow");
            return first.item;
        }

        /**
         * Adds the item to this queue.
         *
         * @param  item the item to add
         */
        public void enqueue(Item item) {
            Node<Item> oldlast = last;
            last = new Node<Item>();
            last.item = item;
            last.next = null;
            if (isEmpty()) first = last;
            else           oldlast.next = last;
            n++;
        }

        /**
         * Removes and returns the item on this queue that was least recently added.
         *
         * @return the item on this queue that was least recently added
         * @throws NoSuchElementException if this queue is empty
         */
        public Item dequeue() {
            if (isEmpty()) throw new NoSuchElementException("Queue underflow");
            Item item = first.item;
            first = first.next;
            n--;
            if (isEmpty()) last = null;   // to avoid loitering
            return item;
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

        /**
         * Returns an iterator that iterates over the items in this queue in FIFO order.
         *
         * @return an iterator that iterates over the items in this queue in FIFO order
         */
        public Iterator<Item> iterator()  {
            return new LinkedIterator(first);
        }

        // an iterator, doesn't implement remove() since it's optional
        private class LinkedIterator implements Iterator<Item> {
            private Node<Item> current;

            public LinkedIterator(Node<Item> first) {
                current = first;
            }

            public boolean hasNext()  { return current != null;                     }
            public void remove()      { throw new UnsupportedOperationException();  }

            public Item next() {
                if (!hasNext()) throw new NoSuchElementException();
                Item item = current.item;
                current = current.next;
                return item;
            }
        }
    }
}
