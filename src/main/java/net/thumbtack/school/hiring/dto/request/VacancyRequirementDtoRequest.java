package net.thumbtack.school.hiring.dto.request;

public class VacancyRequirementDtoRequest extends SkillDtoRequest{
    private boolean obligatory;

    public VacancyRequirementDtoRequest(String requirement, int level, boolean obligatory){
        super(requirement, level);
        this.obligatory = obligatory;
    }

    public boolean isObligatory() {
        return obligatory;
    }

    public void setObligatory(boolean obligatory) {
        this.obligatory = obligatory;
    }
}
