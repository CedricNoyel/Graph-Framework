package AdjacencyList;

import java.util.ArrayList;
import java.util.Map;

import Abstraction.AbstractListGraph;
import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;
import Abstraction.IDirectedGraph;
import AdjacencyMatrix.AdjacencyMatrixDirectedGraph;

public class DirectedGraph<A extends DirectedNode> extends AbstractListGraph<A> implements IDirectedGraph<A> {

	private static int _DEBBUG =0;
		
    //--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

	public DirectedGraph(){
		super();
		this.nodes = new ArrayList<A>();
	}

    public DirectedGraph(int[][] matrix) {
        this.order = matrix.length;
        this.nodes = new ArrayList<A>();
        for (int i = 0; i < this.order; i++) {
            this.nodes.add(i, this.makeNode(i));
        }
        for (A n : this.getNodes()) {
            for (int j = 0; j < matrix[n.getLabel()].length; j++) {
                A nn = this.getNodes().get(j);
                if (matrix[n.getLabel()][j] != 0) {
                    n.getSuccs().put(nn,0);
                    nn.getPreds().put(n,0);
                    this.m++;
                }
            }
        }
    }

    public DirectedGraph(DirectedGraph<A> g) {
        super();
        this.nodes = new ArrayList<>();
        this.order = g.getNbNodes();
        this.m = g.getNbArcs();
        for(A n : g.getNodes()) {
            this.nodes.add(makeNode(n.getLabel()));
        }
        for (A n : g.getNodes()) {
            A nn = this.getNodes().get(n.getLabel());
            for (DirectedNode sn : n.getSuccs().keySet()) {
                DirectedNode snn = this.getNodes().get(sn.getLabel());
                nn.getSuccs().put(snn,0);
                snn.getPreds().put(nn,0);
            }
        }

    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------

    @Override
    public int getNbArcs() {
        return this.m;
    }

    @Override
    public boolean isArc(A from, A to) {
    	// A completer
    	return this.getNodeOfList(from).getSuccs().containsKey(this.getNodeOfList(to));
    }

    @Override
    public void removeArc(A from, A to) {
    	if(this.isArc(from, to)) {
    		this.getNodeOfList(to).getPreds().remove(this.getNodeOfList(from));
    		this.getNodeOfList(from).getSuccs().remove(this.getNodeOfList(to));
    	}
    }

    @Override
    public void addArc(A from, A to) {
    	this.getNodeOfList(from).addSucc(this.getNodeOfList(to), 0);
    	this.getNodeOfList(to).addPred(this.getNodeOfList(from), 0);
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------

    /**
     * Method to generify node creation
     * @param label of a node
     * @return a node typed by A extends DirectedNode
     */
    @Override
    public A makeNode(int label) {
        return (A)new DirectedNode(label);
    }

    /**
     * @return the corresponding nodes in the list this.nodes
     */
    public A getNodeOfList(A src) {
        return this.getNodes().get(src.getLabel());
    }

    /**
     * @return the adjacency matrix representation int[][] of the graph
     */
    @Override
    public int[][] toAdjacencyMatrix() {

        int[][] matrix = new int[order][order];
        for (int i = 0; i < order; i++) {
            for (DirectedNode j : nodes.get(i).getSuccs().keySet()) {
                int IndSucc = j.getLabel();
                matrix[i][IndSucc] = 1;
            }
        }
        return matrix;
    }

    @Override
    public IDirectedGraph computeInverse() {
        DirectedGraph<A> g = new DirectedGraph<>(this);
        for(int i = 0; i < order; i++) {
        	for(DirectedNode j: nodes.get(i).getSuccs().keySet()) {
        		if(!j.getSuccs().containsKey(nodes.get(i))) {
            		g.removeArc(g.getNodeOfList(nodes.get(i)), g.getNodeOfList((A) j));
            		g.addArc(g.getNodeOfList((A) j), g.getNodeOfList(nodes.get(i)));
        		}
        	}
        }
        return g;
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(DirectedNode n : nodes){
            s.append("successors of ").append(n).append(" : ");
            for(DirectedNode sn : n.getSuccs().keySet()){
                s.append(sn).append(" ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        GraphTools.afficherMatrix(Matrix);
        DirectedGraph al = new DirectedGraph(Matrix);
        System.out.println(al);
        System.out.println("");
		System.out.println("----------- Début des tests -----------");
		System.out.println("isEdge 2 3 : " + al.isArc(new DirectedNode(2), new DirectedNode(3)));
		System.out.println("isEdge 2 5 : " + al.isArc(new DirectedNode(2), new DirectedNode(5)));
		System.out.println("removeEdge 2 3 : ");
		al.removeArc(new DirectedNode(2), new DirectedNode(3));
		System.out.println("isEdge 2 3 : " + al.isArc(new DirectedNode(2), new DirectedNode(3)));
		System.out.println("addEdge 2 5 : ");
		al.addArc(new DirectedNode(2), new DirectedNode(5));
		System.out.println("isEdge 2 5 : " + al.isArc(new DirectedNode(2), new DirectedNode(5)));
		System.out.println("----------- Compute inverse -----------");
		//System.out.println(al);

        AdjacencyMatrixDirectedGraph myMatrix = new AdjacencyMatrixDirectedGraph(al.toAdjacencyMatrix());
        System.out.println(myMatrix);

		DirectedGraph am2 = (DirectedGraph) al.computeInverse();
		System.out.println(am2);
    }
}
