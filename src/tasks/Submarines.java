package tasks;

import java.util.*;
import java.util.stream.Collectors;

import model.*;

public class Submarines<T> {
    public static final String T_NAME = "Task-3";
    final ThreadLocal<Stack<Node<T>>> threadLocalStack = ThreadLocal.withInitial(() -> new Stack<Node<T>>()); // lambda expression

    // all the visited nodes , no duplicate.
    final ThreadLocal<List<Node<T>>> threadLocalSet = ThreadLocal.withInitial(LinkedList::new);
    // hash set to each single component
    final ThreadLocal<List<Node<T>>> singleThreadLocalSet = ThreadLocal.withInitial(LinkedList::new);

    /* all connected components order by size of single hash set, without duplicates */
    List<List<T>> allConnectedComponents = new LinkedList<>();


    public List<List<T>> traverse(Traversable<T> someGraph) {
        Matrix matrix = ((TraversableMatrix)someGraph).getMatrix();
        Collection<Node<T>> listOfIndexes = new ArrayList<>();

        for (int row=0 ; row < matrix.getLength(); row++){
            for (int col=0 ; col < matrix.getLength(); col++){
                if(matrix.getPrimitiveMatrix()[row][col]==1)
                    listOfIndexes.add(new Node(new Index(row, col)));
            }
        }
        for (Node<T> singleReachableNode : listOfIndexes) {
            singleThreadLocalSet.get().clear();
            if (!threadLocalSet.get().contains(singleReachableNode)) {
                threadLocalStack.get().push(singleReachableNode);
                while (!threadLocalStack.get().isEmpty()) {
                    Node<T> popped = threadLocalStack.get().pop();
                    threadLocalSet.get().add(popped);
                    singleThreadLocalSet.get().add(popped);

                    Collection<Node<T>> reachableNodes = someGraph.getReachableNodesDiagonal(popped);
//                    System.out.println("Neighbors of: " + popped + " \n it's:" + reachableNodes);
                    for (Node<T> singleReachableNodeN : reachableNodes) {
                        if (!threadLocalSet.get().contains(singleReachableNodeN) && !threadLocalStack.get().contains(singleReachableNodeN)) {
                            threadLocalStack.get().push(singleReachableNodeN);
                        }
                    }
                }
                // TODO: check with tal if possible as hashSet or List
                List<T> connectedComponent = new ArrayList<>();

                boolean isContinue = true;
                for (Node<T> singleNode : singleThreadLocalSet.get()) {
                    if (!isContinue)
                        break;
                    Index tmpIdx = (Index) singleNode.getData();
                    Collection<Index> diagNeighbors;
                    if( tmpIdx.getRow() % 2 == 0 ){
                        diagNeighbors = matrix.getOnlyDiagonalNeighbors(tmpIdx);
                        for (Index index : diagNeighbors){
                            for(Node<T> existNodes : singleThreadLocalSet.get()) {
                                if(existNodes.getData().equals(index)){
                                    isContinue = someGraph.isVerticalAndHorizontalNeighbors(tmpIdx,
                                            singleThreadLocalSet.get());
                                    break;
                                }
                            }
                            if (!isContinue)
                                break;
                        }

                    }
                }
                if(isContinue == true){
                    for (Node<T> node : singleThreadLocalSet.get()) connectedComponent.add(node.getData());
//                    threadLocalSet.get().clear();
                    allConnectedComponents.add(connectedComponent);
                }


            }
        }
        Collections.sort(allConnectedComponents, Comparator.comparingInt(connectedComponent -> connectedComponent.size()));
        return allConnectedComponents;
    }
}
