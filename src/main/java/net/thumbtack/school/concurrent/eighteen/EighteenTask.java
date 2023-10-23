package net.thumbtack.school.concurrent.eighteen;

import net.thumbtack.school.concurrent.seventeen.MultiTask;
import net.thumbtack.school.concurrent.sixteen.Executable;
import net.thumbtack.school.concurrent.sixteen.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Consumer extends Thread {
    private final BlockingQueue<MultiTask> tasksQueue;
    private final BlockingQueue<Event> eventQueue;
    private final Random random;

    public Consumer(BlockingQueue<MultiTask> tasksQueue, BlockingQueue<Event> eventQueue, Random random) {
        this.tasksQueue = tasksQueue;
        this.eventQueue = eventQueue;
        this.random = random;
    }

    public void run() {
        while (true) {
            try {
                MultiTask multiTask = tasksQueue.take();
                List<Executable> list = multiTask.getList();
                if (list == null) {
                    break;
                }
                if (!list.isEmpty()) {
                    int randInt = random.nextInt(list.size());
                    list.remove(randInt).execute();
                    eventQueue.put(new Event(EventType.TASK_FINISHED));

                    if (!list.isEmpty()) {
                        tasksQueue.put(multiTask);
                    }
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}

class Producer extends Thread {
    private final BlockingQueue<MultiTask> tasksQueue;
    private final BlockingQueue<Event> eventQueue;
    private final int multiTask;
    private final Random random;

    public Producer(BlockingQueue<MultiTask> tasksQueue, BlockingQueue<Event> eventQueue,int multiTask, Random random) {
        this.tasksQueue = tasksQueue;
        this.eventQueue = eventQueue;
        this.multiTask = multiTask;
        this.random = random;
    }

    public void run() {
        eventQueue.add(new Event(EventType.PRODUCER_CREATED));
        boolean createNewDeveloper = random.nextBoolean();
        if (createNewDeveloper) {
            createNewDeveloper();
        } else {
            addTask();
        }
        eventQueue.add(new Event(EventType.PRODUCER_FINISHED));
    }

    public void createNewDeveloper() {
        Producer producer = new Producer(tasksQueue, eventQueue, multiTask, random);
        producer.start();
        try {
            producer.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void addTask() {
        for (int i = 0; i < multiTask; i++) {
            LinkedList<Executable> list = new LinkedList<>();
            for (int tasks = 1; tasks < 11; tasks++) {
                eventQueue.add(new Event(EventType.TASK_CREATED));
                String data = String.format("Task%s by %s", tasks, Thread.currentThread().getName());
                list.add(new Task(data));
            }
            tasksQueue.add(new MultiTask("MultiTask", list));
        }
    }
}

public class EighteenTask {
    public static void main(String[] args) throws InterruptedException{
        int consumerCount = 5;
        int producerCount = 5;
        int tasksProducing = 2;

        Random random = new Random();
        BlockingQueue<MultiTask> tasksQueue = new LinkedBlockingQueue<>();
        BlockingQueue<Event> eventsQueue = new LinkedBlockingQueue<>();

        int tasksCreated = 0;
        int tasksFinished = 0;
        int producersCreated = 0;
        int producersFinished = 0;

        Thread[] consumers = new Thread[consumerCount];
        for (int i = 0; i < consumerCount; i++) {
            consumers[i] = new Consumer(tasksQueue, eventsQueue, random);
        }

        Thread[] producers = new Thread[producerCount];
        for (int i = 0; i < producerCount; i++) {
            producers[i] = new Producer(tasksQueue, eventsQueue, tasksProducing, random);
        }

        startThreads(producers);
        startThreads(consumers);
        joinThreads(producers);

        while (!(tasksCreated == tasksFinished && producersCreated == producersFinished)
                || tasksCreated == 0) {
            Event event = eventsQueue.take();
            if (event.getType().equals(EventType.TASK_CREATED)) {
                tasksCreated++;
            }
            if (event.getType().equals(EventType.TASK_FINISHED)) {
                tasksFinished++;
            }
            if (event.getType().equals(EventType.PRODUCER_CREATED)) {
                producersCreated++;
            }
            if (event.getType().equals(EventType.PRODUCER_FINISHED)) {
                producersFinished++;
            }
        }

        for (int i = 0; i < consumerCount; i++) {
            tasksQueue.add(new MultiTask("name", null));
        }
        joinThreads(consumers);
        System.out.println("Queue size: " + tasksQueue.size());
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
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
