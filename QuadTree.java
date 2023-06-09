public class QuadTree<Key extends Comparable<Key>> {
    private Node root;


    private class Node {
        Key x, y;              // x- and y- coordinates
        Node NW, NE, SE, SW;   // four subtrees

        Node(Key x, Key y) {
            this.x = x;
            this.y = y;
        }
    }

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

    public int search(Key x, Key y) {
        return search(root, x, y, 0);
    }

    private int search(Node node, Key x, Key y, int depth) {
        if (node == null) {
            return depth;
        } else if (eq(x, node.x) && eq(y, node.y)) {
            return depth;
        } else if (less(x, node.x) && less(y, node.y)) {
            return search(node.SW, x, y, depth + 1);
        } else if (less(x, node.x) && !less(y, node.y)) {
            return search(node.NW, x, y, depth + 1);
        } else if (!less(x, node.x) && less(y, node.y)) {
            return search(node.SE, x, y, depth + 1);
        } else { // (!less(x, node.x) && !less(y, node.y))
            return search(node.NE, x, y, depth + 1);
        }
    }

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
