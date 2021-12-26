import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Samay Chandna
 * @version 1.0
 * @userid schandna3
 * @GTID 903380585
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     * <p>
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
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
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * AVL and then rotate the tree as needed.
     * <p>
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     * <p>
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @param data the data to be added
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null.");
        }
        root = recadd(root, data);
    }

    /**
     * Recursive function to add new node to AVL
     *
     * @param curr current node being recursively added upon
     * @param data data to be added
     * @return returns pointer after checking for rotation for pointer reinforcement
     */
    private AVLNode<T> recadd(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new AVLNode<>(data);
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(recadd(curr.getRight(), data));
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(recadd(curr.getLeft(), data));
        }
        update(curr);
        return rotate(curr);
    }

    /**
     * O(1) function to update node's balance factor and height
     *
     * @param curr the current node who's height and balance factor are being updated
     */
    private void update(AVLNode<T> curr) {
        int hl = curr.getLeft() != null ? curr.getLeft().getHeight() : -1;
        int hr = curr.getRight() != null ? curr.getRight().getHeight() : -1;
        curr.setHeight(1 + Math.max(hl, hr));
        curr.setBalanceFactor(hl - hr);
    }

    /**
     * Checks if node needs to be rotated, and rotates depending on the balance factor
     *
     * @param curr the current node whose rotation is being checked
     * @return the node whose rotation is being checked
     */
    private AVLNode<T> rotate(AVLNode<T> curr) {
        int bfl = curr.getLeft() != null ? curr.getLeft().getBalanceFactor() : -1;
        int bfr = curr.getRight() != null ? curr.getRight().getBalanceFactor() : -1;
        if (curr.getBalanceFactor() < -1) {
            if (bfr > 0) {
                curr.setRight(rotateRight(curr.getRight()));
            }
            curr = rotateLeft(curr);
        } else if (curr.getBalanceFactor() > 1) {
            if (bfl < 0) {
                curr.setLeft(rotateLeft(curr.getLeft()));
            }
            curr = rotateRight(curr);
        }
        return curr;
    }

    /**
     * Rotates node right
     *
     * @param curr node that is being rotated right
     * @return returns the left child of the current node
     */
    private AVLNode<T> rotateRight(AVLNode<T> curr) {
        AVLNode<T> leftChild = curr.getLeft();
        curr.setLeft(leftChild.getRight());
        leftChild.setRight(curr);
        update(curr);
        update(leftChild);
        return leftChild;
    }

    /**
     * Rotates node left
     *
     * @param curr node that is being rotated left
     * @return returns the right child of the current node
     */
    private AVLNode<T> rotateLeft(AVLNode<T> curr) {
        AVLNode<T> rightChild = curr.getRight();
        curr.setRight(rightChild.getLeft());
        rightChild.setLeft(curr);
        update(curr);
        update(rightChild);
        return rightChild;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     * <p>
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the preessor node.
     * <p>
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     * @throws IllegalArgumentException         if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data is null");
        }
        AVLNode<T> storage = new AVLNode<>(null);
        root = recrem(root, data, storage);
        return storage.getData();
    }

    /**
     * Recursively removes selected node
     *
     * @param curr    current node being checked for removal
     * @param data    data of the node to be removed
     * @param storage stores the successor if node has two children
     * @return returns node that will replace removed node
     */
    private AVLNode<T> recrem(AVLNode<T> curr, T data, AVLNode<T> storage) {
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
                AVLNode<T> succ = new AVLNode<T>(null);
                curr.setRight(getsucc(curr.getRight(), succ));
                curr.setData(succ.getData());
            }
        }
        update(curr);
        return rotate(curr);
    }

    /**
     * Recursively checks for the successor node if node has two children
     *
     * @param curr    current node being checked to see if it is the sucessor
     * @param storage stores the data of the successor node
     * @return returns the current node after checking for rotation
     */
    private AVLNode<T> getsucc(AVLNode<T> curr, AVLNode<T> storage) {
        if (curr.getLeft() == null) {
            storage.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(getsucc(curr.getLeft(), storage));
        }
        update(curr);
        return rotate(curr);
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     * @throws IllegalArgumentException         if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        AVLNode<T> check = recgd(root, data);
        if (check == null) {
            throw new java.util.NoSuchElementException("The data does not exist");
        }
        return check.getData();
    }

    /**
     * Recursively searches for node whose data matches inputted data
     *
     * @param curr current node whose data is being checked
     * @param data data node is being checked with
     * @return returns current node if data matches, or null if data is not found
     */

    private AVLNode<T> recgd(AVLNode<T> curr, T data) {
        if (curr == null || curr.getData().equals(data)) {
            return curr;
        } else if (data.compareTo(curr.getData()) < 0) {
            return recgd(curr.getLeft(), data);
        }
        return recgd(curr.getRight(), data);
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     * @throws IllegalArgumentException if the data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        return recgd(root, data) != null;
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     * <p>
     * Your list should not duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     * <p>
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     * <p>
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     * <p>
     * Example Tree:
     * 10
     * /        \
     * 5          15
     * /   \      /    \
     * 2     7    13    20
     * / \   / \     \  / \
     * 1   4 6   8   14 17  25
     * /           \          \
     * 0             9         30
     * <p>
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> branchList = new LinkedList<T>();
        recDB(root, branchList);
        return branchList;
    }

    /**
     * Recursively checks for the data in a pre-order fashion to see if it is part of the deepest branch
     *
     * @param curr current node being checked
     * @param list list of data of the deepest branch
     * @return returns current node for pointer reinforcement
     */

    private void recDB(AVLNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        }
        if (curr.getHeight() == 0) {
            list.add(curr.getData());
        } else if (curr.getBalanceFactor() > 0) {
            list.add(curr.getData());
            recDB(curr.getLeft(), list);
        } else if (curr.getBalanceFactor() < 0) {
            list.add(curr.getData());
            recDB(curr.getRight(), list);
        } else {
            list.add(curr.getData());
            recDB(curr.getLeft(), list);
            recDB(curr.getRight(), list);
        }
        return;
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     * <p>
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     * <p>
     * Example Tree:
     * 10
     * /        \
     * 5          15
     * /   \      /    \
     * 2     7    13    20
     * / \   / \     \  / \
     * 1   4 6   8   14 17  25
     * /           \          \
     * 0             9         30
     * <p>
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     *              or if data1 > data2
     * @return a sorted list of data that is > data1 and < data2
     * @throws java.lang.IllegalArgumentException if data1 or data2 are null
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new java.lang.IllegalArgumentException("Bounds cannot be null");
        }
        if (data1.compareTo(data2) > 0) {
            throw new java.lang.IllegalArgumentException("Upper bound cannot be less than lower bound");
        }
        List<T> sortedList = new LinkedList<T>();
        recSIB(root, data1, data2, sortedList);
        return sortedList;
    }

    /**
     * Recursively checks in order for nodes that hold data between data1 and data2
     *
     * @param curr  current node being checked
     * @param data1 lower bound of data
     * @param data2 upper bound of data
     * @param list  list being added to if data is between data1 and data2
     */
    private void recSIB(AVLNode<T> curr, T data1, T data2, List<T> list) {
        if (curr == null) {
            return;
        }
        if (curr.getData().compareTo(data1) > 0 && curr.getData().compareTo(data2) < 0) {
            recSIB(curr.getLeft(), data1, data2, list);
            list.add(curr.getData());
            recSIB(curr.getRight(), data1, data2, list);
        } else if (curr.getData().compareTo(data1) <= 0) {
            recSIB(curr.getRight(), data1, data2, list);
        } else if (curr.getData().compareTo(data2) >= 0) {
            recSIB(curr.getLeft(), data1, data2, list);
        }
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     * <p>
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}