package com.gti619.services;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

// Journalisation des connexions
public class AuthLog {

    /**
     * Écriture de l'événement de ocnnexion
     * @param msg message à insérer dans l'événement de connexion
     * @throws Exception
     */
    public static void write(String msg) throws Exception{
        FileWriter fw = new FileWriter("authLogs.txt",true);
        PrintWriter pw = new PrintWriter(fw,true);
        String prefix = (new Date()).toString()+"  :  ";
        pw.println(prefix+msg);
        pw.close();
    }
}