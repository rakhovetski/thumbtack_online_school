package net.thumbtack.school.hiring.dto.request;

public class RemoveVacancyDtoRequest {
    private String employerToken;
    private int id;

    public RemoveVacancyDtoRequest(){

    }

    public RemoveVacancyDtoRequest(int id, String employerToken) {
        this.employerToken = employerToken;
        this.id = id;
    }

    public String getEmployerToken() {
        return employerToken;
    }

    public void setEmployerToken(String employerToken) {
        this.employerToken = employerToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
