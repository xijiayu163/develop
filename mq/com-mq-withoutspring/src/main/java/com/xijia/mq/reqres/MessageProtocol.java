package com.xijia.mq.reqres;

public class MessageProtocol {
	public String handleProtocolMessage(String messageText){
		System.out.println(messageText);
		return "hello";
	}
}
