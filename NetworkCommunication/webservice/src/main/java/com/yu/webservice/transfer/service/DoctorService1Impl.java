package com.yu.webservice.transfer.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.frontend.ServerFactoryBean;

import com.yu.webservice.transfer.entity.Doctor1;

public class DoctorService1Impl implements DoctorService1{

	public Doctor1 getDoctor1(String doctorId) {
		Doctor1 doctor = new Doctor1();
		doctor.setDoctorUid(doctorId);
		doctor.setDoctorUName("doctor1");
		return doctor;
	}
	

	public List<Doctor1> getDoctor1List() {
		List<Doctor1> doctors = new ArrayList<Doctor1>();
		for(int i=1;i<5;i++){
			Doctor1 doctor = new Doctor1();
			doctor.setDoctorUid(String.valueOf(i));
			doctor.setDoctorUName("doctor"+i);
			doctors.add(doctor);
		}
		return doctors;
	}
	
	public static void main(String[] args) {
		DoctorService1 doctorService = new DoctorService1Impl();
		
		ServerFactoryBean bean=new ServerFactoryBean();
		bean.setAddress("http://127.0.0.1:8888/ws/cxf/DoctorService1");
        bean.setServiceClass(DoctorService1.class);//�����ṩwebservcie��ҵ������߽ӿ�
        bean.setServiceBean(doctorService);//�����ʵ��bean
        bean.create();//����������webservice
        System.out.println("wsdl��ַ:http://127.0.0.1:8888/ws/cxf/DoctorService1?WSDL");
	}

}
