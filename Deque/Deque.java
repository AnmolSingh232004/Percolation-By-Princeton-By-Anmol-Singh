// here instead of string we are using item
// if first is empty then last is too and vice versa

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    // construct an empty deque
    private Node first, last;
    private int size;

    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null.");
        }
        Node copyFirst = first;
        first = new Node();
        first.item = item;
        first.next = copyFirst;
        first.prev = null;
        if (isEmpty()) {
           last = first;
        }
        else {
            copyFirst.prev = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item is null.");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;
        last.next = null;
        if (isEmpty()) { // this ensures if deque is of size 0 there is connectivity b/w first and last
            // if I add element from the addFirst(); I can remove that single element by even using removeLast();
            first = last;
        } else {
            oldLast.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) { // lesson learnt dont add if (some staement that I think will make code bette without solid proof)
            throw new NoSuchElementException("Deque is empty.");
        }
        Item item = first.item;
        first = first.next;
        size--;

         if (first != null) {
             first.prev = null;
         } else {
             last = null;
         }
         return item;
    }
    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty." + size);
        }

        Item item = last.item;
        last = last.prev;
        size--;

        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

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
            if (current == null) {
                throw new NoSuchElementException("No more next Item.");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // deque here is interlinked which means if I add element from last only they will end up as first too if like they reach first
        // aka if 2 elements are added from the right the element added at first will become the element at first

        /* Deque<String> stringDeque = new Deque<String>();
         stringDeque.addFirst("a");
        stringDeque.addFirst("b");
        stringDeque.addLast("z");
        stringDeque.addLast("y");
        System.out.println(stringDeque.removeFirst());
        System.out.println(stringDeque.removeLast());
        System.out.println(stringDeque.isEmpty());
        System.out.println(stringDeque.size());
        stringDeque.removeLast();
        stringDeque.removeFirst();
        System.out.println(stringDeque.size());
        System.out.println(stringDeque.isEmpty());
        System.out.println(stringDeque.removeLast());
        System.out.println(stringDeque.removeFirst());
        stringDeque.addFirst("a");
        stringDeque.addFirst("b");
        stringDeque.addLast("z");
        stringDeque.addLast("y");
        System.out.println(stringDeque.isEmpty());
        System.out.println(stringDeque.size());
        Iterator<String> iterator = stringDeque.iterator();
       while (iterator.hasNext()) {
            System.out.println(iterator.next());
        } */

        Deque<Integer> deque = new Deque<>();
        /* deque.addFirst(1);
        deque.addFirst(2);
        deque.isEmpty() ;      //==> false
        deque.isEmpty()  ;      // ==> false
        deque.removeFirst() ;   // ==> 2
        deque.addFirst(6);
        deque.removeFirst()  ;   //==> 6
        System.out.println(deque.isEmpty());     // ==> true*/

        // deque.isEmpty();         // ==> true
        deque.addFirst(2); // last is first till now
        System.out.println(deque.size()); // size 1
        deque.addFirst(3);
        System.out.println(deque.removeLast());
        System.out.println(deque.removeLast());
        System.out.println(deque.size()); // size 2
        System.out.println(deque.removeLast()); // size 1, 2 removed, 3 left prolly here last becomes null
        System.out.println(deque.size()); // size 1
        System.out.println(deque.removeLast());    // ==> 2
        // deque.addFirst(5);
        // deque.removeLast();


    }
}
