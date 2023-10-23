package net.thumbtack.school.hiring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Skill {
    private int id;
    private String description;
    private int level;

    public Skill(String description, int level){
        this.description = description;
        this.level = level;
    }
}
