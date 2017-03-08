package rmi.demo.service;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import rmi.demo.facade.IHello;
import rmi.demo.facade.Person;
public class HelloImpl extends UnicastRemoteObject implements IHello {
    // ���ʵ�ֱ�����һ����ʽ�Ĺ��캯��������Ҫ�׳�һ��RemoteException�쳣  
    protected HelloImpl() throws RemoteException {
        super();
    }
    /**
     * ˵����������Ե�ҵ����
     */
    private static final long serialVersionUID = 4077329331699640331L;
    public String sayHello(Person person) throws RemoteException {
        return "Hello " + person.getName() + " ^_^ ";
    }
    
    
    //
    public static void main(String[] args) {
        try {
            IHello hello = new HelloImpl();
            //���ϴ˳��򣬾Ϳ��Բ�Ҫ�ڿ���̨�Ͽ���RMI��ע�����1099��RMI������ӵ�Ĭ�϶˿�
            //LocateRegistry.createRegistry(1101); 
            java.rmi.Naming.rebind("rmi://localhost:1101/hello", hello);
            System.out.print("Ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
