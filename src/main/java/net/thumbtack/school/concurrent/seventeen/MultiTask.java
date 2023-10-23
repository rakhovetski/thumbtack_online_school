package net.thumbtack.school.concurrent.seventeen;

import net.thumbtack.school.concurrent.sixteen.Executable;

import java.util.LinkedList;
import java.util.List;

public class MultiTask implements Executable {
    private String name;
    private List<Executable> list;

    public MultiTask(String name, List<Executable> list) {
        this.name = name;
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Executable> getList() {
        return list;
    }

    public void setList(LinkedList<Executable> list) {
        this.list = list;
    }

    public void execute() {
        System.out.printf("Thread %s doing %s\n", Thread.currentThread().getId(), getName());
    }
}
