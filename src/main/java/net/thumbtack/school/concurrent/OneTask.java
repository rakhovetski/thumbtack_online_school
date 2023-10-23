package net.thumbtack.school.concurrent;

public class OneTask {
    public static void main(String[] args) {
        Thread thread = Thread.currentThread();

        System.out.println(thread.getName());
        System.out.println(thread.getState());
        System.out.println(thread.getPriority());
        System.out.println(thread.getId());
    }
}
