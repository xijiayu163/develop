package rmi.demo.client;

import java.rmi.Naming;

import rmi.demo.facade.IHello;
import rmi.demo.facade.Person;
public class Hello_RMI_Client {
    public static void main(String[] args) {
        try {
            IHello hello = (IHello) Naming.lookup("rmi://localhost:1101/hello");
            Person person = new Person();
            person.setName("wo ca");
                System.out.println(hello.sayHello(person));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}