package com.ssg.test.base.response;

import com.ssg.test.base.enums.ResponseFailEnum;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler({Exception.class})
    protected CommonResponse<Object> handle5xxException(Exception e) {
        return CommonResponse.fail(ResponseFailEnum.UNKNOWN_ERROR, null);
    }

    @ExceptionHandler({NullPointerException.class})
    protected CommonResponse<Object> handleNullPointerException(NullPointerException e) {
        return CommonResponse.fail(ResponseFailEnum.UNKNOWN_ERROR, null);
    }

    @ExceptionHandler({SQLException.class})
    protected CommonResponse<Object> handleSQLException(SQLException e) {
        return CommonResponse.fail(ResponseFailEnum.FAIL_RUNNING_SQL, null);
    }

    @ExceptionHandler({SQLSyntaxErrorException.class})
    protected CommonResponse<Object> handleSQLException(SQLSyntaxErrorException e) {
        return CommonResponse.fail(ResponseFailEnum.FAIL_RUNNING_SQL, null);
    }

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    protected CommonResponse<Object> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        return CommonResponse.fail(ResponseFailEnum.FAIL_RUNNING_SQL, null);
    }

    @ExceptionHandler({UncategorizedSQLException.class})
    protected CommonResponse<Object> handleUncategorizedSqlException(UncategorizedSQLException e) {
        return CommonResponse.fail(ResponseFailEnum.FAIL_RUNNING_SQL, null);
    }

    @ExceptionHandler({QueryTimeoutException.class})
    protected CommonResponse<Object> handleQueryTimeoutException(QueryTimeoutException e) {
        return CommonResponse.fail(ResponseFailEnum.FAIL_RUNNING_SQL, null);
    }

    @ExceptionHandler({TooManyResultsException.class})
    protected CommonResponse<Object> handleTooManyResultsException(TooManyResultsException e) {
        return CommonResponse.fail(ResponseFailEnum.FAIL_RUNNING_SQL, null);
    }

    @ExceptionHandler({BindException.class})
    protected CommonResponse<Object> handleBindException(BindException e) {
        return CommonResponse.fail(ResponseFailEnum.PARAM_ERROR, null);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected CommonResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return CommonResponse.fail(ResponseFailEnum.PARAM_ERROR, null);
    }

    @ExceptionHandler({ClassCastException.class})
    protected CommonResponse<Object> handleClassCastException(ClassCastException e) {
        return CommonResponse.fail(ResponseFailEnum.PARAM_ERROR, null);
    }

    @ExceptionHandler({IllegalStateException.class})
    protected CommonResponse<Object> handleIllegalStateException(IllegalStateException e) {
        return CommonResponse.fail(ResponseFailEnum.UNKNOWN_ERROR, null);
    }

    @ExceptionHandler({BeanCreationException.class})
    protected CommonResponse<Object> handleBeanCreationException(BeanCreationException e) {
        log.error("handleBeanCreationException :: {} : {}", e.getLocalizedMessage(), Arrays.stream(e.getStackTrace()).findFirst());
        return CommonResponse.fail(ResponseFailEnum.UNKNOWN_ERROR, null);
    }
}