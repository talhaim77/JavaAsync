package tasks;

import model.Index;
import model.IndexStatus;
import model.Matrix;

import java.util.List;
import java.util.Map;

public interface ILightestPath {
    List<Index> getLightestPath(Matrix matrix, Index source, Index dest, Map<Index, IndexStatus> dataMap);
}
