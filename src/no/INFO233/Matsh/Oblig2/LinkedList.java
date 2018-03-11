package no.INFO233.Matsh.Oblig2;


import com.beust.jcommander.internal.Lists;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;


public class LinkedList<E> implements IList<E> {

    private Node headNode;
    private Node tailNode;
    private int numberOfEntries;


    public LinkedList(E newEntry, IList<E> rest) {
        headNode = new Node(newEntry);
        tailNode = headNode;
        numberOfEntries++;
        if (size() >= 1) {
            for (E element : rest) {
                add(element);
            }
        }
    }

    public LinkedList(E newEntry) {
        headNode = new Node(newEntry);
        tailNode = headNode;
        numberOfEntries++;
    }

    // Legger ved en tom konstruktør for tomme lister.
    public LinkedList() {
        headNode = null;
        tailNode = null;
        numberOfEntries = 0;

    }


    private IList<E> reverse(IList<? extends E> list) {
        IList<E> reverse = new LinkedList<>();
        for (E data : list) {
            reverse.put(data);
        }
        return reverse;
    }

    private void swap(E a, E b) {
        E temp = a;
        a = b;
        b = temp;
    }


    private Node getReferenceTo(Object anEntry) {
        boolean found = false;
        Node currentNode = headNode;

        while (!found && (currentNode != null)) {
            if (anEntry.equals(currentNode.data)) {
                found = true;
            } else {
                currentNode = currentNode.getNext();
            }
        }
        return currentNode;
    }


    @Override
    public E first() throws NoSuchElementException {
        if (headNode != null) {
            E result = headNode.getData();
            return result;
        } else {
            throw new NoSuchElementException("Ingen elementer å returnere");
        }
    }

    @Override
    public E last() throws NoSuchElementException {
        if (tailNode != null) {
            E result = tailNode.getData();
            return result;
        } else {
            throw new NoSuchElementException("Ingen elementer å returnere");
        }
    }



    @Override
    public IList<E> rest() {
        IList<E> result = new LinkedList<E>();
        while (headNode.next != null) {
            headNode = headNode.getNext();
            result.add(headNode.data);
        }
        return result;
    }

    @Override
    public boolean add(E elem) {

        Node newNode = new Node(elem);
        if (numberOfEntries == 0) {
            tailNode = newNode;
            headNode = newNode;
        } else {
            tailNode.setNext(newNode);
            tailNode = newNode;

        }
        numberOfEntries++;
        return true;
    }


    @Override
    public boolean put(E elem) {
        Node newNode = new Node(elem);

        if (numberOfEntries == 0) {
            headNode = newNode;
            tailNode = newNode;
        } else {
            newNode.setNext(headNode);
            headNode = newNode;
        }
        numberOfEntries++;
        return true;
    }

    @Override
    public E remove() throws NoSuchElementException {
        E result;
        if (numberOfEntries != 0) {
            result = headNode.data;
            headNode = headNode.getNext();
            numberOfEntries--;
        } else {
            throw new NoSuchElementException("No such element in the list");
        }
        return result;
    }



    @Override
    public boolean remove(Object o) {

        boolean result = false;
        Node nodeN = getReferenceTo(o);
        if (nodeN != null) {
            nodeN.data = headNode.data;
            headNode = headNode.next;
            numberOfEntries--;
            result = true;
        }
        return result;
    }

    @Override
    public boolean contains(Object o) {
        boolean found = false;
        Node currentNode = headNode;
        while (!found && (currentNode != null)) if (o.equals(currentNode.getData())) found = true;
        else {
            currentNode = currentNode.getNext();
        }
        return found;
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    //  Legger til alle elementene i den angitte listen på
    // slutten av listen.
    @Override
    public void append(IList<? extends E> list) {
        assert !list.isEmpty();
        for (E element : list) {
            add(element);
        }
    }


    @Override
    public void prepend(IList<? extends E> list) {
        list = reverse(list);
        for (E element : list) {
            put(element);
        }
    }

    /**
     * ,* Slår sammen flere lister
     * ,*
     * ,* @param lists listene som skal slås sammen
     * ,* @return Ny liste med alle elementene fra listene som
     * ,* skal slås sammen.
     * ,
     */

    @Override
    @SuppressWarnings("unchecked")
    public IList<E> concat(IList<? extends E>... lists) {
        IList<E> result = new LinkedList<>();

        for (IList<? extends E> elem : lists) {
            result.append(elem);
        }
        return result;
    }

    @Override
    public void sort(Comparator<? super E> c) {
// Bubblesort
        if (size() > 1) {
            for (int i = 0; i < size(); i++) {
                Node current = headNode;
                Node nextNode = headNode.next;
                for (int j = 0; j < size() - 1; j++) {
                    if (c.compare(current.data, nextNode.data) >= 1) {
                        E temp = current.data;
                        current.data = nextNode.data;
                        nextNode.data = temp;
                    }
                    current = nextNode;
                    nextNode = nextNode.next;

                }
            }
        }

    }

    @Override
    public void filter(Predicate<? super E> p) {
        for (E elem : this) {
            if (p.test(elem)) {
                remove(elem);
            }
        }

    }

    @Override
    public <U> IList<U> map(Function<? super E, ? extends U> f) {
        IList<U> list = new LinkedList<>();
        for (E elem : this) {
            list.add(f.apply(elem));
        }
        return list;
    }

    @Override
    public <T> T reduce(T t, BiFunction<T, ? super E, T> f) {

        T result = t;
        for (E elem : this) {
            result = f.apply(result, elem);
        }
        return result;
    }

    @Override
    public int size() {
        return this.numberOfEntries;
    }

    @Override
    public void clear() {
        headNode = null;
        tailNode = null;
        numberOfEntries = 0;
    }

    @Override
    public E[] toArray() {
        if (isEmpty()) {
            return null;
        }
        @SuppressWarnings("unchecked")
        E[] array = (E[]) new Object[size()];
        int index = 0;
        for (E value : this) {
            array[index] = value;
            index++;
        }
        return array;
    }


    @Override
    public Iterator<E> iterator() {

        return new Iterator<>() {
            private Node current = null;

            @Override
            public boolean hasNext() {
                return !isEmpty() && current != tailNode;
            }

            @Override
            public E next() {
                if (current == null) {
                    current = headNode;
                    return current.data;
                }
                if (current.next == null) {
                    throw new NoSuchElementException();
                }
                current = current.next;
                return current.data;
            }

            @Override
            public void remove() {

                    throw new UnsupportedOperationException("Ikke implementert");

            }


        };
    }


    private class Node {
        private Node next = null;
        private E data;

        private Node(E dataPortion) {
            this(dataPortion, null);
        }

        private Node(E dataPortion, Node nextNode) {
            data = dataPortion;
            next = nextNode;

        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public E getData() {
            return data;
        }


        public void setData(E data) {
            this.data = data;
        }

    }
}
