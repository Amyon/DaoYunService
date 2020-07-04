package lu.springboot.service;

import lu.springboot.entity.dy_class;
import lu.springboot.entity.dy_class_info;
import lu.springboot.exception.DaoYunException;
import lu.springboot.exception.ErrorCode;
import lu.springboot.mapper.ClassInfoMapper;
import lu.springboot.mapper.ClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;

@Service
public class ClassService {

    @Autowired
    ClassMapper classMapper;
    @Autowired
    ClassInfoMapper classInfoMapper;

    public boolean joinClass(dy_class dyClass) throws SQLException {

        dy_class dyClass1 = classMapper.findClassById(dyClass.getClass_id(),dyClass.getUser_id());
        if(dyClass1 != null){
            //已经加入该班课
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

    /**
     * 查看我加入的所有班课
     * @param user_id
     * @return
     */
    public List<dy_class_info> findMyAllClass(String user_id) {

        List<dy_class> myClassList = classMapper.findMyAllClass(user_id);
        if(myClassList.isEmpty()){
            throw new DaoYunException("no join any class", ErrorCode.NO_JOIN_CLASS);
        }

        List<dy_class_info> myClassInfoList = classInfoMapper.findMyAllClassInfo(myClassList);

        return myClassInfoList;
    }


    /**
     * 查看这个班级里的所有人
     * @param user_id
     * @param class_id
     * @return
     */
    public List<dy_class> findAllUser_InClass(String user_id, int class_id){
        dy_class dyClass = classMapper.findClassById(class_id, user_id);
        if(dyClass == null){
            throw new DaoYunException("this class no exist",ErrorCode.NO_THIS_CLASS);
        }
        List<dy_class> allUserOfClass = classMapper.findAllUserOfClass(class_id);

        return allUserOfClass;
    }


}
