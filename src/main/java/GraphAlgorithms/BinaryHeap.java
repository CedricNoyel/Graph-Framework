package GraphAlgorithms;


import com.sun.deploy.util.ArrayUtil;

import java.lang.reflect.Array;
import java.util.Arrays;

public class BinaryHeap {

    private int[] nodes;
    private int pos;

    public BinaryHeap() {
        this.nodes = new int[32];
        for (int i = 0; i < nodes.length; i++) {
            this.nodes[i] = Integer.MAX_VALUE;
        }
        this.pos = 0;
    }

    public void resize() {
        int[] tab = new int[this.nodes.length + 32];
        for (int i = 0; i < nodes.length; i++) {
            tab[i] = Integer.MAX_VALUE;
        }
        System.arraycopy(this.nodes, 0, tab, 0, this.nodes.length);
        this.nodes = tab;
    }

    public boolean isEmpty() {
        return pos == 0;
    }

    public void insert(int element) {
    	// A completer
        if (pos >= this.nodes.length) {
            this.resize();
        }

        if (pos == 0) {
            this.nodes[pos] = element;
        } else {
            this.nodes[pos] = element;
            int indexElem = pos;
            for (int i = (pos / 2); i >= 0; i--) {
                if (this.nodes[i] > element) {
                    swap(i, indexElem);
//                    System.out.println("swap: idx:" + i + "=" + nodes[i] + " et idx:" + indexElem + "=" + nodes[indexElem]);
                    indexElem = i;
                }
            }
        }
        pos = pos + 1;
    }

    // Remove the root node
    public int remove() {
        BinaryHeap a = new BinaryHeap();
        for (int i = 1; i < pos; i++) {
            a.insert(this.nodes[i]);
        }
        this.pos = pos - 1;
        this.nodes = a.nodes;
        return 1;
    }

    private int getBestChildPos(int src) {
        if (isLeaf(src)) { // the leaf is a stopping case, then we return a default value
            return Integer.MAX_VALUE;
        } else {
            if ( this.nodes[2*src +1] > this.nodes[2*src +2]) {
                return 2*src +2;
            } else {
                return 2*src +1;
            }
        }
    }

    
    /**
	 * Test if the node is a leaf in the binary heap
	 * 
	 * @returns true if it's a leaf or false else
	 * 
	 */	
    private boolean isLeaf(int src) {
        return (2 * src + 1 > this.pos);
    }

    private void swap(int father, int child) {
        int temp = nodes[father];
        nodes[father] = nodes[child];
        nodes[child] = temp;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < pos; i++) {
            s.append(nodes[i]).append(", ");
        }
        return s.toString();
    }

    /**
	 * Recursive test to check the validity of the binary heap
	 * 
	 * @returns a boolean equal to True if the binary tree is compact from left to right
	 * 
	 */
    public boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (right >= pos) {
                return nodes[left] >= nodes[root] && testRec(left);
            } else {
                return nodes[left] >= nodes[root] && testRec(left) && nodes[right] >= nodes[root] && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryHeap jarjarBin = new BinaryHeap();
//        System.out.println(jarjarBin.isEmpty() + "\n");
        int k = 17;
        int m = k;
        int min = 2;
        int max = 20;
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));
//            System.out.print(" insert " + rand + " ;");
            jarjarBin.insert(rand);            
            k--;
        }
     // A completer
        System.out.println("\n" + jarjarBin);
        System.out.println(jarjarBin.test());
        jarjarBin.remove();
        System.out.println("\n" + jarjarBin);

        System.out.println(jarjarBin.getBestChildPos(2));

    }

}
