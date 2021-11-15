public class MyBigInteger{

    MyLinkedList<Integer> bigI;
    int size;

    public MyBigInteger () {

        bigI =new MyLinkedList<>();
        size=bigI.size;
    }

    public boolean isValid() {
        return (!bigI.isEmpty());
    }

    // takes a numerically valued String p and stores it one digit at a time in the linked list
    // example, MyBigInteger("383023322") will store the integer 383023322 in the linked list
    // one digit per node.

    public MyBigInteger(String p) {
        bigI = new MyLinkedList<>();
        if (isNumericString(p)) {
            String st =trim(p);
            for (int i=0;i<st.length();i++) {
                int c = Character.getNumericValue(st.charAt(i));
                bigI.addFirst(c);
            }
        }
        else throw new NumberFormatException();
        size=bigI.size;
    }

    // assumes p is neither null nor empty because it gets called from the constructor
    // so length of s is >=1
    // designed to eliminate all leading 0's
    //  if s has only 0's then it returns a string "0"
    public String trim(String p) {
        int i=0;
        while (i<p.length() && p.charAt(i)=='0')
            i++;
        String s= p.substring(i);
        if (s.length()==0) return "0";
        else return s;
    }

    public boolean isNumericString(String str) {
        if (str==null || str.length()==0) return false;
        return str.matches ("\\d+");
    }

    //add(..) adds this MyBigInteger to other MyBigInteger and returns the sum as a MyBigInteger
    // the original Big Integers don't change.
    public MyBigInteger add(MyBigInteger other) {

        MyBigInteger result = new MyBigInteger();
        MyLinkedList<Integer> listResult =result.bigI;
        int p=0;
        int c=0;
        int r=0;

        if (isValid()&& other.isValid()) {
            MyBigInteger big;
            MyBigInteger small;

            if (size>other.size){
                big=this;
                small=other;
            }
            else {
               big=other;
               small=this;
            }

            Node<Integer> f1=big.bigI.first;
            Node<Integer> f2=small.bigI.first;

            do {
                p =f1.getInfo()+f2.getInfo()+ c;
                c=p/10;
                r=p%10;
                listResult.addLast(r);
                f1=f1.getNext();
                f2=f2.getNext();
            }  while (f2!=null);

            if (f1!=null) {
                do {
                    p=f1.getInfo()+c;
                    c=p/10;
                    r=p%10;
                    listResult.addLast(r);
                    f1=f1.getNext();
                } while (f1!=null);
            }
            else if (c!=0)
                listResult.addLast(c);

        }
        else {
            System.out.println("invalid big integers ...");
            return null;
        }
        return result;
    }

    public String toString() {
        String str ="";
        if (isValid()) {
        Node<Integer> temp = bigI.last;
            do {
            str=str+temp.getInfo();
            temp = temp.getPrev();
            } while (temp!=null);
        } else {
                 System.out.println("big integer is null ...");
                 return null;
                }
        return str;
    }


    // returns true if and only if the two big integers are equal
    public boolean equals(Object other) {
        if (other instanceof MyBigInteger) {
            MyBigInteger o = (MyBigInteger) other;
            String s1=bigI.toString();
            String s2=o.bigI.toString();
            return (s1.equals(s2));

        }
        else return false;
    }
    // returns true if and only if this MyBigInteger is less than other MyBigInteger

    public boolean lessThan(MyBigInteger other) {
         if (other instanceof MyBigInteger) {
            MyBigInteger o = (MyBigInteger) other;
            String s1=bigI.toString();
            String s2=o.bigI.toString();
            return (s1.compareTo(s2)<0);

        }
        else return false;

    }

	public MyBigInteger multiply (MyBigInteger other) {
        // state the time complexity

        // other is empty or 0
        // O(1)
        if (!other.isValid() || !this.isValid())
            return new MyBigInteger("0");

        //O(n)
        if (other.toString() == "0" || this.toString() == "0")
            return new MyBigInteger("0");
        if (other.toString() == "1")
            return new MyBigInteger(this.toString());
        if(this.toString() == "1")
            return new MyBigInteger(other.toString());

        //other is one or two digits
        //O(n)

        int mult = Integer.parseInt(other.toString());

        MyBigInteger result = new MyBigInteger();
        MyLinkedList<Integer> listResult =result.bigI;
        int product = 0;
        int coef_hundred = 0;
        int coef_ten = 0;

        int remainder = 0;
        int remainder_ten = 0;
        int remainder_hundred = 0;


        Node<Integer> f1=this.bigI.first;

        if (f1!=null) {
            while (f1.getNext()!=null) {
                product = (f1.getInfo() * mult);

                remainder_hundred = product / 100;
                remainder_ten += (product%100) / 10;
                remainder += product%10;

                if (remainder % 10 != remainder){
                    remainder_ten += remainder/10;
                    remainder = remainder % 10;
                }

                listResult.addLast(remainder);

                remainder = remainder_ten;
                remainder_ten = remainder_hundred;
                remainder_hundred = 0;

                f1=f1.getNext();
            }
        }

        product = (f1.getInfo() * mult);

        remainder_hundred = product / 100;
        remainder_ten += (product%100) / 10;
        remainder += product%10;

        if (remainder % 10 != remainder){
            remainder_ten += remainder/10;
            remainder = remainder % 10;
        }

        listResult.addLast(remainder);
        if (remainder_hundred != 0 || remainder_ten != 0){
            listResult.addLast(remainder_ten);
            if (remainder_hundred != 0)
                listResult.addLast(remainder_hundred);
        }

        return result;

    }


	public boolean even(){

        //O(1) instead of get()
        int last = this.bigI.removeFirst();
        this.bigI.addFirst(last);

        if (last % 2 == 0)
            return true;
        return false;
	}

}
