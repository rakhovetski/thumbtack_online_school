package net.thumbtack.school.hiring.mappers;

import net.thumbtack.school.hiring.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface ActiveUserMapper {
    @Insert("INSERT INTO active_user(token, id_user) VALUES(#{token}, #{user.id})")
    void insert (@Param("user") User user, @Param("token") String token);

    @Delete("DELETE FROM active_user WHERE token = #{token}")
    void delete (@Param("token") String token);
}
