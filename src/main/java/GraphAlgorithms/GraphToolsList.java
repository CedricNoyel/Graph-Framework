package GraphAlgorithms;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import Abstraction.AbstractListGraph;
import Abstraction.IGraph;
import AdjacencyList.DirectedGraph;
import AdjacencyList.DirectedValuedGraph;
import AdjacencyList.UndirectedGraph;
import AdjacencyList.UndirectedValuedGraph;
import AdjacencyMatrix.AdjacencyMatrixDirectedGraph;
import Collection.Triple;
import Nodes.AbstractNode;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;
import com.sun.deploy.util.ArrayUtil;
import java.lang.Object;

public class GraphToolsList  extends GraphTools {

	private static int _DEBBUG =0;

	private static int[] visited;
	private static int[] debut;
	private static int[] fin;
	private static List<Integer> order_CC;
	private static int cpt=0;

	//--------------------------------------------------
	// 				Constructors
	//--------------------------------------------------

	public GraphToolsList(){
		super();
	}

	// ------------------------------------------
	// 				Accessors
	// ------------------------------------------



	// ------------------------------------------
	// 				Methods
	// ------------------------------------------
    private static void initVisitedArray(int size){
        visited = new int[size];
        for (int i = 0; i < size; i++) {
            visited[i] = 0;
        }
    }

	// A completer
	public static void BreadthFirstSearch(IGraph myGraph, int startNode) {

		List<Integer> toVisit = new ArrayList<>();
        int[][] adjMatrix = myGraph.toAdjacencyMatrix(); // adjMatrix[0][4] = 1 car 0 a comme successeur 4

        int graphSize = myGraph.getNbNodes();
        initVisitedArray(graphSize);

	    // Mark the current node as visited and enqueue it
		toVisit.add(0, startNode);
        int currentVisitedNode;

        System.out.println("- BFS Started - ");

	    while(toVisit.size() > 0)
	    {
            currentVisitedNode = toVisit.get(0);
            visited[currentVisitedNode] = 1;

            System.out.println("toVisit = " + toVisit);
            System.out.println("Visit node : " + currentVisitedNode);
            System.out.println("adjMatrix["+currentVisitedNode+"]: " + Arrays.toString(adjMatrix[currentVisitedNode]));
            System.out.println("visited = " + Arrays.toString(visited));

			for (int w = 0; w < adjMatrix[0].length ; w++) {
				// Si noeud jamais visité et qu'il a un fils
				if (visited[w] == 0 && adjMatrix[currentVisitedNode][w] == 1 && !toVisit.contains(w)) {
					// Alors on ajoute le noeud fils dans la liste toVisit
					toVisit.add(w);
					System.out.println("Add node " + w + " to the toVisit list");
				}
			}
            // Dequeue visied node
            toVisit.remove(toVisit.indexOf(currentVisitedNode));
            System.out.println("_______________________________________________________________________________________");

            // DONE : If visited contains a 0, add the first node to the toVisit list
            // Check des graphs non connexes
            if (toVisit.size() == 0 && Arrays.stream(visited).anyMatch(n->n==0)) {
                System.out.println("Exploration des graphs non connexes");
                for (int n = 0; n < visited.length; n++) {
                    if (visited[n] == 0) {
                        System.out.println("Add node " + n + " to the toVisit list");
                        toVisit.add(n);
                    }
                }
            }
        }
        System.out.println("- BFS Ended - ");
        System.out.println("toVisit = " + toVisit);
        System.out.println("visited = " + Arrays.toString(visited));
	}
	
