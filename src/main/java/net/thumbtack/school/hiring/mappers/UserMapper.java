package net.thumbtack.school.hiring.mappers;

import net.thumbtack.school.hiring.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    @Insert("INSERT INTO user (email, surname, name, patronymic, login, password) " +
            "VALUES(#{user.email}, #{user.surname}, #{user.name}, #{user.patronymic}, #{user.login}, #{user.password})")
    @Options(useGeneratedKeys = true, keyProperty = "user.id")
    Integer insert(@Param("user") User user);


    @Update("UPDATE user JOIN active_user ON user.id = active_user.id_user " +
            "SET email = #{user.email}, surname = #{user.surname}, name = #{user.name}, " +
            "patronymic = #{user.patronymic}, password = #{user.password} WHERE active_user.token = #{token}")
    void update(@Param("user") User user, @Param("token") String token);


    @Delete("DELETE FROM user WHERE user.id = #{id}")
    void delete(@Param("id") int id);


    @Delete("DELETE FROM user")
    void deleteAll();


    @Select("SELECT * FROM user")
    List<User> getAllUsers();


    @Select("SELECT * FROM user WHERE id = #{id}")
    User getById(@Param("id") int id);


    @Select("SELECT * FROM user WHERE login = #{login}")
    User getByLogin(@Param("login") String login);


    @Select("SELECT id FROM user WHERE (login = #{user.login})")
    Integer getIdByUser(@Param("user") User user);


    @Select("SELECT id FROM user WHERE login = #{login}  LIMIT 1")
    int getUserIdByLogin(String login);
}
