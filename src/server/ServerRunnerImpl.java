package server;

import handler.TaskHandler;
import services.LightestPathsDijkImpl;
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

//        TraversableMatrix tMatrix = new TraversableMatrix(new Matrix(null));

        try {
            System.out.println("Server is running");
            server.run(createTaskHandler(findLightestPaths));
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


    private TaskHandler createTaskHandler(LightestPathsDijkImpl lightestPathsDijkstra){
        return new TaskHandler(Arrays.asList(
                new LightestPathTask(lightestPathsDijkstra)
                ));
    }

}
