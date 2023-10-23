package net.thumbtack.school.hiring.dto.request;

public class UpdateVacancyRequirementDtoRequest {
    private int id;
    private String description;
    private int level;
    private boolean obligatory;
    private int vacancyId;

    public UpdateVacancyRequirementDtoRequest(int id, String description, int level, boolean obligatory, int vacancyId) {
        this.id = id;
        this.description = description;
        this.level = level;
        this.obligatory = obligatory;
        this.vacancyId = vacancyId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isObligatory() {
        return obligatory;
    }

    public void setObligatory(boolean obligatory) {
        this.obligatory = obligatory;
    }
}
