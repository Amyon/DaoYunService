package lu.springboot.mapper;

import lu.springboot.entity.checkin;
import org.springframework.stereotype.Component;

@Component(value = "checkin")
public interface CheckinMapper {

    //插入签到记录
    boolean insert(checkin checkin);
    //查询是否已经存在签到记录
    checkin findCheckIn(checkin checkin);

}
