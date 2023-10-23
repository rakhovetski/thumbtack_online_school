package net.thumbtack.school.hiring.dto.request;

import java.util.List;

public class AppendVacancyDtoRequest {
    private String jobTitle;
    private int salary;
    private List<VacancyRequirementDtoRequest> vacancyRequirements;

    public AppendVacancyDtoRequest(String jobTitle, int salary) {
        this.jobTitle = jobTitle;
        this.salary = salary;
    }

    public AppendVacancyDtoRequest(String jobTitle, int salary, List<VacancyRequirementDtoRequest> vacancyRequirements){
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.vacancyRequirements = vacancyRequirements;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public int getSalary() {
        return salary;
    }

    public List<VacancyRequirementDtoRequest> getVacancyRequirements() {
        return vacancyRequirements;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setVacancyRequirements(List<VacancyRequirementDtoRequest> vacancyRequirements) {
        this.vacancyRequirements = vacancyRequirements;
    }


}
