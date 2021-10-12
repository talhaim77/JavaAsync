package tasks;

import model.Index;
import model.Matrix;
import services.LightestPaths;
import tmp.TraversableWeightedMatrix;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.Collection;
import java.util.List;

public class LightestPathTask<T> implements Task{
    public static final String T_NAME = "Lightest-Path-Dijkstra";
    private final LightestPaths lightestPathsAlgo;

    public LightestPathTask(LightestPaths lightestPath) {
        this.lightestPathsAlgo = lightestPath;
    }

    @Override
    public Object run(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        int[][] primitiveMatrix = (int[][]) objectInput.readObject();
        Index source = (Index) objectInput.readObject();
        Index destination = (Index) objectInput.readObject();
        return getLightestPaths(primitiveMatrix, source, destination);
    }

    private Collection<List<T>> getLightestPaths(int[][] primitiveMatrix, Index start, Index end) {
        Matrix matrix = new Matrix(primitiveMatrix);
        TraversableWeightedMatrix traversableWeightedMatrix = new TraversableWeightedMatrix(matrix);
        traversableWeightedMatrix.setStartIndex(start);
//        return this.lightestPathService.getLightestPaths(matrix, start, end);
        return this.lightestPathsAlgo.runAlgo(traversableWeightedMatrix, start, end);
    }


    @Override
    public String getKey() {
        return T_NAME;
    }

}
