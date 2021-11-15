import java.util.*;
import java.lang.*;

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

	public int height(){
        int left;
        int right;

        if (root.getLeft() == null)
            left = 0;
        else
            left = 1+height(root.getLeft());

         if (root.getRight() == null)
            right = 0;
        else
            right = 1+height(root.getRight());

        int max = left;
        if (right > max)
            max = right;

        return max;
	}

    public int height(Node<E> n){
        // helper method for height
        int left;
        int right;

        if (n.getLeft() == null){
            left = 0;
        }
        else{
            left = 1+height(n.getLeft());
        }
        if (n.getRight() == null){
            right = 0;
        }
        else{
            right = 1+height(n.getRight());
        }

        int max = left;
        if (right > max)
            max = right;

        return max;
    }

	public boolean isLeaf(Node<E> n){
        if (n == null)
            throw new NullPointerException("node is null");
        if (n.getLeft() == null && n.getRight() == null)
            return true;
        return false;
	}

	public int nodeLevel (Node<E> v) {
        if (v == null)
            throw new NullPointerException("node is null");
        return this.height() - this.height(v);
	}

	public void breadthFirstTraversal(){
        if (size == 0)
            throw new IllegalStateException("empty tree");
        ArrayList<Node<E>> bft = getBFT();
        for (Node<E> item: bft)
            System.out.println(item.getInfo());
	}
    public ArrayList<Node<E>> getBFT(){
        LinkedList<Node<E>> q = new LinkedList<Node<E>>();
        LinkedList<Node<E>> temp = new LinkedList<Node<E>>();
        ArrayList<Node<E>> result = new ArrayList<Node<E>>();

        q.add(root);
        while (1==1){
            temp.add(q.pop());
            if(q.isEmpty()){
                while(!temp.isEmpty()){
                    Node<E> item = temp.pop();
                    result.add(item);

                    if (item.getLeft() != null)
                        q.add(item.getLeft());
                    if (item.getRight() != null)
                        q.add(item.getRight());
                }
            }
            if (result.size() == size)
                return result;
        }
    }

	public int nodeHeight(Node<E> v){
        return height(v);
	}

	public double averageLevel(){
        double avg = 0;

        ArrayList<Node<E>> bft = getBFT();
        for (Node<E> item: bft)
            avg += nodeHeight(item);
        avg /= size;
        return avg;
	}



    public static void main(String[] args){

       // BinaryTree<String> bt = new BinaryTree<>("cat");
      // bt.addLeft(bt.root,"dog");
       // bt.addRight(bt.root,"mouse");
       BinaryTree<String> bt = new BinaryTree<>("0");
       Node<String> n1 = bt.addLeft(bt.root,"1");
       Node<String> n2 = bt.addRight(bt.root,"2");
       bt.addLeft(n1,"3");
       bt.addRight(n1,"4");
       bt.addLeft(n2,"5");
       bt.addRight(n2,"6");

       bt.breadthFirstTraversal();
    }

}
