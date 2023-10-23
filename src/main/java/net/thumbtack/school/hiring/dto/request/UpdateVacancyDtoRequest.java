package net.thumbtack.school.hiring.dto.request;

public class UpdateVacancyDtoRequest {
    private int id;
    private String jobTitle;
    private int salary;
    private boolean status;
    private String employerToken;

    public UpdateVacancyDtoRequest(int id, String jobTitle, int salary, boolean status, String employerToken) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.status = status;
        this.employerToken = employerToken;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getEmployerToken() {
        return employerToken;
    }

    public void setEmployerToken(String employerToken) {
        this.employerToken = employerToken;
    }
}
