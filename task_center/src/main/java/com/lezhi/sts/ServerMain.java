package com.lezhi.sts;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Colin Yan on 2016/7/29.
 */
public class ServerMain {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/conf/applicationContext.xml");
        context.start();
        System.out.println("Task Center started!");
    }
}
