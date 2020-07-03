package lu.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import lu.springboot.annotation.UserLoginToken;
import lu.springboot.common.DaoYunConstant;
import lu.springboot.common.ResponseResult;
import lu.springboot.entity.dy_class;
import lu.springboot.entity.dy_class_info;
import lu.springboot.entity.dy_user;
import lu.springboot.exception.DaoYunException;
import lu.springboot.exception.ErrorCode;
import lu.springboot.mapper.UserMapper;
import lu.springboot.service.*;
import lu.springboot.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

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
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClassService classService;

    @RequestMapping("/login")
    public ResponseResult login(HttpServletRequest req,
                                HttpServletResponse resp,
      @RequestParam(value = "Tele", required = true) String Tele,
      @RequestParam(value = "Password", required = true) String Password) throws DaoYunException{

        //数据填充
        JSONObject jsonObject = new JSONObject();
        dy_user user = userService.login(Tele);
        if(user.getUser_pwd().equals(Password)){
            String token = tokenService.getToken(user);
            jsonObject.put("dy_user", user);
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
                                 @RequestParam(value = "school_id", required = true)String school_id,
                                 @RequestParam(value = "school_parent_id", required = true)String school_parent_id) throws DaoYunException{

        JSONObject jsonObject = new JSONObject();

//        注册信息封装到实体
        dy_user user = new dy_user(UserID, UserName, Sex, Tele, PassWord, school_id, school_parent_id);
        //注册操作
        if(userService.signUp1(user)){
            return ResponseResult.newSuccessResult(jsonObject, DaoYunConstant.SIGNUP_SUCCESS);
        }
        return ResponseResult.newFailedResult(1,DaoYunConstant.SIGNUP_FAIL);

    }

    /**
     * 修改用户信息
     * @param req
     * @param resp
     * @return
     * @throws DaoYunException
     */
    @UserLoginToken
    @GetMapping("/changeinfo")
    public ResponseResult changeInfo(HttpServletRequest req,
                                     HttpServletResponse resp,
                                     @RequestParam(value="user_name",required = true)String user_name) throws DaoYunException {
        JSONObject jsonObject = new JSONObject();

        // 取出token中带的user_id 进行操作
        String user_tele = TokenUtil.getTokenTele();
//        System.out.println(user_tele);
        //把修改的信息实体化
        dy_user user = new dy_user(user_tele, user_name, null);
        if(userService.changeInformation(user)){
            return ResponseResult.newSuccessResult(jsonObject, DaoYunConstant.CHANGEINFO_SUCCESS);
        }
        return ResponseResult.newFailedResult(1,DaoYunConstant.CHANGEINFO_FAIL);
    }

    /**
     * 修改密码
     * @param req
     * @param resp
     * @param OldPW
     * @param NewPW
     * @return
     * @throws DaoYunException
     */
    @UserLoginToken
    @GetMapping("/changePW")
    public ResponseResult changePW(HttpServletRequest req,
                                     HttpServletResponse resp,
                                     @RequestParam(value="OldPW",required = true)String OldPW,
                                     @RequestParam(value="NewPW",required = true)String NewPW) throws DaoYunException {
        JSONObject jsonObject = new JSONObject();

        // 取出token中带的user_tele 进行操作
        String user_tele = TokenUtil.getTokenTele();
        //把修改的信息实体化
        if(userService.comparePwd(user_tele,OldPW)){
            dy_user user = new dy_user(user_tele, null, NewPW);
            if(userService.changeInformation(user)){
                return ResponseResult.newSuccessResult(jsonObject, DaoYunConstant.CHANGEINFO_SUCCESS);
            }

        }
        return ResponseResult.newFailedResult(1,DaoYunConstant.CHANGEINFO_FAIL);
    }

    /**
     * 创建班课
     * @param req
     * @param resp
     * @param class_name
     * @param course_name
     * @param section
     * @param school_info
     * @return
     * @throws DaoYunException
     */
    @UserLoginToken
    @GetMapping("/createclass")
    public ResponseResult createClass(HttpServletRequest req,
                                   HttpServletResponse resp,
                                   @RequestParam(value="class_name",required = true)String class_name,
                                   @RequestParam(value="course_name",required = true)String course_name,
                                  @RequestParam(value = "section",required = true)String section,
                                  @RequestParam(value = "school_info",required = true)String school_info) throws DaoYunException {
        JSONObject jsonObject = new JSONObject();

        // 取出token中带的user_tele 进行操作
        String user_tele = TokenUtil.getTokenTele();
        //判断用户是否有创建班课的权限,并取出该用户信息
        dy_user user = userService.createPermission(user_tele);
        if(user != null){
            //创建的信息实体化
            String user_id = user.getUser_id();
            dy_class_info dyClassInfo = new dy_class_info(class_name, course_name, section, school_info, user_id);
            int class_id = classInfoService.createCourse(dyClassInfo);
            jsonObject.put("class_id", class_id);
            return ResponseResult.newSuccessResult(jsonObject, DaoYunConstant.CREATE_SUCCESS);
        }
        return ResponseResult.newFailedResult(1,DaoYunConstant.CREATE_FAIL);
    }

    /**
     * 加入班课
     * @param req
     * @param resp
     * @param class_id
     * @return
     * @throws DaoYunException
     */
    @UserLoginToken
    @GetMapping("/joinclass")
    public ResponseResult joinClass(HttpServletRequest req,
                                   HttpServletResponse resp,
                                   @RequestParam(value="class_id",required = true)int class_id) throws DaoYunException, SQLException {
        JSONObject jsonObject = new JSONObject();

        // 取出token中带的user_tele 进行操作
        String user_tele = TokenUtil.getTokenTele();
        //取出用户信息
        dy_user user =userMapper.findUserByTele(user_tele);
        //初始化插入信息，生成实体对象
        dy_class dyClass = new dy_class(class_id, user.getUser_id());
        //判断是否有此班课
        if(classInfoService.findClassById(class_id) == null){
            throw new DaoYunException("class_id error",ErrorCode.JOIN_ERROR);
        }else if(!classService.joinClass(dyClass)){
            return ResponseResult.newFailedResult(1,DaoYunConstant.JOIN_FAIL);
        }else {
            return ResponseResult.newSuccessResult(jsonObject,DaoYunConstant.JOIN_SUCCESS);

        }




    }


}
