package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.facade.user.service.UserFacade;

import entity.User;

@Controller  
@RequestMapping("/user") 
public class UserController {
	@Autowired
	private UserFacade userFacade;
	
	public UserController() {
		System.out.println("initial UserController");
	}
	
	@RequestMapping("/{id}")  
    public ModelAndView view(@PathVariable("id") Long id, HttpServletRequest req) {  
		User user = userFacade.getUser(id);
  
        ModelAndView mv = new ModelAndView();  
        mv.addObject("user", user);  
        mv.setViewName("user/view");  
        return mv;  
    }  
}
