package net.thumbtack.school.hiring.dto.response;

public class AppendVacancyDtoResponse {
    private int id;

    public AppendVacancyDtoResponse() {
    }

    public AppendVacancyDtoResponse(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
