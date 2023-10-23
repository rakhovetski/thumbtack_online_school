package net.thumbtack.school.hiring.dto.response;

import net.thumbtack.school.hiring.model.VacancyRequirement;

import java.util.Set;

public class VacancyDtoResponse {
    private int id;
    private String jobTitle;
    private int salary;
    private Set<VacancyRequirement> vacancyRequirements;
    private boolean status;

    public VacancyDtoResponse(int id, String jobTitle, int salary, Set<VacancyRequirement> vacancyRequirements, boolean status) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.vacancyRequirements = vacancyRequirements;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Set<VacancyRequirement> getVacancyRequirements() {
        return vacancyRequirements;
    }

    public void setVacancyRequirements(Set<VacancyRequirement> vacancyRequirements) {
        this.vacancyRequirements = vacancyRequirements;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
