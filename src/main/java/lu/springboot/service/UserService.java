package lu.springboot.service;

import lu.springboot.entity.SchoolInformation;
import lu.springboot.entity.User;
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

    public User findOneUserObject() {
        return userMapper.findFirstLine();
    }
    public void deleteFanyiByID(int id) {
        userMapper.deleteUserByID(id);
    }

    public void insert(User user) {
        if (user == null) {
            return;
        }
        userMapper.insert(user);
    }

    public void updateByID(User user) {
        if (user == null) {
            return;
        }
        userMapper.updateByID(user);
    }

    /**
     * 用户登录
     * @param Tele
     * @return
     */
    public User login(String Tele){
        User user = userMapper.findUserByTele(Tele);
        if(user == null){
            throw new DaoYunException("Tele is not exist", ErrorCode.TeleError);
        }
        return user;
    }

    /**
     * 用户注册
     * @param user
     * @param schoolInformation
     * @return
     */
    public boolean signUp(User user, SchoolInformation schoolInformation){
        User user1 = userMapper.findUserByTele(user.getTele());
        //判断手机号是否注册
        if(user1 != null){
            throw new DaoYunException("Tele is exist", ErrorCode.TeleExist);
        }else {
            SchoolInformation schoolInformation1 = schoolInformationMapper.ExistSchoolInfo(schoolInformation);
            //查看注册的学校信息是否存在
            if(schoolInformation1 != null){
                //存在就返回学校ID
                user.setSchoolInfo(schoolInformation1.getID());
            }else {
                //不存在学校信息就插入并返回学校ID
                schoolInformationMapper.insert(schoolInformation);
                user.setSchoolInfo(schoolInformation.getID());
            }
            //手机号未注册，插入个人信息。
            userMapper.insert(user);
        }
        return true;
    }
}
