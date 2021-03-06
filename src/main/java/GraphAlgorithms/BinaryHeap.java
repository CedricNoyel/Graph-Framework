package GraphAlgorithms;

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
                    indexElem = i;
                }
            }
        }
        pos = pos + 1;
    }

    // Remove the root node
    public int remove() {
        int temp = 0;
        pos = pos - 1;

        swap(0, pos);
        int nodeToRemove = nodes[pos];
        nodes[pos] = Integer.MAX_VALUE;
        int idxChild = getBestChildPos(temp);

        while (idxChild != Integer.MAX_VALUE && nodes[temp] > nodes[idxChild]) {
            swap(temp, idxChild);
            temp = idxChild;
            idxChild = getBestChildPos(temp);
        }
        return nodeToRemove; // return the removed node value
    }

    private int getBestChildPos(int src) {
        if (isLeaf(src)) { // the leaf is a stopping case, then we return a default value
            return Integer.MAX_VALUE;
        } else {
            return this.nodes[2*src +1] > this.nodes[2*src +2] ? 2 * src + 2 : 2 * src + 1;
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
        
        int k = 17;
        int m = k;
        int min = 2;
        int max = 20;
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));
            jarjarBin.insert(rand);            
            k--;
        }
        
        System.out.println("\n" + jarjarBin);
        System.out.println(jarjarBin.test());
        jarjarBin.remove();
        System.out.println("\n" + jarjarBin);

        System.out.println(jarjarBin.getBestChildPos(2));

    }

}
