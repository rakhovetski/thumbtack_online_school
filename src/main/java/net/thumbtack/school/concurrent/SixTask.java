package net.thumbtack.school.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class PlusSync extends Thread {
    private final List<Integer> list;
    private final Random random;

    public PlusSync(List<Integer> list, Random random) {
        this.list = list;
        this.random = random;
    }

    public void run() {
        for(int i = 0; i < 10_000; i++){
            list.add(random.nextInt(10_000));
        }
    }
}

class MinusSync extends Thread {
    private final List<Integer> list;
    private final Random random;

    public MinusSync(List<Integer> list, Random random) {
        this.list = list;
        this.random = random;
    }

    public void run() {
        int i = 0;
        while (i < 10_000) {
            if (!list.isEmpty()) {
                list.remove(random.nextInt(list.size()));
                i++;
            }
        }
    }
}


public class SixTask {
    public static void main(String[] args) {
        Random random = new Random();

        List<Integer> list = Collections.synchronizedList(new ArrayList<>());

        PlusSync plus = new PlusSync(list, random);
        MinusSync minus = new MinusSync(list, random);

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
