package net.thumbtack.school.concurrent;

import java.util.concurrent.Semaphore;

class PingPong extends Thread {
    private Semaphore ping = new Semaphore(1);
    private Semaphore pong = new Semaphore(0);

    public void ping() {
        try {
            ping.acquire();
            System.out.println("Ping");
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            pong.release();
        }
    }

    public void pong() {
        try {
            pong.acquire();
            System.out.println("Pong");
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            ping.release();
        }
    }
}

public class SevenTask {
    public static void main(String[] args) {
        PingPong pingPong = new PingPong();

        Thread thread1 = new Thread(() -> {
            while(true) {
                pingPong.ping();
            }
        });

        Thread thread2 = new Thread(() -> {
            while(true) {
                pingPong.pong();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
