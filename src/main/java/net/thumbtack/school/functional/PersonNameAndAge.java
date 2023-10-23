package net.thumbtack.school.functional;

public class PersonNameAndAge {
    private String name;
    private int age;

    public PersonNameAndAge(String name, int age){
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
