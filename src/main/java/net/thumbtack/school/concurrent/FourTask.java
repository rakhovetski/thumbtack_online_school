package net.thumbtack.school.concurrent;

import java.util.ArrayList;
import java.util.Random;

class Plus extends Thread {
    private final ArrayList<Integer> list;
    private final Random random;

    public Plus(ArrayList<Integer> list, Random random) {
        this.list = list;
        this.random = random;
    }

    public void run() {
        for(int i = 0; i < 10_000; i++){
            synchronized (list) {
                list.add(random.nextInt(10_000));
            }
        }
    }
}

class Minus extends Thread {
    private final ArrayList<Integer> list;
    private final Random random;

    public Minus(ArrayList<Integer> list, Random random) {
        this.list = list;
        this.random = random;
    }

    public void run() {
        int i = 0;
        while (i < 10_000) {
            synchronized (list) {
                if (!list.isEmpty()) {
                    list.remove(random.nextInt(list.size()));
                    i++;
                }
            }
        }

    }
}

public class FourTask {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>(10_000);

        Random random = new Random();

        Plus plus = new Plus(list, random);
        Minus minus = new Minus(list, random);

        plus.start();
        minus.start();

        try {
            plus.join();
            minus.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println("List size: " + list.size());

    }
}
