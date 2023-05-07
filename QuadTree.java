public class QuadTree<Key extends Comparable<Key>> {
    private Node root;

    // helper node data type
    private class Node {
        Key x, y;              // x- and y- coordinates
        Node NW, NE, SE, SW;   // four subtrees

        Node(Key x, Key y) {
            this.x = x;
            this.y = y;
        }
    }

    /***********************************************************************
     *  Insert (x, y) into appropriate quadrant
     ***************************************************************************/
    public void insert(Key x, Key y) {
        root = insert(root, x, y);
    }

    private Node insert(Node h, Key x, Key y) {
        if (h == null) return new Node(x, y);
        else if (less(x, h.x) && less(y, h.y)) h.SW = insert(h.SW, x, y);
        else if (less(x, h.x) && !less(y, h.y)) h.NW = insert(h.NW, x, y);
        else if (!less(x, h.x) && less(y, h.y)) h.SE = insert(h.SE, x, y);
        else if (!less(x, h.x) && !less(y, h.y)) h.NE = insert(h.NE, x, y);
        return h;
    }

    /***********************************************************************
     *  Search for the node that matches (x, y) coordinate and return its depth
     *  Return the depth of the tree if the node is not found.
     ***************************************************************************/
    public int search(Key x, Key y) {
        Node result = search(root, x, y);
        return (result != null) ? depth(result) : depth(root);
    }

    private Node search(Node node, Key x, Key y) {
        if (node == null) {
            return null;
        } else if (eq(x, node.x) && eq(y, node.y)) {
            return node;
        } else if (less(x, node.x) && less(y, node.y)) {
            return search(node.SW, x, y);
        } else if (less(x, node.x) && !less(y, node.y)) {
            return search(node.NW, x, y);
        } else if (!less(x, node.x) && less(y, node.y)) {
            return search(node.SE, x, y);
        } else { // (!less(x, node.x) && !less(y, node.y))
            return search(node.NE, x, y);
        }
    }

    /***********************************************************************
     *  Helper functions
     ***************************************************************************/
    private boolean less(Key k1, Key k2) {
        return k1.compareTo(k2) < 0;
    }

    private boolean eq(Key k1, Key k2) {
        return k1.compareTo(k2) == 0;
    }

    private int depth(Node node) {
        if (node == null) {
            return -1;
        } else {
            return 1 + Math.max(Math.max(depth(node.NW), depth(node.NE)), Math.max(depth(node.SW), depth(node.SE)));
        }
    }
}
