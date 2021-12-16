import java.util.*;
import java.io.*;

public class AirportRoutes {

    ArrayList<Edge> routes;
    ArrayList<String> airports;
    int numAirports;
    int[] map;
    // add something for DAG

    public AirportRoutes (){

        routes = new ArrayList<>();
        airports = new ArrayList<>();
        numAirports = 0;
        map = new int[numAirports];

    }

    public void buildHubs(int appropriate_parameter){
    // parameter might be the directedGraph you built for the routes
    // this method builds the reducedGraph, I call it the Hubs.

    }

    public  void readAndStoreData(String airportsFile, String routesFile) {
    // read the two files and
    // store the data appropriately for building the directed graph

        // init
        routes = new ArrayList<>();
        airports = new ArrayList<>();
        numAirports = 0;
        Scanner fileScanner;

        // airports
        try {
            fileScanner = new Scanner (new File (airportsFile));
            while (fileScanner.hasNext()) {
                airports.add(new String(fileScanner.nextLine()));
            }
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
        System.out.println("Number of airports: " + airports.size());
        numAirports = airports.size();

        generateMap();

        try {
            fileScanner = new Scanner (new File (routesFile));
            while (fileScanner.hasNext()) {
                String word = fileScanner.nextLine();
                String[] temp = word.split(" ");
                int u = map[encode(temp[0])];
                int v = map[encode(temp[1])];
                routes.add( new Edge(u,v) );
            }
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
        System.out.println("Number of routes: " + routes.size());
        return;

    }

    // finds the minimum number of routes to be added from s for full connectivity
    public int minRoutes(String port) {

        // build reduced G
        // find which SCC port is
        // count number of SCC with edges=0 besides port

        DirectedGraph dg = new DirectedGraph(numAirports);
        for(Edge e: routes)
            dg.addEdge(e.u, e.v);

        DirectedGraph reduced = dg.reduce(true);

        int connections = 0;
        int num = 0;
        int p = dg.sccMap[map[encode(port)]];

        for(int i=0; i<reduced.dGraph.size(); i++){
            DirectedNodeList scc = reduced.dGraph.get(i);
            num = scc.inList.size();
                if (num == 0 && i != p)
                    connections ++;
            }

        System.out.println();
        System.out.println(port+" is part of SCC: "+p);

        return connections;
    }

    // returns the number of flight networks in the underlying undirected graph
    // essentially the number of weakly connected components
    public int numFlightNetworks(){

        // reusing code ... SCCs in an undirected graph are flight networks
        // could be a true SCC or just a CC

        DirectedGraph dg = new DirectedGraph(numAirports);
        for(Edge e: routes){
            dg.addEdge(e.u, e.v);
            dg.addEdge(e.v, e.u); // add backwards edge
        }

        DirectedGraph reduced = dg.reduce(false);

        int connections = 0;
        int num = dg.num;

        return dg.num;
    }

    public int encode(String str){
        // use ascii to encode strings with NO collisions
        // size 729,000 ... could be much smaller ... 17,576 but "-" and "7"
        char[] letters = str.toCharArray();
        int a = letters[0];
        int b = letters[1];
        int c = letters[2];
        int out = a*8100+b*90+c;

        return out;
    }

    public String decode(int in){
        //
        // currently cannot decode
        //
        String c = Character.toString(in%90);
        String b = Character.toString((in/90)%90);
        String a = Character.toString(in/8100);

        System.out.println(c);
        String out = a+b+c;

        return out;
    }

    // public String d2(int in){
    //     // takes an int airport and returns the name of the airport
    //     return map[in];
    // }

    public void generateMap(){

        map = new int[737191];
        for(int i=0; i<map.length; i++){
            map[i] = -1;
        }

        int i = 0;
        for(String port: airports){
            map[encode(port)] = i;
            i++;
        }



    }

    public static void main(String[] args) {
        // build directed graph
        // find SCCs
        // find weakly connected component
        // verify answers to the questions by calling the two methods above

        AirportRoutes ar = new AirportRoutes();
        ar.readAndStoreData("airports-IATA-codes.txt","routes.txt");
        int connections = ar.minRoutes("ORD");
        System.out.println("Min connections needed: "+connections);
        System.out.println("----------");
        int networks = ar.numFlightNetworks();
        System.out.println("Number of flight networks: "+networks);


    }
}
