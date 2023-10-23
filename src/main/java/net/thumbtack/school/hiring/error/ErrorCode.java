package net.thumbtack.school.hiring.error;

public enum ErrorCode {
    WRONG_SURNAME("Wrong surname"),
    WRONG_NAME("Wrong name"),
    WRONG_PATRONYMIC("Wrong patronymic"),
    WRONG_EMAIL("Wrong email"),
    WRONG_LOGIN("Wrong login"),
    WRONG_PASSWORD("Wrong password"),
    WRONG_TITLE("Wrong title"),
    WRONG_ADDRESS("Wrong address"),
    WRONG_SKILL_DESCRIPTION("Wrong skill description"),
    WRONG_SKILL_LEVEL("Wrong skill level, number must be in the range(1, 5)"),
    WRONG_VACANCY_NAME("Wrong vacancy name"),
    WRONG_SALARY("Wrong salay"),
    WRONG_JOB_TITLE("Wrong job title"),
    USER_NOT_FOUND("User not found"),
    WRONG_VACANCY_ID("Wrong vacancy id"),
    WRONG_USER("Wrong user"),
    WRONG_TOKEN("Wrong token"),
    LOGIN_USER_ERROR("User is logged in"),
    LOGIN_IS_USED("Login is used"),
    USER_NOT_EMPLOYER("User not employer"),
    USER_NOT_EMPLOYEE("User not employee"),
    WRONG_ID("Wrong id"),
    WRONG_JSON("Wrong json"),
    SUCCESS("OK"),
    METHOD_NOT_ALLOWED("Method not allowed"),
    WRONG_URL("Wrong url"),
    INVALID_ID("Invalid id"),
    VALIDATION_ERROR("Validate error");

    public String errorString;

    public String getErrorString(){
        return errorString;
    }

    ErrorCode(String errorString){
        this.errorString = errorString;
    }
}
