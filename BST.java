import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;

/**
 * Your implementation of a BST.
 *
 * @author Samay Chandna
 * @version 1.0
 * @userid schandna3
 * @GTID Y903380585
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize an empty BST.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     * <p>
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     * <p>
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Collection is null.");
        }
        for (T node : data) {
            if (node == null) {
                throw new java.lang.IllegalArgumentException("Data within collection is null.");
            }
            add(node);
        }

    }

    /**
     * Adds the data to the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * The data becomes a leaf in the tree.
     * <p>
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null.");
        }
        root = recadd(root, data);
        size++;
    }

    private BSTNode<T> recadd(BSTNode<T> curr, T data) {
        if (curr == null) {
            return new BSTNode<T>(data);
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(recadd(curr.getRight(), data));
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(recadd(curr.getLeft(), data));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null");
        }
        BSTNode<T> storage = new BSTNode<>(null);
        root = recrem(root, data, storage);
        return storage.getData();
    }

    private BSTNode<T> recrem(BSTNode<T> curr, T data, BSTNode<T> storage) {
        if (curr == null) {
            throw new java.util.NoSuchElementException("Data is not in tree");
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(recrem(curr.getRight(), data, storage));
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(recrem(curr.getLeft(), data, storage));
        } else {
            storage.setData(curr.getData());
            size--;
            if (curr.getRight() == null && curr.getLeft() == null) {
                return null;
            } else if (curr.getLeft() == null && curr.getRight() != null) {
                return curr.getRight();
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                return curr.getLeft();
            } else {
                BSTNode<T> succ = new BSTNode<T>(null);
                curr.setRight(getsucc(curr.getRight(), succ));
                curr.setData(succ.getData());
            }
        }
        return curr;
    }

    private BSTNode<T> getsucc(BSTNode<T> curr, BSTNode<T> storage) {
        if (curr.getLeft() == null) {
            storage.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(getsucc(curr.getLeft(), storage));
        }
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     * <p>
     * This must be done recursively.
     * <p>
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null.");
        }
        return recgd(root, data);
    }

    private T recgd(BSTNode<T> curr, T data) {
        if (curr.getData().compareTo(data) == 0) {
            return curr.getData();
        } else if (data.compareTo(curr.getData()) < 0) {
            return recgd(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return recgd(curr.getRight(), data);
        }
        throw new java.util.NoSuchElementException("This data is not in the tree");
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Hint: Should you use value equality or reference equality?
     * <p>
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null");
        }
        return recgd(root, data) != null;
    }

    /**
     * Generate a pre-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> preList = new ArrayList<>(size);
        recpre(root, preList);
        return preList;
    }

    private void recpre(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            list.add(curr.getData());
            recpre(curr.getLeft(), list);
            recpre(curr.getRight(), list);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> ioList = new ArrayList<>(size);
        recio(root, ioList);
        return ioList;
    }

    private void recio(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            recio(curr.getLeft(), list);
            list.add(curr.getData());
            recio(curr.getRight(), list);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> postList = new ArrayList<>(size);
        recpost(root, postList);
        return postList;
    }

    private void recpost(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            recpost(curr.getLeft(), list);
            recpost(curr.getRight(), list);
            list.add(curr.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     * <p>
     * This does not need to be done recursively.
     * <p>
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     * <p>
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> loList = new ArrayList<>(size);
        LinkedList<BSTNode<T>> llq = new LinkedList<>();
        llq.addLast(root);
        while (llq.size() > 0) {
            BSTNode<T> curr = llq.removeFirst();
            if (curr != null) {
                loList.add(curr.getData());
                llq.addLast(curr.getLeft());
                llq.addLast(curr.getRight());
            }
        }
        return loList;
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * This must be done recursively.
     * <p>
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     * <p>
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return recheight(root);
    }

    private int recheight(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            int lh = recheight(curr.getLeft());
            int rh = recheight(curr.getRight());
            if (lh > rh) {
                return lh + 1;
            } else {
                return rh + 1;
            }
        }
    }

    /**
     * Clears the tree.
     * <p>
     * Clears all data and resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     * <p>
     * This must be done recursively.
     * <p>
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     * <p>
     * EXAMPLE: Given the BST below composed of Integers:
     * <p>
     * 50
     * /    \
     * 25      75
     * /  \
     * 12   37
     * /  \    \
     * 10  15    40
     * /
     * 13
     * <p>
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     * <p>
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */
    public List<T> kLargest(int k) {
        if (k > size || k < 0) {
            throw new java.lang.IllegalArgumentException("K must be within bounds.");
        }
        LinkedList<T> klist = new LinkedList<>();
        reck(root, k, klist);
        return klist;
    }

    private void reck(BSTNode<T> curr, int k, LinkedList<T> list) {
        if (list.size() == k || curr == null) {
            return;
        } else {
            reck(curr.getRight(), k, list);
            if (list.size() < k) {
                list.addFirst(curr.getData());
                reck(curr.getLeft(), k, list);
            }
        }
    }

    /**
     * Returns the root of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
