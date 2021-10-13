//package tasks;
//
//import model.Index;
//import model.Matrix;
//import services.LightestPaths;
//
//import java.io.IOException;
//import java.io.ObjectInput;
//import java.util.List;
//
//public class LightestPathTask implements Task{
//    private final LightestPaths lightestPathsAlgo;
//
//    public LightestPathTask(LightestPaths lightestPath) {
//        this.lightestPathsAlgo = lightestPath;
//    }
//
//    @Override
//    public Object run(ObjectInput objectInput) throws IOException, ClassNotFoundException {
//        int[][] primitiveMatrix = (int[][]) objectInput.readObject();
//        Index source = (Index) objectInput.readObject();
//        Index destination = (Index) objectInput.readObject();
//        return getLightestPaths(primitiveMatrix, source, destination);
//    }
//
//    private List<Index> getLightestPaths(int[][] primitiveMatrix, Index start, Index end) {
//        Matrix matrix = new Matrix(primitiveMatrix);
////        return this.lightestPathService.getLightestPaths(matrix, start, end);
//        return this.lightestPathsAlgo.runAlgo(matrix, start, end);
//    }
//
//
//    @Override
//    public String getKey() {
//        return T_NAME;
//    }
//
//}
