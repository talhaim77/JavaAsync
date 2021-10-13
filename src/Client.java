import model.Index;
import services.TaskOneImpl;
import services.ThreadLocalBFS;
import tasks.Submarines;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Client {


    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("127.0.0.1", 8010);
        System.out.println("Created client Socket");

        ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());

        Index startIdx = new Index(0,1);
        Index endIdx = new Index(0,3);

        //         sending #1 matrix
        int[][] primitiveMatrix = {
//                {1, 0, 0, 1},
//                {1, 1, 1, 1},
//                {0, 0, 1, 1},
//                {0, 0, 1, 1},

//                {0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 1, 1, 1, 1, 1, 1, 0},
//                {0, 1, 0, 1, 1, 0, 1, 0},
//                {0, 1, 0, 1, 1, 0, 1, 0},
//                {1, 1, 1, 1, 1, 1, 1, 0},
//                {0, 1, 1, 1, 1, 0, 1, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0},

// Task3
                {1, 1, 0, 1, 1},
                {1, 1, 0, 1, 1},
                {1, 1, 0, 1, 1},
                {1, 1, 0, 1, 1},
                {1, 1, 0, 1, 1},
        };
//        send "Task-1" command then write 2d array to socket
        toServer.writeObject(TaskOneImpl.T_NAME);
        toServer.writeObject(primitiveMatrix);
        printTask(fromServer, "The connected components of matrix is:");

        //send "Task-2" command then write 2d array,
        // start,end Indexes to socket
        toServer.writeObject(ThreadLocalBFS.T_NAME);
        toServer.writeObject(primitiveMatrix);
        toServer.writeObject(startIdx);
        toServer.writeObject(endIdx);
        printTask(fromServer, "The shortest paths using BFS is: ");

        //send "Task-3" command then write 2d array
        toServer.writeObject(Submarines.T_NAME);
        toServer.writeObject(primitiveMatrix);
        printTask(fromServer,"The submarine size is: ");

        toServer.writeObject("stop");
        System.out.println("client: Close all streams");
        fromServer.close();
        toServer.close();
        socket.close();
        System.out.println("client: Closed operational socket");
    }

    public static void printTask(ObjectInputStream fromServer,String str) throws IOException, ClassNotFoundException {
            List<List<Index>> result =
                    new LinkedList((List<Index>) fromServer.readObject());

            if(result.size() != 0){
                System.out.println(str + result.size());
                for (List<Index> singles: result) {
                    System.out.println(singles);
                }
            }
            else{
                System.out.println(str + "is null");
            }
    }

}