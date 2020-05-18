package lu.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import lu.springboot.common.DaoYunConstant;
import lu.springboot.common.ResponseResult;
import lu.springboot.entity.User;
import lu.springboot.exception.DaoYunException;
import lu.springboot.service.SchoolInformationService;
import lu.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/login")
    public ResponseResult login(HttpServletRequest req,
                                HttpServletResponse resp,
      @RequestParam(value = "UserID", required = true) String UserID,
      @RequestParam(value = "Password", required = true) String Password) throws DaoYunException{

        JSONObject jsonObject = new JSONObject();
        User user = userService.login(UserID, Password);
        if(user.getPassWord().equals(Password)){
            jsonObject.put("User", user);
            jsonObject.put("SchoolInformation", schoolInformationService.getSchoolInfo(user.getSchoolInfo()));
            return ResponseResult.newSuccessResult(jsonObject);
        }
        return ResponseResult.newFailedResult(1, DaoYunConstant.LOGIN_FAIL);

    }

    @RequestMapping(value = "user")
    @ResponseBody
    public User operateUser(){
        User result = userService.findOneUserObject();

        log.info(result.toString());
        return result;
    }
}
