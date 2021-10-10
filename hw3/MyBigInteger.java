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
