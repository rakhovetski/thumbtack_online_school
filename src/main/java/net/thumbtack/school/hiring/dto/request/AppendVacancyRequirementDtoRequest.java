package net.thumbtack.school.hiring.dto.request;

public class AppendVacancyRequirementDtoRequest {
    private int vacancyId;
    private String description;
    private int level;
    private boolean obligatory;

    public AppendVacancyRequirementDtoRequest(int vacancyId, String description, int level, boolean obligatory) {
        this.vacancyId = vacancyId;
        this.description = description;
        this.level = level;
        this.obligatory = obligatory;
    }

    public int getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(int vacancyId) {
        this.vacancyId = vacancyId;
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
