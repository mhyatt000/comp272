/*

    incomplete due to past due date ...

*/

import java.util.*;
import java.security.*;
import java.io.*;

public class MyHashSet {

    ArrayList<LinkedList<String>> table;

    public MyHashSet(int tableSize){
        table = new ArrayList<LinkedList<String>>();

        for (int i=0; i<tableSize; i++)
            table.add(null);
    }

    public ArrayList<String> run(String file){
        Scanner fileScanner;
        ArrayList<String> wordList = new ArrayList<String>();
        try {
            fileScanner = new Scanner (new File (file));

            while (fileScanner.hasNext()) {
                String word = fileScanner.nextLine();
                wordList.add(word);
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
        return wordList;
    }

    public int hashcode(String s){

    }

    public add(String s){
        int hash = hashcode(s)
    }
    public static void main(String[] args){
        MyHashSet hs = new MyHashSet(250154);

        ArrayList<String> words = hs.run("EnglishWordList.txt");
        // removing duplicates
        // 251885 - 250154 = 1731 duplicates
        HashSet<String> uwords = new HashSet(words);




    }
}




/*

linked list for collisions
    store collisions at any index

metrics
    (i) total number of collisions, and
    (ii) average size of the linked list

    (iii) time taken to hash all words
        System.currentTimeMillis()
            before and after



1. hashCode() for any string s, hashCode(s) = Math.abs(s.hashCode()) % tableSize

*/
