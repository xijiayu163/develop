package com.yu.jndi.demo1;

import java.util.*;

import javax.naming.*;

import javax.naming.directory.*;

import java.io.*;

public class ListAll {

    public static void main(java.lang.String[] args) {

        Hashtable<String,String> env = new Hashtable<>();

        env.put(Context.INITIAL_CONTEXT_FACTORY,

            "weblogic.jndi.WLInitialContextFactory");

        env.put(Context.PROVIDER_URL, "t3://localhost:7001");

        try {

            InitialContext ctx = new InitialContext(env);

            NamingEnumeration<Binding> enumeration = ctx.listBindings("");

            while(enumeration.hasMore()) {

                Binding binding = (Binding) enumeration.next();

                Object obj = (Object) binding.getObject();

                System.out.println(obj);

            }

        } catch (NamingException e) {

        System.out.println(e);

        }

    } // end main

} // end List