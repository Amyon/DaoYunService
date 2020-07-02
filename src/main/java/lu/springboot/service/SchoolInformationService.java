package lu.springboot.service;

import lu.springboot.entity.dy_school_info;
import lu.springboot.mapper.SchoolInformationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolInformationService {

    @Autowired
    SchoolInformationMapper schoolInformationMapper;

    public dy_school_info getSchoolInfo(String school_parent_id){
        return schoolInformationMapper.findSchoolInfoById(school_parent_id);

    }

}
