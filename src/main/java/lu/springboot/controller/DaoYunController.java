package lu.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import lu.springboot.annotation.UserLoginToken;
import lu.springboot.common.DaoYunConstant;
import lu.springboot.common.ResponseResult;
import lu.springboot.entity.dy_school_Info;
import lu.springboot.entity.dy_user;
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
import java.sql.Date;

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
        dy_user user = userService.login(Tele);
        Date date = user.getGmt_create();
        System.out.println(date);
        if(user.getUser_pwd().equals(Password)){
            String token = tokenService.getToken(user);
            jsonObject.put("dy_user", user);
            jsonObject.put("dy_school_info", schoolInformationService.getSchoolInfo(user.getSchool_parent_id()));
            jsonObject.put("token",token);

            return ResponseResult.newSuccessResult(jsonObject, DaoYunConstant.LOGIN_SUCCESS);
        }
        return ResponseResult.newFailedResult(1, DaoYunConstant.LOGIN_FAIL);

    }

    /**
     * 注册功能
     * @param req
     * @param resp
     * @param UserID
     * @param UserName
     * @param Sex
     * @param Tele
     * @param PassWord
     * @param college
     * @param Faculty
     * @param Major
     * @return
     * @throws DaoYunException
     */
    @RequestMapping("/signup")
    public ResponseResult signup(HttpServletRequest req,
                                 HttpServletResponse resp,
                                 @RequestParam(value = "UserID", required = true)String UserID,
                                 @RequestParam(value = "UserName", required = true)String UserName,
                                 @RequestParam(value = "Sex", required = true)int Sex,
                                 @RequestParam(value = "Tele", required = true)String Tele,
                                 @RequestParam(value = "PassWord", required = true)String PassWord,
                                 @RequestParam(value = "college", required = true)String college,
                                 @RequestParam(value = "Faculty", required = true)String Faculty,
                                 @RequestParam(value = "Major", required = true)String Major) throws DaoYunException{

        JSONObject jsonObject = new JSONObject();

//        注册信息封装到实体
        dy_user user = new dy_user(UserID, UserName, Sex, Tele, PassWord);
        dy_school_Info dyschoolInfo = new dy_school_Info(college, Faculty);

        if(userService.signUp(user, dyschoolInfo)){
            return ResponseResult.newSuccessResult(jsonObject, DaoYunConstant.SIGNUP_SUCCESS);
        }
        return ResponseResult.newFailedResult(1,DaoYunConstant.SIGNUP_FAIL);

    }

    @UserLoginToken
    @GetMapping("/getMessage")
    public ResponseResult getMessage(HttpServletRequest req,
                                     HttpServletResponse resp) throws DaoYunException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("test","you pass Auth");
        // 取出token中带的用户id 进行操作
        System.out.println(TokenUtil.getTokenTele());
        return ResponseResult.newSuccessResult(jsonObject,DaoYunConstant.LOGIN_SUCCESS);
    }
}
