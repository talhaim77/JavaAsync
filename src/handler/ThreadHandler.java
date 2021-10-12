package handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadHandler {

    private static volatile ThreadHandler handler;
    private final static ReadWriteLock lock = new ReentrantReadWriteLock();
    int numOfThreads = Runtime.getRuntime().availableProcessors();
    private final ExecutorService threadPool = new ThreadPoolExecutor(numOfThreads, numOfThreads,
            60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    public ThreadHandler() {
    }

    public static ThreadHandler getInstance() {
        {
            if(handler == null)
            {
                lock.writeLock().lock();
                if(handler == null)
                {
                    handler = new ThreadHandler();
                    lock.writeLock().unlock();
                }
            }
            return handler;
        }
    }

    public void Shutdown()
    {
        threadPool.shutdown();
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }
}
