import java.util.*;
import java.lang.*;

public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> {
     static int k=0;
     static boolean found=false;

     public BinarySearchTree() {
        super();
    }
    public BinarySearchTree(E val) {
        super(val);
    }

    public void contains (E val, Node<E> n) {
        if (found) return;
        if (k>size ) {
            found = false;
        }
        if (n!=null) {
            k++;
            if (n.getInfo().equals(val)) {
                found=true;
            }
            else {
                  contains(val, n.getLeft());
                  contains(val, n.getRight());
                }
            }//if n is null
            else
                return;
    }


    public boolean contains (E val) {
        k=0;
        found=false;
        if (!isEmpty()) {
            contains(val,root);
            return found;
        }
      return false;

    }
    public void insert(E val) {
      if (isEmpty()) {
       root = new Node<E>(val);
       size++;
    }
    else {
           Node<E> temp=root;
           Node<E> current=null;
           int c=0;
           while (temp!=null) {
               current=temp;
               c= temp.getInfo().compareTo(val);
               if (c<0) temp=temp.getRight();
               else if (c>0) temp=temp.getLeft();
               else if (c==0) {
                   System.out.println("duplicate found ..");
                   break;
               }
            }
            if (c<0) addRight(current,val);
            else if (c>0) addLeft(current,val);

           }
    }

    public E findMin(){
      if (!isEmpty()) {
          Node<E> p = root;
          while (p.getLeft()!=null)
          p=p.getLeft();
        return p.getInfo();
      }
      else throw new NoSuchElementException();
    }

    public E findMax(){
      if (!isEmpty()) {
          Node<E> p = root;
          while (p.getRight()!=null)
          p=p.getRight();
        return p.getInfo();
      }
      else throw new NoSuchElementException();
    }

    public MaxHeap<E> buildHeapFromBST(){
        MaxHeap<E> heap = new MaxHeap<E>();
        ArrayList<Node<E>> bft = getBFT();
        ArrayList<E> values = new ArrayList<>();
        for (Node<E> n: bft)
            values.add(n.getInfo());
        heap.buildHeap(values);
        return heap;
	}

    public ArrayList<Node<E>> getBFT(){
        //helper method ... returns an ArrayList in BFT order

        // I didnt know how you would test our code, so I included this method
        // in all BT subclasses just in case
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

    public static void main(String[] args) {
        BinarySearchTree<Integer> bt= new BinarySearchTree<>();
        bt.insert(5);
        bt.insert(10);
        bt.insert(3);
        bt.insert(20);
        bt.insert(8);
        bt.insert(4);
        bt.insert(17);
        bt.insert(2);
        bt.insert(8);
        // System.out.println(bt.contains(2));
       // System.out.println(bt.findMin());
        //System.out.println(bt.findMax());

        MaxHeap<Integer> heap = bt.buildHeapFromBST();
        for (Integer item: heap)
            System.out.println(item);


    }


}
