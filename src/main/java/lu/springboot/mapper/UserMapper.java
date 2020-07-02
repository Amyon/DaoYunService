package lu.springboot.mapper;

import lu.springboot.entity.dy_user;
import org.springframework.stereotype.Component;

@Component(value = "userMapper")
public interface UserMapper {
    dy_user findFirstLine();
    void deleteUserByID(int ID);
    void insert(dy_user user);
    boolean updateByUser_tele(dy_user user);
    dy_user findUserByTele(String user_tele);
    dy_user findUserByuser_id(String user_id);


}
