package tmp;

import java.util.List;
import model.*;
public class Demo01 {
    public static void main(String[] args) {
        int[][] source = {
                {1, 0, 0},
                {1, 0, 1},
                {0, 1, 1},
//                {1, 0, 1},
//                {1, 0, 1},
//                {1, 0, 1},

//                {1, 1, 0},
//                {0, 1, 1},
//                {0, 0, 0},


//                {1, 0, 1, 0},
//                {0, 0, 0, 1},
//                {0, 0, 1, 0},
//                {0, 0, 0, 1},

//                {0, 0, 1, 1},
//                {1, 0, 0, 1},
//                {1, 1, 0, 0},
//                {1, 0, 1, 0}

//                {0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 1, 1, 1, 1, 1, 1, 0},
//                {0, 1, 0, 1, 1, 0, 1, 0},
//                {0, 1, 0, 1, 1, 0, 1, 0},
//                {1, 1, 1, 1, 1, 1, 1, 0},
//                {0, 1, 1, 1, 1, 0, 1, 0},

//                {0, 0, 1, 0, 0, 1, 1, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 1, 1, 1, 1, 1, 0},
//                {0, 1, 0, 1, 0, 0, 0, 0},
//                {0, 1, 0, 1, 1, 0, 1, 0},
//                {1, 1, 1, 1, 1, 0, 1, 0},
//                {0, 1, 1, 1, 1, 0, 1, 0},


//                {0, 0, 1, 0, 0, 1, 1, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0},
//                {1, 0, 0, 0, 1, 1, 0, 1},
//                {0, 1, 0, 1, 0, 0, 0, 1},
//                {1, 0, 0, 0, 0, 0, 1, 1},
//                {1, 0, 1, 0, 1, 0, 0, 1},
//                {1, 0, 0, 1, 1, 0, 0, 1},


        };
        TraversableMatrix tMatrix = new TraversableMatrix(new Matrix(source));
//        System.out.println(tMatrix);
        tMatrix.setStartIndex(new Index(0 ,0));
        Assignments1<Index> localDFS = new Assignments1<>();
        List<List<Index>> listOfComponents = localDFS.traverse(tMatrix);
        for (List<Index> singles: listOfComponents) {
            System.out.println(singles);
        }

    }
}
