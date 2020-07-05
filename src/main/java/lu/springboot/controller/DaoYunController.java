package lu.springboot.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import lu.springboot.annotation.UserLoginToken;
import lu.springboot.common.DaoYunConstant;
import lu.springboot.common.ResponseResult;
import lu.springboot.entity.*;
import lu.springboot.exception.DaoYunException;
import lu.springboot.exception.ErrorCode;
import lu.springboot.mapper.UserMapper;
import lu.springboot.service.*;
import lu.springboot.utils.CacheUtil;
import lu.springboot.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *部分功能控制器
 * @author zhang
 */
@RestController
@Slf4j
public class DaoYunController{

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
    @Autowired
    private presentService presentService;
    @Autowired
    private CheckInService checkInService;

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
                                 @RequestParam(value = "school_parent_id", required = true)String school_parent_id,
                                 @RequestParam(value = "code", required = true)String code) throws DaoYunException{

//        验证短信验证码
        Object object = CacheUtil.cache.getIfPresent(Tele);
        //判断是否发生验证码了
        if(object == null){
            return ResponseResult.newFailedResult(1,DaoYunConstant.CODE_isEXIST);
        }else {
            String cacheCode = (String) object;
            //判断验证码是否正确
            if(!cacheCode.equals(code)){
                return ResponseResult.newFailedResult(1,DaoYunConstant.CODE_ERROR);
            }else {
                JSONObject jsonObject = new JSONObject();

//        注册信息封装到实体
                dy_user user = new dy_user(UserID, UserName, Sex, Tele, PassWord, school_id, school_parent_id);
                //注册操作
                if(userService.signUp1(user)){
                    return ResponseResult.newSuccessResult(jsonObject, DaoYunConstant.SIGNUP_SUCCESS);
                }
                return ResponseResult.newFailedResult(1,DaoYunConstant.SIGNUP_FAIL);
            }
        }


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
        //判断有没有这个班课
        if(classInfoService.findClassById(class_id) == null){
            throw new DaoYunException("class_id error",ErrorCode.JOIN_ERROR);
        }else if(!classService.joinClass(dyClass)){
            //加入班课失败
            return ResponseResult.newFailedResult(1,DaoYunConstant.JOIN_FAIL);
        }else {
            return ResponseResult.newSuccessResult(jsonObject,DaoYunConstant.JOIN_SUCCESS);

        }
    }

    /**
     * 查看已加入的班课
     * @param req
     * @param resp
     * @return
     * @throws DaoYunException
     * @throws SQLException
     */

    @UserLoginToken
    @GetMapping("/myclass")
    public ResponseResult myClass(HttpServletRequest req,
                                    HttpServletResponse resp) throws DaoYunException, SQLException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        // 取出token中带的user_tele 进行操作
        String user_tele = TokenUtil.getTokenTele();
        //取出用户信息
        dy_user user = userMapper.findUserByTele(user_tele);

        //学生用户，查班课
        if(user.getRole_id().equals("2")){
            //查询加入的所有班课信息
            List<dy_class_info> dyClassInfo = classService.findMyAllClass(user.getUser_id());
            //查询每个班课的老师
            for (dy_class_info i: dyClassInfo) {
                //得到该课程的创建者
                dy_user user1 = userMapper.findUserByuser_id(i.getUser_id());
                String teacherName = user1.getUser_name();
                JSONObject jsonObject1 = new JSONObject();
                //封装返回值
                jsonObject1.put("class_id", i.getClass_id());
                jsonObject1.put("class_name",i.getClass_name());
                jsonObject1.put("course_name", i.getCourse_name());
                jsonObject1.put("section",i.getSection());
                jsonObject1.put("school_info", i.getSchool_info());
                jsonObject1.put("teacher_name",teacherName);
                jsonArray.add(jsonObject1);
            }
            jsonObject.put("dy_class_info",jsonArray);

        }else if(user.getRole_id().equals("1")){
            //教师查看创建的所有的班课
            List<dy_class_info> dy_class_infos = schoolInformationService.teacherFindClass(user.getUser_id());
            jsonObject.put("dy_class_info",dy_class_infos);
        }
        return ResponseResult.newSuccessResult(jsonObject,DaoYunConstant.MYALLCLASS_SUCCESS);
    }


    @UserLoginToken
    @GetMapping("/myclassdetail")
    public ResponseResult myClassDetail(HttpServletRequest req,
                                    HttpServletResponse resp,
                                    @RequestParam(value="class_id",required = true)int class_id) throws DaoYunException, SQLException {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        // 取出token中带的user_tele 进行操作
        String user_tele = TokenUtil.getTokenTele();
        //取出用户信息
        dy_user user =userMapper.findUserByTele(user_tele);
        //得到所有选这门课的user_id
        List<dy_class> allUserOfClass= classService.findAllUser_InClass(user.getUser_id(), class_id);

        for (dy_class i : allUserOfClass) {
            //获取所有选这门课的学生信息
            dy_user dyUser = userMapper.findUserByuser_id(i.getUser_id());
            String userName = dyUser.getUser_name();
            JSONObject jsonObject1 = new JSONObject();
            //数据封装
            jsonObject1.put("user_id", i.getUser_id());
            jsonObject1.put("user_name", userName);
            jsonObject1.put("score", i.getScore());

            jsonArray.add(jsonObject1);

        }

        jsonObject.put("user", jsonArray);
        return ResponseResult.newSuccessResult(jsonObject,DaoYunConstant.AllUSER_OF_CLASS_SUCCESS);
    }

    /**
     * 发起签到
     * @param req
     * @param resp
     * @param class_id
     * @param longitude
     * @param latitude
     * @return
     * @throws DaoYunException
     * @throws SQLException
     */
    @UserLoginToken
    @GetMapping("/createpresent")
    public ResponseResult createPresent(HttpServletRequest req,
                                        HttpServletResponse resp,
                                        @RequestParam(value="class_id",required = true)int class_id,
                                        @RequestParam(value = "longitude", required = true)String longitude,
                                        @RequestParam(value = "latitude", required = true)String latitude) throws DaoYunException, SQLException {
        JSONObject jsonObject = new JSONObject();
        // 取出token中带的user_tele 进行操作
        String user_tele = TokenUtil.getTokenTele();

        present p = new present(class_id, longitude,latitude,0 );

        int present_id = presentService.createPresent(p);

        jsonObject.put("present_id", present_id);
        return ResponseResult.newSuccessResult(jsonObject,DaoYunConstant.CREATEPRESENT_SUCCESS);
    }

    /**
     * 老师结束签到
     * @param req
     * @param resp
     * @param present_id
     * @return
     * @throws DaoYunException
     * @throws SQLException
     */
    @UserLoginToken
    @GetMapping("/finishpresent")
    public ResponseResult finshPresent(HttpServletRequest req,
                                        HttpServletResponse resp,
                                        @RequestParam(value="present_id",required = true)int present_id) throws DaoYunException, SQLException {
        JSONObject jsonObject = new JSONObject();
        // 取出token中带的user_tele 进行操作
        String user_tele = TokenUtil.getTokenTele();

        if(!presentService.finishPressent(present_id)){
            return ResponseResult.newFailedResult(1,DaoYunConstant.FINISHPRESENT_FAIL);
        }

//        jsonObject.put("present_id", present_id);
        return ResponseResult.newSuccessResultWithout(DaoYunConstant.FINISHPRESENT_SUCCESS);
    }


    /**
     *
     * 学生查询签到课程
      * @param req
     * @param resp
     * @param class_id
     * @return
     * @throws DaoYunException
     * @throws SQLException
     */
    @UserLoginToken
    @GetMapping("/getpresent")
    public ResponseResult getPresent(HttpServletRequest req,
                                       HttpServletResponse resp,
                                       @RequestParam(value="class_id",required = true)int class_id) throws DaoYunException, SQLException {
        JSONObject jsonObject = new JSONObject();
        // 取出token中带的user_tele 进行操作
        String user_tele = TokenUtil.getTokenTele();

        present p = presentService.getPresent(class_id);
        if(p == null){
            return ResponseResult.newFailedResult(1,DaoYunConstant.GETPRESCENT_FAIL);
        }

        jsonObject.put("present_id", p.getId());
        return ResponseResult.newSuccessResult(jsonObject, DaoYunConstant.GETPRESCENT_SUCCESS);
    }

    @UserLoginToken
    @GetMapping("/createcheckin")
    public ResponseResult createCheckIn(HttpServletRequest req,
                                     HttpServletResponse resp,
                                     @RequestParam(value="present_id",required = true)int present_id ) throws DaoYunException, SQLException {
        JSONObject jsonObject = new JSONObject();
        // 取出token中带的user_tele 进行操作
        String user_tele = TokenUtil.getTokenTele();
        //取出用户信息
        dy_user user =userMapper.findUserByTele(user_tele);
        String user_id = user.getUser_id();
        checkin c = new checkin(user_id, present_id);
        //签到插入数据
        boolean isCheckIn = checkInService.createCheckIn(c);
        if(isCheckIn){
            //取出签到号的课程id
            present p = presentService.getClass_id(present_id);
            if(p != null){
                //加经验成功，
                if(classService.addScore(user_id,p.getClass_id())){
                    return ResponseResult.newSuccessResultWithout(DaoYunConstant.CHECKIN_SUCCESS);
                }
            }
        }
        return ResponseResult.newFailedResult(1,DaoYunConstant.CHECKIN_FAIL);

    }

}
