package rmi.demo.facade;

import java.rmi.Remote;

public interface IHello extends Remote{
	public String sayHello(Person person) throws java.rmi.RemoteException;
}
