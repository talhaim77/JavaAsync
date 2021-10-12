package services;

import handler.ThreadHandler;
import model.*;
import tmp.TraversableWeightedMatrix;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadLocalLightestPaths<T> implements LightestPaths {

    protected final ConcurrentLinkedQueue<Node<T>> priorityQueueThreadLocal = new ConcurrentLinkedQueue<>();
    private final ReentrantLock locker = new ReentrantLock();
    protected final ThreadLocal<Set<Node<T>>> finishedThreadLocal = ThreadLocal.withInitial(LinkedHashSet::new);
    private final AtomicInteger tasksCounter = new AtomicInteger();
    protected Integer minimumWeight = Integer.MAX_VALUE;
    protected Set<DijkNode<T>> minList = new HashSet<>();

    @Override
    public Collection<List<T>> runAlgo(TraversableWeightedMatrix matrix,Index start, Index end) {
        return traverse( (Traversable<T>)matrix, new DijkNode<T>((T)end,Integer.MAX_VALUE));
    }

    public Collection<List<T>> traverse(Traversable<T> graph, DijkNode<T> dest) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        // set for saving all minimum paths
        Set<List<T>> minPathSet = new HashSet<>();
        priorityQueueThreadLocal.add(graph.getOrigin());
        // TODO: set source as 0
        // Now submit the first visit task, which will submit its sub-tasks, based
        graph.getDijkOrigin().setWeight(0);
        ThreadHandler.getInstance().getThreadPool().submit((new Visit( graph,graph.getDijkOrigin(), dest)));

        try {
            while (tasksCounter.get() > 0) {
                Thread.sleep(250);
            }
        } catch (InterruptedException e) {
            // Handle the exception here.. Somehow...
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

        for (DijkNode<T> node : minList) {
            if (node.getWeight() == minimumWeight) {
                List<T> nodePath = getPath(node, dest);
                minPathSet.add(nodePath);
            }
        }
        System.out.println(minPathSet);
        return minPathSet;
    }

    private List<T> getPath(DijkNode<T> node, DijkNode<T> dest) {
        List<T> path = new LinkedList<>();
        path.add( (T) (dest.getParent()).getData());
        path.add( (T) (node.getParent()).getData());
        DijkNode<T> next = node.getParent();
        while (next != null) {
            path.add((T) (next.getParent()).getData());
            next = next.getParent();
        }
        return path;
    }


//    @Override
//    public Collection<List<T>> runAlgo(Matrix matrix, Index start, Index end) {
//        return this.traverse(traversableMatrix, start, end);
//    }

    private class Visit implements Runnable {
        private final Traversable<T> graph;

        /**
         *
         */
        private final DijkNode<T> current;

        private final DijkNode<T> end;

        /**
         *
         */


        public Visit(Traversable<T> graph, DijkNode<T> current, DijkNode<T> end) {
            this.graph = graph;
            this.current = current;
            this.end = end;
            tasksCounter.incrementAndGet();
        }

        @Override
        public void run() {
//            AddFinishedSet(current);
            // Adding current node to the TLS
            finishedThreadLocal.get().add(current);
            Collection<DijkNode<T>> neighbors = graph.getReachableNodes(current);


            for (DijkNode<T> neighbor : neighbors) {
            int newWeight = current.getWeight() + graph.getValue(neighbor);
            if (newWeight < neighbor.getWeight()) {
                neighbor.setWeight(newWeight);
            }

            if (!finishedThreadLocal.get().contains(neighbor)) {
                if (end.getData().equals(neighbor.getData())) {
                    // Lock to protect minimumCost and List when we get here from multiple threads
                    locker.lock();
                    try {
                        if (current.getWeight() <= minimumWeight ) {
                            minList.add(current);
                            minimumWeight = current.getWeight();
//                            System.out.println("mini cost " + minimumWeight);
                        }
                    } finally {
                        locker.unlock();
                    }
                } else {
                    // Parallel
                    ThreadHandler.getInstance().getThreadPool().submit((new Visit( graph, graph.getDijkOrigin(), end)));
                }
            }
        }

            tasksCounter.decrementAndGet();
        }
    }

    private class EdgeClass implements Comparable<EdgeClass> {
        int v, weight;

        public EdgeClass(int v, int weight) {
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(EdgeClass o) {
            return Integer.compare(this.weight, o.weight);
        }

        @Override
        public String toString() {
            return weight + "";
        }

    }

//    public static void main(String[] args) {
//        int[][] mat =     new int[][]{
//                new int[]{100, 100, 100},
//                new int[]{500, 900, 300}};
//        Matrix matrix = new Matrix(mat);
//        ThreadLocalLightestPaths dijkstra_pq = new ThreadLocalLightestPaths();
//        dijkstra_pq.distDijkstra(matrix, new Index(1,0), new Index(1,2));
//    }
}
