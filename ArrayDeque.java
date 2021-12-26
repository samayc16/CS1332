/**
 * Your implementation of an ArrayDeque.
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
public class ArrayDeque<T> {

    /**
     * The initial capacity of the ArrayDeque.
     * <p>
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 11;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * Constructs a new ArrayDeque.
     */
    public ArrayDeque() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the element to the front of the deque.
     * <p>
     * If sufficient space is not available in the backing array, resize it to
     * double the current capacity. When resizing, copy elements to the
     * beginning of the new array and reset front to 0. After the resize and add
     * operation, the new data should be at index 0 of the array. Consider how
     * to do this efficiently.
     * <p>
     * Must be amortized O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null.");
        }
        if (size == backingArray.length) {
            T[] temp = (T[]) new Object[size * 2];
            temp[0] = data;
            int backarrptr = front;
            int newptr = 1;
            while (backarrptr != front) {
                temp[newptr] = backingArray[backarrptr];
                newptr++;
                backarrptr = mod(backarrptr + 1, backingArray.length);
            }
            backingArray = temp;
            front = 0;
        } else {
            int ptr = mod(front - 1, backingArray.length);
            backingArray[ptr] = data;
            front = ptr;
        }
        size++;

    }

    /**
     * Adds the element to the back of the deque.
     * <p>
     * If sufficient space is not available in the backing array, resize it to
     * double the current capacity. When resizing, copy elements to the
     * beginning of the new array and reset front to 0.
     * <p>
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null.");
        }
        if (size == backingArray.length) {
            T[] temp = (T[]) new Object[this.size * 2];
            int backarrptr = front;
            int newptr = 0;
            while (backarrptr != front) {
                    temp[newptr] = backingArray[backarrptr];
                    newptr++;
                    backarrptr = mod(backarrptr + 1, backingArray.length);
            }
            temp[newptr] = data;
            backingArray = temp;
            front = 0;
        } else {
            int ptr = mod(front + size, backingArray.length);
            backingArray[ptr] = data;
        }
        this.size++;
    }

    /**
     * Removes and returns the first element of the deque.
     * <p>
     * Do not grow or shrink the backing array.
     * <p>
     * If the deque becomes empty as a result of this call, do not reset
     * front to 0. Rather, modify the front index as if the deque did not become
     * empty as a result of this call.
     * <p>
     * Replace any spots that you remove from with null. Failure to do so can
     * result in loss of points.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Deque is empty");
        } else {
            T data = backingArray[front];
            backingArray[front] = null;
            front = mod(front + 1, backingArray.length);
            size--;
            return data;
        }
    }

    /**
     * Removes and returns the last element of the deque.
     * <p>
     * Do not grow or shrink the backing array.
     * <p>
     * If the deque becomes empty as a result of this call, do not reset
     * front to 0.
     * <p>
     * Replace any spots that you remove from with null. Failure to do so can
     * result in loss of points.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Deque is empty");
        } else {
            int ptr = mod(front + size - 1, backingArray.length);
            T data = backingArray[ptr];
            backingArray[ptr] = null;
            size--;
            return data;
        }
    }

    /**
     * Returns the first data of the deque without removing it.
     * <p>
     * Must be O(1).
     *
     * @return the first data
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Deque is empty");
        } else {
            return backingArray[front];
        }
    }

    /**
     * Returns the last data of the deque without removing it.
     * <p>
     * Must be O(1).
     *
     * @return the last data
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Deque is empty");
        } else {
            return backingArray[mod(front + size - 1, backingArray.length)];
        }
    }

    /**
     * Returns the backing array of the deque.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the deque
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the deque.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the smallest non-negative remainder when dividing index by
     * modulo. So, for example, if modulo is 5, then this method will return
     * either 0, 1, 2, 3, or 4, depending on what the remainder is.
     * <p>
     * This differs from using the % operator in that the % operator returns
     * the smallest answer with the same sign as the dividend. So, for example,
     * (-5) % 6 => -5, but with this method, mod(-5, 6) = 1.
     * <p>
     * Examples:
     * mod(-3, 5) => 2
     * mod(11, 6) => 5
     * mod(-7, 7) => 0
     * <p>
     * This helper method is here to make the math part of the circular
     * behavior easier to work with.
     *
     * @param index  the number to take the remainder of
     * @param modulo the divisor to divide by
     * @return the remainder in its smallest non-negative form
     * @throws java.lang.IllegalArgumentException if the modulo is non-positive
     */
    private static int mod(int index, int modulo) {
        // DO NOT MODIFY THIS METHOD!
        if (modulo <= 0) {
            throw new IllegalArgumentException("The modulo must be positive");
        }
        int newIndex = index % modulo;
        return newIndex >= 0 ? newIndex : newIndex + modulo;
    }
}
