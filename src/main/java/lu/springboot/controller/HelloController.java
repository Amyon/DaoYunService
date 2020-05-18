package lu.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import lu.springboot.entity.User;
import lu.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import lu.springboot.entity.Fanyi;
import lu.springboot.service.FanyiService;

/**
 *
 * @author zhanglu
 */
@RestController
@Slf4j
public class HelloController {

    @Autowired
    private FanyiService fanyiService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "user")
    @ResponseBody
    public User operateUser(){
        User result = userService.findOneUserObject();

        log.info(result.toString());
        return result;
    }
}
