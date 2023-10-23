package net.thumbtack.school.hiring.dto.request;

public class EmployerIdDtoRequest {
    private int id;

    public EmployerIdDtoRequest(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
