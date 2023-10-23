package net.thumbtack.school.concurrent.nine.model;

public enum TrainingErrorCode {
    TRAINEE_WRONG_FIRSTNAME,
    TRAINEE_WRONG_LASTNAME,
    TRAINEE_WRONG_RATING,
    GROUP_WRONG_NAME,
    TRAINEE_NOT_FOUND,
    SCHOOL_WRONG_NAME,
    DUPLICATE_GROUP_NAME,
    DUPLICATE_TRAINEE,
    GROUP_NOT_FOUND,
    EMPTY_TRAINEE_QUEUE,
    GROUP_WRONG_ROOM;
    private String errorString;
    TrainingErrorCode(){}

    private TrainingErrorCode(String errorString){
        this.errorString = errorString;
    }

    public String getMessage(){
        return errorString;
    }

}

