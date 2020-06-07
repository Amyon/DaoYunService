package lu.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import lu.springboot.annotation.UserLoginToken;
import lu.springboot.common.DaoYunConstant;
import lu.springboot.common.ResponseResult;
import lu.springboot.entity.User;
import lu.springboot.exception.DaoYunException;
import lu.springboot.service.SchoolInformationService;
import lu.springboot.service.TokenService;
import lu.springboot.service.UserService;
import lu.springboot.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *部分功能控制器
 * @author zhang
 */
@RestController
@Slf4j
public class DaoYunController {

    @Autowired
    private UserService userService;
    @Autowired
    private SchoolInformationService schoolInformationService;
    @Autowired
    private TokenService tokenService;

    @RequestMapping("/login")
    public ResponseResult login(HttpServletRequest req,
                                HttpServletResponse resp,
      @RequestParam(value = "Tele", required = true) String Tele,
      @RequestParam(value = "Password", required = true) String Password) throws DaoYunException{

        //数据填充
        JSONObject jsonObject = new JSONObject();
        User user = userService.login(Tele);
        if(user.getPassWord().equals(Password)){
            String token = tokenService.getToken(user);
            jsonObject.put("User", user);
            jsonObject.put("SchoolInformation", schoolInformationService.getSchoolInfo(user.getSchoolInfo()));

            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            resp.addCookie(cookie);

            return ResponseResult.newSuccessResult(jsonObject);
        }
        return ResponseResult.newFailedResult(1, DaoYunConstant.LOGIN_FAIL);

    }
    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage() {

        // 取出token中带的用户id 进行操作
        System.out.println(TokenUtil.getTokenTele());
        return "you pass Auth";
    }


    @RequestMapping(value = "user")
    @ResponseBody
    public User operateUser(){
        User result = userService.findOneUserObject();

        log.info(result.toString());
        return result;
    }
}
