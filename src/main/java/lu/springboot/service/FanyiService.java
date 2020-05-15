package lu.springboot.service;

import lu.springboot.entity.Fanyi;
import lu.springboot.mapper.FanyiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FanyiService {

    @Autowired
    private FanyiMapper fanyiMapper;

    public Fanyi findOneFanyiObject() {
        return fanyiMapper.findFirstLine();
    }

    public void deleteFanyiByID(int id) {
        fanyiMapper.deleteFanyiByID(id);
    }

    public void insert(Fanyi fanyi) {
        if (fanyi == null) {
            return;
        }
        fanyiMapper.insert(fanyi);
    }

    public void updateByID(Fanyi fanyi) {
        if (fanyi == null) {
            return;
        }
        fanyiMapper.updateByID(fanyi);
    }

}
