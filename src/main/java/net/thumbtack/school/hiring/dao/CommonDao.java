package net.thumbtack.school.hiring.dao;

import net.thumbtack.school.hiring.error.ServerException;

public interface CommonDao {
    void clear() throws ServerException;
}
