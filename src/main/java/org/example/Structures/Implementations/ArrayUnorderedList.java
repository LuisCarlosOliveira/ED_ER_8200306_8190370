package org.example.Structures.Implementations;


import org.example.Structures.Interfaces.UnorderedListADT;

public class ArrayUnorderedList<T> extends ArrayList<T>
        implements UnorderedListADT<T> {

    /**
     * Adds an element to the front of the list.
     *
     * @param element the element to be added
     */
    @Override
    public void addToFront(T element) {
        System.arraycopy(array, 0, array, 1, rear);
        array[0] = (T) element;
        rear++;
        modCount++;
    }

    /**
     * Adds an element to the rear of the list.
     *
     * @param element the element to be added
     */
    @Override
    public void addToRear(T element) {
        array[rear] = (T) (element);
        rear++;
        modCount++;
    }

    /**
     * Adds an element after an element of the list.
     *
     * @param element the element to be added
     * @param previous the previous element of the new element.
     */
    @Override
    public void addAfter(T element, T previous) {
        if (!super.contains(previous)) {
            return;
        }

        int previousIndex = super.returnElementIndex(previous);

        System.arraycopy(array, previousIndex + 1, array, previousIndex + 2,
                rear - previousIndex + 1);
        array[previousIndex + 1] = (T) element;
        rear++;
        modCount++;
    }

}
