package server;

import handler.IHandler;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class TcpServer {

    private final int port; // initialize in constructor
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
        new Thread( () -> {
            threadPool = new ThreadPoolExecutor(3, 5, 10, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>() );
            try {
                ServerSocket serverSocket = new ServerSocket(this.port); // bind
                /*
                listen to incoming connection and accept if possible
                be advised: accept is a blocking call
                 */
                while(!stopServer){
                    Socket serverClientConnection = serverSocket.accept();
                    // define a task and submit to our threadPool
                    Runnable clientHandling = ()->{
                        System.out.println("Server: Handling a client");
                        try {
                            requestHandler.handle(serverClientConnection.getInputStream(),
                                    serverClientConnection.getOutputStream());
                            System.out.println("here");
                            // finished handling client. now close all streams
                            serverClientConnection.getInputStream().close();
                            serverClientConnection.getOutputStream().close();
                            serverClientConnection.close();
                        } catch (IOException | ClassNotFoundException ioException) {
                            System.err.println(ioException.getMessage());
                        }
                    };
                    threadPool.execute(clientHandling);
                }

                serverSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }).start();
    }



    public void stop(){
        if(!stopServer){
            stopServer = true;
            if(threadPool!=null)
                threadPool.shutdown();
        }

    }



    public static void main(String[] args) {
        TcpServer webServer = new TcpServer(8010);
        webServer.run(new MatrixIHandler());
    }
}
