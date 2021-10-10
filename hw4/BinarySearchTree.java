public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> {
    //may ony contain comparable elements

    public BinarySearchTree() {
        super();
    }

    public BinarySearchTree(E val) {
        super(val);
    }

    // returns true if BST has val else false.
    public boolean contains (E val) {
        return contains(val, root);
    }
    private boolean contains(E val, Node<E> n){
        if(n == null)
            return false;

        int order = val.compareTo(n.getInfo());
        if(order < 0)
            return contains(val, n.getLeft());
        if(order > 0)
            return contains(val, n.getRight());
        return true;
    }

    // inserts val at the right place satisfying search tree properties, should handle if the tree is empty
    // if value is already there then don’t insert it again
    public void insert(E val) {
        root = insert(val, root);
        size ++;
    }
    private Node<E> insert(E val, Node<E> n){
        if(n == null)
            return new Node<E>(val);

        int order = val.compareTo(n.getInfo());

        if(order < 0){
            n.addLeft(insert(val, n.getLeft()));
        }
        else if(order > 0){
            n.addRight(insert(val, n.getRight()));
        }
        else
            size --;

        return n;
    }

    // returns the minimum value stored in the tree
    public E findMin() {
        if (root == null) return null;
        return findMin(root);
    }
    public E findMin(Node<E> n){
        if (n.getLeft() != null){
            return findMin(n.getLeft());
        }
        return n.getInfo();
    }

    // // returns the maximum value stored in the tree
    public E findMax() {
        if (root == null) return null;
        return findMax(root);
    }
    public E findMax(Node<E> n){
        if (n.getRight() != null){
            return findMax(n.getRight());
        }
        return n.getInfo();
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> bt= new BinarySearchTree<>();
        BinarySearchTree<Integer> a= new BinarySearchTree<>();

        bt.insert(5);
        bt.insert(10);
        bt.insert(3);
        bt.insert(20);
        bt.insert(2);
        bt.insert(35);

        System.out.println("min = " + bt.findMin());
        System.out.println("max = " + bt.findMax());
        System.out.println("size = " + bt.size);
        System.out.println();

        System.out.println("min = " + a.findMin());
        System.out.println("max = " + a.findMax());
        System.out.println("size = " + a.size);
        System.out.println();

    }


}
