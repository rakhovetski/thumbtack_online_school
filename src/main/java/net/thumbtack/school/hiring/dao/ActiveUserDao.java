package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.error.ServerException;
import net.thumbtack.school.hiring.model.User;

public interface ActiveUserDao {

    void insert(User user, String token) throws ServerException;

    void delete(String token) throws ServerException;
}
