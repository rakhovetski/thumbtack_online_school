package net.thumbtack.school.concurrent;


import java.util.concurrent.Semaphore;

public class EightTask {
    static private int i = 0;

    private static final Semaphore read = new Semaphore(0);
    private static final Semaphore write = new Semaphore(1);

    public void readMethod() {
        try {
            read.acquire();
            System.out.printf("Reader: %d read %d\n", Thread.currentThread().getId(), i);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            write.release();
        }
    }

    public void writeMethod() {
        try {
            write.acquire();
            System.out.printf("Writer: %d write %d\n", Thread.currentThread().getId(), i++);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            read.release();
        }
    }
}


class Writer extends Thread {
    private final EightTask eightTask;

    public Writer(EightTask eightTask) {
        this.eightTask = eightTask;
    }

    public void run() {
        for (int i = 0; i <= 5; i++) {
            eightTask.writeMethod();
        }
    }
}

class Reader extends Thread {
    private final EightTask eightTask;

    public Reader(EightTask eightTask) {
        this.eightTask = eightTask;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            eightTask.readMethod();
        }
    }
}

class Main {
    public static void main(String[] args) {
        EightTask eightTask = new EightTask();

        new Writer(eightTask).start();
        new Reader(eightTask).start();
    }
}