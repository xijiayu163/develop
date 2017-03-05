package com.facade.user.service.impl;


import org.springframework.stereotype.Service;

import com.facade.user.service.UserFacade;

import entity.User;

@Service("userFacade")
public class UserFacadeImpl implements UserFacade{
	
	public UserFacadeImpl(){
		System.out.println("initial UserFacadeImpl");
	}

	public User getUser(long id) {
		User user = new User();  
        user.setId(id);  
        user.setName("zhang");  
        return user;
	}

}
