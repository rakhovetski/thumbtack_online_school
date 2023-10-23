package net.thumbtack.school.concurrent.fifteen;

import java.util.Arrays;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Consumer extends Thread {
    private final BlockingQueue<Data> queue;

    public Consumer(BlockingQueue<Data> queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            try {
                Data data = queue.take();
                int[] dataArray = data.get();
                if (dataArray == null) {
                    break;
                }
                System.out.println(Arrays.toString(dataArray));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}

class Producer extends Thread {
    private static final Random random = new Random();
    private final Queue<Data> queue;
    private final int count;

    public Producer(BlockingQueue<Data> queue, int count) {
        this.queue = queue;
        this.count = count;
    }

    public void run() {
        for (int i = 0; i < count; i++) {
            int[] dataArray = new int[100];

            for (int j = 0; j < dataArray.length; j++) {
                dataArray[j] = random.nextInt(100);
            }

            queue.add(new Data(dataArray));
        }
    }
}

public class FifteenTask {
    public static void main(String[] args) {
        int consumersCount = 10;
        int producersCount = 10;
        int data = 2;

        BlockingQueue<Data> queue = new LinkedBlockingQueue<>();
        Thread[] consumers = new Thread[consumersCount];
        for (int i = 0; i < consumersCount; i++) {
            consumers[i] = new Consumer(queue);
        }

        Thread[] producers = new Thread[producersCount];
        for (int i = 0; i < producersCount; i++) {
            producers[i] = new Producer(queue, data);
        }

        for (Thread thread : consumers) {
            thread.start();
        }

        for (Thread thread : producers) {
            thread.start();
        }

        for (Thread thread : producers) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < consumersCount; i++) {
            queue.add(new Data(null));
        }

        for (Thread thread : consumers) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Queue size " + queue.size());
    }
}
