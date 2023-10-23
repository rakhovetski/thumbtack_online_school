package net.thumbtack.school.hiring.service;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import net.thumbtack.school.hiring.daoimpl.collections.ActiveUserDaoImplCol;
import net.thumbtack.school.hiring.daoimpl.collections.UserDaoImplCol;
import net.thumbtack.school.hiring.daoimpl.mybatis.ActiveUserDaoImpl;
import net.thumbtack.school.hiring.daoimpl.mybatis.UserDaoImpl;
import net.thumbtack.school.hiring.dto.request.LoginDtoRequest;
import net.thumbtack.school.hiring.dto.request.LogoutDtoRequest;
import net.thumbtack.school.hiring.dto.response.EmptyDtoResponse;
import net.thumbtack.school.hiring.dto.response.LoginDtoResponse;
import net.thumbtack.school.hiring.error.ErrorCode;
import net.thumbtack.school.hiring.error.ServerException;
import net.thumbtack.school.hiring.model.User;
import net.thumbtack.school.hiring.utils.DaoType;
import net.thumbtack.school.hiring.utils.HiringUtils;
import net.thumbtack.school.hiring.utils.ServerUtils;
import net.thumbtack.school.hiring.utils.Settings;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

public class UserService {
    private static final Gson gson = new Gson();
    private final Settings settings = Settings.getInstance();

    private final UserDaoImpl userDaoSql = new UserDaoImpl();
    private final ActiveUserDaoImpl activeUserDaoSql = new ActiveUserDaoImpl();

    private final UserDaoImplCol userDaoImplCol = new UserDaoImplCol();
    private final ActiveUserDaoImplCol activeUserDaoImplCol = new ActiveUserDaoImplCol();


    public Response login(String json){
        try {
            LoginDtoRequest loginDtoRequest = ServerUtils.getClassFromJson(json, LoginDtoRequest.class);
            validateLogin(loginDtoRequest);

            String token = UUID.randomUUID().toString();
            if (settings.getDaoType().equals(DaoType.SQL)) {
                User user = userDaoSql.getByLogin(loginDtoRequest.getLogin());
                if (user == null || !user.getPassword().equals(loginDtoRequest.getPassword())) {
                    throw new ServerException(ErrorCode.WRONG_LOGIN);
                }
                user.setToken(token);
                activeUserDaoSql.insert(user, token);
            } else {
                User user = userDaoImplCol.getByLogin(loginDtoRequest.getLogin());
                validateUser(user, loginDtoRequest.getPassword());
                user.setToken(token);
                activeUserDaoImplCol.insert(user, token);
            }
            LoginDtoResponse loginDtoResponse = new LoginDtoResponse(token);
            return Response.ok(loginDtoResponse, MediaType.APPLICATION_JSON).build();
        } catch (ServerException ex){
            return HiringUtils.failureResponse(ex);
        }
    }

    public Response logout(String token) {
        try {
            if (settings.getDaoType().equals(DaoType.SQL)) {
                activeUserDaoSql.delete(token);
            }
            else {
                activeUserDaoImplCol.delete(token);
            }
            return Response.ok(new EmptyDtoResponse(), MediaType.APPLICATION_JSON).build();
        }
        catch (ServerException e) {
            return HiringUtils.failureResponse(e);
        }
    }

    private void validateLogin(LoginDtoRequest request) throws ServerException {
        if(request == null){
            throw new ServerException(ErrorCode.WRONG_LOGIN);
        }
        if (Strings.isNullOrEmpty(request.getLogin()))
            throw new ServerException(ErrorCode.WRONG_LOGIN);
        if (Strings.isNullOrEmpty(request.getPassword())    )
            throw new ServerException(ErrorCode.WRONG_PASSWORD);
    }

    private void validateLogout(LogoutDtoRequest request) throws ServerException {
        if (request == null) {
            throw new ServerException(ErrorCode.WRONG_LOGIN);
        }
    }

    private void validateUser(User user, String loginPassword) throws ServerException{
        if (user == null) {
            throw new ServerException(ErrorCode.USER_NOT_FOUND);
        }

        if (!loginPassword.equals(user.getPassword())) {
            throw new ServerException(ErrorCode.WRONG_PASSWORD);
        }
    }


    public void deleteAll() {
        if (settings.getDaoType().equals(DaoType.SQL)) {
            userDaoSql.deleteAll();
        } else {
            userDaoImplCol.deleteAll();
        }
    }
}
