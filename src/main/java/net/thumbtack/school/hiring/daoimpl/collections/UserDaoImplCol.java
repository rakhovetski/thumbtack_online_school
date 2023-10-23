package net.thumbtack.school.hiring.daoimpl.collections;

import net.thumbtack.school.hiring.dao.UserDao;
import net.thumbtack.school.hiring.database.Database;
import net.thumbtack.school.hiring.error.ServerException;
import net.thumbtack.school.hiring.model.User;

import java.util.List;

public class UserDaoImplCol implements UserDao {
    @Override
    public int insert(User user) throws ServerException {
        return Database.getInstance().register(user);
    }

    @Override
    public void update(User user, String token) throws ServerException{
        Database.getInstance().replaceUser(user,token);
    }

    @Override
    public void delete(int id) throws ServerException{
        Database.getInstance().removeUser(id);
    }

    @Override
    public void deleteAll() {
        Database.getInstance().clear();
    }

    @Override
    public List<User> getAllUsers() {
        return Database.getInstance().getAllUsers();
    }

    @Override
    public User getById(int id) {
        return Database.getInstance().getUserById(id);
    }

    @Override
    public User getByLogin(String login) {
        return Database.getInstance().getUserByLogin(login);
    }
}
