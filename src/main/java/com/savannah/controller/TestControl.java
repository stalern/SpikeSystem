package com.savannah.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author stalern
 * @date 2019/12/10~15:50
 */
@RestController
public class TestControl {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
