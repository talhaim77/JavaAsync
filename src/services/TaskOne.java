package services;

import model.Index;
import model.Matrix;

import java.util.List;

public interface TaskOne<T> {

    List<List<T>> runAlgo(int[][] primitiveMatrix);
}
