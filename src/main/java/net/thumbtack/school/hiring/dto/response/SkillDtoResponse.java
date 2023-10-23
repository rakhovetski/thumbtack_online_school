package net.thumbtack.school.hiring.dto.response;

public class SkillDtoResponse {
    private int id;
    private String description;
    private int level;

    public SkillDtoResponse(int id, String description, int level){
        this.description = description;
        this.level = level;
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
}
