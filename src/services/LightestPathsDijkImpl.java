//package services;
//
//import model.Index;
//import model.Matrix;
//
//import java.util.*;
//
//public class LightestPathsDijkImpl implements LightestPaths {
//    private int n;
//    private List<Edge>[] graph;
//
//    public LightestPathsDijkImpl() {
//        graph = new ArrayList[n];
//        for (int x = 0; x < n; x++) {
//            graph[x] = new ArrayList<>();
//        }
//    }
//
//    public int calcIndexValue(Index idx, Matrix m) {
//        int row = idx.getRow();
//        int col = idx.getColumn();
//        return row * (m.getPrimitiveMatrix()[0].length) + col;
//    }
//
//    public Index calcIndexByVal(int val, Matrix m) {
//        int colSize = m.getPrimitiveMatrix()[0].length;
//        return new Index((val / colSize), (val % colSize));
//    }
//
//    private List<Index> distDijkstra(Matrix mat, Index start, Index end) {
//        this.n = mat.getAll().size();
//        PriorityQueue<Edge> pq = new PriorityQueue<>();
//        boolean[] check = new boolean[n];
//        Map<Index, Index> parentMap = new HashMap<>();
//        Edge[] distance = new Edge[n];
//
//        for (int x = 0; x < n; x++) {
//            if (x == calcIndexValue(start, mat)) {
//                distance[x] = new LightestPathsDijkImpl.Edge(x, 0);
//                parentMap.put(calcIndexByVal(x, mat), null);
//            } else {
//                distance[x] = new LightestPathsDijkImpl.Edge(x, Integer.MAX_VALUE);
//            }
//            pq.add(distance[x]);
//        }
//        while (!pq.isEmpty()) {
//            LightestPathsDijkImpl.Edge edge = pq.poll();
//            Index currentIndex = this.calcIndexByVal(edge.v, mat);
//            for (Index idx : mat.getNeighbors(currentIndex)) {
//                int nextVal = calcIndexValue(idx, mat);
//                if (!check[nextVal] && distance[nextVal].weight > distance[edge.v].weight + mat.getValue(idx)) {
//                    distance[nextVal].weight = distance[edge.v].weight + mat.getValue(idx);
//                    parentMap.put(idx, currentIndex);
//                    pq.remove(distance[nextVal]);
//                    pq.add(distance[nextVal]);
//                }
//            }
//            check[edge.v] = true;
//        }
//        List<Index> path = new ArrayList<>();
//        Index parentIndex = parentMap.get(end);
//        while (parentIndex != null) {
//            path.add(end);
//            end = new Index(parentIndex);
//            parentIndex = parentMap.get(end);
//        }
//        path.add(end);
////        System.out.println(Arrays.toString(distance));
//        Collections.reverse(path);
////        for (int i = 0; i < path.size(); i++) {
////            System.out.print(path.get(i) + " ");
////        }
////        System.out.println(Arrays.toString(path));
//        return path;
//    }
//
//    @Override
//    public List<Index> runAlgo(Matrix matrix, Index start, Index end) {
//        return this.distDijkstra(matrix, start, end);
//    }
//
//    private class Edge implements Comparable<Edge> {
//        int v, weight;
//
//        public Edge(int v, int weight) {
//            this.v = v;
//            this.weight = weight;
//        }
//
//        @Override
//        public int compareTo(Edge o) {
//            return Integer.compare(this.weight, o.weight);
//        }
//
//        @Override
//        public String toString() {
//            return weight + "";
//        }
//
//    }
//
//    public static void main(String[] args) {
//        int[][] mat =     new int[][]{
//                new int[]{100, 100, 100},
//                new int[]{500, 900, 300}};
//        Matrix matrix = new Matrix(mat);
//        LightestPathsDijkImpl dijkstra_pq = new LightestPathsDijkImpl();
//        dijkstra_pq.distDijkstra(matrix, new Index(1,0), new Index(1,2));
//    }
//}
