import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node first, last;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
        last = null;
        size = 0;
    }
    private class Node {
        Item item;
        Node next;
        Node prev;
    }


    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null.");
        }
        /* if (size == 1) {
            first = last;
        } */
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            first.prev = null;
            last = first;
        } else {
            Node copy = first;
            first = new Node();
            first.item = item;
            first.next = copy;
            first.prev = null;
            copy.prev = first;
        }
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty.");
        }
        int randomNumber = StdRandom.uniformInt(0, size);
        Node current = first;
        for (int i = 0; i < randomNumber; i++) {
            current = current.next;
        }
        if (current == first) {
            Item item = first.item;
            first = first.next;
            if (first != null) {
                first.prev = null;
            } else {
                last = null;
            }
            size--;
            return item; // middle wont execute because of return
        }

        if (current == last) {
            Item item = last.item;
            last = last.prev;
            if (last != null) {
                last.next = null;
            } else {
                first = null;
            }
            size--;
            return item;
        }
            current.prev.next = current.next; // this fixes shit https://chat.openai.com/c/cb36c838-0c3e-44f2-833e-47d6d3603ec3 explained here kinda
            current.next.prev = current.prev;

        size--;
        return current.item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty.");
        }
        int randomNumber = StdRandom.uniformInt(0, size);
        Node current = first;
        for (int i = 0; i < randomNumber; i++) {
            current = current.next;
        }
        return current.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new DequueIterator();
    }
    // return an independent iterator over items in random order
    private class DequueIterator implements Iterator<Item> {
        private Node current = first;
        private Node current2 = last;
        private String[] itemArray = new String[size];
        private int count = -1;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Operation not supported.");
        }

        @Override
        public Item next() {
            if (current == null) /* || current.prev == null */ {
                throw new NoSuchElementException("No more next Item.");
            }
            count++;
            if (count == 0) {
                Node currentNode = first;
            for (int i = 0; i < size; i++) {
                Node node = currentNode.next;
                itemArray[i] = (String) node.item;
                currentNode = currentNode.next;
            }

                StdRandom.shuffle(itemArray);
            }
            return (Item) itemArray[count];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        /* RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue("BadBoy");
        randomizedQueue.enqueue("BadBoy2");
        randomizedQueue.enqueue("BadBoy3");
        randomizedQueue.enqueue("BadBoy4");
        randomizedQueue.enqueue("BadBoy4");
        randomizedQueue.enqueue("BadBoy5");
        randomizedQueue.enqueue("BadBoy6");
        randomizedQueue.enqueue("BadBoy7");
        System.out.println(randomizedQueue.size());
        System.out.println(randomizedQueue.sample());
        System.out.println(randomizedQueue.size());
        System.out.println(randomizedQueue.dequeue());
        System.out.println(randomizedQueue.size()); */
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
       StdOut.println(queue.isEmpty());
        queue.enqueue(10);
        queue.enqueue(356);
        StdOut.println(queue.dequeue());
        queue.enqueue(366);
        queue.enqueue(91);
        queue.enqueue(136);
        StdOut.println(queue.dequeue());


    }
}