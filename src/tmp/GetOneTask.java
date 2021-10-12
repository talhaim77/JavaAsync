//package tasks;
//
//import Model.Index;
//import Model.Matrix;
//import Model.Traversable;
//import Model.TraversableMatrix;
//import tasks.Assignments1;
//import services.LightestPaths;
//
//import java.io.IOException;
//import java.io.ObjectInput;
//import java.util.List;
//
//public class GetOneTask implements Task{
//
//    public static final String T_NAME = "Task-1";
//    private final TraversableMatrix traversableMatrix;
//
//    public GetOneTask(TraversableMatrix traversableMatrix) {
//        this.traversableMatrix = traversableMatrix;
//    }
//
//    @Override
//    public Object run(ObjectInput objectInput) throws IOException, ClassNotFoundException {
//        int[][] primitiveMatrix = (int[][]) objectInput.readObject();
//        Index source = (Index) objectInput.readObject();
//        return this.getOnes(primitiveMatrix, source);
//    }
//
//    private List<List<Index>> getOnes(int[][] primitiveMatrix, Index source) {
//        this.traversableMatrix.setMatrix(new Matrix(primitiveMatrix));
//        this.traversableMatrix.setStartIndex(new Index(0 ,0));
//        return this.traversableMatrix.runAlgo(this.traversableMatrix, source);
//    }
//
//
//    @Override
//    public String getKey() {
//        return T_NAME;
//    }
//
//}
