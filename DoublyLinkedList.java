import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Samay Chandna
 * @version 1.0
 * @userid schandna3
 * @GTID 903380585
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     * <p>
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws IndexOutOfBoundsException if index < 0 or index > size
     * @throws IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            int middle = (size - 1) / 2;
            int count;
            DoublyLinkedListNode<T> curr;
            if (index > middle) {
                count = size - 1;
                curr = tail;
                while (curr != null) {
                    if (index == count) {
                        DoublyLinkedListNode<T> ins = new DoublyLinkedListNode<>(data, curr.getPrevious(), curr);
                        curr.getPrevious().setNext(ins);
                        curr.setPrevious(ins);
                        break;
                    }
                    curr = curr.getPrevious();
                    count--;
                }
            } else {
                count = 0;
                curr = head;
                while (curr != null) {
                    if (index == count) {
                        DoublyLinkedListNode<T> ins = new DoublyLinkedListNode<>(data, curr.getPrevious(), curr);
                        curr.getPrevious().setNext(ins);
                        curr.setPrevious(ins);
                        break;
                    }
                    curr = curr.getNext();
                    count++;
                }
            }
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        if (size == 0) {
            head = new DoublyLinkedListNode<>(data, null, null);
            tail = head;
        } else {
            DoublyLinkedListNode<T> curr = new DoublyLinkedListNode<>(data, null, head);
            head.setPrevious(curr);
            head = curr;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }
        if (size == 0) {
            addToFront(data);
        } else {
            DoublyLinkedListNode<T> curr = new DoublyLinkedListNode<>(data, tail, null);
            tail.setNext(curr);
            tail = curr;
            size++;
        }

    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     * <p>
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        } else if (index == 0) {
            T data = head.getData();
            head.getNext().setPrevious(null);
            head = head.getNext();
            size--;
            return data;
        } else if (index == size - 1) {
            T data = tail.getData();
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
            size--;
            return data;
        }
        if (index > (size - 1) / 2) {
            int count = size - 1;
            DoublyLinkedListNode<T> curr = tail;
            while (count > index) {
                curr = curr.getPrevious();
                count--;
            }
            T data = curr.getData();
            curr.getNext().setPrevious(curr.getPrevious());
            curr.getPrevious().setNext(curr.getNext());
            size--;
            return data;
        }
        int count = 0;
        DoublyLinkedListNode<T> curr = head;
        while (count < index) {
            curr = curr.getNext();
            count++;
        }
        T data = curr.getData();
        curr.getNext().setPrevious(curr.getPrevious());
        curr.getPrevious().setNext(curr.getNext());
        size--;
        return data;
    }

    /**
     * Removes and returns the first element of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The list is empty");
        } else {
            T data = head.getData();
            DoublyLinkedListNode<T> curr = head.getNext();
            curr.setPrevious(null);
            head = curr;
            size--;
            return data;
        }
    }

    /**
     * Removes and returns the last element of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("The list is empty");
        } else {
            T data = tail.getData();
            DoublyLinkedListNode<T> curr = tail.getPrevious();
            curr.setNext(null);
            tail = curr;
            size--;
            return data;
        }
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     * <p>
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds");
        } else if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        }
        if (index > (size - 1) / 2) {
            int count = size - 1;
            DoublyLinkedListNode<T> curr = tail;
            while (count > index) {
                curr = curr.getPrevious();
                count--;
            }
            return curr.getData();
        }
        int count = 0;
        DoublyLinkedListNode<T> curr = head;
        while (count < index) {
            curr = curr.getNext();
            count++;
        }
        return curr.getData();
    }

    /**
     * Returns whether or not the list is empty.
     * <p>
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     * <p>
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws IllegalArgumentException         if data is null
     * @throws java.util.NoSuchElementException if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        if (size == 0) {
            throw new NoSuchElementException("Data not in list");
        }
        if (data == tail.getData()) {
            removeFromBack();
        } else {
            DoublyLinkedListNode<T> curr = tail;
            int index = size - 1;
            while (index > 0) {
                T newdata = curr.getData();
                if (data.equals(newdata)) {
                    return removeAtIndex(index);
                }
                curr = curr.getPrevious();
                index--;
            }
        }
        throw new NoSuchElementException("Data not in list");
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     * <p>
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        if (size == 0) {
            return new Object[0];
        } else {
            Object[] array = new Object[size];
            DoublyLinkedListNode<T> curr = head;
            int count = 0;
            while (curr != null) {
                array[count] = curr.getData();
                curr = curr.getNext();
                count++;
            }
            return array;
        }

    }

    /**
     * Returns the head node of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
