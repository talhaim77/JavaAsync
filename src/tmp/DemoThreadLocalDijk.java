package tmp;

import model.DijkNode;
import model.Index;
import model.Matrix;
import services.ThreadLocalLightestPaths;

import java.io.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class DemoThreadLocalDijk {
    private boolean doWork = true;
    private final Index index = new Index(0, 0);
    private Matrix matrix;
    private Matrix weightedMatrix;

    /**
     *
     * @param matrix from client weighted matrix
     * @param source start
     * @param dest end
     * @return Collection<List<Index>>
     */
    private Collection<List<Index>> task4(Matrix matrix,Index source,Index dest) {

        // Get traversable
        TraversableWeightedMatrix traversableWeightedMatrix = new TraversableWeightedMatrix(matrix);

        // Initialize origin index
        traversableWeightedMatrix.setStartIndex(source);

        return new ThreadLocalLightestPaths<Index>().traverse(traversableWeightedMatrix, new DijkNode<>(dest,Integer.MAX_VALUE));

    }
}
