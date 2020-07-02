package lu.springboot.service;

import lu.springboot.entity.dy_class_info;
import lu.springboot.mapper.ClassInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassInfoService {

    @Autowired
    ClassInfoMapper classInfoMapper;

    public int createCourse(dy_class_info dyClassInfo){

        classInfoMapper.insert(dyClassInfo);

        System.out.println(dyClassInfo.getClass_id());

        return dyClassInfo.getClass_id();

    }


}
