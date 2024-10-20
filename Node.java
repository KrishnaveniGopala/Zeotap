public class Node {
    public String type; // "operator" or "operand"
    public Node left;   // Left child
    public Node right;  // Right child
    public String value; // Value for operand nodes (e.g., "age > 30")

    public Node(String type, Node left, Node right, String value) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.value = value;
    }
}
