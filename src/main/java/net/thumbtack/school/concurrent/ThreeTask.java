package net.thumbtack.school.concurrent;

public class ThreeTask {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            long i = Thread.currentThread().getId();
            System.out.println("Thread: " + i);
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
