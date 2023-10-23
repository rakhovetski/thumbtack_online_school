package net.thumbtack.school.hiring.model;

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacancyRequirement extends Skill{

    private boolean obligatory ;

    public VacancyRequirement(int id, String description, int level, boolean obligatory){
        super(id, description, level);
        this.obligatory = obligatory;
    }

    public VacancyRequirement(String description, int level, boolean obligatory) {
        super(description, level);
        this.obligatory = obligatory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VacancyRequirement)) return false;
        if (!super.equals(o)) return false;
        VacancyRequirement that = (VacancyRequirement) o;
        return isObligatory() == that.isObligatory();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), isObligatory());
    }
}