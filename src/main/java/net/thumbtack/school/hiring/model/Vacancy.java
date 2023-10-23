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
public class Vacancy {
    private int id;
    private String jobTitle;
    private int salary;
    private Set<VacancyRequirement> vacancyRequirements = new HashSet<>();
    private boolean status;

    public Vacancy(int id, String jobTitle, int salary, boolean status) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.status = status;
    }

    public Vacancy(int id, String jobTitle, int salary) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.status = true;
    }

    public Vacancy(String jobTitle, int salary) {
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.status = true;
    }

    public void addVacancyRequirement(VacancyRequirement vacancyRequirement){
        vacancyRequirements.add(vacancyRequirement);
    }

    public void deleteVacancyRequirement(VacancyRequirement vacancyRequirement){
        vacancyRequirements.remove(vacancyRequirement);
    }

    public void clearVacancyRequirement() {
        vacancyRequirements.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vacancy)) return false;
        Vacancy vacancy = (Vacancy) o;
        return getId() == vacancy.getId() && getSalary() == vacancy.getSalary() && isStatus() == vacancy.isStatus() && Objects.equal(getJobTitle(), vacancy.getJobTitle()) && Objects.equal(getVacancyRequirements(), vacancy.getVacancyRequirements());
    }



    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getJobTitle(), getSalary(), getVacancyRequirements(), isStatus());
    }
}
