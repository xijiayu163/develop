package com.yuxijia.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yuxijia.bean.User;


@Controller
@RequestMapping("/user")
public class UserController {
	
	//参考链接:http://www.journaldev.com/2668/spring-validation-example-mvc-validator
	@Autowired
	@Qualifier("userValidator")
	private Validator validator;
	
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
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
    @RequestMapping(value ="/register",method = RequestMethod.GET)
    public ModelAndView registerPage(){
    	User user = new User();
    	ModelAndView mv = new ModelAndView("register");
    	mv.addObject("user", user);
    	return mv;
    }
    
    @RequestMapping("/register.do")
    public ModelAndView registerAction(@Validated User user,BindingResult bindingResult){
    	ModelAndView mv = new ModelAndView();
      
    	if(bindingResult.hasErrors()){
    		mv.setViewName("register");
    	}else{
    		mv.setViewName("registerSuccess");
    	}
    	return mv;
    }
}
