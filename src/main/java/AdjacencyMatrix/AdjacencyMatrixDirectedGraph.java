package AdjacencyMatrix;

import Abstraction.AbstractMatrixGraph;
import GraphAlgorithms.GraphTools;
import Nodes.AbstractNode;
import Nodes.DirectedNode;
import Nodes.UndirectedNode;
import Abstraction.IDirectedGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the directed graphs structured by an adjacency matrix.
 * It is possible to have simple and multiple graph
 */
public class AdjacencyMatrixDirectedGraph extends AbstractMatrixGraph<DirectedNode> implements IDirectedGraph {

	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixDirectedGraph() {
		super();
	}

	public AdjacencyMatrixDirectedGraph(int[][] M) {
		this.order = M.length;
		this.matrix = new int[this.order][this.order];
		for(int i = 0; i<this.order; i++){
			for(int j = 0; j<this.order; j++){
				this.matrix[i][j] = M[i][j];
				this.m += M[i][j];
			}
		}
	}

	public AdjacencyMatrixDirectedGraph(IDirectedGraph<DirectedNode> g) {
		this.order = g.getNbNodes();
		this.m = g.getNbArcs();
		this.matrix = g.toAdjacencyMatrix();
	}

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------

	@Override
	public int getNbArcs() {
		return this.m;
	}

	public List<Integer> getSuccessors(AbstractNode x) {
		List<Integer> v = new ArrayList<Integer>();
		for(int i =0;i<this.matrix[x.getLabel()].length;i++){
			if(this.matrix[x.getLabel()][i]>0){
				v.add(i);
			}
		}
		return v;
	}

	public List<Integer> getPredecessors(AbstractNode x) {
		List<Integer> v = new ArrayList<Integer>();
		for(int i =0;i<this.matrix.length;i++){
			if(this.matrix[i][x.getLabel()]>0){
				v.add(i);
			}
		}
		return v;
	}
	
	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------		
	
	@Override
	public boolean isArc(AbstractNode from, AbstractNode to) {
		// A completer
		if (this.matrix[from.getLabel()][to.getLabel()] == 0) {
			return false;
		}
		return true;
	}

	/**
	 * removes the arc (from,to) if there exists at least one between these nodes in the graph.
	 */
	@Override
	public void removeArc(AbstractNode from, AbstractNode to) {
		// A completer
		if (this.matrix[from.getLabel()][to.getLabel()] > 0) {
			this.matrix[from.getLabel()][to.getLabel()] -= 1;
		}
	}

	/**
	 * Adds the arc (from,to). we allow multiple graph.
	 */
	@Override
	public void addArc(AbstractNode from, AbstractNode to) {
		// A completer
		this.matrix[from.getLabel()][to.getLabel()] += 1;
	}


	/**
	 * @return the adjacency matrix representation int[][] of the graph
	 */
	public int[][] toAdjacencyMatrix() {
		return this.matrix;
	}

	@Override
	public IDirectedGraph<DirectedNode> computeInverse() {
		// A completer
		AdjacencyMatrixDirectedGraph am = new AdjacencyMatrixDirectedGraph(this.matrix);
		int value;
		for (int i=0; i<this.order; i++) {
			for (int j=i+1; j < this.order; j++) {
				value = am.matrix[i][j];
				am.matrix[i][j] = am.matrix[j][i];
				am.matrix[j][i] = value;
			}
		}
		return am;
	}

	@Override
	public String toString(){
		StringBuilder s = new StringBuilder("Adjacency Matrix: \n");
		for (int[] ints : matrix) {
			for (int anInt : ints) {
				s.append(anInt).append(" ");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static void main(String[] args) {
		int[][] matrix2 = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
		AdjacencyMatrixDirectedGraph am = new AdjacencyMatrixDirectedGraph(matrix2);
		System.out.println(am);
		List<Integer> t = am.getSuccessors(new DirectedNode(1));
		for (Integer integer : t) {
			System.out.print(integer + ", ");
		}
		System.out.println();
		List<Integer> t2 = am.getPredecessors(new DirectedNode(2));
		for (Integer integer : t2) {
			System.out.print(integer + ", ");
		}
		
		// Test isArc()
		int i = 2;
		int j = 3;
		System.out.println("isArc(" + i + "," + j + ") => " + am.isArc(new UndirectedNode(i), new UndirectedNode(j)));
		
		// Test removeEdge()
		am.removeArc(new UndirectedNode(i), new UndirectedNode(j));
		System.out.println("removeArc(" + i + "," + j + ")");
		System.out.println("isArc(" + i + "," + j + ") => " + am.isArc(new UndirectedNode(i), new UndirectedNode(j)));
		
		// Test addEdge()
		am.addArc(new UndirectedNode(i), new UndirectedNode(j));
		System.out.println("addArc(" + i + "," + j + ")");
		System.out.println("isArc(" + i + "," + j + ") => " + am.isArc(new UndirectedNode(i), new UndirectedNode(j)));

		// Test computeInverse()
		System.out.println("computeInverse()");
		System.out.println(am.computeInverse());
		
		
	}
}
