package com.ssg.test.base.response;


import com.ssg.test.base.enums.ResponseFailEnum;
import com.ssg.test.base.enums.ResponseSuccessEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommonResponse<T> {

    private final String resultCode;
    private final String resultMessage;
    private final T resultData;

    public static <T> CommonResponse<T> success(ResponseSuccessEnum res, T data) {
        return new CommonResponse<>(res.resultCode(), res.resultMessage(), data);
    }

    public static <T> CommonResponse<T> fail(ResponseFailEnum res, T data) {
        return new CommonResponse<>(res.resultCode(), res.resultMessage(), data);
    }
}

