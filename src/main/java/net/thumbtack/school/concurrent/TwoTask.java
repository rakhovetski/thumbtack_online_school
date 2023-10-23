package net.thumbtack.school.concurrent;

public class TwoTask {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Second thread");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println("First thread");
    }
}
