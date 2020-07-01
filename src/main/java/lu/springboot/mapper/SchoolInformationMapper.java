package lu.springboot.mapper;

import lu.springboot.entity.dy_school_Info;
import org.springframework.stereotype.Component;


@Component(value = "schoolInfo")
public interface SchoolInformationMapper {

    dy_school_Info findSchoolInfoById(String school_parent_id);

    dy_school_Info ExistSchoolInfo(dy_school_Info dyschoolInfo);

    int insert(dy_school_Info dyschoolInfo);

}
