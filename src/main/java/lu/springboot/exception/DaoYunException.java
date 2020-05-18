package lu.springboot.exception;


import lombok.Getter;
import lu.springboot.common.DaoYunConstant;

public class DaoYunException extends RuntimeException {

    @Getter
    private ErrorCode errorCode;

    public DaoYunException(String msg) {
        super(msg);
    }

    public DaoYunException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public DaoYunException(String msg, ErrorCode errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }

}
