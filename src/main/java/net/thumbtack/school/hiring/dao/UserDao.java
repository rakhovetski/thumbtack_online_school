package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.error.ServerException;
import net.thumbtack.school.hiring.model.User;

import java.util.List;

public interface UserDao {

    int insert(User user) throws ServerException;

    void update(User user, String token) throws ServerException;

    void delete(int id) throws ServerException;

    void deleteAll();

    List<User> getAllUsers();

    User getById(int id);

    User getByLogin(String login);
}
