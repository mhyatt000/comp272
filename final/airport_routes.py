import sys
from pprint import pprint

def read():
    """
    reads the take home final files (2)

    return
    ----------
        list(airporst): digraph nodes
        list(routes): digraph edges
        list(map): for encoding airport strings to node ints
            where map[encode(port)] = node
    """

    airports = []
    with open("airports-IATA-codes.txt") as f:
        airports = [port.strip("\n") for port in f.readlines()]

    routes = []
    with open("routes.txt") as f:
        routes = [Edge(*r.strip("\n").split(" ")) for r in f.readlines()]

    return airports, routes

class Edge:
    """Edge
    u,v represends an edge from node u to node v
    """

    def __init__(self, u:str, v:str):
        self.u = u
        self.v = v

    def __repr__(self):
        return f"Edge({self.u},{self.v})"


class NodeList:
    """Directed Node List

    attr
    ----------
    inlist: nodes with edges directed into this node
    outlist: nodes with edges directed out of this node

    marked: True if this node has been traversed else False
    component: SCC this node belongs to
    """

    def __init__(self, str):
        self.name = str
        self.inList = set()
        self.outList = set()

        self.marked = False
        self.component = None

    def add_in(self, x):
        self.inList.add(x)

    def add_out(self, x):
        self.outList.add(x)


class Graph:
    """Directed Graph

    attr
    ----------
    nodes: nodes in the graph
    edges: edges in the graph
    num_edges: number of distinct edges (no duplicates)
    dgraph: list of NodeList objects for each node in the graph

    finishing: list to denote the finishing number of each node in a traversal

    largest: largest SCC in the graph
    components: dict to denote a node's SCC
    num_components: number of SCC's present in graph

    """

    def __init__(self, nodes: list):
        self.nodes = nodes
        self.num_nodes = len(nodes)

    def build(self, edges: list):
        '''builds a graph from given edges'''

        ## init
        self.edges = edges
        self.num_edges = 0
        self.dgraph = {node: NodeList(node) for node in self.nodes}

        ## add edges
        for e in self.edges:
            if not e.u == e.v:
                self.dgraph[e.u].add_out(e.v) # add v to the outList of u
                self.dgraph[e.v].add_in(e.u) # add u to the inList of u

        ## count distinct edges
        for name, node in self.dgraph.items():
            self.num_edges += len(node.inList)

    def _unmark(self):
        """marks all nodes as False"""
        for node in self.dgraph.values():
            node.marked = False

    def _postorder_dft(self, v, *, reverse=False, weak=False) -> list:
        """performs a post order DFT on node v"""

        ## return if marked
        nv = self.dgraph[v]
        if nv.marked:
            return []
        output = []
        nv.marked = True

        ## if reversed use inList else use outList
        temp = nv.inList if not reverse else nv.outList

        ## traverse
        for u in temp:
            if not self.dgraph[u].marked:
                output += self._postorder_dft(u, reverse=reverse, weak=weak)

        output += [v]
        self.finishing.append(v)
        return output

    def dft(self, *, reverse=False):
        """performs DTF on nodes in graph
        if reverse is True use outList else inList
        """

        ## init
        output = []
        self._unmark()
        self.finishing = []

        ## begin traversal
        for node in self.nodes:
            if not self.dgraph[node].marked:
                cc = self._postorder_dft(node, reverse=reverse)
                output += [cc]

        return output

    def reduce(self):
        """returns a reduced graph
        by performing a reversed DFT and trying (n) forward DFTs
        """

        output = self.dft(reverse=True)

        # temp = []
        # for o in output:
        #     temp += [*o]
        ## finishing is the same as output...

        ## init
        self.largest = 0
        self.num_components = 0
        self.components = {}

        ## find SCCs
        self._unmark()
        for node in self.finishing[::-1]:
            if not self.dgraph[node].marked:
                scc = self._postorder_dft(node, reverse=False, weak=False)
                for n in scc:
                    self.dgraph[n].component = node
                    self.components[n] = node
                if len(scc) > 0:
                    self.num_components += 1
                if len(scc) > self.largest:
                    self.largest = len(scc)


        ## convert graph edges to reduced graph leaders
        edges = []
        for e in self.edges:
            u = self.dgraph[e.u].component
            v = self.dgraph[e.v].component
            if u != v:
                edges += [Edge(u, v)]

        ## build reduced graph
        reduced = Graph(set(self.components.values()))
        reduced.build(edges)
        reduced.components = self.components
        return reduced


def main():

    ## init
    sys.setrecursionlimit(10000)
    print(f"recursion limit: {sys.getrecursionlimit()}")

    airports, routes = read()
    nodes = airports
    edges = routes

    # nodes = [i for i in range(8)]
    # edges = [
    #     Edge(0, 1),
    #     Edge(2, 0),
    #     Edge(1, 2),
    #     #
    #     Edge(2, 3),
    #     Edge(3, 4),
    #     #
    #     Edge(4, 5),
    #     Edge(5, 6),
    #     Edge(6, 4),
    #     Edge(4, 7),
    #     Edge(7, 6),
    # ]

    ## build graph
    g = Graph(nodes)
    g.build(edges)

    print(f"nodes: {len(g.nodes)}")
    print(f"edges: {len(g.edges)}")
    print()

    ## reduce graph to SCC
    r = g.reduce()

    print(f"number of SCC: {len(r.nodes)}")
    print(f"max SCC size: {g.largest}")
    print(f"edges on reduced graph: {r.num_edges}")
    print()

    ## 1. minRoutes(port)
    ## count SCC with no incoming edges except for port
    port = "ORD"
    # port = 4 # used for testing
    p = g.dgraph[port].component
    print(f"{port} is part of component {p}\n")

    # for name,node in g.dgraph.items():
    #     print(name,node.component)

    count = 0
    for node in r.dgraph.values():
        # print((node.name),(node.name != p),(len(node.inList) == 0))

        if (node.name != p) and len(node.inList) == 0:
            count += 1
    print(f"1. min routes needed: {count}")

    ## 2. flightNetworks
    ## make graph undirected by adding Edge(v,u) for all Edge(u,v)
    for node in r.dgraph.values():
        node.inList |= node.outList
        node.outList |= node.inList

    ## SCC and WCC are equivalent
    w = r.reduce()
    print(f"2. flight networks: {len(w.nodes)}")

if __name__ == "__main__":
    main()
