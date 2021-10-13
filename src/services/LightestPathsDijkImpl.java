package services;

import model.Edge;
import model.Index;
import model.Matrix;
import model.Node;

import java.util.*;

public class LightestPathsDijkImpl {
    public static final String T_NAME = "Task-4";

    private int sizeOfIndexes;
    private List<Edge>[] graph;
//    final ThreadLocal<Set<Node<T>>> threadLocalSetVisited = ThreadLocal.withInitial(LinkedHashSet::new);

    public LightestPathsDijkImpl() {
        graph = new ArrayList[sizeOfIndexes];
        for (int x = 0; x < sizeOfIndexes; x++) {
            graph[x] = new ArrayList<>();
        }
    }

    public int calcIndexValue(Index idx, Matrix m) {
        int row = idx.getRow();
        int col = idx.getColumn();
        return row * (m.getPrimitiveMatrix()[0].length) + col;
    }

    public Index calcIndexByVal(int val, Matrix m) {
        int colSize = m.getPrimitiveMatrix()[0].length;
        return new Index((val / colSize), (val % colSize));
    }

    public List<List<Index>> distDijkstra(Matrix mat, Index start, Index end) {
        this.sizeOfIndexes = mat.getAll().size();
        final ThreadLocal<PriorityQueue<Edge>> pq = ThreadLocal.withInitial(() -> new PriorityQueue<>());
        final ThreadLocal<boolean[]> check = ThreadLocal.withInitial(() -> new boolean[this.sizeOfIndexes]);
        final ThreadLocal<Map<Index, List<Index>>> parentMap = ThreadLocal.withInitial(() -> new HashMap<>());
        final ThreadLocal<Edge[]> distance = ThreadLocal.withInitial(() -> new Edge[this.sizeOfIndexes]);

        for (int x = 0; x < sizeOfIndexes; x++) {
            if (x == calcIndexValue(start, mat)) {
                distance.get()[x] = new Edge(x, 0);
                parentMap.get().put(calcIndexByVal(x, mat), null);
            } else {
                distance.get()[x] = new Edge(x, Integer.MAX_VALUE);
            }
            pq.get().add(distance.get()[x]);
        }
        while (!pq.get().isEmpty()) {
            Edge edge = pq.get().poll();
            Index currentIndex = this.calcIndexByVal(edge.getIndex(), mat);
            for (Index idx : mat.getNeighbors(currentIndex)) {
                int nextVal = calcIndexValue(idx, mat);
                if (!check.get()[nextVal] && distance.get()[nextVal].getWeight() >=
                        distance.get()[edge.getIndex()].getWeight() + mat.getValue(idx)) {
                    distance.get()[nextVal].setWeight(distance.get()[edge.getIndex()].getWeight() + mat.getValue(idx));
                    if(parentMap.get().containsKey(idx)){
                        parentMap.get().get(idx).add(currentIndex);
                    }
                    else{
                        parentMap.get().put(idx,new LinkedList<>());
                        parentMap.get().get(idx).add(currentIndex);
                    }

                    pq.get().remove(distance.get()[nextVal]);
                    pq.get().add(distance.get()[nextVal]);
                }

            }
            check.get()[edge.getIndex()] = true;
        }
        // change the reverse parent map to be List<Index> and return
        // List<List<Index>>
        List<List<Index>> path = new ArrayList<>();
        List<Index> parentIndexes = parentMap.get().get(end);
        System.out.println("");
        for(Index idx: parentIndexes){
            findAllPaths(idx);
        }
//        for()
//        for(Index index: parentIndexes){
//            List<Index> singlePath = new ArrayList<>();
//                while(!stopLoop){
//                    parentForEach = parentMap.get().get(index)
//                    singlePath.add()
//                }
//                path.add()
////                path.add(end);
////                end = new Index(index);
////                parentIndex = parentMap.get().get(end);
//
//            }
        return path;
    }

    private List<List<Index>> findAllPaths(ThreadLocal<Map<Index, List<Index>>> map, Index idx, List<List<Index>> path) {
        if (map.get().get(idx) == null)
            return path;
        if (map.get().get(idx).size() > 1){
            List<Index> newPath = new LinkedList<>();

            for(Index neighbor: map.get().get(idx))  {
                newPath.add(neighbor);
                findAllPaths(map, neighbor, newPath);
            }
        } else {

        }
        return path;
    }
//        path.add(end);
//        System.out.println(Arrays.toString(distance));
//        Collections.reverse(path);
//        for (int i = 0; i < path.size(); i++) {
//            System.out.print(path.get(i) + " ");
//        }



    public static void main(String[] args) {
        int[][] mat = {
                {10,  10,  10},
                {10,  300, 10},
                {10,  10,   5}
                };
        Matrix matrix = new Matrix(mat);
        LightestPathsDijkImpl dijkstra_pq = new LightestPathsDijkImpl();
        dijkstra_pq.distDijkstra(matrix, new Index(0,0), new Index(2,2));
    }
}
