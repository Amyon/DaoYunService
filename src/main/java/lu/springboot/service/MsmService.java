package lu.springboot.service;

import java.util.Map;

/**
 * @author johnrambo
 * @create 2020-05-24 20:44
 */
public interface MsmService {
    //发送短信的方法
    boolean send(Map<String, Object> param, String phone);
}
