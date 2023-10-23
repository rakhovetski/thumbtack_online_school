package net.thumbtack.school.concurrent.sixteen;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Consumer extends Thread{
    private final BlockingQueue<Task> queue;

    public Consumer(BlockingQueue<Task> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            try {
                Task task = queue.take();
                if (task.getName() == null) {
                    break;
                }
                task.execute();
            }  catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}

class Producer extends Thread {
    private final Queue<Task> queue;
    private final int count;

    public Producer(BlockingQueue<Task> queue, int count) {
        this.queue = queue;
        this.count = count;
    }

    public void run() {
        for (int i = 0; i < count; i++) {
            queue.add(new Task("task"));
        }
    }
}

public class SixteenTask {
    public static void main(String[] args) {
        int consumersCount = 5;
        int producersCount = 5;

        int tasks = 2;

        BlockingQueue<Task> queue = new LinkedBlockingQueue<>();
        Thread[] consumers = new Thread[consumersCount];
        for (int i = 0; i < consumersCount; i++) {
            consumers[i] = new Consumer(queue);
        }

        Thread[] producers = new Thread[producersCount];
        for (int i = 0; i < producersCount; i++) {
            producers[i] = new Producer(queue, tasks);
        }

        startThreads(producers);
        startThreads(consumers);
        joinThreads(producers);

        for (int i = 0; i < consumersCount; i++) {
            queue.add(new Task(null));
        }

        joinThreads(consumers);
        System.out.println("Queue size " + queue.size());
    }

    public static void startThreads(Thread[] threads) {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public static void joinThreads(Thread[] threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
