import java.util.*;
public class MaxHeap<E extends Comparable<E>> extends ArrayList<E>   {

    ArrayList<E> heap;

    public MaxHeap(){
        heap = new ArrayList<>();
    }

    // returns max value
    public E findMax() {
        return heap.get(0);
    }

    // adds a new value to the heap at the end of the Heap and
    // adjusts values up to the root to ensure Max heap property is satisfied.
    // parent of node at i is given by the formula (i-1)/2
    // throw appropriate exception
    public void addHeap(E val) {
        heap.add(val);

        int ichild = heap.size() - 1;
        int iparent = (ichild - 1)/2;

        E child = val;
        E parent = heap.get(iparent);

        // heapify
        while (1==1){
            iparent = (ichild - 1)/2;
            child = heap.get(ichild);
            parent = heap.get(iparent);

            if (parent.compareTo(val) < 0){
                heap.set(iparent, val);
                heap.set(ichild, parent);
            } else {
                return;
            }

            ichild = iparent;
            if (ichild == 0) {
                return;
            }

        }//while

    } // O(log n)

    //returns the max value at the root of the heap by swapping the last value
    // and percolating the value down from the root to preserve max heap property
    // children of node at i are given by the formula 2i+1,2i+2, to not exceed the
    // bounds of the Heap index, namely, 0 ... size()-1.
    // throw appropriate exception
    public E removeHeap() {

        // init
        E out = findMax();
        int ilast = heap.size()-1;
        heap.set( 0,heap.get(ilast) );
        heap.remove(ilast);
        ilast --;

        int iparent = 0;

        int depth = 1;
        while (ilast > 0){
            ilast = (ilast-1) / 2;
            depth ++;
        }

        for(int i = 0; i <= depth-1; i++){
            int ileft = 2*iparent + 1;
            int iright = 2*iparent + 2;

            if (heap.size()-1 < iright){
                return out;
            }

            E parent = heap.get(iparent);
            E left = heap.get(ileft);
            E right = heap.get(iright);



            if (left.compareTo(right) < 0){
                // left < right
                heap.set(iparent, right);
                heap.set(iright, parent);
                iparent = iright;
            } else if (left.compareTo(right) > 0){
                // left > right
                heap.set(iparent, left);
                heap.set(ileft, parent);
                iparent = ileft;
            }
        }

        return out;
    }

    public void show(){
        System.out.println(heap);
    }

    // takes a list of items E and builds the heap and then prints
    // decreasing values of E with calls to removeHeap().
    public void heapSort(List<E> list){
        MaxHeap<E> temp = new MaxHeap<>();
        temp.buildHeap(list);
        while (temp.heap.size() > 0)
            System.out.println(temp.removeHeap());
        return;
    }

    // merges the other maxheap with this maxheap to produce a new maxHeap.
    public void heapMerge(MaxHeap<E> other){
        ArrayList<E> temp = new ArrayList<>();
        temp.addAll(heap);
        temp.addAll(other.heap);
        heap.clear();
        buildHeap(temp);
    }

    //takes a list of items E and builds the heap by calls to addHeap(..)
    public void buildHeap(List<E> list) {
        for(E item: list){
            addHeap(item);
        }
    }

    public static void main(String[] args){
        MaxHeap<Integer> mh = new MaxHeap<>();

        mh.addHeap(1);
        mh.addHeap(2);
        mh.addHeap(4);
        mh.addHeap(8);
        mh.addHeap(1);
        mh.addHeap(3);

        mh.show();

        mh.heapSort(mh.heap);

        mh.show();

    }
}

// Answer the following questions as inlined Sakai submission along with the GitHub link.
// 1. What is the time-complexity of heapMerge(..) assuming the size of each heap is O(n).
// 2. What is the time-complexities of buildHeap(), addHeap(), and removeHeap()
