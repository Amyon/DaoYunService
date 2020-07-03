package lu.springboot.service;

import lu.springboot.entity.dy_class_info;
import lu.springboot.mapper.ClassInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassInfoService {

    @Autowired
    ClassInfoMapper classInfoMapper;

    /**
     * 创建班课
     * @param dyClassInfo
     * @return
     */
    public int createCourse(dy_class_info dyClassInfo){

        classInfoMapper.insert(dyClassInfo);

        System.out.println(dyClassInfo.getClass_id());

        return dyClassInfo.getClass_id();

    }

    /**
     * 加入班课时，查看是否有此班课存在
     * @param class_id
     * @return
     */
    public dy_class_info findClassById(int class_id){
        dy_class_info dyClassInfo = classInfoMapper.finClassById(class_id);
        return dyClassInfo;
    }

}
