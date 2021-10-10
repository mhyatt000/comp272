import java.util.*;

public class MyBigInteger{

    MyLinkedList<Integer> bigI;

    public MyBigInteger () {
        bigI = new MyLinkedList<>();
    }

    // takes a numerically valued String p and stores it one digit at a time in the linked list
    // example, MyBigInteger("383023322") will store the integer 383023322 in the linked list
    // one digit per node.
    public MyBigInteger(String p) {
        bigI = new MyLinkedList<>();

        int i=0;
        while(i < p.length()){

            int num = Character.getNumericValue(p.charAt(i));
            bigI.addLast(num);
            i++;
        }

    }

    //add(..) adds this MyBigInteger to other MyBigInteger and returns the sum as a MyBigInteger
    // the original Big Integers don't change.
    public MyBigInteger add(MyBigInteger other) {
        MyBigInteger clist = new MyBigInteger();

        Node<Integer> a = other.bigI.last;
        Node<Integer> b = this.bigI.last;

        int c = 0;
        int d = 0;

        while (a != null && b != null){

            //prep
            if (a != null)
                c += a.getInfo();
            if (b != null)
                c += b.getInfo();
            if (c>=10){
                c -= 10;
                d ++;
            }
            clist.bigI.addFirst(c);

            //reset
            if (a != null)
                a = a.getPrev();
            if (b != null)
                b = b.getPrev();
            c = 0;
            if(d != 0){
                d = 0;
                c ++;
            }
        }

        if (c != 0)
            clist.bigI.addFirst(c);

        return clist;
    }

    // returns true if and only if the two big integers are equal
    public boolean equals(Object other) {
        if((other == null) || (getClass() != other.getClass()))
            return false;

        return this.bigI.equals(((MyBigInteger)other).bigI);
    }

    // returns true if and only if this MyBigInteger is less than other MyBigInteger
    public boolean lessThan(MyBigInteger other) {
        // true if this less than other
        if (this.bigI.size != other.bigI.size)
            return ((this.bigI.size < other.bigI.size) == true);

        Node<Integer> a = this.bigI.first;
        Node<Integer> b = other.bigI.first;

        while (a != null && b != null){

            if (a.getInfo() < b.getInfo())
                return true;
            if (a.getInfo() > b.getInfo())
                return false;

            a = a.getNext();
            b = b.getNext();
        }
        return false; //equal is not less than
    }

}





public class MyLinkedList<E>{
    Node<E> first;
    Node<E> last;
    int size;

    public MyLinkedList()
    {
       first = null;
       last = null;
       size=0;
    }

    public boolean isEmpty() {
        return (size==0);
    }

    public void addFirst(E info) {

        Node<E> n =new Node<>();
        n.setInfo(info);

        if (isEmpty())
            last=n;
        else {
            n.setNext(first);
            first.setPrev(n);
        }

        first=n;
        size++;

    }

    public E removeFirst() {

        if (!isEmpty()) {
            E val = first.getInfo();

            if (size>1) {
                first.getNext().setPrev(null);
                first=first.getNext();
                size--;
            }

            else if (size==1) {
                 first=null;
                 last=null;
                 size--;
            }

            return val;
        }

        else
            throw new NoSuchElementException();
    }

    public E removeLast() {

        if (!isEmpty()) {
            E val = last.getInfo();

            if (size>1) {
                last.getPrev().setNext(null);
                last=last.getPrev();
                size--;
            }

            else if (size==1) {
                first=null;
                last=null;
                size--;
            }

            return val;
        }

        else
            throw new NoSuchElementException();
    }

    public E remove(int k) {

        if (!isEmpty()) {
            Node<E> temp = first;

            if ((k>=0) && (k<size)) {
                if (k==0)
                    return removeFirst();
                else if (k==size-1)
                    return removeLast();
                else {
                    for (int i=0;i<k;i++)
                        temp = temp.getNext();
                    E val=temp.getInfo();
                    temp.getPrev().setNext(temp.getNext());
                    temp.getNext().setPrev(temp.getPrev());
                    size--;
                    return val;
                }
            }
            else throw new IndexOutOfBoundsException();
        }
        else {
            System.out.println("list empty ..");
            throw new NoSuchElementException();
        }
    }






    // adds an item to the end of the list with info field set to val
    public void addLast(E val) {
        Node<E> n = new Node<>();
        n.setInfo(val);

        if (isEmpty()){
            first = n;
        }
        else {
            n.setPrev(last);
            last.setNext(n);
        }

        last = n;
        size ++;
    }

    // prints all items in the list from first to last taking care of situations when the list is empty
    // use exception handling
    public void printListForward() {
        if (isEmpty()){
            throw new NoSuchElementException();
        } else {
            Node<E> item = first;
            for (int i=0; i<size; i++){
                System.out.println(item.getInfo());
                item = item.getNext();
            }
        }
    }

    // prints all items in the list from last to first taking care of situations when the list is em
    // use exception handling
    public void printListBackward() {
        if (isEmpty()){
            throw new NoSuchElementException();
        } else {
            Node<E> item = last;
            for (int i=0; i<size; i++){
                System.out.println(item.getInfo());
                item = item.getPrev();
            }
        }
    }

    //returns true if and only if the list contains at least one element e such that
    //Objects.equals(o,e)
    //return false if the list is empty
    public boolean contains(Node<E> o) {
        System.out.println("contains method");
        if(isEmpty())
            return false;
        Node<E> item = first;
        for (int i=0; i<size; i++){
            if(item.equals(o))
                return true;
            item = item.getNext();
        }
        return false;
    }

    // brings the current list back to an empty list
    public void clear() {
        first = null;
        last = null;
    }

    // returns the info value stored at node i
    // throw IndexOutOfBounds exception of i is out of bounds or the list is empty
    public E get(int k) {
        if (isEmpty())
            throw new NoSuchElementException();

        if ((k>=0) && (k<size)) {
            Node<E> temp = first;
            for (int i=0;i<k;i++)
                temp = temp.getNext();
            E val=temp.getInfo();
            return val;

        }
        else throw new IndexOutOfBoundsException();

    }

    // compares this MyLinkedList with the parameter otherList
    // and returns true if the linkedlists have identical values from beginning to end
    // same size and this.get(i).equals(otherList.get(i)) should be true for all i
    // lists can be empty in which case return true
    //should run in O(n) time where n is the size of each list.
    public boolean equals(MyLinkedList other) {
        // the parameter used to be of the Object class
        // I assumed you wanted it to take a parameter of MyLinkedList
        // cannot ambiguously compare members of List interface or MyLinkedList

        if (this.size != other.size){
            return false;
        }
        Node<E> a = this.first;
        Node<E> b = other.first;

        for (int i=0; i<this.size; i++){
            if (a.equals(b) == false)
                return false;
            a = a.getNext();
            b = b.getNext();
        }
        return true;
    }

}


public class Node<E>{
    E info;
    Node<E> prev;
    Node<E> next;

    public Node()
    {
       info=null;
       prev=null;
       next=null;
    }

    public void setNext(Node<E> n){
       next = n;
    }

    public void setPrev(Node<E> p){
        prev = p;
    }

    public void setInfo(E val) {
        info = val;
    }

    public E getInfo() {

        return info;
    }

    public Node<E> getNext() {
        return next;
    }
      public Node<E> getPrev() {
        return prev;
    }

    public boolean equals(Node<E> other){
        if (this.getInfo().equals(other.getInfo())){
            return true;
        }
        return false;
    }

}
