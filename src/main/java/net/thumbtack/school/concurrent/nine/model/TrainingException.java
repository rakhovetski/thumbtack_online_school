package net.thumbtack.school.concurrent.nine.model;

public class TrainingException extends Exception{
    private TrainingErrorCode errorCode;

    public TrainingException(TrainingErrorCode errorCode){
        this.errorCode = errorCode;
    }

    public TrainingErrorCode getErrorCode(){
        return errorCode;
    }
}
