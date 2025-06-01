package com.ssg.test.base.response;


import com.ssg.test.base.enums.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@AllArgsConstructor
public class CommonResponse<T> {

    private final String resultCode;
    private final String resultMessage;
    private final T resultData;

    public static <T> CommonResponse<T> success(ResponseEnum res, T data) {
        return new CommonResponse<>(res.resultCode(), res.resultMessage(), data);
    }

    public static <T> CommonResponse<T> fail(ResponseEnum res, T data) {
        return new CommonResponse<>(res.resultCode(), res.resultMessage(), data);
    }
}

