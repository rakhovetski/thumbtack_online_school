package net.thumbtack.school.concurrent.nine.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class School {
    private String name;
    private int year;
    private Set<Group> groupSet;

    public School(String name, int year) throws TrainingException{
        setName(name);
        this.year = year;
        groupSet = Collections.synchronizedSet(new HashSet<>());
    }

    public String getName(){
        return name;
    }

    public void setName(String name) throws TrainingException{
        if (name == null || name.equals("")){
            throw new TrainingException(TrainingErrorCode.SCHOOL_WRONG_NAME);
        }
        this.name = name;
    }

    public int getYear(){
        return year;
    }

    public void setYear(int year){
        this.year = year;
    }

    public Set<Group> getGroups(){
        return groupSet;
    }

    public void addGroup(Group group) throws TrainingException{
        for(Group groupR : groupSet){
            if(groupR.getName().equals(group.getName())){
                throw new TrainingException(TrainingErrorCode.DUPLICATE_GROUP_NAME);
            }
        }
        groupSet.add(group);
    }

    public void removeGroup(Group group) throws TrainingException{
        removeGroup(group.getName());
    }

    public void removeGroup(String name) throws TrainingException{
        Group groupRes = null;
        for(Group group : groupSet){
            if(group.getName().equals(name)){
                groupRes = group;
            }
        }
        if( groupRes == null){
            throw new TrainingException(TrainingErrorCode.GROUP_NOT_FOUND);
        }
        groupSet.remove(groupRes);
    }

    public boolean containsGroup(Group group){
        return groupSet.contains(group);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        School school = (School) o;
        return year == school.year && Objects.equals(name, school.name) && Objects.equals(groupSet, school.groupSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, year, groupSet);
    }
}

