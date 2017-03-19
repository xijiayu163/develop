package com.yuxijia.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yuxijia.bean.User;


@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/{userid}")
    public ModelAndView view(@PathVariable("userid") int userid, HttpServletRequest req) {
        User user = new User();
        user.setUserId(userid);
        user.setUserName("yuxijia");

        ModelAndView mv = new ModelAndView();
        mv.addObject("user", user);
        mv.setViewName("view");
        return mv;
    }
}
