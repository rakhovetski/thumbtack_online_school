package net.thumbtack.school.hiring.dto.request;


public class RegisterEmployeeDtoRequest extends RegisterDtoRequest{
    public RegisterEmployeeDtoRequest(String email, String surname, String name, String patronymic,String login, String password){
        super(email, surname, name, patronymic, login, password);
    }
}
