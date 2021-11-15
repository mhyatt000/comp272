import java.util.*;
import java.lang.*;

public class BinaryTreeVerification <E extends Comparable<E>> extends BinaryTree<E> {

    public BinaryTreeVerification(){
        super();
    }

    public BinaryTreeVerification(E val){
        super(val);
    }


    public ArrayList<Node<E>> getBFT(){
        //helper method ... returns an ArrayList in BFT order
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

    public boolean isBST(){
        ArrayList<Node<E>> bft = getBFT();
        Node<E> left;
        Node<E> right;

        for (Node<E> item: bft){
            left = item.getLeft();
            right = item.getRight();

            if(left != null && left.getInfo().compareTo(item.getInfo()) > 0)
                return false;
            if(right != null && right.getInfo().compareTo(item.getInfo()) < 0)
                return false;
        }
        return true;

    }

    public boolean isFull() {

        int height = height();
        if (height == 0)
            return true;

        int sum = 1;
        int temp = 1;

        while (height > 0){
            for (int i=0; i < height; i++){
                temp *= 2;
            }
            sum += temp;
            temp = 1;
            height --;
        }
        if (sum == size)
            return true;
        return false;
    }

    public boolean isComplete() {
        boolean leaves = false;
        ArrayList<Node<E>> bft = getBFT();
        for (Node<E> item: bft){
            if (leaves && !isLeaf(item))
                return false;
            if (isLeaf(item) || item.getRight() == null)
                leaves = true;
            if (item.getLeft() == null && item.getRight() != null)
                return false;

        }
        return true;
    }

    public static void main(String[] args){
        BinaryTreeVerification<Integer> bt = new BinaryTreeVerification<Integer>(5);
        Node<Integer> i1 = bt.addLeft(bt.root,4);
        Node<Integer> i2 = bt.addRight(bt.root,6);

        Node<Integer> i3 = bt.addLeft(bt.root.getLeft(),3);
        Node<Integer> i4 = bt.addRight(i1,4);
        Node<Integer> i5 = bt.addLeft(i2,6);
        Node<Integer> i6 = bt.addRight(i2,0);
        // bt.breadthFirstTraversal();
        // System.out.println(bt.isBST());
        System.out.println(bt.isComplete());
    }

}
