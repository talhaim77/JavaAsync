package model;

public class IndexStatus {
    private Index prev;
    private Integer dist;
    private transient boolean source;
    private int minCost;
    private transient volatile boolean visited = false;

    public IndexStatus(Index parent, Integer dist) {
        this.prev = parent;
        this.dist = dist;
    }

    public boolean isSource(){
        return this.source;
    }

    public boolean isVisited(){
        return this.visited;
    }
    public void resetIndex(){
        this.visited = false;
    }
}
