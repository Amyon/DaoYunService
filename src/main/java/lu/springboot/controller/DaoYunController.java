package lu.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import lu.springboot.annotation.UserLoginToken;
import lu.springboot.common.DaoYunConstant;
import lu.springboot.common.ResponseResult;
import lu.springboot.entity.SchoolInformation;
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
                                 @RequestParam(value = "Sex", required = true)String Sex,
                                 @RequestParam(value = "Tele", required = true)String Tele,
                                 @RequestParam(value = "PassWord", required = true)String PassWord,
                                 @RequestParam(value = "college", required = true)String college,
                                 @RequestParam(value = "Faculty", required = true)String Faculty,
                                 @RequestParam(value = "Major", required = true)String Major) throws DaoYunException{

        JSONObject jsonObject = new JSONObject();

//        注册信息封装到实体
        User user = new User(UserID, UserName, Sex, Tele, PassWord);
        SchoolInformation schoolInformation = new SchoolInformation(college, Faculty, Major);

        if(userService.signUp(user,schoolInformation)){
            return ResponseResult.newSuccessResult(jsonObject, DaoYunConstant.SIGNUP_SUCCESS);
        }
        return ResponseResult.newFailedResult(1,DaoYunConstant.SIGNUP_FAIL);

    }

    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage() {

        // 取出token中带的用户id 进行操作
        System.out.println(TokenUtil.getTokenTele());
        return "you pass Auth";
    }
}
