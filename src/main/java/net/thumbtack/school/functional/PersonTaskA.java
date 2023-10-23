package net.thumbtack.school.functional;

public class PersonTaskA {
    private PersonTaskA father;
    private PersonTaskA mother;

    public PersonTaskA(PersonTaskA father, PersonTaskA mother){
        this.father = father;
        this.mother = mother;
    }

    public PersonTaskA getMothersMotherFather(){
        if(mother == null || mother.getMother() == null){
            return null;
        } else {
            return mother.getMother().getFather();
        }
    }

    public PersonTaskA getFather() {
        return father;
    }

    public PersonTaskA getMother() {
        return mother;
    }
}
