package net.thumbtack.school.hiring.dto.request;

public class UpdateSkillDtoRequest {
    private String employeeToken;
    private int id;
    private String description;
    private int level;

    public UpdateSkillDtoRequest(int id, String description, int level, String employeeToken) {
        this.employeeToken = employeeToken;
        this.id = id;
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

    public String getEmployeeToken() {
        return employeeToken;
    }

    public void setEmployeeToken(String employeeToken) {
        this.employeeToken = employeeToken;
    }
}
