/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatear.cliente;

import peaje.formas.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import hibernate.cargar.Administrador;
import hibernate.cargar.GeneraXMLPersonal;
import java.net.Socket;
import java.net.UnknownHostException;

public class LeerTcp implements Runnable {

//  portList;
    InputStream inputStream;
    OutputStream outputSream;
    Thread readThread;
    public String tarjeta;
    private Socket socket;
    frmPrincipal princip;
    Administrador adm = new Administrador(GeneraXMLPersonal.user);
    String separador = File.separatorChar + "";

    public LeerTcp() {
    }

    public void abrir() {
        try {
            socket = new Socket("192.168.0.7", 1024);
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(LeerTcp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LeerTcp.class.getName()).log(Level.SEVERE, null, ex);
        }

            
    }

     
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
    }
  
}
