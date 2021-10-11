import model.Index;
import model.TraversableMatrix;
import tasks.LightestPathTask;
import tasks.GetOnesTask;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("127.0.0.1", 8010);
        System.out.println("Created client Socket");

        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream toServer = new ObjectOutputStream(outputStream);
        ObjectInputStream fromServer = new ObjectInputStream(inputStream);
        //         sending #1 matrix
        int[][] primitiveMatrix = {
                {1, 0, 0},
                {1, 0, 1},
                {0, 1, 1},
        };
        toServer.writeObject(GetOnesTask.T_NAME);
        toServer.writeObject(primitiveMatrix);
        toServer.writeObject(new Index(0, 0));

//         should print [(1,2), (2,2), (0,0), (1,0), (2,0)]
        System.out.println("Task 1 output from server: ");
        System.out.println(fromServer.readObject());

        int[][] primitiveMatrix2 = {
                {100, 100, 100},
                {500, 900, 300}
        };
        toServer.writeObject(LightestPathTask.T_NAME);
        toServer.writeObject(primitiveMatrix2);
        toServer.writeObject(new Index(1, 0));
        toServer.writeObject(new Index(1, 2));
        // output should be [ ... ]
        System.out.println("Task 4 output from server: ");
        System.out.println(fromServer.readObject());

//        toServer.writeObject("stop");
    }
}