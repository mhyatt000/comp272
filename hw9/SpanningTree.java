import java.util.*;
import java.security.*;
import java.io.*;

public class SpanningTree{

    int n; // number of nodes
    int m; // number of edges
    int weight;

    int[] set;
    int[] height;

    PriorityQueue<WeightedEdge> q;
    ArrayList<WeightedEdge> edges;

    public SpanningTree(){

        // init set, height
        n = 50515;
        m=0;

        set = new int[n];
        height = new int[n];
        for(int i=0; i<n; i++){
            set[i] = i;
            height[i] = 1;
        }

        // init q
        ArrayList<String> e = load("artist_edges.txt");
        ArrayList<String> w = load("Weights.txt");

        ArrayList<WeightedEdge> temp = new ArrayList<>();
        edges = new ArrayList<>();

        int len = e.size();
        for(int i=0; i<len; i++){
            String[] uv = e.get(i).split("\t");
            temp.add(new WeightedEdge(
                Integer.parseInt(uv[0]),
                Integer.parseInt(uv[1]),
                Double.parseDouble(w.get(i))
            ));
        }

        q = new PriorityQueue<>(temp);

    }


    public ArrayList<String> load(String file){
        Scanner fileScanner;
        ArrayList<String> list = new ArrayList<String>();
        try {
            fileScanner = new Scanner (new File (file));

            while (fileScanner.hasNext()) {
                String word = fileScanner.nextLine();
                list.add(word);
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
        return list;
    }

    public int find(int x){

        // finds the set number of a node x
        int r = x;
        while(set[r] != r)
            r = set[r];

        // path compression
        int i = x;
        int j;
        while(i != r){
            j = set[i];
            set[i] = r;
            i = j;
        }

        return r;
    }

    public void merge(int a, int b){
        // connect the root of the smaller tree to the root of the larger tree
        // make the set of the smaller root equal to that of the larger
        // keep track of the height

        // merge the trees accordingly

        if (height[a] == height[b]){
            height[a] ++;
            set[b] = a;
        } else {
            // no height change
            if (height[a] > height[b])
                set[b] = a;
            else
                set[a] = b;

        }
    }


    public void build(){
        // builds minimum spanning tree

        for (WeightedEdge item: q){

            int u = find(item.u);
            int v = find(item.v);

            if( u != v ){
                merge(u,v);
                m ++;
                weight += item.weight;
                edges.add(item);
            } // else they are already the same component .. no cycles allowed

            if( m+1 == n)
                return;
        }

    }

    public static void main (String[] args){
        SpanningTree s = new SpanningTree();
        s.build();

        for( WeightedEdge item: s.edges )
            System.out.println(item);

        System.out.println("\nweight: " + s.weight);




    }
}

public class WeightedEdge implements Comparable<WeightedEdge>{
    int u;
    int v;
    double weight;

    public WeightedEdge() {
        u = 0;
        v = 0;
        weight=0;
    }

   public WeightedEdge (int i, int j, double w) {
       u = i;
       v = j;
       weight=w;
    }

    public double getWeight(){
       return weight;
    }

    public int compareTo(WeightedEdge other){
        return (Double.valueOf(weight).compareTo(Double.valueOf(other.weight)));
    }

    public String toString(){
       return ("("+u+","+v+") "+weight);
    }
}
