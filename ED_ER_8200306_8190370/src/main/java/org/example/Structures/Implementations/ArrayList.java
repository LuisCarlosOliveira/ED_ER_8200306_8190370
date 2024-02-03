package org.example.Structures.Implementations;

import org.example.Structures.Interfaces.ListADT;

import java.util.Iterator;


public abstract class ArrayList<T> implements ListADT<T> {

    private final int DEFAULT_CAPACITY = 10;

    /**
     * The class array.
     */
    protected T[] array;

    /**
     * The last element index.
     */
    protected int rear;

    /**
     * The number of operations made.
     */
    protected int modCount;

    /**
     * Default ArrayList class constructor.
     */
    public ArrayList() {
        this.array = (T[]) (new Object[DEFAULT_CAPACITY]);
        this.rear = 0;
        this.modCount = 0;
    }

    protected void shiftLeft(int previous) {
        int tam = this.size() - 1;
        T[] temp = (T[]) new Object[tam];
        int x = 0;

        int y;
        for(y = 0; x < previous; ++x) {
            temp[y] = this.array[x];
            ++y;
        }

        for(x = previous + 1; x < this.size(); ++x) {
            temp[y] = this.array[x];
            ++y;
        }

        this.array = temp;
    }

    protected void shiftRight(int inicial, Object add) {
        int tam = this.size() + 1;
        T[] newArray = (T[]) new Object[tam];
        newArray[inicial] = (T) add;

        int i;
        for(i = 0; i < inicial; ++i) {
            newArray[i] = this.array[i];
        }

        for(i = inicial + 1; i < tam; ++i) {
            newArray[i] = this.array[i - 1];
        }

        this.array = newArray;
    }
    protected void expandCapacity() {
        T[] newArray = (T[]) new Object[this.array.length + 5];

        for(int x = 0; x < this.modCount; ++x) {
            newArray[x] = this.array[x];
        }

        this.array = newArray;
    }

    private void shiftArray(int pos) {
        for (int i = pos; i < this.rear - 1; i++) {
            this.array[i] = this.array[i + 1];
        }
    }

    /**
     * Removes and returns the first element from this list.
     *
     * @return the first element from this list
     */
    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }

        T element = this.array[0];
        this.shiftArray(0);
        this.array[--this.rear] = null;
        this.modCount++;

        return element;
    }

    /**
     * Removes and returns the last element from this list.
     *
     * @return the last element from this list
     */
    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }

        T element = this.array[--this.rear];
        this.array[this.rear] = null;
        this.modCount++;

        return element;
    }

    protected int returnElementIndex(T element) {
        int index = -1;

        for (int i = 0; i < this.rear; i++) {
            if (this.array[i].equals(element)) {
                index = i;
                return index;
            }
        }
        return index;
    }

    /**
     * Removes and returns the specified element from this list.
     *
     * @param element the element to be removed from the list
     * @return the element that was removed
     */
    @Override
    public T remove(T element) {
        int index = this.returnElementIndex(element);

        if (index == -1) {
            return null;
        }

        this.shiftArray(index);
        this.array[--this.rear] = null;
        this.modCount++;

        return element;
    }

    /**
     * Returns a reference to the first element in this list.
     *
     * @return a reference to the first element in this list
     */
    @Override
    public T first() {
        return this.array[0];
    }

    /**
     * Returns a reference to the last element in this list.
     *
     * @return a reference to the last element in this list
     */
    @Override
    public T last() {
        return this.array[this.rear - 1];
    }

    /**
     * Returns a reference to an element in the index
     *
     * @return a reference to an element in the index
     */
    public T get(int index){
        if(rear<=index||index<0) throw new ArrayIndexOutOfBoundsException();
        return array[index];
    }

    /**
     * Returns true if this list contains the specified target element.
     *
     * @param target the target that is being sought in the list
     * @return true if the list contains this element
     */
    @Override
    public boolean contains(T target) {
        for (int i = 0; i < this.rear; i++) {
            if (this.array[i].equals(target)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if this list contains no elements.
     *
     * @return true if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        return this.rear == 0;
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the integer representation of number of elements in this list
     */
    @Override
    public int size() {
        return this.rear;
    }

    /**
     * Returns an iterator for the elements in this list.
     *
     * @return an iterator over the elements in this list
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator<>(this.modCount);
    }

    /**
     * Returns a string representation of this list.
     *
     * @return a string representation of this list
     */
    @Override
    public String toString() {
        String str = "";

        for (int i = 0; i < this.rear; i++) {
            str += this.array[i].toString() + "; ";
        }
        return str;
    }

    private class ArrayListIterator<T> implements Iterator<T> {

        private int expectedModcount;
        private int current;
        private boolean okToRemove;

        private ArrayListIterator(int modCount) {
            this.expectedModcount = modCount;
            this.current = 0;
            this.okToRemove = false;
        }

        @Override
        public boolean hasNext() {
            return this.current < rear;
        }

        @Override
        public T next() {
            if (this.expectedModcount != modCount) {
                throw new IllegalStateException();
            }

            if (!this.hasNext()) {
                return null;
            }

            this.okToRemove = true;

            return (T) array[this.current++];
        }

        @Override
        public void remove() {
            if (!this.okToRemove) {
                return;
            }

            ArrayList.this.remove(array[--this.current]);
            this.expectedModcount++;
            this.okToRemove = false;
        }
    }
}
