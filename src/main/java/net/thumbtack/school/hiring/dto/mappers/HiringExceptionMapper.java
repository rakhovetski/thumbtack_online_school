package net.thumbtack.school.hiring.dto.mappers;

import net.thumbtack.school.hiring.error.ErrorCode;
import net.thumbtack.school.hiring.error.ServerException;
import net.thumbtack.school.hiring.utils.HiringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class HiringExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        StringBuilder message = new StringBuilder();
        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
            message.append(cv.getPropertyPath()).append(" ").append(cv.getMessage()).append("\n");
        }
        return HiringUtils.failureResponse(Response.Status.BAD_REQUEST,
                new ServerException(ErrorCode.VALIDATION_ERROR, message.toString()));
    }
}
