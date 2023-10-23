package net.thumbtack.school.hiring.dto.response;

public class VacancyRequirementDtoResponse {

    private String description;
    private int level;
    private boolean obligatory;

    public VacancyRequirementDtoResponse(String description, int level, boolean obligatory) {
        this.description = description;
        this.level = level;
        this.obligatory = obligatory;
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
