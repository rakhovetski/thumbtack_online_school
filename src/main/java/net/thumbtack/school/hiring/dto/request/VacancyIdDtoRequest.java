package net.thumbtack.school.hiring.dto.request;

public class VacancyIdDtoRequest {
    private int id;

    public VacancyIdDtoRequest(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
