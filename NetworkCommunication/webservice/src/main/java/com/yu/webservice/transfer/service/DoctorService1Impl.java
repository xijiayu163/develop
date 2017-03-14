package com.yu.webservice.transfer.service;

import org.apache.cxf.frontend.ServerFactoryBean;

import com.yu.webservice.transfer.entity.Doctor1;

public class DoctorService1Impl implements DoctorService1{

	public Doctor1 getDoctor1(String doctorId) {
		Doctor1 doctor = new Doctor1();
		doctor.setDoctorUid(doctorId);
		doctor.setDoctorUName("doctor1");
		return doctor;
	}
	
	public static void main(String[] args) {
		DoctorService1 doctorService = new DoctorService1Impl();
		
		ServerFactoryBean bean=new ServerFactoryBean();
		bean.setAddress("http://127.0.0.1:8888/ws/cxf/DoctorService1");
        bean.setServiceClass(DoctorService1.class);//对外提供webservcie的业务类或者接口
        bean.setServiceBean(doctorService);//服务的实现bean
        bean.create();//创建，发布webservice
        System.out.println("wsdl地址:http://127.0.0.1:8888/ws/cxf/DoctorService1?WSDL");
		
	}

}
