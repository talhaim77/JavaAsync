package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements adapter/wrapper/decorator design pattern
 */
public class TraversableMatrix implements Traversable<Index> {
    public static final String T_NAME = "Task-1";

    protected Matrix matrix;
    protected Index startIndex;

    public TraversableMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }


    public Index getStartIndex() {
        return startIndex;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setStartIndex(Index startIndex) {
        this.startIndex = startIndex;
    }

    @Override
    public Node<Index> getOrigin() throws NullPointerException{
        if (this.startIndex == null) throw new NullPointerException("start index is not initialized");
        return new Node<>(this.startIndex);
    }

    @Override
    public DijkNode<Index> getDijkOrigin() {
        if (this.startIndex == null) throw new NullPointerException("start index is not initialized");
        return new DijkNode<>(this.startIndex, Integer.MAX_VALUE);
    }

    @Override
    public Collection<Node<Index>> getReachableNodes(Node<Index> someNode) {
        List<Node<Index>> reachableIndices = new ArrayList<>();
        for (Index index : this.matrix.getNeighbors(someNode.getData())) {
            if (matrix.getValue(index) == 1) {
                // A neighboring index whose value is 1
                Node<Index> indexNode = new Node<>(index, someNode);
                reachableIndices.add(indexNode);
            }
        }
        return reachableIndices;
    }

    @Override
    public Collection<Node<Index>> getReachableNodesDiagonal(Node<Index> someNode) {
        List<Node<Index>> reachableIndicesDiagonal = new ArrayList<>();
        for (Index index : this.matrix.getNeighborsIncludeDiagonal(someNode.getData())) {
            if (matrix.getValue(index) == 1) {
                // A neighboring index whose value is 1
                Node<Index> indexNode = new Node<>(index, someNode);
                reachableIndicesDiagonal.add(indexNode);
            }
        }
        return reachableIndicesDiagonal;

    }

    @Override
    public Node<Index> getNodeByIndex(Index index) {
            return new Node<>(index);
    }

    @Override
    public boolean isVerticalAndHorizontalNeighbors(Index originIndex, List<Node<Index>> nodes) {
        boolean isVertical = false;
        boolean isHorizontal = false;
        for (Node<Index> node : nodes) {
            Index tempIdx = node.getData();
            // check vertical
            try {
                if( ((tempIdx.getRow()-1 == originIndex.getRow()) && (tempIdx.getColumn() == originIndex.getColumn())
                ) || ((tempIdx.getRow()+1 == originIndex.getRow()) && (tempIdx.getColumn() == originIndex.getColumn()) )){
                    isVertical = true;
                }
            } catch (ArrayIndexOutOfBoundsException ignored){}
            // check Horizontal
            try {
                if( ((tempIdx.getRow() == originIndex.getRow()) && (tempIdx.getColumn()-1 == originIndex.getColumn())
                ) || ((tempIdx.getRow() == originIndex.getRow()) && (tempIdx.getColumn()+1 == originIndex.getColumn()) )){
                    isHorizontal = true;
                }
            }catch (ArrayIndexOutOfBoundsException ignored){}
        }
        return isVertical && isHorizontal;
    }

    public Collection<Node<Index>> getReachableNodesWithStreams(Node<Index> someNode) {
        return this.matrix.getNeighbors(someNode.getData())
                .stream().filter(index -> matrix.getValue(index)==1).map(index -> new Node<>(index,someNode))
                .collect(Collectors.toList());
    }

    @Override
    public int getValue(DijkNode<Index> dijkNode) {
        return matrix.getValue((Index) dijkNode.getData());
    }

    @Override
    public String toString() {
        return matrix.toString();
    }

}
