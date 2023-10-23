package net.thumbtack.school.concurrent.nine.task;

import net.thumbtack.school.concurrent.nine.model.Group;
import net.thumbtack.school.concurrent.nine.model.School;
import net.thumbtack.school.concurrent.nine.model.TrainingException;

import java.util.Set;

class SchoolThread extends Thread {
    private final School school;
    private final String operation;

    public SchoolThread(School school, String operation) {
        this.school = school;
        this.operation = operation;
    }

    public void addGroups() {
        for (int i = 50; i < 100; i++) {
            synchronized (school) {
                try {
                    String name = String.format("Group%s", i);
                    school.addGroup(new Group(name, "room"));
                    System.out.printf("Group%s was appended \n", i);
                    Thread.sleep(100);
                } catch (TrainingException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void removeGroups() {
        for (int i = 0; i < 50; i++) {
            synchronized (school) {
                try {
                    school.removeGroup(String.format("Group%s", i));
                    System.out.printf("Group%s was deleted \n", i);
                    Thread.sleep(100);
                } catch (TrainingException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void run() {
        if (operation.equals("add")) {
            addGroups();
        } else {
            removeGroups();
        }
    }
}


public class MySchool {
    public static void main(String[] args) throws TrainingException {
        School school = new School("School", 2023);
        fillSchool(school);

        SchoolThread addGroups = new SchoolThread(school, "add");
        SchoolThread removeGroups = new SchoolThread(school, "remove");

        addGroups.start();
        removeGroups.start();

        try {
            addGroups.join();
            removeGroups.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void fillSchool(School school){
        Set<Group> set = school.getGroups();

        for (int i = 0; i < 50; i++) {
            try {
                String name = String.format("Group%s", i);
                set.add(new Group(name, "room"));
            } catch (TrainingException e) {
                e.printStackTrace();
            }
        }
    }
}

