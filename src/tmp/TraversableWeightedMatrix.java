package tmp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import model.*;

public class TraversableWeightedMatrix implements Traversable<Index>{

    protected final Matrix matrix;
    protected Index startIndex;
    protected int[][] costMatrix;
    protected boolean[][] visited;

    public TraversableWeightedMatrix(Matrix matrix) {
        this.matrix = matrix;
        this.costMatrix = new int[matrix.getPrimitiveMatrix().length][matrix.getPrimitiveMatrix()[0].length];
        this.visited = new boolean[matrix.getPrimitiveMatrix().length][matrix.getPrimitiveMatrix()[0].length];

        initializeCostMatrix();
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public Index getStartIndex() {
        return startIndex;
    }

    @Override
    public Collection<Node<Index>> getReachableNodesDiagonal(Node<Index> someNode) {
        return null;
    }

    @Override
    public Node<Index> getNodeByIndex(Index index) {
        return null;
    }

    @Override
    public boolean isVerticalAndHorizontalNeighbors(Index tmpIdx, List<Node<Index>> nodes) {
        return false;
    }

    public void setStartIndex(Index startIndex) {
        this.startIndex = startIndex;
    }

    @Override
    public Node<Index> getOrigin() throws NullPointerException{
        if (this.startIndex == null) throw new NullPointerException("start index is not initialized");
        Node<Index> node = new Node<>(startIndex);
        return node;
    }

    @Override
    public Collection<Node<Index>> getReachableNodes(Node<Index> someNode) {
        List<Node<Index>> reachableIndex = new ArrayList<>();

        for (Index index : this.matrix.getNeighbors(someNode.getData())) {
            Node<Index> node = new Node<>(index, someNode);
            reachableIndex.add(node);
        }

        return reachableIndex;
    }


    @Override
    public DijkNode<Index> getDijkOrigin() {
        if (this.startIndex == null) throw new NullPointerException("start index is not initialized");
        return new DijkNode<>(this.startIndex, Integer.MAX_VALUE);
    }

    @Override
    public int getValue(DijkNode<Index> dijkNode) {
        return matrix.getValue((Index) dijkNode.getData());
    }

    @Override
    public String toString() {
        return matrix.toString();
    }


    private void initializeCostMatrix()
    {
        int size = matrix.getLength();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size ; j++) {
                this.costMatrix[i][j] = Integer.MAX_VALUE;
                this.visited[i][j] = false;
            }

        }
    }

}