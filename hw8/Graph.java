import java.util.*;
import java.security.*;
import java.io.*;
// import java.lang.math.*;


public class Graph
{
    int numVertex;
    int numEdge;
    ArrayList<ArrayList<Integer>> graph;


    public Graph () {
        numVertex =0;
        numEdge =0;
        graph = new ArrayList<>();

    }

    public Graph(int vertexCount) {

        numVertex=vertexCount;
        numEdge=0;
        graph = new ArrayList<>(numVertex);
        for (int i=0;i<numVertex;i++)
        graph.add(new ArrayList<>());
    }

    public ArrayList<Integer> getNeighbors(int u) {
        //returns the list of points that are directly connected to (u)
        return graph.get(u);
    }

    public boolean edgePresent(int u, int v) {
        // if v is a neighbor of u then an edge is present
        return (graph.get(u).contains(v));

    }

    public void addEdge(int u, int v) {
        // lets assume that the vertices are already created.
        if (u>=0 && u<numVertex && v>=0 && v<numVertex) {
            if (!edgePresent(u,v))
                graph.get(u).add(v);

            if (!edgePresent(v,u))
                graph.get(v).add(u);
            numEdge++;
        }
        else throw new IndexOutOfBoundsException();
    }

    public ArrayList<Edge> loadEdges(String file){
        Scanner fileScanner;
        ArrayList<Edge> edgelist = new ArrayList<Edge>();
        try {
            fileScanner = new Scanner (new File (file));

            while (fileScanner.hasNext()) {
                String word = fileScanner.nextLine();
                String[] splitwords = word.split("\t");

                int u = Integer.parseInt(splitwords[0]);
                int v = Integer.parseInt(splitwords[1]);

                Edge edge = new Edge(u,v);

                edgelist.add(edge);
            }
        }
        catch (IOException e) {
            System.out.println(e);
        }
        return edgelist;
    }

    /*

        BFT




    */


    public void BFT(){
        System.out.println("BFT...");

        /*
            answers questions 1,2,3
            question 1 is required
        */

        // init
        int[] vertexset = new int[36692];
        int[] dimensions;
        for(int i=0; i<vertexset.length; i++)
            vertexset[i] = -1;

        // BFT
        ArrayList<Component> c = new ArrayList<Component>();
        for(int i=0; i<vertexset.length; i++){
            if(vertexset[i] == -1){

                dimensions = BFT(i, vertexset); // [vertex,edge]
                c.add(new Component(i, dimensions[0], dimensions[1]));
            }
        }

        // print statements

        int count_trees = 0;
        int largest = 0;
        for(Component item: c){
            if (item.vertices > largest)
                largest = item.vertices;
            if (item.edges < item.vertices)
                count_trees ++;
        }

        System.out.println(c.size() + " components");
        System.out.println(count_trees + " trees");
        System.out.println(largest + " largest tree");
    }

    public int[] BFT(int label, int[] vertexset){
        HashSet<Integer> neighbors = new HashSet<>();
        HashSet<Integer> temp = new HashSet<>();
        int a;
        int b;

        neighbors.add(label);

        while (1==1){
            a = neighbors.size();
            for(Integer item: neighbors){
                temp.addAll(getNeighbors(item));
            }
            neighbors.addAll(temp);
            b = neighbors.size();

            if(a==b){
                break;
            }
        }

        int edges = 0;
        for(Integer item: neighbors){
            vertexset[item] = label;
            edges += getNeighbors(item).size();
        }

        // edges /= 2;

        return new int[] {neighbors.size(),edges};

    }




    public void DFT(){
        System.out.println("DFT...");
        int[] vertexset = new int[36692];
        int num_components=0;

        for(int i=0; i<vertexset.length; i++)
            vertexset[i] = -1;

        ArrayList<Component> c = new ArrayList<Component>();

        for(int i=0; i<vertexset.length; i++){
            if(vertexset[i] == -1){

                DFT(i, i, vertexset);
                num_components ++;
            }
        }

        System.out.println(num_components+" components");
    }

    public void DFT(int label, int node, int[] vertexset){
        vertexset[node] = label;

        ArrayList<Integer> neighbors = getNeighbors(node);

        for(int item: neighbors){
            if(vertexset[item] == -1){
                DFT(label, item, vertexset);
            }
        }
    }

    public void setUnion(ArrayList<Edge> edges){
        System.out.println("setUnion...");
        int[] vertexset = new int[36692];

        for(int i=0; i<36692; i++){
            vertexset[i] = i;
        }

        int a;
        int b;
        int alabel;
        int blabel;

        for(Edge edge: edges){
            a = Math.min(edge.u, edge.v);
            b = Math.max(edge.u, edge.v);

            alabel = vertexset[a];
            blabel = vertexset[b];

            if(alabel != blabel){
                for(int i=0; i<36692; i++){
                    if (vertexset[i] == blabel) vertexset[i] = alabel;
                }
            }

        }

        HashSet<Integer> componentlabels = new HashSet<>();
        for(int i=0; i<36692; i++)
            componentlabels.add(vertexset[i]);
        System.out.println(componentlabels.size()+" components");

    }



    public static void main(String[] args){
        Graph g = new Graph(36692);
        ArrayList<Edge> load = g.loadEdges("Email-Enron.txt");

        for(Edge e: load){
            g.addEdge(e.u, e.v);
        }

        g.BFT();
        g.DFT();
        g.setUnion(load);

    }
}

public class Edge{
    int u;
    int v;

    public Edge(int u, int v){
        this.u = u;
        this.v = v;
    }

    public String toString(){
        return ("[" + u + ", " + v + "] ");
    }

}

public class Component{

    int label;
    int vertices;
    int edges;

    public Component(int label, int vertices, int edges){
        this.label = label;
        this.vertices = vertices;
        this.edges = edges;
    }

    public String toString(){
        return (label + ": " + vertices + "/" + edges);
    }
}
