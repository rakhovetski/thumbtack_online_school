package net.thumbtack.school.hiring.dto.mappers;

import net.thumbtack.school.hiring.error.ErrorCode;
import net.thumbtack.school.hiring.error.ServerException;
import net.thumbtack.school.hiring.utils.HiringUtils;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MethodNotAllowedExceptionMapper implements ExceptionMapper<NotAllowedException> {

    @Override
    public Response toResponse(NotAllowedException exception) {
        return HiringUtils.failureResponse(Response.Status.METHOD_NOT_ALLOWED,
                new ServerException(ErrorCode.METHOD_NOT_ALLOWED));
    }
}
