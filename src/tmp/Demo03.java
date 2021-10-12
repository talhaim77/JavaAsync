package tmp;

import model.Index;
import model.Matrix;
import model.TraversableMatrix;
import tasks.Submarines;

import java.util.List;

public class Demo03 {
    public static void main(String[] args) {
        int[][] source = {

                {1, 1, 0, 1, 1},
                {1, 1, 0, 1, 1},
                {1, 1, 0, 1, 1},
                {1, 1, 0, 1, 1},
                {1, 1, 0, 1, 1},
//                {1, 1, 1},
//                {1, 0, 1},
//                {1, 1, 1},

//                {1, 1, 0, 1, 1},
//                {1, 1, 0, 1, 1},
//                {1, 1, 0, 1, 1},
//                {1, 1, 0, 1, 1},
//                {1, 1, 0, 1, 1},
//                {1, 1, 1, 0},
//                {1, 0, 1, 0}


//                {0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 1, 1, 1, 1, 1, 1, 0},
//                {0, 1, 0, 1, 1, 0, 1, 0},
//                {0, 1, 0, 1, 1, 0, 1, 0},
//                {1, 1, 1, 1, 1, 1, 1, 0},
//                {0, 1, 1, 1, 1, 0, 1, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0},
//                {1, 1, 1, 0},
//                {1, 1, 1, 0},
//                {1, 0, 1, 0},
//                {1, 1, 1, 0},


        };
        TraversableMatrix tMatrix = new TraversableMatrix(new Matrix(source));
        System.out.println(tMatrix);
        tMatrix.setStartIndex(new Index(0, 0));
        Submarines<Index> subMarine = new Submarines<>();
        List<List<Index>> path = subMarine.traverse(tMatrix);
        System.out.println("Number of valid submarines: " + path.size());
        for (List<Index> singles : path) {
            System.out.println(singles);
        }
    }
}