	public static void DepthFirstSearch(IGraph myGraph, int startNode) {

        List<Integer> toVisit = new ArrayList<>();
        int[][] adjMatrix = myGraph.toAdjacencyMatrix(); // adjMatrix[0][4] = 1 car 0 a comme successeur 4

        int graphSize = myGraph.getNbNodes();
        initVisitedArray(graphSize);

        // Mark the current node as visited and enqueue it
        toVisit.add(0, startNode);
        int currentVisitedNode;

        System.out.println("- DFS Started - ");
        while(toVisit.size() > 0)
        {
            // We just always explore the last elem that we added to the list
            currentVisitedNode = toVisit.get(toVisit.size()-1);
            visited[currentVisitedNode] = 1;

            System.out.println("toVisit = " + toVisit);
            System.out.println("Visit node : " + currentVisitedNode);
            System.out.println("adjMatrix["+currentVisitedNode+"]: " + Arrays.toString(adjMatrix[currentVisitedNode]));
            System.out.println("visited = " + Arrays.toString(visited));

            for (int w = 0; w < adjMatrix[0].length ; w++) {
                // Si noeud jamais visité et qu'il a un fils
                if (visited[w] == 0 && adjMatrix[currentVisitedNode][w] == 1 && !toVisit.contains(w)) {
                    // Alors on ajoute le noeud fils dans la liste toVisit
                    toVisit.add(w);
                    System.out.println("Add node " + w + " to the toVisit list");
                }
            }
            // Dequeue visied node
            toVisit.remove(toVisit.indexOf(currentVisitedNode));
            System.out.println("_______________________________________________________________________________________");

            // DONE : If visited contains a 0, add the first node to the toVisit list
            // Check des graphs non connexes
            if (toVisit.size() == 0 && Arrays.stream(visited).anyMatch(n->n==0)) {
                System.out.println("Exploration des graphs non connexes");
                for (int n = 0; n < visited.length; n++) {
                    if (visited[n] == 0) {
                        System.out.println("Add node " + n + " to the toVisit list");
                        toVisit.add(n);
                    }
                }
            }
        }

        System.out.println("- DFS Ended - ");
        System.out.println("toVisit = " + toVisit);
        System.out.println("visited = " + Arrays.toString(visited));
	}

	// Calcule les sommets accessibles depuis s par une chaîne
    public static void explorerSommet(AbstractNode s, AbstractListGraph a) {
	    System.out.println("explorerSommet(" + s.getLabel() + ")   - cpt: " + cpt);
        debut[s.getLabel()] = cpt;
        visited[s.getLabel()] = 1;
        cpt++;

        if (s instanceof DirectedNode) {
            DirectedNode myNode = (DirectedNode) s;
            for (DirectedNode next : myNode.getSuccs().keySet()) {
                if (visited[next.getLabel()] == 0) {
                    explorerSommet(next, a);
                }
            }
        } else {
            UndirectedNode myNode = (UndirectedNode) s;
            for (UndirectedNode next : myNode.getNeighbours().keySet()) {
                if (visited[next.getLabel()] == 0) {
                    explorerSommet(next, a);
                }
            }
        }

        visited[s.getLabel()] = 2;
        fin[s.getLabel()] = cpt;
    }

    // Calcule les composantes connexe du graphe.
	public static void explorerGraph(AbstractListGraph<AbstractNode> g) {
        cpt = 0;
        debut = new int[g.getNbNodes()];
        visited = new int[g.getNbNodes()];
        fin = new int[g.getNbNodes()];
        AbstractNode first = g.getNodes().get(0);

        List<AbstractNode> nodes = g.getNodes();

        if (first instanceof DirectedNode) {
            for (AbstractNode i : nodes) {
                if (visited[i.getLabel()] == 0) {
                    explorerSommet(i, g);
                }
            }
        } else {
            for (AbstractNode i : nodes) {
                if (visited[i.getLabel()] == 0) {
                    explorerSommet(i, g);
                }
            }
        }

//        System.out.println("explorerGraph - FIN: " + Arrays.toString(fin));
//        System.out.println("explorerGraph - VISITED: " + Arrays.toString(visited));
//        System.out.println("explorerGraph - DEBUT: " + Arrays.toString(debut));
    }


	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 7, false, false, true, 100001);
		GraphTools.afficherMatrix(Matrix);
		DirectedGraph<DirectedNode> al = new DirectedGraph<>(Matrix);
//		System.out.println(al);

        long startTime, endTime;

        startTime = System.nanoTime(); // Get execution start time
        BreadthFirstSearch(al, 0);
        endTime = System.nanoTime();
        long dfsTime = (endTime - startTime) / 1000000;

        startTime = System.nanoTime(); // Get execution start time
        DepthFirstSearch(al, 0);
        endTime = System.nanoTime();
        long bfsTime = (endTime - startTime) / 1000000;

        System.out.println("");
        System.out.println("===== EXECUTION TIME =====");
        System.out.println("Time to explore all nodes");
        System.out.println("bfsTime: " + bfsTime + " ms");
        System.out.println("dfsTime: " + dfsTime + " ms");
        System.out.println("===============================");


        AbstractListGraph<AbstractNode> al2 = new DirectedGraph(Matrix);
        explorerGraph(al2);

        System.out.println("===== EXECUTION TIME =====");
        AbstractListGraph<UndirectedNode> al3 = new UndirectedGraph<>(Matrix);
        explorerGraph(al2);
	}
}
