package services;

import java.util.*;
import java.util.stream.Collectors;
import model.*;
/**
 * When we need to make sure that every thread has its own local data structures  - synchronization
 * is not the solution.
 * TLS- Thread Local Storage
 */
public class ThreadLocalBFS<T> {
//    final ThreadLocal<Queue<Node<T>>> threadLocalQueue = ThreadLocal.withInitial
    final ThreadLocal<Queue<Node<T>>> threadLocalQueue = ThreadLocal.withInitial(() -> new LinkedList<Node<T>>()); // lambda expression

    //Set that contain the visited nodes
    final ThreadLocal<Set<Node<T>>> threadLocalSetVisited = ThreadLocal.withInitial(LinkedHashSet::new);


    public List<List<T>> traverse(Traversable<T> someGraph, Index start, Index destination) {
        /*
        push origin to the queue
        while queue is not empty:
            polled = poll operation
            insert to finished
            invoke getReachableNodes method on removed node
            for each reachableNode:
                if this node is the origin
                    invoke 'createPathToOrigin'
                    add the path to the list of all the paths
         */

        //the list that contains all the paths from origin to destination
       List<List<T>> pathsToDestination = new ArrayList<>();

       //add the origin node to the queue
        threadLocalQueue.get().add(someGraph.getNodeByIndex(start));

        while (!threadLocalQueue.get().isEmpty()) {

            //retrieves the head of the queue
            Node<T> polled = threadLocalQueue.get().poll();

            //add the polled node to the list of visited
            threadLocalSetVisited.get().add(polled);

                Collection<Node<T>> reachableNodes = someGraph.getReachableNodes(polled);

                for (Node<T> singleReachableNode : reachableNodes) {
                    if ((singleReachableNode.getData()).equals(destination)){
                        List<T> oneOfShortestPath = createPathToOrigin(singleReachableNode, someGraph.getNodeByIndex(start));
                        pathsToDestination.add(oneOfShortestPath);
                    }
                    else if (!threadLocalSetVisited.get().contains(singleReachableNode)
                            && !threadLocalQueue.get().contains(singleReachableNode)
                    ) {
                        threadLocalQueue.get().add(singleReachableNode);
                    }
                }
        }
        List<List<T>> allShortestPathToDes = allShortestPaths(pathsToDestination);
        return allShortestPathToDes;
    }

    private List<T> createPathToOrigin(Node<T> destination , Node<T> start) {
        List<T> path = new ArrayList<>();
        path.add(destination.getData());
        Node<T> parent = destination.getParent();
        while(parent.getData() != start.getData()) {
            path.add(parent.getData());
            parent = parent.getParent();
        }
        path.add(start.getData());
        Collections.reverse(path);
        return path;
    }

    private List<List<T>> allShortestPaths(List<List<T>> paths)
    {
        int minSizePath = paths.stream()
                .mapToInt(x -> x.size())
                .min()
                .orElse(0);

        List<List<T>> shortestPaths = paths.stream()
                                .filter(path -> path.size() == minSizePath)
                                .collect(Collectors.toList());
        return shortestPaths;
    }

}



