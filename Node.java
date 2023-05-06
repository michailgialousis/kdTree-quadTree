public class Node {
    int[] point;
    Node left, right;

    public Node(int[] arr)
    {
        this.point = arr;
        this.left = this.right = null;
    }
}
