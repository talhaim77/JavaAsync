package handler;

import tasks.Task;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TaskHandler implements IHandler{
    private final Map<String, Task> tasks;

    public TaskHandler(List<Task> tasks) {
        this.tasks = tasks
                .stream()
                .filter(t -> !t.getKey().equalsIgnoreCase("stop"))
                .collect(Collectors.toMap(t -> t.getKey(), Function.identity()));
    }

    @Override
    public void close() throws IOException {
        tasks
                .values()
                .stream()
                .filter(task -> task instanceof Closeable)
                .forEach(task -> closeTask((Closeable) task));
    }

    @Override
    public void handle(InputStream fromClient, OutputStream toClient)
            throws IOException, ClassNotFoundException {
        // In order to read either objects or primitive types we can use ObjectInputStream
        ObjectInputStream objectInputStream = new ObjectInputStream(fromClient);
        // In order to write either objects or primitive types we can use ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(toClient);

        boolean doWork = true;
        while (doWork) {
            String readObjectKey = objectInputStream.readObject().toString();
            if (!readObjectKey.equals("stop")) {
//                System.out.println(tasks.containsKey(readObjectKey));
                if (tasks.containsKey(readObjectKey)) {
                    Task task = tasks.get(readObjectKey);
                    objectOutputStream.writeObject(task.run(objectInputStream));
                } else {
                    objectOutputStream.writeObject(new TaskError(readObjectKey));
                }
            } else {
                doWork = false;
            }
        }
        throw new StopRequest();
    }
    private void closeTask(Closeable task) {
        try {
            task.close();
        } catch (IOException e) {
            System.err.println("Error occurs while closing task: ");
            e.printStackTrace();
        }
    }

    public static class TaskError extends RuntimeException {
        public TaskError(String key) {
            super("no such task " + key);
        }
    }
    public static class StopRequest extends RuntimeException {

    }
}



