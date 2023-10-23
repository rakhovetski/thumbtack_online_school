package net.thumbtack.school.hiring.dto.request;

public class RemoveVacancyRequirementDtoRequest {
    private int vacancyId;
    private int id;

    public RemoveVacancyRequirementDtoRequest(int id, int vacancyId) {
        this.vacancyId = vacancyId;
        this.id = id;
    }

    public int getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(int vacancyId) {
        this.vacancyId = vacancyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
