package lu.springboot.exception;

import lombok.Getter;

public enum ErrorCode {

    //无此账号
    TeleError(1,"手机号未注册"),

    TeleExist(1,"手机号已注册"),

    FAILED(1, "login fail"),

    // 无权限
    NoPermission(403, "无创建权限."),

    // 参数错误
    PARAM_ERROR(20, "输入错误"),

    OLDPWD_ERROR(20, "旧密码错误"),

    User_id_Exist(1,"User_id 已注册");






    @Getter
    private int code;

    @Getter
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
