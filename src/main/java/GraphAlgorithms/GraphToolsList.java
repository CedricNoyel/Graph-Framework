package GraphAlgorithms;

import java.util.*;

import Abstraction.AbstractListGraph;
import Abstraction.IGraph;
import AdjacencyList.DirectedGraph;
import AdjacencyList.DirectedValuedGraph;
import AdjacencyList.UndirectedGraph;
import Nodes.AbstractNode;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;
public class GraphToolsList  extends GraphTools {

	private static int _DEBBUG = 0;

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

    // BFS using matrix
	public static void breadthFirstSearch(IGraph myGraph, int startNode) {

		List<Integer> toVisit = new ArrayList<>();
        int[][] adjMatrix = myGraph.toAdjacencyMatrix(); // adjMatrix[0][4] = 1 car 0 a comme successeur 4

        int graphSize = myGraph.getNbNodes();
        initVisitedArray(graphSize);

	    // Mark the current node as visited and enqueue it
		toVisit.add(0, startNode);
        int currentVisitedNode;

        if (_DEBBUG == 1) {
        	System.out.println("- BFS Started - ");
        }

	    while(toVisit.size() > 0)
	    {
            currentVisitedNode = toVisit.get(0);
            visited[currentVisitedNode] = 1;

            if (_DEBBUG == 1) {
            	System.out.println("toVisit = " + toVisit);
                System.out.println("Visit node : " + currentVisitedNode);
                System.out.println("adjMatrix["+currentVisitedNode+"]: " + Arrays.toString(adjMatrix[currentVisitedNode]));
                System.out.println("visited = " + Arrays.toString(visited));
            }
            

			for (int w = 0; w < adjMatrix[0].length ; w++) {
				// Si noeud jamais visité et qu'il a un fils
				if (visited[w] == 0 && adjMatrix[currentVisitedNode][w] == 1 && !toVisit.contains(w)) {
					// Alors on ajoute le noeud fils dans la liste toVisit
					toVisit.add(w);
					if (_DEBBUG == 1) {
						System.out.println("Add node " + w + " to the toVisit list");
					}
				}
			}
            // Dequeue visied node
            toVisit.remove(toVisit.indexOf(currentVisitedNode));
            if (_DEBBUG == 1) {
            	System.out.println("_______________________________________________________________________________________");
            }
            
            // DONE : If visited contains a 0, add the first node to the toVisit list
            // Check des graphs non connexes
            if (toVisit.size() == 0 && Arrays.stream(visited).anyMatch(n->n==0)) {
            	if (_DEBBUG == 1) {
            		System.out.println("Exploration des graphs non connexes");
            	}
                for (int n = 0; n < visited.length; n++) {
                    if (visited[n] == 0) {
                    	if (_DEBBUG == 1) {
                    		System.out.println("Add node " + n + " to the toVisit list");
                    	}
                        toVisit.add(n);
                    }
                }
            }
        }
	    if (_DEBBUG == 1) {
	        System.out.println("- BFS Ended - ");
	        System.out.println("toVisit = " + toVisit);
	        System.out.println("visited = " + Arrays.toString(visited));
	    }
	}

	// DFS using matrix
	public static void depthFirstSearch(IGraph myGraph, int startNode) {

        List<Integer> toVisit = new ArrayList<>();
        int[][] adjMatrix = myGraph.toAdjacencyMatrix(); // adjMatrix[0][4] = 1 car 0 a comme successeur 4

        int graphSize = myGraph.getNbNodes();
        initVisitedArray(graphSize);

        // Mark the current node as visited and enqueue it
        toVisit.add(0, startNode);
        int currentVisitedNode;

        if (_DEBBUG == 1) {
        	System.out.println("- DFS Started - ");
        }
        
        while(toVisit.size() > 0)
        {
            // We just always explore the last elem that we added to the list
            currentVisitedNode = toVisit.get(toVisit.size()-1);
            visited[currentVisitedNode] = 1;
            if (_DEBBUG == 1) {
	            System.out.println("toVisit = " + toVisit);
	            System.out.println("Visit node : " + currentVisitedNode);
	            System.out.println("adjMatrix["+currentVisitedNode+"]: " + Arrays.toString(adjMatrix[currentVisitedNode]));
	            System.out.println("visited = " + Arrays.toString(visited));
            }

            for (int w = 0; w < adjMatrix[0].length ; w++) {
                // Si noeud jamais visité et qu'il a un fils
                if (visited[w] == 0 && adjMatrix[currentVisitedNode][w] == 1 && !toVisit.contains(w)) {
                    // Alors on ajoute le noeud fils dans la liste toVisit
                    toVisit.add(w);
                    if (_DEBBUG == 1) {
                    	System.out.println("Add node " + w + " to the toVisit list");
                    }
                }
            }
            // Dequeue visied node
            toVisit.remove(toVisit.indexOf(currentVisitedNode));
            
            if (_DEBBUG == 1) {
            	System.out.println("_______________________________________________________________________________________");
            }
            
            // DONE : If visited contains a 0, add the first node to the toVisit list
            // Check des graphs non connexes
            if (toVisit.size() == 0 && Arrays.stream(visited).anyMatch(n->n==0)) {
            	if (_DEBBUG == 1) {
            		System.out.println("Exploration des graphs non connexes");
            	}
            	
                for (int n = 0; n < visited.length; n++) {
                    if (visited[n] == 0) {
                    	if (_DEBBUG == 1) {
                    		System.out.println("Add node " + n + " to the toVisit list");
                    	}
                    	
                        toVisit.add(n);
                    }
                }
            }
        }

        if (_DEBBUG == 1) {
	        System.out.println("- DFS Ended - ");
	        System.out.println("toVisit = " + toVisit);
	        System.out.println("visited = " + Arrays.toString(visited));
        }
	}

