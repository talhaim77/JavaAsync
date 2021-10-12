package services;

import model.Index;
import model.Matrix;

import java.util.List;

public interface FindOnes<T> {

    List<List<T>> runAlgo(int[][] primitiveMatrix,
                        final Index source);
}
