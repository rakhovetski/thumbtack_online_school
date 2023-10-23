package net.thumbtack.school.hiring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDtoRequest {
    private String login;
    private String password;
}
