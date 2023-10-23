package net.thumbtack.school.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ArrayLock {
    private final List<Integer> list;
    private final Lock lock = new ReentrantLock();

    private final Random random = new Random();

    public ArrayLock(List<Integer> list) {
        this.list = list;
    }

    public void plus() {
        for (int i = 0; i < 1_000; i++) {
            lock.lock();
            try {
                int randomN = random.nextInt(100);
                list.add(randomN);
                System.out.printf("Appended %s value\n", randomN);
            } finally {
                lock.unlock();
            }
        }
    }

    public void minus(){
        for (int i = 0; i < 1_000; i++) {
            lock.lock();
            if( !list.isEmpty()) {
                int randomN = random.nextInt(list.size());
                list.remove(randomN);
                System.out.printf("Deleted %s value\n", randomN);
            }
            lock.unlock();

        }
    }
}

class PlusTen extends Thread {
    private final ArrayLock arrayLock;

    public PlusTen(ArrayLock arrayLock) {
        this.arrayLock = arrayLock;
    }

    public void run() {
        arrayLock.plus();
    }
}

class MinusTen extends Thread{
    private final ArrayLock arrayLock;

    public MinusTen(ArrayLock arrayLock) {
        this.arrayLock = arrayLock;
    }

    public void run() {
        arrayLock.minus();
    }
}

public class TenTask {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayLock arrayLock = new ArrayLock(list);

        PlusTen plusTen = new PlusTen(arrayLock);
        MinusTen minusTen = new MinusTen(arrayLock);

        plusTen.start();
        minusTen.start();

        try {
            plusTen.join();
            minusTen.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.printf("List size %s", list.size());
    }
}
