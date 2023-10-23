package net.thumbtack.school.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

class Formatter {
    private final ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal<>();

    public void setDateFormatThreadLocal(SimpleDateFormat format) {
        this.dateFormatThreadLocal.set(format);
    }

    public String format(Date date) {
        return dateFormatThreadLocal.get().format(date);
    }
}

class FormatterThread extends  Thread {
    private final Formatter formatter;

    public FormatterThread(Formatter formatter) {
        this.formatter = formatter;
    }

    public void run() {
        formatter.setDateFormatThreadLocal(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
        Date date = new Date(100_000_000_000L + new Random().nextInt(1_000_000_000));
        String before = String.format("Thread %s. Date: %s", currentThread().getName(), date);
        System.out.println(before);
        String after = formatter.format(date);
        System.out.println(after);
    }
}

public class ThirteenTask {
    public static void main(String[] args) {
        Formatter formatter = new Formatter();
        Thread thread1 = new FormatterThread(formatter);
        Thread thread2 = new FormatterThread(formatter);
        Thread thread3 = new FormatterThread(formatter);
        Thread thread4 = new FormatterThread(formatter);
        Thread thread5 = new FormatterThread(formatter);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
            thread5.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
