package lu.springboot.mapper;

import lu.springboot.entity.SchoolInformation;
import org.springframework.stereotype.Component;


@Component(value = "schoolInfo")
public interface SchoolInformationMapper {

    SchoolInformation findSchoolInfoById(int id);

}
