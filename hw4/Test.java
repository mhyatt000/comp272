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

        System.out.println("min = " + a.findMin());
        System.out.println("max = " + a.findMax());
        System.out.println("size = " + a.size);
        System.out.println();

    }


}
public class BinaryTree<E> {

    int size;
    Node<E> root;

    public BinaryTree () {
     size =0;
     root=null;
    }


    public BinaryTree(E val) {
        root = new Node(val);
        // root node only
        size=1;

    }
    public boolean isEmpty() {
        return size==0;
    }


    public Node<E> addLeft(Node<E> node, E val) {
        Node<E> n = new Node(val);
        node.addLeft(n);
        size++;
        return n;
    }

    public Node<E> addRight(Node<E> node, E val) {

        Node<E> n = new Node(val);
        node.addRight(n);
        size++;
        return n ;
    }

    public void preOrder(Node<E> n) {

        if (n==null) return;
        System.out.println(n.getInfo());
        preOrder(n.getLeft());
        preOrder(n.getRight());

    }

    public void inOrder(Node<E> n) {

        if (n==null) return;
        inOrder(n.getLeft());
        System.out.println(n.getInfo());
        inOrder(n.getRight());

    }

    public void postOrder(Node<E> n) {

        if (n==null) return;

        postOrder(n.getLeft());
        postOrder(n.getRight());
        System.out.println(n.getInfo());

    }


    public static void main(String[] args){

        // BinaryTree<String> bt = new BinaryTree<>("cat");
        // bt.addLeft(bt.root,"dog");
        // bt.addRight(bt.root,"mouse");
       BinaryTree<String> bt = new BinaryTree<>("-");
       Node<String> n1 =bt.addLeft(bt.root,"*");
       Node<String> n2 = bt.addRight(bt.root,"/");

       bt.addLeft(n1,"x");
       bt.addRight(n1,"y");
       bt.addLeft(n2,"y");
       bt.addRight(n2,"z");
       bt.postOrder(bt.root);
    }

}
public class Node <E>{

    Node<E> left;
    Node<E> right;
    Node<E> parent;
    E  info;

    public Node(Node<E> le, Node<E> ri, Node<E> pa){
        left=le;
        right=ri;
        parent=pa;
    }

    public Node(E val){
        left=null;
        right=null;
        parent=null;
        info=val;
    }

    public Node(){
        left=null;
        right=null;
        parent=null;
        info=null;
    }

    public void addLeft(Node<E> le) {
       left=le;
       le.addParent(this);
    }

    public void addRight(Node<E> ri) {
      right=ri;
      ri.addParent(this);
    }

    public void addParent(Node<E> pa){
     parent=pa;
    }

    public void setInfo(E val){
        info=val;
    }

    public E getInfo() {
       return info;
    }

    public Node<E> getLeft(){
        return left;
    }
    public Node<E> getRight(){
        return right;
    }
}
