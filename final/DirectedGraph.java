import java.util.*;
import java.io.*;

public class DirectedGraph  {

    public ArrayList<DirectedNodeList> dGraph;
    public int numVertex;
    public boolean [] marked;
    public int[] finishing;
    public int globali; // global incrementing variable
    public int[] sccMap;

    public int num;
    public int max;

    public DirectedGraph() {

        dGraph = new ArrayList<>();
        numVertex=0;
        finishing = new int[numVertex];
    }

    public DirectedGraph(int n) {

        numVertex =n;
        finishing = new int[numVertex];
        dGraph = new ArrayList<>(n);
        marked= new boolean[n];
        for (int i=0;i<numVertex;i++)
            dGraph.add(new DirectedNodeList());

    }

    public void addEdge(int u, int v) {
       // assume all vertices are created
       // directed edge u to v will cause outdegree of u to go up and indegree of v to go up.

        if (u>=0 && u<numVertex && v>=0 && v<numVertex) {
            if (u!=v) {
                if (!dGraph.get(u).outList.contains(v)) {
                    getNeighborList(u).addToOutList(v);
                    getNeighborList(v).addToInList(u);
                }
            }
        }
        else throw new IndexOutOfBoundsException();

    }

    public DirectedNodeList getNeighborList(int u) {

        return dGraph.get(u);

    }

    public void printAdjacency(int u) {

       DirectedNodeList dnl = getNeighborList(u);
       System.out.println ("vertices going into "+u+"  "+dnl.getInList());
       System.out.println ("vertices going out of "+u+"  "+dnl.getOutList());
       System.out.println();

    }

    public void postOrderDepthFirstTraversal() {

        for (int i=0;i<numVertex;i++)
            if (!marked[i]){
                postOrderDFT(i);
                System.out.println();}

    }
    public void postOrderDFT(int v){

        marked[v]=true;

        for (Integer u:dGraph.get(v).getOutList())
            if (!marked[u]) postOrderDFT(u);
                System.out.println(v);

    }

    public void RPostOrderDepthFirstTraversal() {
        globali = 0;

        for (int i=0;i<numVertex;i++)
            if (!marked[i])
                RPostOrderDFT (i);

    }
    public void RPostOrderDFT(int v){

        marked[v]=true;

        for (Integer u:dGraph.get(v).getInList()){
            if (!marked[u])
                RPostOrderDFT(u);
        }

        try {
            finishing[globali] = v;
            globali++;
        } catch(ArrayIndexOutOfBoundsException e){}

    }

    public DirectedGraph reduce(boolean verbose){

        RPostOrderDepthFirstTraversal();

        // init   finishing[k] = v
        // k is the finishing number of vertex v
        int[] vertexset = new int[numVertex];
        for(int i=0; i<numVertex; i++)
            vertexset[finishing[i]] = i;

        marked = new boolean[numVertex];
        max = 0;
        num = 0;

        // find SCCs
        for(int i=numVertex-1; i>-1; i--){

            ArrayList<Integer> set = dFT(finishing[i]);

            if (set.size() > max)
                max = set.size();
            if (set.size() > 0)
                num ++;

            for(Integer item: set)
                vertexset[item] = finishing[i];
        }

        if(verbose == true){
            System.out.println("number of SCC: " + num);
            System.out.println("max SCC size:  " + max);
        }

        // reduced graph init
        HashSet<Integer> leaders = new HashSet<>();
        for(int item: vertexset)
            leaders.add(item);

        ArrayList<Integer> leadersList = new ArrayList<>(leaders);

        int[] map = new int[numVertex];
        for(int i=0; i<numVertex; i++)
            map[i] = -1;

        for(int i=0; i<leadersList.size(); i++)
            map[leadersList.get(i)] = i;

        // map converts ints from leaders to the component number that they are
        // from [0, 82168] to [0, 10559]
        // 3n = O(n)

        // build
        DirectedGraph reduced = new DirectedGraph(num);

        for(int i=0; i<numVertex; i++){

            ArrayList<Integer> neighbor = getNeighborList(i).inList;

            for(Integer n: neighbor){
                int input = map[vertexset[n]];
                int output = map[vertexset[i]];

                reduced.addEdge( input, output );
            }
        }

        sccMap = new int[map.length];
        for(int i=0; i<map.length; i++){
            sccMap[i] = map[vertexset[i]];
        }

        reduced.sccMap = sccMap;

        int connects = 0; // number of connections between SCCs
        for(DirectedNodeList node: reduced.dGraph){
            connects += node.inList.size();
        }

        if(verbose == true){
        System.out.println();
        System.out.println("edges on reduced graph: " + connects);
        }

        return reduced;

    }

    public ArrayList<Integer> dFT(int v){

        ArrayList<Integer> temp = new ArrayList<>();
        if (marked[v])
            return temp;
        temp.add(v);
        marked[v]=true;

        for ( Integer u: dGraph.get(v).getOutList() )
            if (!marked[u])
                temp.addAll(dFT(u));
        return temp;
    }

    public static ArrayList<Edge> load(String file){

        Scanner fileScanner;
        ArrayList<Edge> list = new ArrayList<Edge>();
        try {
            fileScanner = new Scanner (new File (file));

            while (fileScanner.hasNext()) {
                String word = fileScanner.nextLine();

                try{
                    String[] temp = word.split("\t");
                    int u = Integer.parseInt(temp[0]);
                    int v = Integer.parseInt(temp[1]);

                    Edge e = new Edge(u,v);
                    list.add(e);

                } // try
                catch(NumberFormatException ex){
                    System.out.println("failed to load edge: \"" + word + "\"");
                }

            }
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
        return list;

    }


    public static void main(String[] args) {

        int n = 11;

        DirectedGraph dg = new DirectedGraph(n);
        String file = "";

        ArrayList<Edge> edges = new ArrayList<>();
        // = dg.load(file);

        edges.add(new Edge(1,0));
        edges.add(new Edge(0,2));
        edges.add(new Edge(2,1));

        edges.add(new Edge(2,3));
        edges.add(new Edge(3,4));

        edges.add(new Edge(4,5));
        edges.add(new Edge(5,6));
        edges.add(new Edge(6,4));

        edges.add(new Edge(7,8));
        edges.add(new Edge(8,9));
        edges.add(new Edge(9,7));

        edges.add(new Edge(10,0));
        // edges.add(new Edge(9,4));

        // edges.add(new Edge(4,6));

        edges.add(new Edge(2,3));
        edges.add(new Edge(2,3));
        edges.add(new Edge(2,3));


        for(Edge e: edges)
            dg.addEdge(e.u, e.v);


        System.out.println();
        System.out.println("Nodes: "+n);
        System.out.println("Edges: "+edges.size());
        System.out.println();

        dg.marked = new boolean[n];

        DirectedGraph reduced = dg.reduce(true);

        int count = 0;
        for(DirectedNodeList scc: reduced.dGraph)
            if (scc.inList.size() == 0)
                count ++;

        for(int i=0; i<reduced.dGraph.size(); i++){
            System.out.println("leader "+i+":");
            System.out.println(reduced.dGraph.get(i).inList.size());
        }

        System.out.println("count is: "+count);

        for(int i=0; i<11; i++){
            System.out.println("vertex "+i+" is reduced vertex "+reduced.sccMap[i]);
        }



    } // main

}
