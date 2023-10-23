package net.thumbtack.school.hiring.dto.request;

public class AppendSkillDtoRequest {
    private String description;
    private int level;

    public AppendSkillDtoRequest(String description, int level){
        this.description = description;
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public int getLevel() {
        return level;
    }

}
