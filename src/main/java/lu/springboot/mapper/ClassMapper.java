package lu.springboot.mapper;

import lu.springboot.entity.dy_class;
import org.springframework.stereotype.Component;

@Component(value = "classMapper")
public interface ClassMapper {

    int insert(dy_class dyClass);

    dy_class findClassById(int class_id, String user_id);
}
