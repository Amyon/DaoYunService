package lu.springboot.common;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * 按规范格式返回响应封装类
 * @author zhang
 *
 */
public class ResponseResult<T> {

    @Setter
    @Getter
    private int code;

    @Setter
    @Getter
    private String msg;

    @Setter
    @Getter
    private T data;

    public static ResponseResult newFailedResult(int code, String msg) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(code);
        responseResult.setMsg(msg);
        return responseResult;
    }

    public static ResponseResult newSuccessResult(JSONObject data) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(0);
        responseResult.setMsg(DaoYunConstant.LOGIN_SUCCESS);
        responseResult.setData(data);
        return responseResult;
    }

}