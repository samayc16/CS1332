import java.util.ArrayList;

/**
 * Your implementation of a MinHeap.
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
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     * <p>
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     * <p>
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     * <p>
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null.");
        }
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        size = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new java.lang.IllegalArgumentException("Element within data is null.");
            } else {
                backingArray[i + 1] = data.get(i);
                size++;
            }
        }
        for (int i = size / 2; i > 0; i--) {
            heapifyDown(i);
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null");
        }
        size++;
        if (backingArray.length == size) {
            T[] temp = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i < size; i++) {
                temp[i] = backingArray[i];
            }
            backingArray = temp;
        }
        backingArray[size] = data;
        heapifyUp();
    }

    /**
     * Heapifies upward to make sure data is in minimum order
     */

    private void heapifyUp() {
        int count = size;
        T mradded = backingArray[count];
        while (count > 1 && mradded.compareTo(backingArray[count / 2]) < 0) {
            backingArray[count] = backingArray[count / 2];
            backingArray[count / 2] = mradded;
            count /= 2;
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after adding.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Heap is empty.");
        }
        T dtr = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        heapifyDown(1);
        return dtr;
    }

    /**
     * Heapifies downaward after removal
     */
    private void heapifyDown(int parent) {
        if (parent > size / 2) {
            return;
        }
        int left = 2 * parent;
        int right = 2 * parent + 1;
        T par = backingArray[parent];
        boolean heapifyleft = right > size && backingArray[left].compareTo(backingArray[right]) < 0;
        if (heapifyleft && backingArray[parent].compareTo(backingArray[left]) > 0) {
            backingArray[parent] = backingArray[left];
            backingArray[left] = par;
            heapifyDown(left);
        } else if (right <= size && backingArray[parent].compareTo(backingArray[right]) > 0) {
            backingArray[parent] = backingArray[right];
            backingArray[right] = par;
            heapifyDown(right);
        } else {
            return;
        }
    }


    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Heap is empty.");
        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     * <p>
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
