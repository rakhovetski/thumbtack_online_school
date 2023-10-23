package net.thumbtack.school.concurrent.nine.task;

import net.thumbtack.school.concurrent.nine.model.Group;
import net.thumbtack.school.concurrent.nine.model.Trainee;
import net.thumbtack.school.concurrent.nine.model.TrainingException;

import java.util.List;

class GroupThread extends Thread{
    private final Group group;
    private final String operation;

    public GroupThread(Group group, String operation) {
        this.group = group;
        this.operation = operation;
    }

    public void addTrainees() {
        for (int i = 0; i < 50; i++) {
            try {
                String name = String.format("Trainee: %s", i);
                group.addTrainee(new Trainee(name, "lastName", 5));
                System.out.printf("Trainee%s was appended\n", i);
                Thread.sleep(100);
            } catch (InterruptedException | TrainingException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeTrainees() {
        for (int i = 0; i < 50; i++) {
            try {
                group.removeTrainee(i);
                System.out.printf("Trainee%s was deleted\n", i);
                Thread.sleep(100);
            } catch (InterruptedException | TrainingException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void run() {
        if (operation.equals("add")) {
            addTrainees();
        } else {
            removeTrainees();
        }
    }
}


public class MyGroup {
    public static void main(String[] args) throws TrainingException{
        Group group = new Group("Name", "Room");
        fillGroup(group);

        GroupThread addTrainees = new GroupThread(group, "add");
        GroupThread removeTrainees = new GroupThread(group, "remove");

        addTrainees.start();
        removeTrainees.start();

        try {
            addTrainees.join();
            removeTrainees.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void fillGroup(Group group) {
        List<Trainee> trainees = group.getTrainees();

        for (int i = 0; i < 50; i++) {
            try {
                String name = String.format("Trainee: %s", i);
                trainees.add(new Trainee(name, "lastName", 5));
            } catch (TrainingException ex) {
                ex.printStackTrace();
            }
        }
    }
}
