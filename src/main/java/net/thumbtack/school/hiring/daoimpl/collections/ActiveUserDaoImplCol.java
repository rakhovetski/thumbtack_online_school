package net.thumbtack.school.hiring.daoimpl.collections;

import net.thumbtack.school.hiring.dao.ActiveUserDao;
import net.thumbtack.school.hiring.database.Database;
import net.thumbtack.school.hiring.error.ServerException;
import net.thumbtack.school.hiring.model.User;

public class ActiveUserDaoImplCol implements ActiveUserDao {
    @Override
    public void insert(User user, String token) throws ServerException {
        Database.getInstance().login(user);
    }

    @Override
    public void delete(String token) throws ServerException{
        Database.getInstance().logout(token);
    }
}
