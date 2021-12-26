import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * Implement insertion sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array and/or comparator is null");
        }
        int border = 1;
        while (border < arr.length) {
            int tempBorder = border;
            while (border > 0 && comparator.compare(arr[border], arr[border - 1]) < 0) {
                T checkData = arr[border - 1];
                arr[border - 1] = arr[border];
                arr[border] = checkData;
                border--;
            }
            border = tempBorder + 1;
        }
    }

    /**
     * Implement cocktail sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array and/or comparator is null");
        }
        boolean swapMade = true;
        int start = 0;
        int end = arr.length - 1;
        while (swapMade) {
            swapMade = false;
            int tempStart = start;
            int tempEnd = end;
            for (int i = start; i < end; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T swappedValue = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = swappedValue;
                    swapMade = true;
                    tempEnd = i;
                }
            }
            end = tempEnd;
            if (swapMade) {
                swapMade = false;
                for (int i = end; i > start; i--) {
                    if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                        T swappedValue = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = swappedValue;
                        swapMade = true;
                        tempStart = i;
                    }
                }
                start = tempStart;
            }
        }
    }

    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     * <p>
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     * <p>
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Array and/or comparator is null");
        }
        int mid = arr.length / 2;
        if (mid == 0) {
            return;
        }
        T[] leftArray = (T[]) new Object[mid];
        T[] rightArray = (T[]) new Object[arr.length - mid];
        for (int i = 0; i < mid; i++) {
            leftArray[i] = arr[i];
        }
        for (int i = mid; i < arr.length; i++) {
            rightArray[i - mid] = arr[i];
        }
        mergeSort(leftArray, comparator);
        mergeSort(rightArray, comparator);
        int leftIndex = 0;
        int rightIndex = 0;
        int currIndex = 0;
        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            if (comparator.compare(leftArray[leftIndex], rightArray[rightIndex]) <= 0) {
                arr[currIndex] = leftArray[leftIndex];
                leftIndex++;
            } else {
                arr[currIndex] = rightArray[rightIndex];
                rightIndex++;
            }
            currIndex++;
        }
        while (leftIndex < mid) {
            arr[currIndex] = leftArray[leftIndex];
            leftIndex++;
            currIndex++;
        }
        while (rightIndex < (arr.length - mid)) {
            arr[currIndex] = rightArray[rightIndex];
            rightIndex++;
            currIndex++;
        }
    }


    /**
     * Implement quick sort.
     * <p>
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     * <p>
     * int pivotIndex = rand.nextInt(b - a) + a;
     * <p>
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     * <p>
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     * <p>
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Array, comparator, and/or pivot index is null");
        }
        recQuickSort(arr, 0, arr.length - 1, comparator, rand);
    }

    /**
     * Private helper function to recursively quicksort
     *
     * @param arr        the array to be sorted
     * @param start      start index
     * @param end        end index
     * @param comparator object which compares two generic types
     * @param rand       random number to choose pivot column
     */
    private static <T> void recQuickSort(T[] arr, int start, int end, Comparator<T> comparator, Random rand) {
        if (end - start < 1) {
            return;
        }
        int pivotIndex = rand.nextInt(end - start + 1) + start;
        T swappedValue = arr[pivotIndex];
        arr[pivotIndex] = arr[start];
        arr[start] = swappedValue;
        int left = start + 1;
        int right = end;
        while (left <= right) {
            while (left <= right && comparator.compare(arr[left], arr[start]) <= 0) {
                left++;
            }
            while (left <= right && comparator.compare(arr[right], arr[start]) >= 0) {
                right--;
            }
            if (left <= right) {
                T swapped = arr[left];
                arr[left] = arr[right];
                arr[right] = swapped;
                left++;
                right--;
            }
        }
        T swapped = arr[start];
        arr[start] = arr[right];
        arr[right] = swapped;
        recQuickSort(arr, start, right - 1, comparator, rand);
        recQuickSort(arr, right + 1, end, comparator, rand);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     * <p>
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     * <p>
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     * <p>
     * Refer to the PDF for more information on LSD Radix Sort.
     * <p>
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     * <p>
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Array is null");
        }
        LinkedList<Integer>[] buckets = new LinkedList[19];
        int largestNum = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == Integer.MIN_VALUE) {
                largestNum = arr[i];
                break;
            } else {
                if (Math.abs(arr[i]) > largestNum) {
                    largestNum = Math.abs(arr[i]);
                }
            }
        }
        int maxDigits = 0;
        while (largestNum != 0) {
            maxDigits++;
            largestNum /= 10;
        }
        int nthDigit = 1;
        for (int i = 1; i <= maxDigits; i++) {
            for (int j = 0; j < arr.length; j++) {
                int bucket = (arr[j] / nthDigit) % 10;
                if (buckets[bucket + 9] == null) {
                    buckets[bucket + 9] = new LinkedList<>();
                }
                buckets[bucket + 9].addLast(arr[j]);
            }
            int idx = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (bucket != null && !bucket.isEmpty()) {
                    arr[idx++] = bucket.removeFirst();
                }
            }
            nthDigit *= 10;
        }
    }

    /**
     * Implement heap sort.
     * <p>
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     * <p>
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     * <p>
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        java.util.PriorityQueue<Integer> heap = new PriorityQueue<>(data);
        int[] result = new int[heap.size()];
        int count = 0;
        while (!heap.isEmpty()) {
            result[count++] = heap.remove();
        }
        return result;
    }
}
