package tmp;

import java.util.List;
import model.*;
import services.ThreadLocalBFS;

public class Demo02 {
    public static void main(String[] args) {
        int[][] source = {
//                {1, 1, 1},
//                {0, 1, 1},
//                {0, 1, 1},

//                {0, 0, 1, 1},
//                {1, 1, 1, 1},
//                {1, 1, 1, 0},
//                {1, 0, 1, 0}


                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 0, 1, 1, 0, 1, 0},
                {0, 1, 0, 1, 1, 0, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0},

        };
        TraversableMatrix tMatrix = new TraversableMatrix(new Matrix(source));
        System.out.println(tMatrix);
        tMatrix.setStartIndex(new Index(0 ,0));
        ThreadLocalBFS<Index> localBFS = new ThreadLocalBFS<>();
        List<List<Index>> path = localBFS.traverse(tMatrix, new Index(2 ,4), new Index(5,1));
        for (List<Index> singles: path) {
            System.out.println(singles);
        }

    }
}
