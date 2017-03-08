package rmi.demo.service;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import rmi.demo.facade.IHello;
import rmi.demo.facade.Person;
public class HelloImpl extends UnicastRemoteObject implements IHello {
    // 这个实现必须有一个显式的构造函数，并且要抛出一个RemoteException异常  
    protected HelloImpl() throws RemoteException {
        super();
    }
    /**
     * 说明清楚此属性的业务含义
     */
    private static final long serialVersionUID = 4077329331699640331L;
    public String sayHello(Person person) throws RemoteException {
        return "Hello " + person.getName() + " ^_^ ";
    }
    
    
    //
    public static void main(String[] args) {
        try {
            IHello hello = new HelloImpl();
            //加上此程序，就可以不要在控制台上开启RMI的注册程序，1099是RMI服务监视的默认端口
            //LocateRegistry.createRegistry(1101); 
            java.rmi.Naming.rebind("rmi://localhost:1101/hello", hello);
            System.out.print("Ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
