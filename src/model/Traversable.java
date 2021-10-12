package model;

import java.util.Collection;
import java.util.List;

/**
 * This interface defines the functionality required for a traversable graph
 */
public interface Traversable<T> {
    public Node<T> getOrigin();
    public DijkNode<T> getDijkOrigin();
    public Collection<Node<T>>  getReachableNodes(Node<T> someNode);
    public Collection<Node<T>> getReachableNodesDiagonal(Node<T> someNode);
    public Node<T> getNodeByIndex(Index index);
//    public boolean isVerticalAndHorizontalNeighbors(Node<Index> node, List<Node<Index>> l);
    public int getValue(DijkNode<T> dijkNode);

    boolean isVerticalAndHorizontalNeighbors(Index tmpIdx, List<Node<T>> nodes);

//    public boolean isVerticalAndHorizontalNeighbors(Index tmpIdx, List<Node<T>> nodes);

//    boolean isDiagonal(Node<T> node, List<Node<T>> nodes);
}
