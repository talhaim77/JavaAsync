package server;

import handler.TaskHandler;
import model.Index;
import services.FindOnesImpl;
import services.LightestPathsDijkImpl;
import tasks.GetOnesTask;
import tasks.LightestPathTask;

import java.io.IOException;
import java.util.Arrays;

public class ServerRunnerImpl implements ServerRunner {
    static final int PORT = 8010;

    @Override
    public void run() {
        System.out.println("Listening server port:" + PORT);
        TcpServer server = new TcpServer(PORT);
        LightestPathsDijkImpl findLightestPaths = new LightestPathsDijkImpl();
        FindOnesImpl<Index>  findOnes = new FindOnesImpl();
//        TraversableMatrix tMatrix = new TraversableMatrix(new Matrix(null));

        try {
            System.out.println("Server is running");
            server.run(createTaskHandler(findLightestPaths, findOnes));

        } catch (TaskHandler.StopRequest e) {
            System.out.println("Stop signal was sent");
            // close the service (should impl Closeable, executorService in Class LightestPathsDijkImpl)
            try {
                server.close();
            } catch (IOException ignored) {
            }

        } catch (Exception e) {
            System.err.println("Encountered an error!");
            e.printStackTrace();
        } finally {
            System.out.println("Server stopped");
        }
    }


    private TaskHandler createTaskHandler(LightestPathsDijkImpl lightestPathsDijkstra, FindOnesImpl findOnes){
        return new TaskHandler(Arrays.asList(
                new LightestPathTask(lightestPathsDijkstra),
                new GetOnesTask<Index>(findOnes)
                ));
    }

}
