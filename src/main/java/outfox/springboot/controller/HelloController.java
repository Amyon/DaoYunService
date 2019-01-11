package outfox.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import outfox.springboot.entity.Fanyi;
import outfox.springboot.service.FanyiService;

/**
 *
 * @author zhanglu
 */
@RestController
@Slf4j
public class HelloController {

    @Autowired
    private FanyiService fanyiService;

    @RequestMapping(value = "hello")
    public String hello(){
        Fanyi fanyi = new Fanyi();
        fanyi.setDevToken("devTokne");
        fanyi.setImei("imei");
        fanyiService.insert(fanyi);
        Fanyi result = fanyiService.findOneFanyiObject();
        log.info(result.toString());
        fanyi.setDevToken("newDevToken");
        fanyiService.updateByID(fanyi);
        Fanyi newResult = fanyiService.findOneFanyiObject();
        log.info(newResult.toString());
        fanyiService.deleteFanyiByID(result.getId());
        return "hello world!";
    }
}
