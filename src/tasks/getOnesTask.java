package tasks;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.*;
import model.*;
import services.FindOnes;


public class GetOnesTask<T> implements Task {
    public static final String T_NAME = "Task-1";
    private final FindOnes findOnes;

    @Override
    public String getKey() {
        return T_NAME;
    }

    @Override
    public Object run(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        int[][] primitiveMatrix = (int[][]) objectInput.readObject();
        Index source = (Index) objectInput.readObject();
        return getOnesMatrix(primitiveMatrix, source);
    }

    private Object getOnesMatrix(int[][] primitiveMatrix, Index source) {
//        TraversableMatrix tMatrix = new TraversableMatrix(new Matrix(primitiveMatrix));
//        tMatrix.setStartIndex(new Index(0, 0));
//        GetOnesTask<Index> localDFS = new GetOnesTask<>();
//        List<List<Index>> listOfComponents = localDFS.traverse(tMatrix);
        return this.findOnes.runAlgo(primitiveMatrix, source);
    }
}