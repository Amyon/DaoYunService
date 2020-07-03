package lu.springboot.mapper;

import lu.springboot.entity.dy_class;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "classMapper")
public interface ClassMapper {

    int insert(dy_class dyClass);

    dy_class findClassById(int class_id, String user_id);

    //查找该用户所有的课程
    List<dy_class> findMyAllClass(String user_id);

    //查找该课程所有的用户
    List<dy_class> findAllUserOfClass(int class_id);
}
