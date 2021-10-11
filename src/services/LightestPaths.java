package services;

import model.Index;
import model.Matrix;

import java.util.List;

public interface LightestPaths {

    /**
     * Run algorithm of dijkstra to find the lightest path from a source Index
     * to Destination Index in 2nd Integer Array.
     * @param
     */
    List<Index> runAlgo(final Matrix matrix,
                                  final Index start,
                                  final Index end);
}
