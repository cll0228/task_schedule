package com.lezhi.sts;

import com.lezhi.sts.model.Task;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Colin Yan on 2016/7/29.
 */
public class ClientMain {

    public static void main(String[] args) throws SocketException {
/*
        StsStartup stsStartup = new StsStartup();
        stsStartup.setIp("localhost");
        stsStartup.setPort(5472);
        stsStartup.start();
*/
        //ApiCallService.helo();
        //ApiCallService.ping();

 //       Task task = new Task();
  //      TaskService.getInstance().submitTask(task);

        Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        while (allNetInterfaces.hasMoreElements())
        {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            System.out.println(netInterface.getName());
            Enumeration addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements())
            {
                ip = (InetAddress) addresses.nextElement();
                if (ip != null && ip instanceof Inet4Address)
                {
                    System.out.println("本机的IP = " + ip.getHostAddress());
                }
            }
        }


    }
}
