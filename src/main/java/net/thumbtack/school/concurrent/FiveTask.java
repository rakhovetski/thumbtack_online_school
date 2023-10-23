package net.thumbtack.school.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ListWrapper {
    private List<Integer> list;

    public ListWrapper() {
        list = new ArrayList<>();
    }

    public synchronized void plus(int num) {
        list.add(num);
    }

    public synchronized void minus(int index) {
        list.remove(index);
    }

    public int getSize() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}

enum Operation {
    PLUS,
    MINUS
}

class PlusMinus extends Thread {
    private ListWrapper listWrapper = new ListWrapper();
    private Operation operation;
    private final Random random = new Random();
    private static final int COUNT = 10_000;

    public PlusMinus(Operation operation) {
        this.operation = operation;
    }

    @Override
    public void run() {
        if(operation == Operation.MINUS) {
            for(int i = 0; i < COUNT; i++) {
                if (!listWrapper.isEmpty()){
                    listWrapper.plus(random.nextInt(COUNT));
                }
            }
        } else {
            for (int i = 0; i < COUNT; i++) {
                listWrapper.plus(random.nextInt(COUNT));
            }
        }
    }
}

public class FiveTask {
    public static void main(String[] args) {
        ListWrapper listWrapper = new ListWrapper();
        Thread thread1 = new Thread(new PlusMinus(Operation.PLUS));
        Thread thread2 = new Thread(new PlusMinus(Operation.MINUS));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println(listWrapper.getSize());
    }
}
