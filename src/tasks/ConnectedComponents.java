//package tasks;
//
//import java.io.IOException;
//import java.io.ObjectInput;
//
//import model.Index;
//import services.TaskOne;
//import services.TaskOneImpl;
//
//
//public class ConnectedComponents<T> implements Task {
//    public static final String T_NAME = "Task-1";
////    private final services.TaskOne connectedComponents;
//
//
//    public ConnectedComponents() {
//    }
//
//    @Override
//    public String getKey() {
//        return T_NAME;
//    }
//
//    @Override
//    public Object run(ObjectInput objectInput) throws IOException, ClassNotFoundException {
//        int[][] primitiveMatrix = (int[][]) objectInput.readObject();
//        TaskOneImpl<Index> findCC = new TaskOneImpl();
//        return findCC.runAlgo(primitiveMatrix);
//    }
//
//}