	// Calcule les sommets accessibles depuis s par une chaîne
    public static void explorerSommet(AbstractNode s, AbstractListGraph a) {
//	    System.out.println("explorerSommet(" + s.getLabel() + ")   - cpt: " + cpt);
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

    // Explorer le graph de maniere recursive en profondeur
	public static void explorerGraph(AbstractListGraph<AbstractNode> g) {
        cpt = 0;
        debut = new int[g.getNbNodes()];
        visited = new int[g.getNbNodes()];
        fin = new int[g.getNbNodes()];
        AbstractNode first = g.getNodes().get(0);

        List<AbstractNode> nodes = g.getNodes();

        for (AbstractNode i : nodes) {
            if (visited[i.getLabel()] == 0) {
                explorerSommet(i, g);
            }
        }

        System.out.println("explorerGraph - FIN: " + Arrays.toString(fin));
        System.out.println("explorerGraph - VISITED: " + Arrays.toString(visited));
        System.out.println("explorerGraph - DEBUT: " + Arrays.toString(debut));
    }

    public static AbstractListGraph inverserGaph(AbstractListGraph g1) {
        System.out.println(g1.toString());
        List<DirectedNode> nodes = g1.getNodes();
        AbstractListGraph b = new DirectedGraph();

        for (DirectedNode x : nodes) {
            for (AbstractNode y : nodes) {
                for (int k = y.getLabel(); k < y.getLabel() + 1; k++) {
                    if ((AbstractNode) g1.getNodes().get(k) == x) {
                        // TODO
                    }
                }
            }
        }
//        Pour x := 1 à n
//            Pour y := 1 à n
//                Pour k := NODE[y] à NODE[y+1]-1
//                Si SUCC[k] = x
//                    alors y est prédécesseur de x

        return g1;
    }

    // DIJKRA complexité O(a + n log n) pour n sommets et a arretes
    public static void dijkra(AbstractListGraph g, DirectedNode startNode) {

	    int n = g.getNbNodes();

        int[] distance = new int[n];
        boolean[] visited = new boolean[n];
        DirectedNode[] predecessor = new DirectedNode[n];

        for (int i = 0; i < n; i++) {
            distance[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }

	    distance[startNode.getLabel()] = 0;
        // On definit une listee trié par poids
        PriorityQueue<DirectedNode> priorityQueue = new PriorityQueue<DirectedNode>( (a,b) -> a.getWeight() - b.getWeight());
        priorityQueue.add(startNode);
        visited[startNode.getLabel()] = true;

        while( !priorityQueue.isEmpty() ){
            // Getting the minimum distance node from priority queue
            DirectedNode actualNode = priorityQueue.poll(); // get head

            // Pour chaque fils du noeud
            actualNode.getSuccs().forEach((currentNodeSucc, dist) -> {
                // Si le noeud n'a jamais été visité
                if(!visited[currentNodeSucc.getLabel()]) {
                    int newDistance = distance[actualNode.getLabel()] + dist;

                    if( newDistance < distance[currentNodeSucc.getLabel()] ){
                        priorityQueue.remove(currentNodeSucc);
                        distance[currentNodeSucc.getLabel()] = newDistance;
                        predecessor[currentNodeSucc.getLabel()] = actualNode;
                        priorityQueue.add(currentNodeSucc);
                    }
                }
            });
            visited[actualNode.getLabel()] = true;
        }

        for (int i = 0; i < n; i++) {
            System.out.println("Disance node " + i + " -> " + distance[i]);
        }


    }


	public static void main(String[] args) {
		int[][] matrix = GraphTools.generateGraphData(1000, 200, false, false, true, 100001);
		DirectedGraph<DirectedNode> al = new DirectedGraph<>(matrix);
		
		int[][] matrix2 = GraphTools.generateGraphData(10, 7, false, false, true, 100001);

        runDfsBfsTest(al);
        runExplorerGraphTest(matrix2);
        runDijkraTest();
	}

    private static void runExplorerGraphTest(int[][] Matrix) {

        AbstractListGraph<AbstractNode> al2 = new DirectedGraph(Matrix);
        explorerGraph(al2);

        AbstractListGraph<AbstractNode> al3 = new UndirectedGraph(Matrix);
        explorerGraph(al3);
    }

	private static void runDfsBfsTest(DirectedGraph<DirectedNode> al){
        al.toAdjacencyMatrix().toString();
        long startTime, endTime;

        startTime = System.nanoTime(); // Get execution start time
        breadthFirstSearch(al, 0);
        endTime = System.nanoTime();
        long dfsTime = (endTime - startTime) / 1000000;

        startTime = System.nanoTime(); // Get execution start time
        depthFirstSearch(al, 0);
        endTime = System.nanoTime();
        long bfsTime = (endTime - startTime) / 1000000;

        System.out.println("");
        System.out.println("===== EXECUTION TIME TO DISCOVER GRAPH =====");
        System.out.println("bfsTime: " + bfsTime + " ms");
        System.out.println("dfsTime: " + dfsTime + " ms");
        System.out.println("============================================");

    }

    private static void runDijkraTest() {
    	
        System.out.println("================ DIJKRA ====================");
        DirectedValuedGraph a = new DirectedValuedGraph(GraphTools.generateValuedGraphData(6, false, true, true, false, 100001));
        System.out.println(a.toString());
        dijkra(a, a.getNodes().get(0));
        System.out.println("============== FIN DIJKRA ==================");

    }
}
