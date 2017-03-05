package com.yuxijia.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yuxijia.bean.User;
import com.yuxijia.bean.Users;


@Controller
@RequestMapping("/user")
public class UserController {
	/**
	 * 查看单个
	 * @param userid
	 * @param req
	 * @return
	 */
    @RequestMapping("/{id}")
    public ModelAndView view(@PathVariable("id") int userid, HttpServletRequest req) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", Users.getInstanceDC().getUser(userid));
        mv.setViewName("view");
        return mv;
    }
    
    /**
     * 查看列表
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView view() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("users", Users.getInstanceDC().getUsers());
        mv.setViewName("list");
        return mv;
    }
    
    /**
     * 新增视图
     * @return
     */
    @RequestMapping("/new")
    public ModelAndView newUser() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("new");
        return mv;
    }
    
    /**
     * 新增保存
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String create(@ModelAttribute("user") User user) {
    	//处理完增加逻辑后重定向到user
    	Users.getInstanceDC().getUsers().add(user);
        return "redirect:/user";
    }
    
    @RequestMapping("/del/{id}")
    public String delete(@PathVariable("id") int userid) {
    	//处理完增加逻辑后重定向到user
    	Users.getInstanceDC().deleteUser(userid);
        return "redirect:/user";
    }
    
    @RequestMapping("/update/{id}")
    public String update(@ModelAttribute("user") User user) {
    	//处理完增加逻辑后重定向到user
    	Users.getInstanceDC().modifyUser(user);
        return "redirect:/user";
    }
}
