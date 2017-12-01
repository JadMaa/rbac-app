package com.gti619.services;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

// this witre to authLogs.txt what happen to the system.
public class AuthLog {

    public static void write(String msg) throws Exception{
        FileWriter fw = new FileWriter("authLogs.txt",true);
        PrintWriter pw = new PrintWriter(fw,true);
        String prefix = (new Date()).toString()+"  :  ";
        pw.println(prefix+msg);
        pw.close();
    }
}
