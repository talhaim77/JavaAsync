package tasks;

import java.io.IOException;
import java.io.ObjectInput;

public interface Task {

    String getKey();

    Object run(ObjectInput objectInput) throws IOException, ClassNotFoundException;
}
