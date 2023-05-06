public class KDtree {
    int k = 2;

    public Node newNode(int[] arr) {
        return new Node(arr);
    }

    public Node insertRec(Node root, int[] point, int depth) {
        if (root == null) {
            return newNode(point);
        }

        int cd = depth % k;
        if (point[cd] < root.point[cd]) {
            root.left
                    = insertRec(root.left, point, depth + 1);
        } else {
            root.right
                    = insertRec(root.right, point, depth + 1);
        }

        return root;
    }

    public Node insert(Node root, int[] point) {
        return insertRec(root, point, 0);
    }

    public boolean arePointsSame(int[] point1, int[] point2) {
        for (int i = 0; i < k; ++i) {
            if (point1[i] != point2[i]) {
                return false;
            }
        }
        return true;
    }

    public int searchRec(Node root, int[] point,
                             int depth) {
        if (root == null) {
            return depth;
        }
        if (arePointsSame(root.point, point)) {
            return depth;
        }

        int cd = depth % k;
        if (point[cd] < root.point[cd]) {
            return searchRec(root.left, point, depth + 1);
        }
        return searchRec(root.right, point, depth + 1);
    }

    public int search(Node root, int[] point) {
        return searchRec(root, point, 0);
    }
}