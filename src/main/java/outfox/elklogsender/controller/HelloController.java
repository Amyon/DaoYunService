package outfox.elklogsender.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author liugang
 */
@RestController
public class HelloController {

    @RequestMapping(value = "hello")
    public String hello(){
        return "hello, 世界!";
    }
}
