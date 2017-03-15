package com.yu.webservice.transfer.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.yu.webservice.transfer.entity.Doctor1;

@WebService(serviceName="DoctorService1")
public interface DoctorService1 {
	public @WebResult Doctor1 getDoctor1(@WebParam(name="doctorId") String doctorId);
	
	public @WebResult List<Doctor1> getDoctor1List();
}
