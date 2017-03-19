package com.yuxijia.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.yuxijia.bean.User;

public class UserValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "userName", "userName.empty");
		User user = (User)target;
		if(user.getAge()<0){
			errors.rejectValue("age", "negativevalue","age can not be negative11");
		}else if(user.getAge()>110){
			errors.rejectValue("age", "too.old");
		}
	}
}
