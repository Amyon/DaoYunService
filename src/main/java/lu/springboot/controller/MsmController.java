package lu.springboot.controller;

//import edu.fzu.commonutils.R;


//import io.swagger.annotations.ApiOperation;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lu.springboot.common.ResponseResult;
import lu.springboot.exception.DaoYunException;
import lu.springboot.service.MsmService;
import lu.springboot.utils.CacheUtil;
import lu.springboot.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author johnrambo
 * @create 2020-05-24 20:44
 */

@RestController
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;


    //发送短信的方法
//    @ApiOperation(value = "发送短信的方法")
//    @GetMapping("send/{phone}")
    @RequestMapping("/msm")
    public ResponseResult sendMsm(HttpServletRequest req,
                                  HttpServletResponse resp,
                                  @RequestParam(value="phone",required = true)String phone) throws DaoYunException
    {
        //1.从redis中获取验证码，如果可以取到，直接返回
//        String code = redisTemplate.opsForValue().get(phone);
        String code = (String) CacheUtil.cache.getIfPresent(phone);
        if(!StringUtils.isEmpty(code)) return ResponseResult.newSuccessResultWithout("验证码发送成功");

        //2.如果获取不到，使用阿里云发送验证码
        //生成一个随机值，传递给阿里云进行发送
        code = RandomUtil.getSixBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);

        //调用service中发送短信的方法
        boolean isSend = msmService.send(param, phone);
        if(isSend)
        {
            //如果发送成功，把发送成功的验证码放入redis
            //redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            CacheUtil.cache.put(phone, code);
            return ResponseResult.newSuccessResultWithout("验证码发送成功");
        }
        else
        {
            return ResponseResult.newSuccessResultWithout("验证码发送失败");
        }
    }

}
