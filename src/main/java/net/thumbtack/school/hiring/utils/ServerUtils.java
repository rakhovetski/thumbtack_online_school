package net.thumbtack.school.hiring.utils;

import com.google.gson.Gson;
import net.thumbtack.school.hiring.error.ErrorCode;
import net.thumbtack.school.hiring.error.ServerException;

public class ServerUtils {
    private static final Gson gson = new Gson();
    public static <T> T getClassFromJson(String jsonString, Class<T> tClass) throws ServerException {
        if(jsonString == null){
            throw new ServerException(ErrorCode.WRONG_JSON);
        }
        return gson.fromJson(jsonString, tClass);
    }
}
