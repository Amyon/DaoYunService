package lu.springboot.mapper;

import lu.springboot.entity.User;
import org.springframework.stereotype.Component;

@Component(value = "userMapper")
public interface UserMapper {
    User findFirstLine();
    void deleteUserByID(int ID);
    void insert(User user);
    void updateByID(User user);
    User findUserByTele(String Tele);


}
