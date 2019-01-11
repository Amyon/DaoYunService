package outfox.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import outfox.springboot.entity.Fanyi;
import outfox.springboot.mapper.FanyiMapper;

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
