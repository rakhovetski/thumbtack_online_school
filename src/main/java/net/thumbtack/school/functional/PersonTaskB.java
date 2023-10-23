package net.thumbtack.school.functional;

import java.util.Optional;

public class PersonTaskB {
    private Optional<PersonTaskB> father;
    private Optional<PersonTaskB> mother;

    public PersonTaskB(PersonTaskB father, PersonTaskB mother){
        this.father = Optional.ofNullable(father);
        this.mother = Optional.ofNullable(mother);
    }

    public Optional<PersonTaskB> getMothersMotherFather(){
        return mother.flatMap(PersonTaskB::getMother).flatMap(PersonTaskB::getFather);
    }

    public Optional<PersonTaskB> getFather() {
        return father;
    }

    public Optional<PersonTaskB> getMother() {
        return mother;
    }
}
