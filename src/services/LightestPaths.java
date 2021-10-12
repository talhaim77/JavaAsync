package services;

import model.Index;
import model.Matrix;
import tmp.TraversableWeightedMatrix;

import java.util.Collection;
import java.util.List;

public interface LightestPaths<T> {

    /**
     * Run algorithm of dijkstra to find the lightest path from a source Index
     * to Destination Index in 2nd Integer Array.
     * @param
     */
    Collection<List<T>> runAlgo(TraversableWeightedMatrix matrix,Index start, final Index end);
}
