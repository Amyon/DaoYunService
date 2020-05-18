package lu.springboot.service;

import lu.springboot.entity.SchoolInformation;
import lu.springboot.mapper.SchoolInformationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolInformationService {

    @Autowired
    SchoolInformationMapper schoolInformationMapper;

    public SchoolInformation getSchoolInfo(int id){
        return schoolInformationMapper.findSchoolInfoById(id);

    }
}
