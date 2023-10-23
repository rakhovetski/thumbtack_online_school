package net.thumbtack.school.concurrent.seventeen;

import net.thumbtack.school.concurrent.sixteen.Executable;
import net.thumbtack.school.concurrent.sixteen.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Producer extends Thread {
    private String producerName;
    private MultiTask multiTask;
    private int count;
    private BlockingQueue<MultiTask> multiTaskBlockingQueue;
    private BlockingQueue<Stage> stageBlockingQueue;

    public Producer(String producerName, MultiTask multiTask, BlockingQueue<MultiTask> multiTaskBlockingQueue,
                    int count, BlockingQueue<Stage> stageBlockingQueue) {
        this.producerName = producerName;
        this.multiTask = multiTask;
        this.count = count;
        this.multiTaskBlockingQueue = multiTaskBlockingQueue;
        this.stageBlockingQueue = stageBlockingQueue;
    }

    public void run() {
        System.out.println(producerName + " started");
        try {
            for (int i = 0; i < count; i++) {
                multiTaskBlockingQueue.put(multiTask);
                stageBlockingQueue.put(Stage.TASK_STARTED);
                Thread.sleep(200);
            }
            stageBlockingQueue.put(Stage.PRODUCER_FINISHED);
            System.out.println(producerName + " finished");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

class Consumer extends Thread {
    private String consumerName;
    private BlockingQueue<MultiTask> multiTaskBlockingQueue;
    private BlockingQueue<Stage> stageBlockingQueue;

    public Consumer(String consumerName, BlockingQueue<MultiTask> multiTaskBlockingQueue, BlockingQueue<Stage> stageBlockingQueue) {
        this.consumerName = consumerName;
        this.multiTaskBlockingQueue = multiTaskBlockingQueue;
        this.stageBlockingQueue = stageBlockingQueue;
    }

    public void run() {
        System.out.println(consumerName + " started");
        while (true) {
            try {
                MultiTask task = multiTaskBlockingQueue.take();
                if (task.getName() == null) {
                    System.out.println(consumerName + " finished");
                    break;
                }
                if (!task.getList().isEmpty()) {
                    task.getList().remove(0).execute();
                }
                if (!task.getList().isEmpty()) {
                    multiTaskBlockingQueue.put(task);
                } else {
                    stageBlockingQueue.put(Stage.TASK_FINISHED);
                }
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}

public class SeventeenTask {
    public static void main(String[] args) {
        int queueSize = 100;
        int countProducerFinished = 0;
        int countTaskFinished = 0;
        int countTaskStarted = 0;

        BlockingQueue<MultiTask> multiTaskBlockingQueue = new ArrayBlockingQueue<>(queueSize);
        BlockingQueue<Stage> stageBlockingQueue = new ArrayBlockingQueue<>(queueSize);
        Producer[] producers = new Producer[5];
        Consumer[] consumers = new Consumer[5];

        List<Executable> list = new ArrayList<>();
        Executable task1 = new Task("First");
        Executable task2 = new Task("Second");
        Executable task3 = new Task("Third");

        Collections.addAll(list, task1, task2, task3);

        for (int i = 0; i < producers.length; i++) {
            producers[i] = new Producer("Producer " + i, new MultiTask("Task " + i, list), multiTaskBlockingQueue, 3, stageBlockingQueue);
        }
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new Consumer("Consumer " + i, multiTaskBlockingQueue, stageBlockingQueue);
        }
        for (int i = 0; i < consumers.length; i++) {
            producers[i].start();
            consumers[i].start();
        }
        try {
            while (producers.length != countProducerFinished || countTaskStarted != countTaskFinished) {
                Stage stage = stageBlockingQueue.take();
                if (stage.equals(Stage.PRODUCER_FINISHED)) {
                    countProducerFinished++;
                }
                if (stage.equals(Stage.TASK_FINISHED)) {
                    countTaskFinished++;
                }
                if(stage.equals(Stage.TASK_STARTED)) {
                    countTaskStarted++;
                }
            }
            for (int i = 0; i < consumers.length; i++) {
                multiTaskBlockingQueue.put(new MultiTask(null, null));
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
