package model;


public class DijkNode<T> extends Node {
    protected Integer weight;
    private DijkNode<T> parent;

    public DijkNode(T data, DijkNode<T> discoveredBy, Integer weight) {
        super(data);
        this.parent = discoveredBy;
        this.weight = weight;
    }

    public DijkNode(T data, Integer weight) {
        super(data);
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public DijkNode<T> getParent() {
        return this.parent;
    }
}
