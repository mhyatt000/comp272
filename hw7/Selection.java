import java.util.*;

public class Selection <E extends Comparable<E>> {
    int k;
    ArrayList<E> input ;
    // this holds the values of type E from which your code will find kth largest.

    // constructors
    public Selection(int k, ArrayList<E> input){
        this.k = k;
        this.input = input;
    }
    public Selection(int k, E... input){
        this.k = k;
        this.input = new ArrayList<E>(Arrays.asList(input));
    }

    public void print(Collection<E> c){
        for(E item: c){
            System.out.print(item);
            System.out.print(" ");
        }
        System.out.println(" ");
    }
    // algorithm 1 methods -- implement 1B
    public E kLargest1B(){
        MaxHeap<E> h = new MaxHeap<>();
        ArrayList<E> list = new ArrayList<>();
        list.addAll(input);
        ArrayList<E> largest = new ArrayList<>();

        for(int i=0; i<k; i++){
            h.addHeap(list.remove( list.size()-1 ));
        }

        largest = h.heapSort();

        for(int i=0; i<list.size(); i++){
            for(int j=0; j<k; j++){
                E a = list.get(i);
                E b = largest.get(j);

                if(a.compareTo(b) > 0){
                    largest.add(j,a);
                    break;
                }
            }
        }

        return largest.get(k-1);

        /*

            `k*log(k) + k*(n-k)`
            worst O(n log n)
            best O((n-k)k log k)

        */
    }

    // algorithm 2 methods -- 6A --
    //change the algorithm to do kth largest not kth smallest that is
    // described here

    public E kLargest6A(){
        /*
        3. build heap, deheapify k times
            `n + k*log(n)`
            worst O(n log n)
            best O(n)
        */
        MaxHeap<E> h = new MaxHeap<>();
        h.buildHeap(input);
        for(int i=0; i<k-1; i++){
            h.removeHeap();
        }
        return h.removeHeap();
    }

    // algorithm 3 methods – 6B

    public E kLargest6B(){
        /*
        4. build a min heap of k random values compare to the root
            `k + (n-k)*log(k)`
            worst O(n log n)
            best O(n)
        */

        PriorityQueue<E> q = new PriorityQueue<>();
        ArrayList<E> list = new ArrayList<>();
        list.addAll(input);
        ArrayList<E> largest = new ArrayList<>();

        for(int i=0; i<k; i++){
            q.add( list.remove( list.size()-1 ));
        }

        for(E item: list){
            E a = q.poll();

            if (item.compareTo(a) > 0)
                a = item;

            q.add(a);
        }

        return q.poll();

    }

    public static void main(String[] args){
        Selection<Integer> s = new Selection<>(4, new Integer[]{-4,90,110,2,2,51,3,4,5,27,6,7,8});

        System.out.println();

        s.print(s.input);
        System.out.println(s.kLargest1B());
        s.print(s.input);
        System.out.println(s.kLargest6A());
        s.print(s.input);
        System.out.println(s.kLargest6B());

        Random rand = new Random();
        ArrayList<Integer> nums = new ArrayList<>();
        for(int i=0; i<1000000; i++)
            nums.add(rand.nextInt());
        System.out.println(nums.size());
        Selection<Integer> s2 = new Selection(100000,nums);

        long start;
        long finish;
        long timeElapsed;

        start = System.currentTimeMillis();
        s2.kLargest6B();
        finish = System.currentTimeMillis();
        timeElapsed = finish - start;
        System.out.println(timeElapsed);

        start = System.currentTimeMillis();
        s2.kLargest6A();
        finish = System.currentTimeMillis();
        timeElapsed = finish - start;
        System.out.println(timeElapsed);

        start = System.currentTimeMillis();
        s2.kLargest1B();
        finish = System.currentTimeMillis();
        timeElapsed = finish - start;
        System.out.println(timeElapsed);

    }
}






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
    public ArrayList<E> heapSort(){
        MaxHeap<E> temp = new MaxHeap<>();
        temp.heap = this.heap;
        ArrayList<E> out = new ArrayList<>();
        while (temp.heap.size() > 0)
            out.add(temp.removeHeap());
        return out;
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

}
