package lu.springboot.mapper;

import lu.springboot.entity.present;
import org.springframework.stereotype.Component;

@Component(value = "presentMapper")
public interface presentMapper {

    int insert(present p);

    boolean updateByID(int id,int state);

    present findPresentByClass_id(int class_id, int state);

    present findClassIdBy_PrescentId(int id);
}
