package lu.springboot.service;

import lu.springboot.entity.User;
import lu.springboot.exception.DaoYunException;
import lu.springboot.exception.ErrorCode;
import lu.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

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
}
