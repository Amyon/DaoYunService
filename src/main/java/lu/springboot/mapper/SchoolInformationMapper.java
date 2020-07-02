package lu.springboot.mapper;

import lu.springboot.entity.dy_school_info;
import org.springframework.stereotype.Component;


@Component(value = "schoolInfo")
public interface SchoolInformationMapper {

    dy_school_info findSchoolInfoById(String school_parent_id);

    dy_school_info ExistSchoolInfo(dy_school_info dyschoolInfo);

    int insert(dy_school_info dyschoolInfo);

}
