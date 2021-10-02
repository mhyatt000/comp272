public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> {

     public BinarySearchTree() {
        
    }
    public BinarySearchTree(E val) {
        
    }
    
// returns true if BST has val else false.
    public boolean contains (E val) {
       
        
    }
// inserts val at the right place satisfying search tree properties, should handle if the tree is empty
// if value is already there then don’t insert it again
    public void insert(E val) {


         }

// returns the minimum value stored in the tree
public E findMin() {

}

// returns the maximum value stored in the tree
public E findMax() {
}
    
    public static void main(String[] args) {
        BinarySearchTree<Integer> bt= new BinarySearchTree<>();
        bt.insert(5);
        bt.insert(10);
        bt.insert(3);
        bt.insert(20);
        bt.insert(8);
        bt.insert(4);
    }
    
             
}