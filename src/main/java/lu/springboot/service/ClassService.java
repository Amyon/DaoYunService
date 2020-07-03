package lu.springboot.service;

import lu.springboot.entity.dy_class;
import lu.springboot.exception.DaoYunException;
import lu.springboot.exception.ErrorCode;
import lu.springboot.mapper.ClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLDataException;
import java.sql.SQLException;

@Service
public class ClassService {

    @Autowired
    ClassMapper classMapper;

    public boolean joinClass(dy_class dyClass) throws SQLException {

        dy_class dyClass1 = classMapper.findClassById(dyClass.getClass_id(),dyClass.getUser_id());
        if(dyClass1 != null){
            throw new DaoYunException("course exist.",ErrorCode.JOIN_EXIST);
        }else {
            int isSuccess = classMapper.insert(dyClass);
            System.out.println(isSuccess);
            if (isSuccess > 0) {
                return true;
            }
        }
        return false;
    }


}
