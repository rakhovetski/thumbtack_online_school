package net.thumbtack.school.hiring.model;

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends User {

    private Set<Skill> skills = new HashSet<>();
    private boolean status = true;

    public Employee(String email, String surname, String name, String patronymic, String login, String password) {
        super(email, surname, name, patronymic, login, password);
    }

    public Employee(String email, String surname, String name, String patronymic, String login, String password, boolean status) {
        this(email, surname, name, patronymic, login, password);
        this.status = status;
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }

    public void removeSkill(Skill skill) {
        skills.remove(skill);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "skills=" + skills +
                ", status=" + status +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return isStatus() == employee.isStatus() && Objects.equal(getSkills(), employee.getSkills());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getSkills(), isStatus());
    }
}





























































