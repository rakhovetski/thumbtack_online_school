package net.thumbtack.school.concurrent.nine.model;


import java.util.*;

public class Group {
    private String name;
    private String room;
    private List<Trainee> students;

    public Group(String name, String room) throws TrainingException {
        setName(name);
        setRoom(room);
        students = Collections.synchronizedList(new ArrayList<>());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws TrainingException {
        if (name == null || name.equals("")) {
            throw new TrainingException(TrainingErrorCode.GROUP_WRONG_NAME);
        }
        this.name = name;
    }

    public String getRoom(){
        return room;
    }

    public void setRoom(String room) throws TrainingException{
        if (room == null || room.equals("")) {
            throw new TrainingException(TrainingErrorCode.GROUP_WRONG_ROOM);
        }
        this.room = room;
    }

    public List<Trainee> getTrainees(){
        return students;
    }

    public void addTrainee(Trainee trainee){
        students.add(trainee);
    }

    public void removeTrainee(Trainee trainee) throws TrainingException{
        if(!students.contains(trainee)){
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        students.remove(trainee);
    }

    public void removeTrainee(int index) throws TrainingException{
        if(index > students.size() - 1 || index < 0){
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        students.remove(index);
    }

    public Trainee getTraineeByFirstName(String firstName) throws TrainingException{
        for(Trainee student : students){
            if( student.getFirstName().equals(firstName)){
                return student;
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public Trainee getTraineeByFullName(String fullName) throws TrainingException{
        for(Trainee student : students){
            if (student.getFullName().equals(fullName)){
                return student;
            }
        }
        throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
    }

    public void sortTraineeListByFirstNameAscendant(){
        students.sort(Comparator.comparing(Trainee::getFirstName));
    }

    public void sortTraineeListByRatingDescendant(){
        students.sort(Comparator.comparing(Trainee::getRating).reversed());
    }

    public void reverseTraineeList(){
        Collections.reverse(students);
    }

    public void rotateTraineeList(int positions){
        Collections.rotate(students, positions);
    }

    public List<Trainee> getTraineesWithMaxRating() throws TrainingException{
        if (students.isEmpty()){
            throw new TrainingException(TrainingErrorCode.TRAINEE_NOT_FOUND);
        }
        List<Trainee> resultStudents = new ArrayList<>();
        int maxMark = 0;
        for(Trainee student : students){
            if(student.getRating() > maxMark){
                maxMark = student.getRating();
            }
        }
        for(Trainee student : students){
            if(student.getRating() == maxMark){
                resultStudents.add(student);
            }
        }
        return resultStudents;
    }

    public boolean hasDuplicates(){
        Set<Trainee> result = new HashSet<>(students);
        return result.size() != students.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(name, group.name) && Objects.equals(room, group.room) && Objects.equals(students, group.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, room, students);
    }
}

