package lu.springboot.service;

import lu.springboot.entity.User;
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
}
