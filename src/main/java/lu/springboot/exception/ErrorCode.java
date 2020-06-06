package lu.springboot.exception;

import lombok.Getter;

public enum ErrorCode {

    //无此账号
    TeleError(1,"该手机号未注册"),

    FAILED(1, "login fail"),

    // 无权限
    FORBIDDEN(403, "forbidden"),

    // 参数错误
    PARAM_ERROR(20, "输入错误");




    @Getter
    private int code;

    @Getter
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
