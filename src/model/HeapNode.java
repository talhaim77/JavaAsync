package model;

public class HeapNode {

    Index vertex;
    int distance;

    public HeapNode(Index vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }
}
