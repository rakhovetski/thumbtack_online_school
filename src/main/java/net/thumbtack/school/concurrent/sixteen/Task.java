package net.thumbtack.school.concurrent.sixteen;

public class Task implements Executable{
    private String name;

    public Task(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void execute() {
        System.out.printf("Thread %s doing %s\n", Thread.currentThread().getId(), getName());
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.printf("Thread %s done by %s\n",getName(), Thread.currentThread().getId());
    }
}
