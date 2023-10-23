package net.thumbtack.school.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class PingPongLock extends Thread {
    private final Lock lock = new ReentrantLock();
    private final Condition ping;
    private final Condition pong;
    private boolean firstPing = true;

    public PingPongLock() {
        ping = lock.newCondition();
        pong = lock.newCondition();
    }

    public void pingMethod() {
        try {
            lock.lock();
            if (firstPing) firstPing = false;
            System.out.println("Ping");
            Thread.sleep(500);
            ping.signalAll();
            pong.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void pongMethod() {
        try {
            lock.lock();
            while (firstPing)
                pong.await();
            System.out.println("Pong");
            Thread.sleep(500);
            pong.signalAll();
            ping.await();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class ElevenTask {
    public static void main(String[] args) {
        PingPongLock pingPongLock = new PingPongLock();

        Thread thread1 = new Thread(() -> {
            while (true) {
                pingPongLock.pingMethod();
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                pingPongLock.pongMethod();
            }
        });

        thread1.start();
        thread2.start();
    }
}
