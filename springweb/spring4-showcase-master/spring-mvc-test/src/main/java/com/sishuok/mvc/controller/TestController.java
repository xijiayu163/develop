package com.sishuok.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test")
public class TestController {
	@RequestMapping(method = RequestMethod.GET)
    public ModelAndView view() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("dialog/dialog");
        return mv;
    }

}
