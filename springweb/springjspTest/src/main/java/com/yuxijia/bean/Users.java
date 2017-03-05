package com.yuxijia.bean;

import java.util.ArrayList;
import java.util.List;

public class Users {
	private volatile static Users _instance;
	private List<User> users;
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	private Users(){
		users = new ArrayList<User>();
		for(int i=1;i<11;i++){
        	User user = new User();
        	user.setUserId(i);
        	user.setUserName("user"+i);
        	users.add(user);
        }
	}
	
	public static Users getInstanceDC() {
        if (_instance == null) {
            synchronized (Users.class) {
                if (_instance == null) {
                    _instance = new Users();
                }
            }
        }
        return _instance;
    }
	
	public void deleteUser(int id){
		User user = getUser(id);
		if(user!=null){
			users.remove(user);
		}
	}
	
	public User getUser(int id){
		for(User user:users){
			if(user.getUserId()==id){
				return user;
			}
		}
		return null;
	}
	
	public void modifyUser(User user){
		User user1 = getUser(user.getUserId());
		if(user!=null){
			user1.setUserName(user.getUserName());
		}
	}
}
