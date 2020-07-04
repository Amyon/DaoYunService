package lu.springboot.mapper;


import lu.springboot.entity.dy_class;
import lu.springboot.entity.dy_class_info;
import lu.springboot.entity.dy_user;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "classInfoMapper")
public interface ClassInfoMapper {


    int insert(dy_class_info dyClassInfo);

    dy_class_info finClassById(int class_id);

    List<dy_class_info> findMyAllClassInfo(List<dy_class> myClassList);

    List<dy_class_info> teacherFindClass(String user_id);
}
