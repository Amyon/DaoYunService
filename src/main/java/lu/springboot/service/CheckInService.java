package lu.springboot.service;


import lu.springboot.entity.checkin;
import lu.springboot.exception.DaoYunException;
import lu.springboot.exception.ErrorCode;
import lu.springboot.mapper.CheckinMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckInService {

    @Autowired
    CheckinMapper checkinMapper;

    public boolean createCheckIn(checkin checkin){
        checkin c = checkinMapper.findCheckIn(checkin);
        if(c != null){
            throw new DaoYunException("you has checkin", ErrorCode.HAS_CHECKIN);
        }
        return checkinMapper.insert(checkin);
    }
}
