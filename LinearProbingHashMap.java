import java.util.*;

/**
 * Your implementation of a LinearProbingHashMap.
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
public class LinearProbingHashMap<K, V> {

    /**
     * The initial capacity of the LinearProbingHashMap when created with the
     * default constructor.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the LinearProbingHashMap
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private LinearProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new LinearProbingHashMap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * <p>
     * Use constructor chaining.
     */
    public LinearProbingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new LinearProbingHashMap.
     * <p>
     * The backing array should have an initial capacity of initialCapacity.
     * <p>
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public LinearProbingHashMap(int initialCapacity) {
        table = new LinearProbingMapEntry[initialCapacity];
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     * <p>
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     * <p>
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     * <p>
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     * <p>
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        return put(key, value, true);
    }

    /**
     * Used private out method to call while resizing
     *
     * @param key    the key to add
     * @param value  the value to add
     * @param resize determines weather or not the function will resize depending on where it is called from
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */

    private V put(K key, V value, Boolean resize) {
        if (key == null || value == null) {
            throw new java.lang.IllegalArgumentException("Key or value is null");
        }
        LinearProbingMapEntry<K, V> tbp = new LinearProbingMapEntry<>(key, value);
        int currind = key.hashCode() % table.length;
        int remind = table.length;
        if (resize && (double) (size + 1) / table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
        }
        for (int p = 0; p < table.length; p++) {
            INSTRUCTOR
                    | 10/28 AT 12:31 AM
            You should stop iterating if table[index] is null OR if you find a duplicate key OR if you have iterated over the entire backing array.

            This also applies to remove, get, and containsKey.

            if (table[(currind + p) % table.length] != null && table[(currind + p) % table.length].isRemoved() &&
                    ((currind + p) % table.length + 1 < remind)) {
                remind = (currind + p) % table.length;
            } else if (table[(currind + p) % table.length] == null && ((currind + p) % table.length < remind)) {
                table[(currind + p) % table.length] = tbp;
                size++;
                return null;
            } else if (table[(currind + p) % table.length] != null &&
                    table[(currind + p) % table.length].getKey().equals(tbp.getKey())) {
                V valuetwo = table[(currind + p) % table.length].getValue();
                if (table[(currind + p) % table.length].isRemoved()) {
                    break;
                }
                table[(currind + p) % table.length] = tbp;
                return valuetwo;
            }
        }
        table[remind] = tbp;
        size++;
        return null;
    }

    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new java.lang.IllegalArgumentException("Key is null");
        }
        int currind = key.hashCode() % table.length;
        int p = 0;
        while (p < table.length) {
            if (table[(currind + p) % table.length] != null
                    && table[(currind + p) % table.length].getKey().equals(key)) {
                if (table[(currind + p) % table.length].isRemoved()) {
                    break;
                }
                table[(currind + p) % table.length].setRemoved(true);
                size--;
                return table[(currind + p) % table.length].getValue();
            }
            p++;
        }
        throw new java.util.NoSuchElementException("This key is not in the map");
    }


    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new java.lang.IllegalArgumentException("Key is null");
        }
        int currind = key.hashCode() % table.length;
        int p = 0;
        while (p < table.length) {
            if (table[(currind + p) % table.length] != null
                    && table[(currind + p) % table.length].getKey().equals(key)) {
                if (table[(currind + p) % table.length].isRemoved()) {
                    break;
                }
                return table[(currind + p) % table.length].getValue();
            }
            p++;
        }
        throw new java.util.NoSuchElementException("This key is not in the map");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new java.lang.IllegalArgumentException("Key is null");
        }
        int currind = key.hashCode() % table.length;
        int p = 0;
        while (p < table.length) {
            if (table[(currind + p) % table.length] != null
                    && table[(currind + p) % table.length].getKey().equals(key)) {
                if (table[(currind + p) % table.length].isRemoved()) {
                    return false;
                }
                return true;
            }
            p++;
        }
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * <p>
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> keyset = new HashSet<K>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                keyset.add(table[i].getKey());
                INSTRUCTOR
                        | 10/28 AT 12:33 AM
                After you've added to keyset, you should check if the size of keyset is equal to size. If it is, you can break out of the loop.

                The same applies to values.

            }
        }
        return keyset;
    }

    /**
     * Returns a List view of the values contained in this map.
     * <p>
     * Use java.util.ArrayList or java.util.LinkedList.
     * <p>
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        ArrayList<V> list = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                list.add(table[i].getValue());
            }
        }
        return list;
    }

    /**
     * Resize the backing table to length.
     * <p>
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     * <p>
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     * You should NOT copy over removed elements to the resized backing table.
     * <p>
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     * <p>
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new java.lang.IllegalArgumentException("The desired length is shorter than the number of items");
        }
        LinearProbingMapEntry<K, V>[] temp = table;
        table = new LinearProbingMapEntry[length];
        size = 0;
        for (int i = 0; i < temp.length; i++) {
            INSTRUCTOR
                    | 10/28 AT 12:37 AM
            Similarly to the put method, you must stop iterating after you've iterated over 'size' number of non-null, non-removed elements.

            if (temp[i] != null && !temp[i].isRemoved()) {
                put(temp[i].getKey(), temp[i].getValue(), false);
            }
        }
    }

    /**
     * Clears the map.
     * <p>
     * Resets the table to a new array of the INITIAL_CAPACITY and resets the
     * size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        table = new LinearProbingMapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the table of the map.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public LinearProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}