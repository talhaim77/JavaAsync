package server;

import handler.IHandler;
import handler.TaskHandler;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class TcpServer implements Closeable {

    private final Integer port; // initialize in constructor
    private final Object mutex = new Object();
    private volatile boolean stopServer; // volatile - stopServer variable is saved in RAM memory
    private ThreadPoolExecutor threadPool; // handle each client in a separate thread
    private IHandler requestHandler; // what is the type of clients' tasks

    public TcpServer(int port){
        this.port = port;
        this.threadPool = null;
        requestHandler = null;
        stopServer = false;
    }

    public void run(IHandler concreteHandler) {
        this.requestHandler = concreteHandler;
        new Thread(this::acceptRequests).start();
        try {
            synchronized (mutex) {
                mutex.wait();
            }
            throw new TaskHandler.StopRequest();
        } catch (InterruptedException ignored) {
        }
    }

    @Override
    public void close() throws IOException {
        stopServer = true;
        new Socket("localhost", port).close(); // break request accept wait
        if (threadPool != null) {
            threadPool.shutdownNow();
        }

        requestHandler.close();
    }

    public void supportClients(IHandler handler) {
        this.requestHandler = handler;
        /*
         A server can do many things. Dealing with listening to clients and initial
         support is done in a separate thread
         */
        Runnable mainServerLogic = () -> {
            this.threadPool = new ThreadPoolExecutor(3,5,
                    10, TimeUnit.SECONDS, new LinkedBlockingQueue());
            /*
            2 Kinds of sockets
            Server Socket - a server sockets listens and wait for incoming connections
            1. server socket binds to specific port number
            2. server socket listens to incoming connections
            3. server socket accepts incoming connections if possible

            Operational socket (client socket)
             */
            try {
                ServerSocket serverSocket = new ServerSocket(this.port); // bind
                /*
                listen to incoming connection and accept if possible
                be advised: accept is a blocking call
                TODO: wrap in another thread
                 */
                while(!stopServer){
                    Socket serverClientConnection = serverSocket.accept();
                    // define a task and submit to our threadPool
                    Runnable clientHandling = ()->{
                        System.out.println("Server: Handling a client");
                        try {
                            requestHandler.handle(serverClientConnection.getInputStream(),
                                    serverClientConnection.getOutputStream());
                        } catch (IOException | ClassNotFoundException ioException) {
                            ioException.printStackTrace();
                        }
                        // terminate connection with client
                        // close all streams
                        try {
                            serverClientConnection.getInputStream().close();
                            serverClientConnection.getOutputStream().close();
                            serverClientConnection.close();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    };
                    threadPool.execute(clientHandling);
                }
                serverSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        };
        new Thread(mainServerLogic).start();
    }


    private void requestHandle(Socket serverToSpecificClient) {
        try {
            requestHandler.handle(serverToSpecificClient.getInputStream(),
                    serverToSpecificClient.getOutputStream());
                    serverToSpecificClient.getInputStream().close();
            serverToSpecificClient.getOutputStream().close();
            serverToSpecificClient.close();
        } catch (TaskHandler.StopRequest e) {
            synchronized (mutex) {
                mutex.notify();
            }
        } catch (IOException | ClassNotFoundException | RuntimeException e) {
            if (!stopServer) {
                System.err.println("Error while handle a client");
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        if(!stopServer){
            stopServer = true;
            if(threadPool!=null)
                threadPool.shutdown();
        }

    }


    private void acceptRequests() {
        //
        threadPool = new ThreadPoolExecutor(2, 5, 10,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        try {

            ServerSocket serverSocket = new ServerSocket(port);
            while (!stopServer) {
                Socket serverToSpecificClient = serverSocket.accept(); // 2 operations: create and bind
            /*
             server will handle each client in a separate thread
             define every client as a Runnable task to execute
             */
                Runnable clientHandling = () -> requestHandle(serverToSpecificClient);
                threadPool.execute(clientHandling);
            }
            serverSocket.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            System.out.println("Stopped acceptance of requests");
        }
    }




    public static void main(String[] args) {
        TcpServer webServer = new TcpServer(8010);
//        webServer.supportClients(new MatrixIHandler());

    }
}
