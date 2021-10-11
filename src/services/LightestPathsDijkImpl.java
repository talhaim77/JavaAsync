package services;

import model.Index;
import model.Matrix;

import java.util.*;

public class LightestPathsDijkImpl implements LightestPaths {
    private int n;
    private List<Edge>[] graph;

    @Override
    public List<Index> runAlgo(Matrix matrix, Index start, Index end) {
        return this.distDijkstra(matrix, start, end);
    }

    private class Edge implements Comparable<Edge> {
        int v, weight;

        public Edge(int v, int weight) {
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }

        @Override
        public String toString() {
            return weight + "";
        }

    }

    public LightestPathsDijkImpl() {
        graph = new ArrayList[n];
        for (int x = 0; x < n; x++) {
            graph[x] = new ArrayList<>();
        }
    }

    public int calcIndexVal(Index idx, Matrix m) {
        int row = idx.getRow();
        int col = idx.getColumn();
        return row * (m.getPrimitiveMatrix()[0].length) + col;
    }

    public Index calcIndexByVal(int val, Matrix m) {
        int colSize = m.getPrimitiveMatrix()[0].length;
        return new Index((val / colSize), (val % colSize));
    }

    private List<Index> distDijkstra(Matrix m, Index start, Index end) {
        this.n = m.getAll().size();
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        boolean[] check = new boolean[n];
        Map<Index, Index> parentMap = new HashMap<>();
        Edge[] distance = new Edge[n];

        for (int x = 0; x < n; x++) {
            if (x == calcIndexVal(start, m)) {
                distance[x] = new LightestPathsDijkImpl.Edge(x, 0);
                parentMap.put(calcIndexByVal(x, m), null);
            } else {
                distance[x] = new LightestPathsDijkImpl.Edge(x, Integer.MAX_VALUE);
            }
            pq.add(distance[x]);
        }
        while (!pq.isEmpty()) {
            LightestPathsDijkImpl.Edge edge = pq.poll();
            Index temp = this.calcIndexByVal(edge.v, m);
            for (Index idx : m.getNeighbors(temp)) {
                int nextVal = calcIndexVal(idx, m);
                if (!check[nextVal] && distance[nextVal].weight > distance[edge.v].weight + m.getValue(idx)) {
                    distance[nextVal].weight = distance[edge.v].weight + m.getValue(idx);
                    parentMap.put(idx, temp);
                    pq.remove(distance[nextVal]);
                    pq.add(distance[nextVal]);
                }
            }
            check[edge.v] = true;
        }
        List<Index> path = new ArrayList<>();
        Index parentIndex = parentMap.get(end);
        while (parentIndex != null) {
            path.add(end);
            end = new Index(parentIndex);
            parentIndex = parentMap.get(end);
        }
        path.add(end);
//        System.out.println(Arrays.toString(distance));
        Collections.reverse(path);
//        for (int i = 0; i < path.size(); i++) {
//            System.out.print(path.get(i) + " ");
//        }
//        System.out.println(Arrays.toString(path));
        return path;
    }

//    public void put(int start, int end, int weight) {
//        graph[start - 1].add(new Edge(end - 1, weight));
//    }


    //    public void putDouble(int start, int end, int weight) {
//        graph[start - 1].add(new Edge(end - 1, weight));
//        graph[end - 1].add(new Edge(start - 1, weight));
//    }

    public static void main(String[] args) {
        int[][] mat =     new int[][]{
                new int[]{100, 100, 100},
                new int[]{500, 900, 300}};
        Matrix matrix = new Matrix(mat);
        LightestPathsDijkImpl dijkstra_pq = new LightestPathsDijkImpl();
        dijkstra_pq.distDijkstra(matrix, new Index(1,0), new Index(1,2));
    }
}
