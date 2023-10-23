package net.thumbtack.school.hiring.dto.request;

public class VacancyNameDtoRequest {
    String name;

    public VacancyNameDtoRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
