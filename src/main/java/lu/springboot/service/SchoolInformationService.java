package lu.springboot.service;

import lu.springboot.entity.dy_class_info;
import lu.springboot.entity.dy_school_info;
import lu.springboot.exception.DaoYunException;
import lu.springboot.exception.ErrorCode;
import lu.springboot.mapper.ClassInfoMapper;
import lu.springboot.mapper.SchoolInformationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolInformationService {

    @Autowired
    SchoolInformationMapper schoolInformationMapper;

    @Autowired
    ClassInfoMapper classInfoMapper;

    public dy_school_info getSchoolInfo(String school_parent_id){
        return schoolInformationMapper.findSchoolInfoById(school_parent_id);

    }

    /**
     * 老师查看自己创建的所有班课
     * @param user_id
     * @return
     */
    public List<dy_class_info> teacherFindClass(String user_id) {
        List<dy_class_info> dyClassInfos = classInfoMapper.teacherFindClass(user_id);
        if (dyClassInfos.isEmpty()){
            throw new DaoYunException("no create class", ErrorCode.TEACHER_NO_CLASS);
        }

        return dyClassInfos;
    }


}
