package com.savannah.controller;

import com.savannah.util.auth.Auth;
import com.savannah.util.auth.Group;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author stalern
 * @date 2019/12/10~15:50
 */
@RestController
public class TestControl {

    @RequestMapping("/login")
    @Auth(Group.BUYER)
    public String login() {
        return "login";
    }
    @RequestMapping("/index")
    @Auth(Group.BUYER)
    public String index(){
        return "index";
    }
}
