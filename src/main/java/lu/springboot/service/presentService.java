package lu.springboot.service;

import lu.springboot.entity.dy_class;
import lu.springboot.entity.present;
import lu.springboot.exception.DaoYunException;
import lu.springboot.exception.ErrorCode;
import lu.springboot.mapper.presentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class presentService {
    @Autowired
    presentMapper presentMapper;

    public int createPresent(present p){

        int present_id = presentMapper.insert(p);
//        if(present_id ==null)
        if(present_id == 0){
            throw new DaoYunException("发起签到失败",ErrorCode.CREATEPRESENT_FAIL);
        }
        return p.getId();
    }

    /**
     * 老师结束签到
     * @param id
     * @return
     */
    public boolean finishPressent(int id){
        return presentMapper.updateByID(id,1);
    }

    /**
     * 查询当前正在签到的课
     * @param class_id
     * @return
     */
    public present getPresent(int class_id){
        present p = presentMapper.findPresentByClass_id(class_id, 0);
        return p;
    }

    /**
     * 用签到号取出课程号用于加经验值
     * @param prescent
     * @return
     */
    public present getClass_id(int prescent_id){
        return presentMapper.findClassIdBy_PrescentId(prescent_id);

    }

}
