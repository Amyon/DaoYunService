package lu.springboot.service;

import lu.springboot.entity.dy_user;
import lu.springboot.exception.DaoYunException;
import lu.springboot.exception.ErrorCode;
import lu.springboot.mapper.SchoolInformationMapper;
import lu.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SchoolInformationMapper schoolInformationMapper;

    public dy_user findOneUserObject() {
        return userMapper.findFirstLine();
    }
    public void deleteFanyiByID(int id) {
        userMapper.deleteUserByID(id);
    }

    public void insert(dy_user user) {
        if (user == null) {
            return;
        }
        userMapper.insert(user);
    }

    /**
     * 用户登录
     * @param user_tele
     * @return
     */
    public dy_user login(String user_tele){
        dy_user user = userMapper.findUserByTele(user_tele);
        if(user == null){
            throw new DaoYunException("Tele is not exist", ErrorCode.TeleError);
        }
        return user;
    }


    /**
     * 用户注册
     * @param user
     * @return
     */
    public boolean signUp1(dy_user user){
        dy_user user1 = userMapper.findUserByTele(user.getUser_tele());
        dy_user user_id = userMapper.findUserByuser_id(user.getUser_id());
        //判断手机号是否注册
        if(user1 != null){
            throw new DaoYunException("Tele is exist", ErrorCode.TeleExist);
        }else if(user_id != null) {
            throw new DaoYunException("user_id is exist", ErrorCode.User_id_Exist);
        }else {
            userMapper.insert(user);
        }

        return true;
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public boolean changeInformation(dy_user user){

        return userMapper.updateByUser_tele(user);
    }

    /**
     * 比较旧密码是否正确
     * @param user_tele
     * @param oldPwd
     * @return
     */
    public boolean comparePwd(String user_tele, String oldPwd){
        dy_user user = userMapper.findUserByTele(user_tele);
        if(!user.getUser_pwd().equals(oldPwd)){
            throw new DaoYunException("旧密码不正确.",ErrorCode.OLDPWD_ERROR);
        }
        return true;
    }

    public dy_user createPermission(String user_tele){
        dy_user user = userMapper.findUserByTele(user_tele);
        if(user.getRole_id().equals("1")){
            return user;
        }else {
            throw new DaoYunException("没有权限创建班课",ErrorCode.NoPermission);
        }
    }

//    /**
//     * 用户注册
//     * @param user
//     * @param dyschoolInfo
//     * @return
//     */
//    public boolean signUp(dy_user user, dy_school_Info dyschoolInfo){
//        dy_user user1 = userMapper.findUserByTele(user.getUser_tele());
//        //判断手机号是否注册
//        if(user1 != null){
//            throw new DaoYunException("Tele is exist", ErrorCode.TeleExist);
//        }else {
//            dy_school_Info dyschoolInfo1 = schoolInformationMapper.ExistSchoolInfo(dyschoolInfo);
//            //查看注册的学校信息是否存在
//            if(dyschoolInfo1 != null){
//                //存在就返回学校ID
//                user.setSchool_id(dyschoolInfo1.getSchool_id());
//            }else {
//                //不存在学校信息就插入并返回学校ID
//                schoolInformationMapper.insert(dyschoolInfo);
//                user.setSchool_id(dyschoolInfo.getSchool_id());
//            }
//            //手机号未注册，插入个人信息。
//            userMapper.insert(user);
//        }
//        return true;
//    }

}